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

    public DefectEntry(int itemID, LocalDate entryDate) {
        this.itemID = itemID;
        this.entryDate = entryDate;
    }

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

    public void save() {
        try {
            dalCopyDefectEntry = new DataAccessLayer.DalObjects.InventoryObjects.DefectEntry(entryDate.toString(), itemID, itemName, getLocation(), quantity);
            dalCopyDefectEntry.save();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void update() throws SQLException {
        dalCopyDefectEntry.update();
    }

    public void delete() {
        try {
            dalCopyDefectEntry.delete();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public boolean find() {
        boolean found;
        try {
            dalCopyDefectEntry = new DataAccessLayer.DalObjects.InventoryObjects.DefectEntry(entryDate.toString(), itemID);

            found = dalCopyDefectEntry.find(); //Retrieves DAL Category Sale from the database
            //Set the fields according to the retrieved data
            if (found) {
                this.itemID = dalCopyDefectEntry.getItemID();
                this.itemName = dalCopyDefectEntry.getItemName();
                this.entryDate = LocalDate.parse(dalCopyDefectEntry.getEntryDate());
                this.quantity = dalCopyDefectEntry.getQuantity();
                if (dalCopyDefectEntry.getLocation().startsWith("SH"))
                this.location = new Location(dalCopyDefectEntry.getLocation(), null);
                else //if (dalCopyDefectEntry.getLocation().startsWith("ST")
                    this.location = new Location(null, dalCopyDefectEntry.getLocation());
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }
}
