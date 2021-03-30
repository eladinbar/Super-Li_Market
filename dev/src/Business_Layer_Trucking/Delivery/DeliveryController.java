package Business_Layer_Trucking.Delivery;

import Business_Layer_Trucking.Facade.FacadeObject.FacadeDeliveryForm;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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


    public DeliveryController(HashMap<Integer,Site> sites)
    {
        // TODO need to complete here.
        demands=new LinkedList<>();
        activeTruckingReports=new HashMap<>();
        oldTruckingReports=new HashMap<>();
        this.sites=sites;
        deliveryForms=new HashMap<>();
        // when data base is added, need to be setted by it;
        lastDeliveryForms = 0;
        lastReportID =0;
    }



    public void createNewTruckingReport(){
        currDF =  new LinkedList<>();
        // TODO need to get it id by it own
        currTR =  new TruckingReport();

    }













    public void updateCurrTR_Date(Date date){
        throw new UnsupportedOperationException();
    }

    public void updateCurrTR_LeavingHour(Date leavingHour){
        throw new UnsupportedOperationException();
    }
    public void updateCurrTR_TruckNumber(int number){
        throw new UnsupportedOperationException();
    }
    public void updateCurrTR_DriverID(int id){
        throw new UnsupportedOperationException();
    }

    public void updateCurrTR_Origin(int origin){
        throw new UnsupportedOperationException();
    }
    public void addCurrTR_Destination(int destination){
        throw new UnsupportedOperationException();
    }
    public void updateCurrTR_TrReplaced(int tr_id){
        throw new UnsupportedOperationException();
    }

    public void addItemToDeliveryForm(Demand demand, int amount){
        // TODO need to add to the current forms, if not exist, create new one
        //TODO update leaving weight in the form
        // TODO need to throw exception if include 2 different areas
        throw new UnsupportedOperationException();
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

    //TODO add setters/adding methods to Maps/Lists

    public boolean addSite(Site site)
    {
        //TODO implement
        throw new UnsupportedOperationException();
    }
    public boolean removeSite(int siteID)
    {
        //TODO implement
        throw new UnsupportedOperationException();
    }


    public void chooseTruck(int truck) {
       
        throw new UnsupportedOperationException();
    }

    public void chooseDriver(int driver) {
        throw new UnsupportedOperationException();
    }

    public void chooseLeavingHour(Date leavingHour) {
    }

    public void saveReport() {
    }

    public void continueAddDemandToReport(int first, int second) {
        // TODO this method do not throw exception for unMatched areas
    }

    public TruckingReport getTruckReport(int trNumber) {
        return null;
    }

    public FacadeDeliveryForm getDeliveryForm(int dfNumber) {
        return null;
    }

    public void removeDestination(int site) {
        //TODO implement
        throw new UnsupportedOperationException();
    }

    public void removeItem(int item) {
    }
}
