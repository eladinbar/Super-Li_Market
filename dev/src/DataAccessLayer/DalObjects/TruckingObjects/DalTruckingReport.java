package DataAccessLayer.DalObjects.TruckingObjects;

import java.time.LocalDate;
import java.time.LocalTime;

public class DalTruckingReport  {
    private int ID;
    private LocalTime leavingHour;
    private LocalDate date;
    private String truckNumber;
    private String driverID;
    private int origin;
    private boolean completed;
    private int replaceTRID;

    public DalTruckingReport(int ID,LocalTime leavingHour,LocalDate date,String truckNumber,String driverID
            ,int origin,boolean completed,int replaceTRID)
    {
        this.ID=ID;
        this.leavingHour=leavingHour;
        this.date=date;
        this.truckNumber=truckNumber;
        this.driverID=driverID;
        this.origin=origin;
        this.completed=completed;
        this.replaceTRID=replaceTRID;
    }

    public int getID() {
        return ID;
    }

    public int getOrigin() {
        return origin;
    }

    public int getReplaceTRID() {
        return replaceTRID;
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

    public String getTruckNumber() {
        return truckNumber;
    }

    public Boolean isCompleted() {
        return completed;
    }
}
