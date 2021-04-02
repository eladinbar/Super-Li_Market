package InventoryModule.ControllerLayer.SimpleObjects;

import java.util.List;

public class Item {
    private int ID;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int minAmount;
    private int manufacturerID;
    private List<Integer> supplierIDs;
    private int shelfQuantity;
    private int storageQuantity;
    private String shelfLocation;
    private String storageLocation;

    public Item(int ID, String name, double costPrice, double sellingPrice,
                int minAmount, int manufacturerID, List<Integer> supplierIDs,
                int shelfQuantity, int storageQuantity, String shelfLocation, String storageLocation) {
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.supplierIDs = supplierIDs;
        this.shelfQuantity = shelfQuantity;
        this.storageQuantity = storageQuantity;
        this.shelfLocation = shelfLocation;
        this.storageLocation = storageLocation;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public int getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(int minAmount) {
        this.minAmount = minAmount;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        this.manufacturerID = manufacturerID;
    }

    public List<Integer> getSupplierIDs() {
        return supplierIDs;
    }

    public void setSupplierIDs(List<Integer> supplierIDs) {
        this.supplierIDs = supplierIDs;
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
}