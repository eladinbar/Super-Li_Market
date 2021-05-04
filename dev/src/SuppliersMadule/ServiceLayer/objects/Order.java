package SuppliersMadule.ServiceLayer.objects;

import java.time.LocalDate;
import java.util.Map;

public class Order {
    private int id;
    private Map<Integer, Integer> products;
    private LocalDate date;
    private boolean delivered;
    private Supplier supplier;

    public Order(int id, LocalDate date, Supplier supplier, Map<Integer, Integer> products, boolean delivered) {
        this.id = id;
        this.products = products;
        this.date = date;
        this.delivered = delivered;
        this.supplier = supplier;
    }

    public Order(SuppliersMadule.BusinessLayer.orderPackage.Order order) {
        this.id = order.getId();
        this.products = order.getProducts();
        this.date = order.getDate();
        this.delivered = order.isDelivered();
        this.supplier = new SuppliersMadule.ServiceLayer.objects.Supplier(order.getSupplier());
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String proToString = "";
        for (Map.Entry<Integer, Integer> en : products.entrySet()) {
            proToString += "\nproduct id : " + en.getKey() + "       amount: " + en.getValue();
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
