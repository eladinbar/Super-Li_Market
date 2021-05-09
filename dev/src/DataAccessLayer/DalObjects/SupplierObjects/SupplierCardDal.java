package DataAccessLayer.DalObjects.SupplierObjects;

import BusinessLayer.SupliersPackage.supplierPackage.Payment;
import DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class SupplierCardDal extends DalObject<SupplierCardDal> {
    public static final String supplierIdColumnName = "Supplier_ID";
    public static final String companyNumberColumnName = "Company_Number";
    public static final String isPermanentDaysColumnName = "Is_Permanent_Days";
    public static final String selfDeliveryColumnName = "Self_Delivery";
    public static final String paymentColumnName = "Payment";

    private int supplierId; //foreign key (personCard)
    private int companyNumber;
    private boolean isPermanentDays;
    private boolean selfDelivery;
    private Payment payment;

    public SupplierCardDal(int supplierId, int companyNumber, boolean isPermanentDays, boolean selfDelivery, Payment payment) throws SQLException {
        super(SupplierCardDalController.getInstance());
        this.supplierId = supplierId;
        this.companyNumber = companyNumber;
        this.isPermanentDays = isPermanentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
    }

    public SupplierCardDal(int supplierId) throws SQLException {
        super(SupplierCardDalController.getInstance());
        this.supplierId = supplierId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public int getCompanyNumber() {
        return companyNumber;
    }

    public int isPermanentDays() {
        if (isPermanentDays)
            return 1;
        return 0;
    }

    public int isSelfDelivery() {
        if (selfDelivery)
            return 1;
        return 0;
    }

    public String getPayment() {
        return payment.toString();
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public void setCompanyNumber(int companyNumber) {
        this.companyNumber = companyNumber;
    }

    public void setPermanentDays(int permanentDays) {
        if (permanentDays==1)
            this.isPermanentDays=true;
        else
            this.isPermanentDays=false;
    }

    public void setSelfDelivery(int selfDelivery) {
        if (selfDelivery==1)
            this.selfDelivery=true;
        else
            this.selfDelivery=false;
    }

    public void setPayment(String payment) {
        this.payment = Payment.valueOf(payment);
    }
}
