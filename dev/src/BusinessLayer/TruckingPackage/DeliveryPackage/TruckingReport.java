package BusinessLayer.TruckingPackage.DeliveryPackage;

import DataAccessLayer.DalObjects.TruckingObjects.DalTruckingReport;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class TruckingReport {
    private int ID;
    private LocalDate date;
    private LocalTime leavingHour;
    private String  truckNumber;
    private String  driverID;
    private LinkedList<Integer> suppliers;
    private boolean completed;
    private boolean approved;


    public TruckingReport(int ID, LocalDate date, LocalTime leavingHour, String  truckNumber, String driverID
                          , LinkedList<Integer> suppliers)
    {
        this.ID=ID;
        this.date=date;
        this.leavingHour=leavingHour;
        this.truckNumber=truckNumber;
        this.driverID=driverID;
        this.suppliers = suppliers;
        this.approved = false;
        this.completed=false;
    }
    public TruckingReport(int ID){
        this.ID=ID;
        this.suppliers = new LinkedList<>();

    }

    public TruckingReport(DalTruckingReport dtr) throws SQLException {
        this.ID=dtr.getID();
        this.date=dtr.getDate();
        this.leavingHour=dtr.getLeavingHour();
        this.suppliers =new LinkedList<>();
        this.truckNumber=dtr.getTruckNumber();
        this.completed=dtr.isCompleted();
        this.driverID=dtr.getDriverID();
        this.approved = dtr.isApproved();
    }

    public TruckingReport(int lastReportID, TruckingReport oldTr) {
        this.ID = lastReportID;
        this.date=oldTr.date;
        this.leavingHour=oldTr.leavingHour;
        this.truckNumber=oldTr.truckNumber;
        this.driverID=oldTr.driverID;
        this.suppliers =oldTr.suppliers;
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

    public String  getTruckNumber() {
        return truckNumber;
    }

    public LinkedList<Integer> getSuppliers() {
        return suppliers;
    }


    public void setCompleted()  {

        this.completed = true;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved() {
        this.approved = true;
    }

    public void setID(int ID) throws SQLException {
        this.ID = ID;

    }


    public void setDate(LocalDate date)  {
        this.date = date;

    }

    public void setSuppliers(LinkedList<Integer> suppliers) throws SQLException {
        this.suppliers = suppliers;

    }

    public void setDriverID(String driverID)  {
        this.driverID = driverID;

    }

    public void setLeavingHour(LocalTime leavingHour) throws SQLException {
        this.leavingHour = leavingHour;

    }



    public void setTruckNumber(String truckNumber)  {
        this.truckNumber = truckNumber;
/*

        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));
*/

    }

    public void addSupplier(int supplier)  {
        suppliers.add(supplier);

    }

    public boolean isCompleted() {
        return completed;
    }
}
