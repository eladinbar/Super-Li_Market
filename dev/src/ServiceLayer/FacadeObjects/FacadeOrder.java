package ServiceLayer.FacadeObjects;

import InfrastructurePackage.TextFormatter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FacadeOrder {
    private int id;
    private ArrayList<FacadeProduct> products;
    private LocalDate date;
    private boolean delivered;
    private FacadeSupplier supplier;
    private String day;

    public FacadeOrder(int id, LocalDate date, FacadeSupplier supplier, ArrayList<FacadeProduct> products, boolean delivered, int day) {
        this.id = id;
        this.products = products;
        this.date = date;
        this.delivered = delivered;
        this.supplier = supplier;
        this.day = conNumToDay(day);
    }

    public FacadeOrder(BusinessLayer.SuppliersPackage.OrderPackage.Order order, ArrayList<FacadeProduct> products, int day) {
        this.id = order.getId();
        this.products = products;
        this.date = order.getDate();
        this.delivered = order.isDelivered();
        this.supplier = new FacadeSupplier(order.getSupplier());
        this.day = "";
        if (date == null)
            this.day = conNumToDay(day);
    }

    public int getId() {
        return id;
    }

    public ArrayList<FacadeProduct> getProducts() {
        return products;
    }

    public Map<Integer,Integer> getProductMap(){
        Map<Integer,Integer> toReturn= new HashMap<>();
        for (FacadeProduct p: products) {
            toReturn.put(p.getProductID(),p.getAmount());
        }
        return  toReturn;
    }

    public FacadeSupplier getSupplier() {
        return supplier;
    }

    public LocalDate getDate() {
        return date;
    }


    @Override
    public String toString() {
        String proToString = "";
        for (FacadeProduct en : products) {
            proToString += en.toString() + "\n";
        }

        if (date == null)
            return String.format("order details:\nsupplier name: %s\t|\taddress:%s\t|\torder id : %d\nsupplier id: %s\t|\tpermanentDay: %s\t|\tphone number: %s\norder products:\n%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\n", supplier.getName(), supplier.getSc().getAddress(), id, supplier.getSc().id, day, supplier.getSc().phone, formatFix("item id"), formatFix("name"), formatFix("quantity"), formatFix("selling price"), formatFix("discount"), formatFix("final price")) + proToString;
        return String.format("order details:\nsupplier name: %s\t|\taddress:%s\t|\torder id : %d\nsupplier id: %s\t|\tdate: %s\t|\tphone number: %s\norder products:\n%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\t|\t%s\n", supplier.getName(), supplier.getSc().getAddress(), id, supplier.getSc().id, date, supplier.getSc().phone, formatFix("item id"), formatFix("name"), formatFix("quantity"), formatFix("selling price"), formatFix("discount"), formatFix("final price")) + proToString;
    }

    private String formatFix(String toFormat) {
        TextFormatter tf = new TextFormatter();
        return tf.centerString(toFormat, tf.getPaddingSize());
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

    public void setId(int id) {
        this.id = id;
    }

    public void setProducts(ArrayList<FacadeProduct> products) {
        this.products = products;
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

    public void setSupplier(FacadeSupplier supplier) {
        this.supplier = supplier;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
