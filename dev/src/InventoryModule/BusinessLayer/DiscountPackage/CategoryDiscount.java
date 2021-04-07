package InventoryModule.BusinessLayer.DiscountPackage;

import InventoryModule.BusinessLayer.Category;

import java.util.Calendar;

public class CategoryDiscount extends Discount{
    Category category;

    public CategoryDiscount(int supplierID, double discount, Calendar date, int itemCount, Category category) {
        this.supplierID = supplierID;
        this.discount = discount;
        this.date = date;
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
