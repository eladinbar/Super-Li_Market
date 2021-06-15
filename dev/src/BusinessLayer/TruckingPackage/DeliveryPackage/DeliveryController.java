package BusinessLayer.TruckingPackage.DeliveryPackage;

import BusinessLayer.Notification;
import BusinessLayer.TruckingNotifications;
import DataAccessLayer.DalControllers.TruckingControllers.*;
import DataAccessLayer.DalObjects.TruckingObjects.*;
import InfrastructurePackage.Pair;
import ServiceLayer.FacadeObjects.FacadeItem;
import ServiceLayer.FacadeObjects.FacadeSupplier;
import ServiceLayer.Response.ResponseT;
import ServiceLayer.Service;

import javax.naming.TimeLimitExceededException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.lang.System.exit;

public class DeliveryController {

    private LinkedList<Demand> demands;
    private HashMap<Integer, TruckingReport> activeTruckingReports;//<trID,TR>
    private HashMap<Integer, TruckingReport> waitingTruckingReports;//<trID,TR>
    private HashMap<Integer, TruckingReport> oldTruckingReports;//<trID,TR>
    private HashMap<Integer, LinkedList<DeliveryForm>> activeDeliveryForms;
    private HashMap<Integer, LinkedList<DeliveryForm>> oldDeliveryForms;
    private int lastNotification;
    private int lastReportID;
    private int lastDeliveryForms;
    private static DeliveryController instance = null;
    private LinkedList<TruckingNotifications> notifications;

    public static DeliveryController getInstance() {
        if (instance == null)
            instance = new DeliveryController();
        return instance;
    }

    private DeliveryController() {
        this.demands = new LinkedList<>();
        this.activeTruckingReports = new HashMap<>();
        this.oldTruckingReports = new HashMap<>();
        this.oldDeliveryForms = new HashMap<>();
        this.notifications = new LinkedList<>();
        this.waitingTruckingReports = new HashMap<>();
        this.lastDeliveryForms = 1;
        this.lastReportID = 0;
        this.lastNotification = 0;
        this.lastReportID++;
        this.activeDeliveryForms = new HashMap<>();
    }


    public LinkedList<Pair<Integer, Integer>> createTruckReport(LinkedList<Pair<Integer, Integer>> items, String driverId,
                                                                String truckId, int maxWeight, String supplier, LocalDate date, int shift) throws SQLException {
        LinkedList<String> suppliers = new LinkedList<>();
        suppliers.add(supplier);
        TruckingReport tr = new TruckingReport(lastReportID, date, shiftToTime(shift), truckId, driverId, suppliers);
        lastReportID++;
        waitingTruckingReports.put(tr.getID(), tr);
        activeDeliveryForms.put(tr.getID(), new LinkedList<>());
        items = insertItemsToTruckReport(items, supplier, maxWeight, tr.getID());
        return items;
    }


    /**
     * adds demands to pool, if already has demand for this item and supplier, adds its to the existing demands
     *
     * @param items    Pair< itemId, amount > to insert to pool
     * @param supplier supplier for this demand
     * @throws SQLException
     */
    public void addDemand(LinkedList<Pair<Integer, Integer>> items, String supplier) throws SQLException {
        boolean found;
        for (Pair<Integer, Integer> item : items) {
            found = false;
            for (Demand demand : demands) {
                if (demand.getItemID() == item.getFirst() && demand.getSupplier().equals(supplier)) {
                    demand.setAmount(demand.getAmount() + item.getSecond());
                    found = true;
                    break;
                }
            }
            if (!found) {
                Demand d = new Demand(item.getFirst(), supplier, item.getSecond());
                demands.add(d);
            }
        }
    }

    public LinkedList<TruckingReport> getActiveTruckingReports() {
        LinkedList<TruckingReport> tr = new LinkedList<>();
        for (Map.Entry<Integer, TruckingReport> entry : activeTruckingReports.entrySet()) {
            tr.add(entry.getValue());
        }
        return tr;
    }

    public LinkedList<TruckingReport> getWaitingTruckingReports() {
        LinkedList<TruckingReport> tr = new LinkedList<>();
        updateWaitingReports();
        for (Map.Entry<Integer, TruckingReport> entry : waitingTruckingReports.entrySet()) {
            tr.add(entry.getValue());
        }
        return tr;
    }

    private void updateWaitingReports() {
        HashMap<Integer, TruckingReport> waitingReports = new HashMap<>();
        for (Map.Entry<Integer, TruckingReport> entry : waitingTruckingReports.entrySet()) {
            if (!entry.getValue().getDate().isBefore(LocalDate.now().minusDays(1)))
                waitingReports.put(entry.getKey(), entry.getValue());
            else {
                oldTruckingReports.put(entry.getKey(), entry.getValue());
                oldDeliveryForms.put(entry.getKey(), activeDeliveryForms.remove(entry.getKey()));
            }

        }
        waitingTruckingReports = waitingReports;
    }


    public LinkedList<TruckingReport> getOldTruckingReports() {
        LinkedList<TruckingReport> tr = new LinkedList<>();
        for (Map.Entry<Integer, TruckingReport> entry : oldTruckingReports.entrySet()) {
            tr.add(entry.getValue());
        }
        return tr;
    }

    /**
     * show all the current notifications, deletes them after showed
     *
     * @return current notifications since last time
     */
    public LinkedList<TruckingNotifications> getNotifications() throws SQLException {
        LinkedList<TruckingNotifications> output = notifications;
        notifications = new LinkedList<>();

        DalTruckingNotificationController.getInstance().deleteAll();
        return output;
    }

    public Pair<LinkedList<String>, LinkedList<String>> getBusyTrucksByDate(LocalDate date) {
        Pair<LinkedList<String>, LinkedList<String>> result = new Pair<>(new LinkedList<>(), new LinkedList<>());
        //first is trucks on morning shift , second is evening
        LinkedList<TruckingReport> reports = getTruckingReportsByDate(date);

        for (TruckingReport report : reports) {
            if (report.getLeavingHour().equals(LocalTime.of(14, 0))) {
                result.getSecond().add(report.getTruckNumber());
            } else
                result.getFirst().add(report.getTruckNumber());


        }
        return result;
    }

    public int getTruckReportCurrentWeight(int report_id) throws NoSuchElementException {
        if (getTruckReport(report_id) == null) {
            throw new NoSuchElementException("couldn't find report with the id:" + report_id);
        }
        LinkedList<DeliveryForm> dfs = null;
        if (activeDeliveryForms.containsKey(report_id)) {
            dfs = activeDeliveryForms.get(report_id);
        } else
            dfs = getOldDeliveryForms(report_id);
        if (dfs == null) {
            throw new NoSuchElementException("couldn't find Delivery Forms for TruckReport number: " + report_id);
        }
        int total = 0;
        for (DeliveryForm df : dfs) {
            total += df.getLeavingWeight();
        }
        return total;
    }

    /**
     * return all truckReports in a specific date
     *
     * @param date - the date the tr is settled to
     * @return LinkedList of TruckReports, empty if couldn't find any
     */
    public LinkedList<TruckingReport> getTruckingReportsByDate(LocalDate date) {
        LinkedList<TruckingReport> output = new LinkedList<>();

        if (date.isBefore(LocalDate.now())) {
            LinkedList<TruckingReport> olds = getOldTruckingReports();
            for (TruckingReport truckingReport : olds) {
                if (truckingReport.getDate().isEqual(date)) {
                    output.add(truckingReport);
                }
            }
        } else {
            for (Map.Entry<Integer, TruckingReport> entry : activeTruckingReports.entrySet()) {
                if (entry.getValue().getDate().isEqual(date)) {
                    output.add(entry.getValue());
                }
            }
            for (Map.Entry<Integer, TruckingReport> entry : waitingTruckingReports.entrySet()) {
                if (entry.getValue().getDate().isEqual(date)) {
                    output.add(entry.getValue());
                }
            }
        }
        return output;
    }

    public LinkedList<Pair<Integer, Integer>> insertItemsToTruckReport(LinkedList<Pair<Integer, Integer>> items, String supplier,
                                                                       int capacity, int report_id) throws NoSuchElementException, SQLException {
        int currentWeight = getTruckReportCurrentWeight(report_id);
        LinkedList<Pair<Integer, Integer>> left = new LinkedList<>();
        // check the report can add more items
        if (currentWeight < capacity) {
            addSupplierToReport(supplier, report_id);
            int leftWeight = capacity - currentWeight;
            // adds items to the report
            for (Pair<Integer, Integer> item : items) {
                if (item.getSecond() != 0) {
                    int amount = findAmountToAdd(item, leftWeight);
                    if (amount > 0) {
                        // finds the deliveryForm to insert to
                        DeliveryForm toInsert = getDeliveryFormBySupplier(report_id, supplier);
                        // if returns null - does not have this supplier yet, need to create new DF
                        if (toInsert != null) {
                            toInsert.addItem(item.getFirst(), amount);
                            updateLeavingWeight(toInsert);
                        } else {
                            HashMap<Integer, Integer> dfItems = new HashMap<>();
                            dfItems.put(item.getFirst(), amount);

                            DeliveryForm df = new DeliveryForm(lastDeliveryForms, supplier, dfItems, getItemTotalWeight(item.getFirst(), amount), report_id);
                            activeDeliveryForms.get(report_id).add(df);
                            lastDeliveryForms++;
                        }
                        item.setSecond(item.getSecond() - amount);
                        currentWeight = currentWeight + getItemTotalWeight(item.getFirst(), amount);
                        //stops if finished to insert
                        if (currentWeight >= capacity)
                            break;
                    }
                }
            }
            // returns only the items left to insert
            for (Pair<Integer, Integer> item : items) {
                if (item.getSecond() != 0) {
                    left.add(item);
                }
            }
        }
        return left;
    }

    private void updateLeavingWeight(DeliveryForm deliveryForm) throws SQLException {
        int total = 0;
        HashMap<Integer, Integer> items = deliveryForm.getItems();
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            total += getItemTotalWeight(entry.getKey(), entry.getValue());
        }
        deliveryForm.setLeavingWeight(total);
    }

    public int getItemTotalWeight(Integer itemID, int amount) {
        int itemWeight = getItemWeight(itemID);
        int totalWeight = itemWeight * amount;
        return totalWeight;
    }


    /**
     * @param report_id the truckReport id to find the deliveryForm in
     * @param supplier  the supplier id.
     * @return the delivery form, returns null if couldn't find
     */
    private DeliveryForm getDeliveryFormBySupplier(int report_id, String supplier) {
        TruckingReport report = getTruckReport(report_id);
        LinkedList<String> suppliers = report.getSuppliers();
        DeliveryForm output = null;
        if (suppliers.contains(supplier)) {
            LinkedList<DeliveryForm> dfs = null;
            if (activeDeliveryForms.containsKey(report_id)) {
                dfs = activeDeliveryForms.get(report_id);
            } else
                dfs = oldDeliveryForms.get(report_id);
            for (DeliveryForm df : dfs) {
                if (df.getDestination().equals(supplier))
                    output = df;
            }
        }
        return output;
    }

    /**
     * finds the maximum amount to add for the specific amount.
     * works like binary search
     *
     * @param item       - Item id, amount
     * @param leftWeight - Maximum to add
     * @return the amount to add, 0 if none
     */
    private int findAmountToAdd(Pair<Integer, Integer> item, int leftWeight) {
        int output = 0;
        int totalWeight = 0;
        boolean found = false;
        int maxAmount = item.getSecond();
        int minimumAmount = 0;
        int itemWeight = getItemWeight(item.getFirst());
        while (!found) {
            totalWeight = output * itemWeight;
            if (minimumAmount >= maxAmount) {
                output = maxAmount;
                found = true;
                break;
            }
            if (totalWeight == leftWeight) {
                found = true;
                break;

            }
            // if adding 1 more exceeds, finished
            else if ((totalWeight < leftWeight) && (totalWeight + itemWeight > leftWeight)) {
                found = true;
                break;
            } else {
                if (totalWeight < leftWeight) {
                    minimumAmount = output + 1;
                } else {
                    maxAmount = output - 1;
                }
                output = (maxAmount + minimumAmount) / 2;
            }
        }
        return output;
    }

    public LinkedList<String> getTruckReportSuppliers(int report_id) {
        return getTruckReport(report_id).getSuppliers();
    }

    public TruckingReport getTruckReport(int trNumber) throws NoSuchElementException {
        LinkedList<TruckingReport> olds = getOldTruckingReports();
        for (TruckingReport tr : olds) {
            if (tr.getID() == trNumber)
                return tr;
        }
        if (activeTruckingReports.containsKey(trNumber)) {
            return activeTruckingReports.get(trNumber);
        } else if (waitingTruckingReports.containsKey(trNumber)) {
            return waitingTruckingReports.get(trNumber);
        } else throw new NoSuchElementException("No Report with such ID");

    }

    public void managerApproveTruckReport(Integer trID) throws TimeLimitExceededException, SQLException {
        TruckingReport report = getTruckReport(trID);
        if (report.getDate().isBefore(LocalDate.now()))
            throw new TimeLimitExceededException("the report date has already passed and cannot be approved");
        if (!report.isApproved()) {
            report.setApproved();
            waitingTruckingReports.remove(trID);
            activeTruckingReports.put(trID, report);
        }
    }

    public void managerCancelTruckReport(int trID) throws TimeLimitExceededException, SQLException {
        TruckingReport report = getTruckReport(trID);
        if (report.getDate().isBefore(LocalDate.now()))
            throw new TimeLimitExceededException("the report date has already passed and cannot be approved");
        if (!report.isApproved()) {
            DalDeliveryFormController controller = DalDeliveryFormController.getInstance();
            LinkedList<DeliveryForm> dfs = activeDeliveryForms.get(trID);
            for (DeliveryForm df : dfs) {
                controller.delete(new DalDeliveryForm(df.getID(), df.getDestination(), df.isCompleted(), df.getLeavingWeight(), df.getTrID()));
            }

            TruckingReport delete = waitingTruckingReports.remove(trID);
            activeDeliveryForms.remove(trID);
            DalTruckingReport tr = new DalTruckingReport(delete.getID(), delete.getLeavingHour(), delete.getDate(), delete.getTruckNumber(), delete.getDriverID(), delete.isCompleted(), delete.isApproved());
            DalTruckingReportController.getInstance().delete(tr);
        }
    }

    public LinkedList<Demand> getDemands() {
        return demands;
    }

    public void setDemandNewAmount(Integer id, Integer amount, String supplier) throws SQLException {
        for (Demand demand : demands) {
            if ((demand.getSupplier().equals(supplier)) && (demand.getItemID() == id)) {
                if (amount == 0) {
                    demands.remove(demand);
                }
                demand.setAmount(amount);
            }
        }
    }

    public DeliveryForm getDeliveryForm(int id) throws NoSuchElementException {
        DeliveryForm deliveryForm = null;
        for (Map.Entry<Integer, LinkedList<DeliveryForm>> entry : activeDeliveryForms.entrySet()) {
            for (DeliveryForm df : entry.getValue()) {
                if (df.getID() == id)
                    return df;
            }
        }
        for (Map.Entry<Integer, LinkedList<DeliveryForm>> entry : oldDeliveryForms.entrySet()) {
            for (DeliveryForm df : entry.getValue()) {
                if (df.getID() == id)
                    return df;
            }
        }
        throw new NoSuchElementException("cannot find DeliveryForm With Such id: " + id);
    }


    private LocalTime shiftToTime(int shift) {
        if (shift == 0)
            return LocalTime.of(6, 0);
        else
            return LocalTime.of(14, 0);
    }


    private void addSupplierToReport(String supplier, int report_id) throws SQLException {
        int supplier_area = getSupplierArea(supplier);
        LinkedList<Integer> report_areas = getReportAreas(report_id);
        if (!report_areas.contains(supplier_area)) {
            addNotification("Truck Report Number: " + report_id + "\n\thas been extended to another delivery area." +
                    "\n\tthe new Delivery area: " + supplier_area);
        }
        getTruckReport(report_id).addSupplier(supplier);
    }

    public void addNotification(String s) throws SQLException {
        notifications.add(new TruckingNotifications(s));
        lastNotification++;
        DalTruckingNotificationController.getInstance().insert(new DalTruckingNotification(lastNotification, s));
    }


    public LinkedList<Integer> getReportAreas(int report_id) {
        LinkedList<Integer> areas = new LinkedList<>();
        LinkedList<String> suppliers = getTruckReport(report_id).getSuppliers();
        for (String supplier : suppliers) {
            if (!areas.contains(getSupplierArea(supplier))) {
                areas.add(getSupplierArea(supplier));
            }
        }
        return areas;
    }

    public void setLastNotification(int lastNotification) {
        this.lastNotification = lastNotification;
    }

    public int getLastNotification() {
        return lastNotification;
    }

    private LinkedList<DeliveryForm> getOldDeliveryForms(int report_id) {
        return oldDeliveryForms.get(report_id);
    }

    private int getSupplierArea(String supplier) throws NoSuchElementException {
        ResponseT<FacadeSupplier> res = Service.getInstance().getSupplier("" + supplier);
        if (res.errorOccurred()) {
            throw new NoSuchElementException(res.getErrorMessage());
        }
        return res.getValue().getSc().getArea();
    }

    private int getItemWeight(Integer item_id) {
        ResponseT<FacadeItem> res = Service.getInstance().getItem(item_id);
        if (res.errorOccurred()) {
            throw new NoSuchElementException(res.getErrorMessage());
        }
        FacadeItem item = res.getValue();
        return item.getWeight();
    }

    public LinkedList<DeliveryForm> getTruckReportDeliveryForms(int report_id) {
        getTruckReport(report_id);
        LinkedList<DeliveryForm> dfs = new LinkedList<>();
        if (activeDeliveryForms.containsKey(report_id))
            dfs = activeDeliveryForms.get(report_id);
        else {
            dfs = oldDeliveryForms.get(report_id);

        }
        return dfs;
    }

    public HashMap<String, HashMap<LocalDate, Integer>> getTruckConstraintsFromUpload() {
        HashMap<String, HashMap<LocalDate, Integer>> result = new HashMap<>();
        HashMap<Integer, TruckingReport> reports = new HashMap<>(activeTruckingReports);
        for (Map.Entry<Integer, TruckingReport> entry : waitingTruckingReports.entrySet()) {
            reports.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Integer, TruckingReport> entry : reports.entrySet()) {
            TruckingReport report = getTruckReport(entry.getKey());
            String truckNumber = report.getTruckNumber();
            if (result.get(truckNumber) == null)  // checks if already exist for the specific truck
            {
                result.put(truckNumber, new HashMap<>());
            }
            LocalDate date = report.getDate();
            int shift = turnTimeToShift(report.getLeavingHour());
            if (result.get(truckNumber).get(date) == null) {
                result.get(truckNumber).put(date, shift);
            } else result.get(truckNumber).put(date, 2);
        }
        return result;
    }

    public HashMap<String, HashMap<LocalDate, Integer>> getDriverConstraintsFromUpload() {
        HashMap<String, HashMap<LocalDate, Integer>> result = new HashMap<>();
        HashMap<Integer, TruckingReport> reports = new HashMap<>(activeTruckingReports);
        for (Map.Entry<Integer, TruckingReport> entry : waitingTruckingReports.entrySet()) {
            reports.put(entry.getKey(), entry.getValue());
        }
        for (Map.Entry<Integer, TruckingReport> entry : reports.entrySet()) {
            TruckingReport report = getTruckReport(entry.getKey());
            String driverID = report.getDriverID();
            if (result.get(driverID) == null) { // checks if already exist for the specific truck
                result.put(driverID, new HashMap<>());
            }
            LocalDate date = report.getDate();
            int shift = turnTimeToShift(report.getLeavingHour());
            if (result.get(driverID).get(date) == null) {
                result.get(driverID).put(date, shift);
            } else result.get(driverID).put(date, 2);
        }
        return result;
    }

    private int turnTimeToShift(LocalTime shift) {
        if (shift.isBefore(LocalTime.of(14, 0)))
            return 0;
        else
            return 1;
    }

    public void upload() throws SQLException {
        DalTruckingNotificationController notificationController = DalTruckingNotificationController.getInstance();
        DalDemandController dalDemandController = DalDemandController.getInstance();
        DalItemsOnDFController itemsOnDF = DalItemsOnDFController.getInstance();
        DalDeliveryFormController delivery = DalDeliveryFormController.getInstance();
        DalTruckingReportController truckingReport = DalTruckingReportController.getInstance();
        LinkedList<DalTruckingReport> d_truckingReports = truckingReport.load();
        // sets the Trucking reports in old/ active as needed
        LinkedList<DalDemand> dalDemands = dalDemandController.load();
        for (DalDemand demand : dalDemands) {                 // demand list create
            demands.add(new Demand(demand));
        }
        for (DalTruckingReport dr : d_truckingReports) {
            activeTruckingReports.put(dr.getID(), new TruckingReport(dr));
        }
        fixActiveAndOldTruckingReports();

        LinkedList<DalDeliveryForm> deliveryForms = delivery.load();
        for (DalDeliveryForm df : deliveryForms) {
            DeliveryForm deliveryForm = new DeliveryForm(df);
            if (activeTruckingReports.containsKey(deliveryForm.getTrID()) || waitingTruckingReports.containsKey(deliveryForm.getTrID())) {
                if (activeDeliveryForms.get(deliveryForm.getTrID()) == null)
                    activeDeliveryForms.put(deliveryForm.getTrID(), new LinkedList<>());
                activeDeliveryForms.get(deliveryForm.getTrID()).add(deliveryForm);
            } else {
                if (oldDeliveryForms.get(deliveryForm.getTrID()) == null)
                    oldDeliveryForms.put(deliveryForm.getTrID(), new LinkedList<>());
                oldDeliveryForms.get(deliveryForm.getTrID()).add(deliveryForm);
            }
            getTruckReport(deliveryForm.getTrID()).addSupplier(deliveryForm.getDestination());
        }
        LinkedList<DalItemsOnDF> itemsOnDFS = itemsOnDF.load();
        for (DalItemsOnDF iod : itemsOnDFS) {
            DeliveryForm df = getDeliveryForm(iod.getDFID());
            df.addItem(iod.getItemID(), iod.getAmount());
        }
        LinkedList<DalTruckingNotification> dalTruckingNotifications = notificationController.load();
        for (DalTruckingNotification notification : dalTruckingNotifications) {
            TruckingNotifications note = new TruckingNotifications(notification);
            lastNotification = Math.max(notification.getID(), lastNotification);
            notifications.add(note);
        }
        lastNotification++;
    }

    private void fixActiveAndOldTruckingReports() {
        HashMap<Integer, TruckingReport> waitingReports = new HashMap<>();
        HashMap<Integer, TruckingReport> activeReports = new HashMap<>();
        HashMap<Integer, TruckingReport> oldReports = new HashMap<>();
        for (Map.Entry<Integer, TruckingReport> entry : activeTruckingReports.entrySet()) {
            if (entry.getValue().getDate().isBefore(LocalDate.now())) {
                oldReports.put(entry.getKey(), entry.getValue());
            } else {
                if (!entry.getValue().isApproved())
                    waitingReports.put(entry.getKey(), entry.getValue());
                else activeReports.put(entry.getKey(), entry.getValue());
            }
        }
        activeTruckingReports = activeReports;
        waitingTruckingReports = waitingReports;
        oldTruckingReports = oldReports;
    }

    public void setCompletedTruckReport(int report_id) throws SQLException {
        TruckingReport report = getTruckReport(report_id);
        LinkedList<DeliveryForm> deliveryForms = getTruckReportDeliveryForms(report_id);
        report.setCompleted();
        for (DeliveryForm df : deliveryForms) {
            df.setCompleted();
        }
        activeDeliveryForms.remove(report_id);
        waitingTruckingReports.remove(report_id);
        oldTruckingReports.put(report_id, report);
        oldDeliveryForms.put(report_id, activeDeliveryForms.remove(report_id));
    }
}
