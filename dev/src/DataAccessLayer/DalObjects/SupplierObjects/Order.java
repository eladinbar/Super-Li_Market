package DataAccessLayer.DalObjects.SupplierObjects;

import BusinessLayer.SupliersPackage.supplierPackage.Supplier;
import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.SupplierControllers.OrderDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class Order extends DalObject<Order> {
    public final String orderIdColumnName = "Order_ID";
    public final String dateColumnName = "Date";
    public final String deliveredColumnName = "Delivered";
    private int id;
    private LocalDate date;
    private boolean delivered;

    public Order( int id, LocalDate date, boolean delivered) throws SQLException {
        super(OrderDalController.getInstance());
        this.id = id;
        this.date = date;
        this.delivered = delivered;
    }
}
