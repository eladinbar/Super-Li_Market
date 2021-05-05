package BusinessLayer.SupliersPackage.supplierPackage;

import java.util.HashMap;
import java.util.Map;

public class QuantityList {
    private final int maxDiscount = 100;
    private Map<Integer, Integer> amount;
    private Map<Integer, Integer> discount;

    public QuantityList() {
        amount = new HashMap<>();
        discount = new HashMap<>();
    }

    public Map<Integer, Integer> getAmount() {
        return amount;
    }

    public Map<Integer, Integer> getDiscount() {
        return discount;
    }

    public void addQuantityListItem(int productID, int amount, int discount) throws Exception {
        checkAmount(amount);
        checkDiscount(discount);
        if (this.amount.containsKey(productID))
            throw new Exception("product already exists in quantity list");
        this.amount.put(productID, amount);
        this.discount.put(productID, discount);
    }

    private void productExists(int productID) throws Exception {
        if (!amount.containsKey(productID))
            throw new Exception("quantity list does not have the product " + productID);
    }

    private void checkAmount(int amount) throws Exception {
        if (amount < 1)
            throw new Exception("illegal amount");
    }

    private void checkDiscount(int discount) throws Exception {
        if (discount < 1 || discount >= maxDiscount)
            throw new Exception("illegal discount");
    }

    public void editQuantityListAmount(int productID, int amount) throws Exception {
        productExists(productID);
        checkAmount(amount);
        this.amount.remove(productID);
        this.amount.put(productID, amount);
    }

    public void editQuantityListDiscount(int productID, int discount) throws Exception {
        productExists(productID);
        checkAmount(discount);
        this.discount.remove(productID);
        this.discount.put(productID, discount);
    }
}
