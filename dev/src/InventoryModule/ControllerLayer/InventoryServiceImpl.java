package InventoryModule.ControllerLayer;

import InventoryModule.ControllerLayer.SimpleObjects.*;
import InventoryModule.ControllerLayer.SimpleObjects.DefectEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class InventoryServiceImpl implements InventoryService {
    private InventoryController inventoryController;

    public InventoryServiceImpl() {
        inventoryController = new InventoryController();
    }

    //-------------------------------------------------------------------------Item functions

    @Override
    public Response addItem(int id, String name, String categoryName, double costPrice, double sellingPrice,
                            int minAmount, String shelfLocation, String storageLocation, int shelfQuantity,
                            int storageQuantity, int manufacturerId, List<Integer> suppliersIds) {
        Response response;
        //Check basic argument constraints
        if (id < 0 | name == null || name.trim().isEmpty() | costPrice < 0 | sellingPrice < 0 | minAmount < 0 |
                shelfLocation == null || shelfLocation.trim().isEmpty() | storageLocation == null || storageLocation.trim().isEmpty() |
                shelfQuantity < 0 | storageQuantity < 0 | manufacturerId < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.addItem(id, name, categoryName, costPrice, sellingPrice,
                    minAmount, shelfLocation, storageLocation,
                    shelfQuantity, storageQuantity, manufacturerId, suppliersIds);
            response = new Response(false, "Item added successfully");
            return response;
        } catch (IllegalArgumentException ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public ResponseT<Item> getItem(int itemId) {
        ResponseT<Item> responseT;
        //Check basic argument constraints
        if (itemId < 0) {
            responseT = new ResponseT<>(true, "Item ID can only be represented as a non-negative number.", null);
            return responseT;
        }
        //Call business layer function
        try {
            InventoryModule.BusinessLayer.Item tempItem = inventoryController.getItem(itemId);
            Item simpleItem = new Item(itemId, tempItem.getName(), tempItem.getCostPrice(), tempItem.getSellingPrice(),
                    tempItem.getMinAmount(), tempItem.getShelfQuantity(), tempItem.getStorageQuantity(), tempItem.getShelfLocation(),
                    tempItem.getStorageLocation(), tempItem.getManufacturerID());
            responseT = new ResponseT<>(false, "", simpleItem);
            return responseT;
        } catch (IllegalArgumentException ex) {
            responseT = new ResponseT(true, ex.getMessage(), null);
            return responseT;
        }

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
    public Response recordDefect(int itemId, Calendar entryDate, int defectQuantity, String defectLocation) {
        Response recorded;
        if (itemId <= 0 || defectQuantity <= 0 || defectLocation.isEmpty() || defectLocation.isBlank()) {
            recorded = new Response(true, "One oe more Arguments is invalid");
            return recorded;
        }
        try {
            inventoryController.recordDefect(itemId, entryDate, defectQuantity, defectLocation);
        } catch (Exception e){
            recorded = new Response(true,e.getMessage());
            return recorded;
        }
        recorded = new Response(false,"");
        return recorded;
    }

    //-------------------------------------------------------------------------Report functions

    @Override
    public ResponseT<List<Item>> inventoryReport() {
        ResponseT<List<Item>> shortageResponse;
        List<Item> simpleItemList = new ArrayList<>();
        try {
            List<InventoryModule.BusinessLayer.Item> shortageItems = inventoryController.inventoryReport();
            for (InventoryModule.BusinessLayer.Item i : shortageItems) {
                Item simpleItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                        i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID());
                simpleItemList.add(simpleItem);
            }
            shortageResponse = new ResponseT<>(false, "", simpleItemList);

        } catch (Exception e) {
            shortageResponse = new ResponseT<>(true, e.getMessage(), null);
        }
        return shortageResponse;
    }

    @Override
    public ResponseT<List<Item>> categoryReport(String categoryName) {
        ResponseT<List<Item>> shortageResponse;
        List<Item> simpleItemList = new ArrayList<>();
        try {
            List<InventoryModule.BusinessLayer.Item> shortageItems = inventoryController.categoryReport(categoryName);
            for (InventoryModule.BusinessLayer.Item i : shortageItems) {
                Item simpleItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                        i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID());
                simpleItemList.add(simpleItem);
            }
            shortageResponse = new ResponseT<>(false, "", simpleItemList);

        } catch (Exception e) {
            shortageResponse = new ResponseT<>(true, e.getMessage(), null);
        }
        return shortageResponse;
    }

    @Override
    public ResponseT<List<Item>> itemShortageReport() {
        ResponseT<List<Item>> shortageResponse;
        List<Item> simpleItemList = new ArrayList<>();
        try {
            List<InventoryModule.BusinessLayer.Item> shortageItems = inventoryController.itemShortageReport();
            for (InventoryModule.BusinessLayer.Item i : shortageItems) {
                Item simpleItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                        i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID());
                simpleItemList.add(simpleItem);
            }
            shortageResponse = new ResponseT<>(false, "", simpleItemList);

        } catch (Exception e) {
            shortageResponse = new ResponseT<>(true, e.getMessage(), null);
        }
        return shortageResponse;
    }

    @Override
    public ResponseT<List<DefectEntry>> defectsReport(Calendar fromDate, Calendar toDate) {
        ResponseT<List<DefectEntry>> defectResponse;
        List<DefectEntry> simpleEntries = new ArrayList<>();
        try {
            List<InventoryModule.BusinessLayer.DefectsPackage.DefectEntry> defectsReport = inventoryController.defectsReport(fromDate, toDate);
            for (InventoryModule.BusinessLayer.DefectsPackage.DefectEntry entry : defectsReport) {
                DefectEntry simple = new DefectEntry(entry.getEntryDate(), entry.getItemID(), entry.getItemName(), entry.getQuantity(), entry.getLocation());
                simpleEntries.add(simple);
            }
            defectResponse = new ResponseT<>(false, "", simpleEntries);
        } catch (Exception e) {
            defectResponse = new ResponseT<>(true, e.getMessage(), null);
        }
        return defectResponse;
    }
}
