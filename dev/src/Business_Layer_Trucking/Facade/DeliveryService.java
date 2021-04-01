package Business_Layer_Trucking.Facade;

import Business_Layer_Trucking.Delivery.DeliveryController;
import Business_Layer_Trucking.Delivery.Demand;
import Business_Layer_Trucking.Delivery.TruckingReport;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDeliveryForm;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDemand;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeTruckingReport;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;

public class DeliveryService {
    private DeliveryController dc;


    public DeliveryService(){
        throw new UnsupportedOperationException();
    }

    public void addDemand(int id, int site, int amount) {
        // TODO if first- update origin
    }

    public FacadeDemand addDemandToReport(int demand, int supplyAmount){//TODO- for ido - change it.(if needed! )
        try {
            Demand d = dc.getDemands().get(demand);
            FacadeDemand fc = new FacadeDemand(d);
            // TODO dc.addCurrTR_Destination();
            dc.addItemToDeliveryForm(d, supplyAmount,false);

            return fc;
        }
        catch (Exception e)
        {
            return null;
        }

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
                        String phoneNumber, String contactName) {//TODO- for ido - change it.(if needed! )
        try{
        dc.addSite(city, siteID, deliveryArea, phoneNumber, contactName );
        }
        catch (Exception e)
        {

        }

    }

    public void addItem(int id, int weight, String name) {
        try {
            dc.addItem(id, weight,name);
        }
        catch (Exception e){}
    }

    public void displaySites() {
        dc.displaySites();
    }
}
