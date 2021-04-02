package InventoryModule.BusinessLayer.SalePackage;

import InfrastructurePackage.Pair;
import InventoryModule.BusinessLayer.Item;

import java.util.Date;

public class ItemSale extends Sale {
    private Item item;

    public ItemSale(double discount, Pair<Date, Date> saleDates, Item item) {
        this.discount = discount;
        this.saleDates = saleDates;
        this.item = item;
    }

    public ItemSale(double discount, Date startDate, Date endDate, Item item) {
        this.discount = discount;
        this.saleDates.setFirst(startDate);
        this.saleDates.setSecond(endDate);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
