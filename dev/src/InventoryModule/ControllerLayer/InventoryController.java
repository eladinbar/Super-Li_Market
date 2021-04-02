package InventoryModule.ControllerLayer;

import InventoryModule.BusinessLayer.Category;
import InventoryModule.BusinessLayer.DefectsPackage.DefectEntry;
import InventoryModule.BusinessLayer.DefectsPackage.DefectsLogger;
import InventoryModule.BusinessLayer.DiscountPackage.Discount;
import InventoryModule.BusinessLayer.Item;
import InventoryModule.BusinessLayer.SalePackage.Sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryController {
    private List<Category> categories;
    private DefectsLogger defectsLogger;
    private List<Discount> discounts;
    private List<Sale> sales;

    public InventoryController() {
        categories = new ArrayList<>();
        defectsLogger = new DefectsLogger();
        discounts = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public void addItem(int id, String name, String category, double costPrice, double sellingPrice, int minAmount,
                     String shelfLocation, String storageLocation, int storageQuantity, int shelfQuantity, int manufacturerId, List<Integer> suppliersId) {

    }

    public Item getItem(int itemId) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    return item;
            }
        }
        throw new IllegalArgumentException("No item with ID " + itemId + " was found in the system.");
    }

    public void modifyItemName(int itemId, String newName) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.setName(newName);
            }
        }
        throw new IllegalArgumentException("No item with ID " + itemId + " was found in the system.");
    }

    public void modifyItemCategory(int itemId, String newCategoryName) {

    }

    public void modifyItemCostPrice(int itemId, double newCostPrice) {

    }

    public void modifyItemSellPrice(int itemId, double newSellName) {

    }

    /*
    if the locations are null, the location will stay the same.
     */
    public void changeItemLocation(int itemId, String newStorageLocation, String newStoreLocation) {

    }

    /*
    If quantity is a negative number the quantity of the Respectable argument will not be changed.
     */
    public void modifyItemQuantity(int itemId, int newStorageQuantity, int newStoreQuantity) {

    }

    public void addItemSupplier(int itemId, int supplierId) {

    }

    public void removeItemSupplier(int itemId, int supplierId) {

    }


    //-------------------------------------------------------------------------

    /*
    If parent category is null, the new category should be added as a main category.
     */
    public void addCategory(String categoryName, String parentCategory) {

    }

    public Category getCategory(String categoryName) {
        return null;
    }

    public void modifyCategoryName(String oldName, String newName) {

    }

    /*
    when the category is deleted all its sub category move to the parent category.
     */
    public void removeCategory(String categoryName) {

    }


    //-------------------------------------------------------------------------

    public void addItemSale(String saleName, int itemID, double saleDiscount, Date startDate, Date endDate) {

    }

    public void addCategorySale(String saleName,String categoryName, double saleDiscount, Date startDate, Date endDate) {

    }

    public void modifySaleName(String oldName, String newName) {

    }

    public void modifySaleDiscount(String saleName, double newDiscount) {

    }

    public void modifySaleDates(String saleName, Date startDate, Date endDate) {

    }


    //-------------------------------------------------------------------------

    public void addItemDiscount(String Supplier, double discount, Date discountDate, int itemCount, int itemId) {

    }

    public void addCategoryDiscount(String Supplier, double discount, Date discountDate, int itemCount, String categoryName) {

    }


    //-------------------------------------------------------------------------

    public void recordDefect(int itemId, String itemName, int defectQuantity, String defectLocation) {

    }


    //-------------------------------------------------------------------------

    public List<Item> inventoryReport() {
        return null;
    }

    public List<Item> itemShortageReport() {
        return null;
    }

    public List<DefectEntry> defectsReport(Date fromDate, Date toDate) {
        return null;
    }

    public List<Item> categoryReport(Date fromDate, Date toDate) {
        return null;
    }
}
