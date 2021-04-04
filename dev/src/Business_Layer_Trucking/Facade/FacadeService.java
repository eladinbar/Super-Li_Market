package Business_Layer_Trucking.Facade;

import Business_Layer_Trucking.Delivery.Demand;
import Business_Layer_Trucking.Facade.FacadeObject.*;
import Business_Layer_Trucking.Resources.Driver;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class FacadeService {
    private DeliveryService deliveryService;
    private ResourcesService resourcesService;
    private FacadeTruckingReport currTR;
    // TODO insert current trucking report;
    //  notiftys when report saved
    private static FacadeService instance =null;


    private FacadeService(){
        deliveryService =  DeliveryService.getInstance();
        resourcesService =   ResourcesService.getInstance();
    }

    public static FacadeService getInstance() {
        if (instance == null)
            instance = new FacadeService();
        return instance;
    }

    public void createTruckingReport(){
        int id = deliveryService.createTruckingReport();
        currTR =  new FacadeTruckingReport(id);

    }


    public FacadeTruck chooseTruck(int truck) throws NoSuchElementException, IllegalStateException {
        // TODO need to get weight and check
        FacadeTruck ft =  new FacadeTruck(resourcesService.chooseTruck(truck) );
        currTR.setTruckNumber(ft.getLicenseNumber());
        return ft;
    }


    public FacadeDemand addDemandToReport(int item_number, int supplyAmount, int siteID){
        return deliveryService.addDemandToReport(item_number,supplyAmount , siteID);
    }



    public FacadeDriver chooseDriver(int driver) {
        // TODO need to check if stands the demands weight
        FacadeDriver fd  = resourcesService.chooseDriver(driver);

        currTR.setDriverID(fd.getID());
        return fd;

    }

    public void chooseLeavingHour(LocalTime leavingHour) {
        deliveryService.chooseLeavingHour(leavingHour);
    }

    public FacadeTruckingReport getCurrTruckReport() {
        return deliveryService.getCurrTruckingReport();
    }

    public void saveReport() {

        deliveryService.saveReport();
        resourcesService.saveReport();

    }

    public void continueAddDemandToReport(int itemID, int amount, int siteID) {
        deliveryService.continueAddDemandToReport(itemID,amount,siteID);
    }

    public FacadeTruckingReport getTruckReport(int trNumber) {
        return new FacadeTruckingReport(deliveryService.getTruckReport(trNumber));
    }

    public FacadeDeliveryForm getDeliveryForms(int dfNumber) {
        return deliveryService.getDeliveryForm(dfNumber);
    }

    public void removeDestination(int site) {
        deliveryService.removeDestination(site);
    }

    public void removeItemFromReport(FacadeDemand demand, int amount) {
        deliveryService.removeItemFromReport(demand,amount);
    }
    public void removeItemFromPool(int item) {
        deliveryService.removeItemFromPool(item);
    }

    public void makeUnavailable_Driver(int driver) {
        resourcesService.makeUnavailable_Driver(driver);
    }

    public void makeAvailable_Driver(int driver) {
        resourcesService.makeAvailable_Driver(driver);
    }

    public void makeUnavailable_Truck(int truck) {
        resourcesService.makeUnavailable_Truck(truck);
    }

    public void makeAvailable_Truck(int truck) {
        resourcesService.makeAvailable_Truck(truck);
    }


    public LinkedList<FacadeTruck> getAvailableTrucks() {
        return resourcesService.getAvailableTrucks();
    }

    public LinkedList<FacadeDemand> getItemsOnTruck() {
        return deliveryService.getItemsOnTruck();
    }

    public void addTruck(String model, int licenseNumber, int weightNeto, int maxWeight)  throws KeyAlreadyExistsException {
        resourcesService.addTruck( model, licenseNumber, weightNeto, maxWeight);
    }

    public void addDriver(int ID, String name, Driver.License licenseType) {
        resourcesService.addDriver(ID, name, licenseType);
    }

    public void addSite(String city, int siteID, int deliveryArea, String phoneNumber, String contactName,String name) {
        deliveryService.addSite(city, siteID, deliveryArea, phoneNumber, contactName,name );

    }

    public void addItem(int id, int weight, String name) {
        deliveryService.addItem(id, weight,name);
    }

    public void displaySites() {
        deliveryService.displaySites();
    }

    public int getItemWeight(int itemID) {
        return deliveryService.getItemWeight(itemID);
    }

    public LinkedList<FacadeDemand> showDemands() throws NoSuchElementException {
        return deliveryService.showDemands();
    }

    public String getItemName(int itemID) {
        return deliveryService.getItemName(itemID);
    }

    public String getSiteName(int site) {
        return deliveryService.getSiteName(site);
    }


    public LinkedList<FacadeDriver> getAvailableDrivers() {
        return resourcesService.getAvailableDrivers();
    }

    public LinkedList<FacadeDriver> getDrivers() {
        return resourcesService.getDrivers();
    }

    public LinkedList<FacadeTruck> getTrucks() {
        return resourcesService.getTrucks();
    }

    // TODO need to unit test it
    public LinkedList<FacadeDemand> sortDemandsBySite(LinkedList<FacadeDemand> demands) {
        for (int i = demands.size() -1 ; i>= 0 ; i--){
            FacadeDemand min = demands.get(i);
            int index = i;
            for (int j= i; j>= 0; j--){
                if (demands.get(j).getSite() < min.getSite()) {
                    min = demands.get(j);
                    index = j;
                }
            }
            FacadeDemand temp = demands.get(i);
            demands.set(i, min);
            demands.set(index,temp);
        }
        return demands;


    }


    public LinkedList<FacadeSite> getCurrentSites() {
        return deliveryService.getCurrentSites();
    }

    public LinkedList<FacadeDemand> getCurrentDemandsBySite(FacadeSite site) {
        return  deliveryService.getCurrentDemands();

    }

    public LinkedList<FacadeItem> getAllItems() {
        return deliveryService.getAllItems();
    }

    public LinkedList<FacadeSite> getAllSites() throws NoSuchElementException {
        return deliveryService.getAllSites();
    }

    public void addDemandToSystem(int itemId, int site, int amount) {
        deliveryService.addDemandToSystem(itemId,site,amount);
    }
}
