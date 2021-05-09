package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.AgreementItemsDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class agreementItemsDal extends DalObject<agreementItemsDal> {
    public static final String productIdColumnName = "Product_ID";
    public static final String productCompIdColumnName = "Product_Company_ID";
    public static final String priceColumnName = "Price";
    private int productId; //foreign key (item)
    private int productCompId;
    private double price;

    public agreementItemsDal(int productId, int productCompId, int price) throws SQLException {
        super(AgreementItemsDalController.getInstance());
        this.productId = productId;
        this.productCompId = productCompId;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public int getProductCompId() {
        return productCompId;
    }

    public double getPrice() {
        return price;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setProductCompId(int productCompId) {
        this.productCompId = productCompId;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
