package SerciveLayer.objects;

import java.util.Map;

public class QuantityList {
    private Map<Integer, Integer> amount;
    private Map<Integer, Integer> discount;

    public QuantityList(Map<Integer, Integer> amount, Map<Integer, Integer> discount) {
        this.amount = amount;
        this.discount = discount;
    }
    public QuantityList(BusinessLayer.SupliersPackage.supplierPackage.QuantityList ql){
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
