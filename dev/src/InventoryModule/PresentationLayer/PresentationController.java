package InventoryModule.PresentationLayer;

import InfrastructurePackage.Pair;
import InventoryModule.ControllerLayer.InventoryService;
import InventoryModule.ControllerLayer.InventoryServiceImpl;
import InventoryModule.ControllerLayer.Response;
import InventoryModule.ControllerLayer.ResponseT;
import InventoryModule.ControllerLayer.SimpleObjects.Category;
import InventoryModule.ControllerLayer.SimpleObjects.Item;
import InventoryModule.ControllerLayer.SimpleObjects.Sale;
import InventoryModule.ControllerLayer.SimpleObjects.SimpleEntity;

import java.util.*;

public class PresentationController implements Runnable {
    private final InventoryService service;
    private final Menu menu;


    public PresentationController() {
        this.service = new InventoryServiceImpl();
        this.menu = new Menu();
    }

    //Item related method
    private void addItem() {
        int id = Integer.parseInt(menu.instructAndReceive("Enter item ID: "));
        String name = menu.instructAndReceive("Enter name: ");
        double costP = Double.parseDouble(menu.instructAndReceive("Enter cost price: "));
        double sellP = Double.parseDouble(menu.instructAndReceive("Enter sell price: "));
        int minA = Integer.parseInt(menu.instructAndReceive("Enter item ID: "));
        int storageQ = Integer.parseInt(menu.instructAndReceive("Enter storage quantity: "));
        int storeQ = Integer.parseInt(menu.instructAndReceive("Enter store quantity: "));
        String storageL = menu.instructAndReceive("Enter storage Location: follow this format \"ST-A<number>-<L/R>-S<shelf>\"");
        String storeL = menu.instructAndReceive("Enter storage Location: follow this format \"SH-A<number>-<L/R>-S<shelf>\"");
        int idManf = Integer.parseInt(menu.instructAndReceive("Enter Manufacturer ID: "));
        String category = menu.instructAndReceive("Enter category Name: ");

        Response r = service.addItem(id, name, category, costP, sellP, minA, storeL, storageL, storageQ, storeQ, idManf, new ArrayList<>());
        if (r.isErrorOccurred()) {
            menu.errorPrompt(r.getMessage());
        } else {
            System.out.println("Item added successfully");
        }
    }

    private void showItem() {
        int itemID = Integer.parseInt(menu.instructAndReceive("Enter item ID: "));
        ResponseT<Item> r = service.getItem(itemID);
        try {
            menu.printEntity(r.getDate());
        } catch (Exception e) {
            menu.errorPrompt("could not get Item. make sure you entered the Id correctly");
        }
    }

    private void editItem() {
        int itemID = Integer.parseInt(menu.instructAndReceive("Enter item ID: "));
        ResponseT<Item> r = service.getItem(itemID);
        try {
            menu.printEntity(r.getDate());
        } catch (Exception e) {
            menu.errorPrompt("could not get Item. make sure you entered the Id correctly");
        }
        menu.printMenu(menu.getItemModificationList());

        Response modResp = editItemChoiceInput(itemID);
        if (modResp.isErrorOccurred())
            System.out.println(modResp.getMessage());
        else
            System.out.println("Changes saved!");

    }

    private Response editItemChoiceInput(int itemId) {
        Scanner scan = new Scanner(System.in);
        String choice = scan.next();
        Response r = null;
        switch (choice) {
            case "1" -> {
                String newName = menu.instructAndReceive("Enter new item name");
                r = service.modifyItemName(itemId, newName);
            }
            case "2" -> {
                String newCategory = menu.instructAndReceive("Enter new item's category");
                r = service.modifyItemCategory(itemId, newCategory);
            }
            case "3" -> {
                double newPrice = Double.parseDouble(menu.instructAndReceive("enter new item cost price"));
                r = service.modifyItemCostPrice(itemId, newPrice);
            }
            case "4" -> {
                double newPrice = Double.parseDouble(menu.instructAndReceive("enter new item selling price"));
                r = service.modifyItemSellingPrice(itemId, newPrice);
            }
            case "5" -> {
                String newLocation = menu.instructAndReceive("enter new item storage location");
                r = service.changeItemStorageLocation(itemId, newLocation);
            }
            case "6" -> {
                String newLocation = menu.instructAndReceive("enter new item store location");
                r = service.changeItemShelfLocation(itemId, newLocation);
            }
            case "7" -> {
                int newQuantity = Integer.parseInt(menu.instructAndReceive("enter new item storage quantity"));
                r = service.modifyItemStorageQuantity(itemId, newQuantity);
            }
            case "8" -> {
                int newQuantity = Integer.parseInt(menu.instructAndReceive("enter new item store quantity"));
                r = service.modifyItemShelfQuantity(itemId, newQuantity);
            }
            case "9" -> {
                int newSupplier = Integer.parseInt(menu.instructAndReceive("add  new supplier for the item: (enter supplier id)"));
                r = service.addItemSupplier(itemId, newSupplier);
            }
            case "10" -> {
                System.out.println("remove a supplier for the item: (enter supplier id)");
                int oldSupplier = scan.nextInt();
                r = service.removeItemSupplier(itemId, oldSupplier);
            }
            default -> r = new Response(true, "invalid choice");
        }
        return r;
    }

    private void removeItem() {
        int itemID = Integer.parseInt(menu.instructAndReceive("Enter item ID: "));
        Response r = service.removeItem(itemID);
        if (r.isErrorOccurred()) {
            menu.errorPrompt(r.getMessage());
        } else {
            System.out.println("Item removed successfully");
        }
    }

    //category related method
    private void addCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        String parentCategoryName = menu.instructAndReceive("Enter parent category: (enter nothing for not setting a parent category)");

        ResponseT<Category> categoryR = service.getCategory(parentCategoryName);
        if (categoryR != null && categoryR.isErrorOccurred()) {
            menu.errorPrompt(categoryR.getMessage());
        }
        Response addR = service.addCategory(catName, parentCategoryName);
        if (categoryR != null && addR.isErrorOccurred())
            System.out.println(addR.getMessage());
        else
            System.out.println("Category added successfully");

    }

    private void showCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        ResponseT<Category> rCategory = service.getCategory(catName);
        if (rCategory.isErrorOccurred())
            System.out.println(rCategory.getMessage());
        else
            menu.printEntity(rCategory.getDate());
    }

    private void editCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        ResponseT<Category> catR = service.getCategory(catName);
        if (catR.isErrorOccurred()) {
            menu.errorPrompt(catR.getMessage());
            return;
        } else
            menu.printEntity(catR.getDate());
        menu.printMenu(menu.getCategoryModificationList());
        String userInput = menu.instructAndReceive("Enter choice: ");
        Response modResp = null;
        switch (userInput) {
            case "1" -> {

                String newName = menu.instructAndReceive("Enter new name: ");
                modResp = service.modifyCategoryName(catR.getDate().getName(), newName);
            }
            case "2" -> {
                String newParent = menu.instructAndReceive("Enter new parent category name: (keep in mind not to use a subcategory!)");
                modResp = service.changeParentCategory(catR.getDate().getName(), newParent);
            }
            default -> modResp = new Response(true, "invalid choice");
        }

        if (modResp.isErrorOccurred())
            menu.errorPrompt(modResp.getMessage());
        else
            System.out.println("Changes Saved!");

    }

    private void removeCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        Response catR = service.removeCategory(catName);
        if (catR.isErrorOccurred())
            menu.errorPrompt(catR.getMessage());
        else
            System.out.println("Category removed!");
    }

    private <T extends SimpleEntity> void showSale() {
        String saleName = menu.instructAndReceive("Enter sale name:");
        ResponseT<Sale<T>> saleR = service.showSale(saleName);
        if (saleR.isErrorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
        } else {
            menu.printEntity(saleR.getDate());
        }
    }

    private void addItemSale() {
        String saleName = menu.instructAndReceive("Enter sale name:");
        double saleDiscount = Double.parseDouble(menu.instructAndReceive("Enter sale Discount: for 10% enter 0.1"));
        Pair<Calendar, Calendar> dates = getSaleDates();
        if (dates == null) return;
        //getting the item to be applied on
        int applyOnItem = Integer.parseInt(menu.instructAndReceive("Enter item id for the sale: "));
        //checking if exists
        ResponseT<Item> rExist = service.getItem(applyOnItem);
        if (rExist.isErrorOccurred()) {
            menu.errorPrompt(rExist.getMessage());
            return;
        }
        Response r1 = service.addItemSale(saleName,applyOnItem,saleDiscount,dates.getFirst(), dates.getSecond());
        if(r1.isErrorOccurred()){
            menu.errorPrompt(r1.getMessage());
            return;
        }
        System.out.println("Sale Added successfully");


    }

    private void addCategorySale() {
        String saleName = menu.instructAndReceive("Enter sale name:");
        double saleDiscount = Double.parseDouble(menu.instructAndReceive("Enter sale Discount: for 10% enter 0.1"));
        //getting the dates
        Pair<Calendar, Calendar> dates = getSaleDates();
        if (dates == null) return;

        //getting the item to be applied on
        String applyOnItem = menu.instructAndReceive("Enter category name for the sale: ");
        //checking if exists
        ResponseT<Category> rExist = service.getCategory(applyOnItem);
        if (rExist.isErrorOccurred()) {
            menu.errorPrompt(rExist.getMessage());
            return;
        }
        Response r1 = service.addCategorySale(saleName,applyOnItem,saleDiscount,dates.getFirst(), dates.getSecond());
        if(r1.isErrorOccurred()){
            menu.errorPrompt(r1.getMessage());
            return;
        }
        System.out.println("Sale Added successfully");
    }

    private Pair<Calendar, Calendar> getSaleDates() {
        String[] startDate = menu.instructAndReceive("Enter start date: use this format <YYYY-MM-DD>").split("-");
        Calendar start = Calendar.getInstance();
        start.set(Integer.parseInt(startDate[0]), Integer.parseInt(startDate[1]), Integer.parseInt(startDate[2]));
        String[] endDate = menu.instructAndReceive("Enter end date: use this format <YYYY-MM-DD>").split("-");
        Calendar end = Calendar.getInstance();
        end.set(Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]), Integer.parseInt(endDate[2]));
        Pair<Calendar,Calendar> dates = new Pair<>(start,end);
        //checking that the date make sense
        if(start.compareTo(end) > 0){
            menu.errorPrompt("end date is before start date");
            return null;
        }
        return dates;
    }

    private <T extends SimpleEntity> void modifySale() {
        String saleName = menu.instructAndReceive("Enter category name: ");
        ResponseT<Sale<T>> saleR = service.showSale(saleName);
        if (saleR.isErrorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
            return;
        } else
            menu.printEntity(saleR.getDate());

        menu.printMenu(menu.getSaleModificationList());
        String userInput = menu.instructAndReceive("Enter choice: ");
        Response modResp = null;
        switch (userInput) {
            case "1" -> {
                String newName = menu.instructAndReceive("Enter new name: ");
                modResp = service.modifySaleName(saleR.getDate().getName(), newName);
            }
            case "2" -> {
                double newDisc = Double.parseDouble(menu.instructAndReceive("Enter sale Discount: (for 10% enter 0.1) "));
                modResp = service.modifySaleDiscount(saleR.getDate().getName(), newDisc);
            }
            case "3" -> {
                Pair<Calendar, Calendar> dates = getSaleDates();
                modResp = service.modifySaleDates(saleR.getDate().getName(), dates.getFirst(),dates.getSecond());
            }
            default -> modResp = new Response(true, "invalid choice");
        }
        if (modResp.isErrorOccurred())
            menu.errorPrompt(modResp.getMessage());
        else
            System.out.println("Changes Saved!");
    }

    private void showSupplierDiscount() {
    }

    private void addItemSupplierDiscount() {
    }

    private void addCategorySupplierDiscount() {
    }

    private void recordDefect() {
    }

    private void inventoryReport() {
    }

    private void categoryReport() {
    }

    private void itemShortageReport() {
    }

    private void defectsReport() {
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
            case "9" -> showSale();
            case "10" -> addItemSale();
            case "11" -> addCategorySale();
            case "12" -> modifySale();
            case "13" -> showSupplierDiscount();
            case "14" -> addItemSupplierDiscount();
            case "15" -> addCategorySupplierDiscount();
            case "16" -> recordDefect();
            case "17" -> inventoryReport();
            case "18" -> categoryReport();
            case "19" -> itemShortageReport();
            case "20" -> defectsReport();
            default -> menu.errorPrompt(choice);
        }
    }


}
