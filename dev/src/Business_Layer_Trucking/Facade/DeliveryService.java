package Business_Layer_Trucking.Facade;

import Business_Layer_Trucking.Delivery.DeliveryController;
import Business_Layer_Trucking.Delivery.Demand;
import Business_Layer_Trucking.Delivery.TruckingReport;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDeliveryForm;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDemand;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeTruckingReport;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DeliveryService {
    private DeliveryController dc;


    public DeliveryService(){

        this.dc = new DeliveryController();
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

    public void chooseLeavingHour(LocalDateTime leavingHour) {//TODO- for ido - change it.(if needed! )

        try {
            dc.chooseLeavingHour(leavingHour);
        }
        catch (Exception e){}

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

    public LinkedList<FacadeDemand> getItemsOnTruck() {
        // TODO need to be impelemted, need to turn Demands into FacadeDemand

        dc.getCurrDF();
        return null;
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

    public List<FacadeDemand> showDemands() {
        // TODO returns null if none exist
        // TODO this method should return linked list of all the existing demands( that haven't been chosen in the current DF)
        return null;
    }

    public String getItemName(int itemID) {
        // TODO exception if not exist
        return  null;
    }
}
