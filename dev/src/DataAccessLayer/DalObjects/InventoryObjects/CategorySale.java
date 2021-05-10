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

    public CategorySale(String name) throws SQLException {
        super(CategorySaleDalController.getInstance());
        this.name = name;
    }

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

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getStartSaleDate() {
        return startSaleDate;
    }

    public void setStartSaleDate(String startSaleDate) {
        this.startSaleDate = startSaleDate;
    }

    public String getEndSaleDate() {
        return endSaleDate;
    }

    public void setEndSaleDate(String endSaleDate) {
        this.endSaleDate = endSaleDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        try {
            controller.update(this, oldName);
        } catch (SQLException ex) {
            this.name = oldName;
            throw new RuntimeException("Something went wrong.");
        }
    }
}
