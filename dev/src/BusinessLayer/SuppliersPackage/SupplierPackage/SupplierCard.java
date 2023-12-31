package BusinessLayer.SuppliersPackage.SupplierPackage;

import DataAccessLayer.DalObjects.SupplierObjects.DalPersonCard;
import DataAccessLayer.DalObjects.SupplierObjects.DalSupplierCard;
import DataAccessLayer.DalObjects.SupplierObjects.DalSupplierContactMembers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierCard extends PersonCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private Payment payment;
    private List<String> contactMembers;
    private DalSupplierCard dalObject;
    private String address;
    private int deliveryArea;

    public SupplierCard(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, Payment payment, String address,int deliveryAre) throws SQLException {
        super(firstName, lastName, email, id, phone);
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = new ArrayList<>();
        this.address=address;
        this.dalObject = toDalObjectSupplierCard();
        this.deliveryArea = deliveryAre;
        dalObject.save();
    }

    public SupplierCard(String supplierId) throws SQLException {
        super(supplierId);
        this.dalObject = toDalObjectSupplierCard();
    }

    public SupplierCard(DalSupplierCard supplierCardDal, DalPersonCard personCardDal) throws SQLException {
        super(personCardDal.getFirstName() , personCardDal.getLastName() , personCardDal.getEmail(), personCardDal.getId(), personCardDal.getPhone(), true);
        this.companyNumber = supplierCardDal.getCompanyNumber();
        this.isPernamentDays = supplierCardDal.isPermanentDays() ==1 ? true : false;
        this.selfDelivery = supplierCardDal.isSelfDelivery() ==1 ? true : false;
        this.payment = Payment.valueOf(supplierCardDal.getPayment());
        this.address = supplierCardDal.getAddress();
        this.deliveryArea = supplierCardDal.getDeliveryAreaOfSupplier();
        readContactMembers();
        this.dalObject = supplierCardDal;
    }

    private void readContactMembers() throws SQLException {
        List<DalSupplierContactMembers> contactMemberList = new ArrayList();
        DalSupplierContactMembers contactMemberDal = new DalSupplierContactMembers(Integer.parseInt(id));
        contactMemberDal.find(contactMemberList);
        List<String> cmStrings = new ArrayList<>();
        for (DalSupplierContactMembers cm : contactMemberList) {
            cmStrings.add(""+cm.getPersonId());
        }
        this.contactMembers = cmStrings;
    }

    public int getDeliveryAreaOfSupplier() {
        return deliveryArea;
    }

    public void setDeliveryArea(int deliveryArea) throws SQLException {
        this.deliveryArea = deliveryArea;
        dalObject.setDeliveryArea(deliveryArea);
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

    public String getAddress() {
        return address;
    }

    public void setCompanyNumber(int companyNumber) throws SQLException {
        this.companyNumber = companyNumber;
        dalObject.setCompanyNumber(companyNumber);
    }

    public void setPernamentDays(boolean pernamentDays) throws SQLException {
        isPernamentDays = pernamentDays;
        dalObject.setPermanentDays(pernamentDays);
    }

    public void setSelfDelivery(boolean selfDelivery) throws SQLException {
        this.selfDelivery = selfDelivery;
        dalObject.setSelfDelivery(selfDelivery);
    }

    public void setPayment(Payment payment) throws SQLException {
        this.payment = payment;
        dalObject.setPayment(payment);
    }

    public void setAddress(String address) throws SQLException {
        this.address = address;
        dalObject.setAddress(address);
    }

    public boolean find() throws SQLException {
        return dalObject.find();
    }

    public void save() throws SQLException {
        dalObject.save();
    }

    public void save(String id,String memberID) throws SQLException {
        dalObject.save(id,memberID);
    }

    public DalSupplierCard toDalObjectSupplierCard() throws SQLException {
        return new DalSupplierCard(Integer.parseInt(id),companyNumber,isPernamentDays,selfDelivery,payment, address,deliveryArea);
    }

    public void delete(String id, String memberID) throws SQLException {
        dalObject.delete(id, memberID);
    }

    public void delete() throws SQLException {
        dalObject.delete();
    }
}
