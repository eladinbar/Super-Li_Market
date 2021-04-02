package InventoryModule.BusinessLayer.DefectsPackage;

import java.util.Date;

public class DefectEntry {
    private Date entryDate;
    private int itemID;
    private String itemName;
    private int quantity;
    private String location; //can only be one - shelf or storage

    public DefectEntry(Date entryDate, int itemID, String itemName, int quantity, String location) {
        this.entryDate = entryDate;
        this.itemID = itemID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.location = location;
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
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
