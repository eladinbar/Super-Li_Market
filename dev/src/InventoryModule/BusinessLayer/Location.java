package InventoryModule.BusinessLayer;

public class Location {
    private String shelfLocation;
    private String storageLocation;

    public Location(String shelfLocation, String storageLocation) {
        this.shelfLocation = shelfLocation;
        this.storageLocation = storageLocation;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public String getStorageLocation() {
        return storageLocation;
    }
}