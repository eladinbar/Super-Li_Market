package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCard;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierContactMembers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    SupplierContactMembers.supplierIdColumnName + " INTEGER NOT NULL," +
                    SupplierContactMembers.personIdColumnName + " INTEGET NOT NULL," +
                    "FOREIGN KEY (" + SupplierContactMembers.supplierIdColumnName + ")" + "REFERENCES " + SupplierCard.supplierIdColumnName + " (" + SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME +") ON DELETE NO ACTION "+
                    "FOREIGN KEY (" + SupplierContactMembers.personIdColumnName + ")" + "REFERENCES " + PersonCard.idColumnName + " (" + PersonCardDalController.PERSON_CARD_TABLE_NAME +") ON DELETE NO ACTION "+
                    ");";

            PreparedStatement stmt = conn.prepareStatement(command);
            System.out.println("Creating '" + tableName + "' table.");
            stmt.executeUpdate();
            System.out.println("Table '" + tableName + "' created.");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
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
