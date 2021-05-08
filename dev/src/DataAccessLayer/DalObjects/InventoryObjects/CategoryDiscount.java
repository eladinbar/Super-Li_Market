package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.CategoryDiscountDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class CategoryDiscount extends DalObject<CategoryDiscount> {
    public static final String discountDateColumnName = "Discount_Date"; //Primary Key
    public static final String supplierIdColumnName = "Supplier_ID"; //Primary Key, Foreign Key
    public static final String categoryNameColumnName = "Category_Name"; //Primary Key, Foreign Key
    public static final String discountColumnName = "Discount";
    public static final String itemCountColumnName = "Item_Count";

    private String discountDate;
    private double discount;
    private int itemCount;
    private String supplierID;
    private String categoryName;

    public CategoryDiscount(String discountDate, String supplierID, String categoryName, double discount, int itemCount) throws SQLException {
        super(CategoryDiscountDalController.getInstance());
        this.discountDate = discountDate;
        this.supplierID = supplierID;
        this.categoryName = categoryName;
        this.discount = discount;
        this.itemCount = itemCount;
    }

    public String getDiscountDate() {
        return discountDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) throws SQLException {
        this.discount = discount;
        controller.update(this);
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) throws SQLException {
        this.itemCount = itemCount;
        controller.update(this);
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
