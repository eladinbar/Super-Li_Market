package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.DalCategory;

import java.sql.*;
import java.util.List;

public class CategoryDalController extends DalController<DalCategory> {
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
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalCategory.categoryNameColumnName + " TEXT NOT NULL," +
                    DalCategory.parentNameColumnName + " TEXT," +
                    "PRIMARY KEY (" + DalCategory.categoryNameColumnName + ")" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalCategory category) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?)";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, category.getName());
            stmt.setString(2, category.getParentName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalCategory category) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "DELETE FROM " + tableName + " WHERE (" + DalCategory.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, category.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalCategory category) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalCategory.parentNameColumnName +
                    "=? WHERE(" + DalCategory.categoryNameColumnName + "=?)";
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
    public boolean update(DalCategory category, String oldName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalCategory.categoryNameColumnName +
                    "=? WHERE(" + DalCategory.categoryNameColumnName + "=?)";
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
    public boolean select(DalCategory category) throws SQLException {
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
    public boolean select(DalCategory category, List<DalCategory> categories) throws SQLException {
        boolean hasItem = false;
        DalCategory savedCategory;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                boolean isDesired = category.getName() == null ? resultSet.getString(2).equals(category.getParentName()) :
                resultSet.getString(1).equals(category.getName());
                if (!hasItem & isDesired)
                    hasItem = true;
                if (isDesired) {
                    savedCategory = new DalCategory();
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
        return hasItem;
    }

    @Override
    public boolean select(List<DalCategory> categories) throws SQLException {
        DalCategory savedCategory;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                savedCategory = new DalCategory();
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
