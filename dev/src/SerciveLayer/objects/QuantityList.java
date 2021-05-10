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
        String amounts="quantity List details :\nPID\t|\tamount\t|\tdiscount\n";
        for (Map.Entry<Integer,Integer> entry: amount.entrySet()) {
            amounts+=entry.getKey()+"\t|\t"+entry.getValue()+"\t|\t"+discount.get(entry.getKey())+"\n";
        }
        return amounts;
    }
}
