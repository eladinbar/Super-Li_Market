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
    public quantityList(BusinessLayer.supplierPackage.quantityList ql){
        this.amount=ql.getAmount();
        this.discount=ql.getDiscount();
    }

    @Override
    public String toString() {
        String amounts="quantity List details :";
        for (Map.Entry<Integer,Integer> entry: amount.entrySet()) {
            amounts+="\nitem ID: "+entry.getKey()+"\nitem amount to get discount: "+entry.getValue()+"\nitem discount: "+discount.get(entry.getKey())+"\n";
        }
        return amounts;
    }
}
