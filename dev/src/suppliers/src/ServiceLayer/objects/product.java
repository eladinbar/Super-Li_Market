package ServiceLayer.objects;

public class product {
    private String name;
    private int productID;
    private String manu;

    public product(String name, int productID, String manu) {
        this.name = name;
        this.productID = productID;
        this.manu = manu;
    }
    public product(BusinessLayer.orderPackage.product p){
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
