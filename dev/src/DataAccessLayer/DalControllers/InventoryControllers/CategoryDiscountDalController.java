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
    public void CreateTable() throws SQLException {
        System.out.println("Initiating create '" + CATEGORY_DISCOUNT_TABLE_NAME + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + CATEGORY_DISCOUNT_TABLE_NAME + " (" +
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
            System.out.println("Creating '" + CATEGORY_DISCOUNT_TABLE_NAME + "' table.");
            stmt.executeUpdate();
            System.out.println("Table '" + CATEGORY_DISCOUNT_TABLE_NAME + "' created.");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public boolean Insert(CategoryDiscount dalObject) {
        return false;
    }

    @Override
    public boolean Delete(CategoryDiscount dalObject) {
        return false;
    }

    @Override
    public CategoryDiscount ConvertReaderToObject() {
        return null;
    }
}
