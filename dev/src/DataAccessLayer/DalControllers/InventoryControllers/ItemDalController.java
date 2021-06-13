package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.DalCategory;
import DataAccessLayer.DalObjects.InventoryObjects.DalItem;

import java.sql.*;
import java.util.List;

import static DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController.CATEGORY_TABLE_NAME;

public class ItemDalController extends DalController<DalItem> {
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
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalItem.itemIdColumnName + " INTEGER NOT NULL," +
                    DalItem.itemNameColumnName + " TEXT," +
                    DalItem.costPriceColumnName + " REAL DEFAULT 0 NOT NULL," +
                    DalItem.sellingPriceColumnName + " REAL DEFAULT 0 NOT NULL," +
                    DalItem.manufacturerIdColumnName + " INTEGER NOT NULL," +
                    DalItem.minAmountColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    DalItem.shelfQuantityColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    DalItem.storageQuantityColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    DalItem.shelfLocationColumnName + " TEXT NOT NULL," +
                    DalItem.storageLocationColumnName + " TEXT NOT NULL," +
                    DalItem.weightColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    DalItem.categoryNameColumnName + " TEXT NOT NULL," +
                    "PRIMARY KEY (" + DalItem.itemIdColumnName + ")," +
                    "FOREIGN KEY (" + DalItem.categoryNameColumnName + ")" +
                    "REFERENCES " + DalCategory.categoryNameColumnName + " (" + CATEGORY_TABLE_NAME + ") ON DELETE CASCADE" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalItem item) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?, ?, ?,?, ?, ?, ?, ?, ?)";
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
            stmt.setInt(11, item.getWeight());
            stmt.setString(12, item.getCategoryName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalItem item) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "DELETE FROM " + tableName + " WHERE (" + DalItem.itemIdColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setInt(1, item.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalItem item) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalItem.itemNameColumnName + "=?," +
                    DalItem.costPriceColumnName + "=?, " + DalItem.sellingPriceColumnName + "=?, " +
                    DalItem.manufacturerIdColumnName + "=?, " + DalItem.minAmountColumnName + "=?, " +
                    DalItem.shelfQuantityColumnName + "=?, " + DalItem.storageQuantityColumnName + "=?, " +
                    DalItem.shelfLocationColumnName + "=?, " + DalItem.storageLocationColumnName + "=?, " +
                    DalItem.weightColumnName + "=?, " +
                    DalItem.categoryNameColumnName + "=? WHERE(" + DalItem.itemIdColumnName + "=?)";
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
            stmt.setInt(10, item.getWeight());
            stmt.setString(11, item.getCategoryName());
            stmt.setInt(12, item.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalItem item, int oldId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalItem.itemIdColumnName + "=?, " + DalItem.itemNameColumnName + "=?," +
                    DalItem.costPriceColumnName + "=?, " + DalItem.sellingPriceColumnName + "=?, " +
                    DalItem.manufacturerIdColumnName + "=?, " + DalItem.minAmountColumnName + "=?, " +
                    DalItem.shelfQuantityColumnName + "=?, " + DalItem.storageQuantityColumnName + "=?, " +
                    DalItem.shelfLocationColumnName + "=?, " + DalItem.storageLocationColumnName + "=?, " +
                    DalItem.weightColumnName + "=?, " +
                    DalItem.categoryNameColumnName + "=? WHERE(" + DalItem.itemIdColumnName + "=?)";
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
            stmt.setInt(11, item.getWeight());
            stmt.setString(12, item.getCategoryName());
            stmt.setInt(13, oldId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(DalItem item) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getInt(1) == item.getItemID();
                if (isDesired) {
                    item.setName(resultSet.getString(2));
                    item.setCostPrice(resultSet.getDouble(3));
                    item.setSellingPrice(resultSet.getDouble(4));
                    item.setManufacturerID(resultSet.getInt(5));
                    item.setMinAmount(resultSet.getInt(6));
                    item.setShelfQuantity(resultSet.getInt(7));
                    item.setStorageQuantity(resultSet.getInt(8));
                    item.setShelfLocation(resultSet.getString(9));
                    item.setStorageLocation(resultSet.getString(10));
                    item.setWeight(resultSet.getInt(11));
                    item.setCategoryName(resultSet.getString(12));
                    break; //Desired item found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    @Override
    public boolean select(DalItem item, List<DalItem> items) throws SQLException {
        boolean hasItem = false;
        DalItem savedItem;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(12).equals(item.getCategoryName());
                if (!hasItem & isDesired)
                    hasItem = true;
                if (isDesired) {
                    savedItem = new DalItem();
                    savedItem.setItemID(resultSet.getInt(1));
                    savedItem.setName(resultSet.getString(2));
                    savedItem.setCostPrice(resultSet.getDouble(3));
                    savedItem.setSellingPrice(resultSet.getDouble(4));
                    savedItem.setManufacturerID(resultSet.getInt(5));
                    savedItem.setMinAmount(resultSet.getInt(6));
                    savedItem.setShelfQuantity(resultSet.getInt(7));
                    savedItem.setStorageQuantity(resultSet.getInt(8));
                    savedItem.setShelfLocation(resultSet.getString(9));
                    savedItem.setStorageLocation(resultSet.getString(10));
                    savedItem.setWeight(resultSet.getInt(11));
                    savedItem.setCategoryName(resultSet.getString(12));
                    items.add(savedItem);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return hasItem;
    }
}
