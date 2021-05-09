package Trucking.Presentation_Layer_Trucking;

import Employees.EmployeeException;
import Trucking.Business_Layer_Trucking.Facade.FacadeObject.*;
import Trucking.Business_Layer_Trucking.Facade.FacadeService;
import Trucking.Business_Layer_Trucking.Resources.Driver;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.nio.file.ProviderMismatchException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class PresentationController {
    private FacadeService facadeService;
    private static PresentationController instance = null;

    private PresentationController() {
        this.facadeService = FacadeService.getInstance();
    }

    public static PresentationController getInstance() {
        if (instance == null)
            instance = new PresentationController();
        return instance;
    }


    public boolean addDemandToReport(int itemNumber, int amount, int siteID) throws IllegalStateException, IllegalArgumentException, SQLException {
        if (itemNumber == 0) return false;
        facadeService.addDemandToReport(itemNumber, amount, siteID);
        return true;


    }

    public boolean continueAddDemandToReport(int itemNumber, int amount, int siteID) throws IllegalArgumentException, SQLException {
        facadeService.continueAddDemandToReport(itemNumber, amount, siteID);
        return true;
    }

    public void CreateReport() throws SQLException {
        facadeService.createTruckingReport();
    }


    public FacadeDeliveryForm getDeliveryForm(int trNumber, int dfNumber) throws IllegalArgumentException, NoSuchElementException {
        FacadeTruckingReport tr = facadeService.getTruckReport(trNumber);
        return facadeService.getDeliveryForm(dfNumber, trNumber);
    }


    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException,SQLException {

        facadeService.addTruck(model, licenseNumber, weightNeto, maxWeight);

    }

    public void addDriver(String id, String name, Driver.License license) throws KeyAlreadyExistsException,SQLException {

        facadeService.addDriver(id, name, license);
    }


    public void addSite(String city, int deliveryArea, String phoneNumber, String contactName, String name) throws KeyAlreadyExistsException, SQLException {
        facadeService.addSite(city, deliveryArea, phoneNumber, contactName, name);
    }

    public void addItem(double weight, String name, int siteID) throws NoSuchElementException, KeyAlreadyExistsException, SQLException {
        facadeService.addItem(weight, name, siteID);
    }


    public void RemoveItemFromPool(int item) throws NoSuchElementException, SQLException {

        facadeService.removeItemFromPool(item);
    }

    public LinkedList<FacadeDemand> showDemands() throws NoSuchElementException, SQLException {
        return facadeService.showDemands();
    }

    public String getItemName(int itemID) {
        return facadeService.getItemName(itemID);
    }

    public String getSiteName(int site) {
        return facadeService.getSiteName(site);
    }


    public LinkedList<FacadeTruck> getAvailableTrucks(LocalDate date, LocalTime shift) {
        return facadeService.getAvailableTrucks(date, shift);

    }

    public FacadeTruck chooseTruck(String truck, LocalDate date, LocalTime shift) throws NoSuchElementException, IllegalStateException, SQLException {
        return facadeService.chooseTruck(truck, date, shift);
    }

    public double getWeight(int itemID) {
        return facadeService.getItemWeight(itemID);
    }


    public FacadeDriver chooseDriver(String driverID, LocalDate date, LocalTime shift) throws IllegalStateException, NoSuchElementException, SQLException {
        return facadeService.chooseDriver(driverID, date, shift);
    }

    public void chooseLeavingHour(LocalTime time) throws IllegalArgumentException, SQLException {
        facadeService.chooseLeavingHour(time);
    }

    public void saveReport() throws SQLException {
        facadeService.saveReport();
    }

    public LinkedList<FacadeDriver> getDrivers() {
        return facadeService.getDrivers();
    }

    public LinkedList<FacadeTruck> getTrucks() {
        return facadeService.getTrucks();
    }

    public LinkedList<FacadeDemand> sortDemandsBySite(LinkedList<FacadeDemand> demands) {
        return facadeService.sortDemandsBySite(demands);
    }

    public LinkedList<FacadeSite> showCurrentSites() {
        return facadeService.getCurrentSites();
    }


    public LinkedList<FacadeDemand> getCurrentDemandsBySite(FacadeSite site) throws SQLException {
        return facadeService.getCurrentDemandsBySite(site);

    }

    public int getWeightOfCurrReport() {
        return facadeService.getWeightOfCurrReport();
    }

    /**
     * this method removes a destination from the in-build Trucking report
     *
     * @param siteID
     * @return @returns true if succeed, throws exception otherwise
     * @throws NoSuchElementException
     */
    public boolean removeDestination(int siteID) throws NoSuchElementException, SQLException { //returns true in succeed
        facadeService.removeDestination(siteID);
        return false;
    }

    public LinkedList<FacadeDemand> getItemsOnTruck() throws SQLException {
        return facadeService.getItemsOnTruck();

    }

    public void removeItemFromReport(FacadeDemand demand, int amount) throws SQLException {
        facadeService.removeItemFromReport(demand, amount);

    }

    public FacadeTruckingReport getCurrTruckReport() {
        return facadeService.getCurrTruckReport();
    }

    public LinkedList<FacadeItem> getAllItems() {
        return facadeService.getAllItems();
    }

    public LinkedList<FacadeSite> getAllSites() throws NoSuchElementException {
        return facadeService.getAllSites();
    }

    public void addDemandToSystem(int itemId, int site, int amount) throws SQLException {
        facadeService.addDemandToSystem(itemId, site, amount);
    }

    public LinkedList<FacadeTruckingReport> getActiveTruckingReports() {
        return facadeService.getActiveTruckingReports();
    }

    public LinkedList<FacadeDeliveryForm> getDeliveryForms(int trID) {
        return facadeService.getDeliveryForms(trID);
    }

    public void updateDeliveryFormRealWeight(int trID, int dfID, int weight) throws IllegalStateException, SQLException {
        facadeService.updateDeliveryFormRealWeight(trID, dfID, weight);
    }

    public void removeSiteFromTruckReport(int siteID, int trID) throws NoSuchElementException, SQLException {
        facadeService.removeSiteFromTruckReport(siteID, trID);
    }


    public void removeDemand(FacadeDemand d) throws SQLException {
        facadeService.removeDemand(d);
    }

    public LinkedList<FacadeDeliveryForm> getUnComplitedDeliveryForms(int trId) {
        return facadeService.getUnComplitedDeliveryForms(trId);
    }

    public FacadeTruckingReport getNewTruckReportID(FacadeTruckingReport oldTr) throws SQLException {

        return facadeService.getNewTruckReport(oldTr);
    }

    public void moveDemandsFromCurrentToReport(FacadeTruckingReport tr) throws SQLException {
        facadeService.moveDemandsFromCurrentToReport(tr);
    }

    public void replaceTruckAndDriver(String truckNumber, String driverID, FacadeTruckingReport tr, int weight) throws InputMismatchException, SQLException {
        facadeService.replaceTruckAndDriver(truckNumber, driverID, tr, weight);
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

    public HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> getDaysAndDrivers() throws IllegalArgumentException {
        return facadeService.getDaysAndDrivers();
    }

    public FacadeDriver getDriver(String id) {
        return facadeService.getDriver(id);
    }

    /*    public LinkedList<FacadeTruck> getAvailableTrucksCurrTr() {
            return facadeService.getAvailableTrucks();
        }*/
    public LinkedList<FacadeDriver> getAvailableDrivers(LocalDate date, LocalTime shift) {
        return facadeService.getAvailableDrivers(date, shift);
    }

    public void deleteDriverConstarint(String id, LocalDate date, LocalTime leavingHour) {
        facadeService.deleteDriverConstarint(id, date, leavingHour);
    }

    public void deleteTruckConstarint(String id, LocalDate date, LocalTime leavingHour) {
        facadeService.deleteTruckConstarint(id, date, leavingHour);
    }

    public void addDriverConstraint(String id, LocalDate date, LocalTime leavingHour) {
        facadeService.addDriverConstarint(id, date, leavingHour);

    }

    public void addTruckConstraint(String id, LocalDate date, LocalTime leavingHour) {
        facadeService.addTruckConstraint(id, date, leavingHour);
    }

    public void chooseDateToCurrentTR(LocalDate chosen) throws SQLException {
        facadeService.chooseDateToCurrentTR(chosen);
    }

    public void removeSiteFromPool(int siteID) throws NoSuchElementException, IllegalStateException, SQLException {
        facadeService.removeSiteFromPool(siteID);
    }

    public int getSiteDeliveryArea(int site) {
        return facadeService.getSiteDeliveryArea(site);
    }
    public void removeItemFromTruckingReport(int trID, FacadeDemand demand) throws SQLException {
        facadeService.removeItemFromTruckingReport(trID,demand);
    }

    public LinkedList<FacadeTruckingReport> showTodayReports() {
        return facadeService.getTodayReports();
    }


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
