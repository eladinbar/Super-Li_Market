package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;

import java.sql.*;

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
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
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
            System.out.println("Creating '" + tableName + "' table.");
            stmt.executeUpdate();
            System.out.println("Table '" + tableName + "' created.");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(ItemDiscount itemDiscount) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, itemDiscount.getDiscountDate());
            stmt.setDouble(2, itemDiscount.getDiscount());
            stmt.setInt(3, itemDiscount.getItemCount());
            stmt.setInt(4, itemDiscount.getSupplierID());
            stmt.setInt(5, itemDiscount.getItemID());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(ItemDiscount itemDiscount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE ?=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, ItemDiscount.discountDateColumnName);
            stmt.setString(2, itemDiscount.getDiscountDate());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(ItemDiscount itemDiscount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + ItemDiscount.discountColumnName + "=?, " +
                    ItemDiscount.itemCountColumnName + "=?, " + ItemDiscount.supplierIdColumnName + "=?, " +
                    ItemDiscount.itemIdColumnName + "=? WHERE(" + ItemDiscount.discountDateColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setDouble(1, itemDiscount.getDiscount());
            stmt.setInt(2, itemDiscount.getItemCount());
            stmt.setInt(3, itemDiscount.getSupplierID());
            stmt.setInt(4, itemDiscount.getItemID());
            stmt.setString(5, itemDiscount.getDiscountDate());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public ItemDiscount select(ItemDiscount itemDiscount) throws SQLException {
        ItemDiscount savedItemDiscount = new ItemDiscount(itemDiscount.getDiscountDate(), 0, 0, 0, 0);
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(0).equals(itemDiscount.getDiscountDate());
                if (isDesired) {
                    savedItemDiscount.setDiscount(resultSet.getInt(1));
                    savedItemDiscount.setItemCount(resultSet.getInt(2));
                    savedItemDiscount.setSupplierID(resultSet.getInt(3));
                    savedItemDiscount.setItemID(resultSet.getInt(4));
                    break; //Desired item discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return savedItemDiscount;
    }
}
