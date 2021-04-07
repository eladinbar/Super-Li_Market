package InventoryModule.ControllerLayer;

import InventoryModule.BusinessLayer.DiscountPackage.CategoryDiscount;
import InventoryModule.BusinessLayer.DiscountPackage.ItemDiscount;
import InventoryModule.ControllerLayer.SimpleObjects.*;
import InventoryModule.ControllerLayer.SimpleObjects.DefectEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InventoryServiceImpl implements InventoryService {
    private final InventoryController inventoryController;

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
            response = new Response(false, "Item added successfully.");
            return response;
        } catch (Exception ex) {
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
        } catch (Exception ex) {
            responseT = new ResponseT<>(true, ex.getMessage(), null);
            return responseT;
        }
    }

    @Override
    public Response modifyItemName(int itemId, String newName) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newName == null || newName.trim().isEmpty()) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemName(itemId, newName);
            response = new Response(false, "Item name modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response modifyItemCategory(int itemId, String newCategoryName) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0) {
            response = new Response(true, "Item ID can only be represented as a non-negative number.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemCategory(itemId, newCategoryName);
            response = new Response(false, "Item category modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response modifyItemCostPrice(int itemId, double newCostPrice) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newCostPrice < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemCostPrice(itemId, newCostPrice);
            response = new Response(false, "Item cost price modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response modifyItemSellingPrice(int itemId, double newSellingPrice) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newSellingPrice < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemSellingPrice(itemId, newSellingPrice);
            response = new Response(false, "Item selling price modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response changeItemLocation(int itemId, String newShelfLocation, String newStorageLocation) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newShelfLocation == null || newShelfLocation.trim().equals("") |
                newStorageLocation == null || newStorageLocation.trim().equals("")) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.changeItemLocation(itemId, newShelfLocation, newStorageLocation);
            response = new Response(false, "Item location changed successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response changeItemShelfLocation(int itemId, String newShelfLocation) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newShelfLocation == null || newShelfLocation.trim().equals("")) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.changeItemShelfLocation(itemId, newShelfLocation);
            response = new Response(false, "Item shelf location changed successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response changeItemStorageLocation(int itemId, String newStorageLocation) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newStorageLocation == null || newStorageLocation.trim().equals("")) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.changeItemStorageLocation(itemId, newStorageLocation);
            response = new Response(false, "Item storage location changed successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response modifyItemQuantity(int itemId, int newShelfQuantity, int newStorageQuantity) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newShelfQuantity <0  | newStorageQuantity < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemQuantity(itemId, newShelfQuantity, newStorageQuantity);
            response = new Response(false, "Item quantity modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response modifyItemShelfQuantity(int itemId, int newShelfQuantity) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newShelfQuantity <0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemShelfQuantity(itemId, newShelfQuantity);
            response = new Response(false, "Item shelf quantity modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response modifyItemStorageQuantity(int itemId, int newStorageQuantity) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newStorageQuantity < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemStorageQuantity(itemId, newStorageQuantity);
            response = new Response(false, "Item storage quantity modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response addItemSupplier(int itemId, int supplierId) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | supplierId < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.addItemSupplier(itemId, supplierId);
            response = new Response(false, "Item supplier added successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response removeItemSupplier(int itemId, int supplierId) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | supplierId < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.removeItemSupplier(itemId, supplierId);
            response = new Response(false, "Item supplier removed successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response removeItem(int itemId) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0) {
            response = new Response(true, "Item ID can only be represented as a non-negative number.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.removeItem(itemId);
            response = new Response(false, "Item removed successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    //-------------------------------------------------------------------------Category functions

    @Override
    public Response addCategory(String categoryName, String parentCategoryName) {
        Response response;
        //Call business layer function
        try {
            inventoryController.addCategory(categoryName, parentCategoryName);
            response = new Response(false, "Category added successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public ResponseT<Category> getCategory(String categoryName) {
        ResponseT<Category> responseT;
        //Call business layer function
        try {
            InventoryModule.BusinessLayer.Category tempCategory = inventoryController.getCategory(categoryName);
            //Simplify business layer category fields
            List<Item> simpleItems = getSimpleItems(tempCategory.getItems());
            List<String> simpleSubCategories = getSimpleCategories(tempCategory.getSubCategories());
            //Create simple category
            Category simpleCategory = new Category(categoryName, simpleItems, tempCategory.getParentCategory().getName(),
                                                    simpleSubCategories);
            responseT = new ResponseT<>(false, "", simpleCategory);
            return responseT;
        } catch (Exception ex) {
            responseT = new ResponseT<>(true, ex.getMessage(), null);
            return responseT;
        }
    }

    private List<String> getSimpleCategories(List<InventoryModule.BusinessLayer.Category> categories) {
        List<String> simpleCategories = new ArrayList<>();
        for (InventoryModule.BusinessLayer.Category category : categories) {
            simpleCategories.add(category.getName());
        }
        return simpleCategories;
    }

    private List<Item> getSimpleItems(List<InventoryModule.BusinessLayer.Item> items) {
        List<Item> simpleItems = new ArrayList<>();
        for (InventoryModule.BusinessLayer.Item item : items) {
            Item simpleItem = new Item(item.getID(), item.getName(), item.getCostPrice(), item.getSellingPrice(),
                                        item.getMinAmount(), item.getShelfQuantity(), item.getStorageQuantity(),
                                        item.getShelfLocation(), item.getStorageLocation(), item.getManufacturerID());
            simpleItems.add(simpleItem);
        }
        return simpleItems;
    }

    @Override
    public Response modifyCategoryName(String oldName, String newName) {
        Response response;
        //Call business layer function
        try {
            inventoryController.modifyCategoryName(oldName, newName);
            response = new Response(false, "Category name modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response removeCategory(String categoryName) {
        Response response;
        //Call business layer function
        try {
            inventoryController.removeCategory(categoryName);
            response = new Response(false, "Category removed successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response changeParentCategory(String categoryName, String newParentName) {
        Response response;
        //Call business layer function
        try {
            inventoryController.changeParentCategory(categoryName, newParentName);
            response = new Response(false, "Parent category changed successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    //-------------------------------------------------------------------------Sale functions

    @Override
    public <T extends SimpleEntity> ResponseT<Sale<T>> showSale(String saleName) {
        return null;
    }

    @Override
    public Response addItemSale(String saleName, int itemID, double saleDiscount, Calendar startDate, Calendar endDate) {
        Response response;
        //Check basic argument constraints
        if (saleName == null || saleName.trim().equals("") | itemID < 0 | saleDiscount < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.addItemSale(saleName, itemID, saleDiscount, startDate, endDate);
            response = new Response(false, "Item Sale added successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response addCategorySale(String saleName, String categoryName, double saleDiscount, Calendar startDate, Calendar endDate) {
        Response response;
        //Check basic argument constraints
        if (saleName == null || saleName.trim().equals("") | saleDiscount < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.addCategorySale(saleName, categoryName, saleDiscount, startDate, endDate);
            response = new Response(false, "Category Sale added successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public Response modifySaleName(String oldName, String newName) {
        Response response;
        try{
            inventoryController.modifySaleName(oldName, newName);

        } catch (Exception e){
            response = new Response(true, e.getMessage());
            return  response;
        }
        response = new Response(false,"");
        return response;
    }

    @Override
    public Response modifySaleDiscount(String saleName, double newDiscount) {
        Response response;
        try{
            inventoryController.modifySaleDiscount(saleName, newDiscount);

        } catch (Exception e){
            response = new Response(true, e.getMessage());
            return  response;
        }
        response = new Response(false,"");
        return response;
    }

    @Override
    public Response modifySaleDates(String saleName, Calendar startDate, Calendar endDate) {
        Response response;
        try{
            inventoryController.modifySaleDates(saleName, startDate, endDate);

        } catch (Exception e){
            response = new Response(true, e.getMessage());
            return  response;
        }
        response = new Response(false,"");
        return response;
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
