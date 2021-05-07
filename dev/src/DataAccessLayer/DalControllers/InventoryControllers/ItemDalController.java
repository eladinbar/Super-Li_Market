package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;

public class ItemDalController extends DalController<Item> {
    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     * <param name="tableName">The table name of the object this controller represents.</param>
     *
     * @param tableName
     */
    public ItemDalController(String tableName) {
        super(tableName);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(Item dalObject) {
        return false;
    }

    @Override
    public boolean Delete(Item dalObject) {
        return false;
    }

    @Override
    public Item ConvertReaderToObject() {
        return null;
    }
}
