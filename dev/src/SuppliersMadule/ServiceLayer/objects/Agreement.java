package SuppliersMadule.ServiceLayer.objects;

import java.util.Map;

public class Agreement {
    private Map<Integer, Integer> products;
    private Map<Integer, Integer> prices;
    private QuantityList ql;

    public Agreement(Map<Integer, Integer> products, Map<Integer, Integer> prices, QuantityList ql) {
        this.ql = ql;
        this.products = products;
        this.prices = prices;
    }

    public Agreement(SuppliersMadule.BusinessLayer.supplierPackage.Agreement ag) {
        this.prices = ag.getPrices();
        this.products = ag.getProducts();
        if (ag.getQl() == null)
            this.ql = null;
        else
            this.ql = new QuantityList(ag.getQl());
    }

    @Override
    public String toString() {
        String p = "";
        for (Map.Entry<Integer, Integer> e : products.entrySet()) {
            p += "product ID: " + e.getKey() + "\nproduct company id: " + e.getValue() + "\nproduct price: " + prices.get(e.getKey()) + "\n--------------------\n";
        }
        if (ql != null)
            return "agreement details:" +
                    "\nproducts: \n" + p +
                    "\n" + ql.toString();
        else
            return "agreement details:" +
                    "\nproducts: \n" + p +
                    "\nsupplier does not have a quantity list";

    }
}
