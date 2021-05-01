package DAL;

import DAL.DalController;
import DAL.DalTruckingReport;

public class DalTruckingReportController extends DalController {

    public DalTruckingReportController(){//TODO - Check when tables created
        super();
        this.tableName="TruckingReports";
        this.columnNames=new String[8];
        columnNames[0]="ID";columnNames[1]="leavingHour";columnNames[2]="date";
        columnNames[3]="truckNumber";columnNames[4]="driverID";columnNames[5]="origin";
        columnNames[6]="completed";columnNames[7]="replaceTRID";
    }

    public boolean insert(DalTruckingReport truckingReport) {
        //TODO - implement
        return false;
    }

    public boolean update(DalTruckingReport truckingReport) {
        //TODO - implement
        return false;
    }

    public boolean delete(DalTruckingReport truckingReport) {
        //TODO - implement
        return false;
    }
    public DalTruckingReport load ()// Select From DB
    {
        //TODO- implement
        return null;
    }
}
