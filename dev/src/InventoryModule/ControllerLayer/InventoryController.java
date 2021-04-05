package InventoryModule.ControllerLayer;

import InventoryModule.BusinessLayer.Category;
import InventoryModule.BusinessLayer.DefectsPackage.DefectEntry;
import InventoryModule.BusinessLayer.DefectsPackage.DefectsLogger;
import InventoryModule.BusinessLayer.DiscountPackage.CategoryDiscount;
import InventoryModule.BusinessLayer.DiscountPackage.Discount;
import InventoryModule.BusinessLayer.DiscountPackage.ItemDiscount;
import InventoryModule.BusinessLayer.Item;
import InventoryModule.BusinessLayer.SalePackage.CategorySale;
import InventoryModule.BusinessLayer.SalePackage.ItemSale;
import InventoryModule.BusinessLayer.SalePackage.Sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryController {
    private final List<Category> categories;
    private DefectsLogger defectsLogger;
    private final List<Discount> discounts;
    private final List<Sale> sales;

    public InventoryController() {
        categories = new ArrayList<>();
        defectsLogger = new DefectsLogger();
        discounts = new ArrayList<>();
        sales = new ArrayList<>();
    }

    //-------------------------------------------------------------------------Item functions

    public void addItem(int id, String name, String categoryName, double costPrice, double sellingPrice, int minAmount,
                     String shelfLocation, String storageLocation, int storageQuantity, int shelfQuantity, int manufacturerId, List<Integer> suppliersIds) {
        //Determine the appropriate category in which to add the new item
        Category itemCategory = null;
        for (Category category : categories) {
            if (category.getName().equals(categoryName))
                itemCategory = category;
            for (Item item : category.getItems()) {
                if (item.getID() == id)
                    throw new IllegalArgumentException("An item with ID: " + id + " already exists in the system.");
            }
        }

        //If 'itemCategory' is still 'null' no category with the given name exists in the system
        if (itemCategory == null)
            throw new IllegalArgumentException("No category with with name: " + categoryName + " was found in the system.");

        //Create a new item with the given attributes and add it to the appropriate category
        Item newItem = new Item(id, name, costPrice, sellingPrice, minAmount, manufacturerId, suppliersIds,
                                shelfQuantity, storageQuantity, shelfLocation, storageLocation);
        itemCategory.addItem(newItem);
    }

    public Item getItem(int itemId) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    return item;
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void modifyItemName(int itemId, String newName) {
        Item item = getItem(itemId);
        item.setName(newName);
    }

    public void modifyItemCategory(int itemId, String newCategoryName) {
        Item modItem = null;
        Category newCategory = null;
        int count = 0; //Counter to track 'foreach' loop iteration in order to throw appropriate exception

        //Search the categories for the appropriate category to add the item to
        for (Category category : categories) {
            count++;
            if (category.getName().equals(newCategoryName))
                newCategory = category;
            for (Item item : category.getItems()) {
                if (item.getID() == itemId) {
                    if (category.getName().equals(newCategoryName))
                        return; //Item is already in the given category
                    else
                        category.removeItem(item);
                    modItem = item;
                }
            }
            if (count == categories.size() & newCategory == null)
                throw new IllegalArgumentException("No category with name: " + newCategoryName + " was found in the system");
            if (modItem != null & newCategory != null) {
                newCategory.addItem(modItem);
                return; //Item was successfully modified
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void modifyItemCostPrice(int itemId, double newCostPrice) {
        Item item = getItem(itemId);
        item.setCostPrice(newCostPrice);
    }

    public void modifyItemSellingPrice(int itemId, double newSellingPrice) {
        Item item = getItem(itemId);
        item.setSellingPrice(newSellingPrice);
    }

    public void changeItemLocation(int itemId, String newShelfLocation, String newStorageLocation) {
        Item item = getItem(itemId);
        item.setLocation(newShelfLocation, newStorageLocation);
    }

    public void changeItemShelfLocation(int itemId, String newShelfLocation) {
        Item item = getItem(itemId);
        item.setShelfLocation(newShelfLocation);
    }

    public void changeItemStorageLocation(int itemId, String newStorageLocation) {
        Item item = getItem(itemId);
        item.setStorageLocation(newStorageLocation);
    }

    public void modifyItemQuantity(int itemId, int newShelfQuantity, int newStorageQuantity) {
        Item item = getItem(itemId);
        item.setQuantity(newShelfQuantity, newStorageQuantity);
    }

    public void modifyItemShelfQuantity(int itemId, int newShelfQuantity) {
        Item item = getItem(itemId);
        item.setShelfQuantity(newShelfQuantity);
    }

    public void modifyItemStorageQuantity(int itemId, int newStorageQuantity) {
        Item item = getItem(itemId);
        item.setStorageQuantity(newStorageQuantity);
    }

    public void addItemSupplier(int itemId, int supplierId) {
        Item item = getItem(itemId);
        item.addSupplier(supplierId);
    }

    public void removeItemSupplier(int itemId, int supplierId) {
        Item item = getItem(itemId);
        item.removeSupplier(supplierId);
    }

    public void removeItem(int itemId) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    category.removeItem(item);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }


    //-------------------------------------------------------------------------Category functions

    /*
    If parent category is null, the new category should be added as a main category.
     */
    public void addCategory(String categoryName, String parentCategoryName) {
        Category newCategory;
        if (parentCategoryName != null) {
            for (Category category : categories) {
                if (category.getName().equals(parentCategoryName)) {
                    newCategory = new Category(categoryName, new ArrayList<>(), category, new ArrayList<>());
                    categories.add(newCategory);
                }
            }
        }

        //If no parent category was found, add new category with NULL parent
        newCategory = new Category(categoryName, new ArrayList<>(), null, new ArrayList<>());
        categories.add(newCategory);
    }

    public Category getCategory(String categoryName) {
        for (Category category: categories) {
            if (category.getName().equals(categoryName))
                return category;
        }
        throw new IllegalArgumentException("No category with name: " + categoryName + " was found in the system.");
    }

    public void modifyCategoryName(String oldName, String newName) {
        Category category = getCategory(oldName);
        category.setName(newName);
    }

    /*
    when the category is removed all its sub categories move to the parent category.
     */
    public void removeCategory(String categoryName) {

    }


    //-------------------------------------------------------------------------Sale functions

    public void addItemSale(String saleName, int itemID, double saleDiscount, Date startDate, Date endDate) {
        ItemSale newSale = new ItemSale(saleName, saleDiscount, startDate, endDate, getItem(itemID));
        sales.add(newSale);
    }

    public void addCategorySale(String saleName, String categoryName, double saleDiscount, Date startDate, Date endDate) {
        CategorySale newSale = new CategorySale(saleName, saleDiscount, startDate, endDate, getCategory(categoryName));
        sales.add(newSale);
    }

    private Sale getSale(String saleName) {
        for (Sale sale : sales) {
            if (sale.getName().equals(saleName))
                return sale;
        }
        throw new IllegalArgumentException("No sale with name: " + saleName + " was found in the system.");
    }

    public void modifySaleName(String oldName, String newName) {
        Sale sale = getSale(oldName);
        sale.setName(newName);
    }

    public void modifySaleDiscount(String saleName, double newDiscount) {
        Sale sale = getSale(saleName);
        sale.setDiscount(newDiscount);
    }

    public void modifySaleDates(String saleName, Date startDate, Date endDate) {
        Sale sale = getSale(saleName);
        sale.setSaleDates(startDate, endDate);
    }


    //-------------------------------------------------------------------------Discount functions

    public void addItemDiscount(int supplierID, double discount, Date discountDate, int itemCount, int itemId) {
        ItemDiscount newDiscount = new ItemDiscount(supplierID, discount, discountDate, itemCount, getItem(itemId));
        discounts.add(newDiscount);
    }

    public void addCategoryDiscount(int supplierID, double discount, Date discountDate, int itemCount, String categoryName) {
        CategoryDiscount newDiscount = new CategoryDiscount(supplierID, discount, discountDate, itemCount, getCategory(categoryName));
        discounts.add(newDiscount);
    }


    //-------------------------------------------------------------------------Defect functions

    public void recordDefect(int itemId, String itemName, Date entryDate, int defectQuantity, String defectLocation) {
        DefectEntry newDefect = new DefectEntry(itemId, itemName, entryDate, defectQuantity, defectLocation);
        defectsLogger.addDefectEntry(newDefect);
    }


    //-------------------------------------------------------------------------Report functions

    public List<Item> inventoryReport() {
        List<Item> inventoryReportList = new ArrayList<>();
        for (Category category : categories) {
            inventoryReportList.addAll(category.getItems());
        }
        return inventoryReportList;
    }

    public List<Item> categoryReport(String categoryName) {
        Category category = getCategory(categoryName);
        List<Item> inventoryCategoryReportList = new ArrayList<>(category.getItems());
        return inventoryCategoryReportList;
    }

    public List<Item> itemShortageReport() {
        List<Item> shortageReportList = new ArrayList<>();
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getQuantity() <= item.getMinAmount())
                    shortageReportList.add(item);
            }
        }
        return shortageReportList;
    }

    public List<DefectEntry> defectsReport(Date fromDate, Date toDate) {
        List<DefectEntry> defectEntryList = new ArrayList<>();
        for (DefectEntry defectEntry : defectsLogger.getDefectEntries()) {
            Date entryDate = defectEntry.getEntryDate();
            if(entryDate.compareTo(fromDate) >= 0 & entryDate.compareTo(toDate) <= 0)
                defectEntryList.add(defectEntry);
        }
        return defectEntryList;
    }
}
