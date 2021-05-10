package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.ProductsInOrderDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class ProductsInOrderDal extends DalObject<ProductsInOrderDal> {
    public static final String productIdColumnName = "Product_Id";
    public static final String orderIdColumnName = "Order_Id";
    public static final String amountColumnName = "Amount";
    private int productId; //foreign key (item) primary Key
    private int orderId; //foreign key (order) primary key
    private int amount;

    public ProductsInOrderDal(int productId, int orderId, int amount) throws SQLException {
        super(ProductsInOrderDalController.getInstance());
        this.productId = productId;
        this.orderId = orderId;
        this.amount = amount;
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

    public void setAmount(int amount) throws SQLException {
        this.amount = amount;
        update();
    }
}
