package Business_Layer_Trucking.Delivery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;

public class TruckingReport {
    private int ID;
    private LocalDate date;
    private LocalTime leavingHour;
    private int truckNumber;
    private int driverID;
    private int origin;
    private LinkedList<Integer> destinations;
    private TruckingReport TRReplace;
    private boolean completed;

    public TruckingReport(int ID, LocalDate date, LocalDateTime leavingHour, int truckNumber, int driverID,
                          int origin, LinkedList<Integer> destinations, TruckingReport TRReplace)
    {
        // TODO need to be completed
        this.completed=false;
        throw new UnsupportedOperationException();

    }
    public TruckingReport(){    }

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

    public void addDestination(int destination) throws Exception {
         try {
             destinations.add(destination);
         }
         catch (Exception e)
        {
            throw new Exception(e.getMessage());
        }

    }
}
