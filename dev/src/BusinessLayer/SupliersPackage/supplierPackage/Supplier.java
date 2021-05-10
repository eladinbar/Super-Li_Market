package BusinessLayer.SupliersPackage.supplierPackage;

import BusinessLayer.InventoryPackage.Item;

import java.sql.SQLException;

public class Supplier {
    private SupplierCard sc;
    private Agreement ag;
    private final int MaxCompanyNumber = 100;
    private final int MaxNamesLength = 20;
    private final int PhoneLength = 10;
    private final int idLength = 9;

    public Supplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) throws Exception {
        Payment pay = paymentCheck(payment);
        this.sc = new SupplierCard(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, pay);
        this.ag = new Agreement();
    }

    public SupplierCard getSc() {
        return sc;
    }

    public Agreement getAg() {
        return ag;
    }

    //method that checks if payment is legal
    protected Payment paymentCheck(String pay) {
        return Payment.valueOf(pay);//checks if pay belongs to enum
    }

    protected PersonCard createPersonCard(String firstName, String lastName, String email, String id, String phone) throws SQLException {
        return new PersonCard(firstName, lastName, email, id, phone);
    }

    public void addContactMember(String memberID) throws Exception {
        if (sc.getContactMembers().contains(memberID)) {
            throw new Exception("contact member with the id " + memberID + " already exists");
        }
        if(memberID.equals(sc.getId()))
            throw new Exception("cannot add supplier to his contact members");
        sc.getContactMembers().add(memberID);
        sc.save(sc.getId(),memberID); //dal
    }

    public void deleteContactMember(String memberID) throws Exception {
        if (!sc.getContactMembers().contains(memberID))
            throw new Exception("contact member with the id " + memberID + " does not exists");
        sc.getContactMembers().remove(memberID);
        sc.delete(sc.getId(),memberID); //dal
    }

    public void deleteQuantityList() throws Exception {
        if (ag.getQl() == null)
            throw new Exception("quantityList does not exist");
        //delete all products in quantityListItems in dal
        for (Integer item: ag.getQl().getAmount().keySet()) {
            ag.deleteQuantityListItem(item);
        }
        ag.setQl(null);
    }

    public void deleteQuantityListItem(int productID) throws Exception {
        ag.deleteQuantityListItem(productID);
    }

    public void addQuantityListItem(int productID, int amount, int discount, String supplierId) throws Exception {
        ag.addQuantityListItem(productID, amount, discount, supplierId);
    }

    public QuantityList addQuantityList() throws Exception {
        return ag.addQuantityList();
    }

    public void editQuantityListAmount(int productID, int amount) throws Exception {
        ag.editQuantityListAmount(productID, amount);
    }

    public void editQuantityListDiscount(int productID, int discount) throws Exception {
        ag.editQuantityListDiscount(productID, discount);
    }

    public QuantityList getQuantityList() throws Exception {
        if (ag.getQl()==null)
            throw new Exception("supplier does not have a quantity list");
        return ag.getQl();
    }

    public void addItemToAgreement(int productID, int companyProductID,int price) throws Exception {
        ag.addItemToAgreement(productID,companyProductID,price, sc.getId());
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
