package ServiceLayer;

import BusinessLayer.InventoryPackage.Item;
import BusinessLayer.Notification;
import BusinessLayer.SuppliersPackage.OrderPackage.Order;
import BusinessLayer.TruckingPackage.DeliveryPackage.Demand;
import BusinessLayer.TruckingPackage.DeliveryPackage.TruckingReport;
import BusinessLayer.TruckingPackage.ResourcesPackage.*;
import InfrastructurePackage.Pair;
import PresentationLayer.TruckingPresentationController;
import ServiceLayer.FacadeObjects.*;
import ServiceLayer.Response.ResponseT;
import com.sun.jdi.connect.IllegalConnectorArgumentsException;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.text.ParagraphView;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class TruckingService {
    private DeliveryService deliveryService;
    private ResourcesService resourcesService;
    private FacadeTruckingReport currTR;
    private static TruckingService instance = null;


    private TruckingService() {
        deliveryService = DeliveryService.getInstance();
        resourcesService = ResourcesService.getInstance();
    }

    public static TruckingService getInstance() {
        if (instance == null)
            instance = new TruckingService();
        return instance;
    }

    // TODO- need to add Notifications where needed


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
     * 6.   adds to the Demands list
     * @return List of pairs, item and its amount, only item that couldn't been delivered.
     * if all items has been settled to a delivery, returns empty list
     */
    public ResponseT<  LinkedList<Pair<Integer, Integer>> > addOrder(Order order) {

        int supplier  = getSupplierFromOrder(order);
        LinkedList<Pair<Integer,Integer>> left = orderToItemsList(order);

        LinkedList<FacadeTruckingReport> thisWeekReports =  getThisWeekReports();
        // inserts into the next 7 days reports only
        left = insertToExistingTR(left , supplier,thisWeekReports);
        if (!left.isEmpty()) {

            Pair<LinkedList<Pair<Integer, Integer>>, LinkedList<FacadeTruckingReport>>
                    afterReportsCreate = createReportsThisWeek(left, supplier);
            // creates reports for the next 7 days only, call drivers from home if needed
            left = afterReportsCreate.getFirst();
            if (!left.isEmpty()) {
                // TODO - need to notify
                LinkedList<FacadeTruckingReport> everyWeekReports = getActiveTruckingReports().getValue();
                everyWeekReports.addAll(getWaitingTruckingReports().getValue());

                // adds to existing TR from every week
                left = insertToExistingTR(left, supplier, everyWeekReports);
                if (!left.isEmpty()) {

                    // creates reports for every possible date
                    afterReportsCreate = createReportsEveryWeek(left, supplier);
                    left = afterReportsCreate.getFirst();
                    if (!left.isEmpty()) {
                       addDemandToPool(left,supplier);
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
    public boolean addPermanentOrder(Order order) throws UnsupportedOperationException{
        boolean succeed = false;
        succeed  = canAddFullOrder(order);
        if (succeed){
            LocalDate date = order.getDate();
            LinkedList<Pair<Integer, Integer>> left = orderToItemsList(order);
            int supplier = getSupplierFromOrder(order);
            LinkedList<FacadeTruckingReport> reports = getAvailableTRsByDate(date);
            left  = insertToExistingTR(left, supplier, reports);

            Pair<LinkedList<Pair<Integer, Integer>>, LinkedList<FacadeTruckingReport>>
                    afterCreateReports = createReportsForDate(left, supplier, date);

            left = afterCreateReports.getFirst();

            if (!left.isEmpty()){
                throw new UnsupportedOperationException("for some reason - didn't recognized the needed weight to possible");
            }
        }

        return succeed;

    }



    public ResponseT< LinkedList<Notification> > getNotifications(){
        return new ResponseT<LinkedList<Notification>>(DeliveryService.getInstance().getNotifications());
    }

    public ResponseT<  LinkedList<FacadeDriver> >getDrivers(){
        throw new UnsupportedOperationException();
    }

    public ResponseT< LinkedList<FacadeTruck> > getTrucks(){

        throw new UnsupportedOperationException();
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
    public void managerApproveTruckReport(Integer trID){
        deliveryService.managerApproveTruckReport(trID);
    }
    public void managerCancelTruckReport(Integer trID){
        deliveryService.managerCancelTruckReport(trID);
    }
    // TODO - employees should call this function
    public void handleLeftOvers() {
        getDemands();
        // for each demand in demands
        addOrder(null);

        throw new UnsupportedOperationException();
    }


    public ResponseT< FacadeTruckingReport> getTruckingReport(int id){
        throw new UnsupportedOperationException();
    }
    public ResponseT< FacadeDeliveryForm> getDeliveryForm(int id){
        throw new UnsupportedOperationException();
    }

    /**
     * this method tries to insert as much as possible items to the received trucking Reports
     * @param reports available TruckingReports
     * @param itemsToInsert - List< Pair< itemId, Amount >>
     * @param supplierCard -  delivery area of the Order
     * @return items left to insert
     */
    private LinkedList<Pair<Integer, Integer>>
    insertToExistingTR(LinkedList<Pair<Integer, Integer>> itemsToInsert,int supplierCard, LinkedList<FacadeTruckingReport> reports){
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param tr -  truck report to check
     * @return returns Max weight truck and driver can handle
     */
    private int getMaxWeight(FacadeTruckingReport tr){
        throw new UnsupportedOperationException();

    }


    /**
     *      this method created new Trucking reports for the received items.
     *      preferably sorted by delivery area
     *      if not possible-> add new drivers to shift
     *      only for the next 7 days
     *
     * @param items -> pair is < ItemID, Amount>
     * @param supplier -> supplier id
     *
     * @return < left items ,LinkedList of the created TruckingReports>>
     */
    private Pair<LinkedList<Pair<Integer,Integer>>  ,LinkedList<FacadeTruckingReport>>
    createReportsThisWeek(    LinkedList<Pair<Integer,Integer>>  items , int supplier ){
        throw new UnsupportedOperationException();
    }

    private Pair< LinkedList<Pair<Integer,Integer>> ,LinkedList<FacadeTruckingReport>>
    createReportsEveryWeek( LinkedList<Pair<Integer,Integer>>  items , int supplier  ){
        throw new UnsupportedOperationException();
    }

    private Pair< LinkedList<Pair<Integer,Integer>> ,LinkedList<FacadeTruckingReport>>
    createReportsForDate( LinkedList<Pair<Integer,Integer>>  items , int supplier , LocalDate date ){
        throw new UnsupportedOperationException();
    }
    private  HashMap<LocalDate, HashMap<Integer, LinkedList<String>>>
    getDaysAndDrivers() throws IllegalArgumentException {
        return resourcesService.getDayAndDrivers();
    }

    private LinkedList<Pair<Integer, Integer>> orderToItemsList(Order order) {
        LinkedList<Pair<Integer,Integer>> left = new LinkedList<>();
        for (Map.Entry<Integer,Integer> entry : order.getProducts().entrySet()){
            left.add(new Pair<>(entry.getKey(),entry.getValue()));
        }
        return left;
    }

    /**
     * this mehtod check whether we can deliver the whole order in the wanted date. checks by overall weight
     * to the day's overall weight
     * @param order already has the wanted date
     * @return true if can, false if cannot
     */
    private boolean canAddFullOrder(Order order){

        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param date -  date to check
     * @return overall weight can add to overall deliveries in this date includes the not created TRs
     */
    private int getDayLeftWeight(LocalDate date){
        throw new UnsupportedOperationException();
    }

    private void createNotification(String content){throw new UnsupportedOperationException();}

    private int getDeliveryArea(int supplier ){throw new UnsupportedOperationException();}

    private Pair<FacadeDriver, FacadeTruck> getDriverAndTruckFromExisting (LocalDate date){
        return resourcesService.findDriverAndTruckForDateFromExisting(date);
    }

    private Pair<FacadeDriver, FacadeTruck> getDriverAndTruckFromPool (LocalDate date){
        return resourcesService.findDriverAndTruckForDateFromPool(date);
    }

    /**
     * inserts the items into demands pool
     * @param items -> itemId, Amount
     * @param  supplier  ->  supplier id
     */
    private void addDemandToPool(LinkedList<Pair<Integer, Integer>> items , int supplier){
        throw new UnsupportedOperationException();
    }

    private Item getItem(int id, int supplier){
        throw new UnsupportedOperationException();
    }

    private LinkedList<FacadeTruckingReport> getAvailableTRsByDate(LocalDate now) {
        throw new UnsupportedOperationException();
    }
    private int getSupplierFromOrder(Order order) {
        return order.getSupplier().getSc().getCompanyNumber();
    }

    private LinkedList<FacadeTruckingReport> getThisWeekReports() {
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
        return thisWeekReports;
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

    public LinkedList<FacadeDemand> sortDemandsBySite(LinkedList<FacadeDemand> demands) {
        for (int i = demands.size() - 1; i >= 0; i--) {
            FacadeDemand min = demands.get(i);
            int index = i;
            for (int j = i; j >= 0; j--) {
                if (demands.get(j).getSite() < min.getSite()) {
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
        // TODO need to check why it is here
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
        // TODO need to check why its here
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


    public void upload() throws SQLException {
        deliveryService.upload();
        HashMap driver_cons =  deliveryService.getDriverConstraintsFromUpload();
        HashMap trucks_cons = deliveryService.getTruckConstraintsFromUpload();
        resourcesService.upload(driver_cons,trucks_cons);
    }*/
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

