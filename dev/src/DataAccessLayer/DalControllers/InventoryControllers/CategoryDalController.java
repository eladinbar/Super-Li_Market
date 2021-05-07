package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;

public class CategoryDalController extends DalController<Category> {
    final static String CATEGORY_TABLE_NAME = "Categories";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public CategoryDalController() {
        super(CATEGORY_TABLE_NAME);
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
