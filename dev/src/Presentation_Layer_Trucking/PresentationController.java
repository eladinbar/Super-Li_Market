package Presentation_Layer_Trucking;

import Business_Layer_Trucking.Facade.FacadeObject.*;
import Business_Layer_Trucking.Facade.FacadeService;
import Business_Layer_Trucking.Resources.Driver;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalTime;
import java.util.*;

public class PresentationController {
    private FacadeService facadeService; // TODO need to check the actual way we hold it -  printer, menu and so on
    private static PresentationController instance =null;

    private PresentationController(){
        this.facadeService =  FacadeService.getInstance();
    }
    public static PresentationController getInstance() {
        if (instance == null)
            instance = new PresentationController();
        return instance;
    }


    public boolean addDemandToReport(int itemNumber, int amount, int siteID) throws IllegalStateException, IllegalArgumentException {
        if (itemNumber == 0) return false;
        facadeService.addDemandToReport(itemNumber , amount, siteID);
        return  true;


    }
    public boolean continueAddDemandToReport(int itemNumber, int amount, int siteID) throws IllegalArgumentException {
        facadeService.continueAddDemandToReport(itemNumber,amount,siteID);
        return true;
    }
    public void CreateReport()
    {
        facadeService.createTruckingReport();
    }


    public FacadeDeliveryForm getDeliveryForm(int trNumber, int dfNumber) throws IllegalArgumentException,NoSuchElementException{
        FacadeTruckingReport tr = facadeService.getTruckReport(trNumber);
        return facadeService.getDeliveryForm(dfNumber,trNumber);
    }



    public void makeUnavailable_Driver(String driver) throws NoSuchElementException
    {
        facadeService.makeUnavailable_Driver(driver);
    }
    public boolean makeAvailable_Driver(String driver)
    {
        facadeService.makeAvailable_Driver(driver);
        return true;
    }
    public boolean makeUnavailable_Truck(String truck)
    {

        facadeService.makeUnavailable_Truck(truck);
        return true;
    }
    public boolean makeAvailable_Truck(String truck)
    {
        facadeService.makeAvailable_Truck(truck);
        return true;
    }

    public void addTruck(String model, String licenseNumber , int weightNeto, int maxWeight) throws KeyAlreadyExistsException {

        facadeService.addTruck( model, licenseNumber, weightNeto, maxWeight);

    }

    public void addDriver(String id, String name , Driver.License license) throws  KeyAlreadyExistsException{

        facadeService.addDriver(id, name, license);
    }


    public void addSite(String city,  int deliveryArea , String phoneNumber, String contactName,String name) throws  KeyAlreadyExistsException{
        facadeService.addSite(city,  deliveryArea, phoneNumber, contactName,name );
    }

    public void addItem(int id, double weight, String name) throws KeyAlreadyExistsException {
        facadeService.addItem(id, weight,name);
    }


    public void RemoveItemFromPool(int item) throws NoSuchElementException
    {
        // TODO need to check -  for some reason - it doesnot removed from the current demands if exist, maybe from DF as well.
        //  also- when i want to create another demand, doesnt remove the right item
        facadeService.removeItemFromPool(item);
    }

    public LinkedList<FacadeDemand> showDemands() throws NoSuchElementException {
        return facadeService.showDemands();
    }

    public String getItemName(int itemID) {
        return facadeService.getItemName(itemID);
    }

    public String getSiteName(int site) {
        return facadeService.getSiteName(site);
    }



    public LinkedList<FacadeTruck> getAvailableTrucks() {
        return facadeService.getAvailableTrucks();

    }

    public FacadeTruck chooseTruck(String truck) throws NoSuchElementException, IllegalStateException{
        return facadeService.chooseTruck(truck);
    }

    public double getWeight(int itemID) {
        return facadeService.getItemWeight(itemID);
    }

    public LinkedList<FacadeDriver> getAvailableDrivers() {
        return facadeService.getAvailableDrivers();
    }

    public FacadeDriver chooseDriver(String driverID) throws IllegalStateException,NoSuchElementException {
        return facadeService.chooseDriver(driverID);
    }

    public void chooseLeavingHour(LocalTime time) throws IllegalArgumentException {
        facadeService.chooseLeavingHour(time);
    }

    public void saveReport() {
        facadeService.saveReport();
    }

    public LinkedList<FacadeDriver> getDrivers() {
        return facadeService.getDrivers();
    }

    public LinkedList<FacadeTruck> getTrucks() {
        return  facadeService.getTrucks();
    }

    public LinkedList<FacadeDemand> sortDemandsBySite(LinkedList<FacadeDemand> demands) {
        return facadeService.sortDemandsBySite(demands);
    }

    public LinkedList<FacadeSite> showCurrentSites() {
        return facadeService.getCurrentSites();
    }


    public LinkedList<FacadeDemand> getCurrentDemandsBySite(FacadeSite site) {
        LinkedList<FacadeDemand> demands = facadeService.getCurrentDemandsBySite(site);
        LinkedList<FacadeDemand> output = new LinkedList<>();
        for (FacadeDemand demand :  demands){
            if (demand.getSite() == site.getSiteID()) output.add(demand);
        }
        return output;
    }
    public int getWeightOfCurrReport(){
        return facadeService.getWeightOfCurrReport();
    }

    /**
     * this method removes a destination from the in-build Trucking report
     * @param siteID
     * @return @returns true if succeed, throws exception otherwise
     * @throws  NoSuchElementException
     */
    public boolean removeDestination(int siteID) throws NoSuchElementException { //returns true in succeed
        facadeService.removeDestination(siteID);
        return false;
    }

    public LinkedList<FacadeDemand> getItemsOnTruck() {
        return facadeService.getItemsOnTruck();

    }

    public void removeItemFromReport(FacadeDemand demand,  int amount ) {
        facadeService.removeItemFromReport(demand, amount);

    }

    public FacadeTruckingReport getCurrTruckReport() {
        return facadeService.getCurrTruckReport();
    }

    public LinkedList<FacadeItem> getAllItems() {
        return facadeService.getAllItems();
    }

    public LinkedList<FacadeSite> getAllSites() throws NoSuchElementException{
        return facadeService.getAllSites();
    }

    public void addDemandToSystem(int itemId, int site, int amount) {
        facadeService.addDemandToSystem(itemId,site,amount);
    }

    public LinkedList<FacadeTruckingReport> getActiveTruckingReports() {
        return facadeService.getActiveTruckingReports();
    }

    public LinkedList<FacadeDeliveryForm> getDeliveryForms(int trID) {
        return facadeService.getDeliveryForms(trID);
    }

    public void updateDeliveryFormRealWeight(int trID, int dfID, int weight) throws IllegalStateException{
        facadeService.updateDeliveryFormRealWeight(trID,dfID,weight);
    }

    public void removeSiteFromTruckReport(int siteID, int trID) {
        //TODO- implement
    }

    public boolean addDemandToTruckReport(int itemNumber, int amount,int siteID, int trID) {
        //TODO- implement
        return false;
    }

    public void replaceTruck(int id, String truckNumber, int weight)throws IllegalStateException,IllegalArgumentException {
        // TODO need to check the current driver fits the new Trucks weight
        facadeService.replaceTruck(id,truckNumber,weight);
    }

    public void replaceDriver(int trID, int driverID) {
        // TODO need to implement
    }

    public LinkedList<FacadeDemand> getItemOnReport(int trID) {
        // TODO need to implement
        return null;
    }

    public void removeItemFromTruckingReport(int trID, FacadeDemand demand) {
        // TODO need to implement
    }

    public int getSiteDeliveryArea(int site) {
        return facadeService.getSiteDeliveryArea( site);
    }

    public boolean continueAddDemandToTruckReport(int itemNumber, int amount, int siteID, int truckId) {
    }


    // TODO
    //   need to check the exception go upwards always.
    //   need to check all exception handle
    //   need to check truck max weight less then truck's Neto
    //   need to implement the initial state better





}
