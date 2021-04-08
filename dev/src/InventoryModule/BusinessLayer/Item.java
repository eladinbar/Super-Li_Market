package InventoryModule.BusinessLayer;

import java.util.List;

public class Item {
    private int ID;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int minAmount;
    private int manufacturerID;
    private final List<Integer> supplierIDs;
    private final Quantity quantity;
    private final Location location;

    public Item(int ID, String name, double costPrice, double sellingPrice, int minAmount, int manufacturerID, List<Integer> supplierIDs,
                int shelfQuantity, int storageQuantity, String shelfLocation, String storageLocation) {
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.supplierIDs = supplierIDs;
        this.quantity = new Quantity(shelfQuantity, storageQuantity);
        this.location = new Location(shelfLocation, storageLocation);
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

    public void addSupplier(int supplierID) {
        supplierIDs.add(supplierID);
    }

    public void removeSupplier(int supplierID) {
        supplierIDs.remove(supplierID);
    }

    public int getQuantity() {
        return quantity.getTotalQuantity();
    }

    public int getShelfQuantity() {
        return quantity.getShelfQuantity();
    }

    public int getStorageQuantity() {
        return quantity.getStorageQuantity();
    }

    public void setQuantity(int shelfQuantity, int storageQuantity) {
        this.quantity.setShelfQuantity(shelfQuantity);
        this.quantity.setStorageQuantity(storageQuantity);
    }

    public void setShelfQuantity(int shelfQuantity) {
        this.quantity.setShelfQuantity(shelfQuantity);
    }

    public void setStorageQuantity(int storageQuantity) {
        this.quantity.setStorageQuantity(storageQuantity);
    }

    public void addShelfQuantity(int shelfQuantity) {
        this.quantity.addShelfQuantity(shelfQuantity);
    }

    public void addStorageQuantity(int storageQuantity) {
        this.quantity.addStorageQuantity(storageQuantity);
    }

    public void removeShelfQuantity(int shelfQuantity) {
        this.quantity.removeShelfQuantity(shelfQuantity);
    }

    public void removeStorageQuantity(int storageQuantity) {
        this.quantity.removeStorageQuantity(storageQuantity);
    }

    public String getShelfLocation() {
        return location.getShelfLocation();
    }

    public String getStorageLocation() {
        return location.getStorageLocation();
    }

    public void setLocation(String shelfLocation, String storageLocation) {
        this.location.setShelfLocation(shelfLocation);
        this.location.setStorageLocation(storageLocation);
    }

    public void setShelfLocation(String shelfLocation) {
        this.location.setShelfLocation(shelfLocation);
    }

    public void setStorageLocation(String storageLocation) {
        this.location.setStorageLocation(storageLocation);
    }
}
