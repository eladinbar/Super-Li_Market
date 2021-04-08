package BusinessLayer.orderPackage;

import BusinessLayer.supplierPackage.supplier;

import java.time.LocalDate;
import java.util.*;

public class order {
    private int id;
    private Map<Integer, Integer> products;
    private LocalDate date;
    private boolean delivered;
    private supplier supplier;

    public order(int id, LocalDate date, supplier supplier) {
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

    public BusinessLayer.supplierPackage.supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(BusinessLayer.supplierPackage.supplier supplier) {
        this.supplier = supplier;
    }

    public void approveOrder() throws Exception {
        if (delivered)
            throw new Exception("order already delivered");
        delivered = true;
    }

    public void addProductToOrder(int orderId, int productId, int amount) {

    }
}
