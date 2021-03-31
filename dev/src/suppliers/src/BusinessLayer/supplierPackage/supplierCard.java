package BusinessLayer.supplierPackage;

import java.util.List;

enum payment{
    check,
    bankTrasfer,
    cash
}
public class supplierCard extends personCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private payment payment;
    private List<personCard> contactMembers;

    public supplierCard(String firstName, String lastName, String email, String id, int phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, BusinessLayer.supplierPackage.payment payment, List<personCard> contactMembers) {
        super(firstName, lastName, email, id, phone);
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = contactMembers;
    }
}
