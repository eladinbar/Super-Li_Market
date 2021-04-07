package ServiceLayer.objects;

import java.util.HashMap;
import java.util.Map;

public class quantityList {
    private Map<Integer, Integer> amount;
    private Map<Integer, Integer> discount;

    public quantityList(Map<Integer, Integer> amount, Map<Integer, Integer> discount) {
        this.amount = amount;
        this.discount = discount;
    }
}
