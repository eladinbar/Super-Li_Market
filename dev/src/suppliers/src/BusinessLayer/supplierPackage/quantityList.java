package BusinessLayer.supplierPackage;

import java.util.HashMap;
import java.util.Map;

public class quantityList {
    private final int maxDiscount = 100;
    private Map<Integer, Integer> amount;
    private Map<Integer, Integer> discount;

    public quantityList() {
        amount=new HashMap<>();
        discount=new HashMap<>();
    }

    public Map<Integer, Integer> getAmount() {
        return amount;
    }

    public Map<Integer, Integer> getDiscount() {
        return discount;
    }

    public void addQuantityListItem(int productID, int amount, int discount) throws Exception {
        if (this.amount.containsKey(productID))
            throw new Exception("product already exists");
        if (amount < 1)
            throw new Exception("illegal amount");
        if (discount < 1 || discount >= maxDiscount)
            throw new Exception("illegal discount");
        this.amount.put(productID, amount);
        this.discount.put(productID, discount);
    }
}
