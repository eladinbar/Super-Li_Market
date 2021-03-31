package BusinessLayer.supplierPackage;

import java.util.HashMap;
import java.util.Map;

public class agreement {
    private Map<Integer, Integer> products;
    private Map<Integer, Integer> prices;
    private quantityList ql;

    public agreement(quantityList ql) {
        this.ql = ql;
        products=new HashMap<>();
        prices=new HashMap<>();
    }
}
