package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Category;

import java.time.LocalDate;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CategoryDiscount extends Discount{
    private DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount dalCopyCategoryDiscount;

    Category category;

    public CategoryDiscount(int supplierID, double discount, Calendar date, int itemCount, Category category) {
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
        Calendar date = getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(date.getTime());

        dalCopyCategoryDiscount = new DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount(formattedDate, supplierID, category.getName(), discount, itemCount);
        dalCopyCategoryDiscount.save();
    }
}
