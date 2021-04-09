package InventoryModule.BusinessLayer.SalePackage;

import InfrastructurePackage.Pair;
import InventoryModule.BusinessLayer.Category;

import java.util.Calendar;

public class CategorySale extends Sale{
    private Category category;

    public CategorySale(String name, double discount, Calendar startDate, Calendar endDate, Category category) {
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
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
