package InventoryModule.PresentationLayer;

import InventoryModule.ControllerLayer.InventoryService;
import InventoryModule.ControllerLayer.InventoryServiceImpl;
import InventoryModule.ControllerLayer.Response;
import InventoryModule.ControllerLayer.ResponseT;
import InventoryModule.ControllerLayer.SimpleObjects.Category;
import InventoryModule.ControllerLayer.SimpleObjects.Item;

import java.util.ArrayList;
import java.util.Scanner;

public class PresentationController implements Runnable {
    private final InventoryService service;
    private final Menu menu;


    public PresentationController() {
        this.service = new InventoryServiceImpl();
        this.menu = new Menu();
    }

    //Item related method
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
        menu.printMenu(menu.getItemModificationList());

        Response modResp =  editItemChoiceInput(itemID);
        if(modResp.isErrorOccurred())
            System.out.println(modResp.getMessage());
        else
            System.out.println("Changes saved!");

    }
    private int getItemIDFromUser() {
        System.out.println("Enter item ID: ");
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }
    private Response editItemChoiceInput(int itemId){
        Scanner scan = new Scanner(System.in);
        String choice = scan.next();
        Response r = null;
         switch (choice){
            case "1" -> {
                System.out.println("Enter new item name");
                scan.useDelimiter("\n");
                String newName = scan.next();
                r = service.modifyItemName(itemId,newName);
            }
            case "2" -> {
                System.out.println("enter new item's category");
                System.out.println("");
                scan.useDelimiter("\n");
                String newCategory = scan.next();
                r = service.modifyItemCategory(itemId,newCategory);
            }
            case "3" -> {
                System.out.println("enter new item cost price");
                double newPrice = scan.nextDouble();
                r = service.modifyItemCostPrice(itemId,newPrice);
            }
            case "4" -> {
                System.out.println("enter new item selling price");
                double newPrice = scan.nextDouble();
                r = service.modifyItemSellingPrice(itemId,newPrice);
            }
            case "5" -> {
                System.out.println("enter new item storage location");
                scan.useDelimiter("\n");
                String newLocation = scan.next();
                r = service.changeItemStorageLocation(itemId,newLocation);
            }
            case "6" -> {
                System.out.println("enter new item store location");
                scan.useDelimiter("\n");
                String newLocation = scan.next();
                r = service.changeItemShelfLocation(itemId,newLocation);
            }
            case "7" -> {
                System.out.println("enter new item storage quantity");
                int newQuantity = scan.nextInt();
                r = service.modifyItemStorageQuantity(itemId,newQuantity);
            }
            case "8" -> {
                System.out.println("enter new item store quantity");
                int newQuantity = scan.nextInt();
                r = service.modifyItemShelfQuantity(itemId,newQuantity);
            }
            case "9" -> {
                System.out.println("add  new supplier for the item: (enter supplier id)");
                int newSupplier = scan.nextInt();
                r = service.addItemSupplier(itemId,newSupplier);
            }
            case "10" -> {
                System.out.println("remove a supplier for the item: (enter supplier id)");
                int oldSupplier = scan.nextInt();
                r = service.removeItemSupplier(itemId,oldSupplier);
            }
            default -> r = new Response(true,"invalid choice");
        }
        return r;
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
    //category related method
    private String getCategoryNameFromUser(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter category name: ");
        scan.useDelimiter("\n");
        return scan.next();
    }
    public void addCategory() {
        Scanner scan = new Scanner(System.in);
        String categoryName = getCategoryNameFromUser();
        System.out.println("Enter parent category: (enter nothing for not setting a parent category)");
        String parentCategoryName = scan.nextLine();
        ResponseT<Category> categoryR = service.getCategory(parentCategoryName);
        if(categoryR != null && categoryR.isErrorOccurred()){
            System.out.println(categoryR.getMessage());
        }

        Response addR = service.addCategory(categoryName,parentCategoryName);
        if(categoryR != null && addR.isErrorOccurred())
            System.out.println(addR.getMessage());
        else
            System.out.println("Category added successfully");

    }
    public void showCategory() {
        String categoryName = getCategoryNameFromUser();
        ResponseT<Category> rCategory = service.getCategory(categoryName);
        if(rCategory.isErrorOccurred())
            System.out.println(rCategory.getMessage());
        else
            menu.printCategoryPrompt(rCategory.getDate());
    }
    public void editCategory() {
        Scanner scan = new Scanner(System.in);
        String catName = getCategoryNameFromUser();
        ResponseT<Category> catR = service.getCategory(catName);
        if(catR.isErrorOccurred()) {
            catR.getMessage();
            return;
        }
        else
        menu.printCategoryPrompt(catR.getDate());
        menu.printMenu(menu.getCategoryModificationList());
        scan.useDelimiter("\n");
        String userInput = scan.next();
        Response modResp = null;
        switch (userInput){
            case "1" -> {
                System.out.println("Enter new Name");
                scan.useDelimiter("\n");
                String newName = scan.next();
                modResp = service.modifyCategoryName(catR.getDate().getName(), newName);
            }
            case "2" -> {
                System.out.println("Enter new parent category name: (keep in mind not to use a subcategory!)");
                scan.useDelimiter("\n");
                String newParent = scan.next();
                modResp = service.changeParentCategory(catR.getDate().getName(),newParent);
            }
            default -> modResp = new Response(true,"invalid choice");
        }

        if(modResp.isErrorOccurred())
            System.out.println(modResp.getMessage());
        else
            System.out.println("Changes Saved!");

    }
    public void removeCategory() {
        String catName = getCategoryNameFromUser();
        Response catR = service.removeCategory(catName);
        if(catR.isErrorOccurred())
            System.out.println(catR.getMessage());
        else
            System.out.println("Category removed!");
    }


    @Override
    public void run() {
        menu.printWelcomePrompt();
        while (true) {
            menu.printOperationMenu(menu.getOperationsList());
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
