package InventoryModule.ControllerLayer.SimpleObjects;

import InventoryModule.PresentationLayer.Menu;

public abstract class SimpleEntity {

    public void printMe(Menu m){
        m.printEntity(this);
    }
}
