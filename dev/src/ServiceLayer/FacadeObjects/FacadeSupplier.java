package ServiceLayer.FacadeObjects;

public class FacadeSupplier {
    private final FacadeSupplierCard sc;
    private final FacadeAgreement ag;

    public FacadeSupplier(FacadeSupplierCard sc, FacadeAgreement ag) throws Exception {
        this.sc = sc;
        this.ag = ag;
    }

    public FacadeSupplier(BusinessLayer.SuppliersPackage.SupplierPackage.Supplier supplier) {
        this.sc = new FacadeSupplierCard(supplier.getSc());
        this.ag = new FacadeAgreement(supplier.getAg());
    }


    public FacadeSupplierCard getSc() {
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
