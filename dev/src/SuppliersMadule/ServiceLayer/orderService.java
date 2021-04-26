package ServiceLayer;

import BusinessLayer.orderPackage.orderController;
import BusinessLayer.supplierPackage.supplierController;
import ServiceLayer.Response.Response;
import ServiceLayer.Response.ResponseT;
import ServiceLayer.objects.order;
import ServiceLayer.objects.product;

import java.time.LocalDate;

public class orderService {
    private orderController oc;

    public orderService() {
        this.oc = new orderController();
    }

    public ResponseT<order> createOrder(LocalDate date, String supplierID, supplierController sp) {
        ResponseT<order> toReturn;
        try {
            toReturn = new ResponseT<>(new order(oc.createOrder(date, sp.getSupplier(supplierID))));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<order> createPernamentOrder(int day, String supplierID, supplierController sp) {
        ResponseT<order> toReturn;
        try {
            toReturn= new ResponseT<>(new order(oc.createPermOrder(day, sp.getSupplier(supplierID))));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response approveOrder(int orderID) {
        Response toReturn = new Response();
        try {
            oc.approveOrder(orderID);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<order> getOrder(int orderID) {
        ResponseT<order> toReturn;
        try {
            toReturn=new ResponseT<>(new order(oc.getOrder(orderID)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<product> createProduct(String name, String manufacturer) {
        ResponseT<product> toReturn;
        try {
            toReturn=new ResponseT<>(new product(oc.createProduct(name, manufacturer)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<product> getProduct(int productID) {
        ResponseT<product> toReturn;
        try {
            toReturn = new ResponseT<>(new product(oc.getProduct(productID)));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public Response removeSupplier(String id) {
        Response toReturn = new Response();
        try {
            oc.removeSupplier(id);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response addProductToOrder(int orderId, int productId, int amount) {
        Response toReturn = new Response();
        try {
            oc.addProductToOrder(orderId, productId, amount);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response productExists(int productID) {
        Response toReturn = new Response();
        try {
            oc.productExist(productID);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response removeProductFromOrder(int orderID, int productID) {
        Response toReturn = new Response();
        try {
            oc.removeProductFromOrder(orderID, productID);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }
}