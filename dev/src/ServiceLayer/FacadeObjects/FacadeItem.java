package ServiceLayer.FacadeObjects;

public class FacadeItem extends FacadeEntity {
    private int ID;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int shelfQuantity;
    private int storageQuantity;
    private int totalQuantity;
    private int minAmount;
    private String shelfLocation;
    private String storageLocation;
    private int manufacturerID;
    private String category;
    private int weight;

    public FacadeItem(int ID, String name, double costPrice, double sellingPrice,
                      int minAmount, int shelfQuantity, int storageQuantity,
                      String shelfLocation, String storageLocation, int manufacturerID, int weight) {
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.shelfQuantity = shelfQuantity;
        this.storageQuantity = storageQuantity;
        this.totalQuantity = storageQuantity + shelfQuantity;
        this.shelfLocation = shelfLocation;
        this.storageLocation = storageLocation;
        this.weight = weight;
        this.category = "Uncategorized";
    }

    public FacadeItem(int ID, String name, double costPrice, double sellingPrice,
                      int minAmount, int shelfQuantity, int storageQuantity,
                      String shelfLocation, String storageLocation, int manufacturerID, int weight, String category) {
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.shelfQuantity = shelfQuantity;
        this.storageQuantity = storageQuantity;
        this.totalQuantity = storageQuantity + shelfQuantity;
        this.shelfLocation = shelfLocation;
        this.storageLocation = storageLocation;
        this.weight = weight;
        this.category = category;
    }

    public int getID() {
        return ID;
    }

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public int getShelfQuantity() {
        return shelfQuantity;
    }

    public int getStorageQuantity() {
        return storageQuantity;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public int getMinAmount() {
        return minAmount;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public String getStorageLocation() {
        return storageLocation;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Item details:\n" +
                "ID: " + ID + '\n' +
                "name: " + name + '\n' +
                "costPrice: " + costPrice + '\n' +
                "sellingPrice: " + sellingPrice + '\n' +
                "shelfQuantity: " + shelfQuantity + '\n' +
                "storageQuantity: " + storageQuantity + '\n' +
                "totalQuantity: " + totalQuantity + '\n' +
                "minAmount: " + minAmount + '\n' +
                "shelfLocation: " + shelfLocation + '\n' +
                "storageLocation: " + storageLocation + '\n' +
                "manufacturerID: " + manufacturerID + '\n' +
                "weight: " + weight + '\n' +
                "category: " + category;
    }
}
