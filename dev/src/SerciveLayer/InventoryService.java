package SerciveLayer;

import SerciveLayer.Response.ResponseT;
import SerciveLayer.SimpleObjects.*;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface InventoryService {

    //-------------------------------------------------------------------------Item functions

    InResponse addItem(int id, String name, String categoryName, double costPrice, double sellingPrice, int minAmount,
                       String shelfLocation, String storageLocation, int shelfQuantity, int storageQuantity, int manufacturerId, List<Integer> suppliersIds);
    InResponseT<Item> getItem(int itemId);
    InResponse modifyItemName(int itemId, String newName);
    InResponse modifyItemCategory(int itemId, String newCategoryName);
    InResponse modifyItemCostPrice(int itemId, double newCostPrice);
    InResponse modifyItemSellingPrice(int itemId, double newSellingPrice);
    /*
    if the locations are null or empty, display an appropriate error message.
     */
    InResponse changeItemShelfLocation(int itemId, String newShelfLocation);
    InResponse changeItemStorageLocation(int itemId, String newStorageLocation);
    /*
    If quantity is a negative number the quantity of the respective item will not be changed.
     */
    InResponse modifyItemShelfQuantity(int itemId, int newShelfQuantity);
    InResponse modifyItemStorageQuantity(int itemId, int newStorageQuantity);
    InResponse addItemSupplier(int itemId, int supplierId);
    InResponse removeItemSupplier(int itemId, int supplierId);
    InResponse removeItem(int itemId);

    //-------------------------------------------------------------------------Category functions

    /*
    If parent category is null or empty, the new category should be added as a main category.
     */
    InResponse addCategory(String categoryName, String parentCategoryName);
    InResponseT<Category> getCategory(String categoryName);
    InResponse modifyCategoryName(String oldName, String newName);
    /*
    when the category is deleted all its sub categories move to the parent category.
     */
    InResponse removeCategory(String categoryName);
    InResponse changeParentCategory(String categoryName, String newParentName);
    //-------------------------------------------------------------------------Sale functions
    <T extends SimpleEntity> InResponseT<Sale<T>> getSale(String saleName);
    InResponse addItemSale(String saleName, int itemID, double saleDiscount, Calendar startDate, Calendar endDate);
    InResponse addCategorySale(String saleName, String categoryName, double saleDiscount, Calendar startDate, Calendar endDate);



    InResponse modifySaleName(String oldName, String newName);
    InResponse modifySaleDiscount(String saleName, double newDiscount);
    InResponse modifySaleDates(String saleName, Calendar startDate, Calendar endDate);

    //-------------------------------------------------------------------------Discount functions

    <T extends SimpleEntity> InResponseT<List<Discount<T>>> getDiscount(int supplierId, Calendar discountDate);
    InResponse addItemDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, int itemId);
    InResponse addCategoryDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, String categoryName);

    //-------------------------------------------------------------------------Defect functions

    InResponse recordDefect(int itemId, Calendar entryDate, int defectQuantity, String defectLocation);

    //-------------------------------------------------------------------------Report functions

    InResponseT<List<Item>> inventoryReport();
    InResponseT<List<Item>> categoryReport(String categoryName);
    InResponseT<List<Item>> itemShortageReport();
    InResponseT<List<DefectEntry>> defectsReport(Calendar fromDate, Calendar toDate);

    ResponseT<Map<Integer, Integer>> getItemsInShortAndQuantities();
}
