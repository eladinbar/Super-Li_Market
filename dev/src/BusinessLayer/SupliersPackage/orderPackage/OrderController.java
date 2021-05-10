package BusinessLayer.SupliersPackage.orderPackage;

import BusinessLayer.SupliersPackage.supplierPackage.Supplier;
import BusinessLayer.SupliersPackage.supplierPackage.SupplierCard;
import DataAccessLayer.DalObjects.SupplierObjects.OrderDal;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCardDal;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCardDal;

import java.time.LocalDate;
import java.util.*;

public class OrderController {
    private final int days = 7;
    private Map<Integer, Order> orders;
    private Map<Integer, List<Order>> pernamentOrders;
    private Map<Integer, product> products;
    private int productCounter;
    private int orderCounter;

    public OrderController() {
        pernamentOrders = new HashMap<>();
        for (int i = 1; i <= days; i++) {
            pernamentOrders.put(i, new ArrayList<>());
        }
        orders = new HashMap<>();
        products = new HashMap<>();
        productCounter = 0;
        orderCounter = 0;
    }

    public void removeSupplier(String id) {
        for (int i = 1; i <= days; i++) {
            for (Order o : pernamentOrders.get(i)) {
                if (o.getSupplier().getSc().getId().equals(id))
                    pernamentOrders.get(i).remove(o); //remove the order from the list
            }
        }
    }

    public Order createOrder(LocalDate date, Supplier supplier) throws Exception {
        if (date != null && date.isBefore(LocalDate.now()))
            throw new Exception("the date should be in the future");
        Order o = new Order(orderCounter, date, supplier);
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

    public void productExist(int productId) throws Exception {
        //todo check if product is in dal
        if (!products.containsKey(productId))
            throw new Exception("product " + productId + " does not exist");
    }

    public Order getOrder(int orderID, Supplier supplier) throws Exception {
        orderExist(orderID);
        Order toReturn = orders.get(orderID);
        if (toReturn != null)
            return toReturn;
        OrderDal orderDal = new OrderDal(orderID);
        orderDal.find();
        return new Order(orderDal,supplier);
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
        Order order = createOrder(null, supplier);
        pernamentOrders.get(day).add(order);
        return order;
    }

    public void removeProductFromOrder(int orderID, int productID) throws Exception {
        productExist(productID);
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
}
