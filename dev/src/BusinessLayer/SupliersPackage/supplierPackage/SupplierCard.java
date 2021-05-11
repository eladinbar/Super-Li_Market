package BusinessLayer.SupliersPackage.supplierPackage;

import DataAccessLayer.DalObjects.SupplierObjects.PersonCardDal;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCardDal;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierContactMembersDal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierCard extends PersonCard {
    private int companyNumber;
    private boolean isPernamentDays;
    private boolean selfDelivery;
    private Payment payment;
    private List<String> contactMembers;
    private SupplierCardDal dalObject;
    private String address;

    public SupplierCard(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, Payment payment, String address) throws SQLException {
        super(firstName, lastName, email, id, phone);
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = new ArrayList<>();
        this.address=address;
        this.dalObject = toDalObjectSupplierCard();
        save();
    }

    public SupplierCard(String supplierId) throws SQLException {
        super(null, null, null, supplierId, null);
    }

    public SupplierCard(SupplierCardDal supplierCardDal, PersonCardDal personCardDal) throws SQLException {
        super(personCardDal.getFirstName() , personCardDal.getLastName() , personCardDal.getEmail(), personCardDal.getId(), personCardDal.getPhone());
        this.companyNumber = supplierCardDal.getCompanyNumber();
        this.isPernamentDays = supplierCardDal.isPermanentDays() ==1 ? true : false;
        this.selfDelivery = supplierCardDal.isSelfDelivery() ==1 ? true : false;
        this.payment = Payment.valueOf(supplierCardDal.getPayment());
        readContactMembers();
        this.dalObject = supplierCardDal;
        //todo add address from dal object
    }

    private void readContactMembers() throws SQLException {
        List<SupplierContactMembersDal> contactMemberList = new ArrayList();
        SupplierContactMembersDal contactMemberDal = new SupplierContactMembersDal(Integer.parseInt(id));
        contactMemberDal.find(contactMemberList);
        List<String> cmStrings = new ArrayList<>();
        for (SupplierContactMembersDal cm : contactMemberList) {
            cmStrings.add(""+cm.getPersonId());
        }
        this.contactMembers = cmStrings;
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

    public void setAddress(String address) {
        this.address = address;
        //todo dal set address
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

    public SupplierCardDal toDalObjectSupplierCard() throws SQLException {
        return new SupplierCardDal(Integer.parseInt(id),companyNumber,isPernamentDays,selfDelivery,payment);
    }

    public void delete(String id, String memberID) throws SQLException {
        dalObject.delete(id, memberID);
    }

    public void delete() throws SQLException {
        dalObject.delete();
    }
}
