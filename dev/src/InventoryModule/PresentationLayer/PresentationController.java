package InventoryModule.PresentationLayer;

import InventoryModule.ControllerLayer.InventoryService;
import InventoryModule.ControllerLayer.InventoryServiceImpl;
import InventoryModule.ControllerLayer.SimpleObjects.Item;

public class PresentationController{
    private InventoryService inventoryService;
    private Menu menu;

    public PresentationController() {
        this.inventoryService = new InventoryServiceImpl();
    }

    public void addItem(){
        Item newItem = new Item();

    }
    public void showItem(){}
    public void editItem(){}
    public void RemoveItem(){}

    public void addCategory(){}
    public void showCategory(){}
    public void editCategory(){}
    public void RemoveCategory(){}



}
