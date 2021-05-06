package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

public class ProductsInOrder extends DalObject<ProductsInOrder> {
    protected ProductsInOrder(DalController<ProductsInOrder> controller) {
        super(controller);
    }
}
