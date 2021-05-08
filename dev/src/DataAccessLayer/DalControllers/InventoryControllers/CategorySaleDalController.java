package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;
import DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount;
import DataAccessLayer.DalObjects.InventoryObjects.CategorySale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                    "REFERENCES " + Category.categoryNameColumnName + " (" + CATEGORY_TABLE_NAME + ") ON DELETE CASCADE," +
                    "CONSTRAINT Natural_Number CHECK (" + CategorySale.discountColumnName + " >= 0)," +
                    "CONSTRAINT End_Date CHECK (date(" + CategorySale.startSaleDateColumnName + ")>=date(" + CategorySale.endSaleDateColumnName + ") OR "
                    + CategorySale.endSaleDateColumnName + " IS NULL)" +
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
            String query = "INSERT INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
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
            String query = "DELETE FROM " + tableName + " WHERE ?=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, CategorySale.categorySaleNameColumnName);
            stmt.setString(2, categorySale.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(CategorySale categorySale) {
        return false;
    }

    @Override
    public CategorySale select(CategorySale categorySale) {
        return null;
    }
}
