package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.OrderDalController;
import DataAccessLayer.DalControllers.SupplierControllers.ProductsInOrderDalController;
import DataAccessLayer.DalControllers.SupplierControllers.SupplierContactMembersDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.time.LocalDate;

public class OrderDal extends DalObject<OrderDal> {
    public static final String orderIdColumnName = "Order_ID";
    public static final String supplierIdColumnName = "Supplier_ID";
    public static final String dateColumnName = "Date";
    public static final String deliveredColumnName = "Delivered";
    private int orderId; //primary key
    private String supplierId;
    private LocalDate date;
    private boolean delivered;

    public OrderDal(int orderId, String supplierId, LocalDate date, boolean delivered) throws SQLException {
        super(OrderDalController.getInstance());
        this.orderId = orderId;
        this.supplierId = supplierId;
        this.date = date;
        this.delivered = delivered;
    }

    public OrderDal(int orderId) throws SQLException {
        super(OrderDalController.getInstance());
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDate() {
        return date.toString();
    }

    public String getSupplierId() {
        return supplierId;
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

    public void setSupplierId(String supplierId) throws SQLException {
        this.supplierId = supplierId;
        update();
    }

    public void setDate(String date) throws SQLException {
        this.date = LocalDate.parse(date);
        update();
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

    public void setDelivered(boolean delivered) throws SQLException {
        this.delivered=delivered;
        update();
    }

    public boolean save(int productId, int orderId, int amount) throws SQLException {
        return ProductsInOrderDalController.getInstance().insert(new ProductsInOrderDal(productId, orderId,amount));
    }

    public boolean delete (int productId, int orderId) throws SQLException {
        return ProductsInOrderDalController.getInstance().delete(new ProductsInOrderDal(productId, orderId, 0));
    }
}
