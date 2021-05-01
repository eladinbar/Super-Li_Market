package DAL;

public class DalDeliveryFormController extends DalController {

    public DalDeliveryFormController(){
        //TODO - Check when tables created
        super();
        this.tableName="DeliveryForms";
        this.columnNames=new String[6];
        columnNames[0]="ID";columnNames[1]="origin";columnNames[2]="destination";
        columnNames[3]="completed";columnNames[4]="leavingWeight";columnNames[5]="TRID";
    }

    public boolean insert(DalDeliveryForm deliveryForm) {
        //TODO - implement
        return false;
    }

    public boolean update(DalDeliveryForm deliveryForm) {
        //TODO - implement
        return false;
    }

    public boolean delete(DalDeliveryForm deliveryForm) {
        //TODO - implement
        return false;
    }
    public DalDeliveryForm load ()// Select From DB
    {
        //TODO- implement
        return null;
    }
}
