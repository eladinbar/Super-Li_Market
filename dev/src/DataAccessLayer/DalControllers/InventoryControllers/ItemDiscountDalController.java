package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static DataAccessLayer.DalControllers.InventoryControllers.ItemDalController.ITEM_TABLE_NAME;
import static DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME;

public class ItemDiscountDalController extends DalController<ItemDiscount> {
    private static ItemDiscountDalController instance = null;
    public final static String ITEM_DISCOUNT_TABLE_NAME = "Item_Discounts";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private ItemDiscountDalController() throws SQLException {
        super(ITEM_DISCOUNT_TABLE_NAME);
    }

    public static ItemDiscountDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new ItemDiscountDalController();
        return instance;
    }

    @Override
    public void createTable() throws SQLException {
        System.out.println("Initiating create '" + ITEM_DISCOUNT_TABLE_NAME + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + ITEM_DISCOUNT_TABLE_NAME + " (" +
                    ItemDiscount.discountDateColumnName + " TEXT NOT NULL," +
                    ItemDiscount.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    ItemDiscount.itemCountColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    ItemDiscount.supplierIdColumnName + " INTEGER NOT NULL," +
                    ItemDiscount.itemIdColumnName + " INTEGER NOT NULL," +
                    "PRIMARY KEY (" + ItemDiscount.discountDateColumnName + ")," +
                    "FOREIGN KEY (" + ItemDiscount.supplierIdColumnName + ")" +
                    "REFERENCES " + SupplierCard.supplierIdColumnName + " (" + SUPPLIER_CARD_TABLE_NAME + ") ON DELETE CASCADE," +
                    "FOREIGN KEY (" + ItemDiscount.itemIdColumnName + ")" +
                    "REFERENCES " + Item.itemIdColumnName + " (" + ITEM_TABLE_NAME + ") ON DELETE CASCADE," +
                    "CONSTRAINT Natural_Number CHECK (" + ItemDiscount.discountColumnName + ">=0 AND " + ItemDiscount.itemCountColumnName + ">=0)" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            System.out.println("Creating '" + ITEM_DISCOUNT_TABLE_NAME + "' table.");
            stmt.executeUpdate();
            System.out.println("Table '" + ITEM_DISCOUNT_TABLE_NAME + "' created.");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public boolean insert(ItemDiscount dalObject) {
        return false;
    }

    @Override
    public boolean delete(ItemDiscount dalObject) {
        return false;
    }

    @Override
    public ItemDiscount convertReaderToObject() {
        return null;
    }
}
