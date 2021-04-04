package ServiceLayer;

import BusinessLayer.supplierPackage.personCard;
import ServiceLayer.Response.Response;
import ServiceLayer.objects.*;

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
    Response<supplier> updatePernamentDays(String id, boolean perm);
    Response<supplier> updatePayment(String id, payment pay); //todo check enum or string
    Response<supplier> addContactMember(String supplierId,String firstName, String lastName, String email, String memberID, int phone);
    Response<supplier> deleteContactMember(String supplierID, String memberID);
}
