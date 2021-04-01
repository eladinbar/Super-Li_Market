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
    private int supplyTime;
    private Quantity quantity;
    private Location location;


    public Item(int ID, String name, double costPrice, double sellingPrice, int minAmount, int manufacturerID, int supplyTime, Quantity quantity, Location location) {
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.supplierIDs = new ArrayList<>();
        this.supplyTime = supplyTime;
        this.quantity = quantity;
        this.location = location;
    }
}
