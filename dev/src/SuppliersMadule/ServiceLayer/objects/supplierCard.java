package SuppliersMadule.ServiceLayer.objects;

import SuppliersMadule.BusinessLayer.supplierPackage.SupplierCard;

import java.util.List;

public class supplierCard extends PersonCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private String payment;
    private List<String> contactMembers;

    public supplierCard(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment, List<String> contactMembers) {
        super(firstName, lastName, email, id, phone);
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = contactMembers;
    }

    public supplierCard(SupplierCard sc) {
        super(sc);
        this.companyNumber = sc.getCompanyNumber();
        this.isPernamentDays = sc.isPernamentDays();
        this.selfDelivery = sc.isSelfDelivery();
        this.payment = sc.getPayment().name();
        this.contactMembers = sc.getContactMembers();
    }

    @Override
    public String toString() {
        return "\nsupplier details:" +
                "\nfirstName: " + firstName +
                "\nlastName: " + lastName +
                "\nemail: " + email +
                "\nid: " + id +
                "\nphone: " + phone +
                "\ncompanyNumber: " + companyNumber +
                "\nisPernamentDays: " + isPernamentDays +
                "\nselfDelivery:" + selfDelivery +
                "\npayment: " + payment +
                "\ncontactMembers: " + contactMembers + "\n\n";
    }
}
