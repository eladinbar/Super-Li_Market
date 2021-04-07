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

    public int getStorageQuantity() {
        return storageQuantity;
    }
}