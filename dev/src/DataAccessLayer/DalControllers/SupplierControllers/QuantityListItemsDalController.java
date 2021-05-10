package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.SupplierObjects.ProductsInOrderDal;
import DataAccessLayer.DalObjects.SupplierObjects.QuantityListItemsDal;

import java.sql.*;

public class QuantityListItemsDalController extends DalController<QuantityListItemsDal> {
    private static QuantityListItemsDalController instance = null;
    public final static String QUANTITY_LIST_ITEMS_TABLE_NAME = "Quantity_List_Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private QuantityListItemsDalController() throws SQLException {
        super(QUANTITY_LIST_ITEMS_TABLE_NAME);
    }

    public static QuantityListItemsDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new QuantityListItemsDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    QuantityListItemsDal.productIdColumnName + " INTEGER NOT NULL," +
                    QuantityListItemsDal.amountColumnName+ " INTEGET NOT NULL," + QuantityListItemsDal.discountColumnName+ " REAL NOT NULL,"+
                    "PRIMARY KEY (" + ProductsInOrderDal.productIdColumnName + "),"+
                    "FOREIGN KEY (" + ProductsInOrderDal.productIdColumnName + ")" + "REFERENCES " + Item.itemIdColumnName + " (" + ItemDalController.ITEM_TABLE_NAME +") ON DELETE NO ACTION "+
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
    public boolean insert(QuantityListItemsDal quantityListItem) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, quantityListItem.getProductId());
            stmt.setInt(2, quantityListItem.getAmount());
            stmt.setDouble(3, quantityListItem.getDiscount());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(QuantityListItemsDal quantityListItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + QuantityListItemsDal.productIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, quantityListItem.getProductId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(QuantityListItemsDal quantityListItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + QuantityListItemsDal.amountColumnName +
                    "=?, "+ QuantityListItemsDal.discountColumnName+"=? WHERE(" + QuantityListItemsDal.productIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, quantityListItem.getAmount());
            stmt.setDouble(2, quantityListItem.getDiscount());
            stmt.setInt(3, quantityListItem.getProductId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(QuantityListItemsDal quantityListItem) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                isDesired = resultSet.getInt(0) == quantityListItem.getProductId();
                if (isDesired) {
                    quantityListItem.setAmount(resultSet.getInt(1));
                    quantityListItem.setDiscount(resultSet.getInt(2));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
