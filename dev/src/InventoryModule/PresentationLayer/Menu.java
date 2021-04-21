package InventoryModule.PresentationLayer;

import InfrastructurePackage.TextFormatter;
import InventoryModule.ControllerLayer.SimpleObjects.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    private TextFormatter tf = new TextFormatter();
    private final List<String> operationsList;
    private final List<String> itemModificationList;
    private final List<String> categoryModificationList;
    private final List<String> saleModificationList;
    private Scanner scan;

    public Menu() {
        itemModificationList = setupItemModList();
        this.operationsList = setupOperationsList();
        categoryModificationList = setupCategoryModList();
        saleModificationList = setupSaleModList();
        scan = new Scanner(System.in);
    }

    //Setup Menu Strings
    private List<String> setupCategoryModList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Change category name");
        list.add("Change new parent category");
        list.add("Change sale dates");

        return list;
    }

    private List<String> setupSaleModList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Change sale name");
        list.add("Change discount");
        list.add("Change sale dates");

        return list;
    }

    private List<String> setupItemModList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Change item name");
        list.add("Change item category");
        list.add("Change item cost price");
        list.add("Change item sell price");
        list.add("Change item storage location");
        list.add("Change item shelf location");
        list.add("Change item storage quantity");
        list.add("Change item shelf quantity");
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
        list.add("Show sale");
        list.add("Add Item Sale");
        list.add("Add Category Sale");
        list.add("Edit Sale");
        list.add("Show supplier discount");
        list.add("Add supplier Item discount");
        list.add("Add supplier Category discount");
        list.add("Report defects");
        list.add("Inventory Report");
        list.add("Category Report");
        list.add("Item Shortage Report");
        list.add("Defects Report");

        return list;
    }

    //Getters
    public List<String> getItemModificationList() {
        return itemModificationList;
    }

    public List<String> getCategoryModificationList() {
        return categoryModificationList;
    }

    public List<String> getSaleModificationList() {
        return saleModificationList;
    }

//    public TextFormatter getTextFormatter() {
//        return tf;
//    }

    //print Options
    public void printWelcomePrompt() {
        System.out.println("Welcome to the 'Super-Lee' Inventory System!");
    }

    public void printOperationMenu() {
        printMenu(operationsList);
        System.out.println("\nPress q to quit");
    }

    public void printMenu(List<String> menuElements) {
        System.out.println("\nPlease enter the number of the desired operation: ");
        int option = 1;
        for (String s : menuElements) {
            System.out.println(option + ". " + s);
            option++;
        }
    }

    public void errorPrompt(String errorInput) {
        System.out.println("Error: " + errorInput);
    }

    //Entities print methods
    public void printEntity(SimpleEntity e) {
        e.printMe(this);
    }

    public void itemHeader() {
        System.out.println(tf.formatItemMenuColumns());
    }

    public void printEntity(Item item) {
        try {
            printItem(item);
        } catch (Exception e) {
            errorPrompt("Something went wrong...");
        }
    }

    public void printEntity(List<Item> itemList) {
        itemHeader();
        for (Item i : itemList) {
            printEntity(i);
        }
    }

    public void printEntity(Category category) {
        tf.CategoryMenuFormat(category);
        printEntity(category.getItems());
    }

    public <T extends SimpleEntity> void printEntity(Sale<T> sale) {
        tf.saleMenuFormat(sale);
        if (sale.getAppliesOn().getClass() == Item.class)
            printEntity((Item) sale.getAppliesOn());
        else if (sale.getAppliesOn().getClass() == Category.class)
            printEntity((Category) sale.getAppliesOn());
    }

    public <T extends SimpleEntity> void printEntity(Discount<T> discount) {
        tf.discountMenuFormat(discount);
        if (discount.getAppliesOn().getClass() == Category.class) {
            printEntity((Category) discount.getAppliesOn());
        } else if (discount.getAppliesOn().getClass() == Item.class)
            printEntity((Item) discount.getAppliesOn());
    }

    public void printEntity(DefectEntry entry) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method[] getMethods = {entry.getClass().getMethod("getEntryDate"), entry.getClass().getMethod("getItemId"),
                entry.getClass().getMethod("getItemName"), entry.getClass().getMethod("getQuantity"),
                entry.getClass().getMethod("getLocation")};
        handleEntityAliment(entry, getMethods);
    }

    //User Input methods
    public String instructAndReceive(String instruction) {
        System.out.println(instruction);
        return scan.nextLine();
    }

    public <T> T instructAndReceive(String instruction, Class<T> type) {
        System.out.println(instruction);
        String next = scan.nextLine();
        if (type == Integer.class) {
            Integer wantedInt;
            try {
                while (!Pattern.matches("(^[1-9][0-9]+$)|0", next)) {
                    System.out.println("invalid input: " + next);
                    System.out.println(instruction);
                    next = scan.nextLine();
                }
                wantedInt = Integer.parseInt(next);
                return (T) wantedInt;
            } catch (Exception e) {
                System.out.println("something went wrong");
                return null;
            }

        }
        if (type == Double.class) {
            Double wantedDouble;
            try {
                while (!Pattern.matches("^\\d+(\\.\\d+)?", next)) {
                    System.out.println("invalid input: " + next);
                    System.out.println(instruction);
                    next = scan.nextLine();
                }
                wantedDouble = Double.parseDouble(next);
                return (T) wantedDouble;
            } catch (Exception e) {
                System.out.println("something went wrong");
                return null;
            }
        }

        return (T) next;
    }

    private void printItem(Item item) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method[] getMethods = {item.getClass().getMethod("getID"), item.getClass().getMethod("getName"),
                item.getClass().getMethod("getCostPrice"), item.getClass().getMethod("getSellingPrice"),
                item.getClass().getMethod("getShelfQuantity"), item.getClass().getMethod("getStorageQuantity"),
                item.getClass().getMethod("getTotalQuantity"), item.getClass().getMethod("getMinAmount"),
                item.getClass().getMethod("getShelfLocation"), item.getClass().getMethod("getStorageLocation"),
                item.getClass().getMethod("getManufacturerID"), item.getClass().getMethod("getCategory")};
        handleEntityAliment(item, getMethods);
    }


    private <T> void handleEntityAliment(T elem, Method[] getMethods) throws IllegalAccessException, InvocationTargetException {
        String output = "";
        for (int i = 0; i < getMethods.length - 1; i++) {
            Method currentMethod = getMethods[i];
            Object value = currentMethod.invoke(elem);
            String stringRep = value.toString();
            if (value instanceof Calendar) {
                stringRep = tf.dateFormat((Calendar) value);
            }
            output = output + tf.centerString(stringRep, tf.getPaddingSize()) + "|";
        }
        Object value = getMethods[getMethods.length - 1].invoke(elem);
        String stringRep = value.toString();
        if (value instanceof Calendar) {
            stringRep = tf.dateFormat((Calendar) value);
        }
        System.out.println(output + tf.centerString(stringRep, tf.getPaddingSize()));
    }

    public void printDefectMenu() {
        System.out.println(tf.DefectsMenuFormat());
    }


}
