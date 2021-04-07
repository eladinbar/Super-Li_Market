package InventoryModule.BusinessLayer;

import java.util.List;

public class Item {
    private int ID;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int minAmount;
    private int manufacturerID;
    private List<Integer> supplierIDs;
    private Quantity quantity;
    private Location location;

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


    public int getMinAmount() {
        return minAmount;
    }


    public int getManufacturerID() {
        return manufacturerID;
    }


    public List<Integer> getSupplierIDs() {
        return supplierIDs;
    }


    public Quantity getQuantity() {
        return quantity;
    }

    public int getShelfQuantity() {
        return quantity.getShelfQuantity();
    }

    public int getStorageQuantity() {
        return quantity.getStorageQuantity();
    }

    public Location getLocation() {
        return location;
    }

    public String getShelfLocation() {
        return location.getShelfLocation();
    }

    public String getStorageLocation() {
        return location.getStorageLocation();
    }
}
