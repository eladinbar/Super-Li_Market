package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController$;
import DataAccessLayer.DalObjects.InventoryObjects.DalItem;
import DataAccessLayer.DalObjects.InventoryObjects.DalItemSale;

import java.sql.*;

import static DataAccessLayer.DalControllers.InventoryControllers.ItemDalController.ITEM_TABLE_NAME;

public class ItemSaleDalController extends DalController$<DalItemSale> {
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
    public boolean createTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalItemSale.itemSaleNameColumnName + " TEXT NOT NULL," +
                    DalItemSale.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    DalItemSale.startSaleDateColumnName + " TEXT NOT NULL," +
                    DalItemSale.endSaleDateColumnName + " TEXT NOT NULL," +
                    DalItemSale.itemIdColumnName + " INTEGER NOT NULL," +
                    "PRIMARY KEY (" + DalItemSale.itemSaleNameColumnName + ")," +
                    "FOREIGN KEY (" + DalItemSale.itemIdColumnName + ")" +
                    "REFERENCES " + DalItem.itemIdColumnName + " (" + ITEM_TABLE_NAME + ") ON DELETE CASCADE" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalItemSale itemSale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, itemSale.getName());
            stmt.setDouble(2, itemSale.getDiscount());
            stmt.setString(3, itemSale.getStartSaleDate());
            stmt.setString(4, itemSale.getEndSaleDate());
            stmt.setInt(5, itemSale.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalItemSale itemSale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "DELETE FROM " + tableName + " WHERE (" + DalItemSale.itemSaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, itemSale.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalItemSale itemSale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalItemSale.discountColumnName + "=?, " +
                    DalItemSale.startSaleDateColumnName + "=?, " + DalItemSale.endSaleDateColumnName + "=?, " +
                    DalItemSale.itemIdColumnName + "=? WHERE(" + DalItemSale.itemSaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setDouble(1, itemSale.getDiscount());
            stmt.setString(2, itemSale.getStartSaleDate());
            stmt.setString(3, itemSale.getEndSaleDate());
            stmt.setInt(4, itemSale.getItemID());
            stmt.setString(5, itemSale.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalItemSale itemSale, String oldName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalItemSale.itemSaleNameColumnName + "=?, " + DalItemSale.discountColumnName + "=?, " +
                    DalItemSale.startSaleDateColumnName + "=?, " + DalItemSale.endSaleDateColumnName + "=?, " +
                    DalItemSale.itemIdColumnName + "=? WHERE(" + DalItemSale.itemSaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, itemSale.getName());
            stmt.setDouble(2, itemSale.getDiscount());
            stmt.setString(3, itemSale.getStartSaleDate());
            stmt.setString(4, itemSale.getEndSaleDate());
            stmt.setInt(5, itemSale.getItemID());
            stmt.setString(6, oldName);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(DalItemSale itemSale) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(itemSale.getName());
                if (isDesired) {
                    itemSale.setDiscount(resultSet.getInt(2));
                    itemSale.setStartSaleDate(resultSet.getString(3));
                    itemSale.setEndSaleDate(resultSet.getString(4));
                    itemSale.setItemID(resultSet.getInt(5));
                    break; //Desired item sale found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
