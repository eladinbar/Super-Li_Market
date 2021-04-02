package InventoryModule.BusinessLayer.SalePackage;

import InfrastructurePackage.Pair;
import InventoryModule.BusinessLayer.Category;

import java.util.Date;

public class CategorySale extends Sale{
    private Category category;

    public CategorySale(double discount, Pair<Date, Date> saleDates, Category category) {
        this.discount = discount;
        this.saleDates = saleDates;
        this.category = category;
    }

    public CategorySale(double discount, Date startDate, Date endDate, Category category) {
        this.discount = discount;
        this.saleDates.setFirst(startDate);
        this.saleDates.setSecond(endDate);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
