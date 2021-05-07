package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

import java.util.Map;

public class Agreement extends DalObject<Agreement> {
    private Map<Integer, Integer> products;
    private Map<Integer, Integer> prices;

    public Agreement(DalController<Agreement> controller, Map<Integer, Integer> products, Map<Integer, Integer> prices) {
        super(controller);
        this.products = products;
        this.prices = prices;
    }
}
