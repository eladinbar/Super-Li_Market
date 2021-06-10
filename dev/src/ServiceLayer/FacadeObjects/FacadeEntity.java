package ServiceLayer.FacadeObjects;

import PresentationLayer.InventorySupplierMenu;

public abstract class FacadeEntity {

    public void printMe(InventorySupplierMenu m){
        m.printEntity(this);
    }
}
