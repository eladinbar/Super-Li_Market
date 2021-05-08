package DataAccessLayer.DalObjects.SupplierObjects;

import BusinessLayer.SupliersPackage.supplierPackage.Supplier;
import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.SupplierControllers.OrderDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;

public class Order extends DalObject<Order> {
    public static final String orderIdColumnName = "Order_ID";
    public static final String dateColumnName = "Date";
    public static final String deliveredColumnName = "Delivered";
    private int id; //primary key
    private LocalDate date;
    private boolean delivered;

    public Order( int id, LocalDate date, boolean delivered) throws SQLException {
        super(OrderDalController.getInstance());
        this.id = id;
        this.date = date;
        this.delivered = delivered;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date.toString();
    }

    public int isDelivered() {
        if (delivered)
            return 1;
        return 0;
    }
}
