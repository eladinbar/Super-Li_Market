package BusinessLayer.SuppliersPackage.SupplierPackage;

import DataAccessLayer.DalObjects.SupplierObjects.DalAgreementItems;
import DataAccessLayer.DalObjects.SupplierObjects.DalQuantityListItems;

import java.sql.SQLException;
import java.util.*;

public class Agreement {
    private Map<Integer, Integer> products;
    private Map<Integer, Integer> prices;
    private QuantityList ql;
    private Map<Integer, DalAgreementItems> dalObjects;

    public Agreement() {
        this.ql =  null;
        products = new HashMap<>();
        prices = new HashMap<>();
        dalObjects = new HashMap<>();
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public Map<Integer, Integer> getPrices() {
        return prices;
    }

    public QuantityList getQl() {
        return ql;
    }

    public QuantityList readQl(String supplierId) throws Exception {
        List<DalQuantityListItems> qlItems = new ArrayList();
        DalQuantityListItems qlDal = new DalQuantityListItems(supplierId);
        qlDal.find(qlItems);
        if (qlItems.size()==0) {
            ql = null;
            return null;
        }
        QuantityList qlBusiness = new QuantityList();
        for (DalQuantityListItems qlItemDal : qlItems) {
            qlBusiness.addQuantityListItem(qlItemDal.getProductId(), qlItemDal.getAmount(), (int) qlItemDal.getDiscount(), "");
            qlBusiness.getDalObjects().put(qlItemDal.getProductId(),qlItemDal);
        }
        this.ql = qlBusiness;
        return qlBusiness;
    }

    public Map<Integer, DalAgreementItems> getDalObjects() {
        return dalObjects;
    }

    public void setQl(QuantityList ql) {
        this.ql = ql;
    }

    public int compID(int id) throws Exception {
        if (!products.containsKey(id))
            throw new Exception("agreement does not have specific item included");
        return products.get(id);
    }

    private void companyNumberExists(int PID) throws Exception {
        for (Map.Entry<Integer, Integer> a : products.entrySet()) {
            if (PID == a.getValue())
                throw new Exception("company number already exists in the system");
        }
    }

    public void deleteQuantityListItem(int productID) throws Exception {
        if (ql == null)
            throw new Exception("supplier does not have a quantity list");
        if (!ql.getAmount().containsKey(productID))
            throw new Exception("supplier quantity list does not contain item " + productID);
        compID(productID);
        ql.getDiscount().remove(productID);
        ql.getAmount().remove(productID);
        ql.deleteItem(productID); //dal
    }

    public void addQuantityListItem(int productID, int amount, int discount, String supplierId) throws Exception {
        if (ql == null)
            throw new Exception("supplier does not have quantity list");
        compID(productID);
        ql.addQuantityListItem(productID, amount, discount, supplierId);
    }

    public QuantityList addQuantityList() throws Exception {
        if (ql != null)
            throw new Exception("supplier already have a quantity list");
        ql = new QuantityList();
        return ql;
    }

    public void editQuantityListAmount(int productID, int amount) throws Exception {
        if (ql == null)
            throw new Exception("supplier does not have quantity list");
        compID(productID);
        ql.editQuantityListAmount(productID, amount);
    }

    public void editQuantityListDiscount(int productID, int discount) throws Exception {
        if (ql == null)
            throw new Exception("supplier does not have quantity list");
        compID(productID);
        ql.editQuantityListDiscount(productID, discount);
    }

    public void addItemToAgreement(int productID, int companyProductID, int price, String supplierId) throws Exception {
        companyNumberExists(companyProductID);
        if (products.containsKey(productID))
            throw new Exception("agreement already have the item");
        if (price < 0)
            throw new Exception("price must be positive number");
        if (companyProductID < 0)
            throw new Exception("company product ID must be positive number");
        products.put(productID, companyProductID);
        prices.put(productID, price);
        if (!supplierId.equals(""))
            saveItem(productID, supplierId); //dal
    }

    public void removeItemFromAgreement(int productId) throws Exception {
        if (!products.containsKey(productId))
            throw new Exception("item does not exists in agreement");
        products.remove(productId);
        prices.remove(productId);
        deleteItem(productId); //dal
    }

    public void editAgreementItemCompanyProductID(int productID, int companyProductID) throws Exception {
        if (!products.containsKey(productID))
            throw new Exception("item does not exists in agreement");
        companyNumberExists(companyProductID);
        if (companyProductID < 0)
            throw new Exception("company product ID must be positive number");
        products.remove(productID);
        products.put(productID, companyProductID);
        dalObjects.get(productID).setProductCompId(companyProductID); //dal
    }

    public void editAgreementItemPrice(int productID, int price) throws Exception {
        if (!products.containsKey(productID))
            throw new Exception("item does not exists in agreement");
        if (price < 0)
            throw new Exception("price must be positive number");
        prices.remove(productID);
        prices.put(productID, price);
        dalObjects.get(productID).setPrice(price);
    }

    public double getPrice(int amount, int productID) throws Exception {
        if (!products.containsKey(productID))
            throw new Exception("item does not exists in agreement");
        if (amount < 0)
            throw new Exception("price must be positive number");
        double price = prices.get(productID) * amount;
        if (ql != null)
            price = ql.getPrice(productID, amount, price);
        return price;
    }

    public Double getProductDiscount(int amount, int productID) throws Exception {
        if (!products.containsKey(productID))
            throw new Exception("item does not exists in agreement");
        if (amount < 0)
            throw new Exception("price must be positive number");
        double agreementPrice = amount * prices.get(productID);
        double afterDiscountPrice = getPrice(amount, productID);
        return agreementPrice - afterDiscountPrice;
    }

    public Integer getSupplierCompanyProductID(int productID) throws Exception {
        if (!products.containsKey(productID))
            throw new Exception("item does not exists in agreement");
        return products.get(productID);
    }

    public boolean deleteItem(int itemId) throws SQLException {
        boolean toReturn = dalObjects.get(itemId).delete();
        dalObjects.remove(itemId);
        return toReturn;
    }

    public boolean saveItem(int itemId, String supplierId) throws SQLException {
        dalObjects.put(itemId, new DalAgreementItems(itemId, Integer.parseInt(supplierId), products.get(itemId), prices.get(itemId)));
        return dalObjects.get(itemId).save();
    }

    public void update(int itemId) throws SQLException {
        dalObjects.get(itemId).update();
    }

    public boolean find(int itemId) throws SQLException {
        return dalObjects.get(itemId).find();
    }
}
