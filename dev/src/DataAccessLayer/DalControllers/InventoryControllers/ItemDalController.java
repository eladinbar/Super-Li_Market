package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;
import DataAccessLayer.DalObjects.InventoryObjects.CategorySale;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.InventoryObjects.ItemSale;

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
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
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
                    "REFERENCES " + Category.categoryNameColumnName + " (" + CATEGORY_TABLE_NAME + ") ON DELETE NO ACTION," +
                    "CONSTRAINT Natural_Number CHECK (" + Item.costPriceColumnName + ">= 0 AND " + Item.sellingPriceColumnName + ">=0 AND " +
                    Item.minAmountColumnName + ">=0 AND " + Item.shelfQuantityColumnName + ">=0 AND " + Item.storageQuantityColumnName + ">=0)" +
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
    public boolean insert(Item item) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?, ?, ?, ?, ?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, item.getItemID());
            stmt.setDouble(2, item.getCostPrice());
            stmt.setDouble(3, item.getSellingPrice());
            stmt.setInt(4, item.getManufacturerID());
            stmt.setInt(5, item.getMinAmount());
            stmt.setInt(6, item.getShelfQuantity());
            stmt.setInt(7, item.getStorageQuantity());
            stmt.setString(8, item.getShelfLocation());
            stmt.setString(9, item.getStorageLocation());
            stmt.setString(10, item.getCategoryName());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(Item item) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE ?=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, Item.itemIdColumnName);
            stmt.setInt(2, item.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Item item) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + Item.costPriceColumnName + "=?, " +
                    Item.sellingPriceColumnName + "=?, " + Item.manufacturerIdColumnName + "=?, " +
                    Item.minAmountColumnName + "=?, " + Item.shelfQuantityColumnName + "=?, " +
                    Item.storageQuantityColumnName + "=?, " + Item.shelfLocationColumnName + "=?, " +
                    Item.storageLocationColumnName + "=?, " + Item.categoryNameColumnName +
                    "=? WHERE(" + Item.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setDouble(1, item.getCostPrice());
            stmt.setDouble(2, item.getSellingPrice());
            stmt.setInt(3, item.getManufacturerID());
            stmt.setInt(4, item.getMinAmount());
            stmt.setInt(5, item.getShelfQuantity());
            stmt.setInt(6, item.getStorageQuantity());
            stmt.setString(7, item.getShelfLocation());
            stmt.setString(8, item.getStorageLocation());
            stmt.setString(9, item.getCategoryName());
            stmt.setInt(10, item.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public Item select(Item item) {
        return null;
    }
}
