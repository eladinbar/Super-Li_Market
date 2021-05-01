package DAL;

public class DalDemandController extends DalController{

    public DalDemandController()
    {//TODO - Check when tables created
        super();
        this.tableName="Demands";
        this.columnNames=new String[3];
        columnNames[0]="itemID";columnNames[1]="amount";columnNames[2]="siteID";
    }

    public boolean insert(DalDemand dalDemand) {
        //TODO - implement
        return false;
    }

    public boolean update(DalDemand dalDemand) {
        //TODO - implement
        return false;
    }

    public boolean delete(DalDemand dalDemand) {
        //TODO - implement
        return false;
    }
    public DalDemand load ()// Select From DB
    {
        //TODO- implement
        return null;
    }
}
