package ServiceLayer;

import BusinessLayer.SuppliersPackage.OrderPackage.Order;
import BusinessLayer.SuppliersPackage.OrderPackage.OrderController;
import BusinessLayer.SuppliersPackage.SupplierPackage.Supplier;
import BusinessLayer.SuppliersPackage.SupplierPackage.SupplierController;
import ServiceLayer.Response.Response;
import ServiceLayer.Response.ResponseT;
import ServiceLayer.FacadeObjects.FacadeItem;
import ServiceLayer.FacadeObjects.FacadeOrder;
import ServiceLayer.FacadeObjects.FacadeProduct;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderService {
    private OrderController oc;

    public OrderService() {
        this.oc = new OrderController();
    }

    public ResponseT<FacadeOrder> createOrder(LocalDate date, String supplierID, SupplierController sp) {
        ResponseT<FacadeOrder> toReturn;
        try {
            toReturn = new ResponseT<>(new FacadeOrder(oc.createOrder(date, sp.getSupplier(supplierID), -1), new ArrayList<>(), -1));
        } catch (Exception e) {
            toReturn = new ResponseT<>(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<FacadeOrder> createPermanentOrder(int day, String supplierID, SupplierController sp) {
        ResponseT<FacadeOrder> toReturn;
        try {
            toReturn = new ResponseT<>(new FacadeOrder(oc.createPermOrder(day, sp.getSupplier(supplierID)), new ArrayList<>(), day));
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

    public ResponseT<FacadeOrder> getOrder(int orderID, InventoryService is, SupplierService sc) {
        ResponseT<FacadeOrder> toReturn;
        try {
            String supID = oc.getOrderSupID(orderID); //get supplier id
            Supplier supRes = sc.getSp().getSupplier(supID); // get the supplier
            ArrayList<FacadeProduct> productList = new ArrayList<>();
            BusinessLayer.SuppliersPackage.OrderPackage.Order o = oc.getOrder(orderID, supRes);
            for (int id : o.getProducts().keySet()) {
                ResponseT<FacadeItem> itemR = is.getItem(id);
                if (itemR.errorOccurred()) {
                    throw new Exception(itemR.getErrorMessage());
                }
                FacadeItem i = itemR.value;
                FacadeProduct newProd = new FacadeProduct(i.getID(), i.getName(),
                        o.getProducts().get(id), o.getSupplier().getPrice(1, id),
                        o.getSupplier().getProductDiscount(o.getProducts().get(id), id));
                productList.add(newProd);
            }
            toReturn = new ResponseT<>(new FacadeOrder(o, productList, oc.getOrderDay(orderID)));
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

    public ResponseT<List<FacadeOrder>> createShortageOrders(Map<String, Map<Integer, Integer>> itemToOrder, Map<Integer, String> itemNames, LocalDate orderDate, SupplierController sp) throws Exception {
        List<FacadeOrder> orderList = new ArrayList<>();
        for (String suppID : itemToOrder.keySet()) {
            Map<Integer, Integer> tempMap = itemToOrder.get(suppID);
            ResponseT<FacadeOrder> order = createOrder(orderDate, suppID, sp);
            if (order.errorOccurred()) return new ResponseT<>(order.getErrorMessage());
            for (int itemID : tempMap.keySet()) {
                addProductToOrder(order.value.getId(), itemID, tempMap.get(itemID));
                FacadeProduct p = new FacadeProduct(itemID, itemNames.get(itemID), tempMap.get(itemID), sp.getSuppliers().get(suppID).getAg().getPrices().get(itemID), sp.getProductDiscount(suppID, tempMap.get(itemID), itemID));
                order.value.getProducts().add(p);
            }
            orderList.add(order.value);
        }
        if (orderList.isEmpty()) {
            return new ResponseT<>("there is no shortage in the store");
        }
        return new ResponseT<>(orderList);
    }

    public Response updateProductDeliveredAmount(int orderId, int productID, int amount) {
        Response toReturn = new Response();
        try {
            oc.updateProductDeliveredAmount(orderId, productID, amount);
        } catch (Exception e) {
            toReturn = new Response(e.getMessage());
        }
        return toReturn;
    }

    public ResponseT<List<Integer>> makeOrders(int day) {
        ResponseT<List<Integer>> toReturn;
        try {
            toReturn = makeOrders(day);
        } catch (Exception e) {
            toReturn = new ResponseT(e.getMessage());
        }
        return toReturn;
    }
}
