package InventoryModule.ControllerLayer;

import InventoryModule.ControllerLayer.SimpleObjects.*;

import java.util.Calendar;
import java.util.List;

public interface InventoryService {

    //-------------------------------------------------------------------------Item functions

    Response addItem(int id, String name, String categoryName, double costPrice, double sellingPrice, int minAmount,
                     String shelfLocation, String storageLocation, int shelfQuantity, int storageQuantity, int manufacturerId, List<Integer> suppliersIds);
    ResponseT<Item> getItem(int itemId);
    Response modifyItemName(int itemId, String newName);
    Response modifyItemCategory(int itemId, String newCategoryName);
    Response modifyItemCostPrice(int itemId, double newCostPrice);
    Response modifyItemSellingPrice(int itemId, double newSellingPrice);
    /*
    if the locations are null, the location will stay the same.
     */
    Response changeItemLocation(int itemId, String newStorageLocation, String newShelfLocation);
    Response changeItemShelfLocation(int itemId, String newShelfLocation);
    Response changeItemStorageLocation(int itemId, String newStorageLocation);
    /*
    If quantity is a negative number the quantity of the respective item will not be changed.
     */
    Response modifyItemQuantity(int itemId, int newStorageQuantity, int newShelfQuantity);
    Response modifyItemShelfQuantity(int itemId, int newShelfQuantity);
    Response modifyItemStorageQuantity(int itemId, int newStorageQuantity);
    Response addItemSupplier(int itemId, int supplierId);
    Response removeItemSupplier(int itemId, int supplierId);
    Response removeItem(int itemId);

    //-------------------------------------------------------------------------Category functions

    /*
    If parent category is null, the new category should be added as a main category.
     */
    Response addCategory(String categoryName, String parentCategoryName);
    ResponseT<Category> getCategory(String categoryName);
    Response modifyCategoryName(String oldName, String newName);
    /*
    when the category is deleted all its sub categories move to the parent category.
     */
    Response removeCategory(String categoryName);
    Response changeParentCategory(String categoryName, String newParentName);
    //-------------------------------------------------------------------------Sale functions
    <T extends SimpleEntity> ResponseT<Sale<T>> showSale(String saleName);
    Response addItemSale(String saleName, int itemID, double saleDiscount, Calendar startDate, Calendar endDate);
    Response addCategorySale(String saleName,String categoryName, double saleDiscount, Calendar startDate, Calendar endDate);



    Response modifySaleName(String oldName, String newName);
    Response modifySaleDiscount(String saleName, double newDiscount);
    Response modifySaleDates(String saleName, Calendar startDate, Calendar endDate);

    //-------------------------------------------------------------------------Discount functions

    <T extends SimpleEntity> ResponseT<List<Discount<T>>> getDiscount(int supplierId, Calendar discountDate);
    Response addItemDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, int itemId);
    Response addCategoryDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, String categoryName);

    //-------------------------------------------------------------------------Defect functions

    Response recordDefect(int itemId, Calendar entryDate, int defectQuantity, String defectLocation);

    //-------------------------------------------------------------------------Report functions

    ResponseT<List<Item>> inventoryReport();
    ResponseT<List<Item>> categoryReport(String categoryName);
    ResponseT<List<Item>> itemShortageReport();
    ResponseT<List<DefectEntry>> defectsReport(Calendar fromDate, Calendar toDate);
}