package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.DalOrder;
import DataAccessLayer.DalObjects.SupplierObjects.DalSupplierCard;

import java.sql.*;
import java.util.List;

public class OrderDalController extends DalController<DalOrder> {
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
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalOrder.orderIdColumnName + " INTEGER NOT NULL," +
                    DalOrder.supplierIdColumnName + " TEXT NOT NULL," +
                    DalOrder.dateColumnName + " TEXT," +
                    DalOrder.deliveredColumnName +" INTEGER NOT NULL,"+
                    DalOrder.dayColumnName + " INTEGER NOT NULL,"+
                    "PRIMARY KEY (" + DalOrder.orderIdColumnName + ")," +
                    "FOREIGN KEY (" + DalOrder.supplierIdColumnName + ")" + "REFERENCES " + DalSupplierCard.supplierIdColumnName + " (" + SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME + ") ON DELETE CASCADE " +
                    ");";

            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalOrder order) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, order.getOrderId());
            stmt.setString(2, order.getSupplierId());
            stmt.setString(3, order.getDate());
            stmt.setInt(4, order.isDelivered());
            stmt.setInt(5, order.getDay());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalOrder order) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + DalOrder.orderIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, order.getOrderId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalOrder order) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + DalOrder.dateColumnName +
                    "=?, "+ DalOrder.deliveredColumnName+"=?, "+ DalOrder.dayColumnName+"=? WHERE(" + DalOrder.orderIdColumnName + "=?)";
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
    public boolean select(DalOrder order) throws SQLException {
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

    public boolean select(List<DalOrder> orders) throws SQLException {
        DalOrder savedOrder;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                savedOrder = new DalOrder(resultSet.getInt(1));
                savedOrder.setSupplierIdLoad(resultSet.getString(2));
                savedOrder.setDateLoad(resultSet.getString(3));
                savedOrder.setDeliveredLoad(resultSet.getInt(4));
                savedOrder.setDayLoad(resultSet.getInt(5));
                orders.add(savedOrder);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public int getOrderCounter() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT MAX(" + DalOrder.orderIdColumnName + ") FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1) + 1;
            return 0;
        } catch (SQLException ex){
            throw new SQLException(ex.getMessage());
        }
    }
}
