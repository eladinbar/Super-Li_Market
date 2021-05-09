package SerciveLayer.SimpleObjects;

import java.time.LocalDate;
import java.util.Calendar;

public class DefectEntry extends SimpleEntity {
    private LocalDate entryDate;
    private int itemId;
    private String itemName;
    private int quantity;
    private String location;

    public DefectEntry(LocalDate entryDate, int itemId, String itemName, int quantity, String location) {
        this.entryDate = entryDate;
        this.itemId = itemId;
        this.itemName = itemName;
        this.quantity = quantity;
        this.location = location;
    }

    public LocalDate getEntryDate() {
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
