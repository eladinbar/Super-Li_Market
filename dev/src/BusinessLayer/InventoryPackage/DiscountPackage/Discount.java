package BusinessLayer.InventoryPackage.DiscountPackage;

import java.time.LocalDate;
import java.util.Calendar;

public abstract class Discount {
    protected String supplierID;
    protected double discount;
    protected LocalDate date;
    protected int itemCount;

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
