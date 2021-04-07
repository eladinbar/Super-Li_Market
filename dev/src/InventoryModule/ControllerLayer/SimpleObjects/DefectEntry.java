package InventoryModule.ControllerLayer.SimpleObjects;

import java.util.Calendar;
import java.util.Date;

public class DefectEntry implements SimpleEntity {
    private Calendar entryDate;
    private int itemId;
    private String itemName;
    private int quantity;
    private String location;

    public DefectEntry(Calendar entryDate, int itemId, String itemName, int quantity, String location) {
        this.entryDate = entryDate;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.location = location;
    }

    public Calendar getEntryDate() {
        return entryDate;
    }

    public int getItemId() {
        return itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getLocation() {
        return location;
    }
}
