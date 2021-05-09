package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Category;

import java.time.LocalDate;
import java.util.Calendar;

public class CategoryDiscount extends Discount{
    Category category;

    public CategoryDiscount(String supplierID, double discount, LocalDate date, int itemCount, Category category) {
        this.supplierID = supplierID;
        this.discount = discount;
        this.date = date;
        //Remove redundant time from dates
        this.itemCount = itemCount;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
