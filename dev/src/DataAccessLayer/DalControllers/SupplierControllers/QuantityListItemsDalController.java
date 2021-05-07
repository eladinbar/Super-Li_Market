package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;
import DataAccessLayer.DalObjects.SupplierObjects.QuantityListItems;

import java.sql.SQLException;

public class QuantityListItemsDalController extends DalController<QuantityListItems> {
    private static QuantityListItemsDalController instance = null;
    final static String QUANTITY_LIST_ITEMS_TABLE_NAME = "Quantity List Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private QuantityListItemsDalController() throws SQLException {
        super(QUANTITY_LIST_ITEMS_TABLE_NAME);
    }

    public static QuantityListItemsDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new QuantityListItemsDalController();
        return instance;
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
