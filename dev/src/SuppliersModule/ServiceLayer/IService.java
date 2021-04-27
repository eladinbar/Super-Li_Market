package ServiceLayer;

import ServiceLayer.Response.*;
import ServiceLayer.objects.*;
import java.time.LocalDate;
import java.util.List;

public interface IService {
    //add supplier and edit all supplier fields
    ResponseT<supplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment);
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
    ResponseT<supplier> getSupplier(String id);

    //add quantityList
    ResponseT<quantityList> addQuantityList(String supplierID);
    Response editQuantityListAmount(String supplierID, int productID, int amount);
    Response editQuantityListDiscount(String supplierID, int productID, int discount);
    Response deleteQuantityList(String supplierID);

    ResponseT<product> addQuantityListItem(String supplierID, int productID, int amount, int discount);
    Response deleteQuantityListItem(String supplierID, int productID);
    ResponseT<quantityList> getQuantityList(String supplierId);

    //orders
    ResponseT<order> createOrder(LocalDate date, String supplierID);
    ResponseT<order> createPernamentOrder(int day, String supplierID);
    Response approveOrder(int orderID);
    ResponseT<order> getOrder(int orderID);
    Response addProductToOrder(int orderId, int productId, int amount);
    Response removeProductFromOrder(int orderID, int productID);

    //agreement
    ResponseT<product> addItemToAgreement(String id, int productID, int companyProductID, int price);
    Response removeItemFromAgreement(String supplierId, int productId);
    Response editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID);
    Response editAgreementItemPrice(String supplierID, int productID, int companyProductID);

    //products
    ResponseT<product> createProduct(String name, String manufacturer);
    ResponseT<product> getProduct(int productID);
    ResponseT<agreement> getAgreement(String supplierID);
    ResponseT<Integer> getPrice(String supplierID,int amount, int productID);

    /*
    ResponseT<order> createOrderFromShortage(LocalDate date,String supplier,List<Integer> productsID,List<Integer> amount);
    ResponseT<Supplier> getCheapestSupplier(int productID,int amount);
    ResponseT<Integer> getDiscountInPercent(String supplierID,int amount, int productID);
    ResponseT<Integer> getTotalDiscount(String supplierID,int amount, int productID);
    ResponseT<Integer> getSupplierCompanyProductID(String supplierID, int productID);
    ResponseT<Integer> getOrderTotalPrice(int orderID);
    ResponseT<order> getLastOrderOf(String supplierID);
    */


    //system
/*    ResponseT<List<product>> getAllSystemProducts();
    ResponseT<List<product>> getAllSupplierProducts();
    ResponseT<List<order>> getAllOrders();
    ResponseT<List<supplier>> getAllSuppliers();*/
}
