package Business_Layer_Trucking.Facade;


import Business_Layer_Trucking.Delivery.DeliveryForm;
import Business_Layer_Trucking.Facade.FacadeObject.*;
import Business_Layer_Trucking.Resources.Driver;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class FacadeService {
    private DeliveryService deliveryService;
    private ResourcesService resourcesService;
    private FacadeTruckingReport currTR;
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


    public FacadeTruck chooseTruck(String truck) throws NoSuchElementException, IllegalStateException {
        int weightToDeliver=deliveryService.getWeightOfCurrReport();
        FacadeTruck ft =  new FacadeTruck(resourcesService.chooseTruck(truck));
        if (weightToDeliver+ ft.getWeightNeto()>ft.getMaxWeight())
        {
            throw new IllegalStateException("Total Weight is: "+weightToDeliver+ft.getWeightNeto()+"But Truck can carry: "+ft.getMaxWeight());
        }
        currTR.setTruckNumber(ft.getLicenseNumber());
        return ft;
    }


    public FacadeDemand addDemandToReport(int item_number, int supplyAmount, int siteID) throws IllegalStateException, IllegalArgumentException{
        return deliveryService.addDemandToReport(item_number,supplyAmount , siteID);
    }



    public FacadeDriver chooseDriver(String driver) throws IllegalStateException,NoSuchElementException{
        FacadeDriver fd  = resourcesService.chooseDriver(driver);
        FacadeTruck ft  = null;
        for (FacadeTruck ft2 : resourcesService.getTrucks()){
            if (ft2.getLicenseNumber().equals(currTR.getTruckNumber()))
                ft = ft2;
        }
        if (ft==null) throw new NoSuchElementException("the truck's license Number does not exist");
        if (fd.getLicenseType().getSize()< ft.getWeightNeto()+deliveryService.getWeightOfCurrReport())
            throw new IllegalStateException("Driver cant handle this delivery");

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

    public FacadeDeliveryForm getDeliveryForm(int dfNumber, int trNumber)throws IllegalArgumentException,NoSuchElementException {
        return deliveryService.getDeliveryForm(dfNumber,trNumber);
    }

    public void removeDestination(int site) {
        deliveryService.removeDestination(site);
    }

    public void removeItemFromReport(FacadeDemand demand, int amount) {
        deliveryService.removeItemFromReport(demand,amount);
    }
    public void removeItemFromPool(int item)throws NoSuchElementException{
        deliveryService.removeItemFromPool(item);
    }
    public int getWeightOfCurrReport(){
        return deliveryService.getWeightOfCurrReport();
    }

    public void makeUnavailable_Driver(String driver)throws NoSuchElementException {
        resourcesService.makeUnavailable_Driver(driver);
    }

    public void makeAvailable_Driver(String driver) {
        resourcesService.makeAvailable_Driver(driver);
    }

    public void makeUnavailable_Truck(String truck) {
        resourcesService.makeUnavailable_Truck(truck);
    }

    public void makeAvailable_Truck(String truck) {
        resourcesService.makeAvailable_Truck(truck);
    }


    public LinkedList<FacadeTruck> getAvailableTrucks() {
        return resourcesService.getAvailableTrucks();
    }

    public LinkedList<FacadeDemand> getItemsOnTruck() {
        return deliveryService.getItemsOnTruck();
    }

    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight)  throws KeyAlreadyExistsException {
        resourcesService.addTruck( model, licenseNumber, weightNeto, maxWeight);
    }

    public void addDriver(String ID, String name, Driver.License licenseType) {
        resourcesService.addDriver(ID, name, licenseType);
    }

    public void addSite(String city, int siteID, int deliveryArea, String phoneNumber, String contactName,String name) {
        deliveryService.addSite(city, siteID, deliveryArea, phoneNumber, contactName,name );

    }

    public void addItem(int id, int weight, String name)throws KeyAlreadyExistsException {
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

    public LinkedList<FacadeTruckingReport> getActiveTruckingReports() {
        return deliveryService.getActiveTruckingReports();
    }

    public LinkedList<FacadeDeliveryForm> getDeliveryForms(int trID){
        return deliveryService.getDeliveryForms(trID);
    }

    public void updateDeliveryFormRealWeight(int trID, int dfID, int weight) throws IllegalStateException{
        // TODO if fails, update old TR to be old. creates new TR with compatible replaced
        // TODO need to update all the DF  to the new TR
        LinkedList<FacadeTruck>trucks=resourcesService.getTrucks();
        FacadeTruck ft=null;
        for (FacadeTruck facadeTruck:trucks)
        {
            if (facadeTruck.getLicenseNumber().equals(currTR.getTruckNumber()));
                ft=facadeTruck;
        }

        if (weight> ft.getMaxWeight())
            throw new IllegalStateException("Overweight related to Delivery Form Number:"+dfID+"  In TR number: "+trID);
        else deliveryService.updateDeliveryFormRealWeight(dfID,weight);
         if (deliveryService.checkIfAllCompleted(trID)){
             deliveryService.archive(trID);
         }
    }


    public void replaceTruck(int trid, String truckNumber) {
        FacadeTruckingReport ftr=getTruckReport(trid);
        String old_truck=ftr.getTruckNumber();
        resourcesService.replaceTruck(old_truck,truckNumber);
    }
}


