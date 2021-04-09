package InventoryModule.BusinessLayer.SalePackage;

import InfrastructurePackage.Pair;
import InventoryModule.BusinessLayer.Item;

import java.util.Calendar;

public class ItemSale extends Sale {
    private Item item;

    public ItemSale(String name, double discount, Pair<Calendar, Calendar> saleDates, Item item) {
        this.name = name;
        this.discount = discount;
        this.saleDates = saleDates;
        this.item = item;
    }

    public ItemSale(String name, double discount, Calendar startDate, Calendar endDate, Item item) {
        this.name = name;
        this.discount = discount;
        this.saleDates = new Pair<>(startDate, endDate);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
