package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

public class Item extends DalObject<Item> {
    protected Item(DalController<Item> controller) {
        super(controller);
    }
}
