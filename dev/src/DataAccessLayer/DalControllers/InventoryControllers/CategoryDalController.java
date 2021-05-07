package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;

public class CategoryDalController extends DalController<Category> {
    private static CategoryDalController instance = null;
    final static String CATEGORY_TABLE_NAME = "Categories";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private CategoryDalController() {
        super(CATEGORY_TABLE_NAME);
    }

    public static CategoryDalController getInstance() {
        if (instance == null)
            instance = new CategoryDalController();
        return instance;
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(Category dalObject) {
        return false;
    }

    @Override
    public boolean Delete(Category dalObject) {
        return false;
    }

    @Override
    public Category ConvertReaderToObject() {
        return null;
    }
}
