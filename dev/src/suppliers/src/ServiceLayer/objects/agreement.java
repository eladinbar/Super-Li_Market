package ServiceLayer.objects;

import java.util.HashMap;
import java.util.Map;

public class agreement {
    private Map<Integer, Integer> products;
    private Map<Integer, Integer> prices;
    private quantityList ql;

    public agreement(Map<Integer, Integer> products, Map<Integer, Integer> prices, ServiceLayer.objects.quantityList ql) {
        this.ql = ql;
        this.products = products;
        this.prices = prices;
    }

    public agreement(BusinessLayer.supplierPackage.agreement ag) {
        this.prices = ag.getPrices();
        this.products = ag.getProducts();
        if (ag.getQl() == null)
            this.ql = null;
        else
            this.ql = new quantityList(ag.getQl());
    }
}