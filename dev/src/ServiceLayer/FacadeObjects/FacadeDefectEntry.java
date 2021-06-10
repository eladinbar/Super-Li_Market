package ServiceLayer.FacadeObjects;

import java.time.LocalDate;

public class FacadeDefectEntry extends FacadeEntity {
    private LocalDate entryDate;
    private int itemId;
    private String itemName;
    private int quantity;
    private String location;

    public FacadeDefectEntry(LocalDate entryDate, int itemId, String itemName, int quantity, String location) {
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
