import InventoryModule.BusinessLayer.Category;
import InventoryModule.BusinessLayer.InventoryController;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

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

    @Test(expected = IllegalArgumentException.class)
    public void testAddItem_nonExistingCategory() {
        //act
        inventoryController.addItem(1, "item", "category", 1, 1, 1,
                "SH-A3-L-S4", "ST-A1-R-S2", 1, 1, 1, new ArrayList<>());
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
        inventoryController.getCategory("test");
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
        //assert (an exception is NOT expected to be thrown here)
        inventoryController.getCategory("test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCategory_sameParent() {
        //act
        inventoryController.addCategory("test", "test");
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
    public void testModifyCategoryName_existingCategory() {
        //set up
        inventoryController.addCategory("test", null);
        //act
        inventoryController.modifyCategoryName("test", "newTest");
        //assert
        inventoryController.getCategory("newTest");
        try {
            inventoryController.getCategory("test");
            fail("Test expected getCategory(\"test\") to throw an exception.");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testModifyCategoryName_nonExistingCategory() {
        //act
        inventoryController.modifyCategoryName("test", "newTest");
    }

    @Test
    public void testChangeParentCategoryTest_validParent() {
        //set up
        inventoryController.addCategory("test", null);
        //act
        inventoryController.changeParentCategory("test", null);
        //assert
        Category category = inventoryController.getCategory("test");
        assertEquals(category.getParentCategory().getName(), "Uncategorized");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeParentCategoryTest_subCategory() {
        //set up
        inventoryController.addCategory("test", null);
        inventoryController.addCategory("test2", "test");
        //act
        inventoryController.changeParentCategory("test", "test2");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testChangeParentCategoryTest_sameCategory() {
        //set up
        inventoryController.addCategory("test", null);
        //act
        inventoryController.changeParentCategory("test", "test");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveCategory_nonExistingCategory() {
        //act
        inventoryController.removeCategory("test");
    }

    @Test
    public void testRemoveCategory_existingCategory() {
        //set up
        inventoryController.addCategory("test", null);
        //act
        inventoryController.removeCategory("test");
        //assert
        try {
            inventoryController.getCategory("test");
            fail("Test expected IllegalArgumentException to be thrown after trying to get non-existing category.");
        } catch(IllegalArgumentException ex) {
            assertTrue(true); //Exception caught, function worked as intended
        }
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