package BusinessLayer.SupliersPackage.orderPackage;

import BusinessLayer.SupliersPackage.supplierPackage.Supplier;

import java.time.LocalDate;
import java.util.*;

public class Order {
    private int id;
    private Map<Integer, Integer> products;
    private LocalDate date;
    private boolean delivered;
    private Supplier supplier;

    public Order(int id, LocalDate date, Supplier supplier) {
        this.id = id;
        this.products = new HashMap<>();
        this.date = date;
        this.delivered = false;
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public BusinessLayer.SupliersPackage.supplierPackage.Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(BusinessLayer.SupliersPackage.supplierPackage.Supplier supplier) {
        this.supplier = supplier;
    }

    public void approveOrder() throws Exception {
        if (delivered)
            throw new Exception("order already delivered");
        delivered = true;
    }

    public void addProductToOrder(int orderId, int productId, int amount) throws Exception {
        if (delivered)
            throw new Exception("order already delivered");
        if (amount < 1)
            throw new Exception("illegal amount");
        products.put(productId, amount);
    }

    public void removeProductFromOrder(int productID) throws Exception {
        if (delivered)
            throw new Exception("order already delivered");
        if (!products.containsKey(productID))
            throw new Exception("order does not contain choosen product");
        products.remove(productID);
    }
}
