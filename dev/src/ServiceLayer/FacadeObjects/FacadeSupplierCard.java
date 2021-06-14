package ServiceLayer.FacadeObjects;

import java.util.List;

public class FacadeSupplierCard extends FacadePersonCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private String payment;
    private List<String> contactMembers;
    private String address;
    private int area;

    public FacadeSupplierCard(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment, List<String> contactMembers, String address, int area) {
        super(firstName, lastName, email, id, phone);
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = contactMembers;
        this.address = address;
        this.area = area;
        // TODO need to do area
    }

    public FacadeSupplierCard(BusinessLayer.SuppliersPackage.SupplierPackage.SupplierCard sc) {
        super(sc);
        this.companyNumber = sc.getCompanyNumber();
        this.isPernamentDays = sc.isPernamentDays();
        this.selfDelivery = sc.isSelfDelivery();
        this.payment = sc.getPayment().name();
        this.contactMembers = sc.getContactMembers();
        this.address = sc.getAddress();
        area =  sc.getDeliveryAreaOfSupplier();

    }

    public int getArea() {
        return area;
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

    public int getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(int companyNumber) {
        this.companyNumber = companyNumber;
    }

    public boolean isPernamentDays() {
        return isPernamentDays;
    }

    public void setPernamentDays(boolean pernamentDays) {
        isPernamentDays = pernamentDays;
    }

    public boolean isSelfDelivery() {
        return selfDelivery;
    }

    public void setSelfDelivery(boolean selfDelivery) {
        this.selfDelivery = selfDelivery;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public List<String> getContactMembers() {
        return contactMembers;
    }

    public void setContactMembers(List<String> contactMembers) {
        this.contactMembers = contactMembers;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setArea(int area) {
        this.area = area;
    }
}
