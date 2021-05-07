package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.ProductsInOrder;

public class ProductsInOrderDalController extends DalController<ProductsInOrder> {
    final String PRODUCTS_IN_ORDER_TABLE_NAME = "Products In Order";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     * <param name="tableName">The table name of the object this controller represents.</param>
     *
     * @param tableName
     */
    public ProductsInOrderDalController(String tableName) {
        super(tableName);
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
