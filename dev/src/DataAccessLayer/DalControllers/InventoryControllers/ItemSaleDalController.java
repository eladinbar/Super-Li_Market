package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.InventoryObjects.ItemSale;

import java.sql.*;

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
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    ItemSale.itemSaleNameColumnName + " TEXT NOT NULL," +
                    ItemSale.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    ItemSale.startSaleDateColumnName + " TEXT NOT NULL," +
                    ItemSale.endSaleDateColumnName + " TEXT NOT NULL," +
                    ItemSale.itemIdColumnName + " INTEGER NOT NULL," +
                    "PRIMARY KEY (" + ItemSale.itemSaleNameColumnName + ")," +
                    "FOREIGN KEY (" + ItemSale.itemIdColumnName + ")" +
                    "REFERENCES " + Item.itemIdColumnName + " (" + ITEM_TABLE_NAME + ") ON DELETE CASCADE" +
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
    public boolean insert(ItemSale itemSale) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, itemSale.getName());
            stmt.setDouble(2, itemSale.getDiscount());
            stmt.setString(3, itemSale.getStartSaleDate());
            stmt.setString(4, itemSale.getEndSaleDate());
            stmt.setInt(5, itemSale.getItemID());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(ItemSale itemSale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + ItemSale.itemSaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, itemSale.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(ItemSale itemSale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + ItemSale.discountColumnName + "=?, " +
                    ItemSale.startSaleDateColumnName + "=?, " + ItemSale.endSaleDateColumnName + "=?, " +
                    ItemSale.itemIdColumnName + "=? WHERE(" + ItemSale.itemSaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

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
    public ItemSale select(ItemSale itemSale) throws SQLException {
        ItemSale savedCategorySale = new ItemSale(itemSale.getName(), 0, null, null, 0);
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(0).equals(itemSale.getName());
                if (isDesired) {
                    savedCategorySale.setDiscount(resultSet.getInt(1));
                    savedCategorySale.setStartSaleDate(resultSet.getString(2));
                    savedCategorySale.setEndSaleDate(resultSet.getString(3));
                    savedCategorySale.setItemID(resultSet.getInt(4));
                    break; //Desired item sale found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return savedCategorySale;
    }
}
