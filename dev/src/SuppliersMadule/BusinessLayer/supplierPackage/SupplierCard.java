package SuppliersMadule.BusinessLayer.supplierPackage;

import java.util.ArrayList;
import java.util.List;

public class SupplierCard extends PersonCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private Payment payment;
    private List<String> contactMembers;

    public SupplierCard(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, Payment payment) {
        super(firstName, lastName, email, id, phone);
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = new ArrayList<>();
    }

    public int getCompanyNumber() {
        return companyNumber;
    }

    public boolean isPernamentDays() {
        return isPernamentDays;
    }

    public boolean isSelfDelivery() {
        return selfDelivery;
    }

    public Payment getPayment() {
        return payment;
    }

    public List<String> getContactMembers() {
        return contactMembers;
    }

    public void setCompanyNumber(int companyNumber) {
        this.companyNumber = companyNumber;
    }

    public void setPernamentDays(boolean pernamentDays) {
        isPernamentDays = pernamentDays;
    }

    public void setSelfDelivery(boolean selfDelivery) {
        this.selfDelivery = selfDelivery;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setContactMembers(List<String> contactMembers) {
        this.contactMembers = contactMembers;
    }
}
