package Business_Layer_Trucking.Delivery;


import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DeliveryController {

    private LinkedList<Demand>  demands;
    private HashMap<Integer,TruckingReport> activeTruckingReports;//<trID,TR>
    private HashMap<Integer,TruckingReport> oldTruckingReports;//<trID,TR>
    private HashMap<Integer,LinkedList<DeliveryForm>> activeDeliveryForms;
    private HashMap<Integer,LinkedList<DeliveryForm>> oldDeliveryForms;
    private HashMap<Integer,Site> sites;//<siteID,TR>
    /*
        private HashMap<Integer,DeliveryForm> deliveryForms;//<dfID,TR>
    */
    private HashMap<Integer, Item> items;
    private int lastSiteID;
    private int lastReportID ;
    private int lastDeliveryForms;
    private LinkedList<DeliveryForm> currDF;
    private TruckingReport currTR;
    private int lastItemId ;
    private static DeliveryController instance=null;

    public static DeliveryController getInstance() {
        if (instance==null)
            instance=new DeliveryController();
        return instance;
    }

    private DeliveryController()
    {
        this.demands=new LinkedList<>();
        this.activeTruckingReports=new HashMap<>();
        this.oldTruckingReports=new HashMap<>();
        this.oldDeliveryForms=new HashMap<>();
        this.sites=new HashMap<>();
/*
        this.deliveryForms=new HashMap<>();
*/
        // when data base is added, need to be set by it;
        this.items=new HashMap<>();
        this.lastDeliveryForms = 1;
        this.lastReportID =0;
        this.lastSiteID =1;
        this.currDF=new LinkedList<>();
        this.currTR=new TruckingReport(lastReportID);
        lastReportID++;
        this.activeDeliveryForms=new HashMap<>();
        this.lastItemId = 1;
    }

    public int createNewTruckingReport(){

        currDF =  new LinkedList<>();
        currTR =  new TruckingReport(lastReportID);
        currTR.setID(lastReportID);
        lastReportID++;
        return lastReportID-1 ;

    }

    /**
     * update the date in the current TR
     * @param date
     * @throws IllegalArgumentException
     */
    public void updateCurrTR_Date(LocalDate date) throws IllegalArgumentException{
        LocalDate now=LocalDate.now();
        if (date.compareTo(now)!=-1)
        {
            currTR.setDate(date);
        }
        else{
            throw new IllegalArgumentException("Cant enter date that passed");
        }

    }

    /**
     * update the leaving hour in the current TR
     * @param leavingHour
     * @throws IllegalArgumentException
     */
    public void updateCurrTR_LeavingHour(LocalTime leavingHour) throws IllegalArgumentException{
        LocalTime now=LocalTime.now();
        if (leavingHour.compareTo(now)!=-1)
        {
            currTR.setLeavingHour(leavingHour);
        }
        else{
            throw new IllegalArgumentException("Cant enter time that passed");
        }

    }

    /**
     * update the truck license number in the current TR
     * @param number
     */
    public void updateCurrTR_TruckNumber(String  number){//facade service need to check availability
        currTR.setTruckNumber(number);
    }

    /**
     * update the driver ID in the current TR
     * @param id
     */
    public void updateCurrTR_DriverID(String id){//facade service need to check availability
        currTR.setDriverID(id);
    }

    /**
     * update the origin site in the current TR
     * @param origin
     * @throws IllegalArgumentException - in case origin does not exist
     */
    public void updateCurrTR_Origin(int origin) throws IllegalArgumentException{
        try{
            sites.get(origin);
            currTR.setOrigin(origin);
        }
        catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("SiteID does not exist");
        }
    }

    /**
     * adding a destination to the current TR
     * @param destination
     * @throws IllegalArgumentException - in case destination does not exist
     */
    public void addCurrTR_Destination(int destination)throws IllegalArgumentException{
        if (sites.containsKey(destination))
            currTR.addDestination(destination);
        else throw new IllegalArgumentException("No such site exist");
    }

    /**
     * updates for thr current TR which TR he is replacing
     * @param tr_id
     * @throws NoSuchElementException
     */
    public void updateCurrTR_TrReplaced(int tr_id)throws NoSuchElementException{
        if (oldTruckingReports.containsKey(tr_id))
        {
            currTR.setTRReplace(oldTruckingReports.get(tr_id));
        }
        else if (activeTruckingReports.containsKey(tr_id))
        {
            currTR.setTRReplace(activeTruckingReports.get(tr_id));
        }
        else throw new NoSuchElementException("TR was not found");
    }

    /**
     * adding item to delivery form
     * @param demand
     * @param amount
     * @param ignore - indicating if to ignore the fact we delivering to two different delivery areas.
     * @param siteID
     * @throws IllegalStateException
     */
    public void addItemToDeliveryForm(Demand demand, int amount,boolean ignore,int siteID) throws IllegalStateException{
        if (currDF.isEmpty())//if list is empty
        {
            HashMap<Integer,Integer> itemsOnDF=new HashMap<>();
            itemsOnDF.put(items.get(demand.getItemID()).getID(),amount);
            DeliveryForm newDF=new DeliveryForm(lastDeliveryForms,items.get(demand.getItemID()).getOriginSiteId(),siteID ,itemsOnDF,
                    (int)items.get(demand.getItemID()).getWeight()*amount,currTR.getID());
            currDF.add(newDF);
            currTR.setOrigin(items.get(demand.getItemID()).getOriginSiteId());
            lastDeliveryForms++;
            if (sites.get(items.get(demand.getItemID()).getOriginSiteId()).getDeliveryArea() != sites.get(demand.getSite()).getDeliveryArea()){
                throw new IllegalStateException("Two different delivery areas");
            }
        }
        else // we need to look in the list maybe there is a form with the same origin& destination
        {
            boolean added=false;
            for (DeliveryForm df:currDF)
            {

                if (df.getOrigin()==items.get(demand.getItemID()).getOriginSiteId()&&df.getDestination()==siteID)
                {
                    HashMap<Integer,Integer> itemsOnDF=df.getItems();
                    if (itemsOnDF.containsKey(demand.getItemID())){
                        itemsOnDF.put(demand.getItemID(),amount + itemsOnDF.get(demand.getItemID()));
                    }
                    else
                        itemsOnDF.put(demand.getItemID(),amount);
                    df.setLeavingWeight((int) (df.getLeavingWeight()+items.get(demand.getItemID()).getWeight()*amount));
                    added=true;
                    break;
                }
                if (!ignore&&sites.get(demand.getSite()).getDeliveryArea()!=sites.get(df.getDestination()).getDeliveryArea()
                        && demand.getSite()!=items.get(demand.getItemID()).getOriginSiteId() )
                {
                    throw new IllegalStateException("Two different delivery areas");
                }
            }
            if (!added)
            {
                if (sites.get(items.get(demand.getItemID()).getOriginSiteId()).getDeliveryArea() != sites.get(demand.getSite()).getDeliveryArea()){
                    throw new IllegalStateException("Two different delivery areas");
                }
                HashMap<Integer,Integer> itemsOnDF=new HashMap<>();
                itemsOnDF.put(items.get(demand.getItemID()).getID(),amount);

                DeliveryForm deliveryForm=new DeliveryForm(lastDeliveryForms,items.get(demand.getItemID()).getOriginSiteId(),siteID
                        ,itemsOnDF, (int) (demand.getAmount()*items.get(demand.getItemID()).getWeight()),currTR.getID());
                currDF.add(deliveryForm);
                lastDeliveryForms++;
            }

        }
        updateTruckReportDestinations(currTR.getID());

    }



    public void updateCurrTR_ID(int id){
        currTR.setID(id);
    }



    public boolean addSite(String city,int  deliveryArea,String phoneNumber,String contactName,String name)

    {

        Site newSite = new Site(lastSiteID, city, deliveryArea, phoneNumber, contactName, name);

        sites.put(lastSiteID,newSite);
        lastSiteID++;
        return true;


    }
    public boolean removeSite(int siteID) throws NoSuchElementException, IllegalStateException
    {
        if (!sites.containsKey(siteID))
        {
            throw new NoSuchElementException("SiteID does not exist");
        }
        else
        {
            sites.remove(siteID);
            for (Map.Entry<Integer,Item> entry: items.entrySet()){
                if (entry.getValue().getOriginSiteId() == siteID){
                    removeItemFromPool(entry.getKey());
                }
            }
            return true;
        }

    }


    public void chooseLeavingHour(LocalTime leavingHour)throws IllegalArgumentException {

        currTR.setLeavingHour(leavingHour);

    }

    /**
     * Moving a TR that has been done from the active to the non active (old) zone.
     */
    public void saveReport(){
        removeCurrTakenDemandsFromTotal();
        activeTruckingReports.put(currTR.getID(),currTR);
        activeDeliveryForms.put(currTR.getID(),currDF);

    }

    public TruckingReport getTruckReport(int trNumber) throws NoSuchElementException{
        if (currTR.getID() == trNumber)
            return currTR;
        if (oldTruckingReports.containsKey(trNumber))
        {
            return oldTruckingReports.get(trNumber);
        }
        else if (activeTruckingReports.containsKey(trNumber))
        {
            return activeTruckingReports.get(trNumber);
        }
        else throw new NoSuchElementException("No Report with that ID");

    }

    /**
     *
     * @param dfNumber
     * @param trNumber
     * @return Delivery form a specific TR
     * @throws IllegalStateException
     * @throws NoSuchElementException
     */
    public DeliveryForm getDeliveryForm(int dfNumber, int trNumber) throws IllegalStateException,NoSuchElementException{
        DeliveryForm result=null;
        if (trNumber>currTR.getID())
        {
            throw new IllegalArgumentException("TR does not exist yet");
        }
        LinkedList<DeliveryForm> dfs=activeDeliveryForms.get(trNumber);
        for (DeliveryForm df:dfs)
        {
            if (df.getTrID()==trNumber&&dfNumber==df.getID())
            {
                result=df;
            }
        }
        if (result==null)
        {
            throw new NoSuchElementException("Delivery form does not exist");
        }
        return result;

    }

    /**
     * Removes a destination from thr current TR
     * @param site
     * @throws NoSuchElementException
     */
    public void removeDestination(int site) throws NoSuchElementException{
        LinkedList<DeliveryForm> toRemove = new LinkedList();
        if (!currTR.getDestinations().contains(site))
        {
            throw new NoSuchElementException("Site does not exist");
        }
        else {

            for (DeliveryForm df: currDF){

                if (df.getDestination() == site || df.getOrigin() == site ) {


                    for (Map.Entry<Integer, Integer> entry : df.getItems().entrySet()) {

                        addDemandToSystem(entry.getKey(), df.getDestination(), entry.getValue());
                    }
                    toRemove.add(df);
                }


            }
            int counter = 0;
            int temp =0;
            for (Integer id: currTR.getDestinations()){

                if (id == site){
                    counter = temp;
                }
                temp++;
            }
            currTR.getDestinations().remove(counter);

        }
        for (DeliveryForm df: toRemove){
            if (currDF.contains(df)){
                currDF.remove(df);
            }
            if (currDF.isEmpty())
                throw new NoSuchElementException("no more demands left to deliver, aborting..");
        }
        updateTruckReportDestinations(currTR.getID());
    }

    /**
     *
     * @param demand- says what item to remove,in which amount,from which site.
     */
    public void removeItemFromReport(Demand demand) {

        for (DeliveryForm df:currDF)
        {
            if (df.getDestination()==demand.getSite()&&df.getItems().containsKey(demand.getItemID()))
            {
                if (demand.getAmount()>=df.getItems().get(demand.getItemID()))
                    df.getItems().remove(demand.getItemID());
                else for(Map.Entry<Integer,Integer> entry:df.getItems().entrySet())
                {
                    if(entry.getKey()==demand.getItemID())
                    {
                        entry.setValue(entry.getValue()-demand.getAmount());
                    }
                }
            }
        }
        updateTruckReportDestinations(currTR.getID());
    }

    public void removeItemFromPool(int item)throws NoSuchElementException, IllegalStateException {



        if (!items.containsKey(item))
        {
            throw new NoSuchElementException("No item with that ID");
        }
        for (Map.Entry<Integer,LinkedList<DeliveryForm>> entry:activeDeliveryForms.entrySet())
        {
            LinkedList<DeliveryForm> ldf=entry.getValue();
            for (DeliveryForm df:ldf)
            {
                HashMap<Integer,Integer> items= df.getItems();
                for (Map.Entry<Integer,Integer> integerIntegerEntry:items.entrySet())
                {
                    if (integerIntegerEntry.getKey()==item)
                        throw new IllegalStateException("Cant delete item when there is a delivery form with it");
                }
            }
        }
        for (Demand d:demands)
        {
            if (d.getItemID()==item)
                throw new IllegalStateException("Cant delete item when there is a demand with it");
        }
        items.remove(item);
    }



    public void addItem( double weight, String name, int siteID)throws NoSuchElementException,KeyAlreadyExistsException {
        if (items.containsKey(lastItemId))
            throw new KeyAlreadyExistsException("ID already taken");
        else if (!sites.containsKey(siteID))
            throw new NoSuchElementException("this site doesn't exist");
        else {
            Item item=new Item(lastItemId, weight, name,siteID );
            items.put(lastItemId,item);
            lastItemId++;
        }
    }

    public void displaySites() {

        for (Integer i:currTR.getDestinations())
        {
            System.out.println(i+" - "+sites.get(i).getName()+"/n");
        }
    }

    public double getItemWeight(int itemID) throws NoSuchElementException{
        if (items.containsKey(itemID))
            return items.get(itemID).getWeight();
        else throw new NoSuchElementException("No such item exist");
    }
    public LinkedList<Demand> getDemands() {
        return demands;
    }

    /**
     *
     * @return LinkedList of demands that are not included in the current TR
     * @throws NoSuchElementException
     */
    public LinkedList<Demand> showDemands() throws NoSuchElementException {

        if (!demands.isEmpty())
        {
            LinkedList<Demand> result=new LinkedList<>();
            for(Demand d:demands)
            {
                boolean added =false;

                if (currDF.isEmpty()){
                    return demands;
                }
                for (DeliveryForm df:currDF)
                {
                    if(df.getItems().containsKey(d.getItemID())&&df.getDestination()==d.getSite())
                    {
                        added = true;
                        int newAmount =d.getAmount()-df.getItems().get(d.getItemID());
                        if (newAmount>0){
                            Demand toAdd=new Demand(d.getItemID(),d.getSite(),newAmount);
                            result.add(toAdd);
                        }
                        //result.add(d);
                    }

                }
                if (!added)
                    result.add(d);
            }
            return result;
        }

        throw new NoSuchElementException("no more demands found yet");

    }

    public String getItemName(int itemID)throws NoSuchElementException {
        if (items.containsKey(itemID))
            return items.get(itemID).getName();
        else throw new NoSuchElementException("No such item exist");
    }

    public String getSiteName(int site) throws NoSuchElementException{
        if (sites.containsKey(site))
            return sites.get(site).getName();
        else throw new NoSuchElementException("No such site exist");
    }

    /**
     *
     * @return LinkedList with sites in the system.
     */
    public LinkedList<Site> getCurrentSites() {
        LinkedList<Site> result=new LinkedList<>();
        LinkedList<Integer> sitesID = currTR.getDestinations();
        for (Integer id: sitesID)
        {

            result.add(sites.get((id)));
        }
        return result;
    }

    /**
     *
     * @return LinkedList with items in the Current in-build trucking Report.
     * no special order is returned
     */
    public LinkedList<Demand> getItemsOnTruck() {
        LinkedList<Demand> result=new LinkedList<>();
        for (DeliveryForm df: currDF)
        {
            for (Map.Entry<Integer,Integer> entry:df.getItems().entrySet())
            {
                Demand d=new Demand(entry.getKey(),df.getDestination(),entry.getValue());
                result.add(d);
            }
        }
        return result;
    }

    public LinkedList<Item> getItems() {
        LinkedList<Item> result=new LinkedList<>();
        for (Map.Entry<Integer,Item> entry:items.entrySet())
        {
            result.add(entry.getValue());
        }
        return result;
    }


    public LinkedList<Site> getAllSites() throws NoSuchElementException {
        LinkedList<Site> sites = new LinkedList<>();
        if (!this.sites.isEmpty()) {
            for (Map.Entry<Integer, Site> entry : this.sites.entrySet()) {
                sites.add(entry.getValue());
            }

            return sites;
        }
        else throw new NoSuchElementException("No sites in the system");
    }

    /**
     * Adding a demand that we received from one of the sites.
     * @param itemId
     * @param site
     * @param amount
     */
    public void addDemandToSystem(int itemId, int site, int amount) {
        if (!items.containsKey(itemId))
        {
            throw new NoSuchElementException("Entered wrong item id");
        }
        if (!sites.containsKey(site))
        {
            throw new NoSuchElementException("Entered wrong site id");
        }
        if (items.get(itemId).getOriginSiteId() == site){
            throw new NoSuchElementException("Origin site and Deliver Site must be different");
        }
        boolean inserted=false;
        for (Demand d:demands)
        {
            if (d.getItemID()==itemId&&d.getSite()==site) {
                d.setAmount(d.getAmount() + amount);
                inserted=true;
                break;
            }
        }
        if (!inserted)
        {   // checks the delivery area is same
            demands.add(new Demand(itemId,site,amount));
            if (sites.get(items.get(itemId).getOriginSiteId()).getDeliveryArea() != sites.get(site).getDeliveryArea())
                throw new InputMismatchException("the demand has 2 delivery area in it\n" +
                        "you may remove it through menu or you can procced.\n" +
                        "please notice, if you proceed");

        }

    }

    /**
     * Calculating the total weight of the current Delivery.
     * @return
     */
    public int getWeightOfCurrReport() {
        int weight =0;
        for (DeliveryForm df:currDF)
        {
            for (Map.Entry<Integer,Integer> entry:df.getItems().entrySet())
            {
                weight= (int) (weight+items.get(entry.getKey()).getWeight()*entry.getValue());
            }
        }
        return weight;
    }



    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< getters, setters>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.


    public TruckingReport getCurrTR() {
        return currTR;
    }

    public LinkedList<DeliveryForm> getCurrDF() {
        return currDF;
    }

    //    public HashMap<Integer, DeliveryForm> getDeliveryForms() {
//        return deliveryForms;
//    }
    public LinkedList<DeliveryForm> getDeliveryForms(int trID){
        return activeDeliveryForms.get(trID);
    }

    public HashMap<Integer, Site> getSites() {
        return sites;
    }

    public LinkedList<TruckingReport> getActiveTruckingReports() {
        LinkedList<TruckingReport> result= new LinkedList<>();
        for(Map.Entry<Integer,TruckingReport> entry:activeTruckingReports.entrySet())
        {
            result.add(entry.getValue());
        }
        return result;
    }

    public HashMap<Integer, TruckingReport> getOldTruckingReports() {
        return oldTruckingReports;
    }


    public void updateDeliveryFormRealWeight(int trID, int dfID, int weight){
        LinkedList<DeliveryForm> deliveryForms = activeDeliveryForms.get(trID);
        for (DeliveryForm df:deliveryForms){
            if (df.getID() == dfID) {
                df.setLeavingWeight(weight);
                df.setCompleted();
            }

        }
    }

    public boolean checkIfAllCompleted(int trID) {
        LinkedList<DeliveryForm> dfs=activeDeliveryForms.get(trID);
        boolean completed=true;
        for (DeliveryForm df:dfs)
        {
            completed=completed&&df.isCompleted();
        }
        return completed;

    }

    public void archive(int trID){
        oldTruckingReports.put(trID,activeTruckingReports.get(trID));
        oldDeliveryForms.put(trID,getDeliveryForms(trID));
        activeTruckingReports.remove(trID);
        activeDeliveryForms.remove(trID);
        activeDeliveryForms.remove(getDeliveryForms(trID));
    }

    public void archiveNotCompleted(int trID) {
        TruckingReport truckReport = getTruckReport(trID);
        int newTrID= createNewTruckingReport();
        currTR.setDate(truckReport.getDate());
        currTR.setLeavingHour(truckReport.getLeavingHour());
        currTR.setTruckNumber(truckReport.getTruckNumber());
        currTR.setDriverID(truckReport.getDriverID());
        currTR.setOrigin(truckReport.getOrigin());
        currTR.setDestinations(truckReport.getDestinations());
        currTR.setTRReplace(truckReport);
        // removing all Delivery forms
        LinkedList<DeliveryForm> oldDfs =  activeDeliveryForms.get(truckReport.getID());

        for (DeliveryForm df : oldDfs){
            DeliveryForm nd = new DeliveryForm(df);
            nd.setID(lastDeliveryForms);
            lastDeliveryForms++;
            currDF.add(nd);
        }
        activeDeliveryForms.remove(truckReport.getID());
        oldDeliveryForms.put(truckReport.getID(),oldDfs);
        //sets the old truck report and removes from active
        oldTruckingReports.put(truckReport.getID(),truckReport);
        activeTruckingReports.remove(truckReport.getID());


    }

    public int getSiteDeliveryArea(int siteID) {
        return sites.get(siteID).getDeliveryArea();
    }

    public void removeSiteFromTruckReport(int siteID, int trID)throws NoSuchElementException{
        boolean removed =false;
        LinkedList<DeliveryForm> ldfs= new LinkedList<>(currDF);
        for (DeliveryForm df:ldfs) {
            if (!df.isCompleted()){

                for (Map.Entry<Integer, Integer> entry : df.getItems().entrySet()) {

                    addDemandToSystem(entry.getKey(), df.getDestination(), entry.getValue());
                }
                if (df.getDestination() == siteID || df.getOrigin() == siteID) {

                    currDF.remove(df);
                    currTR.getDestinations().remove(Integer.valueOf(siteID));

                    removed = true;
                }
            }
        }
        if (!removed) {
            throw new NoSuchElementException("site does not included in Truck Report");
        }

        updateTruckReportDestinations(currTR.getID());

    }

    public TruckingReport getReplaceTruckingReport(int trID) {
        if (currTR.getTRReplace().getID() == trID)
            return currTR;
        for(Map.Entry<Integer,TruckingReport> act : activeTruckingReports.entrySet()){
            if (act.getValue().getTRReplace().getID() == trID){
                return act.getValue();
            }
        }

        return null;
    }

    public boolean addDemandToTruckReport(int itemNumber, int amount, int siteID, int trID)throws IllegalStateException {
        TruckingReport tr=activeTruckingReports.get(trID);
        LinkedList<DeliveryForm>ldfs=activeDeliveryForms.get(trID);
        boolean added=false;

        for (DeliveryForm df:ldfs)
        {
            if (sites.get(siteID).getDeliveryArea()!=sites.get(df.getDestination()).getDeliveryArea())
            {
                throw new IllegalStateException("Two different delivery areas");
            }
            int origin=items.get(itemNumber).getOriginSiteId();

            if ((df.getDestination()==siteID&&df.getOrigin()==origin&&df.getItems().containsKey(itemNumber)))
            {
                HashMap<Integer,Integer> items=df.getItems();
                for (Map.Entry<Integer,Integer> entry:items.entrySet())
                {
                    if (entry.getKey()==itemNumber)
                    {
                        entry.setValue(entry.getValue()+amount);
                        added=true;
                        break;
                    }
                }
                if (!added){
                    items.put(itemNumber,amount);
                    added=true;
                }
                else break;
            }
        }
        updateTruckReportDestinations(trID);
        return added;
    }

    public void replaceDriver(int trID, String driverID){
        TruckingReport tr=activeTruckingReports.get(trID);
        tr.setDriverID(driverID);

    }

    public LinkedList<Demand> getItemOnReport(int trID) {
        LinkedList<DeliveryForm> ldfs= getDeliveryFormsFromOld(trID);
        LinkedList<Demand> result=new LinkedList<>();
        for (DeliveryForm df:ldfs)
        {
            HashMap<Integer,Integer> items= df.getItems();
            for (Map.Entry<Integer,Integer> item:items.entrySet())
            {
                Demand demand=new Demand(item.getKey(),this.items.get(item.getKey()).getOriginSiteId() ,item.getValue());
                result.add(demand);
            }
        }
        return result;
    }

    private LinkedList<DeliveryForm> getDeliveryFormsFromOld(int trID) {
        if (currTR.getTRReplace().getID() == trID)
            return currDF;
        TruckingReport tr = getReplaceTruckingReport(trID);
        return activeDeliveryForms.get(tr.getID());
    }

    public void removeItemFromTruckingReport(int trID,int itemID,int siteID){

        LinkedList<DeliveryForm> deliveryForms=getUncompletedDeliveryFormsFromOld(trID);
        int origin=items.get(itemID).getOriginSiteId();
        for (DeliveryForm df:deliveryForms)
        {
            if (df.getDestination()==siteID&&df.getOrigin()==origin&&df.getItems().containsKey(itemID))
            {
                addDemandToSystem(itemID,siteID,df.getItems().get(itemID));
                df.getItems().remove(itemID);

                if (df.getItems().isEmpty()) {

                    deliveryForms.remove(df);
                }

            }
        }
        updateTruckReportDestinations(getReplaceTruckingReport(trID).getID());
        saveReport();
    }

    public boolean continueAddDemandToTruckReport(int itemNumber, int amount, int siteID, int trId){
        LinkedList<DeliveryForm> ldfs=activeDeliveryForms.get(trId);
        boolean added=false;
        int origin=items.get(itemNumber).getOriginSiteId();
        for (DeliveryForm df:ldfs)
        {
            if (origin==df.getOrigin()&&df.getDestination()==siteID)
            {
                HashMap<Integer,Integer> items=df.getItems();
                for (Map.Entry<Integer,Integer> item:items.entrySet())
                {
                    if (item.getKey()==itemNumber)
                    {
                        item.setValue(item.getValue()+amount);
                        added=true;
                        break;
                    }
                }
            }
        }
        if (!added){
            HashMap<Integer,Integer> itemsOnDF=new HashMap<>();
            itemsOnDF.put(itemNumber,amount);
            int leavingWeight= (int) (items.get(itemNumber).getWeight()*amount);
            DeliveryForm newDF=new DeliveryForm(ldfs.size()+1,origin,siteID,itemsOnDF,leavingWeight,trId);
            ldfs.add(newDF);
            updateTruckReportDestinations(trId);
        }

        return true;
    }

    public void chooseDateToCurrentTR(LocalDate chosen) {
        if ((chosen.compareTo(LocalDate.now()) ==0) && currTR.getLeavingHour().isBefore(LocalTime.now())||chosen.isBefore(LocalDate.now()))
            throw new IllegalArgumentException("time inserted is invalid.");
        currTR.setDate(chosen);
    }

    private void updateTruckReportDestinations(int trID){
        boolean active = false;
        TruckingReport tr = currTR;
        LinkedList<Integer> newDestinations = new LinkedList<>();
        LinkedList<DeliveryForm> dfs=currDF;
        if (! (currTR .getID() == trID)) {
            tr = activeTruckingReports.get(trID);
            dfs=activeDeliveryForms.get(trID);

        }
        for(DeliveryForm df:dfs){
            if (!dfs.contains(df.getDestination())){
                newDestinations.add(df.getDestination());
            }
        }
        tr.setDestinations(newDestinations);

    }



    public void removeDemand(int itemID, int site) {
        for (Demand d: demands) {
            if (d.getSite() == site && d.getItemID() == itemID){
                demands.remove(d);
                break;
            }
        }
    }

    public int getItemOrigin(int itemID) {
        return items.get(itemID).getOriginSiteId();
    }

    public LinkedList<Demand> getCurrentDemands() {
        LinkedList<Demand> result = new LinkedList<>();
        for(DeliveryForm df: currDF){
            for(Map.Entry<Integer,Integer> entry: df.getItems().entrySet()){
                result.add(new Demand(entry.getKey(),df.getDestination(), entry.getValue()));
            }
        }
        return result;
    }


    public void saveReplacedTruckReport() {

        activeTruckingReports.put(currTR.getID(),currTR);
        activeDeliveryForms.put(currTR.getID(),currDF);
    }

    public int moveDemandsFromCurrentToReport(int replaceId) {

        /*TruckingReport replace = getReplaceTruckingReport(replaceId);
        if (replace != null){
            for(DeliveryForm df: currDF){
                boolean found = false;
                for (DeliveryForm rdf:activeDeliveryForms.get(replace.getID())){
                    if (rdf.getOrigin() == df.getOrigin() && rdf.getDestination() == df.getDestination()){
                        mergeDeliveryForms(df,rdf);
                        found = true;
                    }
                }
                if (!found){
                    activeDeliveryForms.get(replace.getID()).add(df);
                    activeTruckingReports.get(replace.getID()).getDestinations().add(df.getDestination());
                }
            }
            return replace.getID();
        }
        else*/

            currTR.setTRReplace(getTruckReport(replaceId));
            int output = currTR.getID();
            saveReport();
            return output;






    }

    /**
     * this method merges 2 delivery forms
     * @param df
     * @param rdf
     */
    private void mergeDeliveryForms(DeliveryForm df, DeliveryForm rdf) {
        if (!(rdf.getOrigin() == df.getOrigin() && rdf.getDestination() == df.getDestination())){
            throw new InputMismatchException("delivery forms do not has same origin or destination");
        }
        for (Map.Entry<Integer,Integer> dfItem: df.getItems().entrySet()){
            boolean found =false;
            for (Map.Entry<Integer,Integer> rdfItem: rdf.getItems().entrySet()){
                if (dfItem.getKey().equals(rdfItem.getKey())) {
                    rdfItem.setValue(rdfItem.getValue() + dfItem.getValue());
                    found = true;
                }
            }
            if (!found){
                rdf.getItems().put(dfItem.getKey(),dfItem.getValue());
            }

        }
        removeCurrTakenDemandsFromTotal();

    }

    private void removeCurrTakenDemandsFromTotal(){
        for (DeliveryForm deliveryForm: currDF){
            if(!deliveryForm.isCompleted()) {
                HashMap<Integer, Integer> items = deliveryForm.getItems();
                LinkedList<Demand> toRemove = new LinkedList<>();
                for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
                    for (Demand demand : demands) {
                        if (demand.getItemID() == entry.getKey() && demand.getSite() == deliveryForm.getDestination()) {
                            demand.setAmount(demand.getAmount() - entry.getValue());
                            if (demand.getAmount() == 0)
                                toRemove.add(demand);
                        }
                    }
                }
                for (Demand d : toRemove) {
                    demands.remove(d);
                }
            }
        }

    }

    public void setNewTruckToTR(int tRid, String truckNumber) {
        getTruckReport(tRid).setTruckNumber(truckNumber);
    }
    public void setNewDriverToTR(int tRid,String driverID){
        getTruckReport(tRid).setDriverID(driverID);
    }


    public void makeDeliveryFormUncompleted(int trID, int dfID) {
        LinkedList<DeliveryForm> deliveryForms = activeDeliveryForms.get(trID);
        for (DeliveryForm df:deliveryForms){
            if (df.getID() == dfID) {

                df.setUncompleted();
            }

        }
    }

    public LinkedList<DeliveryForm> getUncompletedDeliveryFormsFromOld(int old_id) {
        TruckingReport replace = getReplaceTruckingReport(old_id);
        if (replace.getID() == currTR.getID())
            return currDF;

        return activeDeliveryForms.get(replace.getID());
    }
}
