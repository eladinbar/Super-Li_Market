package SerciveLayer.Objects;

public class Supplier {
    private SupplierCard sc;
    private Agreement ag;

    public Supplier(SupplierCard sc, Agreement ag) throws Exception {
        this.sc = sc;
        this.ag = ag;
    }

    public Supplier(BusinessLayer.SuppliersPackage.SupplierPackage.Supplier supplier) {
        this.sc = new SupplierCard(supplier.getSc());
        this.ag = new Agreement(supplier.getAg());
    }

    public SupplierCard getSc() {
        return sc;
    }

    @Override
    public String toString() {
        return sc.toString();
    }
    protected String getName(){
        return sc.firstName+" "+sc.lastName;
    }
}
