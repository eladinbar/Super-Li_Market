package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;
import DataAccessLayer.DalObjects.InventoryObjects.Item;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController.CATEGORY_TABLE_NAME;

public class ItemDalController extends DalController<Item> {
    private static ItemDalController instance = null;
    public final static String ITEM_TABLE_NAME = "Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private ItemDalController() throws SQLException {
        super(ITEM_TABLE_NAME);
    }

    public static ItemDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new ItemDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + ITEM_TABLE_NAME + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + ITEM_TABLE_NAME + " (" +
                    Item.itemIdColumnName + " INTEGER NOT NULL," +
                    Item.costPriceColumnName + " REAL DEFAULT 0 NOT NULL," +
                    Item.sellingPriceColumnName + " REAL DEFAULT 0 NOT NULL," +
                    Item.manufacturerIdColumnName + " INTEGER NOT NULL," +
                    Item.minAmountColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    Item.shelfQuantityColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    Item.storageQuantityColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    Item.shelfLocationColumnName + " TEXT NOT NULL," +
                    Item.storageLocationColumnName + " TEXT NOT NULL," +
                    Item.categoryNameColumnName + " TEXT NOT NULL," +
                    "PRIMARY KEY (" + Item.itemIdColumnName + ")," +
                    "FOREIGN KEY (" + Item.categoryNameColumnName + ")" +
                    "REFERENCES " + Category.categoryNameColumnName + " (" + CATEGORY_TABLE_NAME + ") ON DELETE CASCADE," +
                    "CONSTRAINT Natural_Number CHECK (" + Item.costPriceColumnName + ">= 0 AND " + Item.sellingPriceColumnName + ">=0 AND " +
                    Item.minAmountColumnName + ">=0 AND " + Item.shelfQuantityColumnName + ">=0 AND " + Item.storageQuantityColumnName + ">=0)" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            System.out.println("Creating '" + ITEM_TABLE_NAME + "' table.");
            stmt.executeUpdate();
            System.out.println("Table '" + ITEM_TABLE_NAME + "' created.");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(Item dalObject) {
        return false;
    }

    @Override
    public boolean delete(Item dalObject) {
        return false;
    }

    @Override
    public boolean update(Item dalObject) {
        return false;
    }

    @Override
    public Item select(Item dalObject) {
        return null;
    }
}
