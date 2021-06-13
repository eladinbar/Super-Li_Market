package ServiceLayer;

import InfrastructurePackage.Pair;
import ServiceLayer.Response.Response;
import ServiceLayer.Response.ResponseT;
import ServiceLayer.FacadeObjects.*;

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
    public ResponseT<FacadeSupplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment, String adress,int area) {
        return supplierService.addSupplier(firstName, lastName, email, id, phone, companyNumber, isPernamentDays, selfDelivery, payment,adress,area);
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
    public Response updateDeliveryArea(String id, int area) {
        return supplierService.updateDeliveryArea(id, area);
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
    public ResponseT<FacadeSupplier> getSupplier(String id) {
        return supplierService.getSupplier(id);
    }

    @Override
    public ResponseT<FacadeQuantityList> addQuantityList(String supplierID) {
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
    public ResponseT<FacadeItem> addQuantityListItem(String supplierID, int productID, int amount, int discount) {
        ResponseT<FacadeItem> r = inventoryService.getItem(productID);
        if (!r.errorOccurred()) {
            ResponseT<FacadeItem> rp = supplierService.addQuantityListItem(supplierID, productID, amount, discount, inventoryService);
            if (rp.errorOccurred())
                return new ResponseT<>(rp.getErrorMessage());
        }
        return r;
    }

    public ResponseT<FacadeItem> addItemToAgreement(String id, int productID, int companyProductId, int price) {
        ResponseT<FacadeItem> r = inventoryService.getItem(productID);
        if (!r.errorOccurred()) {
            ResponseT<FacadeItem> rp = supplierService.addItemToAgreement(id, productID, companyProductId, price, inventoryService);
            if(rp.errorOccurred())
                return new ResponseT<>(rp.getErrorMessage());
        }
        inventoryService.addItemSupplier(productID, id);
        return r;
    }

    @Override
    public Response removeItemFromAgreement(String supplierId, int productID) {
        if(!itemExists(productID)){
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.removeItemFromAgreement(supplierId, productID);
    }

    @Override
    public Response editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID) {
        if(!itemExists(productID)){
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.editAgreementItemCompanyProductID(supplierID, productID, companyProductID);
    }

    @Override
    public Response editAgreementItemPrice(String supplierID, int productID, int price) {
        if(!itemExists(productID)){
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.editAgreementItemPrice(supplierID, productID, price);
    }

    @Override
    public ResponseT<FacadeQuantityList> getQuantityList(String supplierId) {
        return supplierService.getQuantityList(supplierId);
    }

    @Override
    public ResponseT<List<FacadeOrder>> createShortageOrder(LocalDate date) {
        ResponseT<Pair<Map<Integer, Integer>, Map<Integer, String>>> itemInShort = inventoryService.getItemsInShortAndQuantities();
        if (itemInShort.errorOccurred())
            return new ResponseT<>(itemInShort.getErrorMessage());
        if(itemInShort.value==null) {
            return new ResponseT<>("there is no item shortage");
        }
        ResponseT<Map<String, Map<Integer, Integer>>> r = supplierService.createShortageOrders(itemInShort.value.getFirst()); //yes always returns a value;
        ResponseT<List<FacadeOrder>> orderR;
        try {
            assert r.value != null;
            orderR = orderService.createShortageOrders(r.value, itemInShort.value.getSecond(), date, supplierService.getSp());
        } catch (Exception e)
        {
            orderR = new ResponseT<>(e.getMessage());
        }
        if (r.errorOccurred())
            orderR.setErrorMessage(orderR.getErrorMessage() + "\n" + r.getErrorMessage());
        return orderR;
    }

    @Override
    public ResponseT<FacadeOrder> createScheduledOrder(int day, int itemID, int amount) {
        if (!itemExists(itemID)) {
            return new ResponseT<>("no Item exist in the system: " + itemID);
        }
        ResponseT<FacadeSupplier> cheap = supplierService.getCheapestSupplier(itemID, amount, true);
        if (cheap.errorOccurred())
            return new ResponseT<>(cheap.getErrorMessage());
        FacadeSupplier s = cheap.value;
        ResponseT<FacadeOrder> scheduledOrder = orderService.createPermanentOrder(day, s.getSc().getId(), supplierService.getSp());
        if (scheduledOrder.errorOccurred())
            return new ResponseT<>(cheap.getErrorMessage());
        if (scheduledOrder.value == null)
            return new ResponseT<FacadeOrder>("Something went wrong.");
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
    public ResponseT<FacadeOrder> createOrder(LocalDate date, String supplierID) {
        return orderService.createOrder(date, supplierID, supplierService.getSp());
    }

    @Override
    public ResponseT<FacadeOrder> createPermanentOrder(int day, String supplierID) {
        return orderService.createPermanentOrder(day, supplierID, supplierService.getSp());
    }


    @Override
    public Response approveOrder(int orderID) {
        getOrder(orderID);
        Response r = orderService.approveOrder(orderID);
        if(r.errorOccurred())
            return r;
        ResponseT<FacadeOrder> orderR = orderService.getOrder(orderID, inventoryService, supplierService);
        if (orderR.errorOccurred())
            return orderR;
        ArrayList<FacadeProduct> productList = orderR.value.getProducts();
        inventoryService.updateQuantityInventory(productList);
        for (FacadeProduct p : productList) {
            if(p.getDiscount() > 0){
                inventoryService.addItemDiscount(orderR.value.getSupplier().getSc().getId(), p.getDiscount(), orderR.value.getDate(), p.getAmount(), p.getProductID());
            }
        }

        return r;
    }

    @Override
    public ResponseT<FacadeOrder> getOrder(int orderID) {
        return orderService.getOrder(orderID, inventoryService,supplierService);
    }

    @Override
    public Response addProductToOrder(int orderId, int productID, int amount) {
        getOrder(orderId);
        if(!itemExists(productID))
            return new ResponseT<>("no Item exist in the system: " + productID);
        return orderService.addProductToOrder(orderId, productID, amount);
    }

    @Override
    public Response updateProductDeliveredAmount(int orderId, int productID, int amount) {
        getOrder(orderId);
        if(!itemExists(productID))
            return new ResponseT<>("no Item exist in the system: " + productID);
        return orderService.updateProductDeliveredAmount(orderId, productID, amount);
    }

    @Override
    public Response removeProductFromOrder(int orderID, int productID) {
        getOrder(orderID);
        if(!itemExists(productID))
            return new ResponseT<>("no Item exist in the system: " + productID);
        return orderService.removeProductFromOrder(orderID, productID);
    }

    @Override
    public ResponseT<List<Integer>> makeOrders(int day) {
        ResponseT<List<Integer>> oIDs = orderService.makeOrders(day);
        for (int id:oIDs.value) {
            FacadeOrder fOrder = getOrder(id).value;
            //todo with ido
            //localDate = localDate of day in the next week
            //create order
            //foreach add items to order
            //send to ido after every foreach or after create order?
        }
        return null;
    }

    @Override
    public ResponseT<Double> getPrice(String supplierID, int amount, int productID) {
        if(!itemExists(productID)){
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.getPrice(supplierID,amount,productID);
    }

    @Override
    public ResponseT<FacadeSupplier> getCheapestSupplier(int productID, int amount, boolean scheduled) {
        if(!itemExists(productID)){
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.getCheapestSupplier(productID,amount, scheduled);
    }

    @Override
    public ResponseT<Double> getOrderTotalPrice(int orderID) {
        getOrder(orderID);
        return orderService.getOrderTotalPrice(orderID);
    }

    @Override
    public ResponseT<Double> getProductDiscount(String supplierID, int amount, int productID) {
        if(!itemExists(productID)){
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.getProductDiscount(supplierID, amount, productID);
    }

    @Override
    public ResponseT<Integer> getSupplierCompanyProductID(String supplierID, int productID) {
        if(!itemExists(productID)){
            return new ResponseT<>("no Item exist in the system: " + productID);
        }
        return supplierService.getSupplierCompanyProductID(supplierID,productID);
    }

    @Override
    public ResponseT<Double> getOrderTotalDiscount(int orderID) {
        getOrder(orderID);
        return orderService.getOrderTotalDiscount(orderID);
    }

    @Override
    public Response addItem(int id, String name, String categoryName, double costPrice, double sellingPrice, int minAmount, String shelfLocation, String storageLocation, int shelfQuantity, int storageQuantity, int manufacturerId, List<String> suppliersIds) {
        return inventoryService.addItem(id,name,categoryName,costPrice,sellingPrice,minAmount,shelfLocation,storageLocation,shelfQuantity,storageQuantity,manufacturerId,suppliersIds);
    }

    @Override
    public ResponseT<FacadeItem> getItem(int itemId) {
        return inventoryService.getItem(itemId);
    }

    @Override
    public Response modifyItemName(int itemId, String newName) {
        return inventoryService.modifyItemName(itemId,newName);
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
        return inventoryService.addCategory(categoryName,parentCategoryName);
    }

    @Override
    public ResponseT<FacadeCategory> getCategory(String categoryName) {
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
    public <T extends FacadeEntity> ResponseT<FacadeSale<T>> getSale(String saleName) {
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
    public <T extends FacadeEntity> ResponseT<List<FacadeDiscount<T>>> getDiscount(String supplierId, LocalDate discountDate) {
        return inventoryService.getDiscount(supplierId,discountDate);
    }

    @Override
    public Response recordDefect(int itemId, LocalDate entryDate, int defectQuantity, String defectLocation) {
        return inventoryService.recordDefect(itemId, entryDate, defectQuantity, defectLocation);
    }

    @Override
    public ResponseT<List<FacadeItem>> inventoryReport() {
        return inventoryService.inventoryReport();
    }

    @Override
    public ResponseT<List<FacadeItem>> categoryReport(String categoryName) {
        return inventoryService.categoryReport(categoryName);
    }

    @Override
    public ResponseT<List<FacadeItem>> itemShortageReport() {
        return inventoryService.itemShortageReport();
    }

    @Override
    public ResponseT<List<FacadeDefectEntry>> defectsReport(LocalDate fromDate, LocalDate toDate) {
        return inventoryService.defectsReport(fromDate, toDate);
    }

    @Override
    public ResponseT<Pair<Map<Integer, Integer >,Map<Integer, String>>> getItemsInShortAndQuantities() {
        return inventoryService.getItemsInShortAndQuantities();
    }

    @Override
    public Response updateQuantityInventory(ArrayList<FacadeProduct> items) {
        return inventoryService.updateQuantityInventory(items);
    }

    @Override
    public ResponseT<FacadeAgreement> getAgreement(String supplierID) {
        return supplierService.getAgreement(supplierID);
    }

    private boolean itemExists(int itemId){
        return !inventoryService.getItem(itemId).errorOccurred();
    }
}
