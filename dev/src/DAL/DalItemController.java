package DAL;

public class DalItemController extends DalController{

    public DalItemController(){//TODO - Check when tables created
        super();
        this.tableName="Items";
        this.columnNames=new String[4];
        columnNames[0]="ID";columnNames[1]="name";columnNames[2]="weight";columnNames[3]="originSite";}

    public boolean insert(DalItem dalItem) {
        //TODO - implement
        return false;
    }

    public boolean update(DalItem dalItem) {
        //TODO - implement
        return false;
    }

    public boolean delete(int itemID) {
        //TODO - implement
        return false;
    }
    public DalItem load ()// Select From DB
    {
        //TODO- implement
        return null;
    }
}
