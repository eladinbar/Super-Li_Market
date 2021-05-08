package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierContactMembers;

import java.sql.SQLException;

public class SupplierContactMembersDalController extends DalController<SupplierContactMembers> {
    private static SupplierContactMembersDalController instance = null;
    public final static String SUPPLIER_CONTACT_MEMBERS_TABLE_NAME = "Supplier_Contact_Members";

    private SupplierContactMembersDalController() throws SQLException {
        super(SUPPLIER_CONTACT_MEMBERS_TABLE_NAME);
    }

    public static SupplierContactMembersDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new SupplierContactMembersDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        return true;
    }

    @Override
    public boolean insert(SupplierContactMembers dalObject) {
        return false;
    }

    @Override
    public boolean delete(SupplierContactMembers dalObject) {
        return false;
    }

    @Override
    public boolean update(SupplierContactMembers dalObject) {
        return false;
    }

    @Override
    public SupplierContactMembers select(SupplierContactMembers dalObject) {
        return null;
    }
}
