package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCard;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierContactMembers;

import java.sql.*;

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
                    "PRIMARY KEY ("+ SupplierContactMembers.supplierIdColumnName+
                    ", "+ SupplierContactMembers.personIdColumnName+"),"+
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
    public boolean insert(SupplierContactMembers person) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, person.getSupplierId());
            stmt.setInt(2, person.getPersonId());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(SupplierContactMembers supplierContactMember) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + SupplierContactMembers.supplierIdColumnName + "=? AND "+SupplierContactMembers.personIdColumnName+"=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, supplierContactMember.getSupplierId());
            stmt.setInt(2, supplierContactMember.getPersonId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(SupplierContactMembers dalObject) {
        return false;
    }

    @Override
    public boolean select(SupplierContactMembers supplierContactMember) {
        return true;
    }
}
