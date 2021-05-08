package SerciveLayer.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Order {
    private int id;
    private ArrayList<Product> products;
    private LocalDate date;
    private boolean delivered;
    private Supplier supplier;

    public Order(int id, LocalDate date, Supplier supplier, ArrayList<Product> products, boolean delivered) {
        this.id = id;
        this.products = products;
        this.date = date;
        this.delivered = delivered;
        this.supplier = supplier;
    }

    public Order(BusinessLayer.SupliersPackage.orderPackage.Order order, ArrayList<Product> products) {
        this.id = order.getId();
        this.products = products;
        this.date = order.getDate();
        this.delivered = order.isDelivered();
        this.supplier = new Supplier(order.getSupplier());
    }

    public int getId() {
        return id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public LocalDate getDate() {
        return date;
    }


    @Override
    public String toString() {
        String proToString = "";
        for (Product en : products) {
            proToString += "\nproduct id : " + en.getProductID() + "       amount: " + en.getAmount();
        }

        if (date == null) {
            return "order details: " +
                    "\nid: " + id + " " +
                    "\nproducts: " + proToString +
                    "\ndelivered: " + delivered +
                    "\nsupplier: " + supplier.getName();
        }

        return "order details: " +
                "\nid: " + id + " " +
                "\nproducts: " + proToString +
                "\ndate: " + date.toString() +
                "\ndelivered: " + delivered +
                "\nsupplier: " + supplier.getName();
    }
}
