package SerciveLayer.SimpleObjects;

import PresentationLayer.Menu$;

public abstract class SimpleEntity {

    public void printMe(Menu$ m){
        m.printEntity(this);
    }
}
