package ServiceLayer;

import BusinessLayer.EmployeePackage.EmployeeException;
import BusinessLayer.EmployeePackage.ShiftPackage.ShiftController;
import BusinessLayer.InventoryPackage.Item;
import BusinessLayer.Notification;
import BusinessLayer.SuppliersPackage.OrderPackage.Order;
import BusinessLayer.TruckingNotifications;
import BusinessLayer.TruckingPackage.DeliveryPackage.DeliveryForm;
import InfrastructurePackage.Pair;
import ServiceLayer.FacadeObjects.*;
import ServiceLayer.Response.Response;
import ServiceLayer.Response.ResponseT;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.naming.TimeLimitExceededException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static java.lang.System.exit;

public class TruckingService {
    private DeliveryService deliveryService;
    private ResourcesService resourcesService;
    private IService service;

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

 /*   TODO - things to check for debbuging
        1. automate delivery set by getting order ( can be done alone)
        2. addDriver to shift function
        3. handleLeft over -  need to set new demands when no drivers are settled. and then create new shift
        4. approve orders by manager
        5. cancel orders by manager
        6. need to check when adding to existing TR, adds to existing DF and creates new one
        7. need to check properly the get driver and truck method

      */


// TODO need to add exceptions
    // TODO need to handle responses

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
     * @return List of pairs, item and its amount, only item that couldn't been delivered.
     * if all items has been settled to a delivery, returns empty list
     */
    public ResponseT<  LinkedList<Pair<Integer, Integer>> > addOrder(FacadeOrder order)  {

        int supplier = getSupplierFromOrder(order);
        LinkedList<Pair<Integer, Integer>> left = orderToItemsList(order);
        ResponseT<LinkedList<FacadeTruckingReport>> res = getThisWeekReports();
        if (res.errorOccurred()){
            return new ResponseT<>(res.getErrorMessage());
        }
        LinkedList<FacadeTruckingReport> thisWeekReports = res.getValue();
        // inserts into the next 7 days reports only

        ResponseT<LinkedList<Pair<Integer, Integer>>> res2 = insertToExistingTR(left, supplier, thisWeekReports);
        if (res.errorOccurred()){
            return new ResponseT<>(res.getErrorMessage());
        }
        left = res2.getValue();
        if (!left.isEmpty()) {

            // creates reports for the next 7 days only, call drivers from home if needed
            ResponseT<LinkedList<Pair<Integer, Integer>>> res3 = createReportsThisWeek(left, supplier);
            if (res3.errorOccurred()){
                return new ResponseT<>(res.getErrorMessage());
            }
            left  =res3.getValue();
            if (!left.isEmpty()) {
                addNotification("Order number: " + order.getId() + "\nhas been settled to deliveries in more the a week");
                LinkedList<FacadeTruckingReport> everyWeekReports = getActiveTruckingReports().getValue();
                everyWeekReports.addAll(getWaitingTruckingReports().getValue());

                // adds to existing TR from every week
                res2 = insertToExistingTR(left, supplier, everyWeekReports);
                if (res2.errorOccurred()){
                    return new ResponseT<>(res.getErrorMessage());
                }
                left= res2.getValue();
                if (!left.isEmpty()) {

                    // creates reports for every possible date

                    res3=  createReportsEveryWeek(left, supplier);
                    if (res3.errorOccurred()){
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
     * @param order -  the permanent order need to be settled
     * @return true if succeeded, false other wise
     */
    public ResponseT<Boolean> addPermanentOrder(FacadeOrder order) {
        boolean succeed ;
        ResponseT<Boolean> res = canAddFullOrder(order);
        if (res.errorOccurred()){
            return res;
        }
        succeed  = res.getValue();
        if (succeed){
            LocalDate date = order.getDate();
            LinkedList<Pair<Integer, Integer>> left = orderToItemsList(order);
            int supplier = getSupplierFromOrder(order);
            LinkedList<FacadeTruckingReport> reports = getAvailableTRsByDate(date);

            ResponseT<LinkedList<Pair<Integer, Integer>>> res2 = insertToExistingTR(left, supplier, reports);
            if (res2.errorOccurred()){
                return new ResponseT<>(res.getErrorMessage());
            }
            left = res2.getValue();
            res2 =  createReportsForDate(left, supplier, date);
            if (res2.errorOccurred()){
                return new ResponseT<>(res.getErrorMessage());
            }
            left = res2.getValue();
            if (!left.isEmpty()){
                return new ResponseT<>("for some reason - didn't recognized the needed weight to possible");

            }
        }
        return new ResponseT<>( succeed);
    }



    public ResponseT< LinkedList<TruckingNotifications> > getNotifications(){

        return new ResponseT<>( deliveryService.getNotifications());
    }

    public ResponseT<  LinkedList<FacadeDriver> >getDrivers(){
        return new ResponseT(resourcesService.getDrivers());
    }

    public ResponseT< LinkedList<FacadeTruck> > getTrucks(){
        return new ResponseT<>(resourcesService.getTrucks());
    }

    public ResponseT<  LinkedList<FacadeDemand> > getDemands(){
        return new ResponseT<LinkedList<FacadeDemand>>(deliveryService.getDemands());
    }

    public ResponseT<  LinkedList<FacadeTruckingReport> > getActiveTruckingReports(){
        return  new ResponseT<>(deliveryService.getActiveTruckingReports());
    }


    public ResponseT<  LinkedList<FacadeTruckingReport> > getWaitingTruckingReports(){
        return  new ResponseT<>(deliveryService.getWaitingTruckingReports());
    }


    public ResponseT<  LinkedList<FacadeTruckingReport >> getOldTruckingReports(){
        return  new ResponseT<>(deliveryService.getOldTruckingReports());
    }


    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException, SQLException {
        resourcesService.addTruck(model, licenseNumber, weightNeto, maxWeight);
    }
    public void managerApproveTruckReport(Integer trID) throws TimeLimitExceededException {
        deliveryService.managerApproveTruckReport(trID);
    }
    public void managerCancelTruckReport(Integer trID) throws TimeLimitExceededException {
        FacadeTruckingReport ftr= deliveryService.getTruckReport(trID);
        String ft=ftr.getTruckNumber();
        String fd=ftr.getDriverID();
        LocalDate date=ftr.getDate();
        int shift=turnTimeToShift(ftr.getLeavingHour());
        resourcesService.deleteDriverConstarint(fd,date,shift);
        resourcesService.deleteTruckConstarint(ft,date,shift);
        deliveryService.managerCancelTruckReport(trID);

    }


    // TODO - employees should call this function
    public void handleLeftOvers() {
        ResponseT<LinkedList<FacadeDemand>> res2 = getDemands();
        if (res2.errorOccurred()){
            return ;
        }
        LinkedList<FacadeDemand> demands = res2.getValue();

        for (FacadeDemand facadeDemand : demands){

            int supplier    = facadeDemand.getSupplier();
            LinkedList< Pair<Integer,Integer>> item = new LinkedList<>();
            item.add (new Pair<>(facadeDemand.getItemID(), facadeDemand.getAmount()));
            ResponseT<LinkedList<FacadeTruckingReport>> res = getThisWeekReports();
            if (res.errorOccurred()){
                System.out.println(res.getErrorMessage());
                return;
            }
            LinkedList<FacadeTruckingReport> thisWeekReports =  res.getValue();
            // inserts into the next 7 days reports only
            ResponseT<LinkedList<Pair<Integer, Integer>>> res3 = insertToExistingTR(item, supplier, thisWeekReports);
            if (res3.errorOccurred()){
                return;
            }
            item = res3.getValue();
            if (!item.isEmpty()) {

                // creates reports for the next 7 days only, call drivers from home if needed
                ResponseT<LinkedList<Pair<Integer, Integer>>> res4 = createReportsThisWeek(item, supplier);
                if (res4.errorOccurred()){
                    return;
                }
                item = res4.getValue();
                if (!item.isEmpty()) {
                    LinkedList<FacadeTruckingReport> everyWeekReports = getActiveTruckingReports().getValue();
                    everyWeekReports.addAll(getWaitingTruckingReports().getValue());

                    // adds to existing TR from every week
                    res3 = insertToExistingTR(item, supplier, thisWeekReports);
                    if (res3.errorOccurred()){
                        return;
                    }
                    item = res3.getValue();
                    if (!item.isEmpty()) {

                        // creates reports for every possible date

                        res4 =  createReportsEveryWeek(item, supplier);
                        if (res4.errorOccurred()){
                            return;
                        }
                        item = res4.getValue();
                    }


                }
            }
            deliveryService.setItemNewAmount(item.getFirst().getFirst(), item.getFirst().getSecond() , supplier);


        }
        demands = getDemands().getValue();
        if (demands.isEmpty())
            addNotification("all demands in pool has been settled successfully!");
        else
            addNotification("couldn't handle all demands in pool :( ");
    }



    public ResponseT< FacadeTruckingReport> getTruckReport(int id){
        try {
            return new ResponseT<FacadeTruckingReport>(deliveryService.getTruckReport(id));
        }catch (NoSuchElementException e){
            return new ResponseT<>("No report found with such ID: " + id);
        }

    }
    public ResponseT< FacadeDeliveryForm> getDeliveryForm(int id){
        return  new ResponseT<FacadeDeliveryForm>( deliveryService.getDeliveryForm(id) );
    }

    public ResponseT< LinkedList<FacadeDeliveryForm>> getDeliveryFormsByTruckReport(int report_id) {
        return new ResponseT<>(deliveryService.getTruckReportDeliveryForms(report_id));
    }

    public LinkedList<FacadeDemand> sortDemandsBySite(LinkedList<FacadeDemand> demands) {
        for (int i = demands.size() - 1; i >= 0; i--) {
            FacadeDemand min = demands.get(i);
            int index = i;
            for (int j = i; j >= 0; j--) {
                if (demands.get(j).getSupplier() < min.getSupplier()) {
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

    public void setCompletedTruckReport(int report_id){
        deliveryService.setCompletedTruckReport(report_id);
    }

    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<< private methods >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    /**
     * this method tries to insert as much as possible items to the received trucking Reports
     * @param reports available TruckingReports
     * @param itemsToInsert - List< Pair< itemId, Amount >>
     * @param supplier -  delivery area of the Order
     * @return items left to insert
     */
    private ResponseT< LinkedList<Pair<Integer, Integer>> >
    insertToExistingTR(LinkedList<Pair<Integer, Integer>> itemsToInsert,int supplier, LinkedList<FacadeTruckingReport> reports){
        LinkedList<Pair<Integer, Integer>> left = itemsToInsert;
        int area = getDeliveryArea(supplier);
        for (FacadeTruckingReport report : reports){
            if (!left.isEmpty()) {
                ResponseT<Integer> res = getMaxWeight(report);
                if (res.errorOccurred()){
                    return new ResponseT<>(res.getErrorMessage());
                }
                int capacity = res.getValue();
                LinkedList<Integer> reportAreas = getReportAreas(report);
                //first iterates through reports, tries to add by delivery area

                if (reportAreas.contains(area)) {

                    left = deliveryService.insertItemsToTruckReport(left, supplier , capacity, report.getID());
                }
            }

        }
        if (! left.isEmpty()) {
            for (FacadeTruckingReport report : reports) {
                ResponseT<Integer> res = getMaxWeight(report);
                if (res.errorOccurred()){
                    return new ResponseT<>(res.getErrorMessage());
                }
                int capacity = res.getValue();
                left = deliveryService.insertItemsToTruckReport(left, supplier, capacity, report.getID());
            }
        }
        return new ResponseT<>(left);
    }


    /**
     *
     * @param tr -  truck report to check
     * @return returns min weight truck and driver can handle
     */
    private ResponseT<Integer> getMaxWeight(FacadeTruckingReport tr){
       return new ResponseT<>(resourcesService.getMaxWeight(tr.getDriverID(),tr.getTruckNumber()));

    }


    /**
     *      this method creates new Trucking reports for the received items.
     *      preferably sorted by delivery area
     *      if not possible-> add new drivers to shift
     *      only for the next 7 days
     *
     * @param items -> pair is < ItemID, Amount>
     * @param supplier -> supplier id
     *
     * @return < left items ,LinkedList of the created TruckingReports>>
     */
    private ResponseT< LinkedList<Pair<Integer,Integer>>>
    createReportsThisWeek(    LinkedList<Pair<Integer,Integer>>  items , int supplier ){

        for (LocalDate currDate  = LocalDate.now(); currDate.isBefore(LocalDate.now().plusDays(8)); currDate = currDate.plusDays(1)){

            ResponseT<LinkedList<Pair<Integer, Integer>>> res = createReportsForDate(items, supplier, currDate);
            if (res.errorOccurred()){
                return new ResponseT<>(res.getErrorMessage());
            }
            items =res.getValue();
        }
        return new ResponseT<>( items);
    }

    private ResponseT< LinkedList<Pair<Integer,Integer>>>
    createReportsEveryWeek( LinkedList<Pair<Integer,Integer>>  items , int supplier  ){
        LocalDate date = getLastShiftDate();
        for (LocalDate currDate  = LocalDate.now(); currDate.isBefore(LocalDate.now().plusDays(8)); currDate = currDate.plusDays(1)){
            ResponseT<LinkedList<Pair<Integer, Integer>>> res = createReportsForDate(items, supplier, currDate);
            if (res.errorOccurred()){
                return new ResponseT<>(res.getErrorMessage());
            }
            items =res.getValue();
        }
        return new ResponseT<>( items);
    }


    private ResponseT< LinkedList<Pair<Integer,Integer>>>
    createReportsForDate( LinkedList<Pair<Integer,Integer>>  items , int supplier , LocalDate date ){
        boolean finish = false;
        while (true){
            ResponseT<Pair<Pair<FacadeDriver, FacadeTruck>, Integer>> res = getDriverAndTruckFromExisting(date);
            if (res.errorOccurred()){
                return new ResponseT<>(res.getErrorMessage());
            }
            Pair<Pair<FacadeDriver, FacadeTruck>, Integer> ret =res.getValue();
            Pair<FacadeDriver, FacadeTruck> driverAndTruck = ret.getFirst();
            int shift = ret.getSecond();
            if (driverAndTruck == null){
                res = getDriverAndTruckFromPool(date);
                if (res.errorOccurred()){
                    return new ResponseT<>(res.getErrorMessage());
                }
                ret = res.getValue();
                driverAndTruck = ret.getFirst();

            }
            finish = (items.isEmpty() || driverAndTruck ==  null);
            if (finish)
                break;
            int maxWeight = Math.min(driverAndTruck.getFirst().getLicenseType().getSize(),(driverAndTruck.getSecond().getMaxWeight() - driverAndTruck.getSecond().getWeightNeto() ));
            items = deliveryService.createReport(items, driverAndTruck.getFirst().getID(), driverAndTruck.getSecond().getLicenseNumber() ,maxWeight,  supplier, date,shift);
            resourcesService.addDriverConstarint(driverAndTruck.getFirst().getID(),date,shift);
            resourcesService.addTruckConstraint(driverAndTruck.getSecond().getLicenseNumber(),date,shift);
        }
        return new ResponseT<>( items);

    }
    private  HashMap<LocalDate, HashMap<Integer, LinkedList<String>>>
    getDaysAndDrivers() throws IllegalArgumentException {
        return resourcesService.getDayAndDrivers();
    }

    private LinkedList<Pair<Integer, Integer>> orderToItemsList(FacadeOrder order) {
        LinkedList<Pair<Integer,Integer>> left = new LinkedList<>();
        for (Map.Entry<Integer,Integer> entry : order.getProductMap().entrySet()){
            left.add(new Pair<>(entry.getKey(),entry.getValue()));
        }
        return left;
    }



    /**
     * this method check whether we can deliver the whole order in the wanted date. checks by overall weight
     * to the day's overall weight
     * @param order already has the wanted date
     * @return true if can, false if cannot
     */
    private ResponseT<Boolean> canAddFullOrder(FacadeOrder order){
        LocalDate date =  order.getDate();
        ResponseT<Integer> val = getDayLeftWeight(date);
        if(val.errorOccurred()){
            return new ResponseT<>(val.getErrorMessage());
        }
        int left = val.getValue();
        int totalWeight = getOrderTotalWeight(order);
        return new ResponseT<> ( totalWeight <= left);
    }

    /**
     *
     * @param date -  date to check
     * @return overall weight can add to overall deliveries in this date includes the not created TRs
     */
    private ResponseT<Integer> getDayLeftWeight(LocalDate date){
        LinkedList<FacadeTruckingReport> reports = deliveryService.getTruckReportsByDate(date);
        int total = 0;
        for (FacadeTruckingReport ftr : reports) {
            ResponseT<Integer> sum = getReportLeftWeight(ftr);
            if (sum.errorOccurred()){
                return  sum;
            }
            total += sum.getValue();
        }
        try {
            ResponseT<Integer> res = getPossibleWeightByDate(date);
            if (res.errorOccurred()){
                return new ResponseT<>(res.getErrorMessage());
            }
            total += res.getValue();
        } catch (EmployeeException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();

            //TODo need to implement here
        }
        return new ResponseT<>(total);

    }


    private ResponseT<Integer> getPossibleWeightByDate(LocalDate date) throws EmployeeException, SQLException {
        LinkedList<FacadeTruckingReport> reports = getAvailableTRsByDate(date);
        ResponseT<Pair<LinkedList<String>, LinkedList<String>>> res = getBusyTrucksByDate(date);
        if (res.errorOccurred()){
            return new ResponseT<>(res.getErrorMessage());
        }
        return new ResponseT<>( resourcesService.getPossibleWeightByDate(date,res.getValue()));

        // TODO - employees need to make a new method, returns boolean and do no insert into shift
    }
    private int turnTimeToShift(LocalTime leavingHour) {
        if (leavingHour.equals(LocalTime.of(14,00)))
            return 1;
        else return  0;
    }



    /**
     *
     * @param date
     * @return pair < < driver, truck> , Shift>
     */
    private ResponseT< Pair <Pair<FacadeDriver, FacadeTruck>,Integer >> getDriverAndTruckFromExisting (LocalDate date){
        ResponseT<Pair<LinkedList<String>, LinkedList<String>>> res = getBusyTrucksByDate(date);
        if (res.errorOccurred()){
            return new ResponseT<>(res.getErrorMessage());
        }
        return new ResponseT<>( resourcesService.findDriverAndTruckForDateFromExisting(date,res.getValue()));
    }

    private ResponseT< Pair <Pair<FacadeDriver, FacadeTruck>,Integer >>  getDriverAndTruckFromPool (LocalDate date){
        ResponseT<Pair<LinkedList<String>, LinkedList<String>>> res = getBusyTrucksByDate(date);
        if (res.errorOccurred()){
            return new ResponseT<>(res.getErrorMessage());
        }
        return new ResponseT<>( resourcesService.findDriverAndTruckForDateFromPool(date,res.getValue()));
    }

    /**
     * inserts the items into demands pool
     * @param items -> itemId, Amount
     * @param  supplier  ->  supplier id
     */
    private void addDemandToPool(LinkedList<Pair<Integer, Integer>> items , int supplier) throws SQLException {
        deliveryService.addDemandToPool(items, supplier);
    }


    private LinkedList<FacadeTruckingReport> getAvailableTRsByDate(LocalDate date) {
        return deliveryService.getAvailableTRsByDate(date);
    }
    private int getSupplierFromOrder(FacadeOrder order) {
        return order.getSupplier().getSc().getCompanyNumber();
    }

    private ResponseT< LinkedList<FacadeTruckingReport> > getThisWeekReports() {
        LinkedList<FacadeTruckingReport> allReports = getActiveTruckingReports().getValue();
        LinkedList<FacadeTruckingReport> waiting = getWaitingTruckingReports().getValue();
        allReports.addAll(waiting);
        LinkedList<FacadeTruckingReport> thisWeekReports = new LinkedList<>();
        // filters the reports, remains only the next 7 day's reports
        for (FacadeTruckingReport ftr: allReports){
            if (ftr.getDate().isBefore(LocalDate.now().plusDays(7))){
                thisWeekReports.add(ftr);
            }
        }
        return new ResponseT<>( thisWeekReports);
    }

    private ResponseT< Pair<LinkedList<String>,LinkedList<String>> > getBusyTrucksByDate(LocalDate date)
    {
        return new ResponseT<>( deliveryService.getBusyTrucksByDate(date));
    }

    private ResponseT< Integer> getReportLeftWeight(FacadeTruckingReport report) {
        try {
            int curr = deliveryService.getTruckReportCurrentWeight(report.getID());
            ResponseT<Integer> res = getMaxWeight(report);
            if (res.errorOccurred()){
                return res;
            }
            int max = res.getValue();
            return new ResponseT<>( max - curr);
        }catch (NoSuchElementException e){
            return new ResponseT(e.getMessage());
        }

    }

    private void addNotification(String content) {
        deliveryService.addNotification(content);
    }



    private int getOrderTotalWeight(FacadeOrder order) {
        Map<Integer, Integer> items = order.getProductMap();
        int total = 0;
        for (Map.Entry<Integer,Integer> entry : items.entrySet()){
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


    private int getDeliveryArea(int supplier ){
        ResponseT<FacadeSupplier> res = service.getSupplier(""+ supplier);
        if(res.errorOccurred()){
            return 0;
        }
        return res.getValue().getSc().getArea();
    }


    private FacadeItem getItem(int id, int supplier){
        ResponseT<FacadeItem> res = service.getItem(id);
        if(res.errorOccurred()){
            return null;
        }
        return res.getValue();
    }



    public String getItemName(int itemID) {
        ResponseT<FacadeItem> res = service.getItem(itemID);
        if(res.errorOccurred()){
            return res.getErrorMessage();
        }
        return res.getValue().getName();
    }

    public String  getSupplierName(int supplier) {
        ResponseT<FacadeSupplier> res = service.getSupplier("" + supplier);
        if(res.errorOccurred()){
            return res.getErrorMessage();
        }
        return res.getValue().getSc().getName();

    }


    public void upload() throws SQLException {
        deliveryService.upload();
        HashMap driver_cons =  deliveryService.getDriverConstraintsFromUpload();
        HashMap trucks_cons = deliveryService.getTruckConstraintsFromUpload();
        resourcesService.upload(driver_cons,trucks_cons);
    }



    public void putInitialTestState() {


        try {
            addTruck("Mercedes", "62321323", 2000, 12000);

            addTruck("Man", "1231231", 1500, 8000);
            addTruck("Volvo", "123", 1000, 10000);
            addTruck("Volvo", "12121", 1000, 14000);
        }catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }

    }






















  /*  public void createTruckingReport() throws SQLException {
        int id = deliveryService.createTruckingReport();
        currTR = new FacadeTruckingReport(id);

    }


    public FacadeTruck chooseTruck(String truck, LocalDate date, LocalTime shift) throws NoSuchElementException, IllegalStateException, SQLException {
        int weightToDeliver = deliveryService.getWeightOfCurrReport();
        FacadeTruck ft = new FacadeTruck(resourcesService.chooseTruck(truck, date, turnTimeToShift(shift)));
        if (weightToDeliver + ft.getWeightNeto() > ft.getMaxWeight()) {
            throw new IllegalStateException("Total Weight is: " + (weightToDeliver + ft.getWeightNeto()) + " But Truck can carry: " + ft.getMaxWeight());
        }

        currTR.setTruckNumber(ft.getLicenseNumber());
        deliveryService.chooseTruck(truck);
        return ft;
    }


    public FacadeDemand addDemandToReport(int item_number, int supplyAmount, int siteID) throws IllegalStateException, IllegalArgumentException, SQLException {
        return deliveryService.addDemandToReport(item_number, supplyAmount, siteID);
    }


    public FacadeDriver chooseDriver(String driver, LocalDate date, LocalTime shift) throws IllegalStateException, NoSuchElementException, SQLException {
        FacadeDriver fd = resourcesService.chooseDriver(driver, date, turnTimeToShift(shift));

        FacadeTruck ft = null;
        for (FacadeTruck ft2 : resourcesService.getTrucks()) {
            if (ft2.getLicenseNumber().equals(currTR.getTruckNumber()))
                ft = ft2;
        }
        if (ft == null) throw new NoSuchElementException("the truck's license Number does not exist");
        if (fd.getLicenseType().getSize() < ft.getWeightNeto() + deliveryService.getWeightOfCurrReport())
            throw new IllegalStateException("Driver cant handle this delivery");

        deliveryService.chooseDriver(driver);
        currTR.setDriverID(fd.getID());
        return fd;

    }

    public void chooseLeavingHour(LocalTime leavingHour) throws IllegalArgumentException, SQLException {
        deliveryService.chooseLeavingHour(leavingHour);
    }

    public FacadeTruckingReport getCurrTruckReport() {
        return deliveryService.getCurrTruckingReport();
    }

    public void saveReport() throws SQLException {

        deliveryService.saveReport();
        FacadeTruckingReport tr = deliveryService.getCurrTruckingReport();

        resourcesService.saveReport(tr.getDate(), turnTimeToShift(tr.getLeavingHour()));

    }

    public void continueAddDemandToReport(int itemID, int amount, int siteID) throws SQLException {
        deliveryService.continueAddDemandToReport(itemID, amount, siteID);
    }

    public FacadeTruckingReport getTruckReport(int trNumber) {
        return deliveryService.getTruckReport(trNumber);
    }

    public FacadeDeliveryForm getDeliveryForm(int dfNumber, int trNumber) throws IllegalArgumentException, NoSuchElementException {
        return deliveryService.getDeliveryForm(dfNumber, trNumber);
    }

    public void removeDestination(int site) throws NoSuchElementException, SQLException {
        deliveryService.removeDestination(site);
    }

    public void removeItemFromReport(FacadeDemand demand, int amount) throws SQLException {
        deliveryService.removeItemFromReport(demand, amount);
    }
    public void removeItemFromPool(int item) throws NoSuchElementException, SQLException {
        deliveryService.removeItemFromPool(item);
    }

    public int getWeightOfCurrReport() {
        return deliveryService.getWeightOfCurrReport();
    }




    public LinkedList<FacadeDemand> getItemsOnTruck() throws SQLException {
        return deliveryService.getItemsOnTruck();
    }

    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException, SQLException {
        resourcesService.addTruck(model, licenseNumber, weightNeto, maxWeight);
    }

    public void addDriver(String ID, String name, Driver.License licenseType) throws SQLException {
        resourcesService.addDriver(ID, name, licenseType);
    }

    public void addSite(String city, int deliveryArea, String phoneNumber, String contactName, String name) throws SQLException {
        deliveryService.addSite(city, deliveryArea, phoneNumber, contactName, name);

    }

    public void addItem(double weight, String name, int siteID) throws NoSuchElementException, KeyAlreadyExistsException, SQLException {
        deliveryService.addItem(weight, name, siteID);
    }

    public void displaySites() {
        deliveryService.displaySites();
    }

    public double getItemWeight(int itemID) {
        return deliveryService.getItemWeight(itemID);
    }

    public LinkedList<FacadeDemand> showDemands() throws NoSuchElementException, SQLException {
        return deliveryService.showDemands();
    }

    public String getItemName(int itemID) {
        return deliveryService.getItemName(itemID);
    }

    public String getSiteName(int site) {
        return deliveryService.getSiteName(site);
    }



    public LinkedList<FacadeDriver> getDrivers() {
        return resourcesService.getDrivers();
    }

    public LinkedList<FacadeTruck> getTrucks() {
        return resourcesService.getTrucks();
    }





    public LinkedList<FacadeSite> getCurrentSites() {
        return deliveryService.getCurrentSites();
    }

    public LinkedList<FacadeDemand> getCurrentDemandsBySite(FacadeSite site) throws SQLException {
        return deliveryService.getCurrentDemands(site);

    }

    public LinkedList<FacadeItem> getAllItems() {
        return deliveryService.getAllItems();
    }

    public LinkedList<FacadeSite> getAllSites() throws NoSuchElementException {
        return deliveryService.getAllSites();
    }

    public void addDemandToSystem(int itemId, int site, int amount) throws SQLException {
        deliveryService.addDemandToSystem(itemId, site, amount);
    }

    public LinkedList<FacadeTruckingReport> getActiveTruckingReports() {
        return deliveryService.getActiveTruckingReports();
    }

    public LinkedList<FacadeDeliveryForm> getDeliveryForms(int trID) {
        return deliveryService.getDeliveryForms(trID);
    }

    public void updateDeliveryFormRealWeight(int trID, int dfID, int weight) throws IllegalStateException, SQLException {

        deliveryService.updateDeliveryFormRealWeight(trID, dfID, weight);
        LinkedList<FacadeTruck> trucks = resourcesService.getTrucks();
        FacadeTruck ft = null;
        for (FacadeTruck facadeTruck : trucks) {

            if (facadeTruck.getLicenseNumber().equals(getTruckReport(trID).getTruckNumber()))//should not be currTR
                ft = facadeTruck;
        }

        if (weight > ft.getMaxWeight()) {
            deliveryService.makeDeliveryFormUncompleted(trID, dfID);
            deliveryService.archiveNotCompleted(trID);
            throw new IllegalStateException("Overweight related to Delivery Form Number:" + dfID + "  In TR number: " + trID);

        }


        if (deliveryService.checkIfAllCompleted(trID)) {
            FacadeTruckingReport truckingReport = deliveryService.getTruckReport(trID);
    *//*         resourcesService.makeAvailable_Driver(truckingReport.getDriverID());
             resourcesService.makeAvailable_Truck(truckingReport.getTruckNumber());*//*
            deliveryService.archive(trID);

        }
    }

*//*
    public void replaceTruck(int trid, String truckNumber, int weight) throws IllegalStateException,IllegalArgumentException,ProviderMismatchException{
        FacadeTruckingReport ftr=getTruckReport(trid);
        String old_truck=ftr.getTruckNumber();
        String curr_Driver=ftr.getDriverID();
        LinkedList<FacadeDriver> listfd=resourcesService.getDrivers();
        FacadeDriver fd=null;
        for (FacadeDriver f:listfd)
        {
            if (f.getID().equals(curr_Driver))
                fd=f;
        }


        FacadeTruck ft=null;
        LinkedList<FacadeTruck> trucks=resourcesService.getTrucks();
        for (FacadeTruck f:trucks)
        {
            if (f.getLicenseNumber()==truckNumber)
            {
                ft=f;
                break;
            }
        }
        if (ft!=null){
            if(ft.getMaxWeight()<weight)
                throw new IllegalStateException("Truck cant carry"+weight+"kgs");
        }
        else throw new IllegalArgumentException("Entered wrong Truck number");
        if (fd!=null){
            if (weight>fd.getLicenseType().getSize())
            {
                throw new ProviderMismatchException("Driver cant handle this weight, choose a new driver first");
            }
        }
        else throw new IllegalArgumentException("Entered wrong driver ID");
        resourcesService.replaceTruck(old_truck,truckNumber);


    }*//*

    public int getSiteDeliveryArea(int site) {
        return deliveryService.getSiteDeliveryArea(site);
    }

    public void removeSiteFromTruckReport(int siteID, int trID) throws NoSuchElementException, SQLException {
        deliveryService.removeSiteFromTruckReport(siteID, trID);
        if (deliveryService.getCurrTruckingReport().getDestinations().isEmpty()) {
            FacadeTruckingReport ft = getTruckReport(trID);
            LocalDate date = ft.getDate();
            int shift = turnTimeToShift(ft.getLeavingHour());
            resourcesService.deleteTruckConstarint(ft.getTruckNumber(), date, shift);
            resourcesService.deleteDriverConstarint(ft.getDriverID(), date, shift);
        } else {
            saveReportReplacedTruckReport();
        }
    }

    private void saveReportReplacedTruckReport() throws SQLException {
        deliveryService.saveReportReplacedTruckReport();
    }

    public boolean addDemandToTruckReport(int itemNumber, int amount, int siteID, int trID) throws IllegalStateException, SQLException {
        return deliveryService.addDemandToTruckReport(itemNumber, amount, siteID, trID);
    }

    public void replaceDriver(int trID, String driverID, int weight) throws IllegalStateException, NoSuchElementException, SQLException {
        LinkedList<FacadeDriver> lfds = resourcesService.getDrivers();
        FacadeDriver fd = null;
        for (FacadeDriver facadeDriver : lfds) {
            if (fd.getID().equals(driverID)) {
                fd = facadeDriver;
                break;
            }
        }
        if (fd == null) {
            throw new NoSuchElementException("No such Driver with that id");
        }
        if (fd.getLicenseType().getSize() < weight) {
            throw new IllegalStateException("Driver cant handle this weight");
        }
        deliveryService.replaceDriver(trID, driverID);
    }

    public LinkedList<FacadeDemand> getItemOnReport(int trID) throws SQLException {
        return deliveryService.getItemOnReport(trID);
    }

    public void removeItemFromTruckingReport(int trID, FacadeDemand demand) throws SQLException {
        deliveryService.removeItemFromTruckingReport(trID, demand);
    }

    public boolean continueAddDemandToTruckReport(int itemNumber, int amount, int siteID, int truckId) throws SQLException {
        return deliveryService.continueAddDemandToTruckReport(itemNumber, amount, siteID, truckId);
    }
    public void chooseDateToCurrentTR(LocalDate chosen) throws SQLException {
        deliveryService.chooseDateToCurrentTR(chosen);
    }

    public void removeSiteFromPool(int siteID) throws NoSuchElementException, IllegalStateException, SQLException {
        deliveryService.removeSiteFromPool(siteID);
    }

    public void removeDemand(FacadeDemand d) throws SQLException {
        deliveryService.removeDemand(d);
    }

    public LinkedList<FacadeDeliveryForm> getUnComplitedDeliveryForms(int trId) {
        return deliveryService.getUnComplitedDeliveryForms(trId);
    }

    public FacadeTruckingReport getNewTruckReport(FacadeTruckingReport oldTr) throws SQLException {
        return deliveryService.getNewTruckReport(oldTr);
    }


    public void moveDemandsFromCurrentToReport(FacadeTruckingReport tr) throws SQLException {

        int replacedId = deliveryService.moveDemandsFromCurrentToReport(tr);
        FacadeTruckingReport replaced = deliveryService.getTruckReport(replacedId);

  *//*      resourcesService.makeUnavailable_Driver(replaced.getDriverID());
        resourcesService.makeUnavailable_Truck(replaced.getTruckNumber());*//*

    }

    public void replaceTruckAndDriver(String truckNumber, String driverID, FacadeTruckingReport tr, int weight) throws InputMismatchException, SQLException {
        FacadeDriver fd = resourcesService.getDriver(driverID);
        FacadeTruck ft = resourcesService.getTruck(truckNumber);
        if (ft.getMaxWeight() < (weight + ft.getWeightNeto()))
            throw new InputMismatchException("the truck's cannot carry this weight");
        if (fd.getLicenseType().getSize() < (weight + ft.getWeightNeto()))
            throw new InputMismatchException("the driver cannot drive with this weight");

      *//*  resourcesService.makeUnavailable_Truck(truckNumber);
        resourcesService.makeUnavailable_Driver(driverID);*//*

        deliveryService.setNewTruckToTR(tr.getID(), truckNumber);
        deliveryService.setNewDriverToTR(tr.getID(), driverID);
        tr.setDriverID(driverID);
        tr.setTruckNumber(truckNumber);
        deliveryService.saveReportReplacedTruckReport();
    }


    public LinkedList<FacadeDemand> getAllDemands() {
        return deliveryService.getAllDemands();
    }

    public LinkedList<FacadeDeliveryForm> getUncompletedDeliveryFormsFromOld(int old_id) {
        return deliveryService.getUncompletedDeliveryFormsFromOld(old_id);

    }

    public LinkedList<FacadeDemand> getUnCompletedItemOnReportByOld(int id) {
        return deliveryService.getUnCompletedItemOnReportByOld(id);
    }


    public LinkedList<FacadeTruckingReport> getOldDTruckingReports() {
        return deliveryService.getOldDTruckingReports();
    }

    public HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> getDaysAndDrivers() throws IllegalArgumentException {
        return resourcesService.getDayAndDrivers();
    }

    public FacadeDriver getDriver(String id) {
        return resourcesService.getDriver(id);
    }

    public LinkedList<FacadeTruck> getAvailableTrucks(LocalDate date, LocalTime shift) {


        return resourcesService.getAvailableTrucks(date, turnTimeToShift(shift));
    }

    public LinkedList<FacadeDriver> getAvailableDrivers(LocalDate date, LocalTime shift) {
        FacadeTruckingReport tr =  deliveryService.getCurrTruckingReport();


        return resourcesService.getAvailableDrivers(date, turnTimeToShift(shift));
    }

    private int turnTimeToShift(LocalTime shift) {
        if (shift.isBefore(LocalTime.of(14, 0)))
            return 0;
        else
            return 1;
    }

    public void deleteDriverConstarint(String id, LocalDate date, LocalTime leavingHour) {
        resourcesService.deleteDriverConstarint(id, date, turnTimeToShift(leavingHour));
    }

    public void deleteTruckConstarint(String id, LocalDate date, LocalTime leavingHour) {
        resourcesService.deleteTruckConstarint(id, date, turnTimeToShift(leavingHour));
    }

    public void addDriverConstarint(String id, LocalDate date, LocalTime leavingHour) {
        resourcesService.addDriverConstarint(id, date, turnTimeToShift(leavingHour));

    }

    public void addTruckConstraint(String id, LocalDate date, LocalTime leavingHour) {
        resourcesService.addTruckConstraint(id, date, turnTimeToShift(leavingHour));
    }

    public LinkedList<FacadeTruckingReport> getTodayReports() {
        return deliveryService.getTodayReports();
    }


    */
}


/*
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
*/

