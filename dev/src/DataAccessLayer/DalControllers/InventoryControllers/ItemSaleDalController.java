package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.ItemSale;

public class ItemSaleDalController extends DalController<ItemSale> {
    final static String ITEM_DISCOUNT_TABLE_NAME = "Item Sales";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public ItemSaleDalController() {
        super(ITEM_DISCOUNT_TABLE_NAME);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(ItemSale dalObject) {
        return false;
    }

    @Override
    public boolean Delete(ItemSale dalObject) {
        return false;
    }

    @Override
    public ItemSale ConvertReaderToObject() {
        return null;
    }
}
