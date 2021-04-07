package ServiceLayer.objects;

import BusinessLayer.supplierPackage.supplier;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class order {
    private int id;
    private Map<Integer, Integer> products;
    private Date date;
    private boolean delivered;
    private supplier supplier;

    public order(int id, Date date, supplier supplier, Map<Integer, Integer> products, boolean delivered) {
        this.id = id;
        this.products = products;
        this.date = date;
        this.delivered = delivered;
        this.supplier = supplier;
    }
}
