package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Item;

import java.time.LocalDate;
import java.util.Calendar;

public class ItemDiscount extends Discount{
    private Item item;

    public ItemDiscount(String supplierID, double discount, LocalDate date, int itemCount, Item item) {
        this.supplierID = supplierID;
        this.discount = discount;
        this.date = date;
        //Remove redundant time from dates
        this.itemCount = itemCount;
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
