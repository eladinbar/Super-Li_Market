package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.ProductsInOrderDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class DalProductsInOrder extends DalObject<DalProductsInOrder> {
    public static final String productIdColumnName = "Product_Id";
    public static final String orderIdColumnName = "Order_Id";
    public static final String amountColumnName = "Amount";
    public static final String amountSuppliedColumnName = "Amount_Supplied";
    private int productId; //foreign key (item) primary Key
    private int orderId; //foreign key (order) primary key
    private int amount;
    private int amountSupplied;

    public DalProductsInOrder(int productId, int orderId, int amount, int amountSupplied) throws SQLException {
        super(ProductsInOrderDalController.getInstance());
        this.productId = productId;
        this.orderId = orderId;
        this.amount = amount;
        this.amountSupplied = amountSupplied;
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

    public int getAmountSupplied() {
        return amountSupplied;
    }

    public void setAmountSupplied(int amountSupplied) throws SQLException {
        this.amountSupplied = amountSupplied;
        update();
    }

    public void setAmountSuppliedLoad(int amountSupplied) {
        this.amountSupplied = amountSupplied;
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