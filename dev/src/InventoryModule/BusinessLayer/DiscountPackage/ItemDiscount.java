package InventoryModule.BusinessLayer.DiscountPackage;

import InventoryModule.BusinessLayer.Item;

import java.util.Date;

public class ItemDiscount extends Discount{
    private Item item;

    public ItemDiscount(int supplierID, double discount, Date date, int itemCount, Item item) {
        this.supplierID = supplierID;
        this.discount = discount;
        this.date = date;
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
