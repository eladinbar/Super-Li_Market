package InventoryModule.ControllerLayer;

import InventoryModule.ControllerLayer.SimpleObjects.Category;
import InventoryModule.ControllerLayer.SimpleObjects.DefectEntry;
import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.util.Date;
import java.util.List;

public class InventoryServiceImpl implements InventoryService{

    @Override
    public Response addItem(int id, String name, String category, double costPrice, double sellingPrice,
                            int minAmount, String shelfLocation, String storageLocation, int storageQuantity,
                            int shelfQuantity, int manufacturerId, List<Integer> suppliersId) {
        return null;
    }

    @Override
    public ResponseT<Item> getItem(int ItemId) {
        return null;
    }

    @Override
    public Response modifyItemName(int itemId, String newName) {
        return null;
    }

    @Override
    public Response modifyItemCategory(int itemId, String newCategoryName) {
        return null;
    }

    @Override
    public Response modifyItemCostPrice(int itemId, double newCostPrice) {
        return null;
    }

    @Override
    public Response modifyItemSellPrice(int itemId, double newSellName) {
        return null;
    }

    @Override
    public Response changeItemLocation(int itemId, String newStorageLocation, String newStoreLocation) {
        return null;
    }

    @Override
    public Response modifyItemQuantity(int itemId, int newStorageQuantity, int newStoreQuantity) {
        return null;
    }

    @Override
    public Response addItemSupplier(int itemId, int supplierId) {
        return null;
    }

    @Override
    public Response removeItemSupplier(int itemId, int supplierId) {
        return null;
    }

    @Override
    public Response addCategory(String categoryName, String parentCategory) {
        return null;
    }

    @Override
    public ResponseT<Category> getCategory(String categoryName) {
        return null;
    }

    @Override
    public Response modifyCategoryName(String OldName, String newName) {
        return null;
    }

    @Override
    public Response removeCategory(String categoryName) {
        return null;
    }

    @Override
    public Response addItemSale(String saleName, int itemID, double saleDiscount, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public Response addCategorySale(String saleName, String categoryName, double saleDiscount, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public Response modifySaleName(String oldName, String newName) {
        return null;
    }

    @Override
    public Response modifySaleDiscount(String saleName, double newDiscount) {
        return null;
    }

    @Override
    public Response modifySaleDates(String saleName, Date startDate, Date endDate) {
        return null;
    }

    @Override
    public Response addItemDiscount(String Supplier, double discount, Date discountDate, int itemCount, int itemId) {
        return null;
    }

    @Override
    public Response addCategoryDiscount(String Supplier, double discount, Date discountDate, int itemCount, String categoryName) {
        return null;
    }

    @Override
    public Response recordDefect(int itemId, String itemName, int defectQuantity, String defectLocation) {
        return null;
    }

    @Override
    public ResponseT<List<Item>> inventoryReport() {
        return null;
    }

    @Override
    public ResponseT<List<Item>> itemShortageReport() {
        return null;
    }

    @Override
    public ResponseT<List<DefectEntry>> defectsReport(Date fromDate, Date toDate) {
        return null;
    }

    @Override
    public ResponseT<List<Item>> categoryReport(Date fromDate, Date toDate) {
        return null;
    }
}
