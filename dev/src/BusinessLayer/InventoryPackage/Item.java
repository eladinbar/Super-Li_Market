package BusinessLayer.InventoryPackage;

import DataAccessLayer.DalObjects.InventoryObjects.DalItem;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Item {
    private DalItem dalCopyItem;

    private int ID;
    private String name;
    private double costPrice;
    private double sellingPrice;
    private int minAmount;
    private int manufacturerID;
    private final List<String> supplierIDs;
    private final Quantity quantity;
    private final Location location;
    private int weight;

    public Item() {
        try {
            dalCopyItem = new DalItem();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
        supplierIDs = new ArrayList<>();
        quantity = new Quantity();
        location = new Location();
    }

    public Item(int ID) {
        try {
            dalCopyItem = new DalItem();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
        this.ID = ID;
        supplierIDs = new ArrayList<>();
        quantity = new Quantity();
        location = new Location();
    }

    public Item(int ID, String name, double costPrice, double sellingPrice, int minAmount, int manufacturerID, List<String> supplierIDs,
                int shelfQuantity, int storageQuantity, String shelfLocation, String storageLocation, int weight) {
        try {
            dalCopyItem = new DalItem();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
        this.ID = ID;
        this.name = name;
        this.costPrice = costPrice;
        this.sellingPrice = sellingPrice;
        this.minAmount = minAmount;
        this.manufacturerID = manufacturerID;
        this.supplierIDs = supplierIDs;
        this.quantity = new Quantity(shelfQuantity, storageQuantity);
        this.location = new Location(shelfLocation, storageLocation);
        this.weight = weight;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        dalCopyItem.setName(name);
        this.name = name;
    }

    public void setAndSaveName(String name) {
        dalCopyItem.setName(name);
        try {
            dalCopyItem.update();
            this.name = name;
        } catch (SQLException ex) {
            dalCopyItem.setName(this.name);
            throw new RuntimeException("Something went wrong.");
        }
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        dalCopyItem.setCostPrice(costPrice);
        this.costPrice = costPrice;
    }

    public void setAndSaveCostPrice(double costPrice) {
        dalCopyItem.setCostPrice(costPrice);
        try {
            dalCopyItem.update();
            this.costPrice = costPrice;
        } catch (SQLException ex) {
            dalCopyItem.setCostPrice(this.costPrice);
            throw new RuntimeException("Something went wrong.");
        }
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        dalCopyItem.setSellingPrice(sellingPrice);
        this.sellingPrice = sellingPrice;
    }

    public void setAndSaveSellingPrice(double sellingPrice) {
        dalCopyItem.setSellingPrice(sellingPrice);
        try {
            dalCopyItem.update();
            this.sellingPrice = sellingPrice;
        } catch (SQLException ex) {
            dalCopyItem.setSellingPrice(this.sellingPrice);
            throw new RuntimeException("Something went wrong.");
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

    public void setShelfQuantity(int shelfQuantity) {
        dalCopyItem.setShelfQuantity(shelfQuantity);
        this.quantity.setShelfQuantity(shelfQuantity);
    }

    public void setAndSaveShelfQuantity(int shelfQuantity) {
        dalCopyItem.setShelfQuantity(shelfQuantity);
        try {
            dalCopyItem.update();
            this.quantity.setShelfQuantity(shelfQuantity);
        } catch (SQLException ex) {
            dalCopyItem.setShelfQuantity(this.getShelfQuantity());
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void setStorageQuantity(int storageQuantity) {
        dalCopyItem.setStorageQuantity(storageQuantity);
        this.quantity.setStorageQuantity(storageQuantity);
    }

    public void setAndSaveStorageQuantity(int storageQuantity) {
        dalCopyItem.setStorageQuantity(storageQuantity);
        try {
            dalCopyItem.update();
            this.quantity.setStorageQuantity(storageQuantity);
        } catch (SQLException ex) {
            dalCopyItem.setStorageQuantity(this.getStorageQuantity());
            throw new RuntimeException("Something went wrong.");
        }
    }

    public String getShelfLocation() {
        return location.getShelfLocation();
    }

    public String getStorageLocation() {
        return location.getStorageLocation();
    }

    public void setShelfLocation(String shelfLocation) {
        dalCopyItem.setShelfLocation(shelfLocation);
        this.location.setShelfLocation(shelfLocation);
    }

    public void setAndSaveShelfLocation(String shelfLocation) {
        dalCopyItem.setShelfLocation(shelfLocation);
        try {
            dalCopyItem.update();
            this.location.setShelfLocation(shelfLocation);
        } catch(SQLException ex) {
            dalCopyItem.setShelfLocation(this.getShelfLocation());
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void setStorageLocation(String storageLocation) {
        dalCopyItem.setStorageLocation(storageLocation);
        this.location.setStorageLocation(storageLocation);
    }

    public void setAndSaveStorageLocation(String storageLocation) {
        dalCopyItem.setStorageLocation(storageLocation);
        try {
            dalCopyItem.update();
            this.location.setStorageLocation(storageLocation);
        } catch (SQLException ex) {
            dalCopyItem.setStorageLocation(this.getStorageLocation());
            throw new RuntimeException("Something went wrong.");
        }
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        dalCopyItem.setWeight(weight);
        this.weight = weight;
    }

    public void setAndSaveWeight(int weight) {
        dalCopyItem.setWeight(weight);
        try {
            dalCopyItem.update();
            this.weight = weight;
        } catch (SQLException ex) {
            dalCopyItem.setWeight(this.weight);
            throw new RuntimeException("Something went wrong.");
        }
    }

    DalItem getDalCopyItem() {
        return dalCopyItem;
    }

    public void save(String categoryName) {
        try {
            dalCopyItem = new DalItem(ID, name, costPrice, sellingPrice, manufacturerID, minAmount,
                    getShelfQuantity(), getStorageQuantity(), getShelfLocation(), getStorageLocation(), categoryName);
            dalCopyItem.save();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void delete() {
        try {
            dalCopyItem.delete();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void update() throws SQLException {
        dalCopyItem.update();
    }

    public boolean find() {
        boolean found;
        try {
            dalCopyItem = new DalItem(ID);

            found = dalCopyItem.find(); //Retrieves DAL Item from the database
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
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }

    public boolean find(List<Item> items, String categoryName) {
        boolean found;
        try {
            List<DalItem> dalCopyItems = new ArrayList<>();
            dalCopyItem = new DalItem(categoryName);

            found = dalCopyItem.find(dalCopyItems); //Retrieves DAL Items from the database
            //Set the fields according to the retrieved data
            if (found) {
                for (DalItem item : dalCopyItems) {
                    Item savedItem = new Item(item.getItemID(), item.getName(), item.getCostPrice(), item.getSellingPrice(), item.getMinAmount(),
                            item.getManufacturerID(), null, item.getShelfQuantity(), item.getStorageQuantity(),
                            item.getShelfLocation(), item.getStorageLocation(), item.getWeight());
                    items.add(savedItem);

                    //Extract supplier IDs
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }
}
