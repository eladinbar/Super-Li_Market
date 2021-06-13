package ServiceLayer;

import InfrastructurePackage.Pair;
import ServiceLayer.Response.*;
import ServiceLayer.FacadeObjects.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;

public interface IService {
    //add supplier and edit all supplier fields
    ResponseT<FacadeSupplier> addSupplier(String firstName, String lastName, String email, String id, String phone, int companyNumber, boolean isPernamentDays, boolean selfDelivery, String payment, String adress,int area);
    Response removeSupplier(String id);
    Response updateFirstName(String id, String lirstName);
    Response updateLastName(String id, String lastName);
    Response updatePhone(String id, String phone);
    Response updateEmail(String id, String email);
    Response updateDeliveryArea(String id, int area);
    Response updateCompanyNumber(String id, int companyNumber);
    Response updateSelfDelivery(String id, boolean self);
    Response updatePernamentDays(String id, boolean perm);
    Response updatePayment(String id, String pay);
    Response addContactMember(String supplierId, String firstName, String lastName, String email, String memberID, String phone);
    Response deleteContactMember(String supplierID, String memberID);
    ResponseT<FacadeSupplier> getSupplier(String id);

    //add quantityList
    ResponseT<FacadeQuantityList> addQuantityList(String supplierID);
    Response editQuantityListAmount(String supplierID, int productID, int amount);
    Response editQuantityListDiscount(String supplierID, int productID, int discount);
    Response deleteQuantityList(String supplierID);
    ResponseT<FacadeItem> addQuantityListItem(String supplierID, int productID, int amount, int discount);
    Response deleteQuantityListItem(String supplierID, int productID);
    ResponseT<FacadeQuantityList> getQuantityList(String supplierId);

    //orders
    ResponseT<List<FacadeOrder>> createShortageOrder(LocalDate date);
    ResponseT<FacadeOrder> createScheduledOrder(int day, int itemID, int amount);
    ResponseT<FacadeOrder> createOrder(LocalDate date, String supplierID);
    ResponseT<FacadeOrder> createPermanentOrder(int day, String supplierID);
    Response approveOrder(int orderID);
    ResponseT<FacadeOrder> getOrder(int orderID);
    Response addProductToOrder(int orderId, int productId, int amount);
    Response updateProductDeliveredAmount(int orderId, int productID, int amount);
    Response removeProductFromOrder(int orderID, int productID);
    ResponseT<List<Integer>> makeOrders(int day);

    //agreement
    ResponseT<FacadeItem> addItemToAgreement(String id, int productID, int companyProductId, int price);
    Response removeItemFromAgreement(String supplierId, int productId);
    Response editAgreementItemCompanyProductID(String supplierID, int productID, int companyProductID);
    Response editAgreementItemPrice(String supplierID, int productID, int price);
    ResponseT<FacadeAgreement> getAgreement(String supplierID);

    //products
    ResponseT<Double> getPrice(String supplierID,int amount, int productID);
    ResponseT<Double> getOrderTotalPrice(int orderID);
    ResponseT<Double> getProductDiscount(String supplierID,int amount, int productID);
    ResponseT<Integer> getSupplierCompanyProductID(String supplierID, int productID);
    ResponseT<Double> getOrderTotalDiscount(int orderID);
    ResponseT<FacadeSupplier> getCheapestSupplier(int productID, int amount, boolean scheduled);

    /*
    ResponseT<order> createOrderFromShortage(LocalDate date,String supplier,List<Integer> productsID,List<Integer> amount);
    */

    /*
    //system
    ResponseT<List<product>> getAllSystemProducts();
    ResponseT<List<product>> getAllSupplierProducts();
    ResponseT<List<order>> getAllOrders();
    ResponseT<List<supplier>> getAllSuppliers();
    */

    //-------------------------------------------------------------------------Item functions

    Response addItem(int id, String name, String categoryName, double costPrice, double sellingPrice, int minAmount,
                     String shelfLocation, String storageLocation, int shelfQuantity, int storageQuantity, int manufacturerId,
                     int weight, List<String> suppliersIds);
    ResponseT<FacadeItem> getItem(int itemId);
    Response modifyItemName(int itemId, String newName);
    Response modifyItemCategory(int itemId, String newCategoryName);
    Response modifyItemCostPrice(int itemId, double newCostPrice);
    Response modifyItemSellingPrice(int itemId, double newSellingPrice);
    /*
    if the locations are null or empty, display an appropriate error message.
     */
    Response changeItemShelfLocation(int itemId, String newShelfLocation);
    Response changeItemStorageLocation(int itemId, String newStorageLocation);
    /*
    If quantity is a negative number the quantity of the respective item will not be changed.
     */
    Response modifyItemShelfQuantity(int itemId, int newShelfQuantity);
    Response modifyItemStorageQuantity(int itemId, int newStorageQuantity);
    Response removeItem(int itemId);

    //-------------------------------------------------------------------------Category functions

    /*
    If parent category is null or empty, the new category should be added as a main category.
     */
    Response addCategory(String categoryName, String parentCategoryName);
    ResponseT<FacadeCategory> getCategory(String categoryName);
    Response modifyCategoryName(String oldName, String newName);
    /*
    when the category is deleted all its sub categories move to the parent category.
     */
    Response removeCategory(String categoryName);
    Response changeParentCategory(String categoryName, String newParentName);
    //-------------------------------------------------------------------------Sale functions
    <T extends FacadeEntity> ResponseT<FacadeSale<T>> getSale(String saleName);
    Response addItemSale(String saleName, int itemID, double saleDiscount, LocalDate startDate, LocalDate endDate);
    Response addCategorySale(String saleName, String categoryName, double saleDiscount, LocalDate startDate, LocalDate endDate);



    Response modifySaleName(String oldName, String newName);
    Response modifySaleDiscount(String saleName, double newDiscount);
    Response modifySaleDates(String saleName, LocalDate startDate, LocalDate endDate);

    //-------------------------------------------------------------------------Discount functions

    <T extends FacadeEntity> ResponseT<List<FacadeDiscount<T>>> getDiscount(String supplierId, LocalDate discountDate);

    //-------------------------------------------------------------------------Defect functions

    Response recordDefect(int itemId, LocalDate entryDate, int defectQuantity, String defectLocation);

    //-------------------------------------------------------------------------Report functions

    ResponseT<List<FacadeItem>> inventoryReport();
    ResponseT<List<FacadeItem>> categoryReport(String categoryName);
    ResponseT<List<FacadeItem>> itemShortageReport();
    ResponseT<List<FacadeDefectEntry>> defectsReport(LocalDate fromDate, LocalDate toDate);

    Response approveTruckReport(int truckReportId);

    ResponseT<Pair<Map<Integer, Integer >,Map<Integer, String>>>getItemsInShortAndQuantities();

    Response updateQuantityInventory(ArrayList<FacadeProduct> items);


}
