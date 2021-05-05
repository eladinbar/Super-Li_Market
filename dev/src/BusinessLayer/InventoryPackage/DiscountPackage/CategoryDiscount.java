package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Category;

import java.util.Calendar;

public class CategoryDiscount extends Discount{
    Category category;

    public CategoryDiscount(int supplierID, double discount, Calendar date, int itemCount, Category category) {
        this.supplierID = supplierID;
        this.discount = discount;
        this.date = date;
        //Remove redundant time from dates
        date.clear(Calendar.MILLISECOND);
        date.clear(Calendar.SECOND);
        date.clear(Calendar.MINUTE);
        date.clear(Calendar.HOUR);
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
