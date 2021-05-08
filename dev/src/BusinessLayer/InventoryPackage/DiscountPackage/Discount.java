package BusinessLayer.InventoryPackage.DiscountPackage;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

public abstract class Discount {
    protected String supplierID;
    protected double discount;
    protected LocalDate date;
    protected int itemCount;

    public String getSupplierID() {
    public Discount(int supplierID, double discount, Calendar date, int itemCount) {
        this.supplierID = supplierID;
        this.discount = discount;
        this.date = date;
        //Remove redundant time from dates
        date.clear(Calendar.MILLISECOND);
        date.clear(Calendar.SECOND);
        date.clear(Calendar.MINUTE);
        date.clear(Calendar.HOUR);
        this.itemCount = itemCount;
    }

    public int getSupplierID() {
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
