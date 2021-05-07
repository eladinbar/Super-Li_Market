package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.CategorySaleDalController;
import DataAccessLayer.DalControllers.SupplierControllers.AgreementItemsDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;
import java.util.Map;

public class AgreementItems extends DalObject<AgreementItems> {
    public static final String productIdColumnName = "Product_ID";
    public static final String productCompIdColumnName = "Product_Company_ID";
    public static final String priceColumnName = "Price";
    private int productId;
    private int productCompId;
    private int price;

    public AgreementItems(int productId, int productCompId, int price) throws SQLException {
        super(AgreementItemsDalController.getInstance());
        this.productId = productId;
        this.productCompId = productCompId;
        this.price = price;
    }
}
