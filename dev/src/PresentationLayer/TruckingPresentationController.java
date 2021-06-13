package PresentationLayer;

import ServiceLayer.FacadeObjects.*;
import ServiceLayer.TruckingService;
import BusinessLayer.TruckingPackage.ResourcesPackage.Driver;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TruckingPresentationController {
    private TruckingService truckingService;
    private static TruckingPresentationController instance = null;

    private TruckingPresentationController() {
        this.truckingService = TruckingService.getInstance();
    }

    public static TruckingPresentationController getInstance() {
        if (instance == null)
            instance = new TruckingPresentationController();
        return instance;
    }
//
//
//    public boolean addDemandToReport(int itemNumber, int amount, int siteID) throws IllegalStateException, IllegalArgumentException, SQLException {
//        if (itemNumber == 0) return false;
//        truckingService.addDemandToReport(itemNumber, amount, siteID);
//        return true;
//
//
//    }
//
//    public boolean continueAddDemandToReport(int itemNumber, int amount, int siteID) throws IllegalArgumentException, SQLException {
//        truckingService.continueAddDemandToReport(itemNumber, amount, siteID);
//        return true;
//    }
//
//    public void CreateReport() throws SQLException {
//        truckingService.createTruckingReport();
//    }
//
//
//    public FacadeDeliveryForm getDeliveryForm(int trNumber, int dfNumber) throws IllegalArgumentException, NoSuchElementException {
//        FacadeTruckingReport tr = truckingService.getTruckReport(trNumber);
//        return truckingService.getDeliveryForm(dfNumber, trNumber);
//    }
//
//
//    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException,SQLException {
//
//        truckingService.addTruck(model, licenseNumber, weightNeto, maxWeight);
//
//    }
//
//    public void addDriver(String id, String name, Driver.License license) throws KeyAlreadyExistsException,SQLException {
//
//        truckingService.addDriver(id, name, license);
//    }
//
//
//    public void addSite(String city, int deliveryArea, String phoneNumber, String contactName, String name) throws KeyAlreadyExistsException, SQLException {
//        truckingService.addSite(city, deliveryArea, phoneNumber, contactName, name);
//    }
//
//    public void addItem(double weight, String name, int siteID) throws NoSuchElementException, KeyAlreadyExistsException, SQLException {
//        truckingService.addItem(weight, name, siteID);
//    }
//
//
//    public void RemoveItemFromPool(int item) throws NoSuchElementException, SQLException {
//
//        truckingService.removeItemFromPool(item);
//    }
//
//    public LinkedList<FacadeDemand> showDemands() throws NoSuchElementException, SQLException {
//        return truckingService.showDemands();
//    }
//
//    public String getItemName(int itemID) {
//        return truckingService.getItemName(itemID);
//    }
//
//    public String getSiteName(int site) {
//        return truckingService.getSiteName(site);
//    }
//
//
//    public LinkedList<FacadeTruck> getAvailableTrucks(LocalDate date, LocalTime shift) {
//        return truckingService.getAvailableTrucks(date, shift);
//
//    }
//
//    public FacadeTruck chooseTruck(String truck, LocalDate date, LocalTime shift) throws NoSuchElementException, IllegalStateException, SQLException {
//        return truckingService.chooseTruck(truck, date, shift);
//    }
//
//    public double getWeight(int itemID) {
//        return truckingService.getItemWeight(itemID);
//    }
//
//
//    public FacadeDriver chooseDriver(String driverID, LocalDate date, LocalTime shift) throws IllegalStateException, NoSuchElementException, SQLException {
//        return truckingService.chooseDriver(driverID, date, shift);
//    }
//
//    public void chooseLeavingHour(LocalTime time) throws IllegalArgumentException, SQLException {
//        truckingService.chooseLeavingHour(time);
//    }
//
//    public void saveReport() throws SQLException {
//        truckingService.saveReport();
//    }
//
//    public LinkedList<FacadeDriver> getDrivers() {
//        return truckingService.getDrivers();
//    }
//
//    public LinkedList<FacadeTruck> getTrucks() {
//        return truckingService.getTrucks();
//    }
//
//    public LinkedList<FacadeDemand> sortDemandsBySite(LinkedList<FacadeDemand> demands) {
//        return truckingService.sortDemandsBySite(demands);
//    }
//
//    public LinkedList<FacadeSite> showCurrentSites() {
//        return truckingService.getCurrentSites();
//    }
//
//
//    public LinkedList<FacadeDemand> getCurrentDemandsBySite(FacadeSite site) throws SQLException {
//        return truckingService.getCurrentDemandsBySite(site);
//
//    }
//
//    public int getWeightOfCurrReport() {
//        return truckingService.getWeightOfCurrReport();
//    }
//
//    /**
//     * this method removes a destination from the in-build Trucking report
//     *
//     * @param siteID
//     * @return @returns true if succeed, throws exception otherwise
//     * @throws NoSuchElementException
//     */
//    public boolean removeDestination(int siteID) throws NoSuchElementException, SQLException { //returns true in succeed
//        truckingService.removeDestination(siteID);
//        return false;
//    }
//
//    public LinkedList<FacadeDemand> getItemsOnTruck() throws SQLException {
//        return truckingService.getItemsOnTruck();
//
//    }
//
//    public void removeItemFromReport(FacadeDemand demand, int amount) throws SQLException {
//        truckingService.removeItemFromReport(demand, amount);
//
//    }
//
//    public FacadeTruckingReport getCurrTruckReport() {
//        return truckingService.getCurrTruckReport();
//    }
//
//    public LinkedList<FacadeItem> getAllItems() {
//        return truckingService.getAllItems();
//    }
//
//    public LinkedList<FacadeSite> getAllSites() throws NoSuchElementException {
//        return truckingService.getAllSites();
//    }
//
//    public void addDemandToSystem(int itemId, int site, int amount) throws SQLException {
//        truckingService.addDemandToSystem(itemId, site, amount);
//    }
//
//    public LinkedList<FacadeTruckingReport> getActiveTruckingReports() {
//        return truckingService.getActiveTruckingReports();
//    }
//
//    public LinkedList<FacadeDeliveryForm> getDeliveryForms(int trID) {
//        return truckingService.getDeliveryForms(trID);
//    }
//
//    public void updateDeliveryFormRealWeight(int trID, int dfID, int weight) throws IllegalStateException, SQLException {
//        truckingService.updateDeliveryFormRealWeight(trID, dfID, weight);
//    }
//
//    public void removeSiteFromTruckReport(int siteID, int trID) throws NoSuchElementException, SQLException {
//        truckingService.removeSiteFromTruckReport(siteID, trID);
//    }
//
//
//    public void removeDemand(FacadeDemand d) throws SQLException {
//        truckingService.removeDemand(d);
//    }
//
//    public LinkedList<FacadeDeliveryForm> getUnComplitedDeliveryForms(int trId) {
//        return truckingService.getUnComplitedDeliveryForms(trId);
//    }
//
//    public FacadeTruckingReport getNewTruckReportID(FacadeTruckingReport oldTr) throws SQLException {
//
//        return truckingService.getNewTruckReport(oldTr);
//    }
//
//    public void moveDemandsFromCurrentToReport(FacadeTruckingReport tr) throws SQLException {
//        truckingService.moveDemandsFromCurrentToReport(tr);
//    }
//
//    public void replaceTruckAndDriver(String truckNumber, String driverID, FacadeTruckingReport tr, int weight) throws InputMismatchException, SQLException {
//        truckingService.replaceTruckAndDriver(truckNumber, driverID, tr, weight);
//    }
//
//
//    public LinkedList<FacadeDemand> getAllDemands() {
//        return truckingService.getAllDemands();
//    }
//
//    public FacadeTruckingReport getCurrentTruckReport() {
//        return truckingService.getCurrTruckReport();
//
//    }
//
//    public LinkedList<FacadeDeliveryForm> getUncompletedDeliveryFormsFromOld(int old_id) {
//        return truckingService.getUncompletedDeliveryFormsFromOld(old_id);
//    }
//
//    public LinkedList<FacadeDemand> getUnCompletedItemOnReportByOld(int id) {
//        return truckingService.getUnCompletedItemOnReportByOld(id);
//    }
//
//    public LinkedList<FacadeTruckingReport> getOldTruckingReport() {
//        return truckingService.getOldDTruckingReports();
//    }
//
//    public HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> getDaysAndDrivers() throws IllegalArgumentException {
//        return truckingService.getDaysAndDrivers();
//    }
//
//    public FacadeDriver getDriver(String id) {
//        return truckingService.getDriver(id);
//    }
//
//    /*    public LinkedList<FacadeTruck> getAvailableTrucksCurrTr() {
//            return facadeService.getAvailableTrucks();
//        }*/
//    public LinkedList<FacadeDriver> getAvailableDrivers(LocalDate date, LocalTime shift) {
//        return truckingService.getAvailableDrivers(date, shift);
//    }
//
//    public void deleteDriverConstarint(String id, LocalDate date, LocalTime leavingHour) {
//        truckingService.deleteDriverConstarint(id, date, leavingHour);
//    }
//
//    public void deleteTruckConstarint(String id, LocalDate date, LocalTime leavingHour) {
//        truckingService.deleteTruckConstarint(id, date, leavingHour);
//    }
//
//    public void addDriverConstraint(String id, LocalDate date, LocalTime leavingHour) {
//        truckingService.addDriverConstarint(id, date, leavingHour);
//
//    }
//
//    public void addTruckConstraint(String id, LocalDate date, LocalTime leavingHour) {
//        truckingService.addTruckConstraint(id, date, leavingHour);
//    }
//
//    public void chooseDateToCurrentTR(LocalDate chosen) throws SQLException {
//        truckingService.chooseDateToCurrentTR(chosen);
//    }
//
//    public void removeSiteFromPool(int siteID) throws NoSuchElementException, IllegalStateException, SQLException {
//        truckingService.removeSiteFromPool(siteID);
//    }
//
//    public int getSiteDeliveryArea(int site) {
//        return truckingService.getSiteDeliveryArea(site);
//    }
//    public void removeItemFromTruckingReport(int trID, FacadeDemand demand) throws SQLException {
//        truckingService.removeItemFromTruckingReport(trID,demand);
//    }
//
//    public LinkedList<FacadeTruckingReport> showTodayReports() {
//        return truckingService.getTodayReports();
//    }
//
//    public void upload() throws SQLException {
//        truckingService.upload();
//    }


    // Not in use for now

/*
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





    public boolean continueAddDemandToTruckReport(int itemNumber, int amount, int siteID, int truckId) {
        return facadeService.continueAddDemandToTruckReport(itemNumber,amount,siteID,truckId);
    }


    }*/

}
