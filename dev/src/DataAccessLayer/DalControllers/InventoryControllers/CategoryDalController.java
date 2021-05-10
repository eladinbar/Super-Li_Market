package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;

import java.sql.*;
import java.util.List;

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
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(command);
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
            String command = "DELETE FROM " + tableName + " WHERE (" + Category.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, category.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Category category) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + Category.parentNameColumnName +
                    "=? WHERE(" + Category.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, category.getParentName());
            stmt.setString(2, category.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Category category, String oldName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + Category.categoryNameColumnName +
                    "=? WHERE(" + Category.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, category.getName());
            stmt.setString(2, oldName);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(Category category) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(category.getName());
                if (isDesired) {
                    category.setParentName(resultSet.getString(2));
                    break; //Desired category found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    @Override
    public boolean select(Category category, List<Category> categories) throws SQLException {
        boolean isDesired = false;
        Category savedCategory = new Category();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = category.getName() == null ? resultSet.getString(2).equals(category.getParentName()) :
                resultSet.getString(1).equals(category.getName());
                if (isDesired) {
                    savedCategory = new Category();
                    if (category.getName() == null)
                        savedCategory.setName(resultSet.getString(1));
                    else
                        savedCategory.setParentName(resultSet.getString(2));
                    categories.add(savedCategory);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    @Override
    public boolean select(List<Category> categories) throws SQLException {
        Category savedCategory = new Category();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                savedCategory = new Category();
                savedCategory.setName(resultSet.getString(1));
                savedCategory.setParentName(resultSet.getString(2));
                categories.add(savedCategory);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }
}
