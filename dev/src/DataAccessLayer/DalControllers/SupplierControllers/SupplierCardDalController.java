package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;

public class SupplierCardDalController extends DalController<SupplierCard> {
    final static String SUPPLIER_CARD_TABLE_NAME = "Supplier Cards";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public SupplierCardDalController() {
        super(SUPPLIER_CARD_TABLE_NAME);
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
