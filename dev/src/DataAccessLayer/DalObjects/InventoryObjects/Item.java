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

    public Item() {}

    public Item(String categoryName) {
        this.categoryName = categoryName;
    }

    public Item(int itemID) {
        this.itemID = itemID;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getShelfQuantity() {
        return shelfQuantity;
    }

    public void setShelfQuantity(int shelfQuantity) {
        this.shelfQuantity = shelfQuantity;
    }

    public int getStorageQuantity() {
        return storageQuantity;
    }

    public void setStorageQuantity(int storageQuantity) {
        this.storageQuantity = storageQuantity;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setItemID(int itemID) throws SQLException {
        int oldId = this.itemID;
        this.itemID = itemID;
        try {
            controller.update(this);
        } catch (SQLException ex) {
            this.itemID = oldId;
            throw ex;
        }
    }
}
