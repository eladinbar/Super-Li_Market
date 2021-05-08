package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.SupplierObjects.AgreementItems;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCard;
import DataAccessLayer.DalObjects.SupplierObjects.ProductsInOrder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductsInOrderDalController extends DalController<ProductsInOrder> {
    private static ProductsInOrderDalController instance = null;
    public final static String PRODUCTS_IN_ORDER_TABLE_NAME = "Products_In_Order";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private ProductsInOrderDalController() throws SQLException {
        super(PRODUCTS_IN_ORDER_TABLE_NAME);
    }

    public static ProductsInOrderDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new ProductsInOrderDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    ProductsInOrder.productIdColumnName + " INTEGER NOT NULL," +
                    ProductsInOrder.amountColumnName + " INTEGET NOT NULL," +
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
    public boolean insert(ProductsInOrder dalObject) {
        return false;
    }

    @Override
    public boolean delete(ProductsInOrder dalObject) {
        return false;
    }

    @Override
    public boolean update(ProductsInOrder dalObject) {
        return false;
    }

    @Override
    public ProductsInOrder select(ProductsInOrder dalObject) {
        return null;
    }
}
