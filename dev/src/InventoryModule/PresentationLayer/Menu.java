package InventoryModule.PresentationLayer;

import InfrastructurePackage.TextFormatter;
import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Menu {
    private TextFormatter tf = new TextFormatter();

    public void printWelcomePrompt(){
        System.out.println("Welcome to 'Super-Li' Inventory System!");
    }
    public void printOperationMenu(Method[] presentationMethods){
        System.out.println("please enter the number of the desired Operation: ");
        int option = 1;
        for (Method method: presentationMethods) {
            if (Modifier.isPublic(method.getModifiers())) {
                System.out.println(option + ". " + method.getName());
                option++;
            }
        }
        System.out.println("\npress q to quit");
    }

    public void printItemPrompt(Item item) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        System.out.println(tf.formatItemMenuColumns());
        Method[] getMethods = {item.getClass().getMethod("getID"), item.getClass().getMethod("getName"),
                item.getClass().getMethod("getCostPrice"),item.getClass().getMethod("getSellingPrice"),
                item.getClass().getMethod("getStoreQuantity"),item.getClass().getMethod("getStorageQuantity"),
                item.getClass().getMethod("getTotalQuantity"),item.getClass().getMethod("getMinAmount"),
                item.getClass().getMethod("getStoreLocation"), item.getClass().getMethod("getStorageLocation"),
                item.getClass().getMethod("getManufacturerID")};
        String output = "";
        for (int i = 0; i < getMethods.length - 1; i++) {
            Method currentMethod = getMethods[i];
            output = output + tf.centerString(currentMethod.invoke(item).toString(), tf.getPaddingSize()) + "|";
        }
        System.out.println(output + tf.centerString(getMethods[getMethods.length - 1].invoke(item).toString(), tf.getPaddingSize()));

    }
}
