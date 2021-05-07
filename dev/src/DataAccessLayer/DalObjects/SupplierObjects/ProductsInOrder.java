package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.SupplierControllers.ProductsInOrderDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.util.Map;

public class ProductsInOrder extends DalObject<ProductsInOrder> {
    public static final String productIdColumnName = "Product_Id";
    public static final String amountColumnName = "Amount";
    private int productId;
    private int amount;

    public ProductsInOrder(int productId, int amount) throws SQLException {
        super(ProductsInOrderDalController.getInstance());
        this.productId = productId;
        this.amount = amount;
    }
}
