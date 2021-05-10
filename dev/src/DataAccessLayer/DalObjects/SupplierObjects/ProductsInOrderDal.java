package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.ProductsInOrderDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class ProductsInOrderDal extends DalObject<ProductsInOrderDal> {
    public static final String productIdColumnName = "Product_Id";
    public static final String amountColumnName = "Amount";
    private int productId; // foreign key (item)
    private int amount;

    public ProductsInOrderDal(int productId, int amount) throws SQLException {
        super(ProductsInOrderDalController.getInstance());
        this.productId = productId;
        this.amount = amount;
    }

    public int getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
