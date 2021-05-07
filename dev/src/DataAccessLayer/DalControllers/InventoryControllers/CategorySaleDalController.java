package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.CategorySale;

public class CategorySaleDalController extends DalController<CategorySale> {
    private static CategorySaleDalController instance = null;
    final static String CATEGORY_SALE_TABLE_NAME = "Category Sales";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private CategorySaleDalController() {
        super(CATEGORY_SALE_TABLE_NAME);
    }

    public static CategorySaleDalController getInstance() {
        if (instance == null)
            instance = new CategorySaleDalController();
        return instance;
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(CategorySale dalObject) {
        return false;
    }

    @Override
    public boolean Delete(CategorySale dalObject) {
        return false;
    }

    @Override
    public CategorySale ConvertReaderToObject() {
        return null;
    }
}
