package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.QuantityListItems;

public class QuantityListItemsDalController extends DalController<QuantityListItems> {
    final String QUANTITY_LIST_ITEMS_TABLE_NAME = "Quantity List Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     * <param name="tableName">The table name of the object this controller represents.</param>
     *
     * @param tableName
     */
    public QuantityListItemsDalController(String tableName) {
        super(tableName);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(QuantityListItems dalObject) {
        return false;
    }

    @Override
    public boolean Delete(QuantityListItems dalObject) {
        return false;
    }

    @Override
    public QuantityListItems ConvertReaderToObject() {
        return null;
    }
}
