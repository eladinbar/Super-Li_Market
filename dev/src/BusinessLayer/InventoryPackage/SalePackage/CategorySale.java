package BusinessLayer.InventoryPackage.SalePackage;

import BusinessLayer.InventoryPackage.Category;

import java.time.LocalDate;
import java.sql.SQLException;

public class CategorySale extends Sale{
    private DataAccessLayer.DalObjects.InventoryObjects.CategorySale dalCopyCategorySale;

    private Category category;

    public CategorySale(String name, double discount, LocalDate startDate, LocalDate endDate, Category category) {
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
        dalCopyCategorySale = new DataAccessLayer.DalObjects.InventoryObjects.CategorySale(name, discount,
                saleDates.getFirst().toString(), saleDates.getSecond().toString(), category.getName());
        dalCopyCategorySale.save();
    }
}
