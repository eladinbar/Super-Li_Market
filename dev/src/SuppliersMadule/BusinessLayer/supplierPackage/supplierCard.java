package BusinessLayer.supplierPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class supplierCard extends personCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private payment payment;
    private List<String> contactMembers;

    public supplierCard(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, payment payment) {
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

    public BusinessLayer.supplierPackage.payment getPayment() {
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

    public void setPayment(BusinessLayer.supplierPackage.payment payment) {
        this.payment = payment;
    }

    public void setContactMembers(List<String> contactMembers) {
        this.contactMembers = contactMembers;
    }
}
