package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.DefectEntryDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class DefectEntry extends DalObject<DefectEntry> {
    public static final String entryDateColumnName = "Entry_Date"; //Primary Key
    public static final String locationColumnName = "Location";
    public static final String quantityColumnName = "Quantity";
    public static final String itemIdColumnName = "Item_ID"; //Foreign Key

    private String entryDate;
    private String location;
    private int quantity;
    private int itemID;

    public DefectEntry(String entryDate, String location, int quantity, int itemID) throws SQLException {
        super(DefectEntryDalController.getInstance());
        this.entryDate = entryDate;
        this.location = location;
        this.quantity = quantity;
        this.itemID = itemID;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
