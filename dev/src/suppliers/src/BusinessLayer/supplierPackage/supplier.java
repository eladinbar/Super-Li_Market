package BusinessLayer.supplierPackage;

import java.util.List;

public class supplier {
    private supplierCard sc;
    private agreement ag;

    public supplier(String firstName, String lastName, String email, String id, int phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, BusinessLayer.supplierPackage.payment payment) {
        this.sc = new supplierCard(firstName,lastName,email,id,phone,companyNumber,isPernamentDays,selfDelivery,payment);
        this.ag = new agreement();
    }
}
