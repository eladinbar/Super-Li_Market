package InventoryModule.ControllerLayer.SimpleObjects;

import java.util.Calendar;

public class Discount<T extends SimpleEntity> {
    private int supplierID;
    private double discount;
    private Calendar date;
    private int itemCount;

    public Discount(int supplierID, double discount, Calendar date, int itemCount) {
        this.supplierID = supplierID;
        this.discount = discount;
        this.date = date;
        this.itemCount = itemCount;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public double getDiscount() {
        return discount;
    }

    public Calendar getDate() {
        return date;
    }

    public int getItemCount() {
        return itemCount;
    }
}
