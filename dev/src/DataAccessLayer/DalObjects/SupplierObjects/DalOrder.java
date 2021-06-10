package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.OrderDalController;
import DataAccessLayer.DalControllers.SupplierControllers.ProductsInOrderDalController;
import DataAccessLayer.DalObjects.DalObject$;

import java.sql.SQLException;
import java.time.LocalDate;

public class DalOrder extends DalObject$<DalOrder> {
    public static final String orderIdColumnName = "Order_ID";
    public static final String supplierIdColumnName = "Supplier_ID";
    public static final String dateColumnName = "Date";
    public static final String deliveredColumnName = "Delivered";
    public static final String dayColumnName = "Day";

    private int orderId; //primary key
    private String supplierId;
    private LocalDate date;
    private boolean delivered;
    private int day;

    public DalOrder(int orderId, String supplierId, LocalDate date, boolean delivered, int day) throws SQLException {
        super(OrderDalController.getInstance());
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.date = date;
        this.delivered = delivered;
        this.day = day;
    }

    public DalOrder(int orderId) throws SQLException {
        super(OrderDalController.getInstance());
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date == null ? null : date.toString();
    }

    public String getSupplierId() {
        return supplierId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) throws SQLException {
        this.day = day;
        update();
    }

    public void setDayLoad(int day) throws SQLException {
        this.day = day;
    }

    public int isDelivered() {
        if (delivered)
            return 1;
        return 0;
    }

    public void setOrderId(int orderId) throws SQLException {
        this.orderId = orderId;
        update();
    }

    public void setOrderIdLoad(int orderId) throws SQLException {
        this.orderId = orderId;
    }

    public void setSupplierId(String supplierId) throws SQLException {
        this.supplierId = supplierId;
        update();
    }

    public void setSupplierIdLoad(String supplierId) throws SQLException {
        this.supplierId = supplierId;
    }

    public void setDate(String date) throws SQLException {
        this.date = LocalDate.parse(date);
        update();
    }

    public void setDateLoad(String date) throws SQLException {
        this.date = LocalDate.parse(date);
    }

    public void setDate(LocalDate date) throws SQLException {
        this.date=date;
        update();
    }

    public void setDelivered(int delivered) throws SQLException {
        if (delivered==1)
            this.delivered=true;
        else
            this.delivered=false;
        update();
    }

    public void setDeliveredLoad(int delivered) throws SQLException {
        if (delivered==1)
            this.delivered=true;
        else
            this.delivered=false;
    }

    public void setDelivered(boolean delivered) throws SQLException {
        this.delivered=delivered;
        update();
    }

    public boolean save(int productId, int orderId, int amount) throws SQLException {
        return ProductsInOrderDalController.getInstance().insert(new DalProductsInOrder(productId, orderId,amount));
    }

    public boolean delete (int productId, int orderId) throws SQLException {
        return ProductsInOrderDalController.getInstance().delete(new DalProductsInOrder(productId, orderId, 0));
    }

    public int getOrderCounter() throws SQLException {
        return controller.getOrderCounter();
    }
}
