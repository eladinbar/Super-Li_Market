package InventoryModule.ControllerLayer;

import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.util.List;

public interface InventoryService {

    public Response addItem(int id, String name, String category,
                            double costPrice, double sellingPrice, int minAmount,
                            String shelfLocation, String storageLocation,
                            int storageQuantity, int shelfQuantity,
                            int manufacturerId, List<Integer> suppliersId);
    public ResponseT<Item> getItem(int ItemId);
    public Response modifyItemName(int itemId, String newName);
    public Response modifyItemCategory(int itemId, String newCategoryName);
    public Response modifyItemCostPrice(int itemId, double newCostPrice);
    public Response modifyItemSellPrice(int itemId, double newSellName);
    /*
    if the locations are null, the location will stay the same.
     */
    public Response changeItemLocation(int itemId, String newStorageLocation, String newStoreLocation);
    /*
    If quantity is a negative number the quantity of the Respectable argument will not be changed.
     */
    public Response modifyItemQuantity(int itemId, int newStorageQuantity, int newStoreQuantity);
    public Response addItemSupplier(int itemId, int supplierId);
    public Response removeItemSupplier(int itemId, int supplierId);
}


