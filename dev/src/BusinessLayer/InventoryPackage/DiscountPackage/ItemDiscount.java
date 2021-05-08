package BusinessLayer.InventoryPackage.DiscountPackage;

import BusinessLayer.InventoryPackage.Item;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class ItemDiscount extends Discount{
    private DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount dalCopyItemDiscount;

    private Item item;

    public ItemDiscount(int supplierID, double discount, Calendar date, int itemCount, Item item) {
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
        Calendar date = getDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = format.format(date.getTime());

        dalCopyItemDiscount = new DataAccessLayer.DalObjects.InventoryObjects.ItemDiscount(formattedDate, supplierID, item.getID(), discount, itemCount);
        dalCopyItemDiscount.save();
    }
}
