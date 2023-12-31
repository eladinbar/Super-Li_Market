package BusinessLayer.TruckingPackage.DeliveryPackage;

import DataAccessLayer.DalControllers.TruckingControllers.DalTruckingReportController;
import DataAccessLayer.DalObjects.TruckingObjects.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import static java.lang.System.exit;

public class TruckingReport {
    private int ID;
    private LocalDate date;
    private LocalTime leavingHour;
    private String  truckNumber;
    private String  driverID;
    private LinkedList<String> suppliers;
    private boolean completed;
    private boolean approved;

    public TruckingReport(int ID, LocalDate date, LocalTime leavingHour, String  truckNumber, String driverID
                          , LinkedList<String> suppliers) throws SQLException {
        this.ID=ID;
        this.date=date;
        this.leavingHour=leavingHour;
        this.truckNumber=truckNumber;
        this.driverID=driverID;
        this.suppliers = suppliers;
        this.approved = false;
        this.completed=false;

        DalTruckingReportController.getInstance().insert(new DalTruckingReport(this.ID,this.leavingHour,this.date,this.truckNumber,this.driverID
                    ,this.completed,this.approved));

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

    public LinkedList<String> getSuppliers() {
        return suppliers;
    }

    public void setCompleted()  {
        this.completed = true;
        try {
            DalTruckingReportController.getInstance().update
                    (new DalTruckingReport(this.ID,this.leavingHour,this.date,this.truckNumber,this.driverID
                            ,this.completed,this.approved));
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved() throws SQLException {
        this.approved = true;
        try {
            DalTruckingReportController.getInstance().update
                    (new DalTruckingReport(this.ID,this.leavingHour,this.date,this.truckNumber,this.driverID
                            ,this.completed,this.approved));
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public void setDate(LocalDate date)  {
        this.date = date;
        try {
            DalTruckingReportController.getInstance().update
                    (new DalTruckingReport(this.ID,this.leavingHour,this.date,this.truckNumber,this.driverID
                            ,this.completed,this.approved));
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public void setSuppliers(LinkedList<String> suppliers) {
        this.suppliers = suppliers;
        try {
            DalTruckingReportController.getInstance().update
                    (new DalTruckingReport(this.ID,this.leavingHour,this.date,this.truckNumber,this.driverID
                    ,this.completed,this.approved));
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public void setDriverID(String driverID)  {
        this.driverID = driverID;
        try {
            DalTruckingReportController.getInstance().update
                    (new DalTruckingReport(this.ID,this.leavingHour,this.date,this.truckNumber,this.driverID
                            ,this.completed,this.approved));
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public void setLeavingHour(LocalTime leavingHour) throws SQLException {
        this.leavingHour = leavingHour;
        try {
            DalTruckingReportController.getInstance().update
                    (new DalTruckingReport(this.ID,this.leavingHour,this.date,this.truckNumber,this.driverID
                            ,this.completed,this.approved));
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public void setTruckNumber(String truckNumber)  {
        this.truckNumber = truckNumber;
        try {
            DalTruckingReportController.getInstance().update
                    (new DalTruckingReport(this.ID,this.leavingHour,this.date,this.truckNumber,this.driverID
                            ,this.completed,this.approved));
        } catch (SQLException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public void addSupplier(String supplier)  {
        if (!suppliers.contains(supplier))
            suppliers.add(supplier);
    }

    public boolean isCompleted() {
        return completed;
    }
}
