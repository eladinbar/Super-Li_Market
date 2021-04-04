package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Delivery.TruckingReport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class FacadeTruckingReport {
    private int ID;
    private LocalDate date;
    private LocalTime leavingHour;
    private int truckNumber;
    private int driverID;
    private int origin;
    private LinkedList<Integer> destinations;
    private FacadeTruckingReport TRReplace;
    private boolean completed;

    public FacadeTruckingReport(int ID, LocalDate date, LocalTime leavingHour, int truckNumber, int driverID,
                                int origin, LinkedList<Integer> destinations, TruckingReport TRReplace)
    {
        this.ID = ID;
        this.date = date;
        this.leavingHour = leavingHour;
        this.truckNumber = truckNumber;
        this.driverID = driverID;
        this.origin = origin;
        this.destinations = destinations;
        this.TRReplace = new FacadeTruckingReport(TRReplace);

        this.completed=false;

    }

    public FacadeTruckingReport(TruckingReport currTR) {
        this.ID = currTR.getID();
        date = currTR.getDate();
        leavingHour = currTR.getLeavingHour();
        truckNumber = currTR.getTruckNumber();
        driverID = currTR.getDriverID();
        origin = currTR.getOrigin();
        destinations = currTR.getDestinations();
        TRReplace = new FacadeTruckingReport( currTR.getTRReplace());
        completed = currTR.isCompleted();

    }

    public FacadeTruckingReport(int id) {
        this.ID = id;
    }

    public int getID() {
        return ID;
    }

    public int getOrigin() {
        return origin;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getLeavingHour() {
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

    public FacadeTruckingReport getTRReplace() {
        return TRReplace;
    }

    public void setCompleted() {
        this.completed = true;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setOrigin(int origin) {
        this.origin = origin;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setDestinations(LinkedList<Integer> destinations) {
        this.destinations = destinations;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public void setLeavingHour(LocalTime leavingHour) {
        this.leavingHour = leavingHour;
    }

    public void setTRReplace(FacadeTruckingReport TRReplace) {
        this.TRReplace = TRReplace;
    }

    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }

}
