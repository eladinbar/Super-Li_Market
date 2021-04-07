package InventoryModule.ControllerLayer.SimpleObjects;


public class Item implements SimpleEntity {
    private int ID;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int storeQuantity;
    private int storageQuantity;
    private int totalQuantity;
    private int minAmount;
    private String storeLocation;
    private String storageLocation;
    private int manufacturerID;

    public Item(int ID, String name, double costPrice, double sellingPrice,
                int minAmount, int storeQuantity, int storageQuantity,
                String storeLocation, String storageLocation,int manufacturerID) {
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.storeQuantity = storeQuantity;
        this.storageQuantity = storageQuantity;
        this.totalQuantity = storageQuantity + storeQuantity;
        this.storeLocation = storeLocation;
        this.storageLocation = storageLocation;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getStoreQuantity() {
        return storeQuantity;
    }

    public int getStorageQuantity() {
        return storageQuantity;
    }

    public int getTotalQuantity(){
        return totalQuantity;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public String getStoreLocation() {
        return storeLocation;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public void setStoreQuantity(int storeQuantity) {
        this.storeQuantity = storeQuantity;
        this.totalQuantity = storageQuantity + storeQuantity;
    }

    public void setStorageQuantity(int storageQuantity) {
        this.storageQuantity = storageQuantity;
        this.totalQuantity = storageQuantity + storeQuantity;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public void setStoreLocation(String storeLocation) {
        this.storeLocation = storeLocation;

    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }
}
