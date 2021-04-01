package InventoryModule.ControllerLayer;

import InventoryModule.BusinessLayer.Category;

import java.util.List;

public interface InventoryService {

    public Response addItem(int id, String name, Category category,
                            double costPrice, double sellingPrice, int minAmount,
                            String shelfLocation, String storageLocation,
                            int storageQuantity, int shelfQuantity,
                            int manufacturerId, List<Integer> suppliersId, int supplyTime);
    public Response editItem();
}


