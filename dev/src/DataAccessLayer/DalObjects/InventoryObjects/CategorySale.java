package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.CategorySaleDalController;
import DataAccessLayer.DalObjects.DalObject;
import InfrastructurePackage.Pair;

import java.sql.SQLException;
import java.util.Calendar;

public class CategorySale extends DalObject<CategorySale> {
    public final String categorySaleNameColumnName = "Name"; //Primary Key
    public final String discountColumnName = "Discount";
    public final String startSaleDateColumnName = "Start_Sale_Date";
    public final String endSaleDateColumnName = "End_Sale_Date";
    public final String categoryNameColumnName = "Category_Name"; //Foreign Key

    private String name;
    private double discount;
    private Calendar startSaleDate;
    private Calendar endSaleDate;
    private String categoryName;

    protected CategorySale(String name, double discount, Calendar startSaleDate, Calendar endSaleDate, String categoryName) throws SQLException {
        super(CategorySaleDalController.getInstance());
        this.name = name;
        this.discount = discount;
        this.startSaleDate = startSaleDate;
        this.endSaleDate = endSaleDate;
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Calendar getStartSaleDate() {
        return startSaleDate;
    }

    public void setStartSaleDate(Calendar startSaleDate) {
        this.startSaleDate = startSaleDate;
    }

    public Calendar getEndSaleDate() {
        return endSaleDate;
    }

    public void setEndSaleDate(Calendar endSaleDate) {
        this.endSaleDate = endSaleDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
