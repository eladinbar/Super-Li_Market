package SerciveLayer.objects;

public class Product {
    private String name;
    private int productID;
    private String manu;

    public Product(String name, int productID, String manu) {
        this.name = name;
        this.productID = productID;
        this.manu = manu;
    }
    public Product(BusinessLayer.SupliersPackage.orderPackage.product p){
        this.productID=p.getProductID();
        this.name=p.getName();
        this.manu=p.getManu().name();
    }

    @Override
    public String toString() {
        return "product: " +
                "\nname: " + name  +
                "\nproductID: " + productID +
                "\nmanufacturer: " + manu  +"\n\n";
    }
}
