package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController$;
import DataAccessLayer.DalObjects.SupplierObjects.*;

import java.sql.*;
import java.util.List;

public class SupplierCardDalController extends DalController$<SupplierCardDal> {
    private static SupplierCardDalController instance = null;
    public final static String SUPPLIER_CARD_TABLE_NAME = "Supplier_Cards";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private SupplierCardDalController() throws SQLException {
        super(SUPPLIER_CARD_TABLE_NAME);
    }

    public static SupplierCardDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new SupplierCardDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    SupplierCardDal.supplierIdColumnName + " INTEGER NOT NULL," +
                    SupplierCardDal.companyNumberColumnName + " INTEGET NOT NULL," +
                    SupplierCardDal.isPermanentDaysColumnName +" INTEGER NOT NULL," +
                    SupplierCardDal.selfDeliveryColumnName+ " INTEGER NOT NULL,"+
                    SupplierCardDal.paymentColumnName+" TEXT NOT NULL,"+
                    SupplierCardDal.addressColumnName + " TEXT NOT NULL,"+
                    "PRIMARY KEY ("+ SupplierCardDal.supplierIdColumnName+"),"+
                    "FOREIGN KEY (" + SupplierCardDal.supplierIdColumnName + ")" + "REFERENCES " + PersonCardDal.idColumnName + " (" + PersonCardDalController.PERSON_CARD_TABLE_NAME +") ON DELETE CASCADE "+
                    ");";

            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(SupplierCardDal supplierCard) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, supplierCard.getSupplierId());
            stmt.setInt(2, supplierCard.getCompanyNumber());
            stmt.setInt(3, supplierCard.isPermanentDays());
            stmt.setInt(4, supplierCard.isSelfDelivery());
            stmt.setString(5, supplierCard.getPayment());
            stmt.setString(6, supplierCard.getAddress());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(SupplierCardDal supplierCard) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + SupplierCardDal.supplierIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, supplierCard.getSupplierId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(SupplierCardDal supplierCard) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + SupplierCardDal.companyNumberColumnName + "=?, "+
                    SupplierCardDal.isPermanentDaysColumnName +"=?, "+
                    SupplierCardDal.selfDeliveryColumnName +"=?, "+
                    SupplierCardDal.paymentColumnName+"=?, "+
                    SupplierCardDal.addressColumnName+
                    "=? WHERE(" + SupplierCardDal.supplierIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, supplierCard.getCompanyNumber());
            stmt.setInt(2, supplierCard.isPermanentDays());
            stmt.setInt(3, supplierCard.isSelfDelivery());
            stmt.setString(4, supplierCard.getPayment());
            stmt.setString(5, supplierCard.getAddress());
            stmt.setInt(6, supplierCard.getSupplierId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(SupplierCardDal supplierCard) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(""+supplierCard.getSupplierId());
                if (isDesired) {
                    supplierCard.setCompanyNumberLoad(resultSet.getInt(2));
                    supplierCard.setPermanentDaysLoad(resultSet.getInt(3));
                    supplierCard.setSelfDeliveryLoad(resultSet.getInt(4));
                    supplierCard.setPaymentLoad(resultSet.getString(5));
                    supplierCard.setAddressLoad(resultSet.getString(6));
                    break;
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    public boolean select(List<SupplierCardDal> suppliers) throws SQLException {
        SupplierCardDal savedSupplier;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                savedSupplier = new SupplierCardDal(resultSet.getInt(1));
                savedSupplier.setCompanyNumberLoad(resultSet.getInt(2));
                savedSupplier.setPermanentDaysLoad(resultSet.getInt(3));
                savedSupplier.setSelfDeliveryLoad(resultSet.getInt(4));
                savedSupplier.setPaymentLoad(resultSet.getString(5));
                savedSupplier.setAddressLoad(resultSet.getString(6));
                suppliers.add(savedSupplier);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }
}