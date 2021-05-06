package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

public class ItemSale extends DalObject<ItemSale> {
    protected ItemSale(DalController<ItemSale> controller) {
        super(controller);
    }
}
