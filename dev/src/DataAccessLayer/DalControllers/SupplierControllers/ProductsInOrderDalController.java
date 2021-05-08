package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.SupplierObjects.AgreementItems;
import DataAccessLayer.DalObjects.SupplierObjects.Order;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCard;
import DataAccessLayer.DalObjects.SupplierObjects.ProductsInOrder;

import java.sql.*;

public class ProductsInOrderDalController extends DalController<ProductsInOrder> {
    private static ProductsInOrderDalController instance = null;
    public final static String PRODUCTS_IN_ORDER_TABLE_NAME = "Products_In_Order";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private ProductsInOrderDalController() throws SQLException {
        super(PRODUCTS_IN_ORDER_TABLE_NAME);
    }

    public static ProductsInOrderDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new ProductsInOrderDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    ProductsInOrder.productIdColumnName + " INTEGER NOT NULL," +
                    ProductsInOrder.amountColumnName + " INTEGET NOT NULL," +
                    "PRIMARY KEY ("+ ProductsInOrder.productIdColumnName + "),"+
                    "FOREIGN KEY (" + ProductsInOrder.productIdColumnName + ")" + "REFERENCES " + Item.itemIdColumnName + " (" + ItemDalController.ITEM_TABLE_NAME +") ON DELETE NO ACTION "+
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
    public boolean insert(ProductsInOrder productsInOrder) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, productsInOrder.getProductId());
            stmt.setInt(2, productsInOrder.getAmount());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(ProductsInOrder productsInOrder) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + ProductsInOrder.productIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, productsInOrder.getProductId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(ProductsInOrder productInOrder) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + ProductsInOrder.amountColumnName +
                    "=? WHERE(" + ProductsInOrder.productIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, productInOrder.getAmount());
            stmt.setInt(2, productInOrder.getProductId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public ProductsInOrder select(ProductsInOrder productInOrder) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(0).equals(productInOrder.getProductId());
                if (isDesired) {
                    productInOrder.setAmount(resultSet.getInt(1));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return productInOrder;
    }
}
