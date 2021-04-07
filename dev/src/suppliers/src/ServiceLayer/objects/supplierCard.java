package ServiceLayer.objects;

import java.util.ArrayList;
import java.util.List;

public class supplierCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private payment payment;
    private List<String> contactMembers;

    public supplierCard(int companyNumber, boolean isPernamentDays, boolean selfDelivery, payment payment, List<String> contactMembers) {
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = contactMembers;
    }
}
