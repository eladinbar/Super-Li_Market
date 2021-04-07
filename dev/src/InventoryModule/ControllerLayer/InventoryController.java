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
import java.util.Calendar;
import java.util.List;

public class InventoryController {
    private final Category baseCategory;
    private final List<Category> categories;
    private final DefectsLogger defectsLogger;
    private final List<Discount> discounts;
    private final List<Sale> sales;

    public InventoryController() {
        baseCategory = new Category("Uncategorized");
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
    If parent category is null or empty, the new category should be added as a main category.
     */
    public void addCategory(String categoryName, String parentCategoryName) {
        if (categoryName.equals(parentCategoryName))
            throw new IllegalArgumentException("A category cannot have itself as its parent category.");

        try {
            getCategory(categoryName);
            throw new IllegalArgumentException("A category with the name: " + categoryName + " already exists in the system.");
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage().equals("A category with the name: " + categoryName + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            Category newCategory;
            if (parentCategoryName != null && !parentCategoryName.trim().equals("")) {
                for (Category parentCategory : categories) {
                    if (parentCategory.getName().equals(parentCategoryName)) {
                        newCategory = new Category(categoryName, new ArrayList<>(), parentCategory, new ArrayList<>());
                        categories.add(newCategory);
                        parentCategory.addSubCategory(newCategory);
                        return;
                    }
                }
                throw new IllegalArgumentException("No parent category with the name: " + parentCategoryName + " was found in the system.");
            }
            else {
                //If parent category is null or empty, add new category with "Uncategorized" as its parent category
                newCategory = new Category(categoryName, new ArrayList<>(), baseCategory, new ArrayList<>());
                categories.add(newCategory);
            }
        }
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

    public void changeParentCategory(String categoryName, String newParentName) {
        if (categoryName.equals(newParentName))
            throw new IllegalArgumentException("A category cannot have itself as its parent category.");

        Category category = getCategory(categoryName);

        if (category.getParentCategory().getName().equals(newParentName)) //Parent category equals to new parent category
            return;

        //If newParentName is null or empty, set parent category as base category
        if (newParentName == null || newParentName.trim().equals(""))
            category.setParentCategory(baseCategory);
        //Else, check whether the given newParentName is a valid parent category to 'category'
        else {
            Category parentCategory = getCategory(newParentName);
            if (isSubCategory(category, parentCategory))
                throw new IllegalArgumentException("Cannot change parent category to a sub category, please enter a different parent category.");
            category.setParentCategory(parentCategory);
        }
    }

    public boolean isSubCategory(Category mainCategory, Category category) {
        boolean isSubCategory = false;
        for (Category subCategory : mainCategory.getSubCategories()) {
            if (category == subCategory)
                return true;
            isSubCategory = isSubCategory(subCategory, category);
        }
        return isSubCategory;
    }

    /*
    when the category is removed all its sub categories move to the parent category.
     */
    public void removeCategory(String categoryName) {
        Category oldCategory = getCategory(categoryName);
        Category parentCategory = oldCategory.getParentCategory();
        for (Item item : oldCategory.getItems()) {
            parentCategory.addItem(item);
            oldCategory.removeItem(item);
        }
        for(Category subCategory : oldCategory.getSubCategories()) {
            parentCategory.addSubCategory(subCategory);
            oldCategory.removeSubCategory(subCategory);
        }
        categories.remove(oldCategory);
    }


    //-------------------------------------------------------------------------Sale functions

    public void addItemSale(String saleName, int itemId, double saleDiscount, Calendar startDate, Calendar endDate) {
        getItem(itemId); //Makes sure an item with 'itemId' exists in the system, throws an exception if not
        try {
            getSale(saleName);
            throw new IllegalArgumentException("A sale with the name " + saleName + " already exists in the system.");
        } catch(IllegalArgumentException ex) { //No item with the same name exists
            if (ex.getMessage().equals("A sale with the name " + saleName + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            ItemSale newSale = new ItemSale(saleName, saleDiscount, startDate, endDate, getItem(itemId));
            sales.add(newSale);
        }
    }

    public void addCategorySale(String saleName, String categoryName, double saleDiscount, Calendar startDate, Calendar endDate) {
        getCategory(categoryName); //Makes sure a category with 'categoryName' exists in the system, throws an exception if not
        try {
            getSale(saleName);
            throw new IllegalArgumentException("A sale with the name " + saleName + " already exists in the system.");
        } catch(IllegalArgumentException ex) { //No item with the same name exists
            if (ex.getMessage().equals("A sale with the name " + saleName + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            CategorySale newSale = new CategorySale(saleName, saleDiscount, startDate, endDate, getCategory(categoryName));
            sales.add(newSale);
        }
    }

    public Sale getSale(String saleName) {
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

    public void modifySaleDates(String saleName, Calendar startDate, Calendar endDate) {
        Sale sale = getSale(saleName);
        sale.setSaleDates(startDate, endDate);
    }


    //-------------------------------------------------------------------------Discount functions

    public void addItemDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, int itemId) {
        getItem(itemId); //Makes sure an item with 'itemId' exists in the system, throws an exception if not
        try {
            getDiscount(supplierId, discountDate);
            throw new IllegalArgumentException("A discount with supplier ID: " + supplierId + " already exists in the system.");
        } catch (Exception ex) {
            if (ex.getMessage().equals("A discount with supplier ID: " + supplierId + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            ItemDiscount newDiscount = new ItemDiscount(supplierId, discount, discountDate, itemCount, getItem(itemId));
            discounts.add(newDiscount);
        }
    }

    public void addCategoryDiscount(int supplierId, double discount, Calendar discountDate, int itemCount, String categoryName) {
        getCategory(categoryName); //Makes sure a category with 'categoryName' exists in the system, throws an exception if not
        try {
            getDiscount(supplierId, discountDate);
            throw new IllegalArgumentException("A discount with supplier ID: " + supplierId + " already exists in the system.");
        } catch (Exception ex) {
            if (ex.getMessage().equals("A discount with supplier ID: " + supplierId + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            CategoryDiscount newDiscount = new CategoryDiscount(supplierId, discount, discountDate, itemCount, getCategory(categoryName));
            discounts.add(newDiscount);
        }
    }

    public List<Discount> getDiscount(int supplierId, Calendar discountDate) {
        List<Discount> discountList = new ArrayList<>();
        for (Discount discount : discounts) {
            if (discount.getSupplierID() == supplierId & discount.getDate().equals(discountDate))
                discountList.add(discount);
        }

        if (discountList.isEmpty())
            throw new IllegalArgumentException("No discount with supplier ID: " + supplierId + " exists in the system.");
        return discountList;
    }


    //-------------------------------------------------------------------------Defect functions

    public void recordDefect(int itemId, Calendar entryDate, int defectQuantity, String defectLocation) {
        Item item = getItem(itemId);
        try {
            getDefectEntry(itemId, entryDate);
            throw new IllegalArgumentException("A defect entry with the same ID:" + itemId + " and the same date recorded:" + entryDate + " already exists in the system.");
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage().equals("A defect entry with the same ID:" + itemId + " and the same date recorded:" + entryDate + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            DefectEntry newDefect = new DefectEntry(itemId, item.getName(), entryDate, defectQuantity, defectLocation);
            defectsLogger.addDefectEntry(newDefect);
        }
    }

    public DefectEntry getDefectEntry(int itemId, Calendar entryDate) {
        DefectEntry defectEntry = defectsLogger.getDefectEntry(itemId, entryDate);
        if (defectEntry == null)
            throw new IllegalArgumentException("No defect entry with ID: " + itemId + " and date recorded: " + entryDate + " were found in the system");
        return defectEntry;
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

    public List<DefectEntry> defectsReport(Calendar fromDate, Calendar toDate) {
        List<DefectEntry> defectEntryList = new ArrayList<>();
        for (DefectEntry defectEntry : defectsLogger.getDefectEntries()) {
            Calendar entryDate = defectEntry.getEntryDate();
            if(entryDate.compareTo(fromDate) >= 0 & entryDate.compareTo(toDate) <= 0)
                defectEntryList.add(defectEntry);
        }
        return defectEntryList;
    }
}
