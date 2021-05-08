package BusinessLayer.InventoryPackage.SalePackage;

import InfrastructurePackage.Pair;
import BusinessLayer.InventoryPackage.Item;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

public class ItemSale extends Sale {
    private DataAccessLayer.DalObjects.InventoryObjects.ItemSale dalCopyItemSale;

    private Item item;

    public ItemSale(String name, double discount, Calendar startDate, Calendar endDate, Item item) {
        super(name, discount, startDate, endDate);
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void save() throws SQLException {
        Calendar start = getStartDate();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStart = format.format(start.getTime());
        Calendar end = getEndDate();
        String formattedEnd = format.format(end.getTime());

        dalCopyItemSale = new DataAccessLayer.DalObjects.InventoryObjects.ItemSale(name, discount, formattedStart, formattedEnd, item.getID());
        dalCopyItemSale.save();
    }
}
