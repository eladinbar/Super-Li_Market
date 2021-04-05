package Business_Layer_Trucking.Delivery;

import Business_Layer_Trucking.Facade.FacadeObject.FacadeDeliveryForm;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class DeliveryController {

    private LinkedList<Demand>  demands;
    private HashMap<Integer,TruckingReport> activeTruckingReports;
    private HashMap<Integer,TruckingReport> oldTruckingReports;
    private HashMap<Integer,Site> sites;
    private HashMap<Integer,DeliveryForm> deliveryForms;
    private HashMap<Integer, Item> items;
    private int lastReportID ;
    private int lastDeliveryForms;
    private List<DeliveryForm> currDF;
    private TruckingReport currTR;
    private static DeliveryController instance=null;

    public static DeliveryController getInstance() {
        if (instance==null)
            instance=new DeliveryController();
        return instance;
    }

    private DeliveryController()
    {
        demands=new LinkedList<>();
        activeTruckingReports=new HashMap<>();
        oldTruckingReports=new HashMap<>();
        this.sites=new HashMap<>();
        deliveryForms=new HashMap<>();
        // when data base is added, need to be setted by it;
        this.items=new HashMap<>();
        lastDeliveryForms = 0;
        lastReportID =0;
        currDF=new LinkedList<>();
        currTR=new TruckingReport(lastReportID);
    }




    public int createNewTruckingReport(){
        currDF =  new LinkedList<>();
        currTR =  new TruckingReport(lastReportID++);
        currTR.setID(lastReportID++);
        return lastReportID ;

    }


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
    public void updateCurrTR_TruckNumber(int number){//facade service need to check availability
        currTR.setTruckNumber(number);
    }
    public void updateCurrTR_DriverID(int id){//facade service need to check availability
        currTR.setDriverID(id);
    }

    public void updateCurrTR_Origin(int origin) throws IllegalArgumentException{
        try{
            sites.get(origin);
            currTR.setOrigin(origin);
        }
        catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("SiteID does not exist");
        }
    }
    public void addCurrTR_Destination(int destination){
            sites.get(destination);
            currTR.addDestination(destination);

    }
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

    public void addItemToDeliveryForm(Demand demand, int amount,boolean ignore,int siteID) throws IllegalStateException{
        boolean ignoreDifferentDeliveryAreas=ignore;
        if (currDF.isEmpty())//if list is empty
        {

            HashMap<Integer,Integer> itemsOnDF=new HashMap<>();
            itemsOnDF.put(items.get(demand.getItemID()).getID(),amount);
            DeliveryForm newDF=new DeliveryForm(1,siteID,demand.getSite(),itemsOnDF,
                    items.get(demand.getItemID()).getWeight()*amount,currTR.getID());
        }
        else // we need to look in the list maybe there is a form with the same origin& destination
        {
            for (DeliveryForm df:currDF)
            {

                if (df.getOrigin()==siteID&&df.getDestination()==demand.getSite())
                {
                    HashMap<Item,Integer> itemsOnDF=new HashMap<>();
                    itemsOnDF.put(items.get(demand.getItemID()),amount);
                    df.getItems().put(items.get(demand.getItemID()).getID(),amount);
                    df.setLeavingWeight(df.getLeavingWeight()+items.get(demand.getItemID()).getWeight()*amount);
                    break;
                }
                if (!ignore&&sites.get(demand.getSite()).getDeliveryArea()!=sites.get(df.getDestination()).getDeliveryArea())
                {
                    throw new IllegalStateException("Two different delivery areas");
                }
            }
        }
    }



    public void updateCurrTR_ID(int id){
        currTR.setID(id);
    }




    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< gettters, setters>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.


    public TruckingReport getCurrTR() {
        return currTR;
    }

    public List<DeliveryForm> getCurrDF() {
        return currDF;
    }

    public HashMap<Integer, DeliveryForm> getDeliveryForms() {
        return deliveryForms;
    }

    public HashMap<Integer, Site> getSites() {
        return sites;
    }

    public HashMap<Integer, TruckingReport> getActiveTruckingReports() {
        return activeTruckingReports;
    }

    public HashMap<Integer, TruckingReport> getOldTruckingReports() {
        return oldTruckingReports;
    }

    public boolean addSite(String city,int  siteID,int  deliveryArea,String phoneNumber,String contactName,String name)
            throws KeyAlreadyExistsException
    {

        if (sites.containsKey(siteID))
        {
            throw new KeyAlreadyExistsException("Duplicate ID");
        }
        else {
            Site newSite = new Site(siteID, city, deliveryArea, phoneNumber, contactName, name);
            sites.put(siteID,newSite);
            return true;
        }

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

    public void saveReport(){

        oldTruckingReports.put(currTR.getID(),currTR);

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
            throw new NoSuchElementException();
        }
        return result;

    }

    public void removeDestination(int site) throws NoSuchElementException{
        if (!currTR.getDestinations().contains(site))
        {
            throw new NoSuchElementException();
        }
        else currTR.getDestinations().remove(site);
    }

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
          throw new NoSuchElementException();
      }
      else items.remove(item);
    }

    public void addItem(int id, int weight, String name)throws KeyAlreadyExistsException {
        if (items.containsKey(id))
            throw new KeyAlreadyExistsException("ID already taken");
        else {
            Item item=new Item(id, weight, name);
            items.put(id,item);
        }
    }

    public void displaySites() {

        for (Integer i:currTR.getDestinations())
        {
            System.out.println(i+" - "+sites.get(i).getName()+"/n");
        }
    }

    public int getItemWeight(int itemID) throws NoSuchElementException{
        if (items.containsKey(itemID))
            return items.get(itemID).getWeight();
        else throw new NoSuchElementException();
    }
    public LinkedList<Demand> getDemands() {
        return demands;
    }

    public LinkedList<Demand> showDemands() throws NoSuchElementException {
        // show demands that was not added
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
                        result.add(d);
                    }
                }
            }
            return result;
        }
        throw new NoSuchElementException("no demand found yet");

    }

    public String getItemName(int itemID)throws NoSuchElementException {
        if (items.containsKey(itemID))
            return items.get(itemID).getName();
        else throw new NoSuchElementException();
    }

    public String getSiteName(int site) throws NoSuchElementException{
        if (sites.containsKey(site))
            return sites.get(site).getName();
        else throw new NoSuchElementException();
    }

    /**
     *
     * @return
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

    public int getWeightOfCurrReport() {
        int weight =0;
        for (DeliveryForm df:currDF)
        {
            for (Map.Entry<Integer,Integer> entry:df.getItems().entrySet())
            {
                weight=weight+items.get(entry.getKey()).getWeight()*entry.getValue();
            }
        }
        return weight;
    }
}
