package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.SupplierContactMembersDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class SupplierContactMembersDal extends DalObject<SupplierContactMembersDal> {
    public static final String supplierIdColumnName = "Supplier_Id";
    public static final String personIdColumnName = "Person_Id";
    private int supplierId; //foreign key (supplier card)
    private int personId; //foreign key (person card)

    public SupplierContactMembersDal(int supplierId, int personId) throws SQLException {
        super(SupplierContactMembersDalController.getInstance());
        this.supplierId = supplierId;
        this.personId = personId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }
}
