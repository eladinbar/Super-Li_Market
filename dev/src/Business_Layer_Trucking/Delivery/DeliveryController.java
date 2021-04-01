package Business_Layer_Trucking.Delivery;

import Business_Layer_Trucking.Facade.FacadeObject.FacadeDeliveryForm;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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


    public DeliveryController()
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
        currTR=new TruckingReport();
    }



    public void createNewTruckingReport(){
        currDF =  new LinkedList<>();
        currTR =  new TruckingReport();
        currTR.setID(lastReportID+1);

    }


    public void updateCurrTR_Date(LocalDate date) throws Exception{//update on commit - throws!
        LocalDate now=LocalDate.now();
        if (date.compareTo(now)!=-1)
        {
            currTR.setDate(date);
        }
        else{
            throw new IllegalArgumentException("Cant enter date that passed");
        }

    }

    public void updateCurrTR_LeavingHour(LocalDateTime leavingHour) throws Exception{//update on commit - throws!
        LocalDateTime now=LocalDateTime.now();
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

    public void updateCurrTR_Origin(int origin) throws Exception{//update on commit - throws!
        try{
            sites.get(origin);
            currTR.setOrigin(origin);
        }
        catch (IllegalArgumentException exception){
            throw new IllegalArgumentException("SiteID does not exist");
        }
    }
    public void addCurrTR_Destination(int destination) throws Exception{//update on commit - throws!
        try{
            sites.get(destination);
            currTR.addDestination(destination);
        }
        catch (Exception exception){
            throw new Exception(exception.getMessage());
        }
    }
    public void updateCurrTR_TrReplaced(int tr_id)throws Exception{//update on commit - throws!
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

    public void addItemToDeliveryForm(Demand demand, int amount,boolean ignore) throws Exception{//update on commit - throws!
        boolean ignoreDifferentDeliveryAreas=ignore;
        if (currDF.isEmpty())//if list is empty
        {
            //TODO check how we know what is the origin
            HashMap<Item,Integer> itemsOnDF=new HashMap<>();
            itemsOnDF.put(items.get(demand.getItemID()),amount);
            DeliveryForm newDF=new DeliveryForm(1,-1,demand.getSite(),itemsOnDF,
                    items.get(demand.getItemID()).getWeight()*amount,currTR.getID());
        }
        else // we need to look in the list maybe there is a form with the same origin& destination
        {
            for (DeliveryForm df:currDF)
            {
                //TODO check how we know what is the origin
                if (df.getOrigin()==-1&&df.getDestination()==demand.getSite())
                {
                    HashMap<Item,Integer> itemsOnDF=new HashMap<>();
                    itemsOnDF.put(items.get(demand.getItemID()),amount);
                    df.getItems().put(items.get(demand.getItemID()),amount);
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

    public LinkedList<Demand> getDemands() {
        return demands;
    }


    public boolean addSite(String city,int  siteID,int  deliveryArea,String phoneNumber,String contactName) throws Exception
    {
        if (sites.containsKey(siteID))
        {
            throw new KeyAlreadyExistsException("Duplicate ID");
        }
        else {
            Site newSite = new Site(siteID, city, deliveryArea, phoneNumber, contactName);
            sites.put(siteID,newSite);
            return true;
        }

    }
    public boolean removeSite(int siteID) throws Exception
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


    public void chooseLeavingHour(LocalDateTime leavingHour)throws Exception {
        LocalDateTime now=LocalDateTime.now();
        if (leavingHour.compareTo(now)!=-1)
        {
            currTR.setLeavingHour(leavingHour);
        }
        else{
            throw new IllegalArgumentException("Cant enter time that passed");
        }
    }

    public void saveReport(){
        //TODO implement - what is the meaning?which report?
        //  save to database?to old trucking reports?
        throw new UnsupportedOperationException();
    }

    public TruckingReport getTruckReport(int trNumber) throws Exception{
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

    public FacadeDeliveryForm getDeliveryForm(int dfNumber) {
        //TODO implement
        throw new UnsupportedOperationException();
    }

    public void removeDestination(int site) throws Exception{
        //TODO implement - need only to show current options
        if (!currTR.getDestinations().contains(site))
        {
            throw new NoSuchElementException();
        }
        else currTR.getDestinations().remove(site);
    }

    public void removeItemFromReport(int item) {
        //TODO: implement
        throw new UnsupportedOperationException();
    }

    public void removeItemFromPool(int item)throws Exception {
      if (!items.containsKey(item))
      {
          throw new NoSuchElementException();
      }
      else items.remove(item);
    }

    public void addItem(int id, int weight, String name)throws Exception {
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
            System.out.println(i+" - "+sites.get(i).getCity()+"/n");//TODO - think about adding field name to site
        }
    }
}
