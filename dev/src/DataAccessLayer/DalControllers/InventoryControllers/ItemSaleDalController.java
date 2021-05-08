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
                    "REFERENCES " + Item.itemIdColumnName + " (" + ITEM_TABLE_NAME + ") ON DELETE CASCADE," +
                    "CONSTRAINT Natural_Number CHECK (" + ItemSale.discountColumnName + " >= 0)," +
                    "CONSTRAINT End_Date CHECK (date(" + ItemSale.startSaleDateColumnName + ")>=date(" + ItemSale.endSaleDateColumnName + ") OR "
                    + ItemSale.endSaleDateColumnName + " IS NULL)" +
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
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, itemSale.getName());
            st.setDouble(2, itemSale.getDiscount());
            st.setString(3, itemSale.getStartSaleDate());
            st.setString(4, itemSale.getEndSaleDate());
            st.setInt(5, itemSale.getItemID());
            System.out.println("Executing " + tableName + " insert.");
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(ItemSale dalObject) {
        return false;
    }

    @Override
    public boolean update(ItemSale dalObject) {
        return false;
    }

    @Override
    public ItemSale select(ItemSale dalObject) {
        return null;
    }
}
