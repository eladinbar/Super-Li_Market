package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCardDal;

import java.sql.*;
import java.util.List;

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
                    ItemDiscount.supplierIdColumnName + " TEXT NOT NULL," +
                    ItemDiscount.itemIdColumnName + " INTEGER NOT NULL," +
                    ItemDiscount.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    ItemDiscount.itemCountColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    "PRIMARY KEY (" + ItemDiscount.discountDateColumnName + ", " + ItemDiscount.supplierIdColumnName + "," +
                    ItemDiscount.itemIdColumnName + ")," +
                    "FOREIGN KEY (" + ItemDiscount.supplierIdColumnName + ")" +
                    "REFERENCES " + SupplierCardDal.supplierIdColumnName + " (" + SUPPLIER_CARD_TABLE_NAME + ") ON DELETE NO ACTION," +
                    "FOREIGN KEY (" + ItemDiscount.itemIdColumnName + ")" +
                    "REFERENCES " + Item.itemIdColumnName + " (" + ITEM_TABLE_NAME + ") ON DELETE NO ACTION" +
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
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, itemDiscount.getDiscountDate());
            stmt.setString(2, itemDiscount.getSupplierID());
            stmt.setInt(3, itemDiscount.getItemID());
            stmt.setDouble(4, itemDiscount.getDiscount());
            stmt.setInt(5, itemDiscount.getItemCount());
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
            String command = "DELETE FROM " + tableName + " WHERE (" + ItemDiscount.discountDateColumnName + "=? AND " + ItemDiscount.supplierIdColumnName +
                    "=? AND " + ItemDiscount.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, itemDiscount.getDiscountDate());
            stmt.setString(2, itemDiscount.getSupplierID());
            stmt.setInt(3, itemDiscount.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(ItemDiscount itemDiscount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + ItemDiscount.discountColumnName + "=?, " + ItemDiscount.itemCountColumnName +
                    "=? WHERE(" + ItemDiscount.discountDateColumnName + "=? AND " + ItemDiscount.supplierIdColumnName
                    + "=? AND " + ItemDiscount.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setDouble(1, itemDiscount.getDiscount());
            stmt.setInt(2, itemDiscount.getItemCount());
            stmt.setString(3, itemDiscount.getDiscountDate());
            stmt.setString(4, itemDiscount.getSupplierID());
            stmt.setInt(5, itemDiscount.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(ItemDiscount itemDiscount, int oldId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + ItemDiscount.itemIdColumnName + "=?, " +
                    ItemDiscount.discountColumnName + "=?, " + ItemDiscount.itemCountColumnName +
                    "=? WHERE(" + ItemDiscount.discountDateColumnName + "=? AND " + ItemDiscount.supplierIdColumnName
                    + "=? AND " + ItemDiscount.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setInt(1, itemDiscount.getItemID());
            stmt.setDouble(2, itemDiscount.getDiscount());
            stmt.setInt(3, itemDiscount.getItemCount());
            stmt.setString(4, itemDiscount.getDiscountDate());
            stmt.setString(5, itemDiscount.getSupplierID());
            stmt.setInt(6, oldId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(ItemDiscount itemDiscount, String oldField) throws SQLException {
        if (extractType(oldField) == 1)
            return updateDate(itemDiscount, oldField);
        else //(extractType(oldField) == 2)
            return updateID(itemDiscount, oldField);
    }

    private boolean updateDate(ItemDiscount itemDiscount, String oldDate) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + ItemDiscount.discountDateColumnName + "=?, " +
                    ItemDiscount.discountColumnName + "=?, " + ItemDiscount.itemCountColumnName +
                    "=? WHERE(" + ItemDiscount.discountDateColumnName + "=? AND " + ItemDiscount.supplierIdColumnName
                    + "=? AND " + ItemDiscount.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, itemDiscount.getDiscountDate());
            stmt.setDouble(2, itemDiscount.getDiscount());
            stmt.setInt(3, itemDiscount.getItemCount());
            stmt.setString(4, oldDate);
            stmt.setString(5, itemDiscount.getSupplierID());
            stmt.setInt(6, itemDiscount.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    public boolean updateID(ItemDiscount itemDiscount, String oldId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + ItemDiscount.supplierIdColumnName + "=?, " +
                    ItemDiscount.discountColumnName + "=?, " + ItemDiscount.itemCountColumnName +
                    "=? WHERE(" + ItemDiscount.discountDateColumnName + "=? AND " + ItemDiscount.supplierIdColumnName
                    + "=? AND " + ItemDiscount.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, itemDiscount.getSupplierID());
            stmt.setDouble(2, itemDiscount.getDiscount());
            stmt.setInt(3, itemDiscount.getItemCount());
            stmt.setString(4, itemDiscount.getDiscountDate());
            stmt.setString(5, oldId);
            stmt.setInt(6, itemDiscount.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    private int extractType(String oldParameter) { //Assumes valid input from business layer
        if (oldParameter.indexOf("-") == 4)
            return 1; //Parameter represents date
        else //(oldParameter.charAt(0) >= 48 & oldParameter.charAt(0) <= 57)
            return 2; //Parameter represents supplier id
    }

    @Override
    public boolean select(ItemDiscount itemDiscount) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(itemDiscount.getDiscountDate()) &&
                        resultSet.getString(2).equals(itemDiscount.getSupplierID()) && resultSet.getInt(3) == itemDiscount.getItemID();
                if (isDesired) {
                    itemDiscount.setDiscount(resultSet.getInt(4));
                    itemDiscount.setItemCount(resultSet.getInt(5));
                    break; //Desired item discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    @Override
    public boolean select(ItemDiscount itemDiscount, List<ItemDiscount> itemDiscounts) throws SQLException {
        boolean isDesired = false;
        ItemDiscount savedItemDiscount = new ItemDiscount();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(itemDiscount.getDiscountDate()) &&
                        resultSet.getString(2).equals(itemDiscount.getSupplierID());
                if (isDesired) {
                    itemDiscount.setItemID(resultSet.getInt(3));
                    itemDiscount.setDiscount(resultSet.getInt(4));
                    itemDiscount.setItemCount(resultSet.getInt(5));
                    itemDiscounts.add(savedItemDiscount);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
