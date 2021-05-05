package BusinessLayer.supplierPackage;

import BusinessLayer.orderPackage.product;

import java.util.regex.*;
import java.util.*;

public class supplier {
    private supplierCard sc;
    private agreement ag;
    private final int MaxCompanyNumber = 100;
    private final int MaxNamesLength = 20;
    private final int PhoneLength = 10;
    private final int idLength = 9;

    public supplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) throws Exception {
        payment pay = paymentCheck(payment);
        this.sc = new supplierCard(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, pay);
        this.ag = new agreement();
    }

    public supplierCard getSc() {
        return sc;
    }

    public agreement getAg() {
        return ag;
    }

    //method that checks if payment is legal
    protected payment paymentCheck(String pay) {
        return payment.valueOf(pay);//checks if pay belongs to enum
    }

    protected personCard createPersonCard(String firstName, String lastName, String email, String id, String phone){
        return new personCard(firstName, lastName, email, id, phone);
    }

    public void addContactMember(String memberID) throws Exception {
        if (sc.getContactMembers().contains(memberID)) {
            throw new Exception("contact member with the id " + memberID + " already exists");
        }
        if(memberID.equals(sc.getId()))
            throw new Exception("cannot add supplier to his contact members");
        sc.getContactMembers().add(memberID);
    }

    public void deleteContactMember(String memberID) throws Exception {
        if (!sc.getContactMembers().contains(memberID))
            throw new Exception("contact member with the id " + memberID + " does not exists");
        sc.getContactMembers().remove(memberID);
    }

    public void deleteQuantityList() throws Exception {
        if (ag.getQl() == null)
            throw new Exception("quantityList does not exist");
        ag.setQl(null);
    }

    public void deleteQuantityListItem(int productID) throws Exception {
        ag.deleteQuantityListItem(productID);
    }

    public void addQuantityListItem(int productID, int amount, int discount) throws Exception {
        ag.addQuantityListItem(productID, amount, discount);
    }

    public quantityList addQuantityList() throws Exception {
        return ag.addQuantityList();
    }

    public void editQuantityListAmount(int productID, int amount) throws Exception {
        ag.editQuantityListAmount(productID, amount);
    }

    public void editQuantityListDiscount(int productID, int discount) throws Exception {
        ag.editQuantityListDiscount(productID, discount);
    }

    public quantityList getQuantityList() throws Exception {
        if (ag.getQl()==null)
            throw new Exception("supplier does not have a quantity list");
        return ag.getQl();
    }

    public void addItemToAgreement(int productID, int companyProductID,int price) throws Exception {
        ag.addItemToAgreement(productID,companyProductID,price);
    }

    public void removeItemFromAgreement(int productId) throws Exception {
        ag.removeItemFromAgreement(productId);
    }

    public double getPrice(int amount, int productID) throws Exception {
        return ag.getPrice(amount,productID);
    }

    public Double getProductDiscount(int amount, int productID) throws Exception {
        return ag.getProductDiscount(amount,productID);
    }

    public Integer getSupplierCompanyProductID(int productID) throws Exception {
        return ag.getSupplierCompanyProductID(productID);
    }
}
