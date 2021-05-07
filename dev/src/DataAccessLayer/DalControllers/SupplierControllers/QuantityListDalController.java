package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.QuantityList;

public class QuantityListDalController extends DalController<QuantityList> {
    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     * <param name="tableName">The table name of the object this controller represents.</param>
     *
     * @param tableName
     */
    public QuantityListDalController(String tableName) {
        super(tableName);
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
