package ServiceLayer;

import ServiceLayer.Response.Response;
import ServiceLayer.objects.payment;
import ServiceLayer.objects.supplier;

import java.util.Date;

public class Service implements IService {
    private orderService orderService;
    private supplierService supplierService;

    public Service() {
        this.orderService = new orderService();
        this.supplierService = new supplierService();
    }

    @Override
    public Response<supplier> addSupplier(String firstName, String lastName, String email, String id, int phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, payment payment) {
        return supplierService.addSupplier(firstName, lastName, email, id, phone, companyNumber,isPernamentDays,selfDelivery,payment);
    }
    @Override
    public Response<supplier> removeSupplier(String id) {
        return supplierService.removeSupplier(id);
    }

    @Override
    public Response<supplier> updateFirstName(String id, String lirstName) {
        return null;
    }

    @Override
    public Response<supplier> updateLastName(String id, String lastName) {
        return null;
    }

    @Override
    public Response<supplier> updatePhone(String id, int phone) {
        return null;
    }

    @Override
    public Response<supplier> updateEmail(String id, String email) {
        return null;
    }

    @Override
    public Response<supplier> updateCompanyNumber(String id, int companyNumber) {
        return null;
    }

    @Override
    public Response<supplier> updateSelfDelivery(String id, boolean self) {
        return null;
    }

    @Override
    public Response<supplier> updatePernamentDays(String id, boolean perm) {
        return null;
    }

    @Override
    public Response<supplier> updatePayment(String id, payment pay) {
        return null;
    }

    @Override
    public Response<supplier> addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, int phone) {
        return null;
    }

    @Override
    public Response<supplier> deleteContactMember(String supplierID, String memberID) {
        return null;
    }

    @Override
    public Response<supplier> getSupplier(String id) {
        return null;
    }

    @Override
    public Response<supplier> addQuantityList(String supplierID) {
        return null;
    }

    @Override
    public Response<supplier> editQuantityListAmount(String supplierID, int productID, int amount) {
        return null;
    }

    @Override
    public Response<supplier> editQuantityListDiscount(String supplierID, int productID, int discount) {
        return null;
    }

    @Override
    public Response<supplier> deleteQuantityList(String supplierID) {
        return null;
    }

    @Override
    public Response<supplier> addQuantityListItem(String supplierID, int productID, int amount, int discount) {
        return null;
    }

    @Override
    public Response<supplier> deleteQuantityListItem(String supplierID, int productID) {
        return null;
    }

    @Override
    public Response<supplier> createOrder(Date date, String supplierID) {
        return null;
    }

    @Override
    public Response<supplier> createPernamentOrder(int day, String supplierID) {
        return null;
    }

    @Override
    public Response<supplier> approveOrder(int orderID) {
        return null;
    }

    @Override
    public Response<supplier> getOrder(int orderID) {
        return null;
    }

    @Override
    public Response<supplier> addProduct(String name, int productID, String manufacturer) {
        return null;
    }

    @Override
    public Response<supplier> getProduct(int productID) {
        return null;
    }
}
