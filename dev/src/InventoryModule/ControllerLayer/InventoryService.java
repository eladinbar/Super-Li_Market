package InventoryModule.ControllerLayer;

import InventoryModule.ControllerLayer.SimpleObjects.Category;
import InventoryModule.ControllerLayer.SimpleObjects.DefectEntry;
import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.util.Date;
import java.util.List;

public interface InventoryService {

    public Response addItem(int id, String name, String category,
                            double costPrice, double sellingPrice, int minAmount,
                            String shelfLocation, String storageLocation,
                            int storageQuantity, int shelfQuantity,
                            int manufacturerId, List<Integer> suppliersId);
    public ResponseT<Item> getItem(int ItemId);
    public Response modifyItemName(int itemId, String newName);
    public Response modifyItemCategory(int itemId, String newCategoryName);
    public Response modifyItemCostPrice(int itemId, double newCostPrice);
    public Response modifyItemSellPrice(int itemId, double newSellName);
    /*
    if the locations are null, the location will stay the same.
     */
    public Response changeItemLocation(int itemId, String newStorageLocation, String newStoreLocation);
    /*
    If quantity is a negative number the quantity of the Respectable argument will not be changed.
     */
    public Response modifyItemQuantity(int itemId, int newStorageQuantity, int newStoreQuantity);
    public Response addItemSupplier(int itemId, int supplierId);
    public Response removeItemSupplier(int itemId, int supplierId);

    //-------------------------------------------------------------------------

    /*
    If parent category is null, the new category should be added as a main category.
     */
    public Response addCategory(String categoryName, String parentCategory);
    public ResponseT<Category> getCategory(String categoryName);
    public Response modifyCategoryName(String OldName, String newName);
    /*
    when the category is deleted all its sub category move to the parent category.
     */
    public Response removeCategory(String categoryName);

    //-------------------------------------------------------------------------

    public Response addItemSale(String saleName,int itemID, double saleDiscount, Date startDate, Date endDate);
    public Response addCategorySale(String saleName,String categoryName, double saleDiscount, Date startDate, Date endDate);
    public Response modifySaleName(String oldName, String newName);
    public Response modifySaleDiscount(String saleName, double newDiscount);
    public Response modifySaleDates(String saleName, Date startDate, Date endDate);

    //-------------------------------------------------------------------------

    public Response addItemDiscount(String Supplier, double discount, Date discountDate, int itemCount, int itemId);
    public Response addCategoryDiscount(String Supplier, double discount, Date discountDate, int itemCount, String categoryName);

    //-------------------------------------------------------------------------

    public Response recordDefect(int itemId, String itemName, int defectQuantity, String defectLocation);

    //-------------------------------------------------------------------------

    public ResponseT<List<Item>> inventoryReport();
    public ResponseT<List<Item>> itemShortageReport();
    public ResponseT<List<DefectEntry>> defectsReport(Date fromDate, Date toDate);
    public ResponseT<List<Item>> categoryReport(Date fromDate, Date toDate);



}


