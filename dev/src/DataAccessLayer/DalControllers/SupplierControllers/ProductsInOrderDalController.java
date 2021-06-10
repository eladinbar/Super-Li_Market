package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController$;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.DalItem;
import DataAccessLayer.DalObjects.SupplierObjects.DalOrder;
import DataAccessLayer.DalObjects.SupplierObjects.DalProductsInOrder;

import java.sql.*;
import java.util.List;

public class ProductsInOrderDalController extends DalController$<DalProductsInOrder> {
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
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalProductsInOrder.productIdColumnName + " INTEGER NOT NULL," +
                    DalProductsInOrder.orderIdColumnName + " INTEGER NOT NULL," +
                    DalProductsInOrder.amountColumnName + " INTEGET NOT NULL," +
                    "PRIMARY KEY ("+ DalProductsInOrder.productIdColumnName +", "+ DalProductsInOrder.orderIdColumnName+ "),"+
                    "FOREIGN KEY (" + DalProductsInOrder.productIdColumnName + ")" + "REFERENCES " + DalItem.itemIdColumnName + " (" + ItemDalController.ITEM_TABLE_NAME +") ON DELETE CASCADE "+
                    "FOREIGN KEY (" + DalProductsInOrder.orderIdColumnName + ")" + "REFERENCES " + DalOrder.orderIdColumnName + " (" + OrderDalController.ORDER_TABLE_NAME +") ON DELETE CASCADE "+
                    ");";

            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalProductsInOrder productsInOrder) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, productsInOrder.getProductId());
            stmt.setInt(2, productsInOrder.getOrderId());
            stmt.setInt(3, productsInOrder.getAmount());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalProductsInOrder productsInOrder) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + DalProductsInOrder.productIdColumnName +"=? AND "+ DalProductsInOrder.orderIdColumnName+ "=?)";
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
    public boolean update(DalProductsInOrder productInOrder) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + DalProductsInOrder.amountColumnName +
                    "=? WHERE(" + DalProductsInOrder.productIdColumnName +"=? AND "+ DalProductsInOrder.orderIdColumnName+ "=?)";
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
    public boolean select(DalProductsInOrder productInOrder) throws SQLException {
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

    public boolean select(DalProductsInOrder product, List<DalProductsInOrder> productsInOrderDal) throws SQLException {
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
                    DalProductsInOrder savedProduct = new DalProductsInOrder(productId, product.getOrderId(), amount);
                    productsInOrderDal.add(savedProduct);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return hasItem;
    }
}
