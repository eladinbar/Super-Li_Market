package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController$;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCardDal;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCardDal;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierContactMembersDal;

import java.sql.*;
import java.util.List;

public class SupplierContactMembersDalController extends DalController$<SupplierContactMembersDal> {
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
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    SupplierContactMembersDal.supplierIdColumnName + " INTEGER NOT NULL," +
                    SupplierContactMembersDal.personIdColumnName + " INTEGET NOT NULL," +
                    "PRIMARY KEY ("+ SupplierContactMembersDal.supplierIdColumnName+
                    ", "+ SupplierContactMembersDal.personIdColumnName+"),"+
                    "FOREIGN KEY (" + SupplierContactMembersDal.supplierIdColumnName + ")" + "REFERENCES " + SupplierCardDal.supplierIdColumnName + " (" + SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME +") ON DELETE CASCADE "+
                    "FOREIGN KEY (" + SupplierContactMembersDal.personIdColumnName + ")" + "REFERENCES " + PersonCardDal.idColumnName + " (" + PersonCardDalController.PERSON_CARD_TABLE_NAME +") ON DELETE CASCADE "+
                    ");";

            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(SupplierContactMembersDal person) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, person.getSupplierId());
            stmt.setInt(2, person.getPersonId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(SupplierContactMembersDal supplierContactMember) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + SupplierContactMembersDal.supplierIdColumnName + "=? AND "+SupplierContactMembersDal.personIdColumnName+"=?)";
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
    public boolean update(SupplierContactMembersDal dalObject) {
        return false;
    }

    @Override
    public boolean select(SupplierContactMembersDal supplierContactMember) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getInt(1) == (supplierContactMember.getSupplierId())
                && resultSet.getInt(2) == supplierContactMember.getPersonId();
                if (isDesired) {
                    //todo
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    public boolean select(SupplierContactMembersDal contactMember, List<SupplierContactMembersDal> contactMemberList) throws SQLException {
        boolean hasItem = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(1).equals(""+contactMember.getSupplierId());
                if (!hasItem & isDesired)
                    hasItem = true;
                if (isDesired) {
                    int personId = resultSet.getInt(2);
                    SupplierContactMembersDal savedSupplier = new SupplierContactMembersDal(contactMember.getSupplierId(), personId);
                    contactMemberList.add(savedSupplier);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return hasItem;
    }
}
