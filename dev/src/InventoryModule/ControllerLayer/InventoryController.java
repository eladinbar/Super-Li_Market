package InventoryModule.ControllerLayer;

import InventoryModule.BusinessLayer.*;
import InventoryModule.BusinessLayer.DefectsPackage.DefectEntry;
import InventoryModule.BusinessLayer.SalePackage.Sale;

import java.util.Calendar;
import java.util.List;

public class InventoryController {

    public void addItem(int id, String name, String categoryName, double costPrice, double sellingPrice,
                            int minAmount, String shelfLocation, String storageLocation, int shelfQuantity,
                            int storageQuantity, int manufacturerId, List<Integer> suppliersIds) {

    }

    public Item getItem(int itemId) {
        return null;
    }

    public void modifyItemName(int itemId, String newName) {
    }

    public void modifyItemCategory(int itemId, String newCategoryName) {
    }

    public void modifyItemCostPrice(int itemId, double newCostPrice) {

    }

    public void modifyItemSellingPrice(int itemId, double newSellingPrice) {

    }

    public void changeItemLocation(int itemId, String newShelfLocation, String newStorageLocation) {

    }

    public void changeItemShelfLocation(int itemId, String newShelfLocation) {

    }

    public void changeItemStorageLocation(int itemId, String newStorageLocation) {

    }

    public void modifyItemQuantity(int itemId, int newShelfQuantity, int newStorageQuantity) {

    }

    public void modifyItemShelfQuantity(int itemId, int newShelfQuantity) {

    }

    public void modifyItemStorageQuantity(int itemId, int newStorageQuantity) {

    }

    public void addItemSupplier(int itemId, int supplierId) {

    }

    public void removeItemSupplier(int itemId, int supplierId) {

    }

    public void removeItem(int itemId) {

    }

    //-------------------------------------------------------------------------Category functions

    public void addCategory(String categoryName, String parentCategoryName) {

    }

    public Category getCategory(String categoryName) {
        return null;
    }

    public void modifyCategoryName(String oldName, String newName) {

    }

    public void removeCategory(String categoryName) {

    }

    public void changeParentCategory(String categoryName, String newParentName) {

    }



    public Sale showSale(String saleName) {
        return null;
    }

    public void addItemSale(String saleName, int itemID, double saleDiscount, Calendar startDate, Calendar endDate) {

    }

    public void addCategorySale(String saleName, String categoryName, double saleDiscount, Calendar startDate, Calendar endDate) {

    }

    //-------------------------------------------------------------------------Sale functions

    public void modifySaleName(String oldName, String newName) {

    }

    public void modifySaleDiscount(String saleName, double newDiscount) {

    }

    public void modifySaleDates(String saleName, Calendar startDate, Calendar endDate) {

    }




    //-------------------------------------------------------------------------Discount functions

    public void addItemDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, int itemId) {

    }

    public void addCategoryDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, String categoryName) {

    }

    //-------------------------------------------------------------------------Defect Functions

    public void recordDefect(int itemId, String itemName, Calendar entryDate, int defectQuantity, String defectLocation) {

    }

    //-------------------------------------------------------------------------Report functions

    public List<Item> inventoryReport() {
        return null;
    }

    public List<Item> categoryReport(String categoryName) {
        return null;
    }

    public List<Item> itemShortageReport() {
        return null;
    }

    public List<DefectEntry> defectsReport(Calendar fromDate, Calendar toDate) {
        return null;
    }
}
