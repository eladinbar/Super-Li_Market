package BusinessLayer.InventoryPackage.SalePackage;

import BusinessLayer.InventoryPackage.Category;

import java.time.LocalDate;
import java.sql.SQLException;

public class CategorySale extends Sale{
    private DataAccessLayer.DalObjects.InventoryObjects.CategorySale dalCopyCategorySale;

    private Category category;

    public CategorySale(String name) {
        super(name);
    }

    public CategorySale(String name, double discount, LocalDate startDate, LocalDate endDate, Category category) {
        super(name, discount, startDate, endDate);
        this.category = category;
    }

    @Override
    public void setName(String name) {
        dalCopyCategorySale.setName(name);
        this.name = name;
    }

    @Override
    public void setAndSaveName(String name) {
        dalCopyCategorySale.setAndSaveName(name);
        this.name = name;
    }

    @Override
    public void setDiscount(double discount) {
        dalCopyCategorySale.setDiscount(discount);
        this.discount = discount;
    }

    @Override
    public void setAndSaveDiscount(double discount) {
        dalCopyCategorySale.setDiscount(discount);
        try {
            dalCopyCategorySale.update();
            this.discount = discount;
        } catch (SQLException ex) {
            dalCopyCategorySale.setDiscount(this.discount);
            throw new RuntimeException("Something went wrong.");
        }
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        dalCopyCategorySale.setStartSaleDate(startDate.toString());
        this.saleDates.setFirst(startDate);
    }

    @Override
    public void setAndSaveStartDate(LocalDate startDate) {
        dalCopyCategorySale.setStartSaleDate(startDate.toString());
        try {
            dalCopyCategorySale.update();
            this.saleDates.setFirst(startDate);
        } catch (SQLException ex) {
            dalCopyCategorySale.setStartSaleDate(this.saleDates.getFirst().toString());
            throw new RuntimeException("Something went wrong.");
        }
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        dalCopyCategorySale.setEndSaleDate(endDate.toString());
        this.saleDates.setSecond(endDate);
    }

    @Override
    public void setAndSaveEndDate(LocalDate endDate) {
        dalCopyCategorySale.setEndSaleDate(endDate.toString());
        try {
            dalCopyCategorySale.update();
            this.saleDates.setSecond(endDate);
        } catch (SQLException ex) {
            dalCopyCategorySale.setEndSaleDate(this.saleDates.getSecond().toString());
            throw new RuntimeException("Something went wrong.");
        }
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        dalCopyCategorySale.setCategoryName(category.getName());
        this.category = category;
    }

    public void setAndSaveCategory(Category category) {
        dalCopyCategorySale.setCategoryName(category.getName());
        try {
            dalCopyCategorySale.update();
            this.category = category;
        } catch (SQLException ex) {
            dalCopyCategorySale.setCategoryName(this.category.getName());
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void save() {
        try {
            dalCopyCategorySale = new DataAccessLayer.DalObjects.InventoryObjects.CategorySale(name, discount,
                    saleDates.getFirst().toString(), saleDates.getSecond().toString(), category.getName());
            dalCopyCategorySale.save();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void update() throws SQLException {
        dalCopyCategorySale.update();
    }

    public void delete() {
        try {
            dalCopyCategorySale.delete();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public boolean find() {
        boolean found;
        try {
            dalCopyCategorySale = new DataAccessLayer.DalObjects.InventoryObjects.CategorySale(name);

            found = dalCopyCategorySale.find(); //Retrieves DAL Category Sale from the database
            //Set the fields according to the retrieved data
            if (found) {
                this.name = dalCopyCategorySale.getName();
                this.discount = dalCopyCategorySale.getDiscount();
                this.setStartDate(LocalDate.parse(dalCopyCategorySale.getStartSaleDate()));
                this.setEndDate(LocalDate.parse(dalCopyCategorySale.getEndSaleDate()));
                this.setCategory(new Category(dalCopyCategorySale.getCategoryName())); //Create new Category with primary key to be replaced
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }
}
