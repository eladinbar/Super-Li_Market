package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDalController extends DalController<Category> {
    private static CategoryDalController instance = null;
    public final static String CATEGORY_TABLE_NAME = "Categories";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private CategoryDalController() throws SQLException {
        super(CATEGORY_TABLE_NAME);
    }

    public static CategoryDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new CategoryDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    Category.categoryNameColumnName + " TEXT NOT NULL," +
                    Category.parentNameColumnName + " TEXT," +
                    "PRIMARY KEY (" + Category.categoryNameColumnName + ")" +
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
    public boolean insert(Category category) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getParentName());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(Category category) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE ?=?";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, Category.categoryNameColumnName);
            stmt.setString(2, category.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Category category) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + Category.parentNameColumnName +
                    "=? WHERE(" + Category.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, category.getParentName());
            stmt.setString(2, category.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public Category select(Category category) {
        return null;
    }
}
