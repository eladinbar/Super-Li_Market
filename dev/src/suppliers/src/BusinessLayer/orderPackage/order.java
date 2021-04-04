package BusinessLayer.orderPackage;
import BusinessLayer.supplierPackage.supplier;

import  java.util.*;
public class order {
    private int id;
    private  Map<int,int> products;
    private Date date;
    private boolean delivered;
    private supplier supplier;

    public order(int id, Map<int, int> products, Date date, boolean delivered, BusinessLayer.supplierPackage.supplier supplier) {
        this.id = id;
        this.products = products;
        this.date = date;
        this.delivered = delivered;
        this.supplier = supplier;
    }
}
