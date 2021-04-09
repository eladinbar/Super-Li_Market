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
    public String addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) {
        return service.addSupplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment).toString();
    }

    public String removeSupplier(String id) {
        return service.removeSupplier(id).toString();
    }

    public String updateFirstName(String id, String lirstName) {
        return service.updateFirstName(id, lirstName).toString();
    }

    public String updateLastName(String id, String lastName) {
        return service.updateLastName(id, lastName).toString();
    }

    public String updatePhone(String id, String phone) {
        return service.updatePhone(id, phone).toString();
    }

    public String updateEmail(String id, String email) {
        return service.updateEmail(id, email).toString();
    }

    public String updateCompanyNumber(String id, int companyNumber) {
        return service.updateCompanyNumber(id, companyNumber).toString();
    }

    public String updateSelfDelivery(String id, boolean self) {
        return service.updateSelfDelivery(id, self).toString();
    }

    public String updatePernamentDays(String id, boolean perm) {
        return service.updatePernamentDays(id, perm).toString();
    }

    public String updatePayment(String id, String pay) {
        return service.updatePayment(id, pay).toString();
    }

    public String addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone) {
        return service.addContactMember(supplierId, firstName, lastName, email, memberID, phone).toString();
    }

    public String deleteContactMember(String supplierID, String memberID) {
        return service.deleteContactMember(supplierID, memberID).toString();
    }

    public String getSupplier(String id) {
        return service.getSupplier(id).toString();
    }

    //add quantityList
    public String addQuantityList(String supplierID) {
        return service.addQuantityList(supplierID).toString();
    }

    public String editQuantityListAmount(String supplierID, int productID, int amount) {
        return service.editQuantityListAmount(supplierID, productID, amount).toString();
    }

    public String editQuantityListDiscount(String supplierID, int productID, int discount) {
        return service.editQuantityListDiscount(supplierID, productID, discount).toString();
    }

    public String deleteQuantityList(String supplierID) {
        return service.deleteQuantityList(supplierID).toString();
    }

    public String addQuantityListItem(String supplierID, int productID, int amount, int discount) {
        return service.addQuantityListItem(supplierID, productID, amount, discount).toString();
    }

    public String getQuantityList(String supplierId){
        return service.getQuantityList(supplierId).toString();
    }

    public String  deleteQuantityListItem(String supplierID, int productID) {
        return service.deleteQuantityListItem(supplierID, productID).toString();
    }

    //orders
    public String createOrder(LocalDate date, String supplierID) {
        return service.createOrder(date, supplierID).toString();
    }

    public String createPernamentOrder(int day, String supplierID) {
        return service.createPernamentOrder(day, supplierID).toString();
    }

    public String approveOrder(int orderID) {
        return service.approveOrder(orderID).toString();
    }

    public String getOrder(int orderID) {
        return service.getOrder(orderID).toString();
    }

    public String addProductToOrder(int orderId, int productId, int amount) {
        return service.addProductToOrder(orderId, productId, amount).toString();
    }

    //products
    public String createProduct(String name, String manufacturer) {
        return service.createProduct(name, manufacturer).toString();
    }

    public String getProduct(int productID) {
        return service.getProduct(productID).toString();
    }

    public String addItemToagreement(String id, int productID, int companyProductID,int price) {
        return service.addItemToAgreement(id,productID,companyProductID,price).toString();
    }

    public String removeItemFromAgreement(String supplierId, int productId) {
        return service.removeItemFromAgreement(supplierId,productId).toString();
    }

    public String editAgreementItemCompanyProductID(String supplierID,int productID,int companyProductID){
        return service.editAgreementItemCompanyProductID(supplierID, productID, companyProductID).toString();
    }
    public String editAgreementItemPrice(String supplierID,int productID,int companyProductID){
        return service.editAgreementItemPrice(supplierID,productID,companyProductID).toString();
    }
    public String removeProductFromOrder(int orderID,int productID){
        return service.removeProductFromOrder(orderID,productID).toString();
    }

    public String getAgreement(String supplierID) {
        return service.getAgreement(supplierID).toString();
    }
}
