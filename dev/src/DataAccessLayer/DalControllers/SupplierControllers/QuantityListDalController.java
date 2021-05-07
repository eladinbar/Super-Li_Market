package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.QuantityList;

public class QuantityListDalController extends DalController<QuantityList> {
    final static String QUANTITY_LIST_TABLE_NAME = "Quantity List";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public QuantityListDalController() {
        super(QUANTITY_LIST_TABLE_NAME);
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
