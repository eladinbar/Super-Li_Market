package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.CategoryDiscountDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.util.Calendar;

public class CategoryDiscount extends DalObject<CategoryDiscount> {
    private Calendar discountDate; //Primary Key
    private double discount;
    private int itemCount;
    private int supplierID; //Foreign Key
    private String categoryName; //Foreign Key

    protected CategoryDiscount(Calendar discountDate, double discount, int itemCount, int supplierID, String categoryName) {
        super(new CategoryDiscountDalController());
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
