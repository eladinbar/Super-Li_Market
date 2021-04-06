package InventoryModule.PresentationLayer;

import InfrastructurePackage.TextFormatter;
import InventoryModule.ControllerLayer.SimpleObjects.Category;
import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private TextFormatter tf = new TextFormatter();
    private final List<String> OperationsList;
    private final List<String> itemModificationList;
    private final List<String> categoryModificationList;

    public Menu() {
        itemModificationList = setupItemModList();
        this.OperationsList = setupOperationsList();
        categoryModificationList = setupCategoryModList();
    }

    //Setup Menu Strings
    private List<String> setupCategoryModList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("change category name");
        list.add("change new parent category");

        return list;
    }
    private List<String> setupItemModList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("change item name");
        list.add("change item category");
        list.add("change item cost price");
        list.add("change item sell price");
        list.add("change item storage location");
        list.add("change item store location");
        list.add("change item storage quantity");
        list.add("change item store quantity");
        list.add("Add supplier");
        list.add("Remove supplier");

        return list;
    }
    private List<String> setupOperationsList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Show item");
        list.add("Add item");
        list.add("Edit item");
        list.add("Remove item");
        list.add("Show category");
        list.add("Add category");
        list.add("Edit category");
        list.add("Remove category");

        return list;
    }

    //Getters
    public List<String> getOperationsList() {
        return OperationsList;
    }
    public List<String> getItemModificationList() {
        return itemModificationList;
    }
    public List<String> getCategoryModificationList() {
        return categoryModificationList;
    }

    //print Options
    public void printWelcomePrompt() {
        System.out.println("Welcome to 'Super-Li' Inventory System!");
    }
    public void printOperationMenu(List<String> operationList) {
        printMenu(operationList);
        System.out.println("\npress q to quit");
    }
    public void printMenu(List<String> menuElements) {
        System.out.println("please enter the number of the desired Operation: ");
        int option = 1;
        for (String s : menuElements) {
            System.out.println(option + ". " + s);
            option++;
        }
    }
    public void ErrorPrompt(String errorInput) {
        System.out.println("invalid input entered: " + errorInput);
    }

    //Entities print methods
    public void printItemPrompt(Item item) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        System.out.println(tf.formatItemMenuColumns());
        Method[] getMethods = {item.getClass().getMethod("getID"), item.getClass().getMethod("getName"),
                item.getClass().getMethod("getCostPrice"), item.getClass().getMethod("getSellingPrice"),
                item.getClass().getMethod("getStoreQuantity"), item.getClass().getMethod("getStorageQuantity"),
                item.getClass().getMethod("getTotalQuantity"), item.getClass().getMethod("getMinAmount"),
                item.getClass().getMethod("getStoreLocation"), item.getClass().getMethod("getStorageLocation"),
                item.getClass().getMethod("getManufacturerID")};
        String output = "";
        for (int i = 0; i < getMethods.length - 1; i++) {
            Method currentMethod = getMethods[i];
            output = output + tf.centerString(currentMethod.invoke(item).toString(), tf.getPaddingSize()) + "|";
        }
        System.out.println(output + tf.centerString(getMethods[getMethods.length - 1].invoke(item).toString(), tf.getPaddingSize()));

    }
    public void printCategoryPrompt(Category category) {
        System.out.println("Category Name: " + category.getName() + "\n" +
                "Parent Category: " + category.getParentCategory().getName() + "\n" +
                "Sub-categories: " + category.getSubCategories().stream().map((c)-> c.getName()).
                                        reduce("",(acc,curr) -> acc + ", " + curr));
        System.out.println(tf.formatItemMenuColumns());
        for (Item i: category.getItems()) {

            try {
                printItemPrompt(i);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }




}
