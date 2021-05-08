package BusinessLayer.InventoryPackage.DiscountPackage;

import java.time.LocalDate;

public abstract class Discount {
    protected String supplierID;
    protected double discount;
    protected LocalDate date;
    protected int itemCount;

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

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getItemCount() {
        return itemCount;
    }
}
