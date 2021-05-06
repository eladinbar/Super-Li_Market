package SerciveLayer;

import BusinessLayer.SupliersPackage.supplierPackage.SupplierController;
import SerciveLayer.Response.*;
import SerciveLayer.objects.Agreement;
import SerciveLayer.objects.Product;
import SerciveLayer.objects.QuantityList;
import SerciveLayer.objects.Supplier;

import java.util.HashMap;
import java.util.Map;

public class SupplierService {
    private SupplierController sp;

    public SupplierService() {
        sp = new SupplierController();
    }

    protected SupplierController getSp() {
        return sp;
    }

    public ResponseT<Supplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) {
        ResponseT<Supplier> toReturn;
        try {
            toReturn = new ResponseT<>(new Supplier(sp.addSupplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment)));
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

    public ResponseT<Supplier> getSupplier(String id) {
        ResponseT<Supplier> toReturn;
        try {
            toReturn = new ResponseT<>(new Supplier(sp.getSupplier(id)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<QuantityList> addQuantityList(String supplierID) {
        ResponseT<QuantityList> toReturn;
        try {
            toReturn=new ResponseT<>(new QuantityList(sp.addQuantityList(supplierID)));
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

    public ResponseT<Product> addQuantityListItem(String supplierID, int productID, int amount, int discount, OrderService oc) {
        ResponseT<Product> toReturn;
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

    public ResponseT<QuantityList> getQuantityList(String supplierId) {
        ResponseT<QuantityList> toReturn;
        try {
            toReturn = new ResponseT<>(new QuantityList(sp.getQuantityList(supplierId)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<Product> addItemToAgreement(String id, int productID, int companyProductID, int price, OrderService oc) {
        ResponseT<Product> toReturn;
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

    public ResponseT<Agreement> getAgreement(String supplierID) {
        ResponseT<Agreement> toReturn;
        try {
            toReturn = new ResponseT<>(new Agreement(sp.getAgreement(supplierID)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<Double> getPrice(String supplierID, int amount, int productID) {
        ResponseT<Double> toReturn;
        try {
            toReturn = new ResponseT<>(sp.getPrice(supplierID, amount, productID));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<Supplier> getCheapestSupplier(int productID, int amount) {
        ResponseT<Supplier> toReturn;
        try {
            toReturn = new ResponseT<>(new Supplier(sp.getCheapestSupplier(productID,amount)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<Double> getProductDiscount(String supplierID, int amount, int productID) {
        ResponseT<Double> toReturn;
        try {
            toReturn = new ResponseT<>(sp.getProductDiscount(supplierID, amount, productID));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<Integer> getSupplierCompanyProductID(String supplierID, int productID) {
        ResponseT<Integer> toReturn;
        try {
            toReturn = new ResponseT<>(sp.getSupplierCompanyProductID(supplierID,productID));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<Map<String, Map<Integer, Integer>>> createShortageOrders(Map<Integer, Integer> items) {
        Map<String, Map<Integer, Integer>> orders = new HashMap<>();
        try {
            for (Integer itemId : items.keySet()) {
                String suppId = sp.getCheapestSupplier(itemId, items.get(itemId)).getSc().getId();
                if (!orders.containsKey(suppId)) {
                    orders.put(suppId, new HashMap<>());
                }
                orders.get(suppId).put(itemId, items.get(itemId));
            }
        } catch(Exception e){
            return new ResponseT<>(e.getMessage());
        }

        return null;
    }
}
