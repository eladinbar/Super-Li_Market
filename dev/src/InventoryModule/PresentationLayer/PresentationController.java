package InventoryModule.PresentationLayer;

import InventoryModule.ControllerLayer.InventoryService;
import InventoryModule.ControllerLayer.InventoryServiceImpl;
import InventoryModule.ControllerLayer.Response;
import InventoryModule.ControllerLayer.ResponseT;
import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PresentationController implements Runnable {
    private final InventoryService service;
    private final Menu menu;
    private final List<String> OperationsList;
    private final List<String> itemModificationList;

    public PresentationController() {
        this.service = new InventoryServiceImpl();
        this.OperationsList = setupOperationsList();
        this.itemModificationList = setupItemModList();
        this.menu = new Menu();
    }
    private ArrayList<String> setupItemModList(){
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

    private ArrayList<String> setupOperationsList() {
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

    public void addItem() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter ID: ");
        int id = scan.nextInt();
        System.out.println("Enter Name: ");
        scan.useDelimiter("\n");
        String name = scan.next();
        System.out.println("Enter cost price: ");
        double costP = scan.nextDouble();
        System.out.println("Enter sell price: ");
        double sellP = scan.nextDouble();
        System.out.println("Enter minimum amount for this item: ");
        int minA = scan.nextInt();
        System.out.println("Enter storage quantity: ");
        int storageQ = scan.nextInt();
        System.out.println("Enter store quantity: ");
        int storeQ = scan.nextInt();
        System.out.println("Enter storage Location: follow this format \"A<number>-<L/R>-S<shelf>\"");
        scan.useDelimiter("\n");
        String storageL = scan.next();
        System.out.println("Enter store Location: follow this format \"A<number>-<L/R>-S<shelf>\"");
        scan.useDelimiter("\n");
        String storeL = scan.next();
        System.out.println("Enter Manufacturer ID: ");
        int idManf = scan.nextInt();
        System.out.println("Enter category name: ");
        scan.useDelimiter("\n");
        String category = scan.next();

        Response r = service.addItem(id,name, category,costP,sellP,minA,storeL,storageL,storageQ,storeQ,idManf, new ArrayList<>());
        if(r.isErrorOccurred()){
            menu.ErrorPrompt(r.getMessage());
        } else {
            System.out.println("Item added successfully");
        }
    }

    public void showItem() {
        int itemID = getItemIDFromUser();
        ResponseT<Item> r = InventoryService.getInventoryService().getItem(itemID);
        try {
            menu.printItemPrompt(r.getDate());
        } catch (Exception e) {
            menu.ErrorPrompt("could not get Item. make sure you entered the Id correctly");
        }
    }

    public void editItem() {
        int itemID = getItemIDFromUser();
        ResponseT<Item> r = InventoryService.getInventoryService().getItem(itemID);
        try {
            menu.printItemPrompt(r.getDate());
        } catch (Exception e) {
            menu.ErrorPrompt("could not get Item. make sure you entered the Id correctly");
        }
        menu.printItemModificationMenu(itemModificationList);

        editItemChoiceInput(itemID);

    }

    private int getItemIDFromUser() {
        System.out.println("Enter item ID: ");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    private void editItemChoiceInput(int itemId){
        Scanner scan = new Scanner(System.in);
        String choice = scan.next();
        switch (choice){
            case "1" -> {
                System.out.println("Enter new item name");
                scan.useDelimiter("\n");
                String newName = scan.next();
                service.modifyItemName(itemId,newName);
            }
            case "2" -> {
                System.out.println("enter new item's category");
                System.out.println("");
                scan.useDelimiter("\n");
                String newCategory = scan.next();
                service.modifyItemCategory(itemId,newCategory);
            }
            case "3" -> {
                System.out.println("enter new item cost price");
                double newPrice = scan.nextDouble();
                service.modifyItemCostPrice(itemId,newPrice);
            }
            case "4" -> {
                System.out.println("enter new item selling price");
                double newPrice = scan.nextDouble();
                service.modifyItemSellingPrice(itemId,newPrice);
            }
            case "5" -> {
                System.out.println("enter new item storage location");
                scan.useDelimiter("\n");
                String newLocation = scan.next();
                service.changeItemStorageLocation(itemId,newLocation);
            }
            case "6" -> {
                System.out.println("enter new item store location");
                scan.useDelimiter("\n");
                String newLocation = scan.next();
                service.changeItemShelfLocation(itemId,newLocation);
            }
            case "7" -> {
                System.out.println("enter new item storage quantity");
                int newQuantity = scan.nextInt();
                service.modifyItemStorageQuantity(itemId,newQuantity);
            }
            case "8" -> {
                System.out.println("enter new item store quantity");
                int newQuantity = scan.nextInt();
                service.modifyItemShelfQuantity(itemId,newQuantity);
            }
            case "9" -> {
                System.out.println("add  new supplier for the item: (enter supplier id)");
                int newSupplier = scan.nextInt();
                service.addItemSupplier(itemId,newSupplier);
            }
            case "10" -> {
                System.out.println("remove a supplier for the item: (enter supplier id)");
                int oldSupplier = scan.nextInt();
                service.removeItemSupplier(itemId,oldSupplier);
            }
        }
    }

    public void removeItem() {
        int itemID = getItemIDFromUser();
        Response r = service.removeItem(itemID);
        if(r.isErrorOccurred()){
            menu.ErrorPrompt(r.getMessage());
        } else {
            System.out.println("Item removed successfully");
        }
    }

    public void addCategory() {
    }

    public void showCategory() {
    }

    public void editCategory() {
    }

    public void removeCategory() {
    }


    @Override
    public void run() {
        menu.printWelcomePrompt();
        while (true) {
            menu.printOperationMenu(OperationsList);
            operationInput();
        }

    }

    private void operationInput() {
        Scanner scan = new Scanner(System.in);
        String choice = scan.next();
        switch (choice) {
            case "1" -> showItem();
            case "2" -> addItem();
            case "3" -> editItem();
            case "4" -> removeItem();
            case "5" -> showCategory();
            case "6" -> addCategory();
            case "7" -> editCategory();
            case "8" -> removeCategory();
            default -> menu.ErrorPrompt(choice);
        }
    }
}
