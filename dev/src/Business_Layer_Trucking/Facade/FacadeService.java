package Business_Layer_Trucking.Facade;

import Business_Layer_Trucking.Facade.FacadeObject.*;
import Business_Layer_Trucking.Resources.Driver;
import Business_Layer_Trucking.Resources.Truck;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class FacadeService {
    private DeliveryService deliveryService;
    private ResourcesService resourcesService;


    public void createTruckingReport(){
        deliveryService.createTruckingReport();

    }


    public void chooseTruck(int truck){

        // TODO need to check if stands the demands weight
        // TODO need to update the trucks
        resourcesService.chooseTruck(truck);
        throw new UnsupportedOperationException();
    }
    public void replaceTruck(int truck)
    {
        resourcesService.replaceTruck(truck);

    }

    public FacadeDemand addDemandToReport(int demand_number, int supplyAmount){
        // TODO need to extract demand from int- number by the order

        return deliveryService.addDemandToReport(demand_number,supplyAmount);
    }

    public void createDemand(int id, int site, int amount){

        deliveryService.addDemand(id, site, amount);
        throw new UnsupportedOperationException();
    }

    public void chooseDriver(int driver) {
        // TODO need to check if stands the demands weight
        // TODO need to update the trucks
        resourcesService.chooseDriver(driver);
    }

    public void chooseLeavingHour(LocalDateTime leavingHour) {
        deliveryService.chooseLeavingHour(leavingHour);
    }

    public FacadeTruckingReport getCurrTruckReport() {
        return deliveryService.getCurrTruckingReport();
    }

    public void saveReport() {
        deliveryService.saveReport();
    }

    public void continueAddDemandToReport(int first, int second) {
        deliveryService.continueAddDemandToReport(first,second);
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

    public void removeItemFromReport(int item) {
        deliveryService.removeItemFromReport(item);
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


    public HashMap<Integer, Truck> getAvailableTrucks() {
        return resourcesService.getAvailableTrucks();
    }

    public LinkedList<FacadeDemand> getItemsOnTruck() {
        return deliveryService.getItemsOnTruck();
    }

    public void addTruck(String model, int licenseNumber, int weightNeto, int maxWeight) {
        resourcesService.addTruck( model, licenseNumber, weightNeto, maxWeight);
    }

    public void addDriver(int ID, String name, Driver.License licenseType) {
        resourcesService.addDriver(ID, name, licenseType);
    }

    public void addSite(String city, int siteID, int deliveryArea, String phoneNumber, String contactName) {
         deliveryService.addSite(city, siteID, deliveryArea, phoneNumber, contactName );

    }

    public void addItem(int id, int weight, String name) {
        deliveryService.addItem(id, weight,name);
    }

    public void displaySites() {
        deliveryService.displaySites();
    }
}
