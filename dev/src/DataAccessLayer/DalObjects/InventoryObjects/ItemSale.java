package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.ItemSaleDalController;
import DataAccessLayer.DalObjects.DalObject;
import InfrastructurePackage.Pair;

import java.sql.SQLException;
import java.util.Calendar;

public class ItemSale extends DalObject<ItemSale> {
    public final String itemSaleNameColumnName = "Name"; //Primary Key
    public final String discountColumnName = "Discount";
    public final String startSaleDateColumnName = "Start_Sale_Date";
    public final String endSaleDateColumnName = "End_Sale_Date";
    public final String itemIdColumnName = "Item_ID"; //Foreign Key

    private String name;
    private double discount;
    private Pair<Calendar, Calendar> saleDates;
    private int itemID;

    protected ItemSale(String name, double discount, Pair<Calendar, Calendar> saleDates, int itemID) throws SQLException {
        super(ItemSaleDalController.getInstance());
        this.name = name;
        this.discount = discount;
        this.saleDates = saleDates;
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

    public Pair<Calendar, Calendar> getSaleDates() {
        return saleDates;
    }

    public void setSaleDates(Pair<Calendar, Calendar> saleDates) {
        this.saleDates = saleDates;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
