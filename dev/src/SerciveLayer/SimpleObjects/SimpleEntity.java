package SerciveLayer.SimpleObjects;

import PresentationLayer.InventoryP.Menu;

public abstract class SimpleEntity {

    public void printMe(Menu m){
        m.printEntity(this);
    }
}
