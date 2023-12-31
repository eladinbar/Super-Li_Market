package ServiceLayer;

import BusinessLayer.InventoryPackage.DiscountPackage.CategoryDiscount;
import BusinessLayer.InventoryPackage.DiscountPackage.ItemDiscount;
import BusinessLayer.InventoryPackage.InventoryController;
import InfrastructurePackage.Pair;
import ServiceLayer.Response.*;
import ServiceLayer.Response.ResponseT;
import ServiceLayer.FacadeObjects.*;
import ServiceLayer.FacadeObjects.FacadeDefectEntry;

import java.time.LocalDate;
import java.util.*;

public class InventoryServiceImpl implements InventoryService {
    private final InventoryController inventoryController;
    private final int MIN_AMOUNT_MULTIPIER = 2;

    public InventoryServiceImpl() {
        inventoryController = new InventoryController();
    }

    //-------------------------------------------------------------------------Item functions

    @Override
    public Response addItem(int id, String name, String categoryName, double costPrice, double sellingPrice,
                            int minAmount, String shelfLocation, String storageLocation, int shelfQuantity,
                            int storageQuantity, int manufacturerId, int weight, List<String> suppliersIds) {
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
                    minAmount, shelfLocation, storageLocation, shelfQuantity, storageQuantity,
                    manufacturerId, weight, suppliersIds);
            response = new Response(false, "Item added successfully.");
            return response;
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public ResponseT<FacadeItem> getItem(int itemId) {
        ResponseT<FacadeItem> responseT;
        //Check basic argument constraints
        if (itemId < 0) {
            responseT = new ResponseT<>(true, "Item ID can only be represented as a non-negative number.", null);
            return responseT;
        }
        //Call business layer function
        try {
            BusinessLayer.InventoryPackage.Item tempItem = inventoryController.getItem(itemId);
            FacadeItem simpleItem = new FacadeItem(itemId, tempItem.getName(), tempItem.getCostPrice(), tempItem.getSellingPrice(),
                    tempItem.getMinAmount(), tempItem.getShelfQuantity(), tempItem.getStorageQuantity(), tempItem.getShelfLocation(),
                    tempItem.getStorageLocation(), tempItem.getManufacturerID(), tempItem.getWeight(), inventoryController.getItemCategory(itemId).getName());
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
    public Response modifyItemShelfQuantity(int itemId, int newShelfQuantity) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | newShelfQuantity < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        inventoryController.modifyItemShelfQuantity(itemId, newShelfQuantity);
        response = new Response(false, "Item shelf quantity modified successfully.");
        return response;

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
            inventoryController.modifyItemStorageQuantity(itemId, newStorageQuantity);
            response = new Response(false, "Item storage quantity modified successfully.");
            return response;
    }

    @Override
    public Response addItemSupplier(int itemId, String supplierId) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | supplierId.isEmpty()) {
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
    public Response removeItemSupplier(int itemId, String supplierId) {
        Response response;
        //Check basic argument constraints
        if (itemId < 0 | supplierId.isEmpty()) {
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
    public ResponseT<FacadeCategory> getCategory(String categoryName) {
        ResponseT<FacadeCategory> responseT;
        //Call business layer function
        try {
            BusinessLayer.InventoryPackage.Category tempCategory = inventoryController.getCategory(categoryName);
            //Simplify business layer category fields
            List<FacadeItem> simpleItems = getSimpleItems(tempCategory.getItems());
            List<String> simpleSubCategories = getSimpleCategories(tempCategory.getSubCategories());
            //Ensure category indeed has a parent category
            BusinessLayer.InventoryPackage.Category tempParentCategory = tempCategory.getParentCategory();
            //Create simple category
            FacadeCategory simpleCategory = new FacadeCategory(categoryName.equals("") ? "Uncategorized" : categoryName, simpleItems,
                    tempParentCategory!=null ? tempCategory.getParentCategory().getName() : "",
                    simpleSubCategories);
            responseT = new ResponseT<>(false, "", simpleCategory);
            return responseT;
        } catch (Exception ex) {
            responseT = new ResponseT<>(true, ex.getMessage(), null);
            return responseT;
        }
    }

    private List<String> getSimpleCategories(List<BusinessLayer.InventoryPackage.Category> categories) {
        List<String> simpleCategories = new ArrayList<>();
        for (BusinessLayer.InventoryPackage.Category category : categories) {
            simpleCategories.add(category.getName());
        }
        return simpleCategories;
    }

    private List<FacadeItem> getSimpleItems(List<BusinessLayer.InventoryPackage.Item> items) {
        List<FacadeItem> simpleItems = new ArrayList<>();
        for (BusinessLayer.InventoryPackage.Item item : items) {
            FacadeItem simpleItem = new FacadeItem(item.getID(), item.getName(), item.getCostPrice(), item.getSellingPrice(),
                    item.getMinAmount(), item.getShelfQuantity(), item.getStorageQuantity(),
                    item.getShelfLocation(), item.getStorageLocation(), item.getManufacturerID(), item.getWeight(), inventoryController.getItemCategory(item.getID()).getName());
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
    @SuppressWarnings("unchecked")
    public <T extends FacadeEntity> ResponseT<FacadeSale<T>> getSale(String saleName) {
        //response to return created
        ResponseT<FacadeSale<T>> responseT;
        if (saleName.trim().isEmpty()) {
            responseT = new ResponseT<>(true, "One or more arguments is invalid", null);
            return responseT;
        }
        BusinessLayer.InventoryPackage.SalePackage.Sale sale;
        try {
            sale = inventoryController.getSale(saleName);

        } catch (Exception e) {
            responseT = new ResponseT<>(true, e.getMessage(), null);
            return responseT;
        }

        //simple sale created
        FacadeSale<T> simple = new FacadeSale<>(sale.getName(), sale.getDiscount(), sale.getSaleDates(), null);
        //Item Sale case
        if (sale.getClass() == BusinessLayer.InventoryPackage.SalePackage.ItemSale.class) {
            BusinessLayer.InventoryPackage.Item i = ((BusinessLayer.InventoryPackage.SalePackage.ItemSale) sale).getItem();
            FacadeItem simpleItem = new FacadeItem(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                    i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID(),
                    i.getWeight(), inventoryController.getItemCategory(i.getID()).getName());
            simple.setAppliesOn((T) simpleItem);
        } else { //Category Sale case
            BusinessLayer.InventoryPackage.Category c = ((BusinessLayer.InventoryPackage.SalePackage.CategorySale) sale).getCategory();
            List<FacadeItem> categoryItems = new ArrayList<>();
            //converting Items of category
            for (BusinessLayer.InventoryPackage.Item i : c.getItems()) {
                FacadeItem simpleCatItem = new FacadeItem(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                        i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID(),
                        i.getWeight(), inventoryController.getItemCategory(i.getID()).getName());
                categoryItems.add(simpleCatItem);
            }
            //getting the sub categories names
            List<String> subCategories = new ArrayList<>();
            for (BusinessLayer.InventoryPackage.Category subC : c.getSubCategories()) {
                subCategories.add(subC.getName());
            }
            //creating simple category
            FacadeCategory simpleCategory = new FacadeCategory(c.getName(), categoryItems, c.getParentCategory().getName(), subCategories);
            simple.setAppliesOn((T) simpleCategory);
        }

        responseT = new ResponseT<>(false, "", simple);
        return responseT;
    }

    @Override
    public Response addItemSale(String saleName, int itemID, double saleDiscount, LocalDate startDate, LocalDate endDate) {
        Response response;
        //Check basic argument constraints
        if (saleName == null || saleName.trim().equals("") | itemID < 0 | saleDiscount < 0) {
            response = new Response(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.addItemSale(saleName, itemID, saleDiscount, startDate, endDate);
        } catch (Exception ex) {
            response = new Response(true, ex.getMessage());
            return response;
        }
        response = new Response(false, "Item Sale added successfully.");
        return response;
    }

    @Override
    public Response addCategorySale(String saleName, String categoryName, double saleDiscount, LocalDate startDate, LocalDate endDate) {
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
        try {
            inventoryController.modifySaleName(oldName, newName);

        } catch (Exception e) {
            response = new Response(true, e.getMessage());
            return response;
        }
        response = new Response(false, "");
        return response;
    }

    @Override
    public Response modifySaleDiscount(String saleName, double newDiscount) {
        Response response;
        try {
            inventoryController.modifySaleDiscount(saleName, newDiscount);

        } catch (Exception e) {
            response = new Response(true, e.getMessage());
            return response;
        }
        response = new Response(false, "");
        return response;
    }

    @Override
    public Response modifySaleDates(String saleName, LocalDate startDate, LocalDate endDate) {
        Response response;
        try {
            inventoryController.modifySaleDates(saleName, startDate, endDate);

        } catch (Exception e) {
            response = new Response(true, e.getMessage());
            return response;
        }
        response = new Response(false, "");
        return response;
    }

    //-------------------------------------------------------------------------Discount functions

    @SuppressWarnings("unchecked")
    public <T extends FacadeEntity> ResponseT<List<FacadeDiscount<T>>> getDiscount(String supplierId, LocalDate discountDate) {
        //response to return created
        ResponseT<List<FacadeDiscount<T>>> responseT;
        List<FacadeDiscount<T>> simpleDiscs = new ArrayList<>();
        if (supplierId.isEmpty()) {
            responseT = new ResponseT<>(true, "One oe more Arguments is invalid", null);
            return responseT;
        }
        List<BusinessLayer.InventoryPackage.DiscountPackage.Discount> discountList;
        try {
            discountList = inventoryController.getDiscount(supplierId, discountDate);

        } catch (Exception e) {
            responseT = new ResponseT<>(true, e.getMessage(), null);
            return responseT;
        }

        for (BusinessLayer.InventoryPackage.DiscountPackage.Discount disc : discountList) {
            //simple discount created
            FacadeDiscount<T> simple = new FacadeDiscount<>(disc.getSupplierID(), disc.getDiscount(), disc.getDate(), disc.getItemCount());
            //Item Discount case
            if (disc.getClass() == ItemDiscount.class) {
                BusinessLayer.InventoryPackage.Item i = ((ItemDiscount) disc).getItem();
                FacadeItem simpleItem = new FacadeItem(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                        i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID(),
                        i.getWeight(), inventoryController.getItemCategory(i.getID()).getName());
                simple.setAppliesOn((T) simpleItem);
            } else { //Category Discount case
                BusinessLayer.InventoryPackage.Category c = ((CategoryDiscount) disc).getCategory();
                List<FacadeItem> categoryItems = new ArrayList<>();
                //converting Items of category
                for (BusinessLayer.InventoryPackage.Item i : c.getItems()) {
                    FacadeItem simpleCatItem = new FacadeItem(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                            i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID(),
                            i.getWeight(), inventoryController.getItemCategory(i.getID()).getName());
                    categoryItems.add(simpleCatItem);
                }
                //getting tre sub categories names
                List<String> subCategories = new ArrayList<>();
                for (BusinessLayer.InventoryPackage.Category subC : c.getSubCategories()) {
                    subCategories.add(subC.getName());
                }
                //creating simple category
                FacadeCategory simpleCategory = new FacadeCategory(c.getName(), categoryItems, c.getParentCategory().getName(), subCategories);
                simple.setAppliesOn((T) simpleCategory);
            }
            simpleDiscs.add(simple);
        }
        responseT = new ResponseT<>(false, "", simpleDiscs);
        return responseT;
    }

    @Override
    public Response addItemDiscount(String supplierId, double discount, LocalDate discountDate, int itemCount, int itemId) {
        Response response;
        if (supplierId.isEmpty() || (discount <= 0 || discount >= 1) || itemCount <= 0 || itemId <= 0) {
            response = new Response(true, "One or more arguments is invalid");
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
    public Response addCategoryDiscount(String supplierId, double discount, LocalDate discountDate, int itemCount, String categoryName) {
        Response response;
        if (supplierId.isEmpty() || (discount <= 0 || discount >= 1) || itemCount <= 0 || categoryName.isEmpty() || categoryName.isBlank()) {
            response = new Response(true, "One or more arguments is invalid");
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
    public Response recordDefect(int itemId, LocalDate entryDate, int defectQuantity, String defectLocation) {
        Response response;
        if (itemId <= 0 || defectQuantity <= 0 || defectLocation.isEmpty() || defectLocation.isBlank()) {
            response = new Response(true, "One or more arguments is invalid");
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
    public ResponseT<List<FacadeItem>> inventoryReport() {
        ResponseT<List<FacadeItem>> shortageResponse;
        List<FacadeItem> simpleItemList = new ArrayList<>();
        try {
            List<BusinessLayer.InventoryPackage.Item> shortageItems = inventoryController.inventoryReport();
            shortageResponse = getResponseListItem(simpleItemList, shortageItems);

        } catch (Exception e) {
            shortageResponse = new ResponseT<>(true, e.getMessage(), null);
        }
        return shortageResponse;
    }

    @Override
    public ResponseT<List<FacadeItem>> categoryReport(String categoryName) {
        ResponseT<List<FacadeItem>> shortageResponse;
        List<FacadeItem> simpleItemList = new ArrayList<>();
        try {
            List<BusinessLayer.InventoryPackage.Item> shortageItems = inventoryController.categoryReport(categoryName);
            shortageResponse = getResponseListItem(simpleItemList, shortageItems);

        } catch (Exception e) {
            shortageResponse = new ResponseT<>(true, e.getMessage(), null);
        }
        return shortageResponse;
    }

    private ResponseT<List<FacadeItem>> getResponseListItem(List<FacadeItem> simpleItemList, List<BusinessLayer.InventoryPackage.Item> shortageItems) {
        ResponseT<List<FacadeItem>> shortageResponse;
        for (BusinessLayer.InventoryPackage.Item i : shortageItems) {
            FacadeItem simpleItem = new FacadeItem(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                    i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID(),
                    i.getWeight(), inventoryController.getItemCategory(i.getID()).getName());
            simpleItemList.add(simpleItem);
        }
        shortageResponse = new ResponseT<>(false, "", simpleItemList);
        return shortageResponse;
    }

    @Override
    public ResponseT<List<FacadeItem>> itemShortageReport() {
        ResponseT<List<FacadeItem>> shortageResponse;
        List<FacadeItem> simpleItemList = new ArrayList<>();
        try {
            List<BusinessLayer.InventoryPackage.Item> shortageItems = inventoryController.itemShortageReport();
            shortageResponse = getResponseListItem(simpleItemList, shortageItems);

        } catch (Exception e) {
            shortageResponse = new ResponseT<>(true, e.getMessage(), new ArrayList<>());
        }
        return shortageResponse;
    }

    @Override
    public ResponseT<List<FacadeDefectEntry>> defectsReport(LocalDate fromDate, LocalDate toDate) {
        ResponseT<List<FacadeDefectEntry>> defectResponse;
        List<FacadeDefectEntry> simpleEntries = new ArrayList<>();
        try {
            List<BusinessLayer.InventoryPackage.DefectsPackage.DefectEntry> defectsReport = inventoryController.defectsReport(fromDate, toDate);
            for (BusinessLayer.InventoryPackage.DefectsPackage.DefectEntry entry : defectsReport) {
                FacadeDefectEntry simple = new FacadeDefectEntry(entry.getEntryDate(), entry.getItemID(), entry.getItemName(), entry.getQuantity(), entry.getLocation());
                simpleEntries.add(simple);
            }
            defectResponse = new ResponseT<>(false, "", simpleEntries);
        } catch (Exception e) {
            defectResponse = new ResponseT<>(true, e.getMessage(), null);
        }
        return defectResponse;
    }

    @Override
    public ResponseT<Pair<Map<Integer, Integer>, Map<Integer, String>>> getItemsInShortAndQuantities() {
        ResponseT<List<FacadeItem>> itemsInShort = itemShortageReport();
        if (itemsInShort.errorOccurred()) {
            return new ResponseT<>(false, itemsInShort.getMessage(), null);
        }
        Map<Integer, Integer> itemQuantityMap = new HashMap<>();
        Map<Integer, String> itemNameMap = new HashMap<>();
        for (FacadeItem i : itemsInShort.value) {
            itemQuantityMap.put(i.getID(), i.getMinAmount() * MIN_AMOUNT_MULTIPIER - i.getShelfQuantity() - i.getStorageQuantity());
            itemNameMap.put(i.getID(),i.getName());
        }
        return new ResponseT<>(new Pair(itemQuantityMap,itemNameMap));
    }

    @Override
    public Response updateQuantityInventory(Map<Integer, Integer> items) {
        for (Integer i: items.keySet()) {
            inventoryController.modifyItemShelfQuantity(i,
                    items.get(i) + inventoryController.getItem(i).getStorageQuantity());
        }
        return new Response(false, "Inventory updated");
    }
}
