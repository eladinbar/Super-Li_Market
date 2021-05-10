package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Item;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ItemDiscount extends Discount{
    private DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount dalCopyItemDiscount;

    private Item item;

    public ItemDiscount() {}

    public ItemDiscount(String supplierId, LocalDate discountDate) {
        super(supplierId, discountDate);
    }

    public ItemDiscount(String supplierID, double discount, LocalDate date, int itemCount, Item item) {
        super(supplierID, discount, date, itemCount);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        dalCopyItemDiscount.setItemID(item.getID());
        this.item = item;
    }

    public void save() {
        try {
            dalCopyItemDiscount = new DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount(date.toString(), supplierID, item.getID(), discount, itemCount);
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
            dalCopyItemDiscount = new DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount(date.toString(), supplierID, item.getID());

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
            List<DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount> dalCopyItemDiscounts = new ArrayList<>();
            dalCopyItemDiscount = new DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount(supplierId, discountDate);

            found = dalCopyItemDiscount.find(dalCopyItemDiscounts); //Retrieves DAL Items from the database
            //Set the fields according to the retrieved data
            if (found) {
                for (DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount itemDiscount : dalCopyItemDiscounts) {
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
