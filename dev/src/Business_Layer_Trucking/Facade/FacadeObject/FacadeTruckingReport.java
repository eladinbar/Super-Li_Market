package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Delivery.TruckingReport;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;

public class FacadeTruckingReport {
    private int ID;
    private LocalDate date;
    private LocalTime leavingHour;
    private int truckNumber;
    private int driverID;
    private int origin;
    private LinkedList<Integer> destinations;
    private TruckingReport TRReplace;
    private boolean completed;

    public FacadeTruckingReport(int ID,LocalDate date,LocalTime leavingHour,int truckNumber,int driverID,
                          int origin,LinkedList<Integer> destinations,TruckingReport TRReplace)
    {
        this.date=date;
        this.ID=ID;
        this.leavingHour=leavingHour;
        this.truckNumber=truckNumber;
        this.origin=origin;
        this.driverID=driverID;
        this.destinations=destinations;
        this.TRReplace=TRReplace;
        this.completed=false;


    }

    public FacadeTruckingReport(TruckingReport currTR) {

        this.date=currTR.getDate();
        this.ID=currTR.getID();
        this.leavingHour=currTR.getLeavingHour();
        this.truckNumber=currTR.getTruckNumber();
        this.origin=currTR.getOrigin();
        this.driverID=currTR.getDriverID();
        this.destinations= currTR.getDestinations();
        this.TRReplace=currTR.getTRReplace();
        this.completed=false;

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

    public void setTRReplace(TruckingReport TRReplace) {
        this.TRReplace = TRReplace;
    }

    public void setTruckNumber(int truckNumber) {
        this.truckNumber = truckNumber;
    }

}
