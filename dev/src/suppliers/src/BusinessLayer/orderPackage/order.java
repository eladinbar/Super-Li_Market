package BusinessLayer.orderPackage;
import BusinessLayer.supplierPackage.supplier;

import  java.util.*;
public class order {
    private int id;
    private  Map<int,int> products;
    private Date date;
    private boolean delivered;
    private supplier supplier;

    public order(int id, Date date, supplier supplier) {
        this.id = id;
        this.products = new HashMap<int,int>();
        while(true){
            //todo add items
            break;
        }
        this.date = date;
        this.delivered = false;
        this.supplier = supplier;
    }
}
