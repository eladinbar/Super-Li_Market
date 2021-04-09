package InventoryModule.PresentationLayer;

import InfrastructurePackage.Pair;
import InventoryModule.ControllerLayer.InventoryService;
import InventoryModule.ControllerLayer.InventoryServiceImpl;
import InventoryModule.ControllerLayer.Response;
import InventoryModule.ControllerLayer.ResponseT;
import InventoryModule.ControllerLayer.SimpleObjects.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class PresentationController implements Runnable {
    private final InventoryService service;
    private final Menu menu;
    private boolean terminate;


    public PresentationController() {
        this.service = new InventoryServiceImpl();
        this.menu = new Menu();
        terminate = false;
    }

    //Item related method
    private void addItem() {
        int itemID;
        try {
            itemID = Integer.parseInt(menu.instructAndReceive("Enter item ID: "));
        } catch (Exception e){
            menu.errorPrompt("invalid input");
            return;
        }
        String name = menu.instructAndReceive("Enter name: ");
        double costP;
        try {
            costP = Double.parseDouble(menu.instructAndReceive("Enter cost price: "));
        } catch (Exception e){
            menu.errorPrompt("invalid input");
            return;
        }

        double sellP;
        try {
            sellP = Double.parseDouble(menu.instructAndReceive("Enter sell price: "));
        } catch (Exception e){
            menu.errorPrompt("invalid input");
            return;
        }

        int minA;
        try {
            minA = Integer.parseInt( menu.instructAndReceive("Enter minimum amount"));
        } catch (Exception e){
            menu.errorPrompt("invalid input");
            return;
        }

        int storageQ;
        try {
            storageQ = Integer.parseInt(menu.instructAndReceive("Enter storage quantity: "));
        } catch (Exception e){
            menu.errorPrompt("invalid input");
            return;
        }
        int storeQ;
        try {
            storeQ = Integer.parseInt(menu.instructAndReceive("Enter Shelf quantity: "));
        } catch (Exception e){
            menu.errorPrompt("invalid input");
            return;
        }
        String storageL = menu.instructAndReceive("Enter storage location: follow this format \"ST-A<number>-<L/R>-S<number>\"");
        String storeL = menu.instructAndReceive("Enter shelf location: follow this format \"SH-A<number>-<L/R>-S<number>\"");
        int intManfac;
        try{
            intManfac = Integer.parseInt(menu.instructAndReceive("Enter manufacturer ID: "));
        } catch (Exception e){
            menu.errorPrompt("Invalid Input");
            return;
        }

        String category = menu.instructAndReceive("Enter category name: ");

        Response r = service.addItem(itemID, name, category, costP, sellP, minA, storeL, storageL, storageQ, storeQ, intManfac, new ArrayList<>());
        if (r.isErrorOccurred()) {
            menu.errorPrompt(r.getMessage());
        } else {
            System.out.println("Item added successfully");
        }
    }

    private void showItem() {
        int itemID;
        try {
            itemID = Integer.parseInt(menu.instructAndReceive("Enter item ID: "));
        } catch (Exception e){
            menu.errorPrompt("invalid input");
            return;
        }
        ResponseT<Item> r = service.getItem(itemID);
        if (!r.isErrorOccurred()) {
            menu.printEntity(r.getData());
        } else {
            menu.errorPrompt(r.getMessage());
        }
    }

    private void editItem() {
        String temp = menu.instructAndReceive("Enter item ID: ");
        int itemID;
        try {
            itemID = Integer.parseInt(temp);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        ResponseT<Item> r = service.getItem(itemID);
        try {
            menu.printEntity(r.getData());
        } catch (Exception e) {
            menu.errorPrompt("Could not get item. Make sure you entered the ID correctly.");
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
        Response r;
        switch (choice) {
            case "1":
                String newName = menu.instructAndReceive("Enter new item name");
                r = service.modifyItemName(itemId, newName);
                break;
            case "2":
                String newCategory = menu.instructAndReceive("Enter new item category");
                r = service.modifyItemCategory(itemId, newCategory);
                break;
            case "3":
                String tempCost = menu.instructAndReceive("Enter cost price: ");
                double newCPrice;
                try {
                    newCPrice = Double.parseDouble(tempCost);
                } catch(Exception ex) {
                    menu.errorPrompt("Invalid input");
                    return new Response(true, "Invalid input");
                }
                r = service.modifyItemCostPrice(itemId, newCPrice);
                break;
            case "4":
                String tempSell = menu.instructAndReceive("Enter cost price: ");
                double newSPrice;
                try {
                    newSPrice = Double.parseDouble(tempSell);
                } catch(Exception ex) {
                    menu.errorPrompt("Invalid input");
                    return new Response(true, "Invalid input");
                }
                r = service.modifyItemSellingPrice(itemId, newSPrice);
                break;
            case "5":
                String newLocationST = menu.instructAndReceive("Enter new item storage location");
                r = service.changeItemStorageLocation(itemId, newLocationST);
                break;
            case "6":
                String newLocationSH = menu.instructAndReceive("Enter new item shelf location");
                r = service.changeItemShelfLocation(itemId, newLocationSH);
                break;
            case "7":
                String tempQuan = menu.instructAndReceive("Enter storage quantity: ");
                int newQuantityST;
                try {
                    newQuantityST = Integer.parseInt(tempQuan);
                } catch(Exception ex) {
                    menu.errorPrompt("Invalid input");
                    return new Response(true, "Invalid input");
                }
                r = service.modifyItemStorageQuantity(itemId, newQuantityST);
                break;
            case "8":
                String tempQuan2 = menu.instructAndReceive("Enter shelf quantity: ");
                int newQuantitySH;
                try {
                    newQuantitySH = Integer.parseInt(tempQuan2);
                } catch(Exception ex) {
                    menu.errorPrompt("Invalid input");
                    return new Response(true, "Invalid input");
                }
                r = service.modifyItemShelfQuantity(itemId, newQuantitySH);
                break;
            case "9":
                String tempSup = menu.instructAndReceive("\"Add  new supplier for the item: (enter supplier ID)\"): ");
                int newSupplier;
                try {
                    newSupplier = Integer.parseInt(tempSup);
                } catch(Exception ex) {
                    menu.errorPrompt("Invalid input");
                    return new Response(true, "Invalid input");
                }
                r = service.addItemSupplier(itemId, newSupplier);
                break;
            case "10":
                String tempRevSup = menu.instructAndReceive("Remove a supplier for the item: (enter supplier ID)");
                int oldSupplier;
                try {
                    oldSupplier = Integer.parseInt(tempRevSup);
                } catch(Exception ex) {
                    menu.errorPrompt("Invalid input");
                    return new Response(true, "Invalid input");
                }
                r = service.removeItemSupplier(itemId, oldSupplier);
                break;
            default:
                r = new Response(true, "Invalid choice");
                break;
        }
        return r;
    }

    private void removeItem() {
        String temp = menu.instructAndReceive("Enter item ID: ");
        int itemID;
        try {
            itemID = Integer.parseInt(temp);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
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
        String parentCategoryName = menu.instructAndReceive("Enter parent category: (enter nothing to set an item as 'uncategorized')");
        Response addR = service.addCategory(catName, parentCategoryName);
        if (addR.isErrorOccurred())
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
            menu.printEntity(rCategory.getData());
    }

    private void editCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        ResponseT<Category> catR = service.getCategory(catName);
        if (catR.isErrorOccurred()) {
            menu.errorPrompt(catR.getMessage());
            return;
        } else
            menu.printEntity(catR.getData());
        menu.printMenu(menu.getCategoryModificationList());
        String userInput = menu.instructAndReceive("Enter choice: ");
        Response modResp;
        switch (userInput) {
            case "1":
                String newName = menu.instructAndReceive("Enter new name: ");
                modResp = service.modifyCategoryName(catR.getData().getName(), newName);
                break;
            case "2":
                String newParent = menu.instructAndReceive("Enter new parent category name: (keep in mind not to use a subcategory!)");
                modResp = service.changeParentCategory(catR.getData().getName(), newParent);
                break;
            default:
                modResp = new Response(true, "Invalid choice");
                break;
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
        String saleName = menu.instructAndReceive("Enter sale name: ");
        ResponseT<Sale<T>> saleR = service.getSale(saleName);
        if (saleR.isErrorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
        } else {
            menu.printEntity(saleR.getData());
        }
    }

    private void addItemSale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        String tempDisc = menu.instructAndReceive("Enter sale discount: (e.g. for 10% enter 0.1)");
        double saleDiscount;
        try {
            saleDiscount = Double.parseDouble(tempDisc);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        Pair<Calendar, Calendar> dates = getStartEndDates();
        if (dates == null) return;
        //getting the item to be applied on
        String temp = menu.instructAndReceive("Enter item ID for the sale: ");
        int applyOnItem;
        try {
            applyOnItem = Integer.parseInt(temp);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //checking if exists
        ResponseT<Item> rExist = service.getItem(applyOnItem);
        if (rExist.isErrorOccurred()) {
            menu.errorPrompt(rExist.getMessage());
            return;
        }
        Response r1 = service.addItemSale(saleName, applyOnItem, saleDiscount, dates.getFirst(), dates.getSecond());
        if (r1.isErrorOccurred()) {
            menu.errorPrompt(r1.getMessage());
            return;
        }
        System.out.println("Sale Added successfully");


    }

    private void addCategorySale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        String tempDisc = menu.instructAndReceive("Enter sale discount: (e.g. for 10% enter 0.1)");
        double saleDiscount;
        try {
            saleDiscount = Double.parseDouble(tempDisc);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //getting the dates
        Pair<Calendar, Calendar> dates = getStartEndDates();
        if (dates == null) return;

        //getting the item to be applied on
        String applyOnItem = menu.instructAndReceive("Enter category name for the sale: ");
        //checking if exists
        ResponseT<Category> rExist = service.getCategory(applyOnItem);
        if (rExist.isErrorOccurred()) {
            menu.errorPrompt(rExist.getMessage());
            return;
        }
        Response r1 = service.addCategorySale(saleName, applyOnItem, saleDiscount, dates.getFirst(), dates.getSecond());
        if (r1.isErrorOccurred()) {
            menu.errorPrompt(r1.getMessage());
            return;
        }
        System.out.println("Sale added successfully");
    }

    private Pair<Calendar, Calendar> getStartEndDates() {
        String[] startDate = menu.instructAndReceive("Enter start date: use this format <YYYY-MM-DD>").split("-");
        Calendar start = Calendar.getInstance();
        try {
            start.set(Integer.parseInt(startDate[0]), Integer.parseInt(startDate[1]) - 1, Integer.parseInt(startDate[2]));
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return null;
        }
        String[] endDate = menu.instructAndReceive("Enter end date: use this format <YYYY-MM-DD>").split("-");
        Calendar end = Calendar.getInstance();
        try {
            end.set(Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]) - 1, Integer.parseInt(endDate[2]));
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return null;
        }
        Pair<Calendar, Calendar> dates = new Pair<>(start, end);
        //checking that the date make sense
        if (start.compareTo(end) > 0) {
            menu.errorPrompt("End date is before start date");
            return null;
        }
        return dates;
    }

    private <T extends SimpleEntity> void modifySale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        ResponseT<Sale<T>> saleR = service.getSale(saleName);
        if (saleR.isErrorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
            return;
        } else
            menu.printEntity(saleR.getData());

        menu.printMenu(menu.getSaleModificationList());
        String userInput = menu.instructAndReceive("Enter choice: ");
        Response modResp;
        switch (userInput) {
            case "1":
                String newName = menu.instructAndReceive("Enter new name: ");
                modResp = service.modifySaleName(saleR.getData().getName(), newName);
                break;
            case "2":
                String tempDisc = menu.instructAndReceive("Enter sale discount: (e.g. for 10% enter 0.1)");
                double newDisc;
                try {
                    newDisc = Double.parseDouble(tempDisc);
                } catch(Exception ex) {
                    menu.errorPrompt("Invalid input");
                    return;
                }
                modResp = service.modifySaleDiscount(saleR.getData().getName(), newDisc);
                break;
            case "3":
                Pair<Calendar, Calendar> dates = getStartEndDates();
                modResp = service.modifySaleDates(saleR.getData().getName(), dates.getFirst(), dates.getSecond());
                break;
            default:
                modResp = new Response(true, "Invalid choice");
                break;
        }
        if (modResp.isErrorOccurred())
            menu.errorPrompt(modResp.getMessage());
        else
            System.out.println("Changes Saved!");
    }

    private <T extends SimpleEntity> void showSupplierDiscount() {
        String temp = menu.instructAndReceive("Enter supplier ID: ");
        int suppId;
        try {
            suppId = Integer.parseInt(temp);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        String[] disDateS = menu.instructAndReceive("Enter the date of the discount: ").split("-");
        Calendar date = Calendar.getInstance();
        try {
            date.set(Integer.parseInt(disDateS[0]), Integer.parseInt(disDateS[1]) - 1, Integer.parseInt(disDateS[2]));
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        ResponseT<List<Discount<T>>> discR = service.getDiscount(suppId, date);
        for (Discount<T> d : discR.getData()) {
            menu.printEntity(d);
        }

    }

    private void addItemSupplierDiscount() {
        //supplier Id
        String temp = menu.instructAndReceive("Enter supplier ID: ");
        int suppId;
        try {
            suppId = Integer.parseInt(temp);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //item id
        String tempID = menu.instructAndReceive("Enter item ID that the discount applies on: ");
        int itemId;
        try {
            itemId = Integer.parseInt(tempID);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //discount
        String tempDisc = menu.instructAndReceive("Enter discount received for the item: (e.g. for 10% enter 0.1)");
        double discountGiven;
        try {
            discountGiven = Double.parseDouble(tempDisc);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //dates
        String[] dateS = menu.instructAndReceive("Enter discount date: use this format <YYYY-MM-DD>").split("-");
        Calendar date = Calendar.getInstance();
        try {
            date.set(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]) - 1, Integer.parseInt(dateS[2]));
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //count
        String tempAmount = menu.instructAndReceive("Enter the amount the discount applied for: ");
        int itemCount;
        try {
            itemCount = Integer.parseInt(tempAmount);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        if (itemCount <= 0) {
            menu.errorPrompt("Item amount has to be at least 1.");
            return;
        }
        Response r1 = service.addItemDiscount(suppId, discountGiven, date, itemCount, itemId);
        if (r1.isErrorOccurred()) {
            menu.errorPrompt(r1.getMessage());
            return;
        }
        System.out.println("Discount added successfully");
    }

    private void addCategorySupplierDiscount() {
        //supplier Id
        String temp = menu.instructAndReceive("Enter supplier ID: ");
        int suppId;
        try {
            suppId = Integer.parseInt(temp);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        String catName = menu.instructAndReceive("Enter the category name that the discount applies on: ");
        //discount
        String tempDisc = menu.instructAndReceive("Enter discount received for the item: (e.g. for 10% enter 0.1)");
        double discountGiven;
        try {
            discountGiven = Double.parseDouble(tempDisc);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //date
        String[] dateS = menu.instructAndReceive("Enter discount date: use this format <YYYY-MM-DD>").split("-");
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]) - 1, Integer.parseInt(dateS[2]));
        String tempAmount = menu.instructAndReceive("Enter the amount the discount applied for: ");
        int itemCount;
        try {
            itemCount = Integer.parseInt(tempAmount);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        if (itemCount <= 0) {
            menu.errorPrompt("Item amount has to be at least 1.");
            return;
        }
        Response r1 = service.addCategoryDiscount(suppId, discountGiven, date, itemCount, catName);
        if (r1.isErrorOccurred()) {
            menu.errorPrompt(r1.getMessage());
            return;
        }
        System.out.println("Sale added successfully");

    }

    private void recordDefect() {
        Calendar date = Calendar.getInstance();
        //item id
        int itemID;
        String temp = menu.instructAndReceive("Enter item ID: ");
        try {
            itemID = Integer.parseInt(temp);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //item amount
        int defectedAmount;
        String tempAmount = menu.instructAndReceive("Enter the defect amount: ");
        try {
            defectedAmount = Integer.parseInt(tempAmount);
        } catch(Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //location2
        String location = menu.instructAndReceive("Enter storage/shelf location: follow this format \"<ST/SH>-A<number>-<L/R>-S<number>\"");

        Response recorded = service.recordDefect(itemID, date, defectedAmount, location);
        if (recorded.isErrorOccurred()) {
            menu.errorPrompt(recorded.getMessage());
            return;
        }
        System.out.println("Defect reported!");


    }

    private void inventoryReport() {
        ResponseT<List<Item>> reportResp = service.inventoryReport();
        if (reportResp.isErrorOccurred()) {
            menu.errorPrompt(reportResp.getMessage());
            return;
        }
        menu.printEntity(reportResp.getData());
    }

    private void categoryReport() {
        String catName = menu.instructAndReceive("Enter category name: ");
        ResponseT<Category> catR = service.getCategory(catName);
        if (catR.isErrorOccurred()) {
            menu.errorPrompt(catR.getMessage());
            return;
        }
        ResponseT<List<Item>> categoryItems = service.categoryReport(catName);
        if (categoryItems.isErrorOccurred()) {
            menu.errorPrompt(categoryItems.getMessage());
        }
        menu.getTextFormatter().CategoryMenuFormat(catR.getData());
        menu.printEntity(categoryItems.getData());
    }

    private void itemShortageReport() {
        ResponseT<List<Item>> shortage = service.itemShortageReport();
        if (shortage.isErrorOccurred()) {
            menu.errorPrompt(shortage.getMessage());
            return;
        }
        menu.printEntity(shortage.getData());
    }

    private void defectsReport() {
        Pair<Calendar, Calendar> interval = getStartEndDates();
        ResponseT<List<DefectEntry>> defects = service.defectsReport(interval.getFirst(), interval.getSecond());
        if (defects.isErrorOccurred()) {
            menu.errorPrompt(defects.getMessage());
            return;
        }
        menu.printDefectMenu();
        try {
            for (DefectEntry entry : defects.getData()) {
                menu.printEntity(entry);
            }
        } catch (Exception e) {
            menu.errorPrompt("Something went wrong...");
        }

    }


    @Override
    public void run() {
        menu.printWelcomePrompt();
        setupSystem();
        while (!terminate) {
            menu.printOperationMenu();
            operationInput();
        }

    }

    private void setupSystem() {
        String toSetup = menu.instructAndReceive("would you like to load the system with data? [y/n]\n 'no' will start up a clean system");
        toSetup.toLowerCase();
        if (toSetup.compareTo("y") != 0) {
            System.out.println("Starting clean system");
            return;
        }
        //Adding categories
        service.addCategory("Dairy products", "");
        service.addCategory("Milks", "Dairy products");
        service.addCategory("Tnuva", "Milks");
        service.addCategory("Tara", "Milks");
        service.addCategory("Yotvata", "Milks");
        service.addCategory("Cheese", "Dairy products");
        service.addCategory("Hard cheeses", "Cheese");
        service.addCategory("Soft cheeses", "Cheese");
        service.addCategory("Snacks", "");
        service.addCategory("Chocolate", "Snacks");
        service.addCategory("Potato Chips", "Snacks");
        service.addCategory("Drinks", "");
        service.addCategory("Soft drinks", "Drinks");
        service.addCategory("CocaCola products", "Soft drinks");
        service.addCategory("Pepsi products", "Soft drinks");
        service.addCategory("Hard drinks", "Drinks");
        service.addCategory("Beers", "Hard drinks");
        service.addCategory("Liqueurs", "Hard drinks");
        service.addCategory("Meat section", "");
        service.addCategory("chicken", "Meat section");
        service.addCategory("Fish", "Meat section");


        //Adding items
        //Tnuva milk 3% regular
        service.addItem(845, "Regular milk 3%", "Tnuva", 3.45, 5.15, 20,
                "SH-A1-L-S5", "ST-A5-S8", 40, 20, 111, new ArrayList<>());
        service.addItemSupplier(845, 111);
        //Tnuva milk regulat 1%
        service.addItem(846, "Regular milk 1%", "Tnuva", 3.45, 5.15, 20,
                "SH-A1-L-S6", "ST-A5-L-S9", 40, 20, 111, new ArrayList<>());
        service.addItemSupplier(846, 111);
        //Tnuva non-Lactose milk
        service.addItem(847, "Non-Lactose Milk", "Tnuva", 3.95, 6.99, 10,
                "SH-A8-R-S9", "ST-A5-L-S10", 40, 20, 111, new ArrayList<>());
        //tara regular
        service.addItem(98754, "Regular milk 3%", "Tara", 3.95, 6.99, 10,
                "SH-A19-R-S54", "ST-A12-R-S15", 40, 20, 112, new ArrayList<>());
        //tara coffee milk
        service.addItem(98755, "Coffee Milk", "Tara", 4.00, 6.99, 10,
                "SH-A19-R-55", "ST-A20-R-S1", 15, 2, 112, new ArrayList<>());
        //tara vanila milk
        service.addItem(98759, "Vanilla  Milk", "Tara", 3.95, 6.99, 10,
                "SH-A3-L-S16", "ST-A7-R-S17", 40, 20, 112, new ArrayList<>());
        //Yotvata choclate milk
        service.addItem(287, "Choclate milk", "Yotvata", 3.95, 6.99, 10,
                "SH-A3-L-S16", "ST-A7-R-S17", 40, 20, 113, new ArrayList<>());
        //Yotvata Caramel Milk
        service.addItem(265, "Caramel milk", "Tnuva", 3.95, 6.99, 10,
                "SH-A3-L-S16", "ST-A7-R-S17", 10, 0, 113, new ArrayList<>());
        //Gouda Cheese
        service.addItem(78525, "Gouda Cheese 200g", "Hard Cheese", 3.95, 12.99, 350,
                "SH-A4-R-S1", "ST-A6-R-S2", 400, 45, 124, new ArrayList<>());
        //Cream cheese
        service.addItem(88435, "cream cheese", "Soft cheeses", 3.95, 6.99, 10,
                "SH-A28-R-S3", "ST-A14-L-S8", 40, 20, 124, new ArrayList<>());
        //frozen pizza
        service.addItem(45667, "Frozen Pizza Big", "Dairy products", 15.95, 25.99, 25,
                "SH-A84-R-S89", "ST-A53-L-S110", 20, 20, 145, new ArrayList<>());
        //Para Chocolate
        service.addItem(999, "Para Chocolate", "Chocolate", 3.95, 6.99, 10,
                "SH-A8-R-S9", "ST-A5-L-S10", 40, 20, 111, new ArrayList<>());
        //Lets chips
        service.addItem(867, "Let's Potato Chips", "Potato Chips", 3.95, 6.99, 10,
                "SH-A34-R-S9", "ST-A32-R-S10", 40, 20, 111, new ArrayList<>());
        //Tapu chips
        service.addItem(868, "Tapu chips", "Potato Chips", 3.95, 6.99, 10,
                "SH-A8-R-S9", "ST-A5-L-S10", 40, 20, 111, new ArrayList<>());

        //Snyders
        service.addItem(81474, "Snyders Chadar", "Snacks", 10, 16, 10,
                "SH-A1-R-S1", "ST-A1-L-S1", 40, 20, 357, new ArrayList<>());
        //Cola Zero
        service.addItem(11111, "Cola Zero", "CocaCola products", 2, 7, 100,
                "SH-A2-R-S4", "ST-A7-L-S45", 120, 20, 357, new ArrayList<>());
        service.addItemSupplier(11111, 113);
        //Fanta
        service.addItem(11115, "Fanta", "CocaCola products", 10, 16, 70,
                "SH-A2-R-S5", "ST-A7-L-S46", 40, 20, 357, new ArrayList<>());
        //Pepsi max
        service.addItem(22285, "Pepsi Max", "Pepsi products", 10, 16, 12,
                "SH-A3-R-S5", "ST-A7-L-S46", 15, 3, 817, new ArrayList<>());
        //Goldstar 500ml
        service.addItem(24285, "Goldstar 500ml", "Beers", 5.6, 12, 54,
                "SH-A6-R-S14", "ST-A6-L-S15", 17, 300, 917, new ArrayList<>());
        //Guinness beer
        service.addItem(25285, "Guinness beer 400ml", "Beers", 7.6, 14.5, 500,
                "SH-A6-R-S15", "ST-A6-L-S15", 400, 300, 1556, new ArrayList<>());

        //Jack daniels 750ml
        service.addItem(25885, "Jack daniels 750ml", "Liqueurs", 7.6, 14.5, 500,
                "SH-A6-R-S15", "ST-A6-L-S15", 400, 300, 1558, new ArrayList<>());

        //Chicken breasts 1kg
        service.addItem(7789, "Chicken breasts 1kg", "Chicken", 21.3, 45.5, 5,
                "SH-A9-L-S18", "ST-A39-L-S1", 10, 5, 9032, new ArrayList<>());
        //Chicken Wings 1kg
        service.addItem(7790, "Chicken Wings 1kg", "Chicken", 26.3, 54.5, 10,
                "SH-A9-L-S19", "ST-A39-L-S2", 15, 1, 9032, new ArrayList<>());
        //whole Salmon
        service.addItem(7794, "Whole Salmon", "Fish", 75.4, 248.3, 1,
                "SH-A9-L-S20", "ST-A39-L-S3", 4, 3, 8753, new ArrayList<>());

        //Adding sales
        //Guinness sale
        Calendar start = Calendar.getInstance();
        start.set(2021, Month.APRIL.getValue() - 1, 11);
        Calendar end = Calendar.getInstance();
        end.set(2021, Month.APRIL.getValue() - 1, 15);
        service.addItemSale("Guinness Sale", 25285, 0.2, start, end);

        //vannila milk sale
        Calendar start2 = Calendar.getInstance();
        start2.set(2021, Month.APRIL.getValue() - 1, 22);
        Calendar end2 = Calendar.getInstance();
        end2.set(2021, Month.APRIL.getValue() - 1, 27);
        service.addCategorySale("Yotvata Sale", "Yotvata", 0.25, start2, end2);

        //Adding suppliers discount
        Calendar discDate = Calendar.getInstance();
        discDate.set(2021, Month.MARCH.getValue() - 1, 3);
        System.out.println(menu.getTextFormatter().dateFormat(discDate));
        service.addItemDiscount(111, 0.12, discDate, 3, 845);

        Calendar discDate2 = Calendar.getInstance();
        discDate2.set(2021, Month.MARCH.getValue() - 1, 4);
        System.out.println(menu.getTextFormatter().dateFormat(discDate2));
        service.addItemDiscount(111, 0.12, discDate2, 10, 846);

        Calendar discDate3 = Calendar.getInstance();
        discDate3.set(2021, Month.MARCH.getValue() - 1, 5);
        System.out.println(menu.getTextFormatter().dateFormat(discDate3));
        service.addItemDiscount(113, 0.10, discDate3, 20, 11111);

        Calendar defectTime = Calendar.getInstance();
        defectTime.set(2021, Month.MARCH.getValue() - 1, 4);
        //Adding defect entries
        service.recordDefect(88435, defectTime, 4, "SH-A28-R-S3");

        Calendar defectTime2 = Calendar.getInstance();
        defectTime2.set(2021, Month.MARCH.getValue() - 1, 4);
        //Adding defect entries
        service.recordDefect(22285, defectTime2, 4, "ST-A7-L-S46");
        Calendar defectTime3 = Calendar.getInstance();
        defectTime3.set(2021, Month.MARCH.getValue() - 1, 4);
        //Adding defect entries
        service.recordDefect(98755, defectTime3, 2, "SH-A19-R-55");

    }

    private void operationInput() {
        Scanner scan = new Scanner(System.in);
        String choice = scan.next();
        switch (choice) {
            case "1":
                showItem();
                break;
            case "2":
                addItem();
                break;
            case "3":
                editItem();
                break;
            case "4":
                removeItem();
                break;
            case "5":
                showCategory();
                break;
            case "6":
                addCategory();
                break;
            case "7":
                editCategory();
                break;
            case "8":
                removeCategory();
                break;
            case "9":
                showSale();
                break;
            case "10":
                addItemSale();
                break;
            case "11":
                addCategorySale();
                break;
            case "12":
                modifySale();
                break;
            case "13":
                showSupplierDiscount();
                break;
            case "14":
                addItemSupplierDiscount();
                break;
            case "15":
                addCategorySupplierDiscount();
                break;
            case "16":
                recordDefect();
                break;
            case "17":
                inventoryReport();
                break;
            case "18":
                categoryReport();
                break;
            case "19":
                itemShortageReport();
                break;
            case "20":
                defectsReport();
                break;
            case "q":
                terminate = true;
                break;
            default:
                menu.errorPrompt(choice);
                break;
        }
    }


}
