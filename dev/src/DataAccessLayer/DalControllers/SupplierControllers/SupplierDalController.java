package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.Supplier;

public class SupplierDalController extends DalController<Supplier> {
    final static String SUPPLIER_TABLE_NAME = "Suppliers";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public SupplierDalController() {
        super(SUPPLIER_TABLE_NAME);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(Supplier dalObject) {
        return false;
    }

    @Override
    public boolean Delete(Supplier dalObject) {
        return false;
    }

    @Override
    public Supplier ConvertReaderToObject() {
        return null;
    }
}
