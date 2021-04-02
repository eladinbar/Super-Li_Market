package InventoryModule.BusinessLayer;

import java.util.ArrayList;
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


    public Item(int ID, String name, double costPrice, double sellingPrice, int minAmount, int manufacturerID, Quantity quantity, Location location) {
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.supplierIDs = new ArrayList<>();
        this.quantity = quantity;
        this.location = location;
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

    public Quantity getQuantity() {
        return quantity;
    }

    //need to implement different quantity setters
    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Location getLocation() {
        return location;
    }

    //need to implement appropriate location setter
    public void setLocation(Location location) {
        this.location = location;
    }
}