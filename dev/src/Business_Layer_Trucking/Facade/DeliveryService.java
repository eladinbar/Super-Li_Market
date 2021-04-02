package Business_Layer_Trucking.Facade;

import Business_Layer_Trucking.Delivery.DeliveryController;
import Business_Layer_Trucking.Delivery.Demand;
import Business_Layer_Trucking.Delivery.Site;
import Business_Layer_Trucking.Delivery.TruckingReport;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDeliveryForm;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDemand;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeSite;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeTruckingReport;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;

public class DeliveryService {
    private DeliveryController dc;
    private static DeliveryService instace = null;


    private DeliveryService(){

        this.dc = DeliveryController.getInstance();
    }

    public static DeliveryService getInstance() {
        if (instace == null)
            instace =  new DeliveryService();
        return instace;
    }



    /*    public void addDemand(int id, int site, int amount) {
        Demand demand =  new Demand(id, site, amount);
        dc.addItemToDeliveryForm(demand,);
    }*/

    public FacadeDemand addDemandToReport(int itemID, int supplyAmount, int siteID)  throws IllegalStateException{
        LinkedList<Demand> demands = dc.getDemands();
        Demand d=null;
        for (Demand curr:  demands) {
            if (curr.getItemID() ==  itemID && curr.getAmount() < supplyAmount && curr.getSite() == siteID){
                d = curr;
            }
        }
        if (d == null){
            throw new IllegalArgumentException("one of arguments doesn't match");
        }
        FacadeDemand fc = new FacadeDemand(d);
        dc.addItemToDeliveryForm(d, supplyAmount, false);
        return fc;


    }



    public void createTruckingReport() {
        dc.createNewTruckingReport();

    }

    public void chooseLeavingHour(LocalTime leavingHour) {//TODO- for ido - change it.(if needed! )

            dc.chooseLeavingHour(leavingHour);


    }

    public FacadeTruckingReport getCurrTruckingReport() {
        return new FacadeTruckingReport(dc.getCurrTR());
    }

    public void saveReport() {
        dc.saveReport();
    }

    public void continueAddDemandToReport(int first, int second){
        //TODO implement
        // notice i changed implementation way : from now "downwards" united continueAddDemandToReport and
        //  addItemToDeliveryForm to one method and added boolean
        //get the right demand from the demands according to first
        //dc.addItemToDeliveryForm(first,second,true);
    }

    public TruckingReport getTruckReport(int trNumber) {//TODO- for ido - change it.(if needed! )
        try {
            return dc.getTruckReport(trNumber);
        }
        catch (Exception e){
            return null;
        }

    }

    public FacadeDeliveryForm getDeliveryForm(int dfNumber) {
        return dc.getDeliveryForm(dfNumber);
    }

    public void removeDestination(int site) {//TODO- for ido - change it.(if needed! )
        try {
            dc.removeDestination(site);
        }
        catch (Exception e ){}
    }

    public void removeItemFromReport(int item) {
        dc.removeItemFromReport(item);
    }
    public void removeItemFromPool(int item) {
        try {
            dc.removeItemFromPool(item);
        }
        catch (Exception e){}
    }

    /**
     *
     * @return a LinkedList of Facade Demands, that holds all the items in the current Trucking Report in build
     */
    public LinkedList<FacadeDemand> getItemsOnTruck() {
        LinkedList<Demand> demands = dc.getItemsOnTruck();
        LinkedList<FacadeDemand> output = new LinkedList<>();
        for (Demand d : demands) {
            output.add(new FacadeDemand(d));
        }
        return output;
    }

    public void addSite(String city, int siteID, int deliveryArea,
                        String phoneNumber, String contactName) throws KeyAlreadyExistsException {

        dc.addSite(city, siteID, deliveryArea, phoneNumber, contactName );
    }

    public void addItem(int id, int weight, String name) throws  KeyAlreadyExistsException {dc.addItem(id, weight,name);}

    public void displaySites() {
        dc.displaySites();
    }

    public int getItemWeight(int itemID) {
        return dc.getItemWeight(itemID);
    }

    public LinkedList<FacadeDemand> showDemands() {
        LinkedList < FacadeDemand> output = new LinkedList<>();
        LinkedList<Demand> demands=  dc.showDemands();
        for ( Demand d : demands) output.add(new FacadeDemand(d));
        return output;
    }

    public String getItemName(int itemID) {return  dc.getItemName(itemID);}

    public String getSiteName(int site) { return dc.getSiteName(site);    }

    public void closeReport() {
        // TODO this method is built if we want to stop building report, maybe not needed.
    }

    public LinkedList<FacadeSite> getSites() {
        HashMap<Integer, Site> sites = dc.getSites();
        LinkedList<FacadeSite> output = new LinkedList<>();
        for (HashMap.Entry<Integer, Site> entry : sites.entrySet()){
            output.add(new FacadeSite(entry.getValue()));
        }
        return output;
    }

    public LinkedList<FacadeSite> getCurrentSites() {
        LinkedList<Site>  sites = dc.getCurrentSites();
        LinkedList<FacadeSite> output =  new LinkedList<>();
        for (Site site: sites){
            output.add(new FacadeSite(site));
        }

        return output;
    }

    public LinkedList<FacadeDemand> getCurrentDemands() {
        LinkedList<Demand> demands =  dc.getDemands();
        LinkedList<FacadeDemand> output =  new LinkedList<>();
        for (Demand d : demands){
            output.add(new FacadeDemand(d));
        }
        return output;
    }
}
