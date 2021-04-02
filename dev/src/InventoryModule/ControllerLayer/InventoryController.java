package InventoryModule.ControllerLayer;

import InventoryModule.BusinessLayer.Category;
import InventoryModule.BusinessLayer.DefectsPackage.DefectEntry;
import InventoryModule.BusinessLayer.DefectsPackage.DefectsLogger;
import InventoryModule.BusinessLayer.DiscountPackage.Discount;
import InventoryModule.BusinessLayer.Item;
import InventoryModule.BusinessLayer.SalePackage.Sale;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InventoryController {
    private List<Category> categories;
    private DefectsLogger defectsLogger;
    private List<Discount> discounts;
    private List<Sale> sales;

    public InventoryController() {
        categories = new ArrayList<>();
        defectsLogger = new DefectsLogger();
        discounts = new ArrayList<>();
        sales = new ArrayList<>();
    }

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
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.setName(newName);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
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
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.setCostPrice(newCostPrice);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void modifyItemSellingPrice(int itemId, double newSellingPrice) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.setSellingPrice(newSellingPrice);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void changeItemLocation(int itemId, String newStorageLocation, String newShelfLocation) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId) {
                    item.setShelfLocation(newShelfLocation);
                    item.setStorageLocation(newStorageLocation);
                }
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void changeItemShelfLocation(int itemId, String newShelfLocation) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.setShelfLocation(newShelfLocation);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void changeItemStorageLocation(int itemId, String newStorageLocation) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.setStorageLocation(newStorageLocation);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void modifyItemQuantity(int itemId, int newStorageQuantity, int newShelfQuantity) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId) {
                    item.setShelfQuantity(newShelfQuantity);
                    item.setStorageQuantity(newStorageQuantity);
                }
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void modifyItemShelfQuantity(int itemId, int newShelfQuantity) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.setShelfQuantity(newShelfQuantity);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void modifyItemStorageQuantity(int itemId, int newStorageQuantity) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.setStorageQuantity(newStorageQuantity);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void addItemSupplier(int itemId, int supplierId) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.addSupplier(supplierId);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
    }

    public void removeItemSupplier(int itemId, int supplierId) {
        for (Category category : categories) {
            for (Item item : category.getItems()) {
                if (item.getID() == itemId)
                    item.removeSupplier(supplierId);
            }
        }
        throw new IllegalArgumentException("No item with ID: " + itemId + " was found in the system.");
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


    //-------------------------------------------------------------------------

    /*
    If parent category is null, the new category should be added as a main category.
     */
    public void addCategory(String categoryName, String parentCategory) {

    }

    public Category getCategory(String categoryName) {
        for (Category category: categories) {
            if (category.getName().equals(categoryName))
                return category;
        }
        throw new IllegalArgumentException("No category with name: " + categoryName + " was found in the system.");
    }

    public void modifyCategoryName(String oldName, String newName) {
        for (Category category: categories) {
            if (category.getName().equals(oldName))
                category.setName(newName);
        }
        throw new IllegalArgumentException("No category with name: " + oldName + " was found in the system.");
    }

    /*
    when the category is removed all its sub categories move to the parent category.
     */
    public void removeCategory(String categoryName) {

    }


    //-------------------------------------------------------------------------

    public void addItemSale(String saleName, int itemID, double saleDiscount, Date startDate, Date endDate) {

    }

    public void addCategorySale(String saleName,String categoryName, double saleDiscount, Date startDate, Date endDate) {

    }

    public void modifySaleName(String oldName, String newName) {
        for (Sale sale : sales) {
            if (sale.getName().equals(oldName))
                sale.setName(newName);
        }
        throw new IllegalArgumentException("No sale with name: " + oldName + " was found in the system.");
    }

    public void modifySaleDiscount(String saleName, double newDiscount) {
        for (Sale sale : sales) {
            if (sale.getName().equals(saleName))
                sale.setDiscount(newDiscount);
        }
        throw new IllegalArgumentException("No sale with name: " + saleName + " was found in the system.");
    }

    public void modifySaleDates(String saleName, Date startDate, Date endDate) {
        for (Sale sale : sales) {
            if (sale.getName().equals(saleName))
                sale.setSaleDates(startDate, endDate);
        }
        throw new IllegalArgumentException("No sale with name: " + saleName + " was found in the system.");
    }


    //-------------------------------------------------------------------------

    public void addItemDiscount(String Supplier, double discount, Date discountDate, int itemCount, int itemId) {

    }

    public void addCategoryDiscount(String Supplier, double discount, Date discountDate, int itemCount, String categoryName) {

    }


    //-------------------------------------------------------------------------

    public void recordDefect(int itemId, String itemName, int defectQuantity, String defectLocation) {

    }


    //-------------------------------------------------------------------------

    public List<Item> inventoryReport() {
        return null;
    }

    public List<Item> itemShortageReport() {
        return null;
    }

    public List<DefectEntry> defectsReport(Date fromDate, Date toDate) {
        return null;
    }

    public List<Item> categoryReport(Date fromDate, Date toDate) {
        return null;
    }
}
