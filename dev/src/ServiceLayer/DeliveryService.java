package ServiceLayer;

import BusinessLayer.TruckingNotifications;
import InfrastructurePackage.Pair;
import ServiceLayer.FacadeObjects.FacadeDeliveryForm;
import ServiceLayer.FacadeObjects.FacadeDemand;
import ServiceLayer.FacadeObjects.FacadeTruckingReport;
import BusinessLayer.TruckingPackage.DeliveryPackage.*;

import javax.naming.TimeLimitExceededException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import static java.lang.System.exit;

public class DeliveryService {
    private final DeliveryController dc;
    private static DeliveryService instance = null;


    private DeliveryService() {
        this.dc = DeliveryController.getInstance();
    }

    public static DeliveryService getInstance() {
        if (instance == null)
            instance = new DeliveryService();
        return instance;
    }

    public LinkedList<String> getTruckReportDestinations(int id) {
        return dc.getTruckReportSuppliers(id);
    }

    public LinkedList<Integer> getTruckReportDeliveryAreas(int id) {
        return dc.getReportAreas(id);
    }


    public LinkedList<TruckingNotifications> getNotifications() throws SQLException {
        return dc.getNotifications();
    }

    public LinkedList<FacadeDemand> getDemands() {
        return turnListDemandToFacade(dc.getDemands());
    }

    public LinkedList<FacadeTruckingReport> getActiveTruckingReports() {
        return turnListTruckingReportsToFacade(dc.getActiveTruckingReports());
    }

    public LinkedList<FacadeTruckingReport> getWaitingTruckingReports() {
        return turnListTruckingReportsToFacade(dc.getWaitingTruckingReports());
    }

    public LinkedList<FacadeTruckingReport> getOldTruckingReports() {
        return turnListTruckingReportsToFacade(dc.getOldTruckingReports());

    }

    public void managerApproveTruckReport(Integer trID) throws TimeLimitExceededException, SQLException {
            dc.managerApproveTruckReport(trID);
    }

    public void managerCancelTruckReport(Integer trID) throws TimeLimitExceededException, SQLException {
        dc.managerCancelTruckReport(trID);
    }

    public void setItemNewAmount(Integer id, Integer amount, String supplier) {
        try {
            dc.setDemandNewAmount(id, amount, supplier);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exit(1);
        }
    }

    public FacadeTruckingReport getTruckReport(int id)throws NoSuchElementException {
        return new FacadeTruckingReport( dc.getTruckReport(id));
    }

    public FacadeDeliveryForm getDeliveryForm(int id) {
        return new FacadeDeliveryForm(dc.getDeliveryForm(id));
    }

    public int getTruckReportCurrentWeight(int id) throws NoSuchElementException{
        return dc.getTruckReportCurrentWeight(id);
    }


    public LinkedList<Pair<Integer, Integer>> insertItemsToTruckReport(LinkedList<Pair<Integer, Integer>> left,
                                                                       String supplier, int capacity, int id) throws SQLException {
        return dc.insertItemsToTruckReport(left, supplier, capacity, id);
    }

    public LinkedList<FacadeTruckingReport> getAvailableTRsByDate(LocalDate date) {
        return turnListTruckingReportsToFacade(dc.getTruckingReportsByDate(date));
    }

    public LinkedList<Pair<Integer, Integer>> createReport(LinkedList<Pair<Integer, Integer>> items, String driverId, String truckId, int maxWeight, String supplier, LocalDate date, int shift) {
        try {
            return dc.createTruckReport(items, driverId, truckId, maxWeight, supplier, date, shift);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            exit(1);
        }
        return null;
    }

    public LinkedList<FacadeDeliveryForm> getTruckReportDeliveryForms(int report_id) {
        return turnListDeliveryFormToFacade(dc.getTruckReportDeliveryForms(report_id));
    }

    public void addDemandToPool(LinkedList<Pair<Integer, Integer>> items, String supplier) throws SQLException {
        dc.addDemand(items, supplier);
    }

    public LinkedList<FacadeTruckingReport> getTruckReportsByDate(LocalDate date) {
        return turnListTruckingReportsToFacade(dc.getTruckingReportsByDate(date));
    }

    private LinkedList<FacadeTruckingReport> turnListTruckingReportsToFacade(LinkedList<TruckingReport> reports) {
        LinkedList<FacadeTruckingReport> ftrs = new LinkedList<>();
        for (TruckingReport tr : reports) {
            ftrs.add(new FacadeTruckingReport(tr));
        }
        return ftrs;
    }

    private LinkedList<FacadeDemand> turnListDemandToFacade(LinkedList<Demand> demands) {
        LinkedList<FacadeDemand> dems = new LinkedList<>();
        for (Demand demand : demands) {
            dems.add(new FacadeDemand(demand));
        }
        return dems;
    }

    private LinkedList<FacadeDeliveryForm> turnListDeliveryFormToFacade(LinkedList<DeliveryForm> deliveryForms) {
        LinkedList<FacadeDeliveryForm> dfs = new LinkedList<>();
        for (DeliveryForm deliveryForm : deliveryForms) {
            dfs.add(new FacadeDeliveryForm(deliveryForm));
        }
        return dfs;
    }

    public Pair<LinkedList<String>, LinkedList<String>> getBusyTrucksByDate(LocalDate date) {
        return dc.getBusyTrucksByDate(date);
    }

    public int getItemTotalWeight(Integer item_id, Integer amount) {
        return dc.getItemTotalWeight(item_id, amount);
    }

    public void addNotification(String content) throws SQLException {
        dc.addNotification(content);
    }

    public void upload() throws SQLException {
        dc.upload();

    }

    public HashMap<String, HashMap<LocalDate, Integer>> getDriverConstraintsFromUpload(){
        return dc.getDriverConstraintsFromUpload();
    }

    public HashMap<String, HashMap<LocalDate, Integer>> getTruckConstraintsFromUpload() {
        return dc.getTruckConstraintsFromUpload();
    }

    public void setCompletedTruckReport(int report_id) throws SQLException {
        dc.setCompletedTruckReport(report_id);
    }
}
