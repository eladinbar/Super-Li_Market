package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;
import DataAccessLayer.DalObjects.SupplierObjects.ProductsInOrder;

public class ProductsInOrderDalController extends DalController<ProductsInOrder> {
    private static ProductsInOrderDalController instance = null;
    final static String PRODUCTS_IN_ORDER_TABLE_NAME = "Products In Order";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private ProductsInOrderDalController() {
        super(PRODUCTS_IN_ORDER_TABLE_NAME);
    }

    public static ProductsInOrderDalController getInstance() {
        if (instance == null)
            instance = new ProductsInOrderDalController();
        return instance;
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(ProductsInOrder dalObject) {
        return false;
    }

    @Override
    public boolean Delete(ProductsInOrder dalObject) {
        return false;
    }

    @Override
    public ProductsInOrder ConvertReaderToObject() {
        return null;
    }
}
