package BusinessLayer.supplierPackage;

public class supplier {
    private int supplierID;
    private supplierCard sc;
    private agreement ag;

    public supplier(int supplierID, supplierCard sc, agreement ag) {
        this.supplierID = supplierID;
        this.sc = sc;
        this.ag = ag;
    }
}
