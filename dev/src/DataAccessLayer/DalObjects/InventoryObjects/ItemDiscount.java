package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.ItemDiscountDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.util.Calendar;

public class ItemDiscount extends DalObject<ItemDiscount> {
    private Calendar discountDate; //Primary Key
    private double discount;
    private int itemCount;
    private int supplierID; //Foreign Key
    private int itemID; //Foreign Key

    protected ItemDiscount(Calendar discountDate, double discount, int itemCount, int supplierID, int itemID) throws SQLException {
        super(ItemDiscountDalController.getInstance());
        this.discountDate = discountDate;
        this.discount = discount;
        this.itemCount = itemCount;
        this.supplierID = supplierID;
        this.itemID = itemID;
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

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
