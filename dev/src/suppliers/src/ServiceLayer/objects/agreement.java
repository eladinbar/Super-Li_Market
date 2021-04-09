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

    @Override
    public String toString() {
        String p="";
        for (Map.Entry<Integer,Integer> e:products.entrySet()) {
            p+="product ID: "+e.getKey()+"\nproduct company id: "+e.getValue()+"\nproduct price: "+prices.get(e.getKey())+"\n";
        }
        return "agreement details:" +
                "\nproducts: \n" + p +
                "\n"+ql.toString();
    }
}
