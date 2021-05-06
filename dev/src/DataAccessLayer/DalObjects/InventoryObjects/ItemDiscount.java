package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

public class ItemDiscount extends DalObject<ItemDiscount> {
    protected ItemDiscount(DalController<ItemDiscount> controller) {
        super(controller);
    }
}
