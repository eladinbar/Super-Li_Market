package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.ItemSale;

import java.sql.SQLException;

public class ItemSaleDalController extends DalController<ItemSale> {
    private static ItemSaleDalController instance = null;
    final static String ITEM_DISCOUNT_TABLE_NAME = "Item Sales";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private ItemSaleDalController() throws SQLException {
        super(ITEM_DISCOUNT_TABLE_NAME);
    }

    public static ItemSaleDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new ItemSaleDalController();
        return instance;
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
