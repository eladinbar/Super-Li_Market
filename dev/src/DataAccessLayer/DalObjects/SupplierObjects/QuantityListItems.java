package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.SupplierControllers.QuantityListItemsDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.util.Map;

public class QuantityListItems extends DalObject<QuantityListItems> {
    public final String productIdColumnName = "Product_Id";
    public final String amountColumnName = "Amount";
    public final String discountColumnName = "Discount";
    private int productId;
    private int amount;
    private int discount;

    public QuantityListItems(int productId, int amount, int discount) throws SQLException {
        super(QuantityListItemsDalController.getInstance());
        this.productId = productId;
        this.amount = amount;
        this.discount = discount;
    }
}
