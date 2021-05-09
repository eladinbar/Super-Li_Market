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
    private String supplierID;
    private int itemID;
    private double discount;
    private int itemCount;

    public ItemDiscount(String discountDate, String supplierID, int itemID, double discount, int itemCount) throws SQLException {
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

    public int getItemID() {
        return itemID;
    }

    public void setDiscountDate(String discountDate) throws SQLException {
        String oldDiscountDate = this.discountDate;
        this.discountDate = discountDate;
        controller.update(this, oldDiscountDate);
    }

    public void setSupplierID(String supplierID) throws SQLException {
        String oldSupplierId = this.supplierID;
        this.supplierID = supplierID;
        controller.update(this, oldSupplierId);
    }

    public void setItemID(int itemID) throws SQLException {
        int oldId = this.itemID;
        this.itemID = itemID;
        controller.update(this, oldId);
    }
}
