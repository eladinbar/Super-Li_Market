package SerciveLayer.SimpleObjects;

import java.time.LocalDate;
import java.util.Calendar;

public class Discount<T extends SimpleEntity> extends SimpleEntity {
    private String supplierID;
    private double discount;
    private LocalDate date;
    private int itemCount;
    private T appliesOn;

    public Discount(String supplierID, double discount, LocalDate date, int itemCount) {
        this.supplierID = supplierID;
        this.discount = discount;
        this.date = date;
        this.itemCount = itemCount;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public double getDiscount() {
        return discount;
    }

    public LocalDate getDate() {
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
