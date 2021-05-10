package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.AgreementItemsDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class agreementItemsDal extends DalObject<agreementItemsDal> {
    public static final String productIdColumnName = "Product_ID";
    public static final String supplierIdColumnName = "Supplier_ID";
    public static final String productCompIdColumnName = "Product_Company_ID";
    public static final String priceColumnName = "Price";
    private int productId; //foreign key (item) primary key
    private int supplierId; //foreign key (supplierCard) primary key
    private int productCompId;
    private double price;

    public agreementItemsDal(int productId, int supplierId, int productCompId, int price) throws SQLException {
        super(AgreementItemsDalController.getInstance());
        this.productId = productId;
        this.supplierId = supplierId;
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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) throws SQLException {
        this.supplierId = supplierId;
        update();
    }

    public void setProductId(int productId) throws SQLException {
        this.productId = productId;
        update();
    }

    public void setProductCompId(int productCompId) throws SQLException {
        this.productCompId = productCompId;
        update();
    }

    public void setPrice(double price) throws SQLException {
        this.price = price;
        update();
    }
}
