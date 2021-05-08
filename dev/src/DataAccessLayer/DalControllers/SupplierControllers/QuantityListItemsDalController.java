package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.SupplierObjects.ProductsInOrder;
import DataAccessLayer.DalObjects.SupplierObjects.QuantityListItems;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuantityListItemsDalController extends DalController<QuantityListItems> {
    private static QuantityListItemsDalController instance = null;
    public final static String QUANTITY_LIST_ITEMS_TABLE_NAME = "Quantity_List_Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private QuantityListItemsDalController() throws SQLException {
        super(QUANTITY_LIST_ITEMS_TABLE_NAME);
    }

    public static QuantityListItemsDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new QuantityListItemsDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    QuantityListItems.productIdColumnName + " INTEGER NOT NULL," +
                    QuantityListItems.amountColumnName+ " INTEGET NOT NULL," + QuantityListItems.discountColumnName+ " REAL NOT NULL,"+
                    "FOREIGN KEY (" + ProductsInOrder.productIdColumnName + ")" + "REFERENCES " + Item.itemIdColumnName + " (" + ItemDalController.ITEM_TABLE_NAME +") ON DELETE NO ACTION "+
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
    public boolean insert(QuantityListItems dalObject) {
        return false;
    }

    @Override
    public boolean delete(QuantityListItems dalObject) {
        return false;
    }

    @Override
    public boolean update(QuantityListItems dalObject) {
        return false;
    }

    @Override
    public QuantityListItems select(QuantityListItems dalObject) {
        return null;
    }
}
