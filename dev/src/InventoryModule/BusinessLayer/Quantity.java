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

    public void setShelfQuantity(int shelfQuantity) {
        if (this.shelfQuantity < shelfQuantity)
            totalQuantity += shelfQuantity - this.shelfQuantity;
        else
            totalQuantity -= this.shelfQuantity - shelfQuantity;

        this.shelfQuantity = shelfQuantity;
    }

    public int getStorageQuantity() {
        return storageQuantity;
    }

    public void setStorageQuantity(int storageQuantity) {
        if (this.storageQuantity < storageQuantity)
            totalQuantity += storageQuantity - this.storageQuantity;
        else
            totalQuantity -= this.storageQuantity - storageQuantity;

        this.storageQuantity = storageQuantity;
    }
}
