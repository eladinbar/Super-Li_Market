package ServiceLayer.objects;

public class supplier {
    private supplierCard sc;
    private agreement ag;

    public supplier(supplierCard sc, agreement ag) throws Exception {
        this.sc = sc;
        this.ag = ag;
    }

    public supplier(BusinessLayer.supplierPackage.supplier supplier) {
        this.sc = new supplierCard(supplier.getSc());
        this.ag = new agreement(supplier.getAg());
    }

    @Override
    public String toString() {
        return sc.toString();
    }
    protected String getName(){
        return sc.firstName+" "+sc.lastName;
    }
}
