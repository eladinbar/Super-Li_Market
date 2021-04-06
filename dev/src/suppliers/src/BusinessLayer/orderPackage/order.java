package BusinessLayer.orderPackage;
import BusinessLayer.supplierPackage.supplier;

import  java.util.*;
public class order {
    private int id;
    private  Map<Integer, Integer> products;
    private Date date;
    private boolean delivered;
    private supplier supplier;

    public order(int id, Date date, supplier supplier) {
        this.id = id;
        this.products = new HashMap<>();
        while(true){
            //todo add items
            break;
        }
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
}
