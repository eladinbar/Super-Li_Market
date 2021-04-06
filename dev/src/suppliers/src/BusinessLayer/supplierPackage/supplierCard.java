package BusinessLayer.supplierPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class supplierCard extends personCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private payment payment;
    private List<personCard> contactMembers;

    public supplierCard(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, payment payment) {
        super(firstName, lastName, email, id, phone);
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = new ArrayList<>();
    }
}
