package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.QuantityListItems;

import java.sql.SQLException;

public class QuantityListItemsDalController extends DalController<QuantityListItems> {
    private static QuantityListItemsDalController instance = null;
    public final static String QUANTITY_LIST_ITEMS_TABLE_NAME = "Quantity_List_Items";

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
    public boolean createTable() throws SQLException {
        return true;
    }

    @Override
    public boolean insert(QuantityListItems dalObject) {
        return false;
    }

    @Override
    public boolean delete(QuantityListItems dalObject) {
        return false;
    }

    @Override
    public boolean update(QuantityListItems dalObject) {
        return false;
    }

    @Override
    public QuantityListItems select(QuantityListItems dalObject) {
        return null;
    }
}
