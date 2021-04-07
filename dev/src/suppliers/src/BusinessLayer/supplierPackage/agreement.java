package BusinessLayer.supplierPackage;

import java.util.*;
import java.util.Scanner;

public class agreement {
    private Map<Integer, Integer> products;
    private Map<Integer, Integer> prices;
    private quantityList ql;

    public agreement() {
        this.ql = null;
        products = new HashMap<>();
        prices = new HashMap<>();
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    public Map<Integer, Integer> getPrices() {
        return prices;
    }

    public void setPrices(Map<Integer, Integer> prices) {
        this.prices = prices;
    }

    public quantityList getQl() {
        return ql;
    }

    public void setQl(quantityList ql) {
        this.ql = ql;
    }

    public void deleteQuantityListItem(int productID) throws Exception {
        if (ql == null)
            throw new Exception("supplier does not have a quantity list");
        if (!ql.getAmount().containsKey(productID))
            throw new Exception("supplier quantity list does not contain item " + productID);
        ql.getDiscount().remove(productID);
        ql.getAmount().remove(productID);
    }

    public void addQuantityListItem(int productID, int amount, int discount) throws Exception {
        if (ql == null)
            throw new Exception("supplier does not have quantity list");
        ql.addQuantityListItem(productID, amount, discount);
    }

    public void addQuantityList() throws Exception {
        if (ql != null)
            throw new Exception("supplier already have a quantity list");
        ql = new quantityList();
    }

    public void editQuantityListAmount(int productID, int amount) throws Exception {
        if (ql == null)
            throw new Exception("supplier does not have quantity list");
        ql.editQuantityListAmount(productID, amount);
    }

    public void editQuantityListDiscount(int productID, int discount) throws Exception {
        if (ql == null)
            throw new Exception("supplier does not have quantity list");
        ql.editQuantityListDiscount(productID, discount);
    }
}
