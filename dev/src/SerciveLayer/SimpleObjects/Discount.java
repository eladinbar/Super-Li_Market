package SerciveLayer.SimpleObjects;

import java.util.Calendar;

public class Discount<T extends SimpleEntity> extends SimpleEntity {
    private int supplierID;
    private double discount;
    private Calendar date;
    private int itemCount;
    private T appliesOn;

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

    public T getAppliesOn() {
        return appliesOn;
    }

    public void setAppliesOn(T appliesOn) {
        this.appliesOn = appliesOn;
    }
}
