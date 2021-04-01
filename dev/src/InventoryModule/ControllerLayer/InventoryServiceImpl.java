package InventoryModule.ControllerLayer;

import java.util.List;

public class InventoryServiceImpl implements InventoryService{

    @Override
    public Response addItem(int id, String name, String category, double costPrice, double sellingPrice, int minAmount,
                            String shelfLocation, String storageLocation, int storageQuantity, int shelfQuantity,
                            int manufacturerId, List<Integer> suppliersId) {
        return null;
    }

    @Override
    public Response modifyItem(int itemId, ) {
        return null;
    }
    public Response editItem2() {
        return null;
    }
}
