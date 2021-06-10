package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.DefectEntryDalController;
import DataAccessLayer.DalObjects.DalObject$;

import java.sql.SQLException;

public class DalDefectEntry extends DalObject$<DalDefectEntry> {
    public static final String entryDateColumnName = "Entry_Date"; //Primary Key
    public static final String itemIdColumnName = "Item_ID"; //Primary Key, Foreign Key
    public static final String itemNameColumnName = "Item_Name";
    public static final String locationColumnName = "Location";
    public static final String quantityColumnName = "Quantity";

    private String entryDate;
    private int itemID;
    private String itemName;
    private String location;
    private int quantity;

    public DalDefectEntry() throws SQLException {
        super(DefectEntryDalController.getInstance());
    }

    public DalDefectEntry(String entryDate, int itemID) throws SQLException {
        super(DefectEntryDalController.getInstance());
        this.entryDate = entryDate;
        this.itemID = itemID;
    }

    public DalDefectEntry(String entryDate, int itemID, String itemName, String location, int quantity) throws SQLException {
        super(DefectEntryDalController.getInstance());
        this.entryDate = entryDate;
        this.itemID = itemID;
        this.itemName = itemName;
        this.location = location;
        this.quantity = quantity;
    }

    public String getEntryDate() {
        return entryDate;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public void setAndSaveEntryDate(String entryDate) {
        String oldEntryDate = this.entryDate;
        this.entryDate = entryDate;
        try {
            controller.update(this, oldEntryDate);
        } catch (SQLException ex) {
            this.entryDate = entryDate;
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
            this.itemID = itemID;
            throw new RuntimeException("Something went wrong.");
        }
    }
}
