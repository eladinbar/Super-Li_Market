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

    public SupplierContactMembersDal(int supplierId) throws SQLException {
        super(SupplierContactMembersDalController.getInstance());
        this.supplierId = supplierId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setSupplierId(int supplierId) throws SQLException {
        this.supplierId = supplierId;
        update();
    }

    public void setSupplierIdLoad(int supplierId) throws SQLException {
        this.supplierId = supplierId;
    }

    public void setPersonId(int personId) throws SQLException {
        this.personId = personId;
        update();
    }

    public void setPersonIdLoad(int personId) throws SQLException {
        this.personId = personId;
    }
}
