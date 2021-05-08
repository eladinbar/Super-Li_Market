package DataAccessLayer.DalObjects.SupplierObjects;

import BusinessLayer.SupliersPackage.supplierPackage.Payment;
import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.util.List;

public class SupplierCard extends DalObject<SupplierCard> {
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

    public SupplierCard(int supplierId, int companyNumber, boolean isPermanentDays, boolean selfDelivery, Payment payment) throws SQLException {
        super(SupplierCardDalController.getInstance());
        this.supplierId = supplierId;
        this.companyNumber = companyNumber;
        this.isPermanentDays = isPermanentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
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
}
