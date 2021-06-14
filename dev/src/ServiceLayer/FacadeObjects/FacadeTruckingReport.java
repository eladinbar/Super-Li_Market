package ServiceLayer.FacadeObjects;

import BusinessLayer.TruckingPackage.DeliveryPackage.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class FacadeTruckingReport {
    private int ID;
    private LocalDate date;
    private LocalTime leavingHour;
    private String  truckNumber;
    private String driverID;
    private LinkedList<String> suppliers;
    private boolean completed;
    private boolean approved;

    public FacadeTruckingReport(int ID, LocalDate date, LocalTime leavingHour, String truckNumber, String driverID,
                              LinkedList<String> suppliers , boolean approved)
    {
        this.ID = ID;
        this.date = date;
        this.leavingHour = leavingHour;
        this.truckNumber = truckNumber;
        this.driverID = driverID;
        this.suppliers = suppliers;
        this.approved = false;
        this.completed=false;

    }

    public FacadeTruckingReport(TruckingReport currTR) {
        this.ID = currTR.getID();
        date = currTR.getDate();
        leavingHour = currTR.getLeavingHour();
        truckNumber = currTR.getTruckNumber();
        driverID = currTR.getDriverID();
        suppliers = currTR.getSuppliers();
        approved = currTR.isApproved();
        completed = currTR.isCompleted();

    }

    public FacadeTruckingReport(int id) {
        this.ID = id;
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

    public LinkedList<String> getSuppliers() {
        return suppliers;
    }


    public void setCompleted() {
        this.completed = true;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setSuppliers(LinkedList<String> suppliers) {
        this.suppliers = suppliers;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public void setLeavingHour(LocalTime leavingHour) {
        this.leavingHour = leavingHour;
    }


    public void setTruckNumber(String  truckNumber) {
        this.truckNumber = truckNumber;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public boolean isApproved() {
        return approved;
    }

    public boolean isCompleted() {
        return completed;
    }
}
