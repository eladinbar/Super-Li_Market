package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.DefectEntryDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.util.Calendar;

public class DefectEntry extends DalObject<DefectEntry> {
    public final String entryDateColumnName = "Entry_Date"; //Primary Key
    public final String locationColumnName = "Location";
    public final String quantityColumnName = "Quantity";
    public final String itemIdColumnName = "Item_ID"; //Foreign Key

    private Calendar entryDate;
    private String location;
    private int quantity;
    private int itemID;

    protected DefectEntry(Calendar entryDate, String location, int quantity, int itemID) throws SQLException {
        super(DefectEntryDalController.getInstance());
        this.entryDate = entryDate;
        this.location = location;
        this.quantity = quantity;
        this.itemID = itemID;
    }

    public Calendar getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Calendar entryDate) {
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
