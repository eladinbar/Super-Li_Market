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
        return null;
    }

    public Response<supplier> getOrder(int orderID) {
        return null;
    }

    public Response<supplier> addProduct(String name, int productID, String manufacturer) {
        return null;
    }

    public Response<supplier> getProduct(int productID) {
        return null;
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
}
