package SerciveLayer;

import InfrastructurePackage.Pair;
import SerciveLayer.Response.Response;
import SerciveLayer.Response.ResponseT;
import SerciveLayer.SimpleObjects.*;
import SerciveLayer.Objects.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface InventoryService {

    //-------------------------------------------------------------------------Item functions

    Response addItem(int id, String name, String categoryName, double costPrice, double sellingPrice, int minAmount,
                     String shelfLocation, String storageLocation, int shelfQuantity, int storageQuantity, int manufacturerId, List<String> suppliersIds);
    ResponseT<Item> getItem(int itemId);
    Response modifyItemName(int itemId, String newName);
    Response modifyItemCategory(int itemId, String newCategoryName);
    Response modifyItemCostPrice(int itemId, double newCostPrice);
    Response modifyItemSellingPrice(int itemId, double newSellingPrice);
    /*
    if the locations are null or empty, display an appropriate error message.
     */
    Response changeItemShelfLocation(int itemId, String newShelfLocation);
    Response changeItemStorageLocation(int itemId, String newStorageLocation);
    /*
    If quantity is a negative number the quantity of the respective item will not be changed.
     */
    Response modifyItemShelfQuantity(int itemId, int newShelfQuantity);
    Response modifyItemStorageQuantity(int itemId, int newStorageQuantity);
    Response addItemSupplier(int itemId, String supplierId);
    Response removeItemSupplier(int itemId, String supplierId);
    Response removeItem(int itemId);

    //-------------------------------------------------------------------------Category functions

    /*
    If parent category is null or empty, the new category should be added as a main category.
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
    <T extends SimpleEntity> ResponseT<Sale<T>> getSale(String saleName);
    Response addItemSale(String saleName, int itemID, double saleDiscount, LocalDate startDate, LocalDate endDate);
    Response addCategorySale(String saleName, String categoryName, double saleDiscount, LocalDate startDate, LocalDate endDate);



    Response modifySaleName(String oldName, String newName);
    Response modifySaleDiscount(String saleName, double newDiscount);
    Response modifySaleDates(String saleName, LocalDate startDate, LocalDate endDate);

    //-------------------------------------------------------------------------Discount functions

    <T extends SimpleEntity> ResponseT<List<Discount<T>>> getDiscount(String supplierId, LocalDate discountDate);
    Response addItemDiscount(String supplierId, double discount, LocalDate discountDate, int itemCount, int itemId);
    Response addCategoryDiscount(String supplierId, double discount, LocalDate discountDate, int itemCount, String categoryName);

    //-------------------------------------------------------------------------Defect functions

    Response recordDefect(int itemId, LocalDate entryDate, int defectQuantity, String defectLocation);

    //-------------------------------------------------------------------------Report functions

    ResponseT<List<Item>> inventoryReport();
    ResponseT<List<Item>> categoryReport(String categoryName);
    ResponseT<List<Item>> itemShortageReport();
    ResponseT<List<DefectEntry>> defectsReport(LocalDate fromDate, LocalDate toDate);

    ResponseT<Pair<Map<Integer, Integer >,Map<Integer, String>>> getItemsInShortAndQuantities();

    Response updateQuantityInventory(ArrayList<Product> items);
}
