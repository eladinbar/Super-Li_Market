package InventoryModule.ControllerLayer;

import InventoryModule.BusinessLayer.Category;
import InventoryModule.BusinessLayer.DefectsPackage.DefectsLogger;
import InventoryModule.BusinessLayer.DiscountPackage.Discount;
import InventoryModule.BusinessLayer.SalePackage.Sale;

import java.util.ArrayList;
import java.util.List;

public class InventoryController {
    private List<Category> categories;
    private DefectsLogger defectsLogger;
    private List<Discount> discounts;
    private List<Sale> sales;

    public InventoryController() {
        categories = new ArrayList<>();
        defectsLogger = new DefectsLogger();
        discounts = new ArrayList<>();
        sales = new ArrayList<>();
    }

    public void addItem(int id, String name, String category, double costPrice, double sellingPrice, int minAmount, String shelfLocation, String storageLocation,
                        int storageQuantity, int shelfQuantity, int manufacturerId, List<Integer> suppliersId) {

    }


}
