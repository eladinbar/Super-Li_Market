package BusinessLayer.InventoryPackage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Item {
    private DataAccessLayer.DalObjects.InventoryObjects.Item dalCopyItem;

    private int ID;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int minAmount;
    private int manufacturerID;
    private final List<String> supplierIDs;
    private final Quantity quantity;
    private final Location location;

    public Item() {
        supplierIDs = new ArrayList<>();
        quantity = new Quantity();
        location = new Location();
    }

    public Item(int ID) {
        this.ID = ID;
        supplierIDs = new ArrayList<>();
        quantity = new Quantity();
        location = new Location();
    }

    public Item(int ID, String name, double costPrice, double sellingPrice, int minAmount, int manufacturerID, List<String> supplierIDs,
                int shelfQuantity, int storageQuantity, String shelfLocation, String storageLocation) {
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.supplierIDs = supplierIDs;
        this.quantity = new Quantity(shelfQuantity, storageQuantity);
        this.location = new Location(shelfLocation, storageLocation);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws SQLException {
        dalCopyItem.setName(name);
        try {
            dalCopyItem.update();
            this.name = name;
        } catch (SQLException ex) {
            dalCopyItem.setName(this.name);
            throw ex;
        }
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) throws SQLException {
        dalCopyItem.setCostPrice(costPrice);
        try {
            dalCopyItem.update();
            this.costPrice = costPrice;
        } catch (SQLException ex) {
            dalCopyItem.setCostPrice(this.costPrice);
            throw ex;
        }
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) throws SQLException {
        dalCopyItem.setSellingPrice(sellingPrice);
        try {
            dalCopyItem.update();
            this.sellingPrice = sellingPrice;
        } catch (SQLException ex) {
            dalCopyItem.setSellingPrice(this.sellingPrice);
            throw ex;
        }
    }

    public int getMinAmount() {
        return minAmount;
    }

    public int getManufacturerID() {
        return manufacturerID;
    }

    public void addSupplier(String supplierID) {
        supplierIDs.add(supplierID);
    }

    public void removeSupplier(String supplierID) {
        supplierIDs.remove(supplierID);
    }

    public int getQuantity() {
        return quantity.getTotalQuantity();
    }

    public int getShelfQuantity() {
        return quantity.getShelfQuantity();
    }

    public int getStorageQuantity() {
        return quantity.getStorageQuantity();
    }

    public void setShelfQuantity(int shelfQuantity) throws SQLException {
        dalCopyItem.setShelfQuantity(shelfQuantity);
        try {
            dalCopyItem.update();
            this.quantity.setShelfQuantity(shelfQuantity);
        } catch (SQLException ex) {
            dalCopyItem.setShelfQuantity(this.getShelfQuantity());
            throw ex;
        }
    }

    public void setStorageQuantity(int storageQuantity) throws SQLException {
        dalCopyItem.setStorageQuantity(storageQuantity);
        try {
            dalCopyItem.update();
            this.quantity.setStorageQuantity(storageQuantity);
        } catch (SQLException ex) {
            dalCopyItem.setStorageQuantity(this.getStorageQuantity());
            throw ex;
        }
    }

    public String getShelfLocation() {
        return location.getShelfLocation();
    }

    public String getStorageLocation() {
        return location.getStorageLocation();
    }

    public void setShelfLocation(String shelfLocation) throws SQLException {
        dalCopyItem.setShelfLocation(shelfLocation);
        try {
            dalCopyItem.update();
            this.location.setShelfLocation(shelfLocation);
        } catch(SQLException ex) {
            dalCopyItem.setShelfLocation(this.getShelfLocation());
            throw ex;
        }
    }

    public void setStorageLocation(String storageLocation) throws SQLException {
        dalCopyItem.setStorageLocation(storageLocation);
        try {
            dalCopyItem.update();
            this.location.setStorageLocation(storageLocation);
        } catch (SQLException ex) {
            dalCopyItem.setStorageLocation(this.getStorageLocation());
            throw ex;
        }
    }

    public void save(String categoryName) throws SQLException {
        dalCopyItem = new DataAccessLayer.DalObjects.InventoryObjects.Item(ID, name, costPrice, sellingPrice, manufacturerID, minAmount,
                getShelfQuantity(), getStorageQuantity(), getShelfLocation(), getStorageLocation(), categoryName);
        dalCopyItem.save();
    }

    public void delete() throws SQLException {
        dalCopyItem.delete();
    }

    public void update() throws SQLException {
        dalCopyItem.update();
    }

    public boolean find() throws SQLException {
        dalCopyItem = new DataAccessLayer.DalObjects.InventoryObjects.Item(ID);

        boolean found = dalCopyItem.find(); //Retrieves DAL Item from the database
        //Set the fields according to the retrieved data
        if (found) {
            this.name = dalCopyItem.getName();
            this.costPrice = dalCopyItem.getCostPrice();
            this.sellingPrice = dalCopyItem.getSellingPrice();
            this.minAmount = dalCopyItem.getMinAmount();
            this.manufacturerID = dalCopyItem.getManufacturerID();
            this.setShelfQuantity(dalCopyItem.getShelfQuantity());
            this.setStorageQuantity(dalCopyItem.getStorageQuantity());
            this.setShelfLocation(dalCopyItem.getShelfLocation());
            this.setStorageLocation(dalCopyItem.getStorageLocation());

            //Extract supplier IDs
        }

        return found;
    }

    public boolean find(List<Item> items, String categoryName) throws SQLException {
        List<DataAccessLayer.DalObjects.InventoryObjects.Item> dalCopyItems = new ArrayList<>();
        dalCopyItem = new DataAccessLayer.DalObjects.InventoryObjects.Item(categoryName);

        boolean found = dalCopyItem.find(dalCopyItems); //Retrieves DAL Items from the database
        //Set the fields according to the retrieved data
        if (found) {
            for (DataAccessLayer.DalObjects.InventoryObjects.Item item : dalCopyItems) {
                Item savedItem = new Item(item.getItemID(), item.getName(), item.getCostPrice(), item.getSellingPrice(), item.getMinAmount(),
                        item.getManufacturerID(), null, item.getShelfQuantity(), item.getStorageQuantity(), item.getShelfLocation(), item.getStorageLocation());
                items.add(savedItem);

                //Extract supplier IDs
            }
        }

        return found;
    }
}
