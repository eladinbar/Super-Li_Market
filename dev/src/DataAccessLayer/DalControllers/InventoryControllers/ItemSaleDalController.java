package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.InventoryObjects.ItemSale;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static DataAccessLayer.DalControllers.InventoryControllers.ItemDalController.ITEM_TABLE_NAME;

public class ItemSaleDalController extends DalController<ItemSale> {
    private static ItemSaleDalController instance = null;
    public final static String ITEM_SALE_TABLE_NAME = "Item_Sales";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private ItemSaleDalController() throws SQLException {
        super(ITEM_SALE_TABLE_NAME);
    }

    public static ItemSaleDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new ItemSaleDalController();
        return instance;
    }

    @Override
    public void createTable() throws SQLException {
        System.out.println("Initiating create '" + ITEM_SALE_TABLE_NAME + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + ITEM_SALE_TABLE_NAME + " (" +
                    ItemSale.itemSaleNameColumnName + " TEXT NOT NULL," +
                    ItemSale.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    ItemSale.startSaleDateColumnName + " TEXT NOT NULL," +
                    ItemSale.endSaleDateColumnName + " TEXT NOT NULL," +
                    ItemSale.itemIdColumnName + " INTEGER NOT NULL," +
                    "PRIMARY KEY (" + ItemSale.itemSaleNameColumnName + ")," +
                    "FOREIGN KEY (" + ItemSale.itemIdColumnName + ")" +
                    "REFERENCES " + Item.itemIdColumnName + " (" + ITEM_TABLE_NAME + ") ON DELETE CASCADE," +
                    "CONSTRAINT Natural_Number CHECK (" + ItemSale.discountColumnName + " >= 0)," +
                    "CONSTRAINT End_Date CHECK (date(" + ItemSale.startSaleDateColumnName + ")>=date(" + ItemSale.endSaleDateColumnName + ") OR "
                    + ItemSale.endSaleDateColumnName + " IS NULL)" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            System.out.println("Creating '" + ITEM_SALE_TABLE_NAME + "' table.");
            stmt.executeUpdate();
            System.out.println("Table '" + ITEM_SALE_TABLE_NAME + "' created.");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    @Override
    public boolean insert(ItemSale dalObject) {
        return false;
    }

    @Override
    public boolean delete(ItemSale dalObject) {
        return false;
    }

    @Override
    public ItemSale convertReaderToObject() {
        return null;
    }
}
