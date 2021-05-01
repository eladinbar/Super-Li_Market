package DAL;

public class DalTruckController extends DalController{

    public DalTruckController(){//TODO - Check when tables created
        super();
        this.tableName="Trucks";
        this.columnNames=new String[4];
        columnNames[0]="model";columnNames[1]="licenseNumber";columnNames[2]="weightNeto";
        columnNames[3]="maxWeight";}

    public boolean insert(DalTruck truck) {
        //TODO - implement
        return false;
    }

    public boolean update(DalTruck truck) {
        //TODO - implement
        return false;
    }

    public boolean delete(DalTruck truck) {
        //TODO - implement
        return false;
    }
    public DalTruck load ()// Select From DB
    {
        //TODO- implement
        return null;
    }
}
