package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.ItemSaleDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class ItemSale extends DalObject<ItemSale> {
    public static final String itemSaleNameColumnName = "Name"; //Primary Key
    public static final String discountColumnName = "Discount";
    public static final String startSaleDateColumnName = "Start_Sale_Date";
    public static final String endSaleDateColumnName = "End_Sale_Date";
    public static final String itemIdColumnName = "Item_ID"; //Foreign Key

    private String name;
    private double discount;
    private String startSaleDate;
    private String endSaleDate;
    private int itemID;

    public ItemSale(String name, double discount, String startSaleDate, String endSaleDate, int itemID) throws SQLException {
        super(ItemSaleDalController.getInstance());
        this.name = name;
        this.discount = discount;
        this.startSaleDate = startSaleDate;
        this.endSaleDate = endSaleDate;
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
