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
    private HashMap<Integer,DeliveryForm> deliveryForms;//<dfID,TR>
    private HashMap<Integer, Item> items;
    private int lastSiteID;
    private int lastReportID ;
    private int lastDeliveryForms;
    private LinkedList<DeliveryForm> currDF;
    private TruckingReport currTR;
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
        this.deliveryForms=new HashMap<>();
        // when data base is added, need to be set by it;
        this.items=new HashMap<>();
        this.lastDeliveryForms = 1;
        this.lastReportID =0;
        this.lastSiteID =1;
        this.currDF=new LinkedList<>();
        this.currTR=new TruckingReport(lastReportID);
        this.activeDeliveryForms=new HashMap<>();
    }

    public int createNewTruckingReport(){
        currDF =  new LinkedList<>();
        currTR =  new TruckingReport(lastReportID);
        currTR.setID(lastReportID);
        return lastReportID ;

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
            DeliveryForm newDF=new DeliveryForm(lastDeliveryForms,siteID,items.get(demand.getItemID()).getOriginSiteId() ,itemsOnDF,
                    (int)items.get(demand.getItemID()).getWeight()*amount,currTR.getID());
            currDF.add(newDF);
            currTR.setOrigin(items.get(demand.getItemID()).getOriginSiteId());
            lastDeliveryForms++;
        }
        else // we need to look in the list maybe there is a form with the same origin& destination
        {boolean added=false;
            for (DeliveryForm df:currDF)
            {

                if (df.getOrigin()==siteID&&df.getDestination()==demand.getSite())
                {
                    HashMap<Item,Integer> itemsOnDF=new HashMap<>();
                    itemsOnDF.put(items.get(demand.getItemID()),amount);
                    df.getItems().put(items.get(demand.getItemID()).getID(),amount);
                    df.setLeavingWeight((int) (df.getLeavingWeight()+items.get(demand.getItemID()).getWeight()*amount));
                    added=true;
                    break;
                }
                if (!ignore&&sites.get(demand.getSite()).getDeliveryArea()!=sites.get(df.getDestination()).getDeliveryArea()
                && demand.getSite()!=items.get(demand.getItemID()).getOriginSiteId())
                {
                    throw new IllegalStateException("Two different delivery areas");
                }
            }
            if (!added)
            {
                HashMap<Integer,Integer> itemsOnDF=new HashMap<>();
                itemsOnDF.put(items.get(demand.getItemID()).getID(),amount);

                DeliveryForm deliveryForm=new DeliveryForm(lastDeliveryForms,siteID,items.get(demand.getItemID()).getOriginSiteId()
                        ,itemsOnDF, (int) (demand.getAmount()*items.get(demand.getItemID()).getWeight()),currTR.getID());
                currDF.add(deliveryForm);
            }
        }
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
    public boolean removeSite(int siteID) throws NoSuchElementException
    {
        if (!sites.containsKey(siteID))
        {
            throw new NoSuchElementException("SiteID does not exist");
        }
        else
        {
            sites.remove(siteID);
            return true;
        }

    }


    public void chooseLeavingHour(LocalTime leavingHour)throws IllegalArgumentException {
        LocalTime now=LocalTime.now();
        if (leavingHour.compareTo(now) !=-1)
        {
            currTR.setLeavingHour(leavingHour);
        }
        else{
            throw new IllegalArgumentException("Cant enter time that passed");
        }
    }

    /**
     * Moving a TR that has been done from the active to the non active (old) zone.
     */
    public void saveReport(){

        activeTruckingReports.put(currTR.getID(),currTR);
        activeDeliveryForms.put(currTR.getID(),currDF);
        lastReportID++;

    }

    public TruckingReport getTruckReport(int trNumber) throws NoSuchElementException{
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
        for (Map.Entry<Integer,DeliveryForm> entry:deliveryForms.entrySet())
        {
            if (entry.getValue().getTrID()==trNumber&&dfNumber==entry.getValue().getID())
            {
                result=entry.getValue();
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
        if (!currTR.getDestinations().contains(site))
        {
            throw new NoSuchElementException("Site does not exist");
        }
        else currTR.getDestinations().remove(site);
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
    }

    public void removeItemFromPool(int item)throws NoSuchElementException {
        if (!items.containsKey(item))
        {
            throw new NoSuchElementException("No item with that ID");
        }
        else items.remove(item);
    }

    public void addItem(int id, double weight, String name, int siteID)throws NoSuchElementException,KeyAlreadyExistsException {
        if (items.containsKey(id))
            throw new KeyAlreadyExistsException("ID already taken");
        else if (!sites.containsKey(siteID))
            throw new NoSuchElementException("this site doesn't exist");
        else {
            Item item=new Item(id, weight, name,siteID );
            items.put(id,item);
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

        // TODO need to subtract the amount from current DF and current TR in order to show the relavent amount
        if (!demands.isEmpty())
        {
            LinkedList<Demand> result=new LinkedList<>();
            for(Demand d:demands)
            {
                if (currDF.isEmpty()){
                    return demands;
                }
                for (DeliveryForm df:currDF)
                {
                    if(!(df.getItems().containsKey(d.getItemID())&&df.getDestination()==d.getSite()&&d.getAmount()>0))
                    {
                        Demand toAdd=new Demand(d.getItemID(),d.getSite(),d.getAmount()-df.getItems().get(d.getItemID()));
                        result.add(toAdd);
                        //TODO - check if working -  not it is not
                        //result.add(d);
                    }
                }
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
        for (Map.Entry<Integer,Site> entry:sites.entrySet())
        {
            result.add(entry.getValue());
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
        {
            demands.add(new Demand(itemId,site,amount));
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

    public List<DeliveryForm> getCurrDF() {
        return currDF;
    }

    public HashMap<Integer, DeliveryForm> getDeliveryForms() {
        return deliveryForms;
    }
    public LinkedList<DeliveryForm> getDeliveryForms(int trID){
        LinkedList<DeliveryForm> forms=new LinkedList<>();
        for (Map.Entry<Integer,DeliveryForm> entry:deliveryForms.entrySet())
        {
            forms.add(entry.getValue());
        }
        return forms;
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


    public void updateDeliveryFormRealWeight(int dfID, int weight){
        deliveryForms.get(dfID).setLeavingWeight(weight);
        deliveryForms.get(dfID).setCompleted();
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
    }

    public void archiveNotCompleted(int trID) {
        // TODO need to update all the DF to the new TR
        LinkedList<DeliveryForm> dfsToTranfer= activeDeliveryForms.get(trID);
        oldTruckingReports.put(trID,activeTruckingReports.get(trID));
        int new_TR_ID=createNewTruckingReport();
        oldDeliveryForms.put(trID,dfsToTranfer);
        activeDeliveryForms.put(new_TR_ID,dfsToTranfer);


    }

    public int getSiteDeliveryArea(int siteID) {
        return sites.get(siteID).getDeliveryArea();
    }

    public void chooseDateToCurrentTR(LocalDate chosen) {
        currTR.setDate(chosen);
    }
}

