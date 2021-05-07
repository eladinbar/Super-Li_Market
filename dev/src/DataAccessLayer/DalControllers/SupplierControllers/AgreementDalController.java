package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.Agreement;

public class AgreementDalController extends DalController<Agreement> {
    final static String AGREEMENT_TABLE_NAME = "Agreements";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public AgreementDalController() {
        super(AGREEMENT_TABLE_NAME);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(Agreement dalObject) {
        return false;
    }

    @Override
    public boolean Delete(Agreement dalObject) {
        return false;
    }

    @Override
    public Agreement ConvertReaderToObject() {
        return null;
    }
}
