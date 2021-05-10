package SerciveLayer;

import BusinessLayer.SupliersPackage.orderPackage.OrderController;
import BusinessLayer.SupliersPackage.supplierPackage.Supplier;
import BusinessLayer.SupliersPackage.supplierPackage.SupplierController;
import SerciveLayer.Response.Response;
import SerciveLayer.Response.ResponseT;
import SerciveLayer.SimpleObjects.Item;
import SerciveLayer.objects.Order;
import SerciveLayer.objects.Product;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {
    private OrderController oc;

    public OrderService() {
        this.oc = new OrderController();
    }

    public ResponseT<Order> createOrder(LocalDate date, String supplierID, SupplierController sp) {
        ResponseT<Order> toReturn;
        try {
            toReturn = new ResponseT<>(new Order(oc.createOrder(date, sp.getSupplier(supplierID)), new ArrayList<>(), -1));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<Order> createPernamentOrder(int day, String supplierID, SupplierController sp) {
        ResponseT<Order> toReturn;
        try {
            toReturn = new ResponseT<>(new Order(oc.createPermOrder(day, sp.getSupplier(supplierID)), new ArrayList<>(), day));
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

    public ResponseT<Order> getOrder(int orderID, InventoryService is,SupplierService ss) {
        ResponseT<Order> toReturn;
        try {
            ArrayList<Product> productList = new ArrayList<>();
            String supID = oc.getOrderSupID(orderID);
            Supplier supplier=ss.getSp().getSupplier(supID);
            BusinessLayer.SupliersPackage.orderPackage.Order o = oc.getOrder(orderID,supplier);
            for (int id : o.getProducts().keySet()) {
                ResponseT<Item> itemR = is.getItem(id);
                if (itemR.errorOccurred()) {
                    throw new Exception(itemR.getErrorMessage());
                }
                Item i = itemR.value;
                Product newProd = new Product(i.getID(), i.getName(),
                        o.getProducts().get(id), o.getSupplier().getPrice(1, id),
                        o.getSupplier().getProductDiscount(o.getProducts().get(id), id));
                productList.add(newProd);
            }
            toReturn = new ResponseT<>(new Order(o, productList, -1));
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


    public ResponseT<Double> getOrderTotalPrice(int orderID) {
        ResponseT<Double> toReturn;
        try {
            toReturn = new ResponseT<>(oc.getOrderTotalPrice(orderID));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<Double> getOrderTotalDiscount(int orderID) {
        ResponseT<Double> toReturn;
        try {
            toReturn = new ResponseT<>(oc.getOrderTotalDiscount(orderID));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<Order>> createShortageOrders(Map<String, Map<Integer, Integer>> itemToOrder, LocalDate orderDate, SupplierController sp) {//todo check again
        List<Order> orderList = new ArrayList<>();
        for (String suppID : itemToOrder.keySet()) {
            Map<Integer, Integer> tempMap = itemToOrder.get(suppID);
            ResponseT<Order> order = createOrder(orderDate, suppID, sp);
            if (order.errorOccurred()) return new ResponseT<>(order.getErrorMessage());
            for (int itemID : tempMap.keySet()) {
                addProductToOrder(order.value.getId(), itemID, tempMap.get(itemID));
            }
            orderList.add(order.value);
        }
        if (orderList.isEmpty()) {
            return new ResponseT<>("there is no shortage in the store");
        }
        return new ResponseT<>(orderList);
    }
}
