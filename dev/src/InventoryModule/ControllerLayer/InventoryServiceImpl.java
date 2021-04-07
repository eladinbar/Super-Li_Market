package InventoryModule.ControllerLayer;

import InventoryModule.BusinessLayer.DiscountPackage.CategoryDiscount;
import InventoryModule.BusinessLayer.DiscountPackage.ItemDiscount;
import InventoryModule.ControllerLayer.SimpleObjects.*;
import InventoryModule.ControllerLayer.SimpleObjects.DefectEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

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

    //-------------------------------------------------------------------------Discount functions

    @Override
    @SuppressWarnings("unchecked")
    public <T extends SimpleEntity> ResponseT<List<Discount<T>>> getDiscount(int supplierId, Calendar discountDate) {
        //response to return created
        ResponseT<List<Discount<T>>> responseT;
        List<Discount<T>> simpleDiscs = new ArrayList<>();
        if (supplierId <= 0) {
            responseT = new ResponseT<>(true, "One oe more Arguments is invalid", null);
            return responseT;
        }
        List<InventoryModule.BusinessLayer.DiscountPackage.Discount> discountList;
        try {
            discountList = inventoryController.getDiscount(supplierId, discountDate);

        } catch (Exception e) {
            responseT = new ResponseT<>(true, e.getMessage(), null);
            return responseT;
        }

        for (InventoryModule.BusinessLayer.DiscountPackage.Discount disc : discountList) {
            //simple discount created
            Discount<T> simple = new Discount<>(disc.getSupplierID(), disc.getDiscount(), disc.getDate(), disc.getItemCount());
            //Item Discount case
            if (disc.getClass() == ItemDiscount.class) {
                InventoryModule.BusinessLayer.Item i = ((ItemDiscount) disc).getItem();
                Item simpleItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                        i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID());
                simple.setAppliesOn((T) simpleItem);
            } else { //Category Discount case
                InventoryModule.BusinessLayer.Category c = ((CategoryDiscount) disc).getCategory();
                List<Item> categoryItems = new ArrayList<>();
                //converting Items of category
                for (InventoryModule.BusinessLayer.Item i : c.getItems()) {
                    Item simpleCatItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                            i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID());
                    categoryItems.add(simpleCatItem);
                }
                //getting tre sub categories names
                List<String> subCategories = new ArrayList<>();
                for (InventoryModule.BusinessLayer.Category subC : c.getSubCategories()) {
                    subCategories.add(subC.getName());
                }
                //creating simple category
                Category simpleCategory = new Category(c.getName(), categoryItems, c.getParentCategory().getName(), subCategories);
                simple.setAppliesOn((T) simpleCategory);
            }
            simpleDiscs.add(simple);
        }
        responseT = new ResponseT<>(false,"",simpleDiscs);
        return responseT;

    }


    @Override
    public Response addItemDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, int itemId) {
        Response response;
        if (supplierId <= 0 || (discount <= 0 || discount >= 1) || itemCount <= 0 || itemId <= 0) {
            response = new Response(true, "One oe more Arguments is invalid");
            return response;
        }
        try {
            inventoryController.addItemDiscount(supplierId, discount, discountDate, itemCount, itemId);
        } catch (Exception e) {
            response = new Response(true, e.getMessage());
            return response;
        }
        response = new Response(false, "");
        return response;
    }

    @Override
    public Response addCategoryDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, String categoryName) {
        Response response;
        if (supplierId <= 0 || (discount <= 0 || discount >= 1) || itemCount <= 0 || categoryName.isEmpty() || categoryName.isBlank()) {
            response = new Response(true, "One oe more Arguments is invalid");
            return response;
        }
        try {
            inventoryController.addCategoryDiscount(supplierId, discount, discountDate, itemCount, categoryName);
        } catch (Exception e) {
            response = new Response(true, e.getMessage());
            return response;
        }
        response = new Response(false, "");
        return response;
    }

    //-------------------------------------------------------------------------Defect Functions

    @Override
    public Response recordDefect(int itemId, Calendar entryDate, int defectQuantity, String defectLocation) {
        Response response;
        if (itemId <= 0 || defectQuantity <= 0 || defectLocation.isEmpty() || defectLocation.isBlank()) {
            response = new Response(true, "One oe more Arguments is invalid");
            return response;
        }
        try {
            inventoryController.recordDefect(itemId, entryDate, defectQuantity, defectLocation);
        } catch (Exception e) {
            response = new Response(true, e.getMessage());
            return response;
        }
        response = new Response(false, "");
        return response;
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
