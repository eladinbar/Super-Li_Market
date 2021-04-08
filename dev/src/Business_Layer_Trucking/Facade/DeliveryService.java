package Business_Layer_Trucking.Facade;

import Business_Layer_Trucking.Delivery.*;
import Business_Layer_Trucking.Facade.FacadeObject.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class DeliveryService {
    private DeliveryController dc;
    private static DeliveryService instace = null;


    private DeliveryService(){

        this.dc = DeliveryController.getInstance();
    }

    public static DeliveryService getInstance() {
        if (instace == null)
            instace =  new DeliveryService();
        return instace;
    }



    /*    public void addDemand(int id, int site, int amount) {
        Demand demand =  new Demand(id, site, amount);
        dc.addItemToDeliveryForm(demand,);
    }*/

    public FacadeDemand addDemandToReport(int itemID, int supplyAmount, int siteID)  throws IllegalStateException, IllegalArgumentException{
        LinkedList<Demand> demands = dc.getDemands();
        Demand d=null;
        for (Demand curr:  demands) {

            if (curr.getItemID() ==  itemID && curr.getAmount() >= supplyAmount && curr.getSite() == siteID){
                d = curr;
            }
        }
        if (d == null){
            throw new IllegalArgumentException("one of arguments doesn't match");
        }
        FacadeDemand fc = new FacadeDemand(d);
        dc.addItemToDeliveryForm(d, supplyAmount, false,siteID);
        return fc;


    }



    public int  createTruckingReport() {
        return dc.createNewTruckingReport();

    }

    public void chooseLeavingHour(LocalTime leavingHour) throws IllegalArgumentException {

        dc.chooseLeavingHour(leavingHour);


    }

    public FacadeTruckingReport getCurrTruckingReport() {
        return new FacadeTruckingReport(dc.getCurrTR());
    }

    public void saveReport() {
        dc.saveReport();
    }

    public FacadeDemand continueAddDemandToReport(int itemID, int supplyAmount, int siteID){
        LinkedList<Demand> demands = dc.getDemands();
        Demand d=null;
        for (Demand curr:  demands) {
            if (curr.getItemID() ==  itemID && curr.getAmount() < supplyAmount && curr.getSite() == siteID){
                d = curr;
            }
        }
        if (d == null){
            throw new IllegalArgumentException("one of arguments doesn't match");
        }
        FacadeDemand fc = new FacadeDemand(d);
        dc.addItemToDeliveryForm(d, supplyAmount, true,siteID);
        return fc;
    }

    public TruckingReport getTruckReport(int trNumber) throws NoSuchElementException{
        return dc.getTruckReport(trNumber);
    }

    public FacadeDeliveryForm getDeliveryForm(int dfNumber, int trNumber)throws IllegalArgumentException,NoSuchElementException {
        return new FacadeDeliveryForm(dc.getDeliveryForm(dfNumber,trNumber));
    }

    public void removeDestination(int site) throws NoSuchElementException {
        dc.removeDestination(site);

    }

    public void removeItemFromReport(FacadeDemand demand, int amount) {

        dc.removeItemFromReport(new Demand(demand.getItemID(),demand.getSite(),amount));
    }
    public void removeItemFromPool(int item) throws NoSuchElementException{
        dc.removeItemFromPool(item);
    }

    /**
     *
     * @return a LinkedList of Facade Demands, that holds all the items in the current Trucking Report in build
     */
    public LinkedList<FacadeDemand> getItemsOnTruck() {
        LinkedList<Demand> demands = dc.getItemsOnTruck();
        LinkedList<FacadeDemand> output = new LinkedList<>();
        for (Demand d : demands) {
            output.add(new FacadeDemand(d));
        }
        return output;
    }

    public void addSite(String city,  int deliveryArea,
                        String phoneNumber, String contactName,String name) throws KeyAlreadyExistsException {

        dc.addSite(city,  deliveryArea, phoneNumber, contactName,name );
    }

    public void addItem(int id, double weight, String name, int siteID) throws NoSuchElementException, KeyAlreadyExistsException {dc.addItem(id, weight,name,siteID);}

    public void displaySites() {
        dc.displaySites();
    }

    public double getItemWeight(int itemID) {
        return dc.getItemWeight(itemID);
    }

    public LinkedList<FacadeDemand> showDemands() throws NoSuchElementException {
        LinkedList < FacadeDemand> output = new LinkedList<>();
        LinkedList<Demand> demands=  dc.showDemands();
        for ( Demand d : demands) output.add(new FacadeDemand(d));
        return output;
    }

    public String getItemName(int itemID) {return  dc.getItemName(itemID);}

    public String getSiteName(int site) { return dc.getSiteName(site);    }



    public LinkedList<FacadeSite> getSites() {
        HashMap<Integer, Site> sites = dc.getSites();
        LinkedList<FacadeSite> output = new LinkedList<>();
        for (HashMap.Entry<Integer, Site> entry : sites.entrySet()){
            output.add(new FacadeSite(entry.getValue()));
        }
        return output;
    }

    public LinkedList<FacadeSite> getCurrentSites() {
        LinkedList<Site>  sites = dc.getCurrentSites();
        LinkedList<FacadeSite> output =  new LinkedList<>();
        for (Site site: sites){
            output.add(new FacadeSite(site));
        }

        return output;
    }

    public LinkedList<FacadeDemand> getCurrentDemands() {
        LinkedList<Demand> demands =  dc.getDemands();
        LinkedList<FacadeDemand> output =  new LinkedList<>();
        for (Demand d : demands){
            output.add(new FacadeDemand(d));
        }
        return output;
    }

    /**
     *
     * @return @list of all items in the trucking system
     */
    public LinkedList<FacadeItem> getAllItems() {
        LinkedList<Item> items = dc.getItems();

        LinkedList<FacadeItem> facadeItems = new LinkedList<>();
        for (Item item: items){
            facadeItems.add(new FacadeItem(item));
        }
        return facadeItems;
    }

    public LinkedList<FacadeSite> getAllSites() throws NoSuchElementException{
        LinkedList<Site> sites=dc.getAllSites();
        LinkedList<FacadeSite> facadeSites=new LinkedList<>();
        for (Site s:sites)
        {
            facadeSites.add(new FacadeSite(s));
        }
        return facadeSites;
    }

    public void addDemandToSystem(int itemId, int site, int amount)throws NoSuchElementException {
        dc.addDemandToSystem(itemId,site,amount);
    }

    public int getWeightOfCurrReport() {
        return dc.getWeightOfCurrReport();
    }

    public LinkedList<FacadeTruckingReport> getActiveTruckingReports() {
        LinkedList<FacadeTruckingReport> result=new LinkedList<>();
        LinkedList<TruckingReport> reports=dc.getActiveTruckingReports();
        for (TruckingReport tr: reports)
        {
            result.add(new FacadeTruckingReport(tr));
        }
        return result;


    }

    public LinkedList<FacadeDeliveryForm> getDeliveryForms(int trID) {
        LinkedList<FacadeDeliveryForm> result=new LinkedList<>();
        LinkedList<DeliveryForm> deliveryForms=dc.getDeliveryForms(trID);
        for (DeliveryForm df:deliveryForms)
        {
            result.add(new FacadeDeliveryForm(df));
        }
        return result;

    }

    public void updateDeliveryFormRealWeight(int trID,int dfID, int weight)throws IllegalStateException {
        dc.updateDeliveryFormRealWeight(trID,dfID,weight);
    }

    public boolean checkIfAllCompleted(int trID) {
        return dc.checkIfAllCompleted(trID);
    }

    public void archive(int trID) {
        dc.archive(trID);
    }

    public void archiveNotCompleted(int trID){
        dc.archiveNotCompleted(trID);
    }

    public int getSiteDeliveryArea(int site) {
        return dc.getSiteDeliveryArea(site);
    }

    public void removeSiteFromTruckReport(int siteID, int trID) throws NoSuchElementException{
        dc.removeSiteFromTruckReport(siteID,trID);
    }

    public boolean addDemandToTruckReport(int itemNumber, int amount, int siteID, int trID) throws IllegalStateException{
        return dc.addDemandToTruckReport(itemNumber, amount,siteID,trID);
    }

    public void replaceDriver(int trID, String driverID) {
        dc.replaceDriver(trID,driverID);
    }

    public LinkedList<FacadeDemand> getItemOnReport(int trID) {
        LinkedList<Demand> demands=dc.getItemOnReport(trID);
        LinkedList<FacadeDemand> result=new LinkedList<>();
        for (Demand d:demands)
        {
            result.add(new FacadeDemand(d));
        }
        return result;
    }

    public void removeItemFromTruckingReport(int trID, FacadeDemand demand){
        dc.removeItemFromTruckingReport(trID,demand.getItemID(),demand.getSite());
    }

    public boolean continueAddDemandToTruckReport(int itemNumber, int amount, int siteID, int truckId) {
        return dc.continueAddDemandToTruckReport(itemNumber,amount,siteID,truckId);
    }


    public void chooseDateToCurrentTR(LocalDate chosen) {
        dc.chooseDateToCurrentTR(chosen);
    }

    public void removeSiteFromPool(int siteID) {
        dc.removeSite(siteID);
    }

    public void chooseDriver(String driver) {
        dc.updateCurrTR_DriverID(driver);
    }

    public void chooseTruck(String truck) {
        dc.updateCurrTR_TruckNumber(truck);
    }
}


