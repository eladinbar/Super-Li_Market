package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Category;
import DataAccessLayer.DalObjects.InventoryObjects.DalCategoryDiscount;

import java.time.LocalDate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDiscount extends Discount{
    private DalCategoryDiscount dalCopyCategoryDiscount;

    Category category;

    public CategoryDiscount() {
        super();
        try {
            dalCopyCategoryDiscount = new DalCategoryDiscount();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public CategoryDiscount(String supplierId, LocalDate discountDate) {
        super(supplierId, discountDate);
        try {
            dalCopyCategoryDiscount = new DalCategoryDiscount();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public CategoryDiscount(String supplierID, double discount, LocalDate date, int itemCount, Category category) {
        super(supplierID, discount, date, itemCount);
        try {
            dalCopyCategoryDiscount = new DalCategoryDiscount();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
        this.category = category;
    }

    public void setAndSaveDiscount(double discount) {
        dalCopyCategoryDiscount.setDiscount(discount);
        try {
            dalCopyCategoryDiscount.update();
            this.discount = discount;
        } catch (SQLException ex) {
            dalCopyCategoryDiscount.setDiscount(this.discount);
            throw new RuntimeException("Something went wrong.");
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        dalCopyCategoryDiscount.setCategoryName(category.getName());
        this.category = category;
    }

    public void setAndSaveCategory(Category category) {
        dalCopyCategoryDiscount.setAndSaveCategoryName(category.getName());
        this.category = category;
    }

    public void save() {
        try {
            dalCopyCategoryDiscount = new DalCategoryDiscount(date.toString(), supplierID, category.getName(), discount, itemCount);
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
            dalCopyCategoryDiscount = new DalCategoryDiscount(date.toString(), supplierID, category.getName());

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
            List<DalCategoryDiscount> dalCopyCategoryDiscounts = new ArrayList<>();
            dalCopyCategoryDiscount = new DalCategoryDiscount(supplierId, discountDate);

            found = dalCopyCategoryDiscount.find(dalCopyCategoryDiscounts); //Retrieves DAL Items from the database
            //Set the fields according to the retrieved data
            if (found) {
                for (DalCategoryDiscount categoryDiscount : dalCopyCategoryDiscounts) {
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
