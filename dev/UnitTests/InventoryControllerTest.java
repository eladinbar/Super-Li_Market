import InventoryModule.BusinessLayer.Category;
import InventoryModule.BusinessLayer.DefectsPackage.DefectsLogger;
import InventoryModule.BusinessLayer.DiscountPackage.Discount;
import InventoryModule.BusinessLayer.SalePackage.Sale;
import InventoryModule.ControllerLayer.InventoryController;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class InventoryControllerTest {
    private static InventoryController inventoryController;

    @BeforeClass
    public static void setUp() {
        inventoryController = new InventoryController();
    }

    @After
    public void tearDown() {
        inventoryController = new InventoryController();
    }

    @Test
    public void addItemTest() {

    }

    @Test
    public void getItemTest() {
    }

    @Test
    public void modifyItemNameTest() {
    }

    @Test
    public void modifyItemCategoryTest() {
    }

    @Test
    public void modifyItemCostPriceTest() {
    }

    @Test
    public void modifyItemSellingPriceTest() {
    }

    @Test
    public void changeItemLocationTest() {
    }

    @Test
    public void changeItemShelfLocationTest() {
    }

    @Test
    public void changeItemStorageLocationTest() {
    }

    @Test
    public void modifyItemQuantityTest() {
    }

    @Test
    public void modifyItemShelfQuantityTest() {
    }

    @Test
    public void modifyItemStorageQuantityTest() {
    }

    @Test
    public void addItemSupplierTest() {
    }

    @Test
    public void removeItemSupplierTest() {
    }

    @Test
    public void removeItemTest() {
    }

    @Test
    public void testAddCategory_NullParent() {
        //act
        inventoryController.addCategory("test", null);
        //assert (an exception is NOT expected to be thrown here)
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCategory_nonExistingParent() {
        //act
        inventoryController.addCategory("test", "parent");
    }

    @Test
    public void testAddCategory_existingParent() {
        //set up
        inventoryController.addCategory("parent", null);
        //act
        inventoryController.addCategory("test", "parent");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCategory_existingCategory() {
        //set up
        inventoryController.addCategory("test", null);
        //act
        inventoryController.addCategory("test", null);
    }

    @Test
    public void testGetCategory_existingCategory() {
        //set up
        inventoryController.addCategory("test", null);
        //act
        inventoryController.getCategory("test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetCategory_nonExistingCategory() {
        //act
        inventoryController.getCategory("test");
    }

    @Test
    public void modifyCategoryNameTest() {
    }

    @Test
    public void changeParentCategoryTest() {

    }

    @Test
    public void removeCategoryTest() {
    }

    @Test
    public void addItemSaleTest() {
    }

    @Test
    public void addCategorySaleTest() {

    }

    @Test
    public void modifySaleNameTest() {
    }

    @Test
    public void modifySaleDiscountTest() {
    }

    @Test
    public void modifySaleDatesTest() {
    }

    @Test
    public void addItemDiscountTest() {
    }

    @Test
    public void addCategoryDiscountTest() {
    }

    @Test
    public void recordDefectTest() {
    }

    @Test
    public void inventoryReportTest() {
    }

    @Test
    public void categoryReportTest() {
    }

    @Test
    public void itemShortageReportTest() {
    }

    @Test
    public void defectsReportTest() {
    }
}