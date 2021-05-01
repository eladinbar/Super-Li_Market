package Trucking.Presentation_Layer_Trucking;

import Trucking.Business_Layer_Trucking.Facade.FacadeObject.*;
import Trucking.Business_Layer_Trucking.Facade.FacadeService;
import Trucking.Business_Layer_Trucking.Resources.Driver;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.nio.file.ProviderMismatchException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PresentationController {
    private FacadeService facadeService;
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

    public void addItem( double weight, String name, int siteID) throws NoSuchElementException, KeyAlreadyExistsException {
        facadeService.addItem( weight,name, siteID);
    }


    public void RemoveItemFromPool(int item) throws NoSuchElementException
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
        return  facadeService.getCurrentDemandsBySite(site);

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

    public void removeSiteFromTruckReport(int siteID, int trID)throws NoSuchElementException {
        facadeService.removeSiteFromTruckReport(siteID,trID);
    }

    public boolean addDemandToTruckReport(int itemNumber, int amount,int siteID, int trID)throws IllegalStateException {
        if (itemNumber==0)
            return false;
        else return facadeService.addDemandToTruckReport(itemNumber,amount,siteID,trID);
    }

    public void replaceTruck(int id, String truckNumber, int weight)throws IllegalStateException,IllegalArgumentException,ProviderMismatchException {

        facadeService.replaceTruck(id,truckNumber,weight);
    }

    public void replaceDriver(int trID, String driverID, int weight)throws IllegalStateException,NoSuchElementException, ProviderMismatchException {
       facadeService.replaceDriver(trID,driverID,weight);
    }

    public LinkedList<FacadeDemand> getItemOnReport(int trID) {
        return facadeService.getItemOnReport(trID);
    }

    public void removeItemFromTruckingReport(int trID, FacadeDemand demand) {
        facadeService.removeItemFromTruckingReport(trID,demand);
    }

    public int getSiteDeliveryArea(int site) {
        return facadeService.getSiteDeliveryArea( site);
    }

    public boolean continueAddDemandToTruckReport(int itemNumber, int amount, int siteID, int truckId) {
        return facadeService.continueAddDemandToTruckReport(itemNumber,amount,siteID,truckId);
    }
    public void chooseDateToCurrentTR(LocalDate chosen) {
        facadeService.chooseDateToCurrentTR(chosen);
    }
    public void removeSiteFromPool(int siteID)throws NoSuchElementException, IllegalStateException{
        facadeService.removeSiteFromPool(siteID);

    }


    public void removeDemand(FacadeDemand d) {
        facadeService.removeDemand(d);
    }

    public LinkedList<FacadeDeliveryForm> getUnComplitedDeliveryForms(int trId) {
        return facadeService.getUnComplitedDeliveryForms(trId);
    }

    public FacadeTruckingReport getNewTruckReportID(FacadeTruckingReport oldTr) {

        return facadeService.getNewTruckReport(oldTr);
    }

    public void moveDemandsFromCurrentToReport(FacadeTruckingReport tr) {
        facadeService.moveDemandsFromCurrentToReport(tr);
    }

    public void replaceTruckAndDriver(String truckNumber, String driverID, FacadeTruckingReport tr, int weight) throws InputMismatchException {
        facadeService.replaceTruckAndDriver(truckNumber,driverID,tr,weight);
    }


    public LinkedList<FacadeDemand> getAllDemands() {
        return facadeService.getAllDemands();
    }

    public FacadeTruckingReport getCurrentTruckReport() {
        return facadeService.getCurrTruckReport();

    }

    public LinkedList<FacadeDeliveryForm> getUncompletedDeliveryFormsFromOld(int old_id) {
        return facadeService.getUncompletedDeliveryFormsFromOld(old_id);
    }

    public LinkedList<FacadeDemand> getUnCompletedItemOnReportByOld(int id) {
        return facadeService.getUnCompletedItemOnReportByOld(id);
    }

    public LinkedList<FacadeTruckingReport> getOldTruckingReport() {
        return facadeService.getOldDTruckingReports();
    }
}
