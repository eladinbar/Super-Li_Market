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
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) throws SQLException {
        this.itemCount = itemCount;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setDiscountDate(String discountDate) throws SQLException {
        String oldDate = this.discountDate;
        this.discountDate = discountDate;
        try {
            controller.update(this, oldDate);
        } catch (SQLException ex) {
            this.discountDate = oldDate;
            throw ex;
        }
    }

    public void setSupplierID(String supplierID) throws SQLException {
        String oldSupplierId = this.supplierID;
        this.supplierID = supplierID;
        try {
            controller.update(this, oldSupplierId);
        } catch(SQLException ex) {
            this.supplierID = oldSupplierId;
            throw ex;
        }
    }

    public void setCategoryName(String categoryName) throws SQLException {
        String oldCategoryName = this.categoryName;
        this.categoryName = categoryName;
        try {
            controller.update(this, oldCategoryName);
        } catch (SQLException ex) {
            this.categoryName = oldCategoryName;
            throw ex;
        }
    }
}
