package DataAccessLayer.DalObjects.SupplierObjects;

import BusinessLayer.SupliersPackage.supplierPackage.Supplier;
import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

import java.time.LocalDate;
import java.util.Map;

public class Order extends DalObject<Order> {
    private int id;
    private Map<Integer, Integer> products;
    private LocalDate date;
    private boolean delivered;

    protected Order(DalController<Order> controller) {
        super(controller);
    }

    public Order(DalController<Order> controller, int id, Map<Integer, Integer> products, LocalDate date, boolean delivered) {
        super(controller);
        this.id = id;
        this.products = products;
        this.date = date;
        this.delivered = delivered;
    }
}
