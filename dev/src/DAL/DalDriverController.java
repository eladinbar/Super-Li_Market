package DAL;

public class DalDriverController extends DalController{

    public DalDriverController(){
        //TODO - Check when tables created
        super();
        this.tableName="DeliveryForms";
        this.columnNames=new String[6];
        columnNames[0]="ID";columnNames[1]="origin";columnNames[2]="destination";
        columnNames[3]="completed";columnNames[4]="leavingWeight";columnNames[5]="TRID";
    }

    public boolean insert(DalDriver dalDriver) {
        //TODO - implement
        return false;
    }

    public boolean update(DalDriver dalDriver) {
        //TODO - implement
        return false;
    }

    public boolean delete(DalDriver dalDriver) {
        //TODO - implement
        return false;
    }
    public DalItem load ()// Select From DB
    {
        //TODO- implement
        return null;
    }
}
