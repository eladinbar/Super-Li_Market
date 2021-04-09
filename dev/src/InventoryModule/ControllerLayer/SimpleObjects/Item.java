package InventoryModule.ControllerLayer.SimpleObjects;

public class Item implements SimpleEntity {
    private int ID;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int shelfQuantity;
    private int storageQuantity;
    private int totalQuantity;
    private int minAmount;
    private String shelfLocation;
    private String storageLocation;
    private int manufacturerID;

    public Item(int ID, String name, double costPrice, double sellingPrice,
                int minAmount, int shelfQuantity, int storageQuantity,
                String shelfLocation, String storageLocation, int manufacturerID) {
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.shelfQuantity = shelfQuantity;
        this.storageQuantity = storageQuantity;
        this.totalQuantity = storageQuantity + shelfQuantity;
        this.shelfLocation = shelfLocation;
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

    public int getShelfQuantity() {
        return shelfQuantity;
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

    public String getShelfLocation() {
        return shelfLocation;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
