package SerciveLayer;

import BusinessLayer.InventoryPackage.DiscountPackage.CategoryDiscount;
import BusinessLayer.InventoryPackage.DiscountPackage.ItemDiscount;
import BusinessLayer.InventoryPackage.InventoryController;
import SerciveLayer.SimpleObjects.*;
import SerciveLayer.SimpleObjects.DefectEntry;

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
    public InResponse addItem(int id, String name, String categoryName, double costPrice, double sellingPrice,
                              int minAmount, String shelfLocation, String storageLocation, int shelfQuantity,
                              int storageQuantity, int manufacturerId, List<Integer> suppliersIds) {
        InResponse response;
        //Check basic argument constraints
        if (id < 0 | name == null || name.trim().isEmpty() | costPrice < 0 | sellingPrice < 0 | minAmount < 0 |
                shelfLocation == null || shelfLocation.trim().isEmpty() | storageLocation == null || storageLocation.trim().isEmpty() |
                shelfQuantity < 0 | storageQuantity < 0 | manufacturerId < 0) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.addItem(id, name, categoryName, costPrice, sellingPrice,
                    minAmount, shelfLocation, storageLocation,
                    shelfQuantity, storageQuantity, manufacturerId, suppliersIds);
            response = new InResponse(false, "Item added successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponseT<Item> getItem(int itemId) {
        InResponseT<Item> responseT;
        //Check basic argument constraints
        if (itemId < 0) {
            responseT = new InResponseT<>(true, "Item ID can only be represented as a non-negative number.", null);
            return responseT;
        }
        //Call business layer function
        try {
            BusinessLayer.InventoryPackage.Item tempItem = inventoryController.getItem(itemId);
            Item simpleItem = new Item(itemId, tempItem.getName(), tempItem.getCostPrice(), tempItem.getSellingPrice(),
                    tempItem.getMinAmount(), tempItem.getShelfQuantity(), tempItem.getStorageQuantity(), tempItem.getShelfLocation(),
                    tempItem.getStorageLocation(), tempItem.getManufacturerID(),inventoryController.getItemCategory(itemId).getName());
            responseT = new InResponseT<>(false, "", simpleItem);
            return responseT;
        } catch (Exception ex) {
            responseT = new InResponseT<>(true, ex.getMessage(), null);
            return responseT;
        }
    }

    @Override
    public InResponse modifyItemName(int itemId, String newName) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0 | newName == null || newName.trim().isEmpty()) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemName(itemId, newName);
            response = new InResponse(false, "Item name modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse modifyItemCategory(int itemId, String newCategoryName) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0) {
            response = new InResponse(true, "Item ID can only be represented as a non-negative number.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemCategory(itemId, newCategoryName);
            response = new InResponse(false, "Item category modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse modifyItemCostPrice(int itemId, double newCostPrice) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0 | newCostPrice < 0) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemCostPrice(itemId, newCostPrice);
            response = new InResponse(false, "Item cost price modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse modifyItemSellingPrice(int itemId, double newSellingPrice) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0 | newSellingPrice < 0) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemSellingPrice(itemId, newSellingPrice);
            response = new InResponse(false, "Item selling price modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse changeItemShelfLocation(int itemId, String newShelfLocation) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0 | newShelfLocation == null || newShelfLocation.trim().equals("")) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.changeItemShelfLocation(itemId, newShelfLocation);
            response = new InResponse(false, "Item shelf location changed successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse changeItemStorageLocation(int itemId, String newStorageLocation) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0 | newStorageLocation == null || newStorageLocation.trim().equals("")) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.changeItemStorageLocation(itemId, newStorageLocation);
            response = new InResponse(false, "Item storage location changed successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse modifyItemShelfQuantity(int itemId, int newShelfQuantity) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0 | newShelfQuantity <0) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemShelfQuantity(itemId, newShelfQuantity);
            response = new InResponse(false, "Item shelf quantity modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse modifyItemStorageQuantity(int itemId, int newStorageQuantity) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0 | newStorageQuantity < 0) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.modifyItemStorageQuantity(itemId, newStorageQuantity);
            response = new InResponse(false, "Item storage quantity modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse addItemSupplier(int itemId, int supplierId) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0 | supplierId < 0) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.addItemSupplier(itemId, supplierId);
            response = new InResponse(false, "Item supplier added successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse removeItemSupplier(int itemId, int supplierId) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0 | supplierId < 0) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.removeItemSupplier(itemId, supplierId);
            response = new InResponse(false, "Item supplier removed successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse removeItem(int itemId) {
        InResponse response;
        //Check basic argument constraints
        if (itemId < 0) {
            response = new InResponse(true, "Item ID can only be represented as a non-negative number.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.removeItem(itemId);
            response = new InResponse(false, "Item removed successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    //-------------------------------------------------------------------------Category functions

    @Override
    public InResponse addCategory(String categoryName, String parentCategoryName) {
        InResponse response;
        //Call business layer function
        try {
            inventoryController.addCategory(categoryName, parentCategoryName);
            response = new InResponse(false, "Category added successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponseT<Category> getCategory(String categoryName) {
        InResponseT<Category> responseT;
        //Call business layer function
        try {
            BusinessLayer.InventoryPackage.Category tempCategory = inventoryController.getCategory(categoryName);
            //Simplify business layer category fields
            List<Item> simpleItems = getSimpleItems(tempCategory.getItems());
            List<String> simpleSubCategories = getSimpleCategories(tempCategory.getSubCategories());
            //Create simple category
            Category simpleCategory = new Category(categoryName, simpleItems, tempCategory.getParentCategory().getName(),
                                                    simpleSubCategories);
            responseT = new InResponseT<>(false, "", simpleCategory);
            return responseT;
        } catch (Exception ex) {
            responseT = new InResponseT<>(true, ex.getMessage(), null);
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

    private List<Item> getSimpleItems(List<BusinessLayer.InventoryPackage.Item> items) {
        List<Item> simpleItems = new ArrayList<>();
        for (BusinessLayer.InventoryPackage.Item item : items) {
            Item simpleItem = new Item(item.getID(), item.getName(), item.getCostPrice(), item.getSellingPrice(),
                                        item.getMinAmount(), item.getShelfQuantity(), item.getStorageQuantity(),
                                        item.getShelfLocation(), item.getStorageLocation(), item.getManufacturerID(),inventoryController.getItemCategory(item.getID()).getName());
            simpleItems.add(simpleItem);
        }
        return simpleItems;
    }

    @Override
    public InResponse modifyCategoryName(String oldName, String newName) {
        InResponse response;
        //Call business layer function
        try {
            inventoryController.modifyCategoryName(oldName, newName);
            response = new InResponse(false, "Category name modified successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse removeCategory(String categoryName) {
        InResponse response;
        //Call business layer function
        try {
            inventoryController.removeCategory(categoryName);
            response = new InResponse(false, "Category removed successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse changeParentCategory(String categoryName, String newParentName) {
        InResponse response;
        //Call business layer function
        try {
            inventoryController.changeParentCategory(categoryName, newParentName);
            response = new InResponse(false, "Parent category changed successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    //-------------------------------------------------------------------------Sale functions

    @Override
    @SuppressWarnings("unchecked")
    public <T extends SimpleEntity> InResponseT<Sale<T>> getSale(String saleName) {
        //response to return created
        InResponseT<Sale<T>> responseT;
        if (saleName.trim().isEmpty()) {
            responseT = new InResponseT<>(true, "One or more arguments is invalid", null);
            return responseT;
        }
        BusinessLayer.InventoryPackage.SalePackage.Sale sale;
        try {
            sale = inventoryController.getSale(saleName);

        } catch (Exception e) {
            responseT = new InResponseT<>(true, e.getMessage(), null);
            return responseT;
        }

            //simple sale created
            Sale<T> simple = new Sale<>(sale.getName(), sale.getDiscount(), sale.getSaleDates(), null);
            //Item Sale case
            if (sale.getClass() == BusinessLayer.InventoryPackage.SalePackage.ItemSale.class) {
                BusinessLayer.InventoryPackage.Item i = ((BusinessLayer.InventoryPackage.SalePackage.ItemSale) sale).getItem();
                Item simpleItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                        i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID()
                        ,inventoryController.getItemCategory(i.getID()).getName());
                simple.setAppliesOn((T) simpleItem);
            } else { //Category Sale case
                BusinessLayer.InventoryPackage.Category c = ((BusinessLayer.InventoryPackage.SalePackage.CategorySale) sale).getCategory();
                List<Item> categoryItems = new ArrayList<>();
                //converting Items of category
                for (BusinessLayer.InventoryPackage.Item i : c.getItems()) {
                    Item simpleCatItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                            i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID()
                            ,inventoryController.getItemCategory(i.getID()).getName());
                    categoryItems.add(simpleCatItem);
                }
                //getting the sub categories names
                List<String> subCategories = new ArrayList<>();
                for (BusinessLayer.InventoryPackage.Category subC : c.getSubCategories()) {
                    subCategories.add(subC.getName());
                }
                //creating simple category
                Category simpleCategory = new Category(c.getName(), categoryItems, c.getParentCategory().getName(), subCategories);
                simple.setAppliesOn((T) simpleCategory);
            }

        responseT = new InResponseT<>(false, "", simple);
        return responseT;
    }

    @Override
    public InResponse addItemSale(String saleName, int itemID, double saleDiscount, Calendar startDate, Calendar endDate) {
        clearDate(startDate,endDate);
        InResponse response;
        //Check basic argument constraints
        if (saleName == null || saleName.trim().equals("") | itemID < 0 | saleDiscount < 0) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.addItemSale(saleName, itemID, saleDiscount, startDate, endDate);
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
        response = new InResponse(false, "Item Sale added successfully.");
        return response;
    }

    @Override
    public InResponse addCategorySale(String saleName, String categoryName, double saleDiscount, Calendar startDate, Calendar endDate) {
        clearDate(startDate,endDate);
        InResponse response;
        //Check basic argument constraints
        if (saleName == null || saleName.trim().equals("") | saleDiscount < 0) {
            response = new InResponse(true, "One or more of the given arguments is invalid.");
            return response;
        }
        //Call business layer function
        try {
            inventoryController.addCategorySale(saleName, categoryName, saleDiscount, startDate, endDate);
            response = new InResponse(false, "Category Sale added successfully.");
            return response;
        } catch (Exception ex) {
            response = new InResponse(true, ex.getMessage());
            return response;
        }
    }

    @Override
    public InResponse modifySaleName(String oldName, String newName) {
        InResponse response;
        try{
            inventoryController.modifySaleName(oldName, newName);

        } catch (Exception e){
            response = new InResponse(true, e.getMessage());
            return  response;
        }
        response = new InResponse(false,"");
        return response;
    }

    @Override
    public InResponse modifySaleDiscount(String saleName, double newDiscount) {
        InResponse response;
        try{
            inventoryController.modifySaleDiscount(saleName, newDiscount);

        } catch (Exception e){
            response = new InResponse(true, e.getMessage());
            return  response;
        }
        response = new InResponse(false,"");
        return response;
    }

    @Override
    public InResponse modifySaleDates(String saleName, Calendar startDate, Calendar endDate) {
        clearDate(startDate,endDate);
        InResponse response;
        try{
            inventoryController.modifySaleDates(saleName, startDate, endDate);

        } catch (Exception e){
            response = new InResponse(true, e.getMessage());
            return  response;
        }
        response = new InResponse(false,"");
        return response;
    }

    //-------------------------------------------------------------------------Discount functions

    @Override
    @SuppressWarnings("unchecked")
    public <T extends SimpleEntity> InResponseT<List<Discount<T>>> getDiscount(int supplierId, Calendar discountDate) {
        clearDate(discountDate);
        //response to return created
        InResponseT<List<Discount<T>>> responseT;
        List<Discount<T>> simpleDiscs = new ArrayList<>();
        if (supplierId <= 0) {
            responseT = new InResponseT<>(true, "One oe more Arguments is invalid", null);
            return responseT;
        }
        List<BusinessLayer.InventoryPackage.DiscountPackage.Discount> discountList;
        try {
            discountList = inventoryController.getDiscount(supplierId, discountDate);

        } catch (Exception e) {
            responseT = new InResponseT<>(true, e.getMessage(), null);
            return responseT;
        }

        for (BusinessLayer.InventoryPackage.DiscountPackage.Discount disc : discountList) {
            //simple discount created
            Discount<T> simple = new Discount<>(disc.getSupplierID(), disc.getDiscount(), disc.getDate(), disc.getItemCount());
            //Item Discount case
            if (disc.getClass() == ItemDiscount.class) {
                BusinessLayer.InventoryPackage.Item i = ((ItemDiscount) disc).getItem();
                Item simpleItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                        i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID()
                        ,inventoryController.getItemCategory(i.getID()).getName());
                simple.setAppliesOn((T) simpleItem);
            } else { //Category Discount case
                BusinessLayer.InventoryPackage.Category c = ((CategoryDiscount) disc).getCategory();
                List<Item> categoryItems = new ArrayList<>();
                //converting Items of category
                for (BusinessLayer.InventoryPackage.Item i : c.getItems()) {
                    Item simpleCatItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                            i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID()
                            ,inventoryController.getItemCategory(i.getID()).getName());
                    categoryItems.add(simpleCatItem);
                }
                //getting tre sub categories names
                List<String> subCategories = new ArrayList<>();
                for (BusinessLayer.InventoryPackage.Category subC : c.getSubCategories()) {
                    subCategories.add(subC.getName());
                }
                //creating simple category
                Category simpleCategory = new Category(c.getName(), categoryItems, c.getParentCategory().getName(), subCategories);
                simple.setAppliesOn((T) simpleCategory);
            }
            simpleDiscs.add(simple);
        }
        responseT = new InResponseT<>(false,"",simpleDiscs);
        return responseT;
    }


    @Override
    public InResponse addItemDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, int itemId) {
        clearDate(discountDate);
        InResponse response;
        if (supplierId <= 0 || (discount <= 0 || discount >= 1) || itemCount <= 0 || itemId <= 0) {
            response = new InResponse(true, "One oe more Arguments is invalid");
            return response;
        }
        try {
            inventoryController.addItemDiscount(supplierId, discount, discountDate, itemCount, itemId);
        } catch (Exception e) {
            response = new InResponse(true, e.getMessage());
            return response;
        }
        response = new InResponse(false, "");
        return response;
    }

    @Override
    public InResponse addCategoryDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, String categoryName) {
        clearDate(discountDate);
        InResponse response;
        if (supplierId <= 0 || (discount <= 0 || discount >= 1) || itemCount <= 0 || categoryName.isEmpty() || categoryName.isBlank()) {
            response = new InResponse(true, "One oe more Arguments is invalid");
            return response;
        }
        try {
            inventoryController.addCategoryDiscount(supplierId, discount, discountDate, itemCount, categoryName);
        } catch (Exception e) {
            response = new InResponse(true, e.getMessage());
            return response;
        }
        response = new InResponse(false, "");
        return response;
    }

    //-------------------------------------------------------------------------Defect Functions

    @Override
    public InResponse recordDefect(int itemId, Calendar entryDate, int defectQuantity, String defectLocation) {
        clearDate(entryDate);
        InResponse response;
        if (itemId <= 0 || defectQuantity <= 0 || defectLocation.isEmpty() || defectLocation.isBlank()) {
            response = new InResponse(true, "One oe more Arguments is invalid");
            return response;
        }
        try {
            inventoryController.recordDefect(itemId, entryDate, defectQuantity, defectLocation);
        } catch (Exception e) {
            response = new InResponse(true, e.getMessage());
            return response;
        }
        response = new InResponse(false, "");
        return response;
    }

    //-------------------------------------------------------------------------Report functions

    @Override
    public InResponseT<List<Item>> inventoryReport() {
        InResponseT<List<Item>> shortageResponse;
        List<Item> simpleItemList = new ArrayList<>();
        try {
            List<BusinessLayer.InventoryPackage.Item> shortageItems = inventoryController.inventoryReport();
            shortageResponse = getResponseListItem(simpleItemList, shortageItems);

        } catch (Exception e) {
            shortageResponse = new InResponseT<>(true, e.getMessage(), null);
        }
        return shortageResponse;
    }

    @Override
    public InResponseT<List<Item>> categoryReport(String categoryName) {
        InResponseT<List<Item>> shortageResponse;
        List<Item> simpleItemList = new ArrayList<>();
        try {
            List<BusinessLayer.InventoryPackage.Item> shortageItems = inventoryController.categoryReport(categoryName);
            shortageResponse = getResponseListItem(simpleItemList, shortageItems);

        } catch (Exception e) {
            shortageResponse = new InResponseT<>(true, e.getMessage(), null);
        }
        return shortageResponse;
    }

    private InResponseT<List<Item>> getResponseListItem(List<Item> simpleItemList, List<BusinessLayer.InventoryPackage.Item> shortageItems) {
        InResponseT<List<Item>> shortageResponse;
        for (BusinessLayer.InventoryPackage.Item i : shortageItems) {
            Item simpleItem = new Item(i.getID(), i.getName(), i.getCostPrice(), i.getSellingPrice(), i.getMinAmount(),
                    i.getShelfQuantity(), i.getStorageQuantity(), i.getShelfLocation(), i.getStorageLocation(), i.getManufacturerID()
                    ,inventoryController.getItemCategory(i.getID()).getName());
            simpleItemList.add(simpleItem);
        }
        shortageResponse = new InResponseT<>(false, "", simpleItemList);
        return shortageResponse;
    }

    @Override
    public InResponseT<List<Item>> itemShortageReport() {
        InResponseT<List<Item>> shortageResponse;
        List<Item> simpleItemList = new ArrayList<>();
        try {
            List<BusinessLayer.InventoryPackage.Item> shortageItems = inventoryController.itemShortageReport();
            shortageResponse = getResponseListItem(simpleItemList, shortageItems);

        } catch (Exception e) {
            shortageResponse = new InResponseT<>(true, e.getMessage(), null);
        }
        return shortageResponse;
    }

    @Override
    public InResponseT<List<DefectEntry>> defectsReport(Calendar fromDate, Calendar toDate) {
        clearDate(fromDate,toDate);
        InResponseT<List<DefectEntry>> defectResponse;
        List<DefectEntry> simpleEntries = new ArrayList<>();
        try {
            List<BusinessLayer.InventoryPackage.DefectsPackage.DefectEntry> defectsReport = inventoryController.defectsReport(fromDate, toDate);
            for (BusinessLayer.InventoryPackage.DefectsPackage.DefectEntry entry : defectsReport) {
                DefectEntry simple = new DefectEntry(entry.getEntryDate(), entry.getItemID(), entry.getItemName(), entry.getQuantity(), entry.getLocation());
                simpleEntries.add(simple);
            }
            defectResponse = new InResponseT<>(false, "", simpleEntries);
        } catch (Exception e) {
            defectResponse = new InResponseT<>(true, e.getMessage(), null);
        }
        return defectResponse;
    }

    private void clearDate(Calendar... calendars){
        for (Calendar cl: calendars) {
            cl.clear(Calendar.MILLISECOND);
            cl.clear(Calendar.SECOND);
            cl.clear(Calendar.MINUTE);
            cl.clear(Calendar.HOUR);
        }
    }
}
