package InventoryModule.PresentationLayer;

import InfrastructurePackage.TextFormatter;
import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TextFormatter tf = new TextFormatter();
        Menu m = new Menu();
        Item i = new Item(12345678, "hello", 0.5003, 3.59, 5,
                98765432,4, 4, "A5-L-S15","A3-L-S15");

        try {
            m.printItemPrompt(i);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
