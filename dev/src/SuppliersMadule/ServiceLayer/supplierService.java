package ServiceLayer;

import BusinessLayer.orderPackage.orderController;
import BusinessLayer.supplierPackage.supplierController;
import ServiceLayer.Response.*;
import ServiceLayer.objects.agreement;
import ServiceLayer.objects.product;
import ServiceLayer.objects.quantityList;
import ServiceLayer.objects.supplier;

public class supplierService {
    private supplierController sp;

    public supplierService() {
        sp = new supplierController();
    }

    protected supplierController getSp() {
        return sp;
    }

    public ResponseT<supplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) {
        ResponseT<supplier> toReturn;
        try {
            toReturn = new ResponseT<>(new supplier(sp.addSupplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }


    public Response removeSupplier(String id) {
        Response toReturn = new Response();
        try {
            sp.removeSupplier(id);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response updateCompanyNumber(String id, int companyNumber) {
        Response toReturn = new Response();
        try {
            sp.updateCompanySupplier(id, companyNumber);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response updateFirstName(String id, String firstName) {
        Response toReturn = new Response();
        try {
            sp.updateFirstName(id, firstName);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response updateLastName(String id, String lastName) {
        Response toReturn = new Response();
        try {
            sp.updateLastName(id, lastName);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response updatePhone(String id, String phone) {
        Response toReturn = new Response();
        try {
            sp.updatePhone(id, phone);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response updateEmail(String id, String email) {
        Response toReturn = new Response();
        try {
            sp.updateEmail(id, email);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response updateSelfDelivery(String id, boolean self) {
        Response toReturn = new Response();
        try {
            sp.updateSelfDelivery(id, self);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response updatePernamentDays(String id, boolean perm) {
        Response toReturn = new Response();
        try {
            sp.updatePernamentDays(id, perm);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response updatePayment(String id, String pay) {
        Response toReturn = new Response();
        try {
            sp.updatePayment(id, pay);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone) {
        Response toReturn = new Response();
        try {
            sp.addContactMember(supplierId, firstName, lastName, email, memberID, phone);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response deleteContactMember(String supplierID, String memberID) {
        Response toReturn = new Response();
        try {
            sp.deleteContactMember(supplierID, memberID);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<supplier> getSupplier(String id) {
        ResponseT<supplier> toReturn;
        try {
            toReturn = new ResponseT<>(new supplier(sp.getSupplier(id)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<quantityList> addQuantityList(String supplierID) {
        ResponseT<quantityList> toReturn;
        try {
            toReturn=new ResponseT<>(new quantityList(sp.addQuantityList(supplierID)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response editQuantityListAmount(String supplierID, int productID, int amount) {
        Response toReturn = new Response();
        try {
            sp.editQuantityListAmount(supplierID, productID, amount);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response editQuantityListDiscount(String supplierID, int productID, int discount) {
        Response toReturn = new Response();
        try {
            sp.editQuantityListDiscount(supplierID, productID, discount);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response deleteQuantityList(String supplierID) {
        Response toReturn = new Response();
        try {
            sp.deleteQuantityList(supplierID);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<product> addQuantityListItem(String supplierID, int productID, int amount, int discount, orderService oc) {
        ResponseT<product> toReturn;
        try {
            sp.addQuantityListItem(supplierID, productID, amount, discount);
            toReturn = oc.getProduct(productID);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response deleteQuantityListItem(String supplierID, int productID) {
        Response toReturn = new Response();
        try {
            sp.deleteQuantityListItem(supplierID, productID);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<quantityList> getQuantityList(String supplierId) {
        ResponseT<quantityList> toReturn;
        try {
            toReturn = new ResponseT<>(new quantityList(sp.getQuantityList(supplierId)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<product> addItemToAgreement(String id, int productID, int companyProductID,int price, orderService oc) {
        ResponseT<product> toReturn;
        try {
            sp.addItemToAgreement(id, productID, companyProductID,price);
            toReturn = oc.getProduct(productID);
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response removeItemFromAgreement(String supplierId, int productId) {
        Response toReturn=new Response();
        try {
            sp.removeItemFromAgreement(supplierId,productId);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID) {
        Response toReturn=new Response();
        try {
            sp.editAgreementItemCompanyProductID(supplierID,productID,companyProductID);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response editAgreementItemPrice(String supplierID, int productID, int price) {
        Response toReturn=new Response();
        try {
            sp.editAgreementItemPrice(supplierID,productID,price);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<agreement> getAgreement(String supplierID) {
        ResponseT<agreement> toReturn;
        try {
            toReturn = new ResponseT<>(new agreement(sp.getAgreement(supplierID)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }
}
