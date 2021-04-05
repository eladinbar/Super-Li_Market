package ServiceLayer;

import ServiceLayer.Response.Response;
import ServiceLayer.objects.supplier;

import java.util.Date;

public class orderService {
    public Response<supplier> createOrder(Date date, String supplierID) {
        return null;
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
}
