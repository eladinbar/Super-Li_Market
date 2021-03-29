package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Delivery.TruckingReport;

import java.util.Date;
import java.util.LinkedList;

public class FacadeTruckingReport {
    private int ID;
    private Date date;
    private Date leavingHour;
    private int truckNumber;
    private int driverID;
    private int origin;
    private LinkedList<Integer> destinations;
    private TruckingReport TRReplace;
    private boolean completed;

    public FacadeTruckingReport(int ID,Date date,Date leavingHour,int truckNumber,int driverID,
                          int origin,LinkedList<Integer> destinations,TruckingReport TRReplace)
    {
        // TODO need to be completed
        this.completed=false;
        throw new UnsupportedOperationException();

    }

    public FacadeTruckingReport(TruckingReport currTR) {

    }

    public int getID() {
        return ID;
    }

    public int getOrigin() {
        return origin;
    }

    public Date getDate() {
        return date;
    }

    public Date getLeavingHour() {
        return leavingHour;
    }

    public int getDriverID() {
        return driverID;
    }

    public int getTruckNumber() {
        return truckNumber;
    }

    public LinkedList<Integer> getDestinations() {
        return destinations;
    }

    public TruckingReport getTRReplace() {
        return TRReplace;
    }

    public void setCompleted() {
        this.completed = true;
    }
    //TODO:check when to set trucking report

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDestinations(LinkedList<Integer> destinations) {
        this.destinations = destinations;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public void setLeavingHour(Date leavingHour) {
        this.leavingHour = leavingHour;
    }

    public void setTRReplace(TruckingReport TRReplace) {
        this.TRReplace = TRReplace;
    }

    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }

}
