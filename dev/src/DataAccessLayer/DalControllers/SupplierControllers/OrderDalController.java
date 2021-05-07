package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.Order;

public class OrderDalController extends DalController<Order> {
    final static String ORDER_TABLE_NAME = "Orders";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public OrderDalController() {
        super(ORDER_TABLE_NAME);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(Order dalObject) {
        return false;
    }

    @Override
    public boolean Delete(Order dalObject) {
        return false;
    }

    @Override
    public Order ConvertReaderToObject() {
        return null;
    }
}
