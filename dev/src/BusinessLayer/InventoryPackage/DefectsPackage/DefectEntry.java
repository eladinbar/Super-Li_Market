package BusinessLayer.InventoryPackage.DefectsPackage;

import BusinessLayer.InventoryPackage.Location;

import java.sql.SQLException;
import java.time.LocalDate;

public class DefectEntry {
    private DataAccessLayer.DalObjects.InventoryObjects.DefectEntry dalCopyDefectEntry;

    private int itemID;
    private String itemName;
    private LocalDate entryDate;
    private int quantity;
    private Location location; //can only be one - shelf or storage

    public DefectEntry(int itemID, String itemName, LocalDate entryDate, int quantity, String location) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.entryDate = entryDate;
        this.quantity = quantity;
        if (location.startsWith("SH"))
            this.location = new Location(location, null);
        else //if (location.startsWith("ST")
            this.location = new Location(null, location);
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public int getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location.getShelfLocation() != null ? location.getShelfLocation() : location.getStorageLocation();
    }

    public void save() throws SQLException {
        dalCopyDefectEntry = new DataAccessLayer.DalObjects.InventoryObjects.DefectEntry(entryDate.toString(), itemID, getLocation(), quantity);
        dalCopyDefectEntry.save();
    }
}
