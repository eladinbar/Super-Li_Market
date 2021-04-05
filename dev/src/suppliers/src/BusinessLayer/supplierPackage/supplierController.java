package BusinessLayer.supplierPackage;
import ServiceLayer.objects.payment;
import java.util.*;
import java.util.function.Supplier;

public class supplierController {
    private Map<String,supplier> suppliers;
    private Map<String,personCard> persons;

    public supplierController() {
        this.suppliers = new HashMap<>();
        this.persons = new HashMap<>();
    }
    private void existSupplier(String id) throws Exception {
        if (!suppliers.containsKey(id))
            throw new Exception("system does not have user with the id :" + id);
    }

    public void addSupplier(String firstName, String lastName, String email, String id, int phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, payment payment) throws Exception {
        if(suppliers.containsKey(id))
            throw new Exception("supplier with the id : "+id+ " already exists");
        supplier newSup;
        //todo create supplier and check valid values
    }

    public void removeSupplier(String id) throws Exception {
        existSupplier(id);
        //todo add remove all object connected to supplier
        suppliers.remove(id);
    }

    public void updateCompanySupplier(String id, int companyNumber) throws Exception {
        existSupplier(id);
    }

    public void updateFirstName(String id, String firstName) throws Exception {
        existSupplier(id);
    }

    public void updateLastName(String id, String lastName) throws Exception {
        existSupplier(id);
    }

    public void updatePhone(String id, int phone) throws Exception {
        existSupplier(id);
    }

    public void updateEmail(String id, String email) throws Exception {
        existSupplier(id);
    }

    public void updateSelfDelivery(String id, boolean self) throws Exception {
        existSupplier(id);
    }

    public void updatePernamentDays(String id, boolean perm) throws Exception {
        existSupplier(id);
    }

    public void updatePayment(String id, payment pay) throws Exception {
        existSupplier(id);
    }

    public void addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, int phone) throws Exception {
        existSupplier(supplierId);
    }

    public void deleteContactMember(String supplierID, String memberID) throws Exception {
        existSupplier(supplierID);
    }

    public void getSupplier(String id) throws Exception {
        existSupplier(id);
    }

    public void addQuantityList(String supplierID) throws Exception {
        existSupplier(supplierID);
    }

    public void editQuantityListAmount(String supplierID, int productID, int amount) throws Exception {
        existSupplier(supplierID);
    }

    public void editQuantityListDiscount(String supplierID, int productID, int discount) throws Exception {
        existSupplier(supplierID);
    }

    public void deleteQuantityList(String supplierID) throws Exception {
        existSupplier(supplierID);
    }

    public void addQuantityListItem(String supplierID, int productID, int amount, int discount) throws Exception {
        existSupplier(supplierID);
    }

    public void deleteQuantityListItem(String supplierID, int productID) throws Exception {
        existSupplier(supplierID);
    }
}
