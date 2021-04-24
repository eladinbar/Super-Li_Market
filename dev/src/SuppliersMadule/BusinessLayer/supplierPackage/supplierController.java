package BusinessLayer.supplierPackage;

import BusinessLayer.orderPackage.product;

import java.util.*;

public class supplierController {
    private Map<String, supplier> suppliers;
    private Map<String, personCard> persons;

    public supplierController() {
        this.suppliers = new HashMap<>();
        this.persons = new HashMap<>();
    }

    public Map<String, supplier> getSuppliers() {
        return suppliers;
    }

    public Map<String, personCard> getPersons() {
        return persons;
    }

    //checks if the supplier exists in the system
    private void existSupplier(String id) throws Exception {
        if (!suppliers.containsKey(id))
            throw new Exception("system does not have user with the id :" + id);
    }

    //method that add new supplier to the system
    public supplier addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) throws Exception {
        if (suppliers.containsKey(id)) //case that the supplier allready exists
            throw new Exception("supplier with the id : " + id + " already exists");
        supplier newSup = new supplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment);
        suppliers.put(id, newSup);
        if (persons.containsKey(id))
            persons.remove(id);
        persons.put(id, newSup.getSc());
        return newSup;
    }

    public void removeSupplier(String id) throws Exception {
        existSupplier(id);
        suppliers.remove(id);
    }

    public void updateCompanySupplier(String id, int companyNumber) throws Exception {
        existSupplier(id);
        supplier supplier = suppliers.get(id);
        supplier.companyNumberCheck(companyNumber);
        supplier.getSc().setCompanyNumber(companyNumber);
    }

    public void updateFirstName(String id, String firstName) throws Exception {
        existSupplier(id);
        supplier supplier = suppliers.get(id);
        supplier.NameCheck(firstName);
        supplier.getSc().setFirstName(firstName);
    }

    public void updateLastName(String id, String lastName) throws Exception {
        existSupplier(id);
        supplier supplier = suppliers.get(id);
        supplier.NameCheck(lastName);
        supplier.getSc().setLastName(lastName);
    }

    public void updatePhone(String id, String phone) throws Exception {
        existSupplier(id);
        supplier supplier = suppliers.get(id);
        supplier.phoneCheck(phone);
        supplier.getSc().setPhone(phone);
    }

    public void updateEmail(String id, String email) throws Exception {
        existSupplier(id);
        supplier supplier = suppliers.get(id);
        supplier.emailCheck(email);
        supplier.getSc().setEmail(email);
    }

    public void updateSelfDelivery(String id, boolean self) throws Exception {
        existSupplier(id);
        supplier supplier = suppliers.get(id);
        supplier.getSc().setSelfDelivery(self);
    }

    public void updatePernamentDays(String id, boolean perm) throws Exception {
        existSupplier(id);
        supplier supplier = suppliers.get(id);
        supplier.getSc().setPernamentDays(perm);
    }

    public void updatePayment(String id, String pay) throws Exception {
        existSupplier(id);
        supplier supplier = suppliers.get(id);
        payment enumPay = supplier.paymentCheck(pay);
        supplier.getSc().setPayment(enumPay);
    }

    public void addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone) throws Exception {
        existSupplier(supplierId);
        if (!persons.containsKey(memberID)) {
            personCard newPerson = suppliers.get(supplierId).createPersonCard(firstName, lastName, email, memberID, phone);
            persons.put(memberID, newPerson);
        }
        suppliers.get(supplierId).addContactMember(memberID);
    }

    public void deleteContactMember(String supplierID, String memberID) throws Exception {
        existSupplier(supplierID);
        suppliers.get(supplierID).deleteContactMember(memberID);
    }

    public supplier getSupplier(String id) throws Exception {
        existSupplier(id);
        return suppliers.get(id);
    }

    public quantityList addQuantityList(String supplierID) throws Exception {
        existSupplier(supplierID);
        return suppliers.get(supplierID).addQuantityList();
    }

    public void editQuantityListAmount(String supplierID, int productID, int amount) throws Exception {
        existSupplier(supplierID);
        suppliers.get(supplierID).editQuantityListAmount(productID, amount);
    }

    public void editQuantityListDiscount(String supplierID, int productID, int discount) throws Exception {
        existSupplier(supplierID);
        suppliers.get(supplierID).editQuantityListDiscount(productID, discount);
    }

    public void deleteQuantityList(String supplierID) throws Exception {
        existSupplier(supplierID);
        suppliers.get(supplierID).deleteQuantityList();
    }

    //todo add method to order controller
    public void addQuantityListItem(String supplierID, int productID, int amount, int discount) throws Exception {
        existSupplier(supplierID);
        suppliers.get(supplierID).addQuantityListItem(productID, amount, discount);
    }

    public void deleteQuantityListItem(String supplierID, int productID) throws Exception {
        existSupplier(supplierID);
        suppliers.get(supplierID).deleteQuantityListItem(productID);
    }

    public quantityList getQuantityList(String supplierId) throws Exception {
        existSupplier(supplierId);
        return suppliers.get(supplierId).getQuantityList();
    }

    public void addItemToAgreement(String id, int productID, int companyProductID,int price) throws Exception {
        existSupplier(id);
        suppliers.get(id).addItemToAgreement(productID, companyProductID,price);
    }

    public void removeItemFromAgreement(String supplierId, int productId) throws Exception {
        existSupplier(supplierId);
        suppliers.get(supplierId).removeItemFromAgreement(productId);

    }

    public void editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID) throws Exception {
        existSupplier(supplierID);
        suppliers.get(supplierID).getAg().editAgreementItemCompanyProductID(productID,companyProductID);
    }

    public void editAgreementItemPrice(String supplierID, int productID, int price) throws Exception {
        existSupplier(supplierID);
        suppliers.get(supplierID).getAg().editAgreementItemPrice(productID,price);
    }

    public agreement getAgreement(String supplierID) throws Exception {
        existSupplier(supplierID);
        return suppliers.get(supplierID).getAg();
    }
}
