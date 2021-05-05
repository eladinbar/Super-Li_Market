package BusinessLayer.InventoryPackage.DefectsPackage;

import BusinessLayer.InventoryPackage.Location;

import java.util.Calendar;

public class DefectEntry {
    private int itemID;
    private String itemName;
    private Calendar entryDate;
    private int quantity;
    private Location location; //can only be one - shelf or storage

    public DefectEntry(int itemID, String itemName, Calendar entryDate, int quantity, String location) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.entryDate = entryDate;
        //Remove redundant time from dates
        entryDate.clear(Calendar.MILLISECOND);
        entryDate.clear(Calendar.SECOND);
        entryDate.clear(Calendar.MINUTE);
        entryDate.clear(Calendar.HOUR);
        this.quantity = quantity;
        if (location.startsWith("SH"))
            this.location = new Location(location, null);
        else //if (location.startsWith("ST")
            this.location = new Location(null, location);
    }

    public Calendar getEntryDate() {
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
}
