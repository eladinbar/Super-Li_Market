package ServiceLayer.objects;

public class product {
    private String name;
    private  int productID;
    private manufacturers manu;

    public product(String name, int productID, manufacturers manu) {
        this.name = name;
        this.productID = productID;
        this.manu = manu;
    }
}
