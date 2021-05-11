package DataAccessLayer.DalControllers.SupplierControllers;


import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.SupplierObjects.OrderDal;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCardDal;
import DataAccessLayer.DalObjects.SupplierObjects.agreementItemsDal;

import java.sql.*;
import java.util.List;

public class OrderDalController extends DalController<OrderDal> {
    private static OrderDalController instance = null;
    public final static String ORDER_TABLE_NAME = "Orders";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private OrderDalController() throws SQLException {
        super(ORDER_TABLE_NAME);
    }

    public static OrderDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new OrderDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    OrderDal.orderIdColumnName + " INTEGER NOT NULL," +
                    OrderDal.supplierIdColumnName + " TEXT NOT NULL," +
                    OrderDal.dateColumnName + " TEXT NOT NULL," +
                    OrderDal.deliveredColumnName +" INTEGER NOT NULL,"+
                    OrderDal.dayColumnName + " INTEGER NOT NULL,"+
                    "PRIMARY KEY (" + OrderDal.orderIdColumnName + ")," +
                    "FOREIGN KEY (" + OrderDal.supplierIdColumnName + ")" + "REFERENCES " + SupplierCardDal.supplierIdColumnName + " (" + SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME + ") ON DELETE CASCADE " +
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
    public boolean insert(OrderDal order) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, order.getOrderId());
            stmt.setString(2, order.getSupplierId());
            stmt.setString(3, order.getDate());
            stmt.setInt(4, order.isDelivered());
            stmt.setInt(5, order.getDay());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(OrderDal order) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + OrderDal.orderIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, order.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(OrderDal order) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + OrderDal.dateColumnName +
                    "=?, "+ OrderDal.deliveredColumnName+"=?, "+ OrderDal.dayColumnName+"=? WHERE(" + OrderDal.orderIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, order.getDate());
            stmt.setInt(2, order.isDelivered());
            stmt.setInt(3, order.getDay());
            stmt.setInt(4, order.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(OrderDal order) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getInt(1)==(order.getOrderId());
                if (isDesired) {
                    order.setSupplierIdLoad(resultSet.getString(2));
                    order.setDateLoad(resultSet.getString(3));
                    order.setDeliveredLoad(resultSet.getInt(4));
                    order.setDayLoad(resultSet.getInt(5));
                    break;
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
