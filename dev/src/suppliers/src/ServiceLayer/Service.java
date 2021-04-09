package ServiceLayer;

import ServiceLayer.Response.Response;
import ServiceLayer.Response.ResponseT;
import ServiceLayer.objects.*;

import java.time.LocalDate;
import java.util.Date;

public class Service implements IService {
    private orderService orderService;
    private supplierService supplierService;

    public Service() {
        this.supplierService = new supplierService();
        this.orderService = new orderService();
    }

    @Override
    public ResponseT<supplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) {
        return supplierService.addSupplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment);
    }

    @Override
    public Response removeSupplier(String id) {
        orderService.removeSupplier(id);
        return supplierService.removeSupplier(id);
    }

    @Override
    public Response updateFirstName(String id, String firstName) {
        return supplierService.updateFirstName(id, firstName);
    }

    @Override
    public Response updateLastName(String id, String lastName) {
        return supplierService.updateLastName(id, lastName);
    }

    @Override
    public Response updatePhone(String id, String phone) {
        return supplierService.updatePhone(id, phone);
    }

    @Override
    public Response updateEmail(String id, String email) {
        return supplierService.updateEmail(id, email);
    }

    @Override
    public Response updateCompanyNumber(String id, int companyNumber) {
        return supplierService.updateCompanyNumber(id, companyNumber);
    }

    @Override
    public Response updateSelfDelivery(String id, boolean self) {
        return supplierService.updateSelfDelivery(id, self);
    }

    @Override
    public Response updatePernamentDays(String id, boolean perm) {
        return supplierService.updatePernamentDays(id, perm);
    }

    @Override
    public Response updatePayment(String id, String pay) {
        return supplierService.updatePayment(id, pay);
    }

    @Override
    public Response addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone) {
        return supplierService.addContactMember(supplierId, firstName, lastName, email, memberID, phone);
    }

    @Override
    public Response deleteContactMember(String supplierID, String memberID) {
        return supplierService.deleteContactMember(supplierID, memberID);
    }

    @Override
    public ResponseT<supplier> getSupplier(String id) {
        return supplierService.getSupplier(id);
    }

    @Override
    public ResponseT<quantityList> addQuantityList(String supplierID) {
        return supplierService.addQuantityList(supplierID);
    }

    @Override
    public Response editQuantityListAmount(String supplierID, int productID, int amount) {
        return supplierService.editQuantityListAmount(supplierID, productID, amount);
    }

    @Override
    public Response editQuantityListDiscount(String supplierID, int productID, int discount) {
        return supplierService.editQuantityListDiscount(supplierID, productID, discount);
    }

    @Override
    public Response deleteQuantityList(String supplierID) {
        return supplierService.deleteQuantityList(supplierID);
    }

    @Override
    public ResponseT<product> addQuantityListItem(String supplierID, int productID, int amount, int discount) {
        ResponseT<product> r = orderService.getProduct(productID);
        if (!r.errorOccured())
            return supplierService.addQuantityListItem(supplierID, productID, amount, discount, orderService);
        return r;
    }

    public ResponseT<product> addItemToAgreement(String id, int productID, int companyProductID, int price) {
        ResponseT<product> r = orderService.getProduct(productID);
        if (!r.errorOccured())
            return supplierService.addItemToAgreement(id, productID, companyProductID, price, orderService);
        return r;
    }

    @Override
    public Response removeItemFromAgreement(String supplierId, int productId) {
        return supplierService.removeItemFromAgreement(supplierId, productId);
    }

    @Override
    public Response editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID) {
        return supplierService.editAgreementItemCompanyProductID(supplierID, productID, companyProductID);
    }

    @Override
    public Response editAgreementItemPrice(String supplierID, int productID, int price) {
        return supplierService.editAgreementItemPrice(supplierID, productID, price);
    }

    @Override
    public ResponseT<quantityList> getQuantityList(String supplierId) {
        return supplierService.getQuantityList(supplierId);
    }

    @Override
    public Response deleteQuantityListItem(String supplierID, int productID) {
        return supplierService.deleteQuantityListItem(supplierID, productID);
    }

    @Override
    public ResponseT<order> createOrder(LocalDate date, String supplierID) {
        return orderService.createOrder(date, supplierID, supplierService.getSp());
    }

    @Override
    public ResponseT<order> createPernamentOrder(int day, String supplierID) {
        return orderService.createPernamentOrder(day, supplierID, supplierService.getSp());
    }

    @Override
    public Response approveOrder(int orderID) {
        return orderService.approveOrder(orderID);
    }

    @Override
    public ResponseT<order> getOrder(int orderID) {
        return orderService.getOrder(orderID);
    }

    @Override
    public Response addProductToOrder(int orderId, int productId, int amount) {
        return orderService.addProductToOrder(orderId, productId, amount);
    }

    @Override
    public Response removeProductFromOrder(int orderID, int productID) {
        return orderService.removeProductFromOrder(orderID, productID);
    }

    @Override
    public ResponseT<product> createProduct(String name, String manufacturer) {
        return orderService.createProduct(name, manufacturer);
    }

    @Override
    public ResponseT<product> getProduct(int productID) {
        return orderService.getProduct(productID);
    }

    @Override
    public ResponseT<agreement> getAgreement(String supplierID) {
        return supplierService.getAgreement(supplierID);
    }
}
