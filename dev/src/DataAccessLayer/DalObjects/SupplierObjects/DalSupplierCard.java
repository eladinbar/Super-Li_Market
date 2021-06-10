package DataAccessLayer.DalObjects.SupplierObjects;

import BusinessLayer.SuppliersPackage.SupplierPackage.Payment;
import DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController;
import DataAccessLayer.DalControllers.SupplierControllers.SupplierContactMembersDalController;
import DataAccessLayer.DalObjects.DalObject$;

import java.sql.SQLException;
import java.util.List;

public class DalSupplierCard extends DalObject$<DalSupplierCard> {
    public static final String supplierIdColumnName = "Supplier_ID";
    public static final String companyNumberColumnName = "Company_Number";
    public static final String isPermanentDaysColumnName = "Is_Permanent_Days";
    public static final String selfDeliveryColumnName = "Self_Delivery";
    public static final String paymentColumnName = "Payment";
    public static final String addressColumnName = "Address";

    private int supplierId; //foreign key (personCard)
    private int companyNumber;
    private boolean isPermanentDays;
    private boolean selfDelivery;
    private Payment payment;
    private String address;

    public DalSupplierCard() throws SQLException {
        super(SupplierCardDalController.getInstance());
    }

    public DalSupplierCard(int supplierId, int companyNumber, boolean isPermanentDays, boolean selfDelivery, Payment payment, String address) throws SQLException {
        super(SupplierCardDalController.getInstance());
        this.supplierId = supplierId;
        this.companyNumber = companyNumber;
        this.isPermanentDays = isPermanentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.address = address;
    }

    public DalSupplierCard(int supplierId) throws SQLException {
        super(SupplierCardDalController.getInstance());
        this.supplierId = supplierId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) throws SQLException {
        this.address = address;
        update();
    }

    public void setAddressLoad(String address) {
        this.address = address;
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

    public void setSupplierId(int supplierId) throws SQLException {
        this.supplierId = supplierId;
        update();
    }

    public void setSupplierIdLoad(int supplierId) throws SQLException {
        this.supplierId = supplierId;
    }

    public void setCompanyNumber(int companyNumber) throws SQLException {
        this.companyNumber = companyNumber;
        update();
    }

    public void setCompanyNumberLoad(int companyNumber) throws SQLException {
        this.companyNumber = companyNumber;
    }

    public void setPermanentDays(int permanentDays) throws SQLException {
        if (permanentDays==1)
            this.isPermanentDays=true;
        else
            this.isPermanentDays=false;
        update();
    }

    public void setPermanentDaysLoad(int permanentDays) throws SQLException {
        if (permanentDays==1)
            this.isPermanentDays=true;
        else
            this.isPermanentDays=false;
    }

    public void setPermanentDays(boolean permanentDays) throws SQLException {
        this.isPermanentDays=permanentDays;
        update();
    }

    public void setSelfDelivery(int selfDelivery) throws SQLException {
        if (selfDelivery==1)
            this.selfDelivery=true;
        else
            this.selfDelivery=false;
        update();
    }

    public void setSelfDeliveryLoad(int selfDelivery) throws SQLException {
        if (selfDelivery==1)
            this.selfDelivery=true;
        else
            this.selfDelivery=false;
    }

    public void setSelfDelivery(boolean selfDelivery) throws SQLException {
        this.selfDelivery=selfDelivery;
        update();
    }

    public void setPayment(String payment) throws SQLException {
        this.payment = Payment.valueOf(payment);
        update();
    }

    public void setPaymentLoad(String payment) throws SQLException {
        this.payment = Payment.valueOf(payment);
    }

    public void setPayment(Payment payment) throws SQLException {
        this.payment=payment;
        update();
    }

    public boolean save(String id,String contactMemberID) throws SQLException {
        SupplierContactMembersDalController.getInstance().insert(new DalSupplierContactMembers(Integer.parseInt(id),Integer.parseInt(contactMemberID)));
        return controller.insert(this);
    }

    public boolean delete(String supId, String personId) throws SQLException {
        return SupplierContactMembersDalController.getInstance().delete(new DalSupplierContactMembers(Integer.parseInt(supId), Integer.parseInt(personId)));
    }

    public boolean loadAllSuppliers(List<DalSupplierCard> suppliers) throws SQLException {
        return controller.select(suppliers);
    }
}
