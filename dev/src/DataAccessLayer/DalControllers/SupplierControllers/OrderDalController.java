package DataAccessLayer.DalControllers.SupplierControllers;


import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.OrderDal;

import java.sql.*;

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
                    OrderDal.dateColumnName + " TEXT NOT NULL," + OrderDal.deliveredColumnName +" INTEGER NOT NULL,"+
                    "PRIMARY KEY (" + OrderDal.orderIdColumnName + ")" +
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
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, order.getId());
            stmt.setString(2, order.getDate());
            stmt.setInt(3, order.isDelivered());
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

            stmt.setInt(1, order.getId());
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
                    "=?, "+ OrderDal.deliveredColumnName+"=? WHERE(" + OrderDal.orderIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, order.getDate());
            stmt.setInt(2, order.isDelivered());
            stmt.setInt(3, order.getId());
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
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                isDesired = resultSet.getString(0).equals(order.getId());
                if (isDesired) {
                    order.setDate(resultSet.getString(1));
                    order.setDelivered(resultSet.getInt(2));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
