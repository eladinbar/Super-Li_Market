package SerciveLayer.Objects;

import InfrastructurePackage.TextFormatter;

import java.util.Map;

public class QuantityList {
    private Map<Integer, Integer> amount;
    private Map<Integer, Integer> discount;

    public QuantityList(Map<Integer, Integer> amount, Map<Integer, Integer> discount) {
        this.amount = amount;
        this.discount = discount;
    }
    public QuantityList(BusinessLayer.SuppliersPackage.SupplierPackage.QuantityList ql){
        this.amount=ql.getAmount();
        this.discount=ql.getDiscount();
    }

    @Override
    public String toString() {
        String amounts=String.format("quantity List details :\n%s\t|\t%s\t|\t%s\n",formatFix("product id"),formatFix("amount"),formatFix("discount"));
        for (Map.Entry<Integer,Integer> entry: amount.entrySet()) {
            amounts+=formatFix(""+entry.getKey())+"\t|\t"+formatFix(""+entry.getValue())+"\t|\t"+formatFix(""+discount.get(entry.getKey()))+"\n";
        }
        return amounts;
    }

    private String formatFix(String toFormat){
        TextFormatter tf=new TextFormatter();
        return tf.centerString(toFormat,tf.getPaddingSize());
    }
}
