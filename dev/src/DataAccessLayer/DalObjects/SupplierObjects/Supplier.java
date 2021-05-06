package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

public class Supplier extends DalObject<Supplier> {
    protected Supplier(DalController<Supplier> controller) {
        super(controller);
    }
}
