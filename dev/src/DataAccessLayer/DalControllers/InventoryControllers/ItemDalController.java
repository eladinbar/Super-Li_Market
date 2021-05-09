package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;
import DataAccessLayer.DalObjects.InventoryObjects.Item;

import java.sql.*;

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
                    Item.itemNameColumnName + " TEXT," +
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
    public boolean insert(Item item) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?, ?, ?,?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setInt(1, item.getItemID());
            stmt.setString(2, item.getName());
            stmt.setDouble(3, item.getCostPrice());
            stmt.setDouble(4, item.getSellingPrice());
            stmt.setInt(5, item.getManufacturerID());
            stmt.setInt(6, item.getMinAmount());
            stmt.setInt(7, item.getShelfQuantity());
            stmt.setInt(8, item.getStorageQuantity());
            stmt.setString(9, item.getShelfLocation());
            stmt.setString(10, item.getStorageLocation());
            stmt.setString(11, item.getCategoryName());
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
            String command = "DELETE FROM " + tableName + " WHERE (" + Item.itemIdColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setInt(1, item.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Item item) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + Item.itemNameColumnName + "=?," +
                    Item.costPriceColumnName + "=?, " + Item.sellingPriceColumnName + "=?, " +
                    Item.manufacturerIdColumnName + "=?, " + Item.minAmountColumnName + "=?, " +
                    Item.shelfQuantityColumnName + "=?, " + Item.storageQuantityColumnName + "=?, " +
                    Item.shelfLocationColumnName + "=?, " + Item.storageLocationColumnName + "=?, " +
                    Item.categoryNameColumnName + "=? WHERE(" + Item.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getCostPrice());
            stmt.setDouble(3, item.getSellingPrice());
            stmt.setInt(4, item.getManufacturerID());
            stmt.setInt(5, item.getMinAmount());
            stmt.setInt(6, item.getShelfQuantity());
            stmt.setInt(7, item.getStorageQuantity());
            stmt.setString(8, item.getShelfLocation());
            stmt.setString(9, item.getStorageLocation());
            stmt.setString(10, item.getCategoryName());
            stmt.setInt(11, item.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(Item item, int oldId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + Item.itemIdColumnName + "=?, " + Item.itemNameColumnName + "=?," +
                    Item.costPriceColumnName + "=?, " + Item.sellingPriceColumnName + "=?, " +
                    Item.manufacturerIdColumnName + "=?, " + Item.minAmountColumnName + "=?, " +
                    Item.shelfQuantityColumnName + "=?, " + Item.storageQuantityColumnName + "=?, " +
                    Item.shelfLocationColumnName + "=?, " + Item.storageLocationColumnName + "=?, " +
                    Item.categoryNameColumnName + "=? WHERE(" + Item.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setInt(1, item.getItemID());
            stmt.setString(2, item.getName());
            stmt.setDouble(3, item.getCostPrice());
            stmt.setDouble(4, item.getSellingPrice());
            stmt.setInt(5, item.getManufacturerID());
            stmt.setInt(6, item.getMinAmount());
            stmt.setInt(7, item.getShelfQuantity());
            stmt.setInt(8, item.getStorageQuantity());
            stmt.setString(9, item.getShelfLocation());
            stmt.setString(10, item.getStorageLocation());
            stmt.setString(11, item.getCategoryName());
            stmt.setInt(12, oldId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public Item select(Item item) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getInt(0) == item.getItemID();
                if (isDesired) {
                    item.setName(resultSet.getString(1));
                    item.setCostPrice(resultSet.getDouble(2));
                    item.setSellingPrice(resultSet.getDouble(3));
                    item.setManufacturerID(resultSet.getInt(4));
                    item.setMinAmount(resultSet.getInt(5));
                    item.setShelfQuantity(resultSet.getInt(6));
                    item.setStorageQuantity(resultSet.getInt(7));
                    item.setShelfLocation(resultSet.getString(8));
                    item.setStorageLocation(resultSet.getString(9));
                    item.setCategoryName(resultSet.getString(10));
                    break; //Desired item found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return item;
    }
}
