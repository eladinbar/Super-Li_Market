package SerciveLayer.objects;

public class Supplier {
    private supplierCard sc;
    private Agreement ag;

    public Supplier(supplierCard sc, Agreement ag) throws Exception {
        this.sc = sc;
        this.ag = ag;
    }

    public Supplier(BusinessLayer.SupliersPackage.supplierPackage.Supplier supplier) {
        this.sc = new supplierCard(supplier.getSc());
        this.ag = new Agreement(supplier.getAg());
    }

    @Override
    public String toString() {
        return sc.toString();
    }
    protected String getName(){
        return sc.firstName+" "+sc.lastName;
    }
}
