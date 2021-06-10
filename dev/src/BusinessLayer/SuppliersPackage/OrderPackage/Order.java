package BusinessLayer.SuppliersPackage.OrderPackage;

import BusinessLayer.SuppliersPackage.SupplierPackage.Supplier;
import DataAccessLayer.DalObjects.SupplierObjects.DalOrder;
import DataAccessLayer.DalObjects.SupplierObjects.DalProductsInOrder;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

public class Order {
    private int id;
    private Map<Integer, Integer> products;
    private LocalDate date;
    private boolean delivered;
    private Supplier supplier;
    private DalOrder dalObject;
    private int day;

    public Order(int id, LocalDate date, Supplier supplier, int day) throws SQLException {
        this.id = id;
        this.products = new HashMap<>();
        this.date = date;
        this.delivered = false;
        this.supplier = supplier;
        this.dalObject = toDalObject();
        this.day = day;
        save();
    }

    public Order(DalOrder orderDal, Supplier supplier) throws Exception {
        this.products = new HashMap<>();
        this.id = orderDal.getOrderId();
        this.delivered = orderDal.isDelivered() == 1 ? true : false;
        this.date = LocalDate.parse(orderDal.getDate());
        this.supplier = supplier;
        this.day = orderDal.getDay();
        this.dalObject = toDalObject();
        readOrderProducts();
    }

    public Order(int id) throws SQLException {
        this.products = new HashMap<>();
        this.id = id;
        this.dalObject = new DalOrder(id);
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

    /**
     * add product to order flag=true means its a new product, flag=false means no need to save on dal
     */
    public void addProductToOrder(int productId, int amount, boolean flag) throws Exception {
        if (flag)
            checkDateToUpdate();
        if (!supplier.getAg().getProducts().containsKey(productId))
            throw new Exception("supplier does not supply the product :" + productId);
        if (products.containsKey(productId))
            throw new Exception("product " + productId + " already exists in the order " + this.id + ", check if you want to edit the amount or remove");
        if (delivered&&flag)
            throw new Exception("order already delivered");
        if (amount < 1)
            throw new Exception("illegal amount");
        products.put(productId, amount);
        if (flag)
            save(productId, id, amount);
    }

    public void removeProductFromOrder(int productID) throws Exception {
        checkDateToUpdate();
        if (delivered)
            throw new Exception("order already delivered");
        if (!products.containsKey(productID))
            throw new Exception("order does not contain the chosen product");
        products.remove(productID);
        delete(productID, id);
    }

    public Double getOrderTotalPrice() throws Exception {
        Double sum = 0.0;
        for (int productID : products.keySet()) {
            sum += supplier.getPrice(products.get(productID), productID);
        }
        return sum;
    }

    public Double getOrderTotalDiscount() throws Exception {
        Double sum = 0.0;
        for (int productID : products.keySet()) {
            sum += supplier.getProductDiscount(products.get(productID), productID);
        }
        return sum;
    }

    private void readOrderProducts() throws Exception {
        List<DalProductsInOrder> orderItems = new ArrayList();
        DalProductsInOrder productInOrderDal = new DalProductsInOrder(id);
        productInOrderDal.find(orderItems);
        for (DalProductsInOrder product : orderItems) {
            addProductToOrder(product.getProductId(), product.getAmount(), false);
        }
    }

    private DalOrder toDalObject() throws SQLException {
        return new DalOrder(id, supplier.getSc().getId(), date, delivered, day);
    }

    private boolean save() throws SQLException {
        return dalObject.save();
    }

    private boolean save(int productId, int orderId, int amount) throws SQLException {
        return dalObject.save(productId, orderId, amount);
    }

    protected boolean delete(int productId, int orderId) throws SQLException {
        return dalObject.delete(productId, orderId);
    }

    public boolean find() throws SQLException {
        return dalObject.find();
    }

    private void checkDateToUpdate() throws Exception {
        day = day == 7 ? 1 : day + 1;
        int yesterday = day == 1 ? 7 : day - 1;
        if (day > 0) {
            if (LocalDate.now().getDayOfWeek() == DayOfWeek.of(day) || LocalDate.now().getDayOfWeek() == DayOfWeek.of(yesterday))
                throw new Exception("order can be updated at most one day before the order");
        } else {
            if (LocalDate.now().isAfter(date.minusDays(2)))
                throw new Exception("order can be updated at most one day before the order");
        }
    }
}
