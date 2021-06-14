package PresentationLayer;

import InfrastructurePackage.TextFormatter;
import ServiceLayer.FacadeObjects.*;
import ServiceLayer.FacadeObjects.FacadeProduct;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InventorySupplierMenu {
    private TextFormatter tf = new TextFormatter();
    private final List<String> mainMenuSelectionList;
    //menu lists for items
    private final List<String> inventoryMainMenuList;

    private final List<String> itemOperationMenuList;
    private final List<String> itemModificationList;
    //menu lists for category
    private final List<String> categoryModificationList;
    private final List<String> categoryOperationMenuList;
    //menu lists for sale
    private final List<String> saleOperationMenuList;
    private final List<String> saleModificationList;
    //menu lists for discount
    private final List<String> discountOperationMenuList;
    //menu lists for report
    private final List<String> reportsOperationMenuList;



    //menu lists suppliers
    private final List<String> suppliersMainMenuList;

    private Scanner scan;

    public InventorySupplierMenu() {
        itemModificationList = setupItemModList();
        mainMenuSelectionList = setupMainMenuSelectionList();
        categoryModificationList = setupCategoryModList();
        saleModificationList = setupSaleModList();
        scan = new Scanner(System.in);


        inventoryMainMenuList = setupInventoryMainMenuList();
        itemOperationMenuList = setupItemMenu();
        categoryOperationMenuList = setupCategoryMenu();
        saleOperationMenuList = setupSaleMenu();
        discountOperationMenuList = setupDiscountMenuList();
        reportsOperationMenuList = setupReportMenuList();
        suppliersMainMenuList = setupSupplierMainList();
    }

    private List<String> setupSupplierMainList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("add supplier");
        list.add("get supplier");
        list.add("update supplier details");
        list.add("add quantity List");
        list.add("edit quantity List");
        list.add("edit agreement");
        list.add("get quantityList");
        list.add("get agreement");
        list.add("remove supplier");

        return list;
    }

    private List<String> setupMainMenuSelectionList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Inventory Menu");
        list.add("Suppliers Menu");
        list.add("Orders Menu");

        return list;
    }

    //Setup Menu Strings
    private List<String> setupCategoryModList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Change category name");
        list.add("Change new parent category");

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

        return list;
    }

    private List<String> setupDiscountMenuList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Show supplier discount");
        return list;
    }

    private List<String> setupSaleMenu() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Show sale");
        list.add("Add item sale");
        list.add("Add category sale");
        list.add("Modify sale");

        return list;
    }

    private List<String> setupCategoryMenu() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Show category");
        list.add("Add category");
        list.add("Modify category");
        list.add("Remove category");

        return list;
    }

    private List<String> setupItemMenu() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Show item");
        list.add("Add item");
        list.add("Modify item");
        list.add("Remove item");

        return list;
    }

    private List<String> setupInventoryMainMenuList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("Item menu");
        list.add("Category menu");
        list.add("Sale menu");
        list.add("Supplier discount menu");
        list.add("Report defect");
        list.add("Reports menu");
        list.add("Approve Shipment");

        return list;
    }

    private List<String> setupReportMenuList(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Inventory report");
        list.add("Category report");
        list.add("Item shortage Report");
        list.add("Defect Report");

        return list;
    }

    //print Options
    public void printWelcomePrompt() {
        System.out.println("Welcome to the 'Super-Lee' Inventory System!");
    }
    public void printMainMenu() {
        printMenu(mainMenuSelectionList);

    }
    public void printInventoryMainMenu(){
        printMenu(inventoryMainMenuList);
    }
    public void printItemMenu(){
        printMenu(itemOperationMenuList);
    }
    public void printCategoryMenu(){
        printMenu(categoryOperationMenuList);
    }
    public void printSaleMenu(){
        printMenu(saleOperationMenuList);
    }
    public void printDiscountMenu(){
        printMenu(discountOperationMenuList);
    }
    public void printReportMenu(){
        printMenu(reportsOperationMenuList);
    }
    public void printItemModMenu(){
        printMenu(itemModificationList);
    }
    public void printCategoryModList(){
        printMenu(categoryModificationList);
    }

    public void printSaleModList(){
        printMenu(saleModificationList);
    }

    public void printMenu(List<String> menuElements) {
        System.out.println("\nPlease enter the number of the desired operation: ");
        int option = 1;
        for (String s : menuElements) {
            System.out.println(option + ". " + s);
            option++;
        }
        System.out.println(option + ". return");
    }

    public void errorPrompt(String errorInput) {
        System.out.println("Error: " + errorInput);
    }

    //Entities print methods
    public void printEntity(FacadeEntity e) {
        e.printMe(this);
    }

    public void itemHeader() {
        System.out.println(tf.formatItemMenuColumns());
    }
    public void productHeader(){
        System.out.println(tf.formatProductMenuColumns());
    }

    public void printEntity(FacadeItem item) {
        try {
            printItem(item);
        } catch (Exception e) {
            errorPrompt("Something went wrong...");
        }
    }

    public void printEntity(List<FacadeItem> itemList) {
        itemHeader();
        for (FacadeItem i : itemList) {
            printEntity(i);
        }
    }

    public void printEntity(FacadeProduct orderProduct) throws NoSuchMethodException {
        Method[] methods = {orderProduct.getClass().getMethod("getProductID"), orderProduct.getClass().getMethod("getName"),
        orderProduct.getClass().getMethod("getAmount"),orderProduct.getClass().getMethod("getSellingPrice"),
        orderProduct.getClass().getMethod("getDiscount"), orderProduct.getClass().getMethod("getFinalPrice")};
        try {
            handleEntityAliment(orderProduct, methods);
        } catch (Exception e){
            errorPrompt("Something went wrong...");
        }
    }

    public void printEntity(ArrayList<FacadeProduct> orderProduct)throws NoSuchMethodException {
        System.out.println(tf.formatProductMenuColumns());
        for (FacadeProduct p :orderProduct) {
            printEntity(p);
        }
    }

    public void printEntity(FacadeCategory category) {
        tf.categoryMenuFormat(category);
        printEntity(category.getItems());
    }

    public <T extends FacadeEntity> void printEntity(FacadeSale<T> sale) {
        tf.saleMenuFormat(sale);
        if (sale.getAppliesOn().getClass() == FacadeItem.class){
            itemHeader();
            printEntity((FacadeItem) sale.getAppliesOn());
        }
        else if (sale.getAppliesOn().getClass() == FacadeCategory.class)
            printEntity((FacadeCategory) sale.getAppliesOn());
    }

    public <T extends FacadeEntity> void printEntity(FacadeDiscount<T> discount) {
        tf.discountMenuFormat(discount);
        if (discount.getAppliesOn().getClass() == FacadeCategory.class) {
            printEntity((FacadeCategory) discount.getAppliesOn());
        } else if (discount.getAppliesOn().getClass() == FacadeItem.class)
            printEntity((FacadeItem) discount.getAppliesOn());
    }

    public void printEntity(FacadeDefectEntry entry) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method[] getMethods = {entry.getClass().getMethod("getEntryDate"), entry.getClass().getMethod("getItemId"),
                entry.getClass().getMethod("getItemName"), entry.getClass().getMethod("getQuantity"),
                entry.getClass().getMethod("getLocation")};
        handleEntityAliment(entry, getMethods);
    }

    //User Input methods
    public String instructAndReceive(String instruction) {
        System.out.print(instruction);
        return scan.nextLine();
    }
    
    public <T> T instructAndReceive(String instruction, Class<T> type) {
        System.out.print(instruction);
        String next = scan.nextLine();
        if (type == Integer.class) {
            Integer wantedInt;
            try {
                while (!Pattern.matches("(^[1-9][0-9]*$)|0", next)) {
                    System.out.println("invalid input: " + next);
                    System.out.print(instruction);
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
                    System.out.print(instruction);
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

    private void printItem(FacadeItem item) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Method[] getMethods = {item.getClass().getMethod("getID"), item.getClass().getMethod("getName"),
                item.getClass().getMethod("getCostPrice"), item.getClass().getMethod("getSellingPrice"),
                item.getClass().getMethod("getShelfQuantity"), item.getClass().getMethod("getStorageQuantity"),
                item.getClass().getMethod("getTotalQuantity"), item.getClass().getMethod("getMinAmount"),
                item.getClass().getMethod("getShelfLocation"), item.getClass().getMethod("getStorageLocation"),
                item.getClass().getMethod("getManufacturerID"), item.getClass().getMethod("getCategory"), item.getClass().getMethod("getWeight")};
        handleEntityAliment(item, getMethods);
    }


    private <T> void handleEntityAliment(T elem, Method[] getMethods) throws IllegalAccessException, InvocationTargetException {
        String output = "";
        for (int i = 0; i < getMethods.length - 1; i++) {
            Method currentMethod = getMethods[i];
            Object value = currentMethod.invoke(elem);
            String stringRep = value.toString();
//            if (value instanceof Calendar) {
//                stringRep = tf.dateFormat((Calendar) value);
//            }
            output = output + tf.centerString(stringRep, tf.getPaddingSize()) + "|";
        }
        Object value = getMethods[getMethods.length - 1].invoke(elem);
        String stringRep = value.toString();
        System.out.println(output + tf.centerString(stringRep, tf.getPaddingSize()));
    }

    public void printDefectMenu() {
        System.out.println(tf.defectsMenuFormat());
    }


}
