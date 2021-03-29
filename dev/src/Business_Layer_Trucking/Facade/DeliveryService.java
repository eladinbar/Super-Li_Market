package Business_Layer_Trucking.Facade;

import Business_Layer_Trucking.Delivery.DeliveryController;
import Business_Layer_Trucking.Delivery.Demand;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDemand;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeTruckingReport;

import java.util.Date;

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
}
