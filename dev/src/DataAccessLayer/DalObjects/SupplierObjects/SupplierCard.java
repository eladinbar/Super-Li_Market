package DataAccessLayer.DalObjects.SupplierObjects;

import BusinessLayer.SupliersPackage.supplierPackage.Payment;
import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

import java.util.List;

public class SupplierCard extends DalObject<SupplierCard> {
    private int supplierId; //foreign key of personCard
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private Payment payment;
    private List<String> contactMembers;

    protected SupplierCard(DalController<SupplierCard> controller) {
        super(controller);
    }

    public SupplierCard(DalController<SupplierCard> controller, int supplierId, int companyNumber, boolean isPernamentDays, boolean selfDelivery, Payment payment, List<String> contactMembers) {
        super(controller);
        this.supplierId = supplierId;
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = contactMembers;
    }
}
