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

    public ItemDiscount() throws SQLException {
        super(ItemDiscountDalController.getInstance());
    }

    public ItemDiscount(String discountDate, String supplierID) throws SQLException {
        super(ItemDiscountDalController.getInstance());
        this.discountDate = discountDate;
        this.supplierID = supplierID;
    }

    public ItemDiscount(String discountDate, String supplierID, int itemID) throws SQLException {
        super(ItemDiscountDalController.getInstance());
        this.discountDate = discountDate;
        this.supplierID = supplierID;
        this.itemID = itemID;
    }

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

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setDiscountDate(String discountDate) {
        this.discountDate = discountDate;
    }

    public void setAndSaveDiscountDate(String discountDate) {
        String oldDate = this.discountDate;
        this.discountDate = discountDate;
        try {
            controller.update(this, oldDate);
        } catch (SQLException ex) {
            this.discountDate = oldDate;
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public void setAndSaveSupplierID(String supplierID) {
        String oldSupplierId = this.supplierID;
        this.supplierID = supplierID;
        try {
            controller.update(this, oldSupplierId);
        } catch(SQLException ex) {
            this.supplierID = oldSupplierId;
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public void setAndSaveItemID(int itemID) {
        int oldId = this.itemID;
        this.itemID = itemID;
        try {
            controller.update(this, oldId);
        } catch (SQLException ex) {
            this.itemID = oldId;
            throw new RuntimeException("Something went wrong.");
        }
    }
}
