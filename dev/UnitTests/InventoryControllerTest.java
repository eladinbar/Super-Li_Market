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

class InventoryControllerTest {
    InventoryController inventoryController;
    Category baseCategory;
    private List<Category> categories;
    private DefectsLogger defectsLogger;
    private List<Discount> discounts;
    private List<Sale> sales;

    @BeforeClass
    void setUp() {
        inventoryController = new InventoryController();
        baseCategory = new Category("Uncategorized");
        categories = new ArrayList<>();
        defectsLogger = new DefectsLogger();
        discounts = new ArrayList<>();
        sales = new ArrayList<>();
    }

    @After
    void tearDown() {

    }

    @Test
    void addItem() {

    }

    @Test
    void getItem() {
    }

    @Test
    void modifyItemName() {
    }

    @Test
    void modifyItemCategory() {
    }

    @Test
    void modifyItemCostPrice() {
    }

    @Test
    void modifyItemSellingPrice() {
    }

    @Test
    void changeItemLocation() {
    }

    @Test
    void changeItemShelfLocation() {
    }

    @Test
    void changeItemStorageLocation() {
    }

    @Test
    void modifyItemQuantity() {
    }

    @Test
    void modifyItemShelfQuantity() {
    }

    @Test
    void modifyItemStorageQuantity() {
    }

    @Test
    void addItemSupplier() {
    }

    @Test
    void removeItemSupplier() {
    }

    @Test
    void removeItem() {
    }

    @Test(expected = IllegalArgumentException.class)
    void addExistingCategory() {
        //set up
        Category newCategory = new Category("test");
        //act
        inventoryController.addCategory("test", "");
        //assert

    }

    @Test
    void addCategory() {
        //set up
        Category newCategory = new Category("test");
        //act
        inventoryController.addCategory("test", "");
        //assert

    }

    @Test
    void getCategory() {
    }

    @Test
    void modifyCategoryName() {
    }

    @Test
    void changeParentCategory() {

    }

    @Test
    void removeCategory() {
    }

    @Test
    void addItemSale() {
    }

    @Test
    void addCategorySale() {

    }

    @Test
    void modifySaleName() {
    }

    @Test
    void modifySaleDiscount() {
    }

    @Test
    void modifySaleDates() {
    }

    @Test
    void addItemDiscount() {
    }

    @Test
    void addCategoryDiscount() {
    }

    @Test
    void recordDefect() {
    }

    @Test
    void inventoryReport() {
    }

    @Test
    void categoryReport() {
    }

    @Test
    void itemShortageReport() {
    }

    @Test
    void defectsReport() {
    }
}