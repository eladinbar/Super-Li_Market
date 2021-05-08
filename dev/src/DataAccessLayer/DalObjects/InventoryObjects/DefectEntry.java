package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.DefectEntryDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class DefectEntry extends DalObject<DefectEntry> {
    public static final String entryDateColumnName = "Entry_Date"; //Primary Key
    public static final String itemIdColumnName = "Item_ID"; //Primary Key, Foreign Key
    public static final String locationColumnName = "Location";
    public static final String quantityColumnName = "Quantity";

    private String entryDate;
    private int itemID;
    private String location;
    private int quantity;

    public DefectEntry(String entryDate, int itemID, String location, int quantity) throws SQLException {
        super(DefectEntryDalController.getInstance());
        this.entryDate = entryDate;
        this.location = location;
        this.quantity = quantity;
        this.itemID = itemID;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) throws SQLException {
        this.location = location;
        controller.update(this);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws SQLException {
        this.quantity = quantity;
        controller.update(this);
    }

    public int getItemID() {
        return itemID;
    }
}
