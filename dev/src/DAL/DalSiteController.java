package DAL;

public class DalSiteController extends DalController{

    public  DalSiteController(){//TODO - Check when tables created
        super();
        this.tableName="Sites";
        this.columnNames=new String[6];
        columnNames[0]="siteID";columnNames[1]="name";columnNames[2]="city";
        columnNames[3]="deliverArea";columnNames[4]="contactName";columnNames[5]="phoneNumber";}

    public boolean insert(DalSite dalSite) {
        //TODO - implement
        return false;
    }

    public boolean update(DalSite dalSite) {
        //TODO - implement
        return false;
    }

    public boolean delete(DalSite dalSite) {
        //TODO - implement
        return false;
    }
    public DalSite load ()// Select From DB
    {
        //TODO- implement
        return null;
    }
}
