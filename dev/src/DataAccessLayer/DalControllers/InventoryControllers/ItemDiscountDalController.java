package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount;

public class ItemDiscountDalController extends DalController<ItemDiscount> {
    final static String ITEM_DISCOUNTS_TABLE_NAME = "Item Discounts";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public ItemDiscountDalController() {
        super(ITEM_DISCOUNTS_TABLE_NAME);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(ItemDiscount dalObject) {
        return false;
    }

    @Override
    public boolean Delete(ItemDiscount dalObject) {
        return false;
    }

    @Override
    public ItemDiscount ConvertReaderToObject() {
        return null;
    }
}
