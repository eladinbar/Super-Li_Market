package DataAccessLayer.DalObjects.TruckingObjects;

import java.sql.SQLException;

public class DalSupplierListOnTR {
    int TRID;
    int supplier;
    private DalSupplierListOnTR(int ID,int supplier)
    {
        this.TRID=ID;
        this.supplier=supplier;
    }

    //TODO - make sure this controller is called
    //TODO - add SQL methods - delete load ,create Table, upate
}
