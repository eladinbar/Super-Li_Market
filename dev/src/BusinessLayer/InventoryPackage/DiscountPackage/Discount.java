package BusinessLayer.InventoryPackage.DiscountPackage;

import java.util.Calendar;

public abstract class Discount {
    protected int supplierID;
    protected double discount;
    protected Calendar date;
    protected int itemCount;

    public int getSupplierID() {
        return supplierID;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Calendar getDate() {
        return date;
    }

    public int getItemCount() {
        return itemCount;
    }
}
