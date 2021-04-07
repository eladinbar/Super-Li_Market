package ServiceLayer;

import BusinessLayer.orderPackage.orderController;
import BusinessLayer.supplierPackage.supplierController;
import ServiceLayer.Response.Response;
import ServiceLayer.objects.supplier;

import java.time.LocalDate;
import java.util.Date;

public class orderService {
    private orderController oc;

    public orderService() {
        this.oc = new orderController();
    }

    public Response createOrder(LocalDate date, String supplierID, supplierController sp) {
        Response toReturn=null;
        try {
            oc.createOrder(date,sp.getSupplier(supplierID));
        } catch (Exception e) {
            toReturn=new Response(true,e.getMessage());
        }
        return toReturn;
    }

    public Response createPernamentOrder(int day, String supplierID, supplierController sp) {
        Response toReturn=null;
        try {
            oc.createPermOrder(day,sp.getSupplier(supplierID));
        } catch (Exception e) {
            toReturn=new Response(true,e.getMessage());
        }
        return toReturn;
    }

    public Response approveOrder(int orderID) {
        Response toReturn=null;
        try {
            oc.approveOrder(orderID);
        } catch (Exception e) {
            toReturn=new Response(true,e.getMessage());
        }
        return toReturn;
    }

    public Response getOrder(int orderID) {
        Response toReturn=null;
        try {
            oc.getOrder(orderID);
        } catch (Exception e) {
            toReturn=new Response(true,e.getMessage());
        }
        return toReturn;
    }

    public Response createProduct(String name, String manufacturer) {
        Response toReturn=null;
        try {
            oc.createProduct(name,  manufacturer);
        } catch (Exception e) {
            toReturn=new Response(true,e.getMessage());
        }
        return toReturn;
    }

    public Response getProduct(int productID) {
        Response toReturn=null;
        try {
            oc.getProduct(productID);
        } catch (Exception e) {
            toReturn=new Response(true,e.getMessage());
        }
        return toReturn;
    }

    public Response removeSupplier(String id) {
        Response toReturn=null;
        try {
            oc.removeSupplier(id);
        } catch (Exception e) {
            toReturn=new Response(true,e.getMessage());
        }
        return toReturn;
    }

    public Response addProductToOrder(int orderId, int productId) {
        Response toReturn=null;
        try {
            oc.addProductToOrder(orderId, productId);
        } catch (Exception e) {
            toReturn=new Response(true,e.getMessage());
        }
        return toReturn;
    }

    public Response productExists(int productID) {
        Response toReturn=null;
        try {
            oc.productExist(productID);
        } catch (Exception e) {
            toReturn=new Response(true,e.getMessage());
        }
        return toReturn;
    }
}
