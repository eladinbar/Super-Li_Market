package ServiceLayer.FacadeObjects;

import java.util.List;

public class FacadeSupplierCard extends FacadePersonCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private String payment;
    private List<String> contactMembers;
    private String address;

    public FacadeSupplierCard(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment, List<String> contactMembers, String address) {
        super(firstName, lastName, email, id, phone);
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = contactMembers;
        this.address = address;
    }

    public FacadeSupplierCard(BusinessLayer.SuppliersPackage.SupplierPackage.SupplierCard sc) {
        super(sc);
        this.companyNumber = sc.getCompanyNumber();
        this.isPernamentDays = sc.isPernamentDays();
        this.selfDelivery = sc.isSelfDelivery();
        this.payment = sc.getPayment().name();
        this.contactMembers = sc.getContactMembers();
        this.address = sc.getAddress();
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

    public String getAddress() {
        return address;
    }
}
