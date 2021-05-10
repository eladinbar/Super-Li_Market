package Trucking.Business_Layer_Trucking.Facade;

import Trucking.Business_Layer_Trucking.Delivery.*;
import Trucking.Business_Layer_Trucking.Facade.FacadeObject.*;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

public class DeliveryService {
    private DeliveryController dc;
    private static DeliveryService instace = null;


    private DeliveryService() {

        this.dc = DeliveryController.getInstance();
    }

    public static DeliveryService getInstance() {
        if (instace == null)
            instace = new DeliveryService();
        return instace;
    }



    /*    public void addDemand(int id, int site, int amount) {
        Demand demand =  new Demand(id, site, amount);
        dc.addItemToDeliveryForm(demand,);
    }*/

    public FacadeDemand addDemandToReport(int itemID, int supplyAmount, int siteID) throws IllegalStateException, IllegalArgumentException, SQLException {
        LinkedList<Demand> demands = dc.getDemands();
        Demand d = null;
        for (Demand curr : demands) {

            if (curr.getItemID() == itemID && curr.getAmount() >= supplyAmount && curr.getSite() == siteID) {
                d = curr;
            }
        }
        if (d == null) {
            throw new IllegalArgumentException("one of arguments doesn't match");
        }
        FacadeDemand fc = new FacadeDemand(d);
        dc.addItemToDeliveryForm(d, supplyAmount, false, siteID);
        return fc;


    }



    public int  createTruckingReport() throws SQLException {
        return dc.createNewTruckingReport();

    }

    public void chooseLeavingHour(LocalTime leavingHour) throws IllegalArgumentException, SQLException {

        dc.chooseLeavingHour(leavingHour);


    }

    public FacadeTruckingReport getCurrTruckingReport() {
        return new FacadeTruckingReport(dc.getCurrTR());
    }

    public void saveReport() throws SQLException {
        dc.saveReport();
    }



    public FacadeDemand continueAddDemandToReport(int itemID, int supplyAmount, int siteID) throws SQLException {
        LinkedList<Demand> demands = dc.getDemands();
        Demand d = null;
        for (Demand curr : demands) {
            if (curr.getItemID() == itemID && curr.getAmount() > supplyAmount && curr.getSite() == siteID) {
                d = curr;
            }
        }
        if (d == null) {
            throw new IllegalArgumentException("one of arguments doesn't match");
        }
        FacadeDemand fc = new FacadeDemand(d);
        dc.addItemToDeliveryForm(d, supplyAmount, true, siteID);
        return fc;
    }

    public FacadeTruckingReport getTruckReport(int trNumber) throws NoSuchElementException {
        return new FacadeTruckingReport(dc.getTruckReport(trNumber));
    }

    public FacadeDeliveryForm getDeliveryForm(int dfNumber, int trNumber) throws IllegalArgumentException, NoSuchElementException {
        return new FacadeDeliveryForm(dc.getDeliveryForm(dfNumber, trNumber));
    }

    public void removeDestination(int site) throws NoSuchElementException, SQLException {
        dc.removeDestination(site);

    }

    public void removeItemFromReport(FacadeDemand demand, int amount) throws SQLException {

        dc.removeItemFromReport(new Demand(demand.getItemID(), demand.getSite(), amount));
    }
    public void removeItemFromPool(int item) throws NoSuchElementException,SQLException{
        dc.removeItemFromPool(item);
    }

    /**
     * @return a LinkedList of Facade Demands, that holds all the items in the current Trucking Report in build
     */
    public LinkedList<FacadeDemand> getItemsOnTruck() throws SQLException {
        LinkedList<Demand> demands = dc.getItemsOnTruck();
        LinkedList<FacadeDemand> output = new LinkedList<>();
        for (Demand d : demands) {
            output.add(new FacadeDemand(d));
        }
        return output;
    }

    public void addSite(String city,  int deliveryArea,
                        String phoneNumber, String contactName,String name) throws KeyAlreadyExistsException, SQLException {

        dc.addSite(city, deliveryArea, phoneNumber, contactName, name);
    }

    public void addItem( double weight, String name, int siteID) throws NoSuchElementException, KeyAlreadyExistsException, SQLException {dc.addItem( weight,name,siteID);}

    public void displaySites() {
        dc.displaySites();
    }

    public double getItemWeight(int itemID) {
        return dc.getItemWeight(itemID);
    }

    public LinkedList<FacadeDemand> showDemands() throws NoSuchElementException, SQLException {
        LinkedList<Demand> demands = new LinkedList<>();
        LinkedList<DeliveryForm> dfs = new LinkedList<>();

        demands = dc.getDemands();
        dfs = dc.getCurrDF();
        LinkedList<FacadeDemand> output = new LinkedList<>();

        if (!demands.isEmpty()) {
            for (Demand d : demands) {
                boolean added = false;

                if (demands.isEmpty()) {
                    for (Demand demand : demands) {
                        output.add((new FacadeDemand(demand)));
                        return output;
                    }
                }
                for (DeliveryForm df : dfs) {
                    if (!df.isCompleted()) {
                        if (df.getItems().containsKey(d.getItemID()) && df.getDestination() == d.getSite()) {
                            added = true;
                            int newAmount = d.getAmount() - df.getItems().get(d.getItemID());
                            if (newAmount > 0) {
                                Demand toAdd = new Demand(d.getItemID(), d.getSite(), newAmount);
                                output.add(new FacadeDemand(toAdd));

                            }
                        }
                    }

                }
                if (!added)
                    output.add(new FacadeDemand(d));
            }
            return output;
        }

        throw new NoSuchElementException("no more demands found yet");


    }

    public String getItemName(int itemID) {
        return dc.getItemName(itemID);
    }

    public String getSiteName(int site) {
        return dc.getSiteName(site);
    }


    public LinkedList<FacadeSite> getSites() {
        HashMap<Integer, Site> sites = dc.getSites();
        LinkedList<FacadeSite> output = new LinkedList<>();
        for (HashMap.Entry<Integer, Site> entry : sites.entrySet()) {
            output.add(new FacadeSite(entry.getValue()));
        }
        return output;
    }

    public LinkedList<FacadeSite> getCurrentSites() {
        LinkedList<Site> sites = dc.getCurrentSites();
        LinkedList<FacadeSite> output = new LinkedList<>();
        for (Site site : sites) {
            output.add(new FacadeSite(site));
        }

        return output;
    }

    /**
     * @param site
     * @return returns only demands associated to this site
     */
    public LinkedList<FacadeDemand> getCurrentDemands(FacadeSite site) throws SQLException {
        LinkedList<Demand> demands =  dc.getCurrentDemands();
        LinkedList<FacadeDemand> output =  new LinkedList<>();
        for (Demand d : demands){
            if (d.getSite() == site.getSiteID() || dc.getItemOrigin(d.getItemID()) == site.getSiteID())
                output.add(new FacadeDemand(d));
        }
        return output;
    }

    /**
     * @return @list of all items in the trucking system
     */
    public LinkedList<FacadeItem> getAllItems() {
        LinkedList<Item> items = dc.getItems();

        LinkedList<FacadeItem> facadeItems = new LinkedList<>();
        for (Item item : items) {
            facadeItems.add(new FacadeItem(item));
        }
        return facadeItems;
    }

    public LinkedList<FacadeSite> getAllSites() throws NoSuchElementException {
        LinkedList<Site> sites = dc.getAllSites();
        LinkedList<FacadeSite> facadeSites = new LinkedList<>();
        for (Site s : sites) {
            facadeSites.add(new FacadeSite(s));
        }
        return facadeSites;
    }

    public void addDemandToSystem(int itemId, int site, int amount) throws NoSuchElementException, SQLException {
        dc.addDemandToSystem(itemId,site,amount);
    }

    public int getWeightOfCurrReport() {
        return dc.getWeightOfCurrReport();
    }

    public LinkedList<FacadeTruckingReport> getActiveTruckingReports() {
        LinkedList<FacadeTruckingReport> result = new LinkedList<>();
        LinkedList<TruckingReport> reports = dc.getActiveTruckingReports();
        for (TruckingReport tr : reports) {
            result.add(new FacadeTruckingReport(tr));
        }
        return result;


    }

    public LinkedList<FacadeDeliveryForm> getDeliveryForms(int trID) {
        LinkedList<FacadeDeliveryForm> result = new LinkedList<>();
        LinkedList<DeliveryForm> deliveryForms = dc.getDeliveryForms(trID);
        for (DeliveryForm df : deliveryForms) {
            result.add(new FacadeDeliveryForm(df));
        }
        return result;

    }

    public void updateDeliveryFormRealWeight(int trID,int dfID, int weight) throws IllegalStateException, SQLException {
        dc.updateDeliveryFormRealWeight(trID,dfID,weight);
    }

    public boolean checkIfAllCompleted(int trID) {
        return dc.checkIfAllCompleted(trID);
    }

    public void archive(int trID) throws SQLException {
        dc.archive(trID);
    }

    public void archiveNotCompleted(int trID) throws SQLException {
        dc.archiveNotCompleted(trID);
    }

    public int getSiteDeliveryArea(int site) {
        return dc.getSiteDeliveryArea(site);
    }

    public void removeSiteFromTruckReport(int siteID, int trID) throws NoSuchElementException, SQLException {
        dc.removeSiteFromTruckReport(siteID,trID);
    }

    public boolean addDemandToTruckReport(int itemNumber, int amount, int siteID, int trID) throws IllegalStateException, SQLException {
        return dc.addDemandToTruckReport(itemNumber, amount,siteID,trID);
    }

    public void replaceDriver(int trID, String driverID) throws SQLException {
        dc.replaceDriver(trID,driverID);
    }

    public LinkedList<FacadeDemand> getItemOnReport(int trID) throws SQLException {
        LinkedList<Demand> demands=dc.getItemOnReport(trID);
        LinkedList<FacadeDemand> result=new LinkedList<>();
        for (Demand d:demands)
        {
            result.add(new FacadeDemand(d));
        }
        return result;
    }

    public void removeItemFromTruckingReport(int trID, FacadeDemand demand) throws SQLException {
        dc.removeItemFromTruckingReport(trID,demand.getItemID(),demand.getSite());
    }

    public boolean continueAddDemandToTruckReport(int itemNumber, int amount, int siteID, int truckId) throws SQLException {
        return dc.continueAddDemandToTruckReport(itemNumber,amount,siteID,truckId);
    }


    public void chooseDateToCurrentTR(LocalDate chosen) throws SQLException {
        dc.chooseDateToCurrentTR(chosen);
    }

    public void removeSiteFromPool(int siteID) throws NoSuchElementException, IllegalStateException,SQLException{
        dc.removeSite(siteID);
    }

    public void chooseDriver(String driver) throws SQLException {
        dc.updateCurrTR_DriverID(driver);
    }

    public void chooseTruck(String truck) throws SQLException {
        dc.updateCurrTR_TruckNumber(truck);
    }

    public void removeDemand(FacadeDemand d) throws SQLException{
        dc.removeDemand(d.getItemID(), d.getSite());
    }

    public LinkedList<FacadeDeliveryForm> getUnComplitedDeliveryForms(int trId) {
        LinkedList<DeliveryForm> deliveryForms = dc.getDeliveryForms(trId);
        LinkedList<FacadeDeliveryForm> facadeDeliveryForms = new LinkedList<>();
        for (DeliveryForm d : deliveryForms) {
            if (!d.isCompleted()) {
                facadeDeliveryForms.add(new FacadeDeliveryForm(d));
            }
        }
        return facadeDeliveryForms;
    }

    public FacadeTruckingReport getNewTruckReport(FacadeTruckingReport oldTr) throws SQLException {
        TruckingReport old = dc.getReplaceTruckingReport(oldTr.getID());
        if (old != null) {
            return new FacadeTruckingReport(old);
        } else {
            createTruckingReport();
            FacadeTruckingReport toRet = new FacadeTruckingReport(dc.getCurrTR());

            saveReportReplacedTruckReport();
            return toRet;
        }
    }


    public void saveReportReplacedTruckReport() throws SQLException {
        dc.saveReplacedTruckReport();
    }

    /**
     * this method transfers the new chosen demands into an active trucking report
     * in case there is no replace active report, such as no demands left, creates a new one
     *
     * @param tr the Trucking report the active trucking report is replacing
     * @return true if it could find an active trucking report, returns false otherwise.
     */
    public int moveDemandsFromCurrentToReport(FacadeTruckingReport tr) throws SQLException {
        return dc.moveDemandsFromCurrentToReport(tr.getID());

    }

    public void setNewTruckToTR(int TRid, String truckNumber) throws SQLException {
        dc.setNewTruckToTR(TRid,truckNumber);
    }
    public void setNewDriverToTR(int TRid, String driverID) throws SQLException {
        dc.setNewDriverToTR(TRid,driverID);
    }

    public LinkedList<FacadeDemand> getAllDemands() {

        LinkedList<FacadeDemand> fd = new LinkedList<>();
        LinkedList<Demand> demands = dc.getDemands();
        for (Demand d : demands) {
            fd.add(new FacadeDemand(d));
        }
        return fd;

    }

    public void makeDeliveryFormUncompleted(int trID, int dfID) throws SQLException {
        dc.makeDeliveryFormUncompleted(trID, dfID);
    }

    public LinkedList<FacadeDeliveryForm> getUncompletedDeliveryFormsFromOld(int old_id) {
        LinkedList<DeliveryForm> deliveryForms = dc.getUncompletedDeliveryFormsFromOld(old_id);
        LinkedList<FacadeDeliveryForm> fdf = new LinkedList<>();
        for (DeliveryForm dfs : deliveryForms) {
            fdf.add(new FacadeDeliveryForm(dfs));
        }
        return fdf;
    }

    public LinkedList<FacadeDemand> getUnCompletedItemOnReportByOld(int id) {
        LinkedList<DeliveryForm> fdf = dc.getUncompletedDeliveryFormsFromOld(id);
        LinkedList<FacadeDemand> fd = new LinkedList<>();
        for (DeliveryForm df : fdf) {
            for (Map.Entry<Integer, Integer> entry : df.getItems().entrySet()) {
                fd.add(new FacadeDemand(entry.getKey(), df.getDestination(), entry.getValue()));
            }
        }
        return fd;
    }

    public LinkedList<FacadeTruckingReport> getOldDTruckingReports() {
        HashMap<Integer, TruckingReport> oldTruckingReport = dc.getOldTruckingReports();
        LinkedList<FacadeTruckingReport> output = new LinkedList<>();
        for (Map.Entry<Integer, TruckingReport> tr : oldTruckingReport.entrySet()) {
            output.add(new FacadeTruckingReport(tr.getValue()));
        }
        return output;
    }

    public LinkedList<FacadeTruckingReport> getTodayReports() {
        LinkedList<FacadeTruckingReport> ftr = new LinkedList<>();
        for (TruckingReport tr : dc.getTodayReports())
            ftr.add(new FacadeTruckingReport(tr));
        return ftr;
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
}


