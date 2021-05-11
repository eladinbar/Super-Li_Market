package BusinessLayer.SupliersPackage.supplierPackage;

import java.util.*;

public class SupplierController {
    private Map<String, Supplier> suppliers;
    private Map<String, PersonCard> persons;

    public SupplierController() {
        this.suppliers = new HashMap<>();
        this.persons = new HashMap<>();
    }

    public Map<String, Supplier> getSuppliers() {
        return suppliers;
    }

    public Map<String, PersonCard> getPersons() {
        return persons;
    }

    //checks if the supplier exists in the system
    private void existSupplier(String id) throws Exception {
        SupplierCard supplierCard = new SupplierCard(id);
        if (!suppliers.containsKey(id) && !supplierCard.find())
            throw new Exception("system does not have user with the id :" + id);
    }

    //method that add new supplier to the system
    public Supplier addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment,String address) throws Exception {
        if (suppliers.containsKey(id)) //case that the supplier already exists
            throw new Exception("supplier with the id : " + id + " already exists");
        emailCheck(email);
        phoneCheck(phone);
        Supplier newSup = new Supplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment,address);
        suppliers.put(id, newSup);
        if (persons.containsKey(id))
            persons.remove(id);
        persons.put(id, newSup.getSc());
        return newSup;
    }

    public void removeSupplier(String id) throws Exception {
        existSupplier(id);
        new SupplierCard(id).delete();
        suppliers.remove(id);
    }

    public void updateCompanySupplier(String id, int companyNumber) throws Exception {
        existSupplier(id);
        Supplier supplier = getSupplier(id);
        supplier.getSc().setCompanyNumber(companyNumber);
    }

    public void updateFirstName(String id, String firstName) throws Exception {
        existSupplier(id);
        Supplier supplier = getSupplier(id);
        supplier.getSc().setFirstName(firstName);
    }

    public void updateLastName(String id, String lastName) throws Exception {
        existSupplier(id);
        Supplier supplier = getSupplier(id);
        supplier.getSc().setLastName(lastName);
    }

    public void updatePhone(String id, String phone) throws Exception {
        existSupplier(id);
        Supplier supplier = getSupplier(id);
        phoneCheck(phone);
        supplier.getSc().setPhone(phone);
    }

    public void updateEmail(String id, String email) throws Exception {
        existSupplier(id);
        Supplier supplier = getSupplier(id);
        emailCheck(email);
        supplier.getSc().setEmail(email);
    }

    public void updateSelfDelivery(String id, boolean self) throws Exception {
        existSupplier(id);
        Supplier supplier = getSupplier(id);
        supplier.getSc().setSelfDelivery(self);
    }

    public void updatePernamentDays(String id, boolean perm) throws Exception {
        existSupplier(id);
        Supplier supplier = getSupplier(id);
        supplier.getSc().setPernamentDays(perm);
    }

    public void updatePayment(String id, String pay) throws Exception {
        existSupplier(id);
        Supplier supplier = getSupplier(id);
        Payment enumPay = supplier.paymentCheck(pay);
        supplier.getSc().setPayment(enumPay);
    }

    public void addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone) throws Exception {
        existSupplier(supplierId);
        if (!persons.containsKey(memberID)) {
            PersonCard newPerson = getSupplier(supplierId).createPersonCard(firstName, lastName, email, memberID, phone);
            persons.put(memberID, newPerson);
        }
        getSupplier(supplierId).addContactMember(memberID);
    }

    public void deleteContactMember(String supplierID, String memberID) throws Exception {
        existSupplier(supplierID);
        getSupplier(supplierID).deleteContactMember(memberID);
    }

    public Supplier getSupplier(String id) throws Exception {
        existSupplier(id);
        Supplier toReturn = suppliers.get(id);
        if (toReturn==null) {
           toReturn = new Supplier(id);
           suppliers.put(id,toReturn);
        }
        return toReturn;
    }

    public QuantityList addQuantityList(String supplierID) throws Exception {
        existSupplier(supplierID);
        return getSupplier(supplierID).addQuantityList();
    }

    public void editQuantityListAmount(String supplierID, int productID, int amount) throws Exception {
        existSupplier(supplierID);
        getSupplier(supplierID).editQuantityListAmount(productID, amount);
    }

    public void editQuantityListDiscount(String supplierID, int productID, int discount) throws Exception {
        existSupplier(supplierID);
        getSupplier(supplierID).editQuantityListDiscount(productID, discount);
    }

    public void deleteQuantityList(String supplierID) throws Exception {
        existSupplier(supplierID);
        getSupplier(supplierID).deleteQuantityList();
    }

    public void addQuantityListItem(String supplierID, int productID, int amount, int discount) throws Exception {
        existSupplier(supplierID);
        getSupplier(supplierID).addQuantityListItem(productID, amount, discount, supplierID);
    }

    public void deleteQuantityListItem(String supplierID, int productID) throws Exception {
        existSupplier(supplierID);
        getSupplier(supplierID).deleteQuantityListItem(productID);
    }

    public QuantityList getQuantityList(String supplierId) throws Exception {
        existSupplier(supplierId);
        return getSupplier(supplierId).getQuantityList();
    }

    public void addItemToAgreement(String id, int productID, int companyProductID, int price) throws Exception {
        existSupplier(id);
        getSupplier(id).addItemToAgreement(productID, companyProductID, price);
    }

    public void removeItemFromAgreement(String supplierId, int productId) throws Exception {
        existSupplier(supplierId);
        getSupplier(supplierId).removeItemFromAgreement(productId);

    }

    public void editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID) throws Exception {
        existSupplier(supplierID);
        getSupplier(supplierID).getAg().editAgreementItemCompanyProductID(productID, companyProductID);
    }

    public void editAgreementItemPrice(String supplierID, int productID, int price) throws Exception {
        existSupplier(supplierID);
        getSupplier(supplierID).getAg().editAgreementItemPrice(productID, price);
    }

    public Agreement getAgreement(String supplierID) throws Exception {
        existSupplier(supplierID);
        return getSupplier(supplierID).getAg();
    }

    public double getPrice(String supplierID, int amount, int productID) throws Exception {
        existSupplier((supplierID));
        return getSupplier(supplierID).getPrice(amount, productID);
    }

    public Supplier getCheapestSupplier(int productID, int amount, boolean scheduled) throws Exception {
        double cheapestPrice = Double.POSITIVE_INFINITY;
        Supplier cheapestSup = null;
        for (Supplier sup : suppliers.values()) {
            try {
                if (!scheduled || sup.getSc().isPernamentDays()) {
                    if (sup.getPrice(amount, productID) < cheapestPrice) {
                        cheapestPrice = sup.getPrice(amount, productID);
                        cheapestSup = sup;
                    }
                }
            } catch (Exception ignored) {
            }
        }
        if (cheapestSup == null)
            throw new Exception("There is no supplier that supply the product: " + productID);
        return cheapestSup;
    }

    public Double getProductDiscount(String supplierID, int amount, int productID) throws Exception {
        existSupplier(supplierID);
        return getSupplier(supplierID).getProductDiscount(amount, productID);
    }

    public Integer getSupplierCompanyProductID(String supplierID, int productID) throws Exception {
        existSupplier(supplierID);
        return getSupplier(supplierID).getSupplierCompanyProductID(productID);
    }

    private void phoneCheck(String phone) throws Exception {
        for (Supplier s : suppliers.values()) {
            if (s.getSc().getEmail().equals(phone))
                throw new Exception("phone already exists in the system");
        }
    }

    private void emailCheck(String email) throws Exception {
        for (Supplier s : suppliers.values()) {
            if (s.getSc().getEmail().equals(email))
                throw new Exception("email already exists in the system");
        }
    }
}
