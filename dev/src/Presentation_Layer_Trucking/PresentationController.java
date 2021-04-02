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
        this.facadeService =  new FacadeService();
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
        facadeService.addDemandToReport(itemNumber , amount, siteID);
        return true;
    }
    public void CreateReport()
    {
        facadeService.createTruckingReport();
    }

    // TODO no usage atm - need to check why

    public FacadeDeliveryForm getDeliveryForm(int trNumber, int dfNumber){
        FacadeTruckingReport tr = facadeService.getTruckReport(trNumber);
        return facadeService.getDeliveryForms(dfNumber);
    }


// TODO need to check if really needed
    public void rePlanning()
    {
        /*int option=-1;
        while (option != -1)
        switch (option){
            case 1://remove site
            { // TODO all sout need to be deleted and remove to GUI
                facadeService.displaySites();//TODO - watch - added method ! take a look all the way down ( there is a sout)
                int site=-1;
                facadeService.removeDestination(site);
            }
            case 2://switch demand(=site)
            {
                int site =-1;
                facadeService.removeDestination(site);
                System.out.println("choose demand to add");
                int demand=-1;
                System.out.println("choose amount");
                int amount=-1;
                FacadeDemand new_FD = facadeService.addDemandToReport(demand,amount);
                while (new_FD != null){
                    //TODO system in need to be added here
                    new_FD = facadeService.addDemandToReport(demand,amount);
                }

            }
            case 3://replace truck
            {
                System.out.println("choose new truck");
                HashMap  trucks =facadeService.getAvailableTrucks();

                int truck=-1;
                facadeService.replaceTruck(truck);
            }
            case 4://remove items
            {
                System.out.println("choose item to remove");

                LinkedList<FacadeDemand> items = facadeService.getItemsOnTruck();
                int item=-1;

                facadeService.removeItemFromReport(item);
            }
        }
        return facadeService.getCurrTruckReport();*/

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

    // TODO
    public void addSite(String city, int siteID, int deliveryArea , String phoneNumber, String contactName) throws  KeyAlreadyExistsException{
        facadeService.addSite(city, siteID, deliveryArea, phoneNumber, contactName );
    }

    public void addItem(int id, int weight, String name) throws KeyAlreadyExistsException {
        facadeService.addItem(id, weight,name);
    }
    public void RemoveItemFromPool()
    {
        //TODO- implement if needed.
        int item=-1;
        facadeService.removeItemFromPool(item);
    }

    public List<FacadeDemand> showDemands() {
        return facadeService.showDemands();
    }

    public String getItemName(int itemID) {
        return facadeService.getItemName(itemID);
    }

    public int getSiteName(int site) {
        return 0;
    }

    public void closeReport() {
    }


    public LinkedList<FacadeTruck> getAvailableTrucks() {
        return null;
    }

    public FacadeTruck chooseTruck(int truckID) {
        return facadeService.chooseTruck(truckID);
    }

    public int getWeight(int itemID) {
        return facadeService.getItemWeight(itemID);
    }

    public LinkedList<FacadeDriver> getAvailableDrivers() {
        return null;
    }

    public FacadeDriver chooseDriver(int driverID) {
        return null;
    }

    public void chooseLeavingHour(LocalTime time) {
    }

    public void saveReport() {
        facadeService.saveReport();
    }

    public LinkedList<FacadeDriver> getDrivers() {
        return null;
    }

    public LinkedList<FacadeTruck> getTrucks() {
        return null;
    }

    public List<FacadeDemand> sortDemandsBySite(List<FacadeDemand> demands) {
        return demands;
    }

    public LinkedList<FacadeSite> showCurrentSites() {
        return null;
    }

    public LinkedList<FacadeDemand> getCurrentDemandsBySite(FacadeSite site) {
    }

    public boolean removeDestination(int siteID) { //returns true in succeed 
    }

    public LinkedList<FacadeDemand> getItemsOnTruck() {
        return facadeService.getItemsOnTruck();
        // TODO sort by site?
    }

    public void removeItemFromReport(int itemId) {
    }

    public FacadeTruckingReport getCurrTruckReport() {
    }
}
