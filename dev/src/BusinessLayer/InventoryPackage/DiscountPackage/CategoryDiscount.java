package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Category;

import java.time.LocalDate;
import java.sql.SQLException;

public class CategoryDiscount extends Discount{
    private DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount dalCopyCategoryDiscount;

    Category category;

    public CategoryDiscount(String supplierID, double discount, LocalDate date, int itemCount, Category category) {
        super(supplierID, discount, date, itemCount);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void save() throws SQLException {
        dalCopyCategoryDiscount = new DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount(date.toString(), supplierID, category.getName(), discount, itemCount);
        dalCopyCategoryDiscount.save();
    }
}
