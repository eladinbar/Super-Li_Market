package SerciveLayer;

import SerciveLayer.Response.*;
import SerciveLayer.objects.*;
import java.time.LocalDate;
import java.util.List;

public interface IService {
    //add supplier and edit all supplier fields
    ResponseT<Supplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment);
    Response removeSupplier(String id);
    Response updateFirstName(String id, String lirstName);
    Response updateLastName(String id, String lastName);
    Response updatePhone(String id, String phone);
    Response updateEmail(String id, String email);
    Response updateCompanyNumber(String id, int companyNumber);
    Response updateSelfDelivery(String id, boolean self);
    Response updatePernamentDays(String id, boolean perm);
    Response updatePayment(String id, String pay);
    Response addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone);
    Response deleteContactMember(String supplierID, String memberID);
    ResponseT<Supplier> getSupplier(String id);

    //add quantityList
    ResponseT<QuantityList> addQuantityList(String supplierID);
    Response editQuantityListAmount(String supplierID, int productID, int amount);
    Response editQuantityListDiscount(String supplierID, int productID, int discount);
    Response deleteQuantityList(String supplierID);
    ResponseT<Product> addQuantityListItem(String supplierID, int productID, int amount, int discount);
    Response deleteQuantityListItem(String supplierID, int productID);
    ResponseT<QuantityList> getQuantityList(String supplierId);

    //orders
    ResponseT<List<Order>> createShortageOrder(LocalDate date);
    ResponseT<Order> createScheduledOrder(int day, int itemID, int amount);
    ResponseT<Order> createOrder(LocalDate date, String supplierID);
    ResponseT<Order> createPernamentOrder(int day, String supplierID);
    Response approveOrder(int orderID);
    ResponseT<Order> getOrder(int orderID);
    Response addProductToOrder(int orderId, int productId, int amount);
    Response removeProductFromOrder(int orderID, int productID);

    //agreement
    ResponseT<Product> addItemToAgreement(String id, int productID, int companyProductID, int price);
    Response removeItemFromAgreement(String supplierId, int productId);
    Response editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID);
    Response editAgreementItemPrice(String supplierID, int productID, int companyProductID);
    ResponseT<Agreement> getAgreement(String supplierID);

    //products
    ResponseT<Product> createProduct(String name, String manufacturer);
    ResponseT<Product> getProduct(int productID);
    ResponseT<Double> getPrice(String supplierID,int amount, int productID);
    ResponseT<Supplier> getCheapestSupplier(int productID, int amount, boolean scheduled);
    ResponseT<Double> getOrderTotalPrice(int orderID);
    ResponseT<Double> getProductDiscount(String supplierID,int amount, int productID);
    ResponseT<Integer> getSupplierCompanyProductID(String supplierID, int productID);
    ResponseT<Double> getOrderTotalDiscount(int orderID);

    /*
    ResponseT<order> createOrderFromShortage(LocalDate date,String supplier,List<Integer> productsID,List<Integer> amount);
    */

    /*
    //system
    ResponseT<List<product>> getAllSystemProducts();
    ResponseT<List<product>> getAllSupplierProducts();
    ResponseT<List<order>> getAllOrders();
    ResponseT<List<supplier>> getAllSuppliers();
    */

}
