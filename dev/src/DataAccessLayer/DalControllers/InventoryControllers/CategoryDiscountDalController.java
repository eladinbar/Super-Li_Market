package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount;

public class CategoryDiscountDalController extends DalController<CategoryDiscount> {
    final static String CATEGORY_DISCOUNT_TABLE_NAME = "Category Discounts";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public CategoryDiscountDalController() {
        super(CATEGORY_DISCOUNT_TABLE_NAME);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(CategoryDiscount dalObject) {
        return false;
    }

    @Override
    public boolean Delete(CategoryDiscount dalObject) {
        return false;
    }

    @Override
    public CategoryDiscount ConvertReaderToObject() {
        return null;
    }
}
