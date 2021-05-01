package DAL;

public class DalItemsOnDFController extends DalController{

    public DalItemsOnDFController(){//TODO - Check when tables created
        super();
        this.tableName="ItemsOnDF";
        this.columnNames=new String[2];
        columnNames[0]="itemID";columnNames[1]="amount";}

    public boolean insert(DalItemsOnDF items) {
        //TODO - implement
        return false;
    }

    public boolean update(DalItemsOnDF items) {
        //TODO - implement
        return false;
    }

    public boolean delete(DalItemsOnDF items) {
        //TODO - implement
        return false;
    }
    public DalItemsOnDF load ()// Select From DB
    {
        //TODO- implement
        return null;
    }
}
