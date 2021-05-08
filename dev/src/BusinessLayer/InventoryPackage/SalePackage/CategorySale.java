package BusinessLayer.InventoryPackage.SalePackage;

import InfrastructurePackage.Pair;
import BusinessLayer.InventoryPackage.Category;

import java.time.LocalDate;
import java.util.Calendar;

public class CategorySale extends Sale{
    private Category category;

    public CategorySale(String name, double discount, LocalDate startDate, LocalDate endDate, Category category) {
        this.name = name;
        this.discount = discount;
        this.saleDates = new Pair<>(startDate, endDate);
        //Remove redundant time from dates
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
