package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;

import java.sql.SQLException;

public class SupplierCardDalController extends DalController<SupplierCard> {
    private static SupplierCardDalController instance = null;
    final static String SUPPLIER_CARD_TABLE_NAME = "Supplier Cards";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private SupplierCardDalController() throws SQLException {
        super(SUPPLIER_CARD_TABLE_NAME);
    }

    public static SupplierCardDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new SupplierCardDalController();
        return instance;
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
