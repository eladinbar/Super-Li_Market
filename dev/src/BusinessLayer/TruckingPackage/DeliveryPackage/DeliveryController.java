package BusinessLayer.TruckingPackage.DeliveryPackage;


import BusinessLayer.InventoryPackage.InventoryController;
import BusinessLayer.InventoryPackage.Item;
import BusinessLayer.Notification;
import BusinessLayer.SuppliersPackage.OrderPackage.Order;
import BusinessLayer.TruckingNotifications;
import BusinessLayer.TruckingPackage.ResourcesPackage.Truck;
import InfrastructurePackage.Pair;
import ServiceLayer.InventoryService;

import javax.naming.TimeLimitExceededException;
import java.lang.invoke.LambdaConversionException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DeliveryController {


    private LinkedList<Demand> demands;
    private HashMap<Integer, TruckingReport> activeTruckingReports;//<trID,TR>
    private HashMap<Integer, TruckingReport> waitingTruckingReports;//<trID,TR>
    private HashMap<Integer, TruckingReport> oldTruckingReports;//<trID,TR>
    private HashMap<Integer, LinkedList<DeliveryForm>> activeDeliveryForms;
    private HashMap<Integer, LinkedList<DeliveryForm>> oldDeliveryForms;

    private int lastReportID;
    private int lastDeliveryForms;
    private TruckingReport currTR;
    private static DeliveryController instance = null;
    private LinkedList<Notification> notifications;

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
        this.notifications =  new LinkedList<>();
        //this.sites = new HashMap<>();
/*
        this.deliveryForms=new HashMap<>();
*/
        // when data base is added, need to be set by it;
        //this.items = new HashMap<>();
        this.waitingTruckingReports =  new HashMap<>();
        this.lastDeliveryForms = 1;
        this.lastReportID = 0;

        this.currTR = new TruckingReport(lastReportID);
        lastReportID++;
        this.activeDeliveryForms = new HashMap<>();
    }

    // TODO - need to check weight insert


    // TODO - when go to old DFs and Trs, need to take form DB and not from here

    public LinkedList<Pair<Integer, Integer>> createTruckReport(LinkedList<Pair<Integer, Integer>> items, String driverId, String truckId, int maxWeight, int supplier, LocalDate date)  {
        TruckingReport tr= new TruckingReport(lastReportID);
        lastReportID++;
        //TODO - need to save in DB report and DFs
        tr.addSupplier(supplier);
        tr.setDate(date);
        tr.setTruckNumber(truckId);
        tr.setDriverID(truckId);
        items = insertItemsToTruckReport(items, supplier,maxWeight,tr.getID());
        return items;
    }

    /**
     * adds demands to pool, if already has demand for this item and supplier, adds its to the existing demands
     * @param items Pair< itemId, amount > to insert to pool
     * @param supplier supplier for this demand
     * @throws SQLException
     */
    public void addDemand(LinkedList<Pair<Integer,Integer>> items, int supplier) throws SQLException {
        for (Pair<Integer,Integer> item : items) {
            for (Demand demand : demands) {
                if (demand.getItemID() == item.getFirst() && demand.getSupplier() == supplier) {
                    demand.setAmount(demand.getAmount() + item.getSecond());
                    break;
                }
                Demand d = new Demand(item.getFirst(),supplier,item.getSecond());
                demands.add(d);
            }
        }
        // TODO - need to save in DB
    }

    public LinkedList<TruckingReport> getActiveTruckingReports(){
        LinkedList<TruckingReport> tr =  new LinkedList<>();
        for (Map.Entry<Integer,TruckingReport> entry : activeTruckingReports.entrySet()){
            tr.add(entry.getValue());
        }
        return tr;
    }

    public LinkedList<TruckingReport> getWaitingTruckingReports(){
        LinkedList<TruckingReport> tr =  new LinkedList<>();
        for (Map.Entry<Integer,TruckingReport> entry : waitingTruckingReports.entrySet()){
            tr.add(entry.getValue());
        }
        return tr;
    }

    public LinkedList<TruckingReport> getOldTruckingReports(){
        LinkedList<TruckingReport> tr =  new LinkedList<>();
        for (Map.Entry<Integer,TruckingReport> entry : oldTruckingReports.entrySet()){
            tr.add(entry.getValue());
        }
        return tr;
    }

    /**
     * show all the current notifications, deletes them after showed
     * @return current notifications since last time
     */
    public LinkedList<Notification> getNotifications() {
        // TODO need to delete from pool
        // TODO need to delete from DB
        return notifications;
    }

    public LinkedList<String> getBusyTrucksByDate(LocalDate date) {
        LinkedList<String> result=new LinkedList<>();
        for (Map.Entry<Integer,TruckingReport> entry:waitingTruckingReports.entrySet())
        {
         if (!result.contains(entry.getValue().getTruckNumber()))
             result.add(entry.getValue().getTruckNumber());
        }
        for (Map.Entry<Integer,TruckingReport> entry:activeTruckingReports.entrySet())
        {
            if (!result.contains(entry.getValue().getTruckNumber()))
                result.add(entry.getValue().getTruckNumber());
        }
        return result;

    }

    public int getTruckReportCurrentWeight(int report_id) {
        LinkedList<DeliveryForm> dfs  = null;
        if (activeDeliveryForms.containsKey(report_id)){
            dfs = activeDeliveryForms.get(report_id);
        }
        else
            dfs = getOldDeliveryForms(report_id);
        if (dfs ==  null){
            throw new NoSuchElementException("couldn't find Delivery Forms for TruckReport number: " + report_id);
        }
        int total = 0;
        for (DeliveryForm df : dfs){
            total += df.getLeavingWeight();
        }

        return total;
    }

    /**
     * return all truckReports in a specific date
     * @param date - the date the tr is settled to
     * @return LinkedList of TruckReports, empty if couldn't find any
     */
    public LinkedList<TruckingReport> getTruckingReportsByDate(LocalDate date) {
        LinkedList<TruckingReport> output = new LinkedList<>();

        if (date.isBefore(LocalDate.now())){
            LinkedList<TruckingReport> olds = getOldTruckingReports();
            for (TruckingReport truckingReport : olds){
                if (truckingReport.getDate().isEqual(date)){
                    output.add(truckingReport);
                }
            }
        }
        else{
            for (Map.Entry<Integer, TruckingReport> entry : activeTruckingReports.entrySet()){
                if (entry.getValue().getDate().isEqual(date)){
                    output.add( entry.getValue());
                }
            }
            for (Map.Entry<Integer, TruckingReport> entry : waitingTruckingReports.entrySet()){
                if (entry.getValue().getDate().isEqual(date)){
                    output.add( entry.getValue());
                }
            }
        }
        return  output;
    }

    public LinkedList<Pair<Integer, Integer>> insertItemsToTruckReport(LinkedList<Pair<Integer, Integer>> items, int supplier, int capacity, int report_id) {
        int currentWeight = getTruckReportCurrentWeight(report_id);
        int area =  getSupplierArea(supplier);
        LinkedList<Pair<Integer,Integer>> left =  new LinkedList<>();
        // check the report can add more items
        if ( currentWeight< capacity) {
            addSupplierToReport(supplier,report_id);
            int leftWeight = capacity - currentWeight;
            // adds items to the report
            for (Pair<Integer,Integer> item: items){
                if (item.getSecond() != 0) {
                    int amount = findAmountToAdd(item, leftWeight);
                    if (amount > 0) {
                        // finds the deliveryForm to insert to
                        DeliveryForm toInsert = getDeliveryFormBySupplier(report_id ,supplier );
                        // if returns null - does not have this supplier yet, need to create new DF
                        if (toInsert != null){
                            toInsert.addItem(item.getFirst(), amount);
                            //TODO - need to update dateBase
                        }
                        else{
                            HashMap<Integer, Integer> dfItems= new HashMap<>();
                            dfItems.put(item.getFirst(), amount);

                            DeliveryForm df = new DeliveryForm(lastDeliveryForms, supplier,dfItems,getItemTotalWeight(item.getFirst(), amount) , report_id );
                            activeDeliveryForms.get(report_id).add(df);
                            lastDeliveryForms++;
                            // TODO - need to save dfs  in database

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
            for (Pair<Integer,Integer> item: items) {
                if (item.getSecond()!= 0){
                    left.add(item);
                }
            }

        }
        return left;
    }

    private int getItemTotalWeight(Integer itemID, int amount) {
        int itemWeight = getItemWeight(itemID);
        int totalWeight = itemWeight * amount;
        return totalWeight;
    }



    /**
     *
     * @param report_id the truckReport id to find the deliveryForm in
     * @param supplier the supplier id.
     * @return the delivery form, returns null if couldn't find
     */
    private DeliveryForm getDeliveryFormBySupplier(int report_id, int supplier) {
        TruckingReport report = getTruckReport(report_id);
        LinkedList<Integer> suppliers = report.getSuppliers();
        DeliveryForm output = null;
        if (suppliers.contains(supplier)){
            LinkedList<DeliveryForm> dfs =  null;
            if (activeDeliveryForms.containsKey(report_id)){
                dfs = activeDeliveryForms.get(report_id);
            }
            else
                dfs = oldDeliveryForms.get(report_id);
            for (DeliveryForm df : dfs){
                if (df.getDestination() == supplier)
                    output = df;
            }
        }
        return output;
    }

    /**
     * finds the maximum amount to add for the specific amount.
     * works like binary search
     * @param item - Item id, amount
     * @param leftWeight - Maximum to add
     * @return the amount to add, 0 if none
     */
    private int findAmountToAdd(Pair<Integer, Integer> item, int leftWeight) {
       int output = 0;
       int totalWeight = 0;
       boolean found = false;
       int maxAmount = item.getSecond();
       int minimumAmount =0;
       int itemWeight = getItemWeight(item.getFirst());
       while (!found){
           totalWeight = output * itemWeight;
           if (totalWeight == leftWeight)
               found = true;
           // if adding 1 more exceeds, finished
           else if (( totalWeight < leftWeight) &&  (totalWeight + itemWeight > leftWeight) )
               found= true;
           else{
               if (totalWeight< leftWeight){
                   minimumAmount = output + 1;
               }
               else{
                   maxAmount = output - 1;
               }
               output = (maxAmount + minimumAmount) /2;

           }
       }
       return output;
    }

    public LinkedList<Integer> getTruckReportSuppliers(int report_id) {
        return getTruckReport(report_id).getSuppliers();

    }


    public TruckingReport getTruckReport(int trNumber) throws NoSuchElementException {
        if (currTR.getID() == trNumber)
            return currTR;
        if (oldTruckingReports.containsKey(trNumber)) {
            return oldTruckingReports.get(trNumber);
        } else if (activeTruckingReports.containsKey(trNumber)) {
            return activeTruckingReports.get(trNumber);
        }
        else if (waitingTruckingReports.containsKey(trNumber)){
            return waitingTruckingReports.get(trNumber);
        }
        else throw new NoSuchElementException("No Report with that ID");

    }

    public void managerApproveTruckReport(Integer trID) throws TimeLimitExceededException {
        // todo DB update approved
        TruckingReport report = getTruckReport(trID);
        if (report.getDate().isBefore(LocalDate.now()))
            throw new TimeLimitExceededException();
        if (!report.isApproved()) {
            report.setApproved(true);


            waitingTruckingReports.remove(trID);
            activeTruckingReports.put(trID, report);
        }
    }

    public void managerCancelTruckReport(int trID) throws TimeLimitExceededException {

        TruckingReport report = getTruckReport(trID);
        if (report.getDate().isBefore(LocalDate.now()))
            throw new TimeLimitExceededException();
        if (!report.isApproved()) {

            waitingTruckingReports.remove(trID);
            //TODO - need to remove delivery Forms


            //TODO - need to delete from DB as well as deliveryForms
        }
    }


    public LinkedList<Demand> getDemands() {
        return demands;
    }

    public void setDemandNewAmount(Integer id, Integer amount, int supplier) throws SQLException {
        for (Demand demand : demands){
            if ((demand.getSupplier() == supplier ) && (demand.getItemID() == id)){
                demand.setAmount(amount);
            }
        }
    }

    public DeliveryForm getDeliveryForm(int id) {
        DeliveryForm deliveryForm = null;
        for (Map.Entry<Integer,LinkedList< DeliveryForm>> entry : activeDeliveryForms.entrySet()){
            if (entry.getValue().contains(id)){
                deliveryForm = entry.getValue().get(id);
            }
        }
        return  deliveryForm;
    }





    private void addSupplierToReport(int supplier,  int report_id) {
        int supplier_area =  getSupplierArea(supplier);
        LinkedList<Integer> report_areas = getReportAreas(report_id);
        if (!report_areas.contains(supplier_area)){
            addNotification("Truck Report Number: " + report_id + "\n\thas been extended to another delivery area." +
                    "\n\tthe new Delivery area: " + supplier_area);
        }
        getTruckReport(report_id).addSupplier(supplier);

    }

    private void addNotification(String s) {
        notifications.add(new TruckingNotifications(s));
    }


    private LinkedList<Integer> getReportAreas(int report_id) {
        LinkedList<Integer> areas = new LinkedList<>();
        LinkedList<Integer> suppliers = getTruckReport(report_id).getSuppliers();
        for (Integer supplier: suppliers){
            if (!areas.contains(getSupplierArea(supplier))){
                areas.add(getSupplierArea(supplier));
            }
        }
        return areas;
    }
    // TODO - need to get from DB
    private LinkedList<DeliveryForm> getOldDeliveryForms(int report_id) {
        return oldDeliveryForms.get(report_id);
    }

    private int getSupplierArea(int supplier) {
        throw new UnsupportedOperationException();
        // TODO - need to implement when possible
    }

    private int getItemWeight(Integer item_id) {
        throw new UnsupportedOperationException();
    }


//    public int createNewTruckingReport() throws SQLException {
//
//        currDF = new LinkedList<>();
//        currTR = new TruckingReport(lastReportID);
//        currTR.setID(lastReportID);
//        lastReportID++;
//        return lastReportID - 1;
//
//    }
//
//    /**
//     * update the date in the current TR
//     *
//     * @param date
//     * @throws IllegalArgumentException
//     */
//    public void updateCurrTR_Date(LocalDate date) throws IllegalArgumentException, SQLException {
//        LocalDate now = LocalDate.now();
//        if (date.compareTo(now) != -1) {
//            currTR.setDate(date);
//        } else {
//            throw new IllegalArgumentException("Cant enter date that passed");
//        }
//
//    }
//
//    /**
//     * update the leaving hour in the current TR
//     *
//     * @param leavingHour
//     * @throws IllegalArgumentException
//     */
//    public void updateCurrTR_LeavingHour(LocalTime leavingHour) throws IllegalArgumentException, SQLException {
//        LocalTime now = LocalTime.now();
//        if (leavingHour.compareTo(now) != -1) {
//            currTR.setLeavingHour(leavingHour);
//        } else {
//            throw new IllegalArgumentException("Cant enter time that passed");
//        }
//
//    }
//
//    /**
//     * update the truck license number in the current TR
//     *
//     * @param number
//     */
//    public void updateCurrTR_TruckNumber(String number) throws SQLException {//facade service need to check availability
//        currTR.setTruckNumber(number);
//    }
//
//    /**
//     * update the driver ID in the current TR
//     *
//     * @param id
//     */
//    public void updateCurrTR_DriverID(String id) throws SQLException {//facade service need to check availability
//        currTR.setDriverID(id);
//    }
//
//    /**
//     * update the origin site in the current TR
//     *
//     * @param origin
//     * @throws IllegalArgumentException - in case origin does not exist
//     */
//    public void updateCurrTR_Origin(int origin) throws IllegalArgumentException {
//        try {
//            sites.get(origin);
//            currTR.setOrigin(origin);
//        } catch (IllegalArgumentException | SQLException exception) {
//            throw new IllegalArgumentException("SiteID does not exist");
//        }
//    }
//
//    /**
//     * adding a destination to the current TR
//     *
//     * @param destination
//     * @throws IllegalArgumentException - in case destination does not exist
//     */
//    public void addCurrTR_Destination(int destination) throws IllegalArgumentException {
//        if (sites.containsKey(destination))
//            currTR.addDestination(destination);
//        else throw new IllegalArgumentException("No such site exist");
//    }
//
//    /**
//     * updates for thr current TR which TR he is replacing
//     *
//     * @param tr_id
//     * @throws NoSuchElementException
//     */
//    public void updateCurrTR_TrReplaced(int tr_id) throws NoSuchElementException, SQLException {
//        if (oldTruckingReports.containsKey(tr_id)) {
//            currTR.setTRReplace(tr_id);
//        } else if (activeTruckingReports.containsKey(tr_id)) {
//            currTR.setTRReplace(tr_id);
//        } else throw new NoSuchElementException("TR was not found");
//    }
//
//    /**
//     * adding item to delivery form
//     *
//     * @param demand
//     * @param amount
//     * @param ignore - indicating if to ignore the fact we delivering to two different delivery areas.
//     * @param siteID
//     * @throws IllegalStateException
//     */
//    public void addItemToDeliveryForm(Demand demand, int amount, boolean ignore, int siteID) throws IllegalStateException, SQLException {
//        if (currDF.isEmpty())//if list is empty
//        {
//            HashMap<Integer, Integer> itemsOnDF = new HashMap<>();
//            itemsOnDF.put(items.get(demand.getItemID()).getID(), amount);
//            DeliveryForm newDF = new DeliveryForm(lastDeliveryForms, items.get(demand.getItemID()).getOriginSiteId(), siteID, itemsOnDF,
//                    (int) items.get(demand.getItemID()).getWeight() * amount, currTR.getID());
//            currDF.add(newDF);
//            currTR.setOrigin(items.get(demand.getItemID()).getOriginSiteId());
//            lastDeliveryForms++;
//            if (sites.get(items.get(demand.getItemID()).getOriginSiteId()).getDeliveryArea() != sites.get(demand.getSite()).getDeliveryArea()) {
//                throw new IllegalStateException("Two different delivery areas");
//            }
//        } else // we need to look in the list maybe there is a form with the same origin& destination
//        {
//            boolean added = false;
//            for (DeliveryForm df : currDF) {
//
//                if (df.getOrigin() == items.get(demand.getItemID()).getOriginSiteId() && df.getDestination() == siteID) {
//                    HashMap<Integer, Integer> itemsOnDF = df.getItems();
//                    if (itemsOnDF.containsKey(demand.getItemID())) {
//                        itemsOnDF.put(demand.getItemID(), amount + itemsOnDF.get(demand.getItemID()));
//                    } else
//                        itemsOnDF.put(demand.getItemID(), amount);
//                    df.setLeavingWeight((int) (df.getLeavingWeight() + items.get(demand.getItemID()).getWeight() * amount));
//                    added = true;
//                    break;
//                }
//                if (!ignore && sites.get(demand.getSite()).getDeliveryArea() != sites.get(df.getDestination()).getDeliveryArea()
//                        && demand.getSite() != items.get(demand.getItemID()).getOriginSiteId()) {
//                    throw new IllegalStateException("Two different delivery areas");
//                }
//            }
//            if (!added) {
//                if (sites.get(items.get(demand.getItemID()).getOriginSiteId()).getDeliveryArea() != sites.get(demand.getSite()).getDeliveryArea()) {
//                    throw new IllegalStateException("Two different delivery areas");
//                }
//                HashMap<Integer, Integer> itemsOnDF = new HashMap<>();
//                itemsOnDF.put(items.get(demand.getItemID()).getID(), amount);
//
//                DeliveryForm deliveryForm = new DeliveryForm(lastDeliveryForms, items.get(demand.getItemID()).getOriginSiteId(), siteID
//                        , itemsOnDF, (int) (demand.getAmount() * items.get(demand.getItemID()).getWeight()), currTR.getID());
//                currDF.add(deliveryForm);
//                lastDeliveryForms++;
//            }
//
//        }
//        updateTruckReportDestinations(currTR.getID());
//
//    }
//
//
//    public void updateCurrTR_ID(int id) throws SQLException {
//        currTR.setID(id);
//    }
//
//
//    public boolean addSite(String city, int deliveryArea, String phoneNumber, String contactName, String name) throws SQLException {
//
////        Site newSite = new Site(lastSiteID, city, deliveryArea, phoneNumber, contactName, name);
//
//        sites.put(lastSiteID, newSite);
//        lastSiteID++;
//        return true;
//
//
//    }
//
//    public boolean removeSite(int siteID) throws NoSuchElementException, IllegalStateException,SQLException {
//        if (!sites.containsKey(siteID)) {
//            throw new NoSuchElementException("SiteID does not exist");
//        } else {
//            for (Map.Entry<Integer, Item> entry : items.entrySet()) {
//                if (entry.getValue().getOriginSiteId() == siteID) {
//                    removeItemFromPool(entry.getKey());
//                }
//            }
//            Site s=sites.remove(siteID);
//            DalSiteController.getInstance().delete(new DalSite(siteID,s.getName(),s.getCity(),s.getDeliveryArea(),s.getContactName(),s.getPhoneNumber()));
//            return true;
//        }
//
//    }
//
//
//    public void chooseLeavingHour(LocalTime leavingHour) throws IllegalArgumentException, SQLException {
//
//        currTR.setLeavingHour(leavingHour);
//
//    }
//
//    /**
//     * Moving a TR that has been done from the active to the non active (old) zone.
//     */
//    public void saveReport() throws SQLException {
//        removeCurrTakenDemandsFromTotal();
//        activeTruckingReports.put(currTR.getID(), currTR);
//        activeDeliveryForms.put(currTR.getID(), currDF);
//        saveTruckReportDB(currTR);
//        for (DeliveryForm df : currDF) {
//            saveDeliveryFormDB(df);
//        }
//        updateDemandsOnDB(demands);
//
//    }
//
//

//
//    /**
//     * @param dfNumber
//     * @param trNumber
//     * @return Delivery form a specific TR
//     * @throws IllegalStateException
//     * @throws NoSuchElementException
//     */
//    public DeliveryForm getDeliveryForm(int dfNumber, int trNumber) throws IllegalStateException, NoSuchElementException {
//        DeliveryForm result = null;
//        if (trNumber > currTR.getID()) {
//            throw new IllegalArgumentException("TR does not exist yet");
//        }
//        LinkedList<DeliveryForm> dfs = activeDeliveryForms.get(trNumber);
//        for (DeliveryForm df : dfs) {
//            if (df.getTrID() == trNumber && dfNumber == df.getID()) {
//                result = df;
//            }
//        }
//        if (result == null) {
//            throw new NoSuchElementException("Delivery form does not exist");
//        }
//        return result;
//
//    }
//
//    /**
//     * Removes a destination from thr current TR
//     *
//     * @param site
//     * @throws NoSuchElementException
//     */
//    public void removeDestination(int site) throws NoSuchElementException, SQLException {
//        LinkedList<DeliveryForm> toRemove = new LinkedList();
//        if (!currTR.getDestinations().contains(site)) {
//            throw new NoSuchElementException("Site does not exist");
//        } else {
//
//            for (DeliveryForm df : currDF) {
//
//                if (df.getDestination() == site || df.getOrigin() == site) {
//
//
//                    for (Map.Entry<Integer, Integer> entry : df.getItems().entrySet()) {
//
//                        addDemandToSystem(entry.getKey(), df.getDestination(), entry.getValue());
//                    }
//                    toRemove.add(df);
//                }
//
//            }
//            int counter = 0;
//            int temp = 0;
//            for (Integer id : currTR.getDestinations()) {
//
//                if (id == site) {
//                    counter = temp;
//                }
//                temp++;
//            }
//            currTR.getDestinations().remove(counter);
//
//        }
//        for (DeliveryForm df : toRemove) {
//            if (currDF.contains(df)) {
//                currDF.remove(df);
//            }
//            if (currDF.isEmpty())
//                throw new NoSuchElementException("no more demands left to deliver, aborting..");
//        }
//        updateTruckReportDestinations(currTR.getID());
//    }
//
//    /**
//     * @param demand- says what item to remove,in which amount,from which site.
//     */
//    public void removeItemFromReport(Demand demand) throws SQLException {
//
//        for (DeliveryForm df : currDF) {
//            if (df.getDestination() == demand.getSite() && df.getItems().containsKey(demand.getItemID())) {
//                if (demand.getAmount() >= df.getItems().get(demand.getItemID()))
//                    df.getItems().remove(demand.getItemID());
//                else for (Map.Entry<Integer, Integer> entry : df.getItems().entrySet()) {
//                    if (entry.getKey() == demand.getItemID()) {
//                        entry.setValue(entry.getValue() - demand.getAmount());
//                    }
//                }
//            }
//        }
//        updateTruckReportDestinations(currTR.getID());
//    }
//
//    public void removeItemFromPool(int item) throws NoSuchElementException, IllegalStateException,SQLException {
//
//
//        if (!items.containsKey(item)) {
//            throw new NoSuchElementException("No item with that ID");
//        }
//        for (Map.Entry<Integer, LinkedList<DeliveryForm>> entry : activeDeliveryForms.entrySet()) {
//            LinkedList<DeliveryForm> ldf = entry.getValue();
//            for (DeliveryForm df : ldf) {
//                HashMap<Integer, Integer> items = df.getItems();
//                for (Map.Entry<Integer, Integer> integerIntegerEntry : items.entrySet()) {
//                    if (integerIntegerEntry.getKey() == item)
//                        throw new IllegalStateException("Cant delete item when there is a delivery form with it");
//                }
//            }
//        }
//        for (Demand d : demands) {
//            if (d.getItemID() == item)
//                throw new IllegalStateException("Cant delete item when there is a demand with it");
//        }
//        Item i=items.remove(item);
//        DalItemController.getInstance().delete(new DalItem(item,i.getWeight(),i.getName(),i.getOriginSiteId()));
//    }
//
//
//    public void addItem(double weight, String name, int siteID) throws NoSuchElementException, KeyAlreadyExistsException, SQLException {
//        if (items.containsKey(lastItemId))
//            throw new KeyAlreadyExistsException("ID already taken");
//        else if (!sites.containsKey(siteID))
//            throw new NoSuchElementException("this site doesn't exist");
//        else {
//            Item item = new Item(lastItemId, weight, name, siteID);
//            items.put(lastItemId, item);
//            lastItemId++;
//        }
//    }
//
//    public void displaySites() {
//
//        for (Integer i : currTR.getDestinations()) {
//            System.out.println(i + " - " + sites.get(i).getName() + "/n");
//        }
//    }
//
//    public double getItemWeight(int itemID) throws NoSuchElementException {
//        if (items.containsKey(itemID))
//            return items.get(itemID).getWeight();
//        else throw new NoSuchElementException("No such item exist");
//    }
//
//    public LinkedList<Demand> getDemands() {
//        return demands;
//    }
//
//    /**
//     * @return LinkedList of demands that are not included in the current TR
//     * @throws NoSuchElementException
//     */
//    public LinkedList<Demand> showDemands() throws NoSuchElementException, SQLException {
//
//        if (!demands.isEmpty()) {
//            LinkedList<Demand> result = new LinkedList<>();
//            for (Demand d : demands) {
//                boolean added = false;
//
//                if (currDF.isEmpty()) {
//                    return demands;
//                }
//                for (DeliveryForm df : currDF) {
//                    if (df.getItems().containsKey(d.getItemID()) && df.getDestination() == d.getSite()) {
//                        added = true;
//                        int newAmount = d.getAmount() - df.getItems().get(d.getItemID());
//                        if (newAmount > 0) {
//                            Demand toAdd = new Demand(d.getItemID(), d.getSite(), newAmount);
//                            result.add(toAdd);
//                        }
//                        //result.add(d);
//                    }
//
//                }
//                if (!added)
//                    result.add(d);
//            }
//            return result;
//        }
//
//        throw new NoSuchElementException("no more demands found yet");
//
//    }
//
//    public String getItemName(int itemID) throws NoSuchElementException {
//        if (items.containsKey(itemID))
//            return items.get(itemID).getName();
//        else throw new NoSuchElementException("No such item exist");
//    }
//
//    public String getSiteName(int site) throws NoSuchElementException {
//        if (sites.containsKey(site))
//            return sites.get(site).getName();
//        else throw new NoSuchElementException("No such site exist");
//    }
//
//    /**
//     * @return LinkedList with sites in the system.
//     */
//    public LinkedList<Site> getCurrentSites() {
//        LinkedList<Site> result = new LinkedList<>();
//        LinkedList<Integer> sitesID = currTR.getDestinations();
//        for (Integer id : sitesID) {
//
//            result.add(sites.get((id)));
//        }
//        return result;
//    }
//
//    /**
//     * @return LinkedList with items in the Current in-build trucking Report.
//     * no special order is returned
//     */
//    public LinkedList<Demand> getItemsOnTruck() throws SQLException {
//        LinkedList<Demand> result = new LinkedList<>();
//        for (DeliveryForm df : currDF) {
//            for (Map.Entry<Integer, Integer> entry : df.getItems().entrySet()) {
//                Demand d = new Demand(entry.getKey(), df.getDestination(), entry.getValue());
//                result.add(d);
//            }
//        }
//        return result;
//    }
//
//    public LinkedList<Item> getItems() {
//        LinkedList<Item> result = new LinkedList<>();
//        for (Map.Entry<Integer, Item> entry : items.entrySet()) {
//            result.add(entry.getValue());
//        }
//        return result;
//    }
//
//
//    public LinkedList<Site> getAllSites() throws NoSuchElementException {
//        LinkedList<Site> sites = new LinkedList<>();
//        if (!this.sites.isEmpty()) {
//            for (Map.Entry<Integer, Site> entry : this.sites.entrySet()) {
//                sites.add(entry.getValue());
//            }
//
//            return sites;
//        } else throw new NoSuchElementException("No sites in the system");
//    }
//
//    /**
//     * Adding a demand that we received from one of the sites.
//     *
//     * @param itemId
//     * @param site
//     * @param amount
//     */
//    public void addDemandToSystem(int itemId, int site, int amount) throws SQLException {
//        if (!items.containsKey(itemId)) {
//            throw new NoSuchElementException("Entered wrong item id");
//        }
//        if (!sites.containsKey(site)) {
//            throw new NoSuchElementException("Entered wrong site id");
//        }
//        if (items.get(itemId).getOriginSiteId() == site) {
//            throw new NoSuchElementException("Origin site and Deliver Site must be different");
//        }
//        boolean inserted = false;
//        for (Demand d : demands) {
//            if (d.getItemID() == itemId && d.getSite() == site) {
//                d.setAmount(d.getAmount() + amount);
//                inserted = true;
//                break;
//            }
//        }
//        if (!inserted) {   // checks the delivery area is same
//            demands.add(new Demand(itemId, site, amount));
//            if (sites.get(items.get(itemId).getOriginSiteId()).getDeliveryArea() != sites.get(site).getDeliveryArea())
//                throw new InputMismatchException("the demand has 2 delivery area in it\n" +
//                        "you may remove it through menu or you can procced.\n" +
//                        "please notice, if you proceed");
//            DalDemandController.getInstance().insert(new DalDemand(itemId, amount, site));
//        }
//
//
//
//    }
//
//    /**
//     * Calculating the total weight of the current Delivery.
//     *
//     * @return
//     */
//    public int getWeightOfCurrReport() {
//        int weight = 0;
//        for (DeliveryForm df : currDF) {
//            for (Map.Entry<Integer, Integer> entry : df.getItems().entrySet()) {
//                weight = (int) (weight + items.get(entry.getKey()).getWeight() * entry.getValue());
//            }
//        }
//        return weight;
//    }
//
//
//    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< getters, setters>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.
//
//
//    public TruckingReport getCurrTR() {
//        return currTR;
//    }
//
//    public LinkedList<DeliveryForm> getCurrDF() {
//        return currDF;
//    }
//
//    //    public HashMap<Integer, DeliveryForm> getDeliveryForms() {
////        return deliveryForms;
////    }
//    public LinkedList<DeliveryForm> getDeliveryForms(int trID) {
//        return activeDeliveryForms.get(trID);
//    }
//
//    public HashMap<Integer, Site> getSites() {
//        return sites;
//    }
//
//    public LinkedList<TruckingReport> getActiveTruckingReports() {
//        LinkedList<TruckingReport> result = new LinkedList<>();
//        for (Map.Entry<Integer, TruckingReport> entry : activeTruckingReports.entrySet()) {
//            result.add(entry.getValue());
//        }
//        return result;
//    }
//
//    public HashMap<Integer, TruckingReport> getOldTruckingReports() {
//        return oldTruckingReports;
//    }
//
//
//    public void updateDeliveryFormRealWeight(int trID, int dfID, int weight) throws SQLException {
//        LinkedList<DeliveryForm> deliveryForms = activeDeliveryForms.get(trID);
//        for (DeliveryForm df : deliveryForms) {
//            if (df.getID() == dfID) {
//                df.setLeavingWeight(weight);
//                df.setCompleted();
//                updateDeliveryFormDB(df);
//            }
//
//        }
//    }
//
//
//    public boolean checkIfAllCompleted(int trID) {
//        LinkedList<DeliveryForm> dfs = activeDeliveryForms.get(trID);
//        boolean completed = true;
//        for (DeliveryForm df : dfs) {
//            completed = completed && df.isCompleted();
//        }
//        return completed;
//
//    }
//
//    public void archive(int trID) throws SQLException {
//        oldTruckingReports.put(trID, activeTruckingReports.get(trID));
//        oldDeliveryForms.put(trID, getDeliveryForms(trID));
//        activeTruckingReports.remove(trID);
//        activeDeliveryForms.remove(trID);
//        activeDeliveryForms.remove(getDeliveryForms(trID));
//        getTruckReport(trID).setCompleted();
//        updateTruckingReportDB(getTruckReport(trID));
//    }
//
//    public void archiveNotCompleted(int trID) throws SQLException {
//        TruckingReport truckReport = getTruckReport(trID);
//        int newTrID = createNewTruckingReport();
//        currTR.setDate(truckReport.getDate());
//        currTR.setLeavingHour(truckReport.getLeavingHour());
//        currTR.setTruckNumber(truckReport.getTruckNumber());
//        currTR.setDriverID(truckReport.getDriverID());
//        currTR.setOrigin(truckReport.getOrigin());
//        currTR.setDestinations(truckReport.getDestinations());
//        currTR.setTRReplace(truckReport.getID());
//        // removing all Delivery forms
//        LinkedList<DeliveryForm> oldDfs = activeDeliveryForms.get(truckReport.getID());
//
//        for (DeliveryForm df : oldDfs) {
//            DeliveryForm nd = new DeliveryForm(df);
//            nd.setID(lastDeliveryForms);
//            lastDeliveryForms++;
//            currDF.add(nd);
//        }
//        activeDeliveryForms.remove(truckReport.getID());
//        oldDeliveryForms.put(truckReport.getID(), oldDfs);
//        //sets the old truck report and removes from active
//        oldTruckingReports.put(truckReport.getID(), truckReport);
//        activeTruckingReports.remove(truckReport.getID());
//
//
//    }
//
//    public int getSiteDeliveryArea(int siteID) {
//        return sites.get(siteID).getDeliveryArea();
//    }
//
//    public void removeSiteFromTruckReport(int siteID, int trID) throws NoSuchElementException, SQLException {
//        boolean removed = false;
//        LinkedList<DeliveryForm> ldfs = new LinkedList<>(currDF);
//        for (DeliveryForm df : ldfs) {
//            if (!df.isCompleted()) {
//
//                for (Map.Entry<Integer, Integer> entry : df.getItems().entrySet()) {
//
//                    addDemandToSystem(entry.getKey(), df.getDestination(), entry.getValue());
//                }
//                if (df.getDestination() == siteID || df.getOrigin() == siteID) {
//
//                    currDF.remove(df);
//                    currTR.getDestinations().remove(Integer.valueOf(siteID));
//
//                    removed = true;
//                }
//            }
//        }
//        if (!removed) {
//            throw new NoSuchElementException("site does not included in Truck Report");
//        }
//
//        updateTruckReportDestinations(currTR.getID());
//
//    }
//
//    public TruckingReport getReplaceTruckingReport(int trID) {
//        if (currTR.getTRReplace() == trID)
//            return currTR;
//        for (Map.Entry<Integer, TruckingReport> act : activeTruckingReports.entrySet()) {
//            if (act.getValue().getTRReplace() == trID) {
//                return act.getValue();
//            }
//        }
//
//        return null;
//    }
//
//    public boolean addDemandToTruckReport(int itemNumber, int amount, int siteID, int trID) throws IllegalStateException, SQLException {
//        TruckingReport tr = activeTruckingReports.get(trID);
//        LinkedList<DeliveryForm> ldfs = activeDeliveryForms.get(trID);
//        boolean added = false;
//
//        for (DeliveryForm df : ldfs) {
//            if (sites.get(siteID).getDeliveryArea() != sites.get(df.getDestination()).getDeliveryArea()) {
//                throw new IllegalStateException("Two different delivery areas");
//            }
//            int origin = items.get(itemNumber).getOriginSiteId();
//
//            if ((df.getDestination() == siteID && df.getOrigin() == origin && df.getItems().containsKey(itemNumber))) {
//                HashMap<Integer, Integer> items = df.getItems();
//                for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
//                    if (entry.getKey() == itemNumber) {
//                        entry.setValue(entry.getValue() + amount);
//                        added = true;
//                        break;
//                    }
//                }
//                if (!added) {
//                    items.put(itemNumber, amount);
//                    added = true;
//                } else break;
//            }
//        }
//        updateTruckReportDestinations(trID);
//        return added;
//    }
//
//    public void replaceDriver(int trID, String driverID) throws SQLException {
//        TruckingReport tr = activeTruckingReports.get(trID);
//        tr.setDriverID(driverID);
//
//    }
//
//    public LinkedList<Demand> getItemOnReport(int trID) throws SQLException {
//        LinkedList<DeliveryForm> ldfs = getDeliveryFormsFromOld(trID);
//        LinkedList<Demand> result = new LinkedList<>();
//        for (DeliveryForm df : ldfs) {
//            HashMap<Integer, Integer> items = df.getItems();
//            for (Map.Entry<Integer, Integer> item : items.entrySet()) {
//                Demand demand = new Demand(item.getKey(), this.items.get(item.getKey()).getOriginSiteId(), item.getValue());
//                result.add(demand);
//            }
//        }
//        return result;
//    }
//
//    private LinkedList<DeliveryForm> getDeliveryFormsFromOld(int trID) {
//        if (currTR.getTRReplace() == trID)
//            return currDF;
//        TruckingReport tr = getReplaceTruckingReport(trID);
//        return activeDeliveryForms.get(tr.getID());
//    }
//
//    public void removeItemFromTruckingReport(int trID, int itemID, int siteID) throws SQLException {
//
//        LinkedList<DeliveryForm> deliveryForms = getUncompletedDeliveryFormsFromOld(trID);
//        int origin = items.get(itemID).getOriginSiteId();
//        for (DeliveryForm df : deliveryForms) {
//            if (df.getDestination() == siteID && df.getOrigin() == origin && df.getItems().containsKey(itemID)) {
//                addDemandToSystem(itemID, siteID, df.getItems().get(itemID));
//                df.getItems().remove(itemID);
//
//                if (df.getItems().isEmpty()) {
//
//                    deliveryForms.remove(df);
//                }
//
//            }
//        }
//        updateTruckReportDestinations(getReplaceTruckingReport(trID).getID());
//        saveReport();
//    }
//
//    public boolean continueAddDemandToTruckReport(int itemNumber, int amount, int siteID, int trId) throws SQLException {
//        LinkedList<DeliveryForm> ldfs = activeDeliveryForms.get(trId);
//        boolean added = false;
//        int origin = items.get(itemNumber).getOriginSiteId();
//        for (DeliveryForm df : ldfs) {
//            if (origin == df.getOrigin() && df.getDestination() == siteID) {
//                HashMap<Integer, Integer> items = df.getItems();
//                for (Map.Entry<Integer, Integer> item : items.entrySet()) {
//                    if (item.getKey() == itemNumber) {
//                        item.setValue(item.getValue() + amount);
//                        added = true;
//                        break;
//                    }
//                }
//            }
//        }
//        if (!added) {
//            HashMap<Integer, Integer> itemsOnDF = new HashMap<>();
//            itemsOnDF.put(itemNumber, amount);
//            int leavingWeight = (int) (items.get(itemNumber).getWeight() * amount);
//            DeliveryForm newDF = new DeliveryForm(ldfs.size() + 1, origin, siteID, itemsOnDF, leavingWeight, trId);
//            ldfs.add(newDF);
//            updateTruckReportDestinations(trId);
//        }
//
//        return true;
//    }
//
//    public void chooseDateToCurrentTR(LocalDate chosen) throws SQLException {
//        if (chosen.isBefore(LocalDate.now()))
//            throw new IllegalArgumentException("time inserted is invalid.");
//        currTR.setDate(chosen);
//    }
//
//    private void updateTruckReportDestinations(int trID) throws SQLException {
//        boolean active = false;
//        TruckingReport tr = currTR;
//        LinkedList<Integer> newDestinations = new LinkedList<>();
//        LinkedList<DeliveryForm> dfs = currDF;
//        if (!(currTR.getID() == trID)) {
//            tr = activeTruckingReports.get(trID);
//            dfs = activeDeliveryForms.get(trID);
//
//        }
//        for (DeliveryForm df : dfs) {
//            if (!dfs.contains(df.getDestination())) {
//                newDestinations.add(df.getDestination());
//            }
//        }
//        tr.setDestinations(newDestinations);
//
//    }
//
//
//    public void removeDemand(int itemID, int site) throws SQLException {
//        for (Demand d : demands) {
//            if (d.getSite() == site && d.getItemID() == itemID) {
//                demands.remove(d);
//                DalDemandController.getInstance().delete(new DalDemand(itemID,d.getAmount(),site));
//                break;
//            }
//        }
//    }
//
//    public int getItemOrigin(int itemID) {
//        return items.get(itemID).getOriginSiteId();
//    }
//
//    public LinkedList<Demand> getCurrentDemands() throws SQLException {
//        LinkedList<Demand> result = new LinkedList<>();
//        for (DeliveryForm df : currDF) {
//            for (Map.Entry<Integer, Integer> entry : df.getItems().entrySet()) {
//                result.add(new Demand(entry.getKey(), df.getDestination(), entry.getValue()));
//            }
//        }
//        return result;
//    }
//
//
//    public void saveReplacedTruckReport() throws SQLException {
//
//        activeTruckingReports.put(currTR.getID(), currTR);
//        activeDeliveryForms.put(currTR.getID(), currDF);
//        saveTruckReportDB(currTR);
//        for (DeliveryForm deliveryForm : currDF) {
//            saveDeliveryFormDB(deliveryForm);
//        }
//        updateDemandsOnDB(demands);
//    }
//
//    public int moveDemandsFromCurrentToReport(int replaceId) throws SQLException {
//
//        /*TruckingReport replace = getReplaceTruckingReport(replaceId);
//        if (replace != null){
//            for(DeliveryForm df: currDF){
//                boolean found = false;
//                for (DeliveryForm rdf:activeDeliveryForms.get(replace.getID())){
//                    if (rdf.getOrigin() == df.getOrigin() && rdf.getDestination() == df.getDestination()){
//                        mergeDeliveryForms(df,rdf);
//                        found = true;
//                    }
//                }
//                if (!found){
//                    activeDeliveryForms.get(replace.getID()).add(df);
//                    activeTruckingReports.get(replace.getID()).getDestinations().add(df.getDestination());
//                }
//            }
//            return replace.getID();
//        }
//        else*/
//
//        currTR.setTRReplace(replaceId);
//        int output = currTR.getID();
//        saveReport();
//        return output;
//
//
//    }
//
//    /**
//     * this method merges 2 delivery forms
//     *
//     * @param df
//     * @param rdf
//     */
//    private void mergeDeliveryForms(DeliveryForm df, DeliveryForm rdf) throws SQLException {
//        if (!(rdf.getOrigin() == df.getOrigin() && rdf.getDestination() == df.getDestination())) {
//            throw new InputMismatchException("delivery forms do not has same origin or destination");
//        }
//        for (Map.Entry<Integer, Integer> dfItem : df.getItems().entrySet()) {
//            boolean found = false;
//            for (Map.Entry<Integer, Integer> rdfItem : rdf.getItems().entrySet()) {
//                if (dfItem.getKey().equals(rdfItem.getKey())) {
//                    rdfItem.setValue(rdfItem.getValue() + dfItem.getValue());
//                    found = true;
//                }
//            }
//            if (!found) {
//                rdf.getItems().put(dfItem.getKey(), dfItem.getValue());
//            }
//
//        }
//        removeCurrTakenDemandsFromTotal();
//
//    }
//
//    private void removeCurrTakenDemandsFromTotal() throws SQLException {
//        for (DeliveryForm deliveryForm : currDF) {
//            if (!deliveryForm.isCompleted()) {
//                HashMap<Integer, Integer> items = deliveryForm.getItems();
//                LinkedList<Demand> toRemove = new LinkedList<>();
//                for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
//                    for (Demand demand : demands) {
//                        if (demand.getItemID() == entry.getKey() && demand.getSite() == deliveryForm.getDestination()) {
//                            demand.setAmount(demand.getAmount() - entry.getValue());
//                            if (demand.getAmount() == 0)
//                                toRemove.add(demand);
//                        }
//                    }
//                }
//                for (Demand d : toRemove) {
//                    demands.remove(d);
//                }
//            }
//        }
//
//    }
//
//    public void setNewTruckToTR(int tRid, String truckNumber) throws SQLException {
//        getTruckReport(tRid).setTruckNumber(truckNumber);
//    }
//
//    public void setNewDriverToTR(int tRid, String driverID) throws SQLException {
//        getTruckReport(tRid).setDriverID(driverID);
//    }
//
//
//    public void makeDeliveryFormUncompleted(int trID, int dfID) throws SQLException {
//        LinkedList<DeliveryForm> deliveryForms = activeDeliveryForms.get(trID);
//        for (DeliveryForm df : deliveryForms) {
//            if (df.getID() == dfID) {
//
//                df.setUncompleted();
//                updateDeliveryFormDB(df);
//            }
//
//        }
//    }
//
//    public LinkedList<DeliveryForm> getUncompletedDeliveryFormsFromOld(int old_id) {
//        TruckingReport replace = getReplaceTruckingReport(old_id);
//        if (replace.getID() == currTR.getID())
//            return currDF;
//
//        return activeDeliveryForms.get(replace.getID());
//    }
//
//    public LinkedList<TruckingReport> getTodayReports() {
//        LinkedList<TruckingReport> result = new LinkedList<>();
//        for (Map.Entry<Integer, TruckingReport> entry : activeTruckingReports.entrySet()) {
//            if (entry.getValue().getDate().equals(LocalDate.now()))
//                result.add(entry.getValue());
//        }
//        return result;
//    }
//
//    public HashMap<String, HashMap<LocalDate, Integer>> getTruckConstraintsFromUpload() {
//        HashMap<String, HashMap<LocalDate, Integer>> result = new HashMap<>();
//        for (Map.Entry<Integer, TruckingReport> entry : activeTruckingReports.entrySet()) {
//            TruckingReport report = getTruckReport(entry.getKey());
//            String truckNumber = report.getTruckNumber();
//            if (result.get(truckNumber) == null)  // checks if already exist for the specific truck
//            {
//                result.put(truckNumber, new HashMap<>());
//            }
//            LocalDate date = report.getDate();
//            int shift = turnTimeToShift(report.getLeavingHour());
//            if (result.get(truckNumber).get(date) == null) {
//                result.get(truckNumber).put(date, shift);
//            } else result.get(truckNumber).put(date, 2);
//        }
//        return result;
//    }
//
//    public HashMap<String, HashMap<LocalDate, Integer>> getDriverConstraintsFromUpload() {
//        HashMap<String, HashMap<LocalDate, Integer>> result = new HashMap<>();
//        for (Map.Entry<Integer, TruckingReport> entry : activeTruckingReports.entrySet()) {
//            TruckingReport report = getTruckReport(entry.getKey());
//            String driverID = report.getDriverID();
//            if (result.get(driverID) == null)  // checks if already exist for the specific truck
//            {
//                result.put(driverID, new HashMap<>());
//            }
//            LocalDate date = report.getDate();
//            int shift = turnTimeToShift(report.getLeavingHour());
//            if (result.get(driverID).get(date) == null) {
//                result.get(driverID).put(date, shift);
//            } else result.get(driverID).put(date, 2);
//        }
//        return result;
//    }
//
//    private int turnTimeToShift(LocalTime shift) {
//        if (shift.isBefore(LocalTime.of(14, 0)))
//            return 0;
//        else
//            return 1;
//    }
//
//    public void upload() throws SQLException {
//        DalItemController dalItemController = DalItemController.getInstance();
//        DalDemandController dalDemandController = DalDemandController.getInstance();
//        DalItemsOnDFController itemsOnDF = DalItemsOnDFController.getInstance();
//        DalSiteController dsc = DalSiteController.getInstance();
//        DalDeliveryFormController delivery = DalDeliveryFormController.getInstance();
//        DalTruckingReportController truckingReport = DalTruckingReportController.getInstance();
//        LinkedList<DalItem> dalItems = dalItemController.load();
//        for (DalItem item : dalItems)                        // item list create
//            items.put(item.getID(), new Item(item));
//        LinkedList<DalSite> dalSites = dsc.load();
//        for (DalSite site : dalSites)                        // site list create
//            sites.put(site.getSiteID(), new Site(site));
//        LinkedList<DalTruckingReport> d_truckingReports = truckingReport.load();
//        // sets the Trucking reports in old/ active as needed
//        LinkedList<DalDemand> dalDemands = dalDemandController.load();
//        for (DalDemand demand : dalDemands) {                 // demand list create
//            demands.add(new Demand(demand));
//        }
//        for (DalTruckingReport dr : d_truckingReports) {
//            activeTruckingReports.put(dr.getID(), new TruckingReport(dr));
//            activeDeliveryForms.put(dr.getID(), new LinkedList<>());
//        }
//        fixActiveAndOldTruckingReports();
//
//        LinkedList<DalDeliveryForm> deliveryForms = delivery.load();
//        for (DalDeliveryForm df : deliveryForms) {
//            DeliveryForm deliveryForm = new DeliveryForm(df);
//            if (activeTruckingReports.containsKey(deliveryForm.getTrID())) {
//                if (activeDeliveryForms.get(deliveryForm.getTrID()) == null)
//                    activeDeliveryForms.put(deliveryForm.getTrID(), new LinkedList<>());
//                activeDeliveryForms.get(deliveryForm.getTrID()).add(deliveryForm);
//
//            } else {
//                if (oldDeliveryForms.get(deliveryForm.getTrID()) == null)
//                    oldDeliveryForms.put(deliveryForm.getTrID(), new LinkedList<>());
//                oldDeliveryForms.get(deliveryForm.getTrID()).add(deliveryForm);
//            }
//
//            getTruckReport(deliveryForm.getTrID()).addDestination(deliveryForm.getDestination());
//
//
//        }
//        LinkedList<DalItemsOnDF> itemsOnDFS = itemsOnDF.load();
//        for (DalItemsOnDF iod : itemsOnDFS) {
//            DeliveryForm df = getDF(iod.getDFID());
//            df.addItem(iod.getItemID(), iod.getAmount());
//        }
//
//
//    }
//
//    private DeliveryForm getDF(int ID) {
//        for (Map.Entry<Integer, LinkedList<DeliveryForm>> entry : activeDeliveryForms.entrySet()) {
//            for (DeliveryForm df : entry.getValue()) {
//                if (df.getID() == ID) {
//                    return df;
//                }
//            }
//        }
//        for (Map.Entry<Integer, LinkedList<DeliveryForm>> entry : oldDeliveryForms.entrySet()) {
//            for (DeliveryForm df : entry.getValue()) {
//                if (df.getID() == ID) {
//                    return df;
//                }
//            }
//        }
//        return null;
//    }
//
//    private void fixActiveAndOldTruckingReports() {
//        HashMap<Integer, TruckingReport> ids = new HashMap<>();
//        for (Map.Entry<Integer, TruckingReport> entry : activeTruckingReports.entrySet()) {
//            int id = entry.getValue().getTRReplace();
//            if (id != -1) {
//                if (!ids.containsKey(id))
//                    ids.put(id, getTruckReport(id));
//            }
//            if (entry.getValue().isCompleted()) {
//                id = entry.getValue().getID();
//                if (!ids.containsKey(id))
//                    ids.put(id, getTruckReport(id));
//            }
//        }
//        oldTruckingReports = ids;
//        for (Map.Entry<Integer, TruckingReport> entry : ids.entrySet()) {
//            activeTruckingReports.remove(entry.getKey());
//            oldDeliveryForms.put(entry.getKey(), new LinkedList<>());
//            activeDeliveryForms.remove(entry.getKey());
//        }
//    }
//
//
//    private void updateDeliveryFormDB(DeliveryForm df) throws SQLException {
//        DalDeliveryFormController.getInstance().update(new DalDeliveryForm
//                (df.getID(), df.getOrigin(), df.getDestination(), df.isCompleted(), df.getLeavingWeight(), df.getTrID()));
//        DalItemsOnDFController controller=DalItemsOnDFController.getInstance();
//        for (Map.Entry<Integer,Integer> entry:df.getItems().entrySet())
//        {
//            controller.update(new DalItemsOnDF(df.getID(),entry.getKey(),entry.getValue()));
//        }
//    }
//
//    private void updateTruckingReportDB(TruckingReport tr) throws SQLException {
//        DalTruckingReportController.getInstance().update(new DalTruckingReport
//                (tr.getID(), tr.getLeavingHour(), tr.getDate(), tr.getTruckNumber(), tr.getDriverID(), tr.getOrigin(), tr.isCompleted(), tr.getTRReplace()));
//    }
//
//    private void saveDeliveryFormDB(DeliveryForm df) throws SQLException {
//        DalDeliveryFormController.getInstance().insert(new DalDeliveryForm(df.getID(), df.getOrigin(), df.getDestination(), df.isCompleted(), df.getLeavingWeight(), df.getTrID()));
//        DalItemsOnDFController controller=DalItemsOnDFController.getInstance();
//        for (Map.Entry<Integer,Integer> entry:df.getItems().entrySet())
//        {
//            controller.insert(new DalItemsOnDF(df.getID(),entry.getKey(),entry.getValue()));
//        }
//    }
//
//    private void saveTruckReportDB(TruckingReport currTR) throws SQLException {
//        DalTruckingReportController.getInstance().insert(new DalTruckingReport(currTR.getID(), currTR.getLeavingHour(),
//                currTR.getDate(), currTR.getTruckNumber(), currTR.getDriverID(), currTR.getOrigin(), currTR.isCompleted(), currTR.getTRReplace()));
//    }
//
//    private void saveDemandDB(Demand demand) throws SQLException {
//        DalDemandController.getInstance().insert(new DalDemand(demand.getItemID(), demand.getAmount(), demand.getSite()));
//    }
//
//    private void updateDemandDB(Demand demand) throws SQLException {
//        DalDemandController.getInstance().update(new DalDemand(demand.getItemID(), demand.getAmount(), demand.getSite()));
//    }
//
//    private void updateDemandsOnDB(LinkedList<Demand> ds) throws SQLException {
//        for (Demand d : ds) {
//            updateDemandDB(d);
//        }
//    }

}
