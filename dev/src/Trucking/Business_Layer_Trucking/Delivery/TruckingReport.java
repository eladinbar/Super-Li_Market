package Trucking.Business_Layer_Trucking.Delivery;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class TruckingReport {
    private int ID;
    private LocalDate date;
    private LocalTime leavingHour;
    private String  truckNumber;
    private String  driverID;
    private int origin;
    private LinkedList<Integer> destinations;
    private TruckingReport TRReplace;
    private boolean completed;


    public TruckingReport(int ID, LocalDate date, LocalTime leavingHour, String  truckNumber, String driverID,
                          int origin, LinkedList<Integer> destinations, TruckingReport TRReplace)
    {
        this.ID=ID;
        this.date=date;
        this.leavingHour=leavingHour;
        this.truckNumber=truckNumber;
        this.driverID=driverID;
        this.origin=origin;
        this.destinations=destinations;
        this.TRReplace=TRReplace;
    }
    public TruckingReport(int ID){
        this.ID=ID;
        this.destinations = new LinkedList<>();

    }

    public TruckingReport(int lastReportID, TruckingReport oldTr) {
        this.ID = lastReportID;
        this.date=oldTr.date;
        this.leavingHour=oldTr.leavingHour;
        this.truckNumber=oldTr.truckNumber;
        this.driverID=oldTr.driverID;
        this.origin=oldTr.origin;
        this.destinations=oldTr.destinations;
        this.TRReplace=oldTr;
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

    public String getDriverID() {
        return driverID;
    }

    public String  getTruckNumber() {
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

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public void setLeavingHour(LocalTime leavingHour) {
        this.leavingHour = leavingHour;
    }

    public void setTRReplace(TruckingReport TRReplace) {
        this.TRReplace = TRReplace;
    }

    public void setTruckNumber(String truckNumber) {
        this.truckNumber = truckNumber;
    }

    public void addDestination(int destination)  {
        destinations.add(destination);
    }

    public boolean isCompleted() {
        return completed;
    }
}
