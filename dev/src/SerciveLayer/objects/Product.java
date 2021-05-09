package SerciveLayer.objects;

import SerciveLayer.SimpleObjects.SimpleEntity;

public class Product extends SimpleEntity {
    private int productID;
    private String name;
    private int amount;
    private double sellingPrice;
    private double discount;
    private double finalPrice;

    public Product(int productID, String name, int amount, double sellingPrice, double discount) {
        this.productID = productID;
        this.name = name;
        this.amount = amount;
        this.sellingPrice = sellingPrice;
        this.discount = discount;
        this.finalPrice = sellingPrice*amount*(1-discount/100);
    }

    public int getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    @Override
    public String toString() {
        return "product: " +
                "\nname: " + name  +
                "\nproductID: " + productID +
                "\nmanufacturer: "  +"\n\n";
    }
}
