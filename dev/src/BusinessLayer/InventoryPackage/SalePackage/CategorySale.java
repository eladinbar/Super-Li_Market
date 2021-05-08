package BusinessLayer.InventoryPackage.SalePackage;

import BusinessLayer.InventoryPackage.Category;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CategorySale extends Sale{
    private DataAccessLayer.DalObjects.InventoryObjects.CategorySale dalCopyCategorySale;

    private Category category;

    public CategorySale(String name, double discount, Calendar startDate, Calendar endDate, Category category) {
        super(name, discount, startDate, endDate);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void save() throws SQLException {
        Calendar start = getStartDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStart = format.format(start.getTime());
        Calendar end = getEndDate();
        String formattedEnd = format.format(end.getTime());

        dalCopyCategorySale = new DataAccessLayer.DalObjects.InventoryObjects.CategorySale(name, discount, formattedStart, formattedEnd, category.getName());
        dalCopyCategorySale.save();
    }
}
