package BusinessLayer.SupliersPackage.supplierPackage;

import DataAccessLayer.DalObjects.SupplierObjects.SupplierCardDal;

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

    public SupplierCard(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, Payment payment) throws SQLException {
        super(firstName, lastName, email, id, phone);
        this.companyNumber = companyNumber;
        this.isPernamentDays = isPernamentDays;
        this.selfDelivery = selfDelivery;
        this.payment = payment;
        this.contactMembers = new ArrayList<>();
        this.dalObject = toDalObject();
        save();
    }

    public SupplierCard(String supplierId){
        super(null, null, null, supplierId, null);
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

    public void setCompanyNumber(int companyNumber) throws SQLException {
        this.companyNumber = companyNumber;
        dalObject.update();
    }

    public void setPernamentDays(boolean pernamentDays) throws SQLException {
        isPernamentDays = pernamentDays;
        dalObject.update();
    }

    public void setSelfDelivery(boolean selfDelivery) throws SQLException {
        this.selfDelivery = selfDelivery;
        dalObject.update();
    }

    public void setPayment(Payment payment) throws SQLException {
        this.payment = payment;
        dalObject.update();
    }

    public boolean find() throws SQLException {
        SupplierCardDal supplierCardDal = toDalObject();
        return supplierCardDal.find();
    }

    public void save() throws SQLException {
        toDalObject().save();
    }

    public void save(String id,String memberID) throws SQLException {
        toDalObject().save(id,memberID);
    }

    public SupplierCardDal toDalObject() throws SQLException {
        return new SupplierCardDal(Integer.parseInt(id),companyNumber,isPernamentDays,selfDelivery,payment);
    }

    public void delete(String id, String memberID) throws SQLException {
        dalObject.delete(id, memberID);
    }
}
