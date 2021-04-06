package ServiceLayer;

import BusinessLayer.orderPackage.orderController;
import ServiceLayer.Response.Response;
import ServiceLayer.objects.supplier;

import java.util.Date;

public class orderService {
    private orderController oc;

    public orderService() {
        this.oc = new orderController();
    }

    public Response<supplier> createOrder(Date date, String supplierID) {
        Response<supplier> toReturn=null;
        try {
            oc.createOrder(date, supplierID);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> createPernamentOrder(int day, String supplierID) {
        return null;
    }

    public Response<supplier> approveOrder(int orderID) {
        Response<supplier> toReturn=null;
        try {
            oc.approveOrder(orderID);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> getOrder(int orderID) {
        Response<supplier> toReturn=null;
        try {
            oc.getOrder(orderID);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> createProduct(String name, String manufacturer) {
        Response<supplier> toReturn=null;
        try {
            oc.createProduct(name,  manufacturer);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> getProduct(int productID) {
        Response<supplier> toReturn=null;
        try {
            oc.getProduct(productID);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> removeSupplier(String id) {
        Response<supplier> toReturn=null;
        try {
            oc.removeSupplier(id);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }

    public Response<supplier> addProductToOrder(int orderId, int productId) {
        Response<supplier> toReturn=null;
        try {
            oc.addProductToOrder(orderId, productId);
        } catch (Exception e) {
            toReturn=new Response(e.getMessage());
        }
        return toReturn;
    }
}
