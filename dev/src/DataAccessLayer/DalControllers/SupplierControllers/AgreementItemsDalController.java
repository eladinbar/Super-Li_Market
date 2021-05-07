package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;
import DataAccessLayer.DalObjects.SupplierObjects.AgreementItems;

import java.sql.SQLException;

public class AgreementItemsDalController extends DalController<AgreementItems> {
    private static AgreementItemsDalController instance = null;
    final static String AGREEMENT_ITEMS_TABLE_NAME = "Agreement_Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private AgreementItemsDalController() throws SQLException {
        super(AGREEMENT_ITEMS_TABLE_NAME);
    }

    public static AgreementItemsDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new AgreementItemsDalController();
        return instance;
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
