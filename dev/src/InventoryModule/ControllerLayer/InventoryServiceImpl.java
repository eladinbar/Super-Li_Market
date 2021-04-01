package InventoryModule.ControllerLayer;

import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.util.List;

public class InventoryServiceImpl implements InventoryService{

    @Override
    public Response addItem(int id, String name, String category, double costPrice, double sellingPrice, int minAmount,
                            String shelfLocation, String storageLocation, int storageQuantity, int shelfQuantity,
                            int manufacturerId, List<Integer> suppliersId) {
        return null;
    }

    @Override
    public ResponseT<Item> getItem(int ItemId) {
        return null;
    }

    @Override
    public Response modifyItemName(int itemId, String newName) {
        return null;
    }

    @Override
    public Response modifyItemCategory(int itemId, String newCategoryName) {
        return null;
    }

    @Override
    public Response modifyItemCostPrice(int itemId, double newCostPrice) {
        return null;
    }

    @Override
    public Response modifyItemSellPrice(int itemId, double newSellName) {
        return null;
    }

    @Override
    public Response changeItemLocation(int itemId, String newStorageLocation, String newStoreLocation) {
        return null;
    }

    @Override
    public Response modifyItemQuantity(int itemId, int newStorageQuantity, int newStoreQuantity) {
        return null;
    }

    @Override
    public Response addItemSupplier(int itemId, int supplierId) {
        return null;
    }

    @Override
    public Response removeItemSupplier(int itemId, int supplierId) {
        return null;
    }
}
