package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;
import DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController.CATEGORY_TABLE_NAME;
import static DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME;

public class CategoryDiscountDalController extends DalController<CategoryDiscount> {
    private static CategoryDiscountDalController instance = null;
    final static String CATEGORY_DISCOUNT_TABLE_NAME = "Category_Discounts";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private CategoryDiscountDalController() throws SQLException {
        super(CATEGORY_DISCOUNT_TABLE_NAME);
    }

    public static CategoryDiscountDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new CategoryDiscountDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    CategoryDiscount.discountDateColumnName + " TEXT NOT NULL," +
                    CategoryDiscount.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    CategoryDiscount.itemCountColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    CategoryDiscount.supplierIdColumnName + " INTEGER NOT NULL," +
                    CategoryDiscount.categoryNameColumnName + " TEXT NOT NULL," +
                    "PRIMARY KEY (" + CategoryDiscount.discountDateColumnName + ")," +
                    "FOREIGN KEY (" + CategoryDiscount.supplierIdColumnName + ")" +
                    "REFERENCES " + SupplierCard.supplierIdColumnName + " (" + SUPPLIER_CARD_TABLE_NAME + ") ON DELETE CASCADE," +
                    "FOREIGN KEY (" + CategoryDiscount.categoryNameColumnName + ")" +
                    "REFERENCES " + Category.categoryNameColumnName + " (" + CATEGORY_TABLE_NAME + ") ON DELETE CASCADE," +
                    "CONSTRAINT Natural_Number CHECK (" + CategoryDiscount.discountColumnName + ">= 0 AND " + CategoryDiscount.itemCountColumnName + ">=0)" +
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
    public boolean insert(CategoryDiscount categoryDiscount) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, categoryDiscount.getDiscountDate());
            stmt.setDouble(2, categoryDiscount.getDiscount());
            stmt.setInt(3, categoryDiscount.getItemCount());
            stmt.setInt(4, categoryDiscount.getSupplierID());
            stmt.setString(5, categoryDiscount.getCategoryName());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(CategoryDiscount categoryDiscount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE ?=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, CategoryDiscount.discountDateColumnName);
            stmt.setString(2, categoryDiscount.getDiscountDate());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(CategoryDiscount categoryDiscount) {
        return false;
    }

    @Override
    public CategoryDiscount select(CategoryDiscount categoryDiscount) {
        return null;
    }
}
