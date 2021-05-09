package BusinessLayer.InventoryPackage.SalePackage;

import InfrastructurePackage.Pair;
import BusinessLayer.InventoryPackage.Item;

import java.time.LocalDate;
import java.util.Calendar;

public class ItemSale extends Sale {
    private Item item;

    public ItemSale(String name, double discount, LocalDate startDate, LocalDate endDate, Item item) {
        this.name = name;
        this.discount = discount;
        this.saleDates = new Pair<>(startDate, endDate);
        //Remove redundant time from dates

        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
