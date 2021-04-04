package ServiceLayer;

import BusinessLayer.supplierPackage.personCard;
import ServiceLayer.Response.Response;
import ServiceLayer.objects.*;

import java.util.Date;
import java.util.List;

public interface IService {
    //add supplier and edit all supplier fields
    Response<supplier> addSupplier(String firstName, String lastName, String email, String id, int phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, payment payment);
    Response<supplier> removeSupplier(String id);
    Response<supplier> updateFirstName(String id, String lirstName);
    Response<supplier> updateLastName(String id, String lastName);
    Response<supplier> updatePhone(String id, int phone);
    Response<supplier> updateEmail(String id, String email);
    Response<supplier> updateCompanyNumber(String id, int companyNumber);
    Response<supplier> updateSelfDelivery(String id, boolean self);
    Response<supplier> updatePernamentDays(String id, boolean perm);
    Response<supplier> updatePayment(String id, payment pay); //todo check enum or string
    Response<supplier> addContactMember(String supplierId,String firstName, String lastName, String email, String memberID, int phone);
    Response<supplier> deleteContactMember(String supplierID, String memberID);
    Response<supplier> getSupplier(String id);

    //add quantityList
    //todo optional add lise of products id to supplier controller
    Response<supplier> addQuantityList(String supplierID);
    Response<supplier> editQuantityListAmount(String supplierID,int productID, int amount);
    Response<supplier> editQuantityListDiscount(String supplierID,int productID, int discount);
    Response<supplier> deleteQuantityList(String supplierID);
    Response<supplier> addQuantityListItem(String supplierID,int productID, int amount,int discount);
    Response<supplier> deleteQuantityListItem(String supplierID,int productID);

    //orders
    Response<supplier> createOrder(Date date, String supplierID);
    Response<supplier> createPernamentOrder(int day, String supplierID);
    Response<supplier> approveOrder(int orderID);
    Response<supplier> getOrder(int orderID);
    //todo check if nessesary Response<supplier> setOrders();

    //products
    //todo check about enum
    Response<supplier> addProduct(String name,int productID, String manufacturer);
    Response<supplier> getProduct(int productID);
}
