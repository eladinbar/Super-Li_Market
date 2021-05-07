package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

import java.util.Map;

public class QuantityList extends DalObject<QuantityList> {
    private Map<Integer, Integer> amount;
    private Map<Integer, Integer> discount;

    public QuantityList(DalController<QuantityList> controller, Map<Integer, Integer> amount, Map<Integer, Integer> discount) {
        super(controller);
        this.amount = amount;
        this.discount = discount;
    }
}
