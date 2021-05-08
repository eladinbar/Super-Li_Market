package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Item;

import java.sql.SQLException;
import java.time.LocalDate;

public class ItemDiscount extends Discount{
    private DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount dalCopyItemDiscount;

    private Item item;

    public ItemDiscount(String supplierID, double discount, LocalDate date, int itemCount, Item item) {
        super(supplierID, discount, date, itemCount);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void save() throws SQLException {
        dalCopyItemDiscount = new DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount(date.toString(), supplierID, item.getID(), discount, itemCount);
        dalCopyItemDiscount.save();
    }
}
