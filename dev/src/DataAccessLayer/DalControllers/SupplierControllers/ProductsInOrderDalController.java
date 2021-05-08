package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.ProductsInOrder;

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
