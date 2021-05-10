package DataAccessLayer.DalControllers.SupplierControllers;
import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCardDal;
import DataAccessLayer.DalObjects.SupplierObjects.agreementItemsDal;

import java.sql.*;

public class AgreementItemsDalController extends DalController<agreementItemsDal> {
    private static AgreementItemsDalController instance = null;
    public final static String AGREEMENT_ITEMS_TABLE_NAME = "Agreement_Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private AgreementItemsDalController() throws SQLException {
        super(AGREEMENT_ITEMS_TABLE_NAME);
    }

    public static AgreementItemsDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new AgreementItemsDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    agreementItemsDal.productIdColumnName + " INTEGER NOT NULL," +
                    agreementItemsDal.supplierIdColumnName + " INTEGER NOT NULL,"+
                    agreementItemsDal.productCompIdColumnName + " INTEGER NOT NULL," + agreementItemsDal.priceColumnName + " REAL NOT NULL," +
                    "PRIMARY KEY (" + agreementItemsDal.productIdColumnName +", "+ agreementItemsDal.supplierIdColumnName + ")," +
                    "FOREIGN KEY (" + agreementItemsDal.productIdColumnName + ")" + "REFERENCES " + Item.itemIdColumnName + " (" + ItemDalController.ITEM_TABLE_NAME + ") ON DELETE CASCADE " +
                    "FOREIGN KEY (" + agreementItemsDal.supplierIdColumnName + ")" + "REFERENCES " + SupplierCardDal.supplierIdColumnName + " (" + SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME + ") ON DELETE CASCADE " +
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
    public boolean insert(agreementItemsDal agreementItem) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, agreementItem.getProductId());
            stmt.setInt(2, agreementItem.getSupplierId());
            stmt.setInt(3, agreementItem.getProductCompId());
            stmt.setDouble(4, agreementItem.getPrice());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(agreementItemsDal agreementItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + agreementItemsDal.productIdColumnName +"=? AND "+
                    agreementItemsDal.supplierIdColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, agreementItem.getProductId());
            stmt.setInt(2, agreementItem.getSupplierId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(agreementItemsDal agreementItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + agreementItemsDal.priceColumnName +
                    "=?, " + agreementItemsDal.productCompIdColumnName + "=? WHERE(" + agreementItemsDal.productIdColumnName +"=? AND "+
                    agreementItemsDal.supplierIdColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setDouble(1, agreementItem.getPrice());
            stmt.setInt(2, agreementItem.getProductCompId());
            stmt.setInt(3, agreementItem.getProductId());
            stmt.setInt(4, agreementItem.getSupplierId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(agreementItemsDal agreementItem) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()) {
                isDesired = resultSet.getInt(0) == agreementItem.getProductId() && resultSet.getInt(1)== agreementItem.getSupplierId();
                if (isDesired) {
                    agreementItem.setProductCompId(resultSet.getInt(2));
                    agreementItem.setPrice(resultSet.getInt(3));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}