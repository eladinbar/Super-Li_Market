package BusinessLayer.supplierPackage;

import java.util.HashMap;
import java.util.Map;

public class quantityList {
    private Map<Integer, Integer> amount;
    private Map<Integer, Integer> discount;
    public quantityList() {
        amount=new HashMap<>();
        discount=new HashMap<>();
    }
}
