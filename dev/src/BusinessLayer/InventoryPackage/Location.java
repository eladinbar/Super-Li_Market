package BusinessLayer.InventoryPackage;

public class Location {
    private String shelfLocation;
    private String storageLocation;

    public Location() {}

    public Location(String shelfLocation, String storageLocation) {
        this.shelfLocation = shelfLocation;
        this.storageLocation = storageLocation;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(String storageLocation) {
        this.storageLocation = storageLocation;
    }
}
