package Trucking.Business_Layer_Trucking.Delivery;

import DAL.DalObjects_Trucking.DalTruckingReport;
import DAL.DalTruckingReportController;

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
    private int origin;
    private LinkedList<Integer> destinations;
    private int TRReplace;
    private boolean completed;


    public TruckingReport(int ID, LocalDate date, LocalTime leavingHour, String  truckNumber, String driverID,
                          int origin, LinkedList<Integer> destinations)
    {
        this.ID=ID;
        this.date=date;
        this.leavingHour=leavingHour;
        this.truckNumber=truckNumber;
        this.driverID=driverID;
        this.origin=origin;
        this.destinations=destinations;
        this.TRReplace=-1;
    }
    public TruckingReport(int ID){
        this.ID=ID;
        this.destinations = new LinkedList<>();
        this.TRReplace=-1;

    }

    public TruckingReport(DalTruckingReport dtr) throws SQLException {
        this.ID=dtr.getID();
        this.date=dtr.getDate();
        this.leavingHour=dtr.getLeavingHour();
        this.destinations=new LinkedList<>();
        this.truckNumber=dtr.getTruckNumber();
        this.completed=dtr.isCompleted();
        this.driverID=dtr.getDriverID();
        this.origin=dtr.getOrigin();
        this.TRReplace=dtr.getReplaceTRID();
        DalTruckingReportController.getInstance().insert(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

    }

    public TruckingReport(int lastReportID, TruckingReport oldTr) {
        this.ID = lastReportID;
        this.date=oldTr.date;
        this.leavingHour=oldTr.leavingHour;
        this.truckNumber=oldTr.truckNumber;
        this.driverID=oldTr.driverID;
        this.origin=oldTr.origin;
        this.destinations=oldTr.destinations;
        this.TRReplace=oldTr.getID();
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

    public int getTRReplace() {
        return TRReplace;
    }

    public void setCompleted() throws SQLException {
        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

        this.completed = true;
    }

    public void setID(int ID) throws SQLException {
        this.ID = ID;
        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

    }

    public void setOrigin(int origin) throws SQLException {
        this.origin = origin;
        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

    }

    public void setDate(LocalDate date) throws SQLException {
        this.date = date;
        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

    }

    public void setDestinations(LinkedList<Integer> destinations) throws SQLException {
        this.destinations = destinations;
        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

    }

    public void setDriverID(String driverID) throws SQLException {
        this.driverID = driverID;
        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

    }

    public void setLeavingHour(LocalTime leavingHour) throws SQLException {
        this.leavingHour = leavingHour;
        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

    }

    public void setTRReplace(int TRReplace) throws SQLException {
        this.TRReplace = TRReplace;
        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

    }

    public void setTruckNumber(String truckNumber) throws SQLException {
        this.truckNumber = truckNumber;
        DalTruckingReportController.getInstance().update(new DalTruckingReport(ID,leavingHour,date,truckNumber,driverID,origin,completed,TRReplace));

    }

    public void addDestination(int destination)  {
        destinations.add(destination);

    }

    public boolean isCompleted() {
        return completed;
    }
}
