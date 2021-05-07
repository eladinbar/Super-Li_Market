package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierContactMembers;

import java.sql.SQLException;

public class SupplierContactMembersDalController extends DalController<SupplierContactMembers> {
    private static SupplierContactMembersDalController instance = null;
    final static String SUPPLIER_CONTACT_MEMBERS_TABLE_NAME = "Supplier_Contact_Members";

    private SupplierContactMembersDalController() throws SQLException {
        super(SUPPLIER_CONTACT_MEMBERS_TABLE_NAME);
    }

    public static SupplierContactMembersDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new SupplierContactMembersDalController();
        return instance;
    }

    @Override
    public void CreateTable() throws SQLException {

    }

    @Override
    public boolean Insert(SupplierContactMembers dalObject) {
        return false;
    }

    @Override
    public boolean Delete(SupplierContactMembers dalObject) {
        return false;
    }

    @Override
    public SupplierContactMembers ConvertReaderToObject() {
        return null;
    }
}
