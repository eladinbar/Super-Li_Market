package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.ProductsInOrderDalController;
import DataAccessLayer.DalObjects.DalObject$;

import java.sql.SQLException;

public class DalProductsInOrder extends DalObject$<DalProductsInOrder> {
    public static final String productIdColumnName = "Product_Id";
    public static final String orderIdColumnName = "Order_Id";
    public static final String amountColumnName = "Amount";
    private int productId; //foreign key (item) primary Key
    private int orderId; //foreign key (order) primary key
    private int amount;

    public DalProductsInOrder(int productId, int orderId, int amount) throws SQLException {
        super(ProductsInOrderDalController.getInstance());
        this.productId = productId;
        this.orderId = orderId;
        this.amount = amount;
    }

    public DalProductsInOrder(int orderId) throws SQLException {
        super(ProductsInOrderDalController.getInstance());
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public int getAmount() {
        return amount;
    }

    public void setProductId(int productId) throws SQLException {
        this.productId = productId;
        update();
    }

    public void setProductIdLoad(int productId) throws SQLException {
        this.productId = productId;
    }

    public void setAmount(int amount) throws SQLException {
        this.amount = amount;
        update();
    }

    public void setAmountLoad(int amount) throws SQLException {
        this.amount = amount;
    }
}
