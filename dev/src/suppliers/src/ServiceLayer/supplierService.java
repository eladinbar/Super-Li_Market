package ServiceLayer;

import BusinessLayer.supplierPackage.supplierController;
import ServiceLayer.Response.Response;
import ServiceLayer.objects.payment;
import ServiceLayer.objects.supplier;

public class supplierService {
    private supplierController sp;

    public supplierService() {
        sp=new supplierController();
    }

    public Response<supplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) {
        Response<supplier> toReturn=null;
        try {
            sp.addSupplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> removeSupplier(String id) {
        Response<supplier> toReturn=null;
        try {
            sp.removeSupplier(id);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> updateCompanyNumber(String id, int companyNumber) {
        Response<supplier> toReturn=null;
        try {
            sp.updateCompanySupplier(id,companyNumber);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> updateFirstName(String id, String firstName) {
        Response<supplier> toReturn=null;
        try {
            sp.updateFirstName(id,firstName);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> updateLastName(String id, String lastName) {
        Response<supplier> toReturn=null;
        try {
            sp.updateLastName(id,lastName);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> updatePhone(String id, String phone) {
        Response<supplier> toReturn=null;
        try {
            sp.updatePhone(id,phone);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> updateEmail(String id, String email) {
        Response<supplier> toReturn=null;
        try {
            sp.updateEmail(id,email);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> updateSelfDelivery(String id, boolean self) {
        Response<supplier> toReturn=null;
        try {
            sp.updateSelfDelivery(id,self);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> updatePernamentDays(String id, boolean perm) {
        Response<supplier> toReturn=null;
        try {
            sp.updatePernamentDays(id,perm);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> updatePayment(String id, String pay) {
        Response<supplier> toReturn=null;
        try {
            sp.updatePayment(id,pay);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone) {
        Response<supplier> toReturn=null;
        try {
            sp.addContactMember(supplierId, firstName, lastName, email, memberID, phone) ;
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> deleteContactMember(String supplierID, String memberID) {
        Response<supplier> toReturn=null;
        try {
            sp.deleteContactMember(supplierID,memberID);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> getSupplier(String id) {
        Response<supplier> toReturn=null;
        try {
            sp.getSupplier(id);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> addQuantityList(String supplierID) {
        Response<supplier> toReturn=null;
        try {
            sp.addQuantityList(supplierID);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> editQuantityListAmount(String supplierID, int productID, int amount) {
        Response<supplier> toReturn=null;
        try {
            sp.editQuantityListAmount(supplierID, productID, amount);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> editQuantityListDiscount(String supplierID, int productID, int discount) {
        Response<supplier> toReturn=null;
        try {
            sp.editQuantityListDiscount(supplierID, productID, discount);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> deleteQuantityList(String supplierID) {
        Response<supplier> toReturn=null;
        try {
            sp.deleteQuantityList(supplierID);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> addQuantityListItem(String supplierID, int productID, int amount, int discount) {
        Response<supplier> toReturn=null;
        try {
            sp.addQuantityListItem(supplierID, productID, amount, discount);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> deleteQuantityListItem(String supplierID, int productID) {
        Response<supplier> toReturn=null;
        try {
            sp.deleteQuantityListItem(supplierID, productID);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }
}
