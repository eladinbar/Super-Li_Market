package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;

public class SupplierCardDalController extends DalController<SupplierCard> {
    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     * <param name="tableName">The table name of the object this controller represents.</param>
     *
     * @param tableName
     */
    public SupplierCardDalController(String tableName) {
        super(tableName);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(SupplierCard dalObject) {
        return false;
    }

    @Override
    public boolean Delete(SupplierCard dalObject) {
        return false;
    }

    @Override
    public SupplierCard ConvertReaderToObject() {
        return null;
    }
}
