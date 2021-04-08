package ServiceLayer;

import ServiceLayer.Response.*;
import ServiceLayer.objects.*;

import java.time.LocalDate;

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
    Response addContactMember(String supplierId,String firstName, String lastName, String email, String memberID, String phone);
    Response deleteContactMember(String supplierID, String memberID);
    ResponseT<supplier> getSupplier(String id);

    //add quantityList
    //todo optional add lise of products id to supplier controller
    Response addQuantityList(String supplierID);
    Response editQuantityListAmount(String supplierID,int productID, int amount);
    Response editQuantityListDiscount(String supplierID,int productID, int discount);
    Response deleteQuantityList(String supplierID);
    ResponseT<product> addQuantityListItem(String supplierID,int productID, int amount,int discount);
    Response deleteQuantityListItem(String supplierID,int productID);

    //orders
    ResponseT<order> createOrder(LocalDate date, String supplierID);
    Response createPernamentOrder(int day, String supplierID);
    Response approveOrder(int orderID);
    Response getOrder(int orderID);
    Response addProductToOrder(int orderId , int productId, int amount);
    //todo check if nessesary Response<supplier> setOrders();

    //products
    Response createProduct(String name, String manufacturer);
    Response getProduct(int productID);

    ResponseT<quantityList> getQuantityList(String supplierId);

    ResponseT<product> addItemToAgreement(String id, int productID, int companyProductID);
}
