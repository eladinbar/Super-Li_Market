package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.QuantityListItemsDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class DalQuantityListItems extends DalObject<DalQuantityListItems> {
    public static final String productIdColumnName = "Product_Id";
    public static final String supplierIdColumnName = "Supplier_Id";
    public static final String amountColumnName = "Amount";
    public static final String discountColumnName = "Discount";
    private int productId; // foreign key (item) primary key
    private String supplierId; //foreign key (supplierCard) primary key
    private int amount;
    private double discount;

    public DalQuantityListItems(int productId, String supplierId, int amount, int discount) throws SQLException {
        super(QuantityListItemsDalController.getInstance());
        this.productId = productId;
        this.supplierId = supplierId;
        this.amount = amount;
        this.discount = discount;
    }
    public DalQuantityListItems(String supplierId) throws SQLException {
        super(QuantityListItemsDalController.getInstance());
        this.supplierId = supplierId;
    }

    public int getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }

    public double getDiscount() {
        return discount;
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

    public void setDiscount(double discount) throws SQLException {
        this.discount = discount;
        update();
    }

    public void setDiscountLoad(double discount) throws SQLException {
        this.discount = discount;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) throws SQLException {
        this.supplierId = supplierId;
        update();
    }

    public void setSupplierIdLoad(String supplierId) throws SQLException {
        this.supplierId = supplierId;
    }
}
