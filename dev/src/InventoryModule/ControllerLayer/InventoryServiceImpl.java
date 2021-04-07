package InventoryModule.ControllerLayer;

import InventoryModule.ControllerLayer.SimpleObjects.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InventoryServiceImpl implements InventoryService{

    //-------------------------------------------------------------------------Item functions

    @Override
    public Response addItem(int id, String name, String categoryName, double costPrice, double sellingPrice,
                            int minAmount, String shelfLocation, String storageLocation, int storageQuantity,
                            int shelfQuantity, int manufacturerId, List<Integer> suppliersIds) {
        return null;
    }

    @Override
    public ResponseT<Item> getItem(int itemId) {
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
    public Response modifyItemSellingPrice(int itemId, double newSellingPrice) {
        return null;
    }

    @Override
    public Response changeItemLocation(int itemId, String newShelfLocation, String newStorageLocation) {
        return null;
    }

    @Override
    public Response changeItemShelfLocation(int itemId, String newShelfLocation) {
        return null;
    }

    @Override
    public Response changeItemStorageLocation(int itemId, String newStorageLocation) {
        return null;
    }

    @Override
    public Response modifyItemQuantity(int itemId, int newShelfQuantity, int newStorageQuantity) {
        return null;
    }

    @Override
    public Response modifyItemShelfQuantity(int itemId, int newShelfQuantity) {
        return null;
    }

    @Override
    public Response modifyItemStorageQuantity(int itemId, int newStorageQuantity) {
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
    public Response removeItem(int itemId) {
        return null;
    }

    //-------------------------------------------------------------------------Category functions

    @Override
    public Response addCategory(String categoryName, String parentCategoryName) {
        return null;
    }

    @Override
    public ResponseT<Category> getCategory(String categoryName) {
        return null;
    }

    @Override
    public Response modifyCategoryName(String oldName, String newName) {
        return null;
    }

    @Override
    public Response removeCategory(String categoryName) {
        return null;
    }

    @Override
    public Response changeParentCategory(String categoryName, String newParentName) {
        return null;
    }



    @Override
    public <T extends SimpleEntity> ResponseT<Sale<T>> showSale(String saleName) {
        return null;
    }

    @Override
    public Response addItemSale(String saleName, int itemID, double saleDiscount, Calendar startDate, Calendar endDate) {
        return null;
    }

    @Override
    public Response addCategorySale(String saleName, String categoryName, double saleDiscount, Calendar startDate, Calendar endDate) {
        return null;
    }

    //-------------------------------------------------------------------------Sale functions

    @Override
    public Response modifySaleName(String oldName, String newName) {
        return null;
    }

    @Override
    public Response modifySaleDiscount(String saleName, double newDiscount) {
        return null;
    }

    @Override
    public Response modifySaleDates(String saleName, Calendar startDate, Calendar endDate) {
        return null;
    }

    @Override
    public <T extends SimpleEntity> ResponseT<List<Discount<T>>> getDiscount(int supplierId, Calendar discountDate) {
        return null;
    }


    //-------------------------------------------------------------------------Discount functions

    @Override
    public Response addItemDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, int itemId) {
        return null;
    }

    @Override
    public Response addCategoryDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, String categoryName) {
        return null;
    }

    //-------------------------------------------------------------------------Defect Functions

    @Override
    public Response recordDefect(int itemId, String itemName, Calendar entryDate, int defectQuantity, String defectLocation) {
        return null;
    }

    //-------------------------------------------------------------------------Report functions

    @Override
    public ResponseT<List<Item>> inventoryReport() {
        return null;
    }

    @Override
    public ResponseT<List<Item>> categoryReport(String categoryName) {
        return null;
    }

    @Override
    public ResponseT<List<Item>> itemShortageReport() {
        return null;
    }

    @Override
    public ResponseT<List<DefectEntry>> defectsReport(Calendar fromDate, Calendar toDate) {
        return null;
    }
}
