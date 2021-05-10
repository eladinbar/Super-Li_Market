package SerciveLayer;

import SerciveLayer.Response.Response;
import SerciveLayer.Response.ResponseT;
import SerciveLayer.SimpleObjects.*;
import SerciveLayer.objects.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
        if (!itemExists(productID)) {
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.editQuantityListAmount(supplierID, productID, amount);
    }

    @Override
    public Response editQuantityListDiscount(String supplierID, int productID, int discount) {
        if (!itemExists(productID)) {
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
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
            if (rp.errorOccurred())
                return new ResponseT<>(rp.getErrorMessage());
        }
        return r;
    }

    public ResponseT<Item> addItemToAgreement(String id, int productID, int companyProductId, int price) {
        ResponseT<Item> r = inventoryService.getItem(productID);
        if (!r.errorOccurred()) {
            ResponseT<Item> rp = supplierService.addItemToAgreement(id, productID, companyProductId, price, inventoryService);
            if (rp.errorOccurred())
                return new ResponseT<>(rp.getErrorMessage());
            inventoryService.addItemSupplier(productID, id);
        }
        return r;
    }

    @Override
    public Response removeItemFromAgreement(String supplierId, int productID) {
        if (!itemExists(productID)) {
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.removeItemFromAgreement(supplierId, productID);
    }

    @Override
    public Response editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID) {
        if (!itemExists(productID)) {
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.editAgreementItemCompanyProductID(supplierID, productID, companyProductID);
    }

    @Override
    public Response editAgreementItemPrice(String supplierID, int productID, int price) {
        if (!itemExists(productID)) {
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.editAgreementItemPrice(supplierID, productID, price);
    }

    @Override
    public ResponseT<QuantityList> getQuantityList(String supplierId) {
        return supplierService.getQuantityList(supplierId);
    }

    @Override
    public ResponseT<List<Order>> createShortageOrder(LocalDate date) {
        ResponseT<Map<Integer, Integer>> itemInShort = inventoryService.getItemsInShortAndQuantities();
        if (itemInShort.errorOccurred())
            return new ResponseT<>(itemInShort.getErrorMessage());
        //todo check if there is always value
        ResponseT<Map<String, Map<Integer, Integer>>> r = supplierService.createShortageOrders(itemInShort.value);
        ResponseT<List<Order>> orderR=null;//todo check
        try {
            orderR = orderService.createShortageOrders(r.value, date, supplierService.getSp());
        } catch (Exception e)//todo check the exeption !!
        {
        }
        if (r.errorOccurred())
            orderR.setErrorMessage(r.getErrorMessage());
        return orderR;
    }

    @Override
    public ResponseT<Order> createScheduledOrder(int day, int itemID, int amount) {
        if (!itemExists(itemID)) {
            return new ResponseT<>("no Item exist in the system: " + itemID);
        }
        ResponseT<Supplier> cheap = supplierService.getCheapestSupplier(itemID, amount, true);
        if (cheap.errorOccurred())
            return new ResponseT<>(cheap.getErrorMessage());
        Supplier s = cheap.value;
        ResponseT<Order> scheduledOrder = orderService.createPernamentOrder(day, s.getSc().getId(), supplierService.getSp());
        if (scheduledOrder.errorOccurred())
            return new ResponseT<>(cheap.getErrorMessage());
        Response addItemResp = orderService.addProductToOrder(scheduledOrder.value.getId(), itemID, amount);
        if (addItemResp.errorOccurred())
            return new ResponseT<>(addItemResp.getErrorMessage());

        return getOrder(scheduledOrder.value.getId());
    }

    @Override
    public Response deleteQuantityListItem(String supplierID, int productID) {
        if (!itemExists(productID))
            return new ResponseT<>("no Item exist in the system: " + productID);
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
        if (r.errorOccurred())
            return r;
        ResponseT<Order> orderR = orderService.getOrder(orderID, inventoryService);
        if (orderR.errorOccurred())
            return orderR;
        ArrayList<Product> productList = orderR.value.getProducts();
        inventoryService.updateQuantityInventory(productList);
        for (Product p : productList) {
            if (p.getDiscount() > 0) {
                inventoryService.addItemDiscount(orderR.value.getSupplier().getSc().getId(), p.getDiscount(), orderR.value.getDate(), p.getAmount(), p.getProductID());
            }
        }

        return r;
    }

    @Override
    public ResponseT<Order> getOrder(int orderID) {
        return orderService.getOrder(orderID, inventoryService);
    }

    @Override
    public Response addProductToOrder(int orderId, int productID, int amount) {
        if (!itemExists(productID))
            return new ResponseT<>("no Item exist in the system: " + productID);
        return orderService.addProductToOrder(orderId, productID, amount);
    }

    @Override
    public Response removeProductFromOrder(int orderID, int productID) {
        if (!itemExists(productID))
            return new ResponseT<>("no Item exist in the system: " + productID);
        return orderService.removeProductFromOrder(orderID, productID);
    }

    @Override
    public ResponseT<Double> getPrice(String supplierID, int amount, int productID) {
        if (!itemExists(productID)) {
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.getPrice(supplierID, amount, productID);
    }

    @Override
    public ResponseT<Supplier> getCheapestSupplier(int productID, int amount, boolean scheduled) {
        if (!itemExists(productID)) {
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.getCheapestSupplier(productID, amount, scheduled);
    }

    @Override
    public ResponseT<Double> getOrderTotalPrice(int orderID) {
        return orderService.getOrderTotalPrice(orderID);
    }

    @Override
    public ResponseT<Double> getProductDiscount(String supplierID, int amount, int productID) {
        if (!itemExists(productID)) {
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.getProductDiscount(supplierID, amount, productID);
    }

    @Override
    public ResponseT<Integer> getSupplierCompanyProductID(String supplierID, int productID) {
        if (!itemExists(productID)) {
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.getSupplierCompanyProductID(supplierID, productID);
    }

    @Override
    public ResponseT<Double> getOrderTotalDiscount(int orderID) {
        return orderService.getOrderTotalDiscount(orderID);
    }

    @Override
    public Response addItem(int id, String name, String categoryName, double costPrice, double sellingPrice, int minAmount, String shelfLocation, String storageLocation, int shelfQuantity, int storageQuantity, int manufacturerId, List<String> suppliersIds) {
        return inventoryService.addItem(id, name, categoryName, costPrice, sellingPrice, minAmount, shelfLocation, storageLocation, shelfQuantity, storageQuantity, manufacturerId, suppliersIds);
    }

    @Override
    public ResponseT<Item> getItem(int itemId) {
        return inventoryService.getItem(itemId);
    }

    @Override
    public Response modifyItemName(int itemId, String newName) {
        return inventoryService.modifyItemName(itemId, newName);
    }

    @Override
    public Response modifyItemCategory(int itemId, String newCategoryName) {
        return inventoryService.modifyItemCategory(itemId, newCategoryName);
    }

    @Override
    public Response modifyItemCostPrice(int itemId, double newCostPrice) {
        return inventoryService.modifyItemCostPrice(itemId, newCostPrice);
    }

    @Override
    public Response modifyItemSellingPrice(int itemId, double newSellingPrice) {
        return inventoryService.modifyItemSellingPrice(itemId, newSellingPrice);
    }

    @Override
    public Response changeItemShelfLocation(int itemId, String newShelfLocation) {
        return inventoryService.changeItemShelfLocation(itemId, newShelfLocation);
    }

    @Override
    public Response changeItemStorageLocation(int itemId, String newStorageLocation) {
        return inventoryService.changeItemShelfLocation(itemId, newStorageLocation);
    }

    @Override
    public Response modifyItemShelfQuantity(int itemId, int newShelfQuantity) {
        return inventoryService.modifyItemShelfQuantity(itemId, newShelfQuantity);
    }

    @Override
    public Response modifyItemStorageQuantity(int itemId, int newStorageQuantity) {
        return inventoryService.modifyItemStorageQuantity(itemId, newStorageQuantity);
    }

    @Override
    public Response removeItem(int itemId) {
        return inventoryService.removeItem(itemId);
    }

    @Override
    public Response addCategory(String categoryName, String parentCategoryName) {
        return inventoryService.addCategory(categoryName, parentCategoryName);
    }

    @Override
    public ResponseT<Category> getCategory(String categoryName) {
        return inventoryService.getCategory(categoryName);
    }

    @Override
    public Response modifyCategoryName(String oldName, String newName) {
        return inventoryService.modifyCategoryName(oldName, newName);
    }

    @Override
    public Response removeCategory(String categoryName) {
        return inventoryService.removeCategory(categoryName);
    }

    @Override
    public Response changeParentCategory(String categoryName, String newParentName) {
        return inventoryService.changeParentCategory(categoryName, newParentName);
    }

    @Override
    public <T extends SimpleEntity> ResponseT<Sale<T>> getSale(String saleName) {
        return inventoryService.getSale(saleName);
    }

    @Override
    public Response addItemSale(String saleName, int itemID, double saleDiscount, LocalDate startDate, LocalDate endDate) {
        return inventoryService.addItemSale(saleName, itemID, saleDiscount, startDate, endDate);
    }

    @Override
    public Response addCategorySale(String saleName, String categoryName, double saleDiscount, LocalDate startDate, LocalDate endDate) {
        return inventoryService.addCategorySale(saleName, categoryName, saleDiscount, startDate, endDate);
    }

    @Override
    public Response modifySaleName(String oldName, String newName) {
        return inventoryService.modifySaleName(oldName, newName);
    }

    @Override
    public Response modifySaleDiscount(String saleName, double newDiscount) {
        return inventoryService.modifySaleDiscount(saleName, newDiscount);
    }

    @Override
    public Response modifySaleDates(String saleName, LocalDate startDate, LocalDate endDate) {
        return inventoryService.modifySaleDates(saleName, startDate, endDate);
    }

    @Override
    public <T extends SimpleEntity> ResponseT<List<Discount<T>>> getDiscount(String supplierId, LocalDate discountDate) {
        return inventoryService.getDiscount(supplierId, discountDate);
    }

    @Override
    public Response recordDefect(int itemId, LocalDate entryDate, int defectQuantity, String defectLocation) {
        return inventoryService.recordDefect(itemId, entryDate, defectQuantity, defectLocation);
    }

    @Override
    public ResponseT<List<Item>> inventoryReport() {
        return inventoryService.inventoryReport();
    }

    @Override
    public ResponseT<List<Item>> categoryReport(String categoryName) {
        return inventoryService.categoryReport(categoryName);
    }

    @Override
    public ResponseT<List<Item>> itemShortageReport() {
        return inventoryService.itemShortageReport();
    }

    @Override
    public ResponseT<List<DefectEntry>> defectsReport(LocalDate fromDate, LocalDate toDate) {
        return inventoryService.defectsReport(fromDate, toDate);
    }

    @Override
    public ResponseT<Map<Integer, Integer>> getItemsInShortAndQuantities() {
        return inventoryService.getItemsInShortAndQuantities();
    }

    @Override
    public Response updateQuantityInventory(ArrayList<Product> items) {
        return inventoryService.updateQuantityInventory(items);
    }

    @Override
    public ResponseT<Agreement> getAgreement(String supplierID) {
        return supplierService.getAgreement(supplierID);
    }

    private boolean itemExists(int itemId) {
        return !inventoryService.getItem(itemId).errorOccurred();
    }
}
