package ServiceLayer;

import BusinessLayer.EmployeePackage.EmployeeException;
import BusinessLayer.EmployeePackage.ShiftPackage.ShiftController;
import BusinessLayer.TruckingNotifications;
import DataAccessLayer.DalControllers.TruckingControllers.*;
import InfrastructurePackage.Pair;
import ServiceLayer.FacadeObjects.*;
import ServiceLayer.Response.ResponseT;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.naming.TimeLimitExceededException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static java.lang.System.exit;

public class TruckingService {
    private final DeliveryService deliveryService;
    private final ResourcesService resourcesService;
    private final IService service;

    private static TruckingService instance = null;


    private TruckingService() {
        deliveryService = DeliveryService.getInstance();
        resourcesService = ResourcesService.getInstance();
        service = Service.getInstance();
    }

    public static TruckingService getInstance() {
        if (instance == null)
            instance = new TruckingService();
        return instance;
    }


    /**
     * this method receives an Order and returns and Linked List of items that couldn't been delivered
     * ->  if an item couldn't been delivered, it will be stored in the demands map. will be
     * set to a delivery as soon as a new shift will we set.
     * delivery set priority:
     * 1.   next 7 days, existing deliveries
     * 2.   this week, new deliveries
     * 3.   add a driver to an existing shift this week (will also alert the Employee Manager)
     * 4.   next week's existing TR -> alerts Trucking manager
     * 5.   next week's new TR -> alerts Trucking manager
     * 6.   adds to the Demands list -> alerts Trucking manager
     *
     * @return List of pairs, item and its amount, only item that couldn't been delivered.
     * if all items has been settled to a delivery, returns empty list
     */
    public ResponseT<LinkedList<Pair<Integer, Integer>>> addOrder(FacadeOrder order) {

        String supplier = getSupplierFromOrder(order);
        LinkedList<Pair<Integer, Integer>> left = orderToItemsList(order);
        ResponseT<LinkedList<FacadeTruckingReport>> res = getThisWeekReports();
        if (res.errorOccurred()) {
            return new ResponseT<>(res.getErrorMessage());
        }
        LinkedList<FacadeTruckingReport> thisWeekReports = res.getValue();
        // inserts into the next 7 days reports only

        ResponseT<LinkedList<Pair<Integer, Integer>>> res2 = insertToExistingTR(left, supplier, thisWeekReports);
        if (res2.errorOccurred()) {
            return new ResponseT<>(res2.getErrorMessage());
        }
        left = res2.getValue();
        if (!left.isEmpty()) {
            // creates reports for the next 7 days only, call drivers from home if needed
            ResponseT<LinkedList<Pair<Integer, Integer>>> res3 = null;
            try {
                res3 = createReportsThisWeek(left, supplier);
            } catch (EmployeeException | SQLException e) {
                return new ResponseT<>(e.getMessage());
            }
            if (res3.errorOccurred()) {
                return new ResponseT<>(res3.getErrorMessage());
            }
            left = res3.getValue();
            if (!left.isEmpty()) {
                addNotification("Order number: " + order.getId() + "\nhas been settled to deliveries in more the a week");
                LinkedList<FacadeTruckingReport> everyWeekReports = getActiveTruckingReports().getValue();
                everyWeekReports.addAll(getWaitingTruckingReports().getValue());

                // adds to existing TR from every week
                res2 = insertToExistingTR(left, supplier, everyWeekReports);
                if (res2.errorOccurred()) {
                    return new ResponseT<>(res.getErrorMessage());
                }
                left = res2.getValue();
                if (!left.isEmpty()) {

                    // creates reports for every possible date

                    try {
                        res3 = createReportsEveryWeek(left, supplier);
                    } catch (EmployeeException | SQLException e) {
                        return new ResponseT<>(e.getMessage());
                    }
                    if (res3.errorOccurred()) {
                        return new ResponseT<>(res.getErrorMessage());
                    }
                    left = res3.getValue();
                    if (!left.isEmpty()) {
                        addNotification("order number: " + order.getId() + "" +
                                "\nhas failed to be delivered as a whole. the remain items has been delivered to pool");
                        try {
                            addDemandToPool(left, supplier);
                        } catch (SQLException e) {
                            return new ResponseT<>("problem has been occurred with DB");
                        }
                    }
                }
            }
        }
        return new ResponseT<>(left);
    }


    /**
     * this order must been done in a specific day. all of it
     * if fails, deliveries none of it
     *
     * @param order -  the permanent order need to be settled
     * @return true if succeeded, false other wise
     */
    public ResponseT<Boolean> addPermanentOrder(FacadeOrder order) {
        boolean succeed;
        ResponseT<Boolean> res = canAddFullOrder(order);
        if (res.errorOccurred()) {
            return res;
        }
        succeed = res.getValue();
        if (succeed) {
            LocalDate date = order.getDate();
            LinkedList<Pair<Integer, Integer>> left = orderToItemsList(order);
            String supplier = getSupplierFromOrder(order);
            LinkedList<FacadeTruckingReport> reports = getAvailableTRsByDate(date);

            ResponseT<LinkedList<Pair<Integer, Integer>>> res2 = insertToExistingTR(left, supplier, reports);
            if (res2.errorOccurred()) {
                return new ResponseT<>(res.getErrorMessage());
            }
            left = res2.getValue();
            try {
                res2 = createReportsForDate(left, supplier, date);
            } catch (EmployeeException e) {
                return new ResponseT<>(e.getMessage());

            } catch (SQLException e) {
                return new ResponseT<>(e.getMessage());

            }
            if (res2.errorOccurred()) {
                return new ResponseT<>(res.getErrorMessage());
            }
            left = res2.getValue();
            if (!left.isEmpty()) {
                return new ResponseT<>("for some reason - didn't recognized the needed weight to possible");

            }
        }
        return new ResponseT<>(succeed);
    }


    public ResponseT<LinkedList<TruckingNotifications>> getNotifications() {
        try {
            return new ResponseT<>(deliveryService.getNotifications());
        } catch (SQLException e) {
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<LinkedList<FacadeDriver>> getDrivers() {
        return new ResponseT(resourcesService.getDrivers());
    }

    public ResponseT<LinkedList<FacadeTruck>> getTrucks() {
        return new ResponseT<>(resourcesService.getTrucks());
    }

    public ResponseT<LinkedList<FacadeDemand>> getDemands() {
        return new ResponseT<LinkedList<FacadeDemand>>(deliveryService.getDemands());
    }

    public ResponseT<LinkedList<FacadeTruckingReport>> getActiveTruckingReports() {
        return new ResponseT<>(deliveryService.getActiveTruckingReports());
    }


    public ResponseT<LinkedList<FacadeTruckingReport>> getWaitingTruckingReports() {
        return new ResponseT<>(deliveryService.getWaitingTruckingReports());
    }


    public ResponseT<LinkedList<FacadeTruckingReport>> getOldTruckingReports() {
        return new ResponseT<>(deliveryService.getOldTruckingReports());
    }


    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException, SQLException {
        resourcesService.addTruck(model, licenseNumber, weightNeto, maxWeight);
    }

    public void managerApproveTruckReport(Integer trID) throws TimeLimitExceededException {
        try {
            deliveryService.managerApproveTruckReport(trID);
        } catch (SQLException e) {
            System.out.println((e.getMessage()));

        }
    }

    public void managerCancelTruckReport(Integer trID) throws TimeLimitExceededException {
        FacadeTruckingReport ftr = deliveryService.getTruckReport(trID);
        String ft = ftr.getTruckNumber();
        String fd = ftr.getDriverID();
        LocalDate date = ftr.getDate();
        int shift = turnTimeToShift(ftr.getLeavingHour());
        resourcesService.deleteDriverConstraint(fd, date, shift);
        resourcesService.deleteTruckConstraint(ft, date, shift);
        try {
            deliveryService.managerCancelTruckReport(trID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void handleLeftOvers() {
        ResponseT<LinkedList<FacadeDemand>> res2 = getDemands();
        if (res2.errorOccurred()) {
            System.out.println(res2.getErrorMessage());
            return;
        }
        LinkedList<FacadeDemand> demands = res2.getValue();

        for (FacadeDemand facadeDemand : demands) {

            String supplier = facadeDemand.getSupplier();
            LinkedList<Pair<Integer, Integer>> item = new LinkedList<>();
            item.add(new Pair<>(facadeDemand.getItemID(), facadeDemand.getAmount()));
            ResponseT<LinkedList<FacadeTruckingReport>> res = getThisWeekReports();
            if (res.errorOccurred()) {
                System.out.println(res.getErrorMessage());
                return;
            }
            LinkedList<FacadeTruckingReport> thisWeekReports = res.getValue();
            // inserts into the next 7 days reports only
            ResponseT<LinkedList<Pair<Integer, Integer>>> res3 = insertToExistingTR(item, supplier, thisWeekReports);
            if (res3.errorOccurred()) {
                return;
            }
            item = res3.getValue();
            if (!item.isEmpty()) {

                // creates reports for the next 7 days only, call drivers from home if needed
                ResponseT<LinkedList<Pair<Integer, Integer>>> res4 = null;
                try {
                    res4 = createReportsThisWeek(item, supplier);
                } catch (EmployeeException | SQLException e) {
                    System.out.println((e.getMessage()));
                    return;

                }
                if (res4.errorOccurred()) {
                    System.out.println(res4.getErrorMessage());
                    return;
                }
                item = res4.getValue();
                if (!item.isEmpty()) {
                    LinkedList<FacadeTruckingReport> everyWeekReports = getActiveTruckingReports().getValue();
                    everyWeekReports.addAll(getWaitingTruckingReports().getValue());

                    // adds to existing TR from every week
                    res3 = insertToExistingTR(item, supplier, thisWeekReports);
                    if (res3.errorOccurred()) {
                        System.out.println(res3.getErrorMessage());
                        return;
                    }
                    item = res3.getValue();
                    if (!item.isEmpty()) {

                        // creates reports for every possible date

                        try {
                            res4 = createReportsEveryWeek(item, supplier);
                        } catch (EmployeeException | SQLException e) {
                            System.out.println(e.getMessage());
                            return;
                        }
                        if (res4.errorOccurred()) {
                            System.out.println(res4.getMessage());
                            return;
                        }
                        item = res4.getValue();
                    }
                }
            }
            deliveryService.setItemNewAmount(item.getFirst().getFirst(), item.getFirst().getSecond(), supplier);
        }
        demands = getDemands().getValue();
        if (demands.isEmpty())
            addNotification("all demands in pool has been settled successfully!");
        else
            addNotification("couldn't handle all demands in pool :( ");
    }

    public ResponseT<FacadeTruckingReport> getTruckReport(int id) {
        try {
            return new ResponseT<FacadeTruckingReport>(deliveryService.getTruckReport(id));
        } catch (NoSuchElementException e) {
            return new ResponseT<>("No report found with such ID: " + id);
        }

    }

    public ResponseT<FacadeDeliveryForm> getDeliveryForm(int id) {
        return new ResponseT<FacadeDeliveryForm>(deliveryService.getDeliveryForm(id));
    }

    public ResponseT<LinkedList<FacadeDeliveryForm>> getDeliveryFormsByTruckReport(int report_id) {
        return new ResponseT<>(deliveryService.getTruckReportDeliveryForms(report_id));
    }

    public LinkedList<FacadeDemand> sortDemandsBySite(LinkedList<FacadeDemand> demands) {
        for (int i = demands.size() - 1; i >= 0; i--) {
            FacadeDemand min = demands.get(i);
            int index = i;
            for (int j = i; j >= 0; j--) {
                if (Integer.parseInt(demands.get(j).getSupplier()) < Integer.parseInt(min.getSupplier())) {
                    min = demands.get(j);
                    index = j;
                }
            }
            FacadeDemand temp = demands.get(i);
            demands.set(i, min);
            demands.set(index, temp);
        }
        return demands;
    }

    public void setCompletedTruckReport(int report_id) throws SQLException {
        deliveryService.setCompletedTruckReport(report_id);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< private methods >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * this method tries to insert as much as possible items to the received trucking Reports
     *
     * @param reports       available TruckingReports
     * @param itemsToInsert - List< Pair< itemId, Amount >>
     * @param supplier      -  delivery area of the Order
     * @return items left to insert
     */
    private ResponseT<LinkedList<Pair<Integer, Integer>>>
    insertToExistingTR(LinkedList<Pair<Integer, Integer>> itemsToInsert, String supplier, LinkedList<FacadeTruckingReport> reports) {
        LinkedList<Pair<Integer, Integer>> left = itemsToInsert;
        ResponseT<Integer> res = getDeliveryArea(supplier);
        if (res.errorOccurred()) {
            return new ResponseT<>(res.getErrorMessage());
        }
        int area = res.getValue();
        for (FacadeTruckingReport report : reports) {
            if (!left.isEmpty()) {
                ResponseT<Integer> res2 = getMaxWeight(report);
                if (res2.errorOccurred()) {
                    return new ResponseT<>(res2.getErrorMessage());
                }
                int capacity = res2.getValue();
                LinkedList<Integer> reportAreas = getReportAreas(report);
                //first iterates through reports, tries to add by delivery area

                if (reportAreas.contains(area)) {

                    try {
                        left = deliveryService.insertItemsToTruckReport(left, supplier, capacity, report.getID());
                    } catch (SQLException e) {
                        return new ResponseT<>(e.getMessage());

                    }
                }
            }

        }
        if (!left.isEmpty()) {
            for (FacadeTruckingReport report : reports) {
                ResponseT<Integer> res2 = getMaxWeight(report);
                if (res2.errorOccurred()) {
                    return new ResponseT<>(res2.getErrorMessage());
                }
                int capacity = res2.getValue();
                try {
                    left = deliveryService.insertItemsToTruckReport(left, supplier, capacity, report.getID());
                } catch (SQLException e) {
                    return new ResponseT<>(e.getMessage());

                }
            }
        }
        return new ResponseT<>(left);
    }


    /**
     * @param tr -  truck report to check
     * @return returns min weight truck and driver can handle
     */
    private ResponseT<Integer> getMaxWeight(FacadeTruckingReport tr) {
        return new ResponseT<>(resourcesService.getMaxWeight(tr.getDriverID(), tr.getTruckNumber()));

    }


    /**
     * this method creates new Trucking reports for the received items.
     * preferably sorted by delivery area
     * if not possible-> add new drivers to shift
     * only for the next 7 days
     *
     * @param items    -> pair is < ItemID, Amount>
     * @param supplier -> supplier id
     * @return < left items ,LinkedList of the created TruckingReports>>
     */
    private ResponseT<LinkedList<Pair<Integer, Integer>>>
    createReportsThisWeek(LinkedList<Pair<Integer, Integer>> items, String supplier) throws EmployeeException, SQLException {

        for (LocalDate currDate = LocalDate.now(); currDate.isBefore(LocalDate.now().plusDays(8)) && !items.isEmpty(); currDate = currDate.plusDays(1)) {

            ResponseT<LinkedList<Pair<Integer, Integer>>> res = createReportsForDate(items, supplier, currDate);
            if (res.errorOccurred()) {
                return new ResponseT<>(res.getErrorMessage());
            }
            if (res.getValue() != null) {

                items = res.getValue();
            }
        }
        return new ResponseT<>(items);
    }

    private ResponseT<LinkedList<Pair<Integer, Integer>>>
    createReportsEveryWeek(LinkedList<Pair<Integer, Integer>> items, String supplier) throws EmployeeException, SQLException {
        LocalDate date = getLastShiftDate();
        for (LocalDate currDate = LocalDate.now(); currDate.isBefore(LocalDate.now().plusDays(8)); currDate = currDate.plusDays(1)) {
            ResponseT<LinkedList<Pair<Integer, Integer>>> res = createReportsForDate(items, supplier, currDate);
            if (res.errorOccurred()) {
                return new ResponseT<>(res.getErrorMessage());
            }
            items = res.getValue();
        }
        return new ResponseT<>(items);
    }


    private ResponseT<LinkedList<Pair<Integer, Integer>>>
    createReportsForDate(LinkedList<Pair<Integer, Integer>> items, String supplier, LocalDate date) throws EmployeeException, SQLException {
        boolean finish = false;
        while (true) {
            ResponseT<Pair<Pair<FacadeDriver, FacadeTruck>, Integer>> res = getDriverAndTruckFromExisting(date);
            if (res == null) {
                return new ResponseT<>(items);
            }
            if (res.errorOccurred()) {
                return new ResponseT<>(res.getErrorMessage());
            }
            Pair<Pair<FacadeDriver, FacadeTruck>, Integer> ret = res.getValue();
            Pair<FacadeDriver, FacadeTruck> driverAndTruck = ret.getFirst();
            int shift = ret.getSecond();
            if (driverAndTruck == null) {
                res = getDriverAndTruckFromPool(date);
                if (res.errorOccurred()) {
                    return new ResponseT<>(res.getErrorMessage());
                }
                ret = res.getValue();
                if (res.getValue() == null) {
                    driverAndTruck = null;
                } else {
                    driverAndTruck = ret.getFirst();
                }
            }
            finish = (items.isEmpty() || driverAndTruck == null);
            if (finish)
                break;
            int maxWeight = Math.min(driverAndTruck.getFirst().getLicenseType().getSize(), (driverAndTruck.getSecond().getMaxWeight() - driverAndTruck.getSecond().getWeightNeto()));
            items = deliveryService.createReport(items, driverAndTruck.getFirst().getID(), driverAndTruck.getSecond().getLicenseNumber(), maxWeight, supplier, date, shift);
            resourcesService.addDriverConstraint(driverAndTruck.getFirst().getID(), date, shift);
            resourcesService.addTruckConstraint(driverAndTruck.getSecond().getLicenseNumber(), date, shift);
        }
        return new ResponseT<>(items);

    }

    private HashMap<LocalDate, HashMap<Integer, LinkedList<String>>>
    getDaysAndDrivers() throws IllegalArgumentException, EmployeeException {
        return resourcesService.getDayAndDrivers();
    }

    private LinkedList<Pair<Integer, Integer>> orderToItemsList(FacadeOrder order) {
        LinkedList<Pair<Integer, Integer>> left = new LinkedList<>();
        for (Map.Entry<Integer, Integer> entry : order.getProductMap().entrySet()) {
            left.add(new Pair<>(entry.getKey(), entry.getValue()));
        }
        return left;
    }


    /**
     * this method check whether we can deliver the whole order in the wanted date. checks by overall weight
     * to the day's overall weight
     *
     * @param order already has the wanted date
     * @return true if can, false if cannot
     */
    private ResponseT<Boolean> canAddFullOrder(FacadeOrder order) {
        LocalDate date = order.getDate();
        ResponseT<Integer> val = getDayLeftWeight(date);
        if (val.errorOccurred()) {
            return new ResponseT<>(val.getErrorMessage());
        }
        int left = val.getValue();
        int totalWeight = getOrderTotalWeight(order);
        return new ResponseT<>(totalWeight <= left);
    }

    /**
     * @param date -  date to check
     * @return overall weight can add to overall deliveries in this date includes the not created TRs
     */
    private ResponseT<Integer> getDayLeftWeight(LocalDate date) {
        LinkedList<FacadeTruckingReport> reports = deliveryService.getTruckReportsByDate(date);
        int total = 0;
        for (FacadeTruckingReport ftr : reports) {
            ResponseT<Integer> sum = getReportLeftWeight(ftr);
            if (sum.errorOccurred()) {
                return sum;
            }
            total += sum.getValue();
        }
        try {
            ResponseT<Integer> res = getPossibleWeightByDate(date);
            if (res.errorOccurred()) {
                return new ResponseT<>(res.getErrorMessage());
            }
            total += res.getValue();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ResponseT<>(total);
    }


    private ResponseT<Integer> getPossibleWeightByDate(LocalDate date) throws EmployeeException, SQLException {
        LinkedList<FacadeTruckingReport> reports = getAvailableTRsByDate(date);
        ResponseT<Pair<LinkedList<String>, LinkedList<String>>> res = getBusyTrucksByDate(date);
        if (res.errorOccurred()) {
            return new ResponseT<>(res.getErrorMessage());
        }
        return new ResponseT<>(resourcesService.getPossibleWeightByDate(date, res.getValue()));

    }

    private int turnTimeToShift(LocalTime leavingHour) {
        if (leavingHour.equals(LocalTime.of(14, 00)))
            return 1;
        else return 0;
    }


    /**
     * @param date
     * @return pair < < driver, truck> , Shift>
     */
    private ResponseT<Pair<Pair<FacadeDriver, FacadeTruck>, Integer>> getDriverAndTruckFromExisting(LocalDate date) throws EmployeeException {
        ResponseT<Pair<LinkedList<String>, LinkedList<String>>> res = getBusyTrucksByDate(date);
        if (res.errorOccurred()) {
            return new ResponseT<>(res.getErrorMessage());
        }
        Pair<Pair<FacadeDriver, FacadeTruck>, Integer> driverAndTruck = resourcesService.findDriverAndTruckForDateFromExisting(date, res.getValue());
        if (driverAndTruck == null)
            return null;
        return new ResponseT<>(driverAndTruck);
    }

    private ResponseT<Pair<Pair<FacadeDriver, FacadeTruck>, Integer>> getDriverAndTruckFromPool(LocalDate date) throws EmployeeException, SQLException {
        ResponseT<Pair<LinkedList<String>, LinkedList<String>>> res = getBusyTrucksByDate(date);
        if (res.errorOccurred()) {
            return new ResponseT<>(res.getErrorMessage());
        }
        Pair<Pair<FacadeDriver, FacadeTruck>, Integer> val = resourcesService.findDriverAndTruckForDateFromPool(date, res.getValue());
        if (val == null) {
            return null;
        }
        return new ResponseT<>(val);
    }

    /**
     * inserts the items into demands pool
     *
     * @param items    -> itemId, Amount
     * @param supplier ->  supplier id
     */
    private void addDemandToPool(LinkedList<Pair<Integer, Integer>> items, String supplier) throws SQLException {
        deliveryService.addDemandToPool(items, supplier);
    }

    private LinkedList<FacadeTruckingReport> getAvailableTRsByDate(LocalDate date) {
        return deliveryService.getAvailableTRsByDate(date);
    }

    private String getSupplierFromOrder(FacadeOrder order) {
        return order.getSupplier().getSc().getId();
    }

    private ResponseT<LinkedList<FacadeTruckingReport>> getThisWeekReports() {
        LinkedList<FacadeTruckingReport> allReports = getActiveTruckingReports().getValue();
        LinkedList<FacadeTruckingReport> waiting = getWaitingTruckingReports().getValue();
        allReports.addAll(waiting);
        LinkedList<FacadeTruckingReport> thisWeekReports = new LinkedList<>();
        // filters the reports, remains only the next 7 day's reports
        for (FacadeTruckingReport ftr : allReports) {
            if (ftr.getDate().isBefore(LocalDate.now().plusDays(7))) {
                thisWeekReports.add(ftr);
            }
        }
        return new ResponseT<>(thisWeekReports);
    }

    private ResponseT<Pair<LinkedList<String>, LinkedList<String>>> getBusyTrucksByDate(LocalDate date) {
        return new ResponseT<>(deliveryService.getBusyTrucksByDate(date));
    }

    private ResponseT<Integer> getReportLeftWeight(FacadeTruckingReport report) {
        try {
            int curr = deliveryService.getTruckReportCurrentWeight(report.getID());
            ResponseT<Integer> res = getMaxWeight(report);
            if (res.errorOccurred()) {
                return res;
            }
            int max = res.getValue();
            return new ResponseT<>(max - curr);
        } catch (NoSuchElementException e) {
            return new ResponseT(e.getMessage());
        }
    }

    private void addNotification(String content) {
        try {
            deliveryService.addNotification(content);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    private int getOrderTotalWeight(FacadeOrder order) {
        Map<Integer, Integer> items = order.getProductMap();
        int total = 0;
        for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
            total += deliveryService.getItemTotalWeight(entry.getKey(), entry.getValue());
        }
        return total;
    }

    private LinkedList<Integer> getReportAreas(FacadeTruckingReport report) {
        return deliveryService.getTruckReportDeliveryAreas(report.getID());
    }


    private LocalDate getLastShiftDate() {
        return ShiftController.getInstance().getLastShiftDate();
    }


    private ResponseT<Integer> getDeliveryArea(String supplier) {
        ResponseT<FacadeSupplier> res = service.getSupplier("" + supplier);
        if (res.errorOccurred()) {
            return new ResponseT<>(res.getErrorMessage());
        }
        return new ResponseT<>(res.getValue().getSc().getArea());
    }


    private ResponseT<FacadeItem> getItem(int id, int supplier) {
        ResponseT<FacadeItem> res = service.getItem(id);
        if (res.errorOccurred()) {
            return new ResponseT<>(res.getErrorMessage());
        }
        return new ResponseT<>(res.getValue());
    }


    public String getItemName(int itemID) {
        ResponseT<FacadeItem> res = service.getItem(itemID);
        if (res.errorOccurred()) {
            return res.getErrorMessage();
        }
        return res.getValue().getName();
    }

    public String getSupplierName(String supplier) {
        ResponseT<FacadeSupplier> res = service.getSupplier("" + supplier);
        if (res.errorOccurred()) {
            return res.getErrorMessage();
        }
        return res.getValue().getSc().getName();
    }


    public void upload() throws SQLException {
        deliveryService.upload();
        HashMap driver_cons = deliveryService.getDriverConstraintsFromUpload();
        HashMap trucks_cons = deliveryService.getTruckConstraintsFromUpload();
        resourcesService.upload(driver_cons, trucks_cons);
    }

    public void putInitialTestState() {
        try {
            DalTruckingNotificationController notificationController = DalTruckingNotificationController.getInstance();
            DalDemandController dalDemandController = DalDemandController.getInstance();
            DalItemsOnDFController itemsOnDF = DalItemsOnDFController.getInstance();
            DalDeliveryFormController delivery = DalDeliveryFormController.getInstance();
            DalTruckingReportController truckingReport = DalTruckingReportController.getInstance();

            addTruck("Mercedes", "62321323", 2000, 12000);

            addTruck("Man", "1231231", 1500, 8000);
            addTruck("Volvo", "123", 1000, 10000);
            addTruck("Volvo", "12121", 1000, 14000);
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }
}
