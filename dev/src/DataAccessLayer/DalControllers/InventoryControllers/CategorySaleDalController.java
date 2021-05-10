package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;
import DataAccessLayer.DalObjects.InventoryObjects.CategorySale;

import java.sql.*;

import static DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController.CATEGORY_TABLE_NAME;

public class CategorySaleDalController extends DalController<CategorySale> {
    private static CategorySaleDalController instance = null;
    public final static String CATEGORY_SALE_TABLE_NAME = "Category_Sales";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private CategorySaleDalController() throws SQLException {
        super(CATEGORY_SALE_TABLE_NAME);
    }

    public static CategorySaleDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new CategorySaleDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    CategorySale.categorySaleNameColumnName + " TEXT NOT NULL," +
                    CategorySale.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    CategorySale.startSaleDateColumnName + " TEXT NOT NULL," +
                    CategorySale.endSaleDateColumnName + " TEXT NOT NULL," +
                    CategorySale.categoryNameColumnName + " TEXT NOT NULL," +
                    "PRIMARY KEY (" + CategorySale.categorySaleNameColumnName + ")," +
                    "FOREIGN KEY (" + CategorySale.categoryNameColumnName + ")" +
                    "REFERENCES " + Category.categoryNameColumnName + " (" + CATEGORY_TABLE_NAME + ") ON DELETE CASCADE" +
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
    public boolean insert(CategorySale categorySale) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, categorySale.getName());
            stmt.setDouble(2, categorySale.getDiscount());
            stmt.setString(3, categorySale.getStartSaleDate());
            stmt.setString(4, categorySale.getEndSaleDate());
            stmt.setString(5, categorySale.getCategoryName());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(CategorySale categorySale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "DELETE FROM " + tableName + " WHERE (" + CategorySale.categorySaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categorySale.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(CategorySale categorySale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + CategorySale.discountColumnName + "=?, " +
                    CategorySale.startSaleDateColumnName + "=?, " + CategorySale.endSaleDateColumnName + "=?, " +
                    CategorySale.categoryNameColumnName + "=? WHERE(" + CategorySale.categorySaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setDouble(1, categorySale.getDiscount());
            stmt.setString(2, categorySale.getStartSaleDate());
            stmt.setString(3, categorySale.getEndSaleDate());
            stmt.setString(4, categorySale.getCategoryName());
            stmt.setString(5, categorySale.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(CategorySale categorySale, String oldName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + CategorySale.categorySaleNameColumnName + "=?, " + CategorySale.discountColumnName + "=?, " +
                    CategorySale.startSaleDateColumnName + "=?, " + CategorySale.endSaleDateColumnName + "=?, " +
                    CategorySale.categoryNameColumnName + "=? WHERE(" + CategorySale.categorySaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categorySale.getName());
            stmt.setDouble(2, categorySale.getDiscount());
            stmt.setString(3, categorySale.getStartSaleDate());
            stmt.setString(4, categorySale.getEndSaleDate());
            stmt.setString(5, categorySale.getCategoryName());
            stmt.setString(6, oldName);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(CategorySale categorySale) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                isDesired = resultSet.getString(0).equals(categorySale.getName());
                if (isDesired) {
                    categorySale.setDiscount(resultSet.getInt(1));
                    categorySale.setStartSaleDate(resultSet.getString(2));
                    categorySale.setEndSaleDate(resultSet.getString(3));
                    categorySale.setCategoryName(resultSet.getString(4));
                    break; //Desired category sale found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
