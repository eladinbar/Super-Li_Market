package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class Item extends DalObject<Item> {
    public static final String itemIdColumnName = "Item_ID"; //Primary Key
    public static final String itemNameColumnName = "Name";
    public static final String costPriceColumnName = "Cost_Price";
    public static final String sellingPriceColumnName = "Selling_Price";
    public static final String manufacturerIdColumnName = "Manufacturer_ID";
    public static final String minAmountColumnName = "Minimum_Amount";
    public static final String shelfQuantityColumnName = "Shelf_Quantity";
    public static final String storageQuantityColumnName = "Storage_Quantity";
    public static final String shelfLocationColumnName = "Shelf_Location";
    public static final String storageLocationColumnName = "Storage_Location";
    public static final String categoryNameColumnName = "Category_Name"; //Foreign Key

    private int itemID;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int manufacturerID;
    private int minAmount;
    private int shelfQuantity;
    private int storageQuantity;
    private String shelfLocation;
    private String storageLocation;
    private String categoryName;

    public Item(int itemID, String name, double costPrice, double sellingPrice, int manufacturerID, int minAmount,
                int shelfQuantity, int storageQuantity, String shelfLocation, String storageLocation, String categoryName) throws SQLException {
        super(ItemDalController.getInstance());
        this.itemID = itemID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.manufacturerID = manufacturerID;
        this.minAmount = minAmount;
        this.shelfQuantity = shelfQuantity;
        this.storageQuantity = storageQuantity;
        this.shelfLocation = shelfLocation;
        this.storageLocation = storageLocation;
        this.categoryName = categoryName;
    }

    public int getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws SQLException {
        this.name = name;
        controller.update(this);
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) throws SQLException {
        this.costPrice = costPrice;
        controller.update(this);
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) throws SQLException {
        this.sellingPrice = sellingPrice;
        controller.update(this);
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) throws SQLException {
        this.manufacturerID = manufacturerID;
        controller.update(this);
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) throws SQLException {
        this.minAmount = minAmount;
        controller.update(this);
    }

    public int getShelfQuantity() {
        return shelfQuantity;
    }

    public void setShelfQuantity(int shelfQuantity) throws SQLException {
        this.shelfQuantity = shelfQuantity;
        controller.update(this);
    }

    public int getStorageQuantity() {
        return storageQuantity;
    }

    public void setStorageQuantity(int storageQuantity) throws SQLException {
        this.storageQuantity = storageQuantity;
        controller.update(this);
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) throws SQLException {
        this.shelfLocation = shelfLocation;
        controller.update(this);
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) throws SQLException {
        this.storageLocation = storageLocation;
        controller.update(this);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) throws SQLException {
        this.categoryName = categoryName;
        controller.update(this);
    }
}
