package Presentation_Layer_Trucking;

import Business_Layer_Trucking.Facade.FacadeObject.*;
import Business_Layer_Trucking.Facade.FacadeService;
import Business_Layer_Trucking.Resources.Driver;
import com.sun.jdi.connect.Connector;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDateTime;
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

    public boolean addDemandToReport(int itemNumber, int amount, int siteID) throws IllegalStateException {
        if (itemNumber == -1) return false;
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
        return facadeService.getDeliveryForms(dfNumber,trNumber);
    }



    public void makeUnavailable_Driver(int driver)
    {

        facadeService.makeUnavailable_Driver(driver);
    }
    public boolean makeAvailable_Driver(int driver)
    {

        facadeService.makeAvailable_Driver(driver);
        return true;
    }
    public boolean makeUnavailable_Truck(int truck)
    {

        facadeService.makeUnavailable_Truck(truck);
        return true;
    }
    public boolean makeAvailable_Truck(int truck)
    {
        facadeService.makeAvailable_Truck(truck);
        return true;
    }

    public void addTruck(String model, int licenseNumber , int weightNeto, int maxWeight) throws KeyAlreadyExistsException {

        facadeService.addTruck( model, licenseNumber, weightNeto, maxWeight);

    }

    public void addDriver(int id, String name , Driver.License license) throws  KeyAlreadyExistsException{

        facadeService.addDriver(id, name, license);
    }


    public void addSite(String city, int siteID, int deliveryArea , String phoneNumber, String contactName,String name) throws  KeyAlreadyExistsException{
        facadeService.addSite(city, siteID, deliveryArea, phoneNumber, contactName,name );
    }

    public void addItem(int id, int weight, String name) throws KeyAlreadyExistsException {
        facadeService.addItem(id, weight,name);
    }

    // TODO need to add to menu after check
    public void RemoveItemFromPool(int item)
    {
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

    public FacadeTruck chooseTruck(int truckID) {
        return facadeService.chooseTruck(truckID);
    }

    public int getWeight(int itemID) {
        return facadeService.getItemWeight(itemID);
    }

    public LinkedList<FacadeDriver> getAvailableDrivers() {
        return facadeService.getAvailableDrivers();
    }

    public FacadeDriver chooseDriver(int driverID) throws IllegalStateException {
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

    /**
     * this method removes a destination from the in-build Trucking report
     * @param siteID
     * @return @returns true if succeed, throws exception otherwise
     */
    public boolean removeDestination(int siteID) { //returns true in succeed
        facadeService.removeDestination(siteID);
        return true;
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


    // TODO
//       need to check the exception go upwards always.
//       need to check all exception handle
    //  need to check truck max weight less then truck's Neto
    //  need to implement the initial state better





}
