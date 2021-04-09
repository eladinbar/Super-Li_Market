package InventoryModule.BusinessLayer.SalePackage;

import InfrastructurePackage.Pair;
import InventoryModule.BusinessLayer.Item;

import java.util.Calendar;

public class ItemSale extends Sale {
    private Item item;

    public ItemSale(String name, double discount, Calendar startDate, Calendar endDate, Item item) {
        this.name = name;
        this.discount = discount;
        this.saleDates = new Pair<>(startDate, endDate);
        //Remove redundant time from dates
        startDate.clear(Calendar.MILLISECOND);
        startDate.clear(Calendar.SECOND);
        startDate.clear(Calendar.MINUTE);
        startDate.clear(Calendar.HOUR);
        endDate.clear(Calendar.MILLISECOND);
        endDate.clear(Calendar.SECOND);
        endDate.clear(Calendar.MINUTE);
        endDate.clear(Calendar.HOUR);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
