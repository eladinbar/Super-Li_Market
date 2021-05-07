package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount;

public class ItemDiscountDalController extends DalController<ItemDiscount> {
    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     * <param name="tableName">The table name of the object this controller represents.</param>
     *
     * @param tableName
     */
    public ItemDiscountDalController(String tableName) {
        super(tableName);
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
