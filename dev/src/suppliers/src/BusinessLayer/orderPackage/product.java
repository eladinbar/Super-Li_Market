package BusinessLayer.orderPackage;

public class product {
    private String name;
    private int productID;
    private manufacturers manu;

    public product(String name, int productID, manufacturers manu) {
        this.name = name;
        this.productID = productID;
        this.manu = manu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public manufacturers getManu() {
        return manu;
    }

    public void setManu(manufacturers manu) {
        this.manu = manu;
    }
}
