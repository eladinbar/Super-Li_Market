package InventoryModule.BusinessLayer;

public class Quantity {
    private int totalQuantity;
    private int shelfQuantity;
    private int storageQuantity;

    public Quantity(int shelfQuantity, int storageQuantity) {
        this.shelfQuantity = shelfQuantity;
        this.storageQuantity = storageQuantity;
        this.totalQuantity = shelfQuantity + storageQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getShelfQuantity() {
        return shelfQuantity;
    }

    public void addShelfQuantity(int shelfQuantity) {
        this.shelfQuantity += shelfQuantity;
        this.totalQuantity += shelfQuantity;
    }

    public void removeShelfQuantity(int shelfQuantity) {
        this.shelfQuantity -= shelfQuantity;
        this.totalQuantity -= shelfQuantity;
    }

    public int getStorageQuantity() {
        return storageQuantity;
    }

    public void addStorageQuantity(int storageQuantity) {
        this.storageQuantity += storageQuantity;
        this.totalQuantity += storageQuantity;
    }

    public void removeStorageQuantity(int storageQuantity) {
        this.storageQuantity -= storageQuantity;
        this.totalQuantity -= storageQuantity;
    }
}
