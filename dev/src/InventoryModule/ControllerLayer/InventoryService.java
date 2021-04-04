package InventoryModule.ControllerLayer;

import InventoryModule.ControllerLayer.SimpleObjects.Category;
import InventoryModule.ControllerLayer.SimpleObjects.DefectEntry;
import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.util.Date;
import java.util.List;

public interface InventoryService {

    Response addItem(int id, String name, String categoryName, double costPrice, double sellingPrice, int minAmount,
                     String shelfLocation, String storageLocation, int storageQuantity, int shelfQuantity, int manufacturerId, List<Integer> suppliersIds);
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

    //-------------------------------------------------------------------------

    /*
    If parent category is null, the new category should be added as a main category.
     */
    Response addCategory(String categoryName, String parentCategory);
    ResponseT<Category> getCategory(String categoryName);
    Response modifyCategoryName(String oldName, String newName);
    /*
    when the category is deleted all its sub categories move to the parent category.
     */
    Response removeCategory(String categoryName);

    //-------------------------------------------------------------------------

    Response addItemSale(String saleName,int itemID, double saleDiscount, Date startDate, Date endDate);
    Response addCategorySale(String saleName,String categoryName, double saleDiscount, Date startDate, Date endDate);
    Response modifySaleName(String oldName, String newName);
    Response modifySaleDiscount(String saleName, double newDiscount);
    Response modifySaleDates(String saleName, Date startDate, Date endDate);

    //-------------------------------------------------------------------------

    Response addItemDiscount(String Supplier, double discount, Date discountDate, int itemCount, int itemId);
    Response addCategoryDiscount(String Supplier, double discount, Date discountDate, int itemCount, String categoryName);

    //-------------------------------------------------------------------------

    Response recordDefect(int itemId, String itemName, int defectQuantity, String defectLocation);

    //-------------------------------------------------------------------------

    ResponseT<List<Item>> inventoryReport();
    ResponseT<List<Item>> itemShortageReport();
    ResponseT<List<DefectEntry>> defectsReport(Date fromDate, Date toDate);
    ResponseT<List<Item>> categoryReport(Date fromDate, Date toDate);

    static InventoryService getInventoryService(){
        return new InventoryServiceImpl();
    }
}


