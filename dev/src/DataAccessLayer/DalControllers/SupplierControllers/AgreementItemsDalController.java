package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.AgreementItems;

public class AgreementItemsDalController extends DalController<AgreementItems> {
    final static String AGREEMENT_ITEMS_TABLE_NAME = "Agreement Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public AgreementItemsDalController() {
        super(AGREEMENT_ITEMS_TABLE_NAME);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(AgreementItems dalObject) {
        return false;
    }

    @Override
    public boolean Delete(AgreementItems dalObject) {
        return false;
    }

    @Override
    public AgreementItems ConvertReaderToObject() {
        return null;
    }
}
