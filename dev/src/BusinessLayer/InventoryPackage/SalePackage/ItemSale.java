package BusinessLayer.InventoryPackage.SalePackage;

import BusinessLayer.InventoryPackage.Item;

import java.sql.SQLException;
import java.time.LocalDate;

public class ItemSale extends Sale {
    private DataAccessLayer.DalObjects.InventoryObjects.ItemSale dalCopyItemSale;

    private Item item;

    public ItemSale(String name, double discount, LocalDate startDate, LocalDate endDate, Item item) {
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
         dalCopyItemSale = new DataAccessLayer.DalObjects.InventoryObjects.ItemSale(name, discount,
                 saleDates.getFirst().toString(), saleDates.getSecond().toString(), item.getID());
        dalCopyItemSale.save();
    }
}
