package DAL;

import java.time.LocalDate;
import java.time.LocalTime;

public class DalTruckingReport implements DalObject{
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
}
