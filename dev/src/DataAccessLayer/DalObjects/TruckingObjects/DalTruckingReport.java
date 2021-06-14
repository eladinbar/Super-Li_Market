package DataAccessLayer.DalObjects.TruckingObjects;

import BusinessLayer.TruckingPackage.DeliveryPackage.TruckingReport;

import java.time.LocalDate;
import java.time.LocalTime;

public class DalTruckingReport  {
    private int ID;
    private LocalTime leavingHour;
    private LocalDate date;
    private String truckNumber;
    private String driverID;
    private boolean completed;
    private boolean approved;

    public DalTruckingReport(){}

    public DalTruckingReport(int ID,LocalTime leavingHour,LocalDate date,String truckNumber,String driverID
            ,boolean completed,boolean approved)
    {
        this.ID=ID;
        this.leavingHour=leavingHour;
        this.date=date;
        this.truckNumber=truckNumber;
        this.driverID=driverID;
        this.completed=completed;
        this.approved=approved;
    }

    public int getID() {
        return ID;
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

    public boolean isApproved() {
        return approved;
    }
}
