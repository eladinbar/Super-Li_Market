package SerciveLayer.Objects;

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

    public Agreement(BusinessLayer.SuppliersPackage.SupplierPackage.Agreement ag) {
        this.prices = ag.getPrices();
        this.products = ag.getProducts();
        if (ag.getQl() == null)
            this.ql = null;
        else
            this.ql = new QuantityList(ag.getQl());
    }

    @Override
    public String toString() {
        String p = "Agreement details:\nproduct ID\t|\tproduct company id\t|\tproduct price\n";
        for (Map.Entry<Integer, Integer> e : products.entrySet()) {
            p += e.getKey() + "\t|\t" + e.getValue() + "\t|\t" + prices.get(e.getKey()) + "\n";
        }
        if (ql != null)
            return p + ql.toString();
        else
            return p + "\nsupplier does not have a quantity list";
    }
}
