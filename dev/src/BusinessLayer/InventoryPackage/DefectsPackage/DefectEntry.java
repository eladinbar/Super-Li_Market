package BusinessLayer.InventoryPackage.DefectsPackage;

import BusinessLayer.InventoryPackage.Location;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefectEntry {
    private DataAccessLayer.DalObjects.InventoryObjects.DefectEntry dalCopyDefectEntry;

    private int itemID;
    private String itemName;
    private LocalDate entryDate;
    private int quantity;
    private Location location; //can only be one - shelf or storage

    public DefectEntry() {}

    public DefectEntry(int itemID, LocalDate entryDate) {
        this.itemID = itemID;
        this.entryDate = entryDate;
    }

    public DefectEntry(int itemID, String itemName, LocalDate entryDate, int quantity, String location) {
        try {
            dalCopyDefectEntry = new DataAccessLayer.DalObjects.InventoryObjects.DefectEntry();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
        this.itemID = itemID;
        this.itemName = itemName;
        this.entryDate = entryDate;
        this.quantity = quantity;
        if (location.startsWith("SH"))
            this.location = new Location(location, null);
        else //if (location.startsWith("ST")
            this.location = new Location(null, location);
    }

    public DefectEntry(int itemID, String itemName, LocalDate entryDate, int quantity, Location location) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.entryDate = entryDate;
        this.quantity = quantity;
        this.location = location;
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

    public boolean find(List<DefectEntry> defectEntries) {
        boolean found;
        try {
            List<DataAccessLayer.DalObjects.InventoryObjects.DefectEntry> dalCopyDefectEntries = new ArrayList<>();
            dalCopyDefectEntry = new DataAccessLayer.DalObjects.InventoryObjects.DefectEntry();

            found = dalCopyDefectEntry.findAll(dalCopyDefectEntries); //Retrieves all DAL defect entries from the database
            //Set the fields according to the retrieved data
            if (found) {
                for (DataAccessLayer.DalObjects.InventoryObjects.DefectEntry defectEntry : dalCopyDefectEntries) {
                    Location savedLocation;
                    if (defectEntry.getLocation().startsWith("SH"))
                        savedLocation = new Location(defectEntry.getLocation(), null);
                    else //if (dalCopyDefectEntry.getLocation().startsWith("ST")
                        savedLocation = new Location(null, defectEntry.getLocation());
                    DefectEntry savedDefectEntry = new DefectEntry(defectEntry.getItemID(), defectEntry.getItemName(),
                            LocalDate.parse(defectEntry.getEntryDate()), defectEntry.getQuantity(), savedLocation);

                    defectEntries.add(savedDefectEntry);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
        return found;
    }
}
