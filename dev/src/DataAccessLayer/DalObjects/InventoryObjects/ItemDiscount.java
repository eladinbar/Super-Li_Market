package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.ItemDiscountDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class ItemDiscount extends DalObject<ItemDiscount> {
    public static final String discountDateColumnName = "Discount_Date"; //Primary Key
    public static final String supplierIdColumnName = "Supplier_ID"; //Primary Key, Foreign Key
    public static final String itemIdColumnName = "Item_ID"; //Primary Key, Foreign Key
    public static final String discountColumnName = "Discount";
    public static final String itemCountColumnName = "Item_Count";

    private String discountDate;
    private int supplierID;
    private int itemID;
    private double discount;
    private int itemCount;

    public ItemDiscount(String discountDate, int supplierID, int itemID, double discount, int itemCount) throws SQLException {
        super(ItemDiscountDalController.getInstance());
        this.discountDate = discountDate;
        this.supplierID = supplierID;
        this.itemID = itemID;
        this.discount = discount;
        this.itemCount = itemCount;

    }

    public String getDiscountDate() {
        return discountDate;
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

    public int getItemID() {
        return itemID;
    }
}
