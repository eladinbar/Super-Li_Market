package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.SupplierObjects.OrderDal;
import DataAccessLayer.DalObjects.SupplierObjects.ProductsInOrderDal;

import java.sql.*;
import java.util.List;

public class ProductsInOrderDalController extends DalController<ProductsInOrderDal> {
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
                    ProductsInOrderDal.productIdColumnName + " INTEGER NOT NULL," +
                    ProductsInOrderDal.orderIdColumnName + " INTEGER NOT NULL," +
                    ProductsInOrderDal.amountColumnName + " INTEGET NOT NULL," +
                    "PRIMARY KEY ("+ ProductsInOrderDal.productIdColumnName +", "+ProductsInOrderDal.orderIdColumnName+ "),"+
                    "FOREIGN KEY (" + ProductsInOrderDal.productIdColumnName + ")" + "REFERENCES " + Item.itemIdColumnName + " (" + ItemDalController.ITEM_TABLE_NAME +") ON DELETE CASCADE "+
                    "FOREIGN KEY (" + ProductsInOrderDal.orderIdColumnName + ")" + "REFERENCES " + OrderDal.orderIdColumnName + " (" + OrderDalController.ORDER_TABLE_NAME +") ON DELETE CASCADE "+
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
    public boolean insert(ProductsInOrderDal productsInOrder) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, productsInOrder.getProductId());
            stmt.setInt(2, productsInOrder.getOrderId());
            stmt.setInt(3, productsInOrder.getAmount());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(ProductsInOrderDal productsInOrder) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + ProductsInOrderDal.productIdColumnName +"=? AND "+ProductsInOrderDal.orderIdColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, productsInOrder.getProductId());
            stmt.setInt(2, productsInOrder.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(ProductsInOrderDal productInOrder) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + ProductsInOrderDal.amountColumnName +
                    "=? WHERE(" + ProductsInOrderDal.productIdColumnName +"=? AND "+ProductsInOrderDal.orderIdColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, productInOrder.getAmount());
            stmt.setInt(2, productInOrder.getProductId());
            stmt.setInt(3, productInOrder.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(ProductsInOrderDal productInOrder) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getInt(1) == productInOrder.getProductId()
                && resultSet.getInt(2) == productInOrder.getOrderId();
                if (isDesired) {
                    productInOrder.setAmountLoad(resultSet.getInt(3));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    public boolean select(ProductsInOrderDal product, List<ProductsInOrderDal> productsInOrderDal) throws SQLException {
        boolean hasItem = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(2).equals(""+product.getOrderId());
                if (!hasItem & isDesired)
                    hasItem = true;
                if (isDesired) {
                    int productId = resultSet.getInt(1);
                    int amount = resultSet.getInt(3);
                    ProductsInOrderDal savedProduct = new ProductsInOrderDal(productId, product.getOrderId(), amount);
                    productsInOrderDal.add(savedProduct);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return hasItem;
    }
}
