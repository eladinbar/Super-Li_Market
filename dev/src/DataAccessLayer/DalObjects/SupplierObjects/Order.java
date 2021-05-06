package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

public class Order extends DalObject<Order> {
    protected Order(DalController<Order> controller) {
        super(controller);
    }
}
