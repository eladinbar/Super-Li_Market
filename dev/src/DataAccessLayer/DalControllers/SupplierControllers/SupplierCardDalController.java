package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.*;

import java.sql.*;

public class SupplierCardDalController extends DalController<SupplierCardDal> {
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
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    SupplierCardDal.supplierIdColumnName + " INTEGER NOT NULL," +
                    SupplierCardDal.companyNumberColumnName + " INTEGET NOT NULL," + SupplierCardDal.isPermanentDaysColumnName +" INTEGER NOT NULL," + SupplierCardDal.selfDeliveryColumnName+ " INTEGER NOT NULL,"+ SupplierCardDal.paymentColumnName+" TEXT NOT NULL,"+
                    "PRIMARY KEY ("+ SupplierCardDal.supplierIdColumnName+"),"+
                    "FOREIGN KEY (" + SupplierCardDal.supplierIdColumnName + ")" + "REFERENCES " + PersonCardDal.idColumnName + " (" + PersonCardDalController.PERSON_CARD_TABLE_NAME +") ON DELETE CASCADE "+
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
    public boolean insert(SupplierCardDal supplierCard) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, supplierCard.getSupplierId());
            stmt.setInt(2, supplierCard.getCompanyNumber());
            stmt.setInt(3, supplierCard.isPermanentDays());
            stmt.setInt(4, supplierCard.isSelfDelivery());
            stmt.setString(5, supplierCard.getPayment());
            System.out.println("Executing " + tableName + " insert.");
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
            String query = "UPDATE " + tableName + " SET " + SupplierCardDal.companyNumberColumnName +
                    "=?, "+ SupplierCardDal.isPermanentDaysColumnName +"=?, "+ SupplierCardDal.selfDeliveryColumnName +"=?, "+SupplierCardDal.paymentColumnName+"=? WHERE(" + SupplierCardDal.supplierIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, supplierCard.getCompanyNumber());
            stmt.setInt(2, supplierCard.isPermanentDays());
            stmt.setInt(3, supplierCard.isSelfDelivery());
            stmt.setString(4, supplierCard.getPayment());
            stmt.setInt(5, supplierCard.getSupplierId());
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
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                isDesired = resultSet.getString(0).equals(supplierCard.getSupplierId());
                if (isDesired) {
                    supplierCard.setCompanyNumber(resultSet.getInt(1));
                    supplierCard.setPermanentDays(resultSet.getInt(2));
                    supplierCard.setSelfDelivery(resultSet.getInt(3));
                    supplierCard.setPayment(resultSet.getString(4));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
