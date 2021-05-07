package SerciveLayer;

import SerciveLayer.Response.Response;
import SerciveLayer.Response.ResponseT;
import SerciveLayer.SimpleObjects.Item;
import SerciveLayer.objects.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class Service implements IService {
    private OrderService orderService;
    private SupplierService supplierService;
    private InventoryService inventoryService;

    public Service() {
        this.supplierService = new SupplierService();
        this.orderService = new OrderService();
        this.inventoryService = new InventoryServiceImpl();
    }

    @Override
    public ResponseT<Supplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment) {
        return supplierService.addSupplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment);
    }

    @Override
    public Response removeSupplier(String id) {
        orderService.removeSupplier(id);
        return supplierService.removeSupplier(id);
    }

    @Override
    public Response updateFirstName(String id, String firstName) {
        return supplierService.updateFirstName(id, firstName);
    }

    @Override
    public Response updateLastName(String id, String lastName) {
        return supplierService.updateLastName(id, lastName);
    }

    @Override
    public Response updatePhone(String id, String phone) {
        return supplierService.updatePhone(id, phone);
    }

    @Override
    public Response updateEmail(String id, String email) {
        return supplierService.updateEmail(id, email);
    }

    @Override
    public Response updateCompanyNumber(String id, int companyNumber) {
        return supplierService.updateCompanyNumber(id, companyNumber);
    }

    @Override
    public Response updateSelfDelivery(String id, boolean self) {
        return supplierService.updateSelfDelivery(id, self);
    }

    @Override
    public Response updatePernamentDays(String id, boolean perm) {
        return supplierService.updatePernamentDays(id, perm);
    }

    @Override
    public Response updatePayment(String id, String pay) {
        return supplierService.updatePayment(id, pay);
    }

    @Override
    public Response addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone) {
        return supplierService.addContactMember(supplierId, firstName, lastName, email, memberID, phone);
    }

    @Override
    public Response deleteContactMember(String supplierID, String memberID) {
        return supplierService.deleteContactMember(supplierID, memberID);
    }

    @Override
    public ResponseT<Supplier> getSupplier(String id) {
        return supplierService.getSupplier(id);
    }

    @Override
    public ResponseT<QuantityList> addQuantityList(String supplierID) {
        return supplierService.addQuantityList(supplierID);
    }

    @Override
    public Response editQuantityListAmount(String supplierID, int productID, int amount) {
        return supplierService.editQuantityListAmount(supplierID, productID, amount);
    }

    @Override
    public Response editQuantityListDiscount(String supplierID, int productID, int discount) {
        return supplierService.editQuantityListDiscount(supplierID, productID, discount);
    }

    @Override
    public Response deleteQuantityList(String supplierID) {
        return supplierService.deleteQuantityList(supplierID);
    }

    @Override
    public ResponseT<Item> addQuantityListItem(String supplierID, int productID, int amount, int discount) {
        ResponseT<Item> r = inventoryService.getItem(productID);
        if (!r.errorOccurred()) {
            ResponseT<Item> rp = supplierService.addQuantityListItem(supplierID, productID, amount, discount, inventoryService);
            if(rp.errorOccurred())
                return new ResponseT<>(rp.getErrorMessage());
        }
        return r;
    }

    public ResponseT<Item> addItemToAgreement(String id, int productID, int companyProductID, int price) {
        ResponseT<Item> r = inventoryService.getItem(productID);
        if (!r.errorOccurred()) {
            ResponseT<Item> rp = supplierService.addItemToAgreement(id, productID, companyProductID, price, inventoryService);
            if(rp.errorOccurred())
                return new ResponseT<>(rp.getErrorMessage());
        }
        return r;
    }

    @Override
    public Response removeItemFromAgreement(String supplierId, int productId) {
        return supplierService.removeItemFromAgreement(supplierId, productId);
    }

    @Override
    public Response editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID) {
        return supplierService.editAgreementItemCompanyProductID(supplierID, productID, companyProductID);
    }

    @Override
    public Response editAgreementItemPrice(String supplierID, int productID, int price) {
        return supplierService.editAgreementItemPrice(supplierID, productID, price);
    }

    @Override
    public ResponseT<QuantityList> getQuantityList(String supplierId) {
        return supplierService.getQuantityList(supplierId);
    }

    @Override
    public ResponseT<List<Order>> createShortageOrder(LocalDate date) {//todo check again
        ResponseT<Map<Integer,Integer>> itemInShort = inventoryService.getItemsInShortAndQuantities();
        if(itemInShort.errorOccurred())
            return new ResponseT<>(itemInShort.getErrorMessage());
        ResponseT<Map<String,Map<Integer,Integer>>> r = supplierService.createShortageOrders(itemInShort.value);
        if(r.errorOccurred())
            return new ResponseT<>(r.getErrorMessage());
        ResponseT<List<Order>> orderR = orderService.createShortageOrders(r.value,date,supplierService.getSp());
        return orderR;
    }

    @Override
    public ResponseT<Order> createScheduledOrder(int day, int itemID, int amount) {
        ResponseT<Supplier> cheap = supplierService.getCheapestSupplier(itemID,amount,true);
        if(cheap.errorOccurred())
            return new ResponseT<>(cheap.getErrorMessage());
        Supplier s = cheap.value;
        ResponseT<Order> scheduledOrder = orderService.createPernamentOrder(day,s.getSc().getId(), supplierService.getSp());
        if(scheduledOrder.errorOccurred())
            return new ResponseT<>(cheap.getErrorMessage());
        Response addItemResp = orderService.addProductToOrder(scheduledOrder.value.getId(),itemID,amount);
        //todo: check the case if the item already exists in the order and handle it accordingly
        if(addItemResp.errorOccurred())
            return new ResponseT<>(true, "Supplier already has the item in the order, check if you want to edit the amount." +
                    "\nOrder ID to edit : " + scheduledOrder.value.getId(),null);

        return getOrder(scheduledOrder.value.getId());
    }

    @Override
    public Response deleteQuantityListItem(String supplierID, int productID) {
        return supplierService.deleteQuantityListItem(supplierID, productID);
    }

    @Override
    public ResponseT<Order> createOrder(LocalDate date, String supplierID) {
        return orderService.createOrder(date, supplierID, supplierService.getSp());
    }

    @Override
    public ResponseT<Order> createPernamentOrder(int day, String supplierID) {
        return orderService.createPernamentOrder(day, supplierID, supplierService.getSp());
    }

    @Override
    public Response approveOrder(int orderID) {
        Response r = orderService.approveOrder(orderID);
        if(r.errorOccurred())
            return r;
        ResponseT<Order> orderR = orderService.getOrder(orderID, inventoryService);
        if(orderR.errorOccurred())
            return orderR;
        inventoryService.updateQuantityInventory(orderR.value.getProducts());

        return r;
    }

    @Override
    public ResponseT<Order> getOrder(int orderID) {
        return orderService.getOrder(orderID, inventoryService);
    }

    @Override
    public Response addProductToOrder(int orderId, int productId, int amount) {
        return orderService.addProductToOrder(orderId, productId, amount);
    }

    @Override
    public Response removeProductFromOrder(int orderID, int productID) {
        return orderService.removeProductFromOrder(orderID, productID);
    }

    @Override
    public ResponseT<Double> getPrice(String supplierID, int amount, int productID) {
        //todo check bofore if product exists in inventory module
        return supplierService.getPrice(supplierID,amount,productID);
    }

    @Override
    public ResponseT<Supplier> getCheapestSupplier(int productID, int amount, boolean scheduled) {
        //todo check bofore if product exists in inventory module
        return supplierService.getCheapestSupplier(productID,amount, scheduled);
    }

    @Override
    public ResponseT<Double> getOrderTotalPrice(int orderID) {
        return orderService.getOrderTotalPrice(orderID);
    }

    @Override
    public ResponseT<Double> getProductDiscount(String supplierID, int amount, int productID) {
        //todo check if product exists
        return supplierService.getProductDiscount(supplierID, amount, productID);
    }

    @Override
    public ResponseT<Integer> getSupplierCompanyProductID(String supplierID, int productID) {
        //todo check if product exists
        return supplierService.getSupplierCompanyProductID(supplierID,productID);
    }

    @Override
    public ResponseT<Double> getOrderTotalDiscount(int orderID) {
        return orderService.getOrderTotalDiscount(orderID);
    }

    @Override
    public ResponseT<Agreement> getAgreement(String supplierID) {
        return supplierService.getAgreement(supplierID);
    }
}
