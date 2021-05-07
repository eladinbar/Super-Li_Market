package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;
import DataAccessLayer.DalObjects.SupplierObjects.QuantityList;

import java.sql.SQLException;

public class QuantityListDalController extends DalController<QuantityList> {
    private static QuantityListDalController instance = null;
    final static String QUANTITY_LIST_TABLE_NAME = "Quantity List";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private QuantityListDalController() throws SQLException {
        super(QUANTITY_LIST_TABLE_NAME);
    }

    public static QuantityListDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new QuantityListDalController();
        return instance;
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(QuantityList dalObject) {
        return false;
    }

    @Override
    public boolean Delete(QuantityList dalObject) {
        return false;
    }

    @Override
    public QuantityList ConvertReaderToObject() {
        return null;
    }
}
