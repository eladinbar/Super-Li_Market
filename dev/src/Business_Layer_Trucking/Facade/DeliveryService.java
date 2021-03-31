package Business_Layer_Trucking.Facade;

import Business_Layer_Trucking.Delivery.DeliveryController;
import Business_Layer_Trucking.Delivery.Demand;
import Business_Layer_Trucking.Delivery.TruckingReport;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDeliveryForm;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDemand;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeTruckingReport;

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

    public FacadeDemand addDemandToReport(int demand, int supplyAmount) {
        Demand d = dc.getDemands().get(demand);
        FacadeDemand fc = new FacadeDemand(d);
        // TODO dc.addCurrTR_Destination();
        dc.addItemToDeliveryForm(d, supplyAmount);

        return fc;
    }

    public void createTruckingReport() {
        dc.createNewTruckingReport();

    }

    public void chooseLeavingHour(Date leavingHour) {
        dc.chooseLeavingHour(leavingHour);
    }

    public FacadeTruckingReport getCurrTruckingReport() {
        return new FacadeTruckingReport(dc.getCurrTR());
    }

    public void saveReport() {
        dc.saveReport();
    }

    public void continueAddDemandToReport(int first, int second) {
        dc.continueAddDemandToReport(first,second);
    }

    public TruckingReport getTruckReport(int trNumber) {
        return dc.getTruckReport(trNumber);
    }

    public FacadeDeliveryForm getDeliveryForm(int dfNumber) {
        return dc.getDeliveryForm(dfNumber);
    }

    public void removeDestination(int site) {
        dc.removeDestination(site);
    }

    public void removeItem(int item) {
        dc.removeItem(item);
    }

    public LinkedList<FacadeDemand> getItemsOnTruck() {
        // TODO need to be impelemted, need to turn Demands into FacadeDemand

        dc.getCurrDF();
        return null;
    }

    public void addSite(String city, int siteID, int deliveryArea,
                        String phoneNumber, String contactName) {
        dc.addSite(city, siteID, deliveryArea, phoneNumber, contactName );

    }

    public void addItem(int id, int weight, String name) {
        dc.addItem(id, weight,name);
    }
}
