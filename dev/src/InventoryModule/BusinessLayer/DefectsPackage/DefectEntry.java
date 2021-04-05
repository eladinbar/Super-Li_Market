package InventoryModule.BusinessLayer.DefectsPackage;

import InventoryModule.BusinessLayer.Location;

import java.util.Date;

public class DefectEntry {
    private int itemID;
    private String itemName;
    private Date entryDate;
    private int quantity;
    private Location location; //can only be one - shelf or storage

    public DefectEntry(int itemID, String itemName, Date entryDate, int quantity, String location) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.entryDate = entryDate;
        this.quantity = quantity;
        if (location.contains("SH"))
            this.location = new Location(location, null);
        else //if (location.contains("ST")
            this.location = new Location(null, location);
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location.getShelfLocation() != null ? location.getShelfLocation() : location.getStorageLocation();
    }

    public void setLocation(String location) {
        if (this.location.getShelfLocation() != null)
            this.location.setShelfLocation(location);
        else //if (this.location.getStorageLocation() != null)
            this.location.setStorageLocation(location);
    }
}
