package BusinessLayer.SupliersPackage.orderPackage;

import BusinessLayer.SupliersPackage.supplierPackage.Supplier;
import DataAccessLayer.DalObjects.SupplierObjects.OrderDal;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Order {
    private int id;
    private Map<Integer, Integer> products;
    private LocalDate date;
    private boolean delivered;
    private Supplier supplier;
    private OrderDal dalObject;

    public Order(int id, LocalDate date, Supplier supplier) throws SQLException {
        this.id = id;
        this.products = new HashMap<>();
        this.date = date;
        this.delivered = false;
        this.supplier = supplier;
        this.dalObject = toDalObject();
        save();
    }

    public Order(int id){
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws SQLException {
        this.id = id;
        dalObject.setOrderId(id);
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) throws SQLException {
        this.date = date;
        dalObject.setDate(date);
    }

    public boolean isDelivered() {
        return delivered;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) throws SQLException {
        this.supplier = supplier;
        dalObject.setSupplierId(supplier.getSc().getId());
    }

    public void approveOrder() throws Exception {
        if (delivered)
            throw new Exception("order already delivered");
        delivered = true;
        dalObject.setDelivered(true);
    }

    public void addProductToOrder(int productId, int amount) throws Exception {
        if(!supplier.getAg().getProducts().containsKey(productId))
            throw new Exception("supplier does not supply the product :"+ productId);
        if(products.containsKey(productId))
            throw new Exception("product " +productId + " already exists in the order " + this.id + ", check if you want to edit the amount or remove");
        if (delivered)
            throw new Exception("order already delivered");
        if (amount < 1)
            throw new Exception("illegal amount");
        products.put(productId, amount);
        save(productId, id, amount);
    }

    public void removeProductFromOrder(int productID) throws Exception {
        if (delivered)
            throw new Exception("order already delivered");
        if (!products.containsKey(productID))
            throw new Exception("order does not contain the chosen product");
        products.remove(productID);
        delete(productID, id);
    }

    public Double getOrderTotalPrice() throws Exception {
        Double sum=0.0;
        for (int productID:products.keySet()) {
            sum+=supplier.getPrice(products.get(productID),productID);
        }
        return sum;
    }

    public Double getOrderTotalDiscount() throws Exception {
        Double sum=0.0;
        for (int productID:products.keySet()) {
            sum+=supplier.getProductDiscount(products.get(productID),productID);
        }
        return sum;
    }

    private OrderDal toDalObject() throws SQLException {
        return new OrderDal(id, supplier.getSc().getId(), date, delivered);
    }

    private boolean save() throws SQLException {
        return dalObject.save();
    }

    private boolean save (int productId, int orderId, int amount) throws SQLException {
        return dalObject.save(productId, orderId, amount);
    }

    protected boolean delete (int productId, int orderId) throws SQLException {
        return dalObject.delete(productId, orderId);
    }
    public boolean find() throws SQLException {
        return dalObject.find();
    }
}
