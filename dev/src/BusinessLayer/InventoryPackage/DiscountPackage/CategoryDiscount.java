package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Category;
import BusinessLayer.InventoryPackage.Item;

import java.time.LocalDate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDiscount extends Discount{
    private DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount dalCopyCategoryDiscount;

    Category category;

    public CategoryDiscount() {}

    public CategoryDiscount(String supplierId, LocalDate discountDate) {
        super(supplierId, discountDate);
    }

    public CategoryDiscount(String supplierID, double discount, LocalDate date, int itemCount, Category category) {
        super(supplierID, discount, date, itemCount);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        dalCopyCategoryDiscount.setCategoryName(category.getName());
        this.category = category;
    }

    public void save() {
        try {
            dalCopyCategoryDiscount = new DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount(date.toString(), supplierID, category.getName(), discount, itemCount);
            dalCopyCategoryDiscount.save();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void update() throws SQLException {
        dalCopyCategoryDiscount.update();
    }

    public void delete() {
        try {
            dalCopyCategoryDiscount.delete();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public boolean find() {
        boolean found;
        try {
            dalCopyCategoryDiscount = new DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount(date.toString(), supplierID, category.getName());

            found = dalCopyCategoryDiscount.find(); //Retrieves DAL Category Sale from the database
            //Set the fields according to the retrieved data
            if (found) {
                this.date = LocalDate.parse(dalCopyCategoryDiscount.getDiscountDate());
                this.supplierID = dalCopyCategoryDiscount.getSupplierID();
                this.discount = dalCopyCategoryDiscount.getDiscount();
                this.itemCount = dalCopyCategoryDiscount.getItemCount();
                this.category = new Category(dalCopyCategoryDiscount.getCategoryName()); //Create new Category with primary key to be replaced
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }

    public boolean find(List<CategoryDiscount> categoryDiscounts, String supplierId, String discountDate) {
        boolean found;
        try {
            List<DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount> dalCopyCategoryDiscounts = new ArrayList<>();
            dalCopyCategoryDiscount = new DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount(supplierId, discountDate);

            found = dalCopyCategoryDiscount.find(dalCopyCategoryDiscounts); //Retrieves DAL Items from the database
            //Set the fields according to the retrieved data
            if (found) {
                for (DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount categoryDiscount : dalCopyCategoryDiscounts) {
                    CategoryDiscount savedCategoryDiscount = new CategoryDiscount(categoryDiscount.getSupplierID(), categoryDiscount.getDiscount(),
                            LocalDate.parse(categoryDiscount.getDiscountDate()), categoryDiscount.getItemCount(), new Category(categoryDiscount.getCategoryName()));
                    categoryDiscounts.add(savedCategoryDiscount);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }
}
