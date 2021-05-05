package BusinessLayer.SupliersPackage.orderPackage;

import BusinessLayer.SupliersPackage.supplierPackage.Supplier;

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
                    pernamentOrders.get(i).remove(o);
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
        if (!orders.containsKey(orderId))
            throw new Exception("order does not exist");
    }

    public void productExist(int productId) throws Exception {
        if (!products.containsKey(productId))
            throw new Exception("product " + productId + " does not exist");
    }

    public Order getOrder(int orderID) throws Exception {
        orderExist(orderID);
        return orders.get(orderID);
    }

    public void addProductToOrder(int orderId, int productId, int amount) throws Exception {
        //todo complete check if the product is in the agreement
        //todo check if the date is not approved
        //todo check after agreement
        orderExist(orderId);
        productExist(productId);
        orders.get(orderId).addProductToOrder(orderId, productId, amount);
    }

    public product createProduct(String name, String manufacturer) throws Exception {
        manufacturers manu = manufacturers.valueOf(manufacturer);
        for (product p : products.values())
            if (p.getName().equals(name) && p.getManu().name().equals(manufacturer))
                throw new Exception("product already exists in the system");
        product p = new product(name, productCounter, manu);
        products.put(productCounter, p);
        productCounter++;
        return p;
    }

    public product getProduct(int productID) throws Exception {
        productExist(productID);
        return products.get(productID);
    }

    public Order createPermOrder(int day, Supplier supplier) throws Exception {
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
