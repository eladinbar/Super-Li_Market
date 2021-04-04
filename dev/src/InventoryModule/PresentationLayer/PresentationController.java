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
    private final InventoryService inventoryService;
    private final Menu menu;
    private final List<String> OperationsList;

    public PresentationController() {
        this.inventoryService = new InventoryServiceImpl();
        this.OperationsList = setupOperationsList();
        this.menu = new Menu();
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

        Response r = inventoryService.addItem(id,name, category,costP,sellP,minA,storeL,storageL,storageQ,storeQ,idManf, new ArrayList<>());
        if(r.isErrorOccurred()){
            menu.ErrorPrompt(r.getMessage());
        } else {
            System.out.println("Item added successfully");
        }
    }

    public void showItem() {
        System.out.println("Enter item ID: ");
        Scanner scan = new Scanner(System.in);
        int itemID = scan.nextInt();
        ResponseT<Item> r = InventoryService.getInventoryService().getItem(itemID);
        try {
            menu.printItemPrompt(r.getDate());
        } catch (Exception e) {
            menu.ErrorPrompt("could not get Item. make sure you entered the Id correctly");
        }
    }

    public void editItem() {
    }

    public void removeItem() {
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
