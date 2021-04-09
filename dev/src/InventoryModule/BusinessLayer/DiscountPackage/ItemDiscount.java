package InventoryModule.BusinessLayer.DiscountPackage;

import InventoryModule.BusinessLayer.Item;

import java.util.Calendar;

public class ItemDiscount extends Discount{
    private Item item;

    public ItemDiscount(int supplierID, double discount, Calendar date, int itemCount, Item item) {
        this.supplierID = supplierID;
        this.discount = discount;
        this.date = date;
        //Remove redundant time from dates
        date.clear(Calendar.MILLISECOND);
        date.clear(Calendar.SECOND);
        date.clear(Calendar.MINUTE);
        date.clear(Calendar.HOUR);
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
