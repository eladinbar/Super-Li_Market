package BusinessLayer.orderPackage;

import BusinessLayer.supplierPackage.supplier;

import java.time.LocalDate;
import java.util.*;

public class orderController {
    private final int days = 7;
    private Map<Integer, order> orders;
    private Map<Integer, List<order>> pernamentOrders;
    private Map<Integer, product> products;
    private int productCounter;
    private int orderCounter;


    public orderController() {
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
            for (order o : pernamentOrders.get(i)) {
                if (o.getSupplier().getSc().getId().equals(id))
                    pernamentOrders.get(i).remove(o);
            }
        }
    }

    public order createOrder(LocalDate date, supplier supplier) throws Exception {
        if (date.isBefore(LocalDate.now()))
            throw new Exception("the date should be in the future");
        order o = new order(orderCounter, date, supplier);
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

    public order getOrder(int orderID) throws Exception {
        orderExist(orderID);
        return orders.get(orderID);
    }

    public void addProductToOrder(int orderId, int companyId) throws Exception {
        //todo complete check if the product is in the agreement
        //todo check if the date is not approved
        orderExist(orderId);
        int productId = orders.get(orderId).getSupplier().getAg().getProducts().get(companyId);
        productExist(productId);
        orders.get(orderId).addProductToOrder(orderId, productId);
    }

    public void createProduct(String name, String manufacturer) throws Exception {
        manufacturers manu = manufacturers.valueOf(manufacturer);
        for (product p : products.values())
            if (p.getName().equals(name) && p.getManu().name().equals(manufacturer))//todo check the enum compare
                throw new Exception("product already exists in the system");
        products.put(productCounter, new product(name, productCounter, manu));
        productCounter++;
    }

    public product getProduct(int productID) throws Exception {
        productExist(productID);
        return products.get(productID);
    }

    public void createPermOrder(int day, supplier supplier) throws Exception {
        order order = createOrder(null, supplier);
        pernamentOrders.get(day).add(order);
    }
}
