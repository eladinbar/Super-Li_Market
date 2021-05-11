package BusinessLayer.InventoryPackage;

import BusinessLayer.InventoryPackage.DefectsPackage.DefectEntry;
import BusinessLayer.InventoryPackage.DefectsPackage.DefectsLogger;
import BusinessLayer.InventoryPackage.DiscountPackage.CategoryDiscount;
import BusinessLayer.InventoryPackage.DiscountPackage.Discount;
import BusinessLayer.InventoryPackage.DiscountPackage.ItemDiscount;
import BusinessLayer.InventoryPackage.SalePackage.CategorySale;
import BusinessLayer.InventoryPackage.SalePackage.ItemSale;
import BusinessLayer.InventoryPackage.SalePackage.Sale;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InventoryController {
    private final Category BASE_CATEGORY = new Category("Uncategorized");
    private final List<Category> categories;
    private final List<Sale> sales;
    private final List<Discount> discounts;
    private final DefectsLogger defectsLogger;

    public InventoryController() {
        categories = new ArrayList<>();
        defectsLogger = new DefectsLogger();
        discounts = new ArrayList<>();
        sales = new ArrayList<>();
    }

    //-------------------------------------------------------------------------Item functions

    public void addItem(int id, String name, String categoryName, double costPrice, double sellingPrice, int minAmount, String shelfLocation, String storageLocation,
                        int storageQuantity, int shelfQuantity, int manufacturerId, List<String> suppliersIds) {
        Category itemCategory;
        try {
            itemCategory = getCategory(categoryName);
        } catch(IllegalArgumentException ex) {
            throw new IllegalArgumentException("No category with name: " + categoryName + " was found in the system.");
        }
        try {
            if (getItem(id, itemCategory) != null)
                throw new IllegalArgumentException("An item with ID: " + id + " already exists in the system.");
        } catch (IllegalArgumentException ex) {
            //No item with 'id' found - continue
        }

        //Create a new item with the given attributes and add it to the appropriate category
        Item newItem = new Item(id, name, costPrice, sellingPrice, minAmount, manufacturerId, suppliersIds,
                shelfQuantity, storageQuantity, shelfLocation, storageLocation);
        newItem.save(itemCategory.getName());
        itemCategory.addItem(newItem);
    }

    public Item getItem(int itemId) {
        Item item;
        for (Category category : categories) {
            item = getItem(itemId, category);
            if (item != null)
                return item;
        }
        //Check in base category as well
        item = getItem(itemId, BASE_CATEGORY);
        if (item != null)
            return item;

        //Retrieve from database
        Item savedItem = new Item(itemId);
        boolean found;
        found = savedItem.find();
        if (found)
            return savedItem;

        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    private Item getItem(int itemId, Category category) {
        for (Item item : category.getItems()) {
            if (item.getID() == itemId)
                return item;
        }
        return null;
    }

    public Category getItemCategory(int itemId) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    return category;
            }
        }
        //Check in base category as well
        for (Item item : BASE_CATEGORY.getItems()) {
            if (item.getID() == itemId)
                return BASE_CATEGORY;
        }

        //Retrieve from database
        Item savedItem = new Item(itemId);
        boolean found = savedItem.find();
        if (!found)
            throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");

        return getCategory(savedItem.getDalCopyItem().getCategoryName());
    }

    public void modifyItemName(int itemId, String newName) {
        Item item = getItem(itemId);
        item.setAndSaveName(newName);
    }

    public void modifyItemCategory(int itemId, String newCategoryName) {
        Item modItem = null;
        Category oldCategory = null;
        Category newCategory = null;
        int count = 0; //Counter to track 'foreach' loop iteration in order to throw appropriate exception

        //Search the categories for the appropriate category to add the item to
        //Start the search from base category
        if (newCategoryName.trim().equals("") | newCategoryName.trim().equals("Uncategorized"))
            newCategory = BASE_CATEGORY;
        for (Item item : BASE_CATEGORY.getItems()) {
            if (item.getID() == itemId) {
                if (newCategoryName.trim().equals("") | newCategoryName.trim().equals("Uncategorized"))
                    return; //Item is already in the base category
                else {
                    item.delete(); //Remove item with outdated category from database
                    BASE_CATEGORY.removeItem(item);
                    oldCategory = BASE_CATEGORY; //Save old category in case new category does not exist for rollback
                }
                modItem = item;
                break; //Item was removed
            }
        }
        //Continue search in the rest of the categories
        for (Category category : categories) {
            count++;
            if (category.getName().equals(newCategoryName))
                newCategory = category;
            for (Item item : category.getItems()) {
                if (item.getID() == itemId) {
                    if (category.getName().equals(newCategoryName))
                        return; //Item is already in the given category
                    else {
                        item.delete(); //Remove item with outdated category from database
                        category.removeItem(item);
                        oldCategory = category; //Save old category in case new category does not exist for rollback
                    }
                    modItem = item;
                    break; //Item was removed
                }
            }
            if (count == categories.size() & newCategory == null) {
                if (oldCategory != null) {
                    modItem.save(oldCategory.getName());
                    oldCategory.addItem(modItem); //Rollback item category modification
                }
                throw new IllegalArgumentException("No category with name: " + newCategoryName + " was found in the system");
            }
            if (modItem != null & newCategory != null) {
                modItem.save(oldCategory.getName());
                newCategory.addItem(modItem);
                return; //Item was successfully modified
            }
        }
        if (count == categories.size() & newCategory == null) {
            if (oldCategory != null) {
                modItem.save(oldCategory.getName());
            }
                oldCategory.addItem(modItem); //Rollback item category modification
            throw new IllegalArgumentException("No category with name: " + newCategoryName + " was found in the system");
        }
        if (modItem != null & newCategory != null) {
            modItem.save(oldCategory.getName());
            newCategory.addItem(modItem);
            return; //Item was successfully modified
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void modifyItemCostPrice(int itemId, double newCostPrice) {
        Item item = getItem(itemId);
        item.setAndSaveCostPrice(newCostPrice);
    }

    public void modifyItemSellingPrice(int itemId, double newSellingPrice) {
        Item item = getItem(itemId);
        item.setAndSaveSellingPrice(newSellingPrice);
    }

    public void changeItemShelfLocation(int itemId, String newShelfLocation) {
        Item item = getItem(itemId);
        item.setAndSaveShelfLocation(newShelfLocation);
    }

    public void changeItemStorageLocation(int itemId, String newStorageLocation) {
        Item item = getItem(itemId);
        item.setAndSaveStorageLocation(newStorageLocation);
    }

    public void modifyItemShelfQuantity(int itemId, int newShelfQuantity) {
        Item item = getItem(itemId);
        item.setAndSaveShelfQuantity(newShelfQuantity);
    }

    public void modifyItemStorageQuantity(int itemId, int newStorageQuantity) {
        Item item = getItem(itemId);
        item.setAndSaveStorageQuantity(newStorageQuantity);
    }

    public void addItemSupplier(int itemId, String supplierId) {
        Item item = getItem(itemId);
        item.addSupplier(supplierId);
    }

    public void removeItemSupplier(int itemId, String supplierId) {
        Item item = getItem(itemId);
        item.removeSupplier(supplierId);
    }

    public void removeItem(int itemId) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId) {
                    item.delete();
                    category.removeItem(item);
                }
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

            try {
                Category parentCategory = getCategory(parentCategoryName);
                Category newCategory = new Category(categoryName, new ArrayList<>(), parentCategory, new ArrayList<>());
                newCategory.save();
                categories.add(newCategory);
                parentCategory.addSubCategory(newCategory);
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("No parent category with the name: " + parentCategoryName + " was found in the system.");
            }
        }
    }

    public Category getCategory(String categoryName) {
        Category businessCategory = getBusinessCategory(categoryName);
        if (businessCategory != null)
            return businessCategory;
        
        //Retrieve from database
        Category savedCategory = new Category(categoryName);
        boolean found;
        found = savedCategory.find();
        if (found) {
            categories.add(savedCategory);
            Category parentCategory = getBusinessCategory(savedCategory.getParentCategory().getName());
            if (parentCategory != null) {
                savedCategory.setParentCategory(parentCategory);
                parentCategory.addSubCategory(savedCategory);
            }
            List<Category> subCategories = getBusinessSubCategories(savedCategory.getName());
            savedCategory.addSubCategories(subCategories);
            return savedCategory;
        }
        
        throw new IllegalArgumentException("No category with name: " + categoryName + " was found in the system.");
    }

    private Category getBusinessCategory(String categoryName) {
        if (categoryName.equals("") | categoryName.equals("Uncategorized"))
            return BASE_CATEGORY;

        for (Category category: categories) {
            if (category.getName().equals(categoryName))
                return category;
        }

        return null;
    }

    private List<Category> getBusinessSubCategories(String parentName) {
        List<Category> subCategories = new ArrayList<>();
        for (Category parentCategory : categories) {
            if (parentCategory.getName().equals(parentName))
                subCategories.add(parentCategory);
        }
        return subCategories;
    }

    public void modifyCategoryName(String oldName, String newName) {
        Category category = getCategory(oldName);
        category.setAndSaveName(newName);
    }

    public void changeParentCategory(String categoryName, String newParentName) {
        if (categoryName.equals(newParentName))
            throw new IllegalArgumentException("A category cannot have itself as its parent category.");

        Category category = getCategory(categoryName);

        if (category.getParentCategory().getName().equals(newParentName)) //Parent category equals to new parent category
            return;

        //If newParentName is null or empty, set parent category as base category
        if (newParentName == null || newParentName.trim().equals("") | newParentName.trim().equals("Uncategorized")) {
            category.setAndSaveParentCategory(BASE_CATEGORY);
        }
        //Else, check whether the given newParentName is a valid parent category to 'category'
        else {
            loadSubCategories(category); //Ensure all subcategories of category are loaded into the system

            Category parentCategory = getCategory(newParentName);
            if (isSubCategory(category, parentCategory))
                throw new IllegalArgumentException("Cannot change parent category to a sub category, please enter a different parent category.");
            category.setAndSaveParentCategory(parentCategory);
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

    private void loadSubCategories(Category parent) {
        List<Category> subCategories = new ArrayList<>();
        Category category = new Category();
        category.find(subCategories, parent.getName());

        parent.addSubCategories(subCategories);
        for (Category subCategory : subCategories) {
            subCategory.setParentCategory(parent);
        }
    }

    /*
    When the category is removed all its sub categories move to the parent category.
     */
    public void removeCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().equals("") | categoryName.trim().equals("Uncategorized"))
            throw new IllegalArgumentException("Base category cannot be removed.");

        Category oldCategory = getCategory(categoryName);
        Category parentCategory = oldCategory.getParentCategory();
        for (Item item : oldCategory.getItems()) {
            item.delete();
            item.save(oldCategory.getName());
            parentCategory.addItem(item);
            oldCategory.removeItem(item);
        }
        for(Category subCategory : oldCategory.getSubCategories()) {
            parentCategory.addSubCategory(subCategory);
            oldCategory.removeSubCategory(subCategory);
        }
        oldCategory.delete();
        categories.remove(oldCategory);
    }


    //-------------------------------------------------------------------------Sale functions

    public void addItemSale(String saleName, int itemId, double saleDiscount, LocalDate startDate, LocalDate endDate) {
        Item item = getItem(itemId); //Makes sure an item with 'itemId' exists in the system, throws an exception if not
        try {
            getSale(saleName);
            throw new IllegalArgumentException("A sale with the name " + saleName + " already exists in the system.");
        } catch(IllegalArgumentException ex) { //No item with the same name exists
            if (ex.getMessage().equals("A sale with the name " + saleName + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            ItemSale newSale = new ItemSale(saleName, saleDiscount, startDate, endDate, item);
            newSale.save();
            sales.add(newSale);
        }
    }

    public void addCategorySale(String saleName, String categoryName, double saleDiscount, LocalDate startDate, LocalDate endDate) {
        getCategory(categoryName); //Makes sure a category with 'categoryName' exists in the system, throws an exception if not
        try {
            getSale(saleName);
            throw new IllegalArgumentException("A sale with the name " + saleName + " already exists in the system.");
        } catch(IllegalArgumentException ex) { //No item with the same name exists
            if (ex.getMessage().equals("A sale with the name " + saleName + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            CategorySale newSale = new CategorySale(saleName, saleDiscount, startDate, endDate, getCategory(categoryName));
            newSale.save();
            sales.add(newSale);
        }
    }

    public Sale getSale(String saleName) {
        for (Sale sale : sales) {
            if (sale.getName().equals(saleName))
                return sale;
        }

        //Retrieve from database
        CategorySale savedCategorySale = new CategorySale(saleName);
        boolean found;
        found = savedCategorySale.find();
        if (found) {
            savedCategorySale.setCategory(getCategory(savedCategorySale.getCategory().getName()));
            sales.add(savedCategorySale);
            return savedCategorySale;
        }

        ItemSale savedItemSale = new ItemSale(saleName);
        found = savedItemSale.find();
        if (found) {
            savedItemSale.setItem(getItem(savedItemSale.getItem().getID()));
            sales.add(savedItemSale);
            return savedItemSale;
        }

        throw new IllegalArgumentException("No sale with name: " + saleName + " was found in the system.");
    }

    public void modifySaleName(String oldName, String newName) {
        Sale sale = getSale(oldName);
        sale.setAndSaveName(newName);
    }

    public void modifySaleDiscount(String saleName, double newDiscount) {
        Sale sale = getSale(saleName);
        sale.setAndSaveDiscount(newDiscount);
    }

    public void modifySaleDates(String saleName, LocalDate startDate, LocalDate endDate) {
        Sale sale = getSale(saleName);
        sale.setAndSaveSaleDates(startDate, endDate);
    }


    //-------------------------------------------------------------------------Discount functions

    public void addItemDiscount(String supplierId, double discount, LocalDate discountDate, int itemCount, int itemId) {
        getItem(itemId); //Makes sure an item with 'itemId' exists in the system, throws an exception if not
        try {
            getDiscount(supplierId, discountDate);
            throw new IllegalArgumentException("A discount with supplier ID: " + supplierId + " already exists in the system.");
        } catch (Exception ex) {
            if (ex.getMessage().equals("A discount with supplier ID: " + supplierId + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            ItemDiscount newDiscount = new ItemDiscount(supplierId, discount, discountDate, itemCount, getItem(itemId));
            newDiscount.save();
            discounts.add(newDiscount);
        }
    }

    public void addCategoryDiscount(String supplierId, double discount, LocalDate discountDate, int itemCount, String categoryName) {
        getCategory(categoryName); //Makes sure a category with 'categoryName' exists in the system, throws an exception if not
        try {
            getDiscount(supplierId, discountDate);
            throw new IllegalArgumentException("A discount with supplier ID: " + supplierId + " already exists in the system.");
        } catch (Exception ex) {
            if (ex.getMessage().equals("A discount with supplier ID: " + supplierId + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            CategoryDiscount newDiscount = new CategoryDiscount(supplierId, discount, discountDate, itemCount, getCategory(categoryName));
            newDiscount.save();
            discounts.add(newDiscount);
        }
    }

    public List<Discount> getDiscount(String supplierId, LocalDate discountDate) {
        List<Discount> discountList = new ArrayList<>();
        for (Discount discount : discounts) {
            if (discount.getSupplierID().equals(supplierId) & discount.getDate().compareTo(discountDate) == 0)
                discountList.add(discount);
        }

        //Retrieve from database
        List<CategoryDiscount> savedCategoryDiscountList = new ArrayList<>();
        CategoryDiscount savedCategoryDiscount = new CategoryDiscount();
        boolean found;
        found = savedCategoryDiscount.find(savedCategoryDiscountList, supplierId, discountDate.toString());
        if (found) {
            savedCategoryDiscount.setCategory(getCategory(savedCategoryDiscount.getCategory().getName()));
            discountList.addAll(savedCategoryDiscountList);
            discounts.addAll(savedCategoryDiscountList);
        }

        List<ItemDiscount> savedItemDiscountList = new ArrayList<>();
        ItemDiscount savedItemDiscount = new ItemDiscount();
        found = savedItemDiscount.find(savedItemDiscountList, supplierId, discountDate.toString());
        if (found) {
            savedItemDiscount.setItem(getItem(savedItemDiscount.getItem().getID()));
            discountList.addAll(savedItemDiscountList);
            discounts.addAll(savedItemDiscountList);
        }

        if (discountList.isEmpty())
            throw new IllegalArgumentException("No discount with supplier ID: " + supplierId + " exists in the system.");
        return discountList;
    }


    //-------------------------------------------------------------------------Defect functions

    public void recordDefect(int itemId, LocalDate entryDate, int defectQuantity, String defectLocation) {
        Item item = getItem(itemId);
        try {
            getDefectEntry(itemId, entryDate);
            throw new IllegalArgumentException("A defect entry with the same ID:" + itemId + " and the same date recorded:" + entryDate + " already exists in the system.");
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage().equals("A defect entry with the same ID:" + itemId + " and the same date recorded:" + entryDate + " already exists in the system."))
                throw ex; //Rethrow exception thrown in 'try' block (different error message)

            DefectEntry newDefect = new DefectEntry(itemId, item.getName(), entryDate, defectQuantity, defectLocation);
            newDefect.save();
            defectsLogger.addDefectEntry(newDefect);
        }
    }

    public DefectEntry getDefectEntry(int itemId, LocalDate entryDate) {
        DefectEntry defectEntry = defectsLogger.getDefectEntry(itemId, entryDate);
        if (defectEntry != null)
            return defectEntry;

        //Retrieve from database
        defectEntry = new DefectEntry(itemId, entryDate);
        boolean found;
        found = defectEntry.find();
        if (found) {
            defectsLogger.addDefectEntry(defectEntry);
            return defectEntry;
        }

        throw new IllegalArgumentException("No defect entry with ID: " + itemId + " and date recorded: " + entryDate + " were found in the system");
    }

    private DefectEntry getBusinessDefectEntry(int itemId, LocalDate entryDate) {
        return defectsLogger.getDefectEntry(itemId, entryDate);
    }


    //-------------------------------------------------------------------------Report functions

    public List<Item> inventoryReport() {
        loadCategories(); //Ensure all categories are loaded into the system

        List<Item> inventoryReportList = new ArrayList<>();
        for (Category category : categories) {
            inventoryReportList.addAll(category.getItems());
        }
        //Add all items in base category to the report
        inventoryReportList.addAll(BASE_CATEGORY.getItems());
        return inventoryReportList;
    }

    private void loadCategories() {
        List<Category> savedCategories = new ArrayList<>();
        Category loadCategory = new Category();
        loadCategory.find(savedCategories);

        for (Category category : savedCategories) {
            if (getBusinessCategory(category.getName()) == null) { //If category is not already in business - continue
                for (Category someCategory : savedCategories) {
                    if (category.getName().equals(someCategory.getParentCategory().getName()))
                        category.addSubCategory(someCategory);
                    else if (category.getParentCategory().getName().equals(someCategory.getName()))
                        category.setParentCategory(someCategory);
                }
                categories.add(category);
            }
        }
    }

    public List<Item> categoryReport(String categoryName) {
        Category category = getCategory(categoryName);
        List<Item> inventoryCategoryReportList = new ArrayList<>(category.getItems());
        return inventoryCategoryReportList;
    }

    public List<Item> itemShortageReport() {
        loadCategories(); //Ensure all categories are loaded into the system

        List<Item> shortageReportList = new ArrayList<>();
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getQuantity() <= item.getMinAmount())
                    shortageReportList.add(item);
            }
        }
        //Check for under minimum amount items in base category
        for (Item item : BASE_CATEGORY.getItems()) {
            if (item.getQuantity() <= item.getMinAmount())
                shortageReportList.add(item);
        }
        return shortageReportList;
    }

    public List<DefectEntry> defectsReport(LocalDate fromDate, LocalDate toDate) {
        loadDefects(); //Ensure all defect entries are loaded into the system

        List<DefectEntry> defectEntryList = new ArrayList<>();
        for (DefectEntry defectEntry : defectsLogger.getDefectEntries()) {
            LocalDate entryDate = defectEntry.getEntryDate();
            if(entryDate.compareTo(fromDate) >= 0 & entryDate.compareTo(toDate) <= 0)
                defectEntryList.add(defectEntry);
        }
        return defectEntryList;
    }

    private void loadDefects() {
        List<DefectEntry> savedDefectEntries = new ArrayList<>();
        DefectEntry loadDefectEntry = new DefectEntry();
        loadDefectEntry.find(savedDefectEntries);

        for (DefectEntry defectEntry : savedDefectEntries) {
            if (getBusinessDefectEntry(defectEntry.getItemID(), defectEntry.getEntryDate()) == null) { //If defect entry is not already in business - continue
                defectsLogger.addDefectEntry(defectEntry);
            }
        }
    }
}
