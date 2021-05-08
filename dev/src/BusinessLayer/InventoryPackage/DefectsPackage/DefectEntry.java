package BusinessLayer.InventoryPackage.DefectsPackage;

import BusinessLayer.InventoryPackage.Location;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

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
        //Remove redundant time from dates
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
        Calendar date = getEntryDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(date.getTime());

        dalCopyDefectEntry = new DataAccessLayer.DalObjects.InventoryObjects.DefectEntry(formattedDate, itemID, getLocation(), quantity);
        dalCopyDefectEntry.save();
    }
}
