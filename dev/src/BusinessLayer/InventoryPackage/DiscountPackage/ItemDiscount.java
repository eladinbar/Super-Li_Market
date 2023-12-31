package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Item;
import DataAccessLayer.DalObjects.InventoryObjects.DalItemDiscount;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDiscount extends Discount{
    private DalItemDiscount dalCopyItemDiscount;

    private Item item;

    public ItemDiscount() {
        super();
        try {
            dalCopyItemDiscount = new DalItemDiscount();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public ItemDiscount(String supplierId, LocalDate discountDate) {
        super(supplierId, discountDate);
        try {
            dalCopyItemDiscount = new DalItemDiscount();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public ItemDiscount(String supplierID, double discount, LocalDate date, int itemCount, Item item) {
        super(supplierID, discount, date, itemCount);
        try {
            dalCopyItemDiscount = new DalItemDiscount();
        } catch(SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
        this.item = item;
    }

    public void setAndSaveDiscount(double discount) {
        dalCopyItemDiscount.setDiscount(discount);
        try {
            dalCopyItemDiscount.update();
            this.discount = discount;
        } catch (SQLException ex) {
            dalCopyItemDiscount.setDiscount(this.discount);
            throw new RuntimeException("Something went wrong.");
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        dalCopyItemDiscount.setItemID(item.getID());
        this.item = item;
    }

    public void setAndSaveItem(Item item) {
        dalCopyItemDiscount.setAndSaveItemID(item.getID());
        this.item = item;
    }

    public void save() {
        try {
            dalCopyItemDiscount = new DalItemDiscount(date.toString(), supplierID, item.getID(), discount, itemCount);
            dalCopyItemDiscount.save();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void update() throws SQLException {
        dalCopyItemDiscount.update();
    }

    public void delete() {
        try {
            dalCopyItemDiscount.delete();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public boolean find() {
        boolean found;
        try {
            dalCopyItemDiscount = new DalItemDiscount(date.toString(), supplierID, item.getID());

            found = dalCopyItemDiscount.find(); //Retrieves DAL Category Sale from the database
            //Set the fields according to the retrieved data
            if (found) {
                this.date = LocalDate.parse(dalCopyItemDiscount.getDiscountDate());
                this.supplierID = dalCopyItemDiscount.getSupplierID();
                this.discount = dalCopyItemDiscount.getDiscount();
                this.itemCount = dalCopyItemDiscount.getItemCount();
                this.item = new Item(dalCopyItemDiscount.getItemID()); //Create new Item with primary key to be replaced
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }

    public boolean find(List<ItemDiscount> itemDiscounts, String supplierId, String discountDate) {
        boolean found;
        try {
            List<DalItemDiscount> dalCopyItemDiscounts = new ArrayList<>();
            dalCopyItemDiscount = new DalItemDiscount(supplierId, discountDate);

            found = dalCopyItemDiscount.find(dalCopyItemDiscounts); //Retrieves DAL Items from the database
            //Set the fields according to the retrieved data
            if (found) {
                for (DalItemDiscount itemDiscount : dalCopyItemDiscounts) {
                    ItemDiscount savedItemDiscount = new ItemDiscount(itemDiscount.getSupplierID(), itemDiscount.getDiscount(),
                            LocalDate.parse(itemDiscount.getDiscountDate()), itemDiscount.getItemCount(), new Item(dalCopyItemDiscount.getItemID()));
                    itemDiscounts.add(savedItemDiscount);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }
}
