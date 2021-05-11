package BusinessLayer.InventoryPackage.SalePackage;

import BusinessLayer.InventoryPackage.Item;

import java.sql.SQLException;
import java.time.LocalDate;

public class ItemSale extends Sale {
    private DataAccessLayer.DalObjects.InventoryObjects.ItemSale dalCopyItemSale;

    private Item item;

    public ItemSale(String name) {
        super(name);
    }

    public ItemSale(String name, double discount, LocalDate startDate, LocalDate endDate, Item item) {
        super(name, discount, startDate, endDate);
        this.item = item;
    }

    @Override
    public void setName(String name) {
        dalCopyItemSale.setName(name);
        this.name = name;
    }

    @Override
    public void setAndSaveName(String name) {
        dalCopyItemSale.setAndSaveName(name);
        this.name = name;
    }

    @Override
    public void setDiscount(double discount) {
        dalCopyItemSale.setDiscount(discount);
        this.discount = discount;
    }

    @Override
    public void setAndSaveDiscount(double discount) {
        dalCopyItemSale.setDiscount(discount);
        try {
            dalCopyItemSale.update();
            this.discount = discount;
        } catch (SQLException ex) {
            dalCopyItemSale.setDiscount(this.discount);
            throw new RuntimeException("Something went wrong.");
        }
    }

    @Override
    public void setStartDate(LocalDate startDate) {
        dalCopyItemSale.setStartSaleDate(startDate.toString());
        this.saleDates.setFirst(startDate);
    }

    @Override
    public void setAndSaveStartDate(LocalDate startDate) {
        dalCopyItemSale.setStartSaleDate(startDate.toString());
        try {
            dalCopyItemSale.update();
            this.saleDates.setFirst(startDate);
        } catch (SQLException ex) {
            dalCopyItemSale.setStartSaleDate(this.saleDates.getFirst().toString());
            throw new RuntimeException("Something went wrong.");
        }
    }

    @Override
    public void setEndDate(LocalDate endDate) {
        dalCopyItemSale.setEndSaleDate(endDate.toString());
        this.saleDates.setSecond(endDate);
    }

    @Override
    public void setAndSaveEndDate(LocalDate endDate) {
        dalCopyItemSale.setEndSaleDate(endDate.toString());
        try {
            dalCopyItemSale.update();
            this.saleDates.setSecond(endDate);
        } catch (SQLException ex) {
            dalCopyItemSale.setEndSaleDate(this.saleDates.getSecond().toString());
            throw new RuntimeException("Something went wrong.");
        }
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        dalCopyItemSale.setItemID(item.getID());
        this.item = item;
    }

    public void setAndSaveItem(Item item) {
        dalCopyItemSale.setItemID(item.getID());
        try {
            dalCopyItemSale.update();
            this.item = item;
        } catch (SQLException ex) {
            dalCopyItemSale.setItemID(this.item.getID());
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void save() {
        try {
            dalCopyItemSale = new DataAccessLayer.DalObjects.InventoryObjects.ItemSale(name, discount,
                    saleDates.getFirst().toString(), saleDates.getSecond().toString(), item.getID());
            dalCopyItemSale.save();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void update() throws SQLException {
        dalCopyItemSale.update();
    }

    public void delete() {
        try {
            dalCopyItemSale.delete();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public boolean find() {
        boolean found;
        try {
            dalCopyItemSale = new DataAccessLayer.DalObjects.InventoryObjects.ItemSale(name);

            found = dalCopyItemSale.find(); //Retrieves DAL Category Sale from the database
            //Set the fields according to the retrieved data
            if (found) {
                this.name = dalCopyItemSale.getName();
                this.discount = dalCopyItemSale.getDiscount();
                this.setStartDate(LocalDate.parse(dalCopyItemSale.getStartSaleDate()));
                this.setEndDate(LocalDate.parse(dalCopyItemSale.getEndSaleDate()));
                this.setItem(new Item(dalCopyItemSale.getItemID())); //Create new item with primary key to be replaced
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }
}
