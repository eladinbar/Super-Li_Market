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
    public Response<supplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) {
        return supplierService.addSupplier(firstName, lastName, email, id, phone, companyNumber,isPernamentDays,selfDelivery,payment);
    }
    @Override
    public Response<supplier> removeSupplier(String id) {
        orderService.removeSupplier(id);
        return supplierService.removeSupplier(id);
    }

    @Override
    public Response<supplier> updateFirstName(String id, String firstName) {
        return supplierService.updateFirstName(id,firstName);
    }

    @Override
    public Response<supplier> updateLastName(String id, String lastName) {
        return supplierService.updateLastName(id,lastName);
    }

    @Override
    public Response<supplier> updatePhone(String id, String phone) {
        return supplierService.updatePhone(id,phone);
    }

    @Override
    public Response<supplier> updateEmail(String id, String email) {
        return supplierService.updateEmail(id,email);
    }

    @Override
    public Response<supplier> updateCompanyNumber(String id, int companyNumber) {
        return supplierService.updateCompanyNumber(id,companyNumber);
    }

    @Override
    public Response<supplier> updateSelfDelivery(String id, boolean self) {
        return supplierService.updateSelfDelivery(id,self);
    }

    @Override
    public Response<supplier> updatePernamentDays(String id, boolean perm) {
        return supplierService.updatePernamentDays(id,perm);
    }

    @Override
    public Response<supplier> updatePayment(String id, String pay) {
        return supplierService.updatePayment(id,pay);
    }

    @Override
    public Response<supplier> addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone) {
        return supplierService.addContactMember(supplierId, firstName, lastName, email, memberID, phone);
    }

    @Override
    public Response<supplier> deleteContactMember(String supplierID, String memberID) {
        return supplierService.deleteContactMember(supplierID, memberID);
    }

    @Override
    public Response<supplier> getSupplier(String id) {
        return supplierService.getSupplier(id);
    }

    @Override
    public Response<supplier> addQuantityList(String supplierID) {
        return supplierService.addQuantityList(supplierID);
    }

    @Override
    public Response<supplier> editQuantityListAmount(String supplierID, int productID, int amount) {
        return supplierService.editQuantityListAmount(supplierID, productID, amount);
    }

    @Override
    public Response<supplier> editQuantityListDiscount(String supplierID, int productID, int discount) {
        return supplierService.editQuantityListDiscount(supplierID, productID, discount);
    }

    @Override
    public Response<supplier> deleteQuantityList(String supplierID) {
        return supplierService.deleteQuantityList(supplierID);
    }

    @Override
    public Response<supplier> addQuantityListItem(String supplierID, int productID, int amount, int discount) {
        return supplierService.addQuantityListItem(supplierID, productID, amount,  discount);
    }

    @Override
    public Response<supplier> deleteQuantityListItem(String supplierID, int productID) {
        return supplierService.deleteQuantityListItem(supplierID, productID);
    }

    @Override
    public Response<supplier> createOrder(Date date, String supplierID) {
        return orderService.createOrder(date, supplierID);
    }

    @Override
    public Response<supplier> createPernamentOrder(int day, String supplierID) {
        return orderService.createPernamentOrder(day, supplierID);
    }

    @Override
    public Response<supplier> approveOrder(int orderID) {
        return orderService.approveOrder(orderID);
    }

    @Override
    public Response<supplier> getOrder(int orderID) {
        return orderService.getOrder(orderID);
    }

    @Override
    public Response<supplier> addProduct(String name, int productID, String manufacturer) {
        return orderService.addProduct(name, productID, manufacturer);
    }

    @Override
    public Response<supplier> getProduct(int productID) {
        return orderService.getProduct(productID);
    }
}
