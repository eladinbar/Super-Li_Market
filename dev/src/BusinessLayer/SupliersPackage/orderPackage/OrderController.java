package BusinessLayer.SupliersPackage.orderPackage;

import BusinessLayer.SupliersPackage.supplierPackage.Supplier;
import DataAccessLayer.DalObjects.SupplierObjects.OrderDal;

import java.time.LocalDate;
import java.util.*;

public class OrderController {
    private final int days = 7;
    private Map<Integer, Order> orders;
    private Map<Integer, List<Order>> pernamentOrders;
    private int orderCounter;

    public OrderController() {
        pernamentOrders = new HashMap<>();
        for (int i = 1; i <= days; i++) {
            pernamentOrders.put(i, new ArrayList<>());
        }
        orders = new HashMap<>();
        orderCounter = 0;
    }

    public void removeSupplier(String id) {
        for (int i = 1; i <= days; i++) {
            for (Order o : pernamentOrders.get(i)) {
                if (o.getSupplier().getSc().getId().equals(id))
                    pernamentOrders.get(i).remove(o); //remove the order from the list
            }
        }
        for(Order o : orders.values()){
            if (o.getSupplier().getSc().getId().equals(id))
                orders.remove(o); //remove the order from the list
        }
    }

    public Order createOrder(LocalDate date, Supplier supplier, int day) throws Exception {
        if (date != null && date.isBefore(LocalDate.now()))
            throw new Exception("the date should be in the future");
        Order o = new Order(orderCounter, date, supplier, day);
        orders.put(orderCounter, o);
        orderCounter++;
        return o;
    }

    public void approveOrder(int orderID) throws Exception {
        orderExist(orderID);
        orders.get(orderID).approveOrder();
    }

    private void orderExist(int orderId) throws Exception {
        if (!orders.containsKey(orderId) && !(new Order(orderId).find()))
            throw new Exception("order does not exist");
    }

    public Order getOrder(int orderID, Supplier supplier) throws Exception {
        orderExist(orderID);
        Order toReturn = orders.get(orderID);
        if (toReturn != null)
            return toReturn;
        OrderDal orderDal = new OrderDal(orderID);
        orderDal.find();
        Order newOrder = new Order(orderDal, supplier);
        orders.put(orderID, newOrder);
        if (newOrder.getDate() == null)
            pernamentOrders.get(newOrder.getDate()).add(newOrder);
        return newOrder;
    }

    public String getOrderSupID(int orderID) throws Exception {
        orderExist(orderID);
        Order toReturn = orders.get(orderID);
        if (toReturn != null)
            return orders.get(orderID).getSupplier().getSc().getId();
        OrderDal orderDal = new OrderDal(orderID);
        orderDal.find();
        return orderDal.getSupplierId();
    }

    public void addProductToOrder(int orderId, int productId, int amount) throws Exception {
        orderExist(orderId);
        orders.get(orderId).addProductToOrder(productId, amount);
    }

    public Order createPermOrder(int day, Supplier supplier) throws Exception {
        for (Order o : pernamentOrders.get(day)) {
            if (o.getSupplier().getSc().getId().equals(supplier.getSc().getId())) {
                return o;
            }
        }
        Order order = createOrder(null, supplier, day);
        pernamentOrders.get(day).add(order);
        return order;
    }

    public void removeProductFromOrder(int orderID, int productID) throws Exception {
        orderExist(orderID);
        orders.get(orderID).removeProductFromOrder(productID);
    }

    public Double getOrderTotalPrice(int orderID) throws Exception {
        orderExist(orderID);
        return orders.get(orderID).getOrderTotalPrice();
    }

    public Double getOrderTotalDiscount(int orderID) throws Exception {
        orderExist(orderID);
        return orders.get(orderID).getOrderTotalDiscount();
    }

    public int getOrderDay(int orderID) {
        for (int i = 1; i < 8; i++) {
            for (Order o : pernamentOrders.get(i)) {
                if (o.getId() == orderID)
                    return i;
            }
        }
        return -1;
    }
}
