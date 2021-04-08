package PresentationLayer;

import ServiceLayer.IService;
import ServiceLayer.Response.Response;
import ServiceLayer.Response.ResponseT;
import ServiceLayer.Service;
import ServiceLayer.objects.order;
import ServiceLayer.objects.supplier;

import java.time.LocalDate;

public class PresentationController {
    private IService service;

    public PresentationController() {
        service = new Service();
    }

    //add supplier and edit all supplier fields
    public void addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) {
        ResponseT<supplier> print = service.addSupplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment);
        if (print.isErrorOccurred())
            System.out.println(print.getMessage());
        else
            System.out.println(print.getDate().toString());
    }

    public void removeSupplier(String id) {
        service.removeSupplier(id);
    }

    public void updateFirstName(String id, String lirstName) {
        service.updateFirstName(id, lirstName);
    }

    public void updateLastName(String id, String lastName) {
        service.updateLastName(id, lastName);
    }

    public void updatePhone(String id, String phone) {
        service.updatePhone(id, phone);
    }

    public void updateEmail(String id, String email) {
        service.updateEmail(id, email);
    }

    public void updateCompanyNumber(String id, int companyNumber) {
        service.updateCompanyNumber(id, companyNumber);
    }

    public void updateSelfDelivery(String id, boolean self) {
        service.updateSelfDelivery(id, self);
    }

    public void updatePernamentDays(String id, boolean perm) {
        service.updatePernamentDays(id, perm);
    }

    public void updatePayment(String id, String pay) {
        service.updatePayment(id, pay);
    }

    public void addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone) {
        service.addContactMember(supplierId, firstName, lastName, email, memberID, phone);
    }

    public void deleteContactMember(String supplierID, String memberID) {
        service.deleteContactMember(supplierID, memberID);
    }

    public void getSupplier(String id) {
        service.getSupplier(id);
    }

    //add quantityList
    //todo optional add lise of products id to supplier controller
    public void addQuantityList(String supplierID) {
        service.addQuantityList(supplierID);
    }

    public void editQuantityListAmount(String supplierID, int productID, int amount) {
        service.editQuantityListAmount(supplierID, productID, amount);
    }

    public void editQuantityListDiscount(String supplierID, int productID, int discount) {
        service.editQuantityListDiscount(supplierID, productID, discount);
    }

    public void deleteQuantityList(String supplierID) {
        service.deleteQuantityList(supplierID);
    }

    public void addQuantityListItem(String supplierID, int productID, int amount, int discount) {
        service.addQuantityListItem(supplierID, productID, amount, discount);
    }

    public void deleteQuantityListItem(String supplierID, int productID) {
        service.deleteQuantityListItem(supplierID, productID);
    }

    //orders
    public ResponseT<order> createOrder(LocalDate date, String supplierID) {
        return service.createOrder(date, supplierID);
    }

    public void createPernamentOrder(int day, String supplierID) {
        service.createPernamentOrder(day, supplierID);
    }

    public void approveOrder(int orderID) {
        service.approveOrder(orderID);
    }

    public void getOrder(int orderID) {
        service.getOrder(orderID);
    }

    public void addProductToOrder(int orderId, int productId, int amount) {
        service.addProductToOrder(orderId, productId, amount);
    }
    //todo check if nessesary Response<supplier> setOrders();

    //products
    public void createProduct(String name, String manufacturer) {
        service.createProduct(name, manufacturer);
    }

    public void getProduct(int productID) {
        service.getProduct(productID);
    }
}
