package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.CategorySaleDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class CategorySale extends DalObject<CategorySale> {
    public static final String categorySaleNameColumnName = "Name"; //Primary Key
    public static final String discountColumnName = "Discount";
    public static final String startSaleDateColumnName = "Start_Sale_Date";
    public static final String endSaleDateColumnName = "End_Sale_Date";
    public static final String categoryNameColumnName = "Category_Name"; //Foreign Key

    private String name;
    private double discount;
    private String startSaleDate;
    private String endSaleDate;
    private String categoryName;

    public CategorySale(String name, double discount, String startSaleDate, String endSaleDate, String categoryName) throws SQLException {
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

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) throws SQLException {
        this.discount = discount;
        controller.update(this);
    }

    public String getStartSaleDate() {
        return startSaleDate;
    }

    public void setStartSaleDate(String startSaleDate) throws SQLException {
        this.startSaleDate = startSaleDate;
        controller.update(this);
    }

    public String getEndSaleDate() {
        return endSaleDate;
    }

    public void setEndSaleDate(String endSaleDate) throws SQLException {
        this.endSaleDate = endSaleDate;
        controller.update(this);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) throws SQLException {
        this.categoryName = categoryName;
        controller.update(this);
    }
}
