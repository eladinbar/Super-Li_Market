package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;

public class ItemDalController extends DalController<Item> {
    private static ItemDalController instance = null;
    final static String ITEM_TABLE_NAME = "Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private ItemDalController() {
        super(ITEM_TABLE_NAME);
    }

    public static ItemDalController getInstance() {
        if (instance == null)
            instance = new ItemDalController();
        return instance;
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
