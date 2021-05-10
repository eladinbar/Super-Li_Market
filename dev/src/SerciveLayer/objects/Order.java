package SerciveLayer.objects;

import InfrastructurePackage.TextFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

public class Order {
    private int id;
    private ArrayList<Product> products;
    private LocalDate date;
    private boolean delivered;
    private Supplier supplier;
    private String day;
    private String address;
    //todo make sure adress added !! in to string too
    public Order(int id, LocalDate date, Supplier supplier, ArrayList<Product> products, boolean delivered, int day) {
        this.id = id;
        this.products = products;
        this.date = date;
        this.delivered = delivered;
        this.supplier = supplier;
        this.day = conNumToDay(day);
    }

    public Order(BusinessLayer.SupliersPackage.orderPackage.Order order, ArrayList<Product> products, int day) {
        this.id = order.getId();
        this.products = products;
        this.date = order.getDate();
        this.delivered = order.isDelivered();
        this.supplier = new Supplier(order.getSupplier());
        this.day = "";
        if(date==null)
            this.day=conNumToDay(day);
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
            proToString += en.toString() + "\n";
        }

        if (date == null)
            return String.format("order details:\nsupplier name: %s\t|\taddress:%s\t|\torder id : %d\nsupplier id: %s\t|\tpermanentDay: %s\t|\tphone number: %s\norder products:\n%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\n", supplier.getName(), "**insertaddress**", id, supplier.getSc().id, day, supplier.getSc().phone,formatFix("item id"),formatFix("name"),formatFix("quantity"),formatFix("selling price"),formatFix("discount"),formatFix("final price")) + proToString;
        return String.format("order details:\nsupplier name: %s\t|\taddress:%s\t|\torder id : %d\nsupplier id: %s\t|\tdate: %s\t|\tphone number: %s\norder products:\n%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\n", supplier.getName(), "**insertaddress**", id, supplier.getSc().id, date, supplier.getSc().phone,formatFix("item id"),formatFix("name"),formatFix("quantity"),formatFix("selling price"),formatFix("discount"),formatFix("final price")) + proToString;
    }

    private String formatFix(String toFormat){
        TextFormatter tf=new TextFormatter();
        return tf.centerString(toFormat,tf.getPaddingSize());
    }

    private String conNumToDay(int num) {
        String toReturn = "";
        switch (num) {
            case 1 -> toReturn += "Sunday";
            case 2 -> toReturn += "Monday";
            case 3 -> toReturn += "Tuesday";
            case 4 -> toReturn += "Wednesday";
            case 5 -> toReturn += "Thursday";
            case 6 -> toReturn += "Friday";
            case 7 -> toReturn += "Saturday";
        }
        return toReturn;
    }
}
