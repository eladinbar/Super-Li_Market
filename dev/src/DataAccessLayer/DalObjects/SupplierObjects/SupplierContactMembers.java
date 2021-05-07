package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.SupplierContactMembersDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class SupplierContactMembers extends DalObject<SupplierContactMembers> {
    private int supplierId;
    private int personId;

    public SupplierContactMembers(int supplierId, int personId) throws SQLException {
        super(SupplierContactMembersDalController.getInstance());
        this.supplierId = supplierId;
        this.personId = personId;
    }
}
