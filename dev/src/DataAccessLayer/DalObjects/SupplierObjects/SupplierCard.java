package DataAccessLayer.DalObjects.SupplierObjects;

import BusinessLayer.SupliersPackage.supplierPackage.Payment;
import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.util.List;

public class SupplierCard extends DalObject<SupplierCard> {
    public final String supplierIdColumnName = "Supplier_ID";
    public final String companyNumberColumnName = "Company_Number";
    public final String isPermanentDaysColumnName = "Is_Permanent_Days";
    public final String selfDeliveryColumnName = "Self_Delivery";
    public final String paymentColumnName = "Payment";


    private int supplierId; //foreign key of personCard
    private int companyNumber;
    private boolean isPermanentDays;
    private boolean selfDelivery;
    private Payment payment;
    private List<String> contactMembers;

    public SupplierCard(int supplierId, int companyNumber, boolean isPermanentDays, boolean selfDelivery, Payment payment, List<String> contactMembers) throws SQLException {
        super(SupplierCardDalController.getInstance());
        this.supplierId = supplierId;
        this.companyNumber = companyNumber;
        this.isPermanentDays = isPermanentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = contactMembers;
    }
}
