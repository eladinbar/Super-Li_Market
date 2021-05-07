package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.CategoryDiscountDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.util.Calendar;

public class CategoryDiscount extends DalObject<CategoryDiscount> {
    public final String discountDateColumnName = "Discount_Date"; //Primary Key
    public final String discountColumnName = "Discount";
    public final String itemCountColumnName = "Item_Count";
    public final String supplierIdColumnName = "Supplier_ID"; //Foreign Key
    public final String categoryNameColumnName = "Category_Name"; //Foreign Key

    private Calendar discountDate;
    private double discount;
    private int itemCount;
    private int supplierID;
    private String categoryName;

    protected CategoryDiscount(Calendar discountDate, double discount, int itemCount, int supplierID, String categoryName) throws SQLException {
        super(CategoryDiscountDalController.getInstance());
        this.discountDate = discountDate;
        this.discount = discount;
        this.itemCount = itemCount;
        this.supplierID = supplierID;
        this.categoryName = categoryName;
    }

    public Calendar getDiscountDate() {
        return discountDate;
    }

    public void setDiscountDate(Calendar discountDate) {
        this.discountDate = discountDate;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(int supplierID) {
        this.supplierID = supplierID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
