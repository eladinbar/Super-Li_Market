package SerciveLayer.objects;

import InfrastructurePackage.TextFormatter;
import SerciveLayer.SimpleObjects.SimpleEntity;

import java.lang.reflect.Field;

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
        TextFormatter tf = new TextFormatter();
        return String.format("%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\n",tf.centerString(""+productID,20),tf.centerString(""+name,20),tf.centerString(""+amount,20),tf.centerString(""+sellingPrice,20),tf.centerString(""+discount,20),tf.centerString(""+finalPrice,20));
    }
}
