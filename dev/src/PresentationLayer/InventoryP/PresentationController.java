package PresentationLayer.InventoryP;

import InfrastructurePackage.Pair;
import SerciveLayer.InventoryService;
import SerciveLayer.InventoryServiceImpl;
import SerciveLayer.InResponse;
import SerciveLayer.InResponseT;
import SerciveLayer.SimpleObjects.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

public class PresentationController implements Runnable {
    private final InventoryService service;
    //servicecontroller
    private final Menu menu;
    private boolean terminate;


    public PresentationController() {
        this.service = new InventoryServiceImpl();
        this.menu = new Menu();
        terminate = false;
    }

    //Item related method
    private void addItem() {
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        String name = menu.instructAndReceive("Enter name: ");
        double costP = menu.instructAndReceive("Enter cost price: ", Double.class);
        double sellP = menu.instructAndReceive("Enter sell price: ", Double.class);
        int minA = menu.instructAndReceive("Enter minimum amount: ", Integer.class);
        int storageQ = menu.instructAndReceive("Enter storage quantity: ", Integer.class);
        int storeQ = menu.instructAndReceive("Enter shelf quantity: ", Integer.class);
        String storageL = menu.instructAndReceive("Enter storage location: follow this format \"ST-A<number>-<L/R>-S<number>\"");
        String storeL = menu.instructAndReceive("Enter shelf location: follow this format \"SH-A<number>-<L/R>-S<number>\"");
        int intManfac = menu.instructAndReceive("Enter manufacturer ID: ", Integer.class);
        String category = menu.instructAndReceive("Enter category name: ");
        InResponse r = service.addItem(itemID, name, category, costP, sellP, minA, storeL, storageL, storageQ, storeQ, intManfac, new ArrayList<>());
        if (r.isErrorOccurred()) {
            menu.errorPrompt(r.getMessage());
        } else {
            System.out.println("Item added successfully");
        }
    }

    private void showItem() {
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        InResponseT<Item> r = service.getItem(itemID);
        if (!r.isErrorOccurred()) {
            menu.itemHeader();
            menu.printEntity(r.getData());
        } else {
            menu.errorPrompt(r.getMessage());
        }
    }

    private void editItem() {
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        InResponseT<Item> r = service.getItem(itemID);
        if (r.isErrorOccurred()) {
            menu.errorPrompt("Could not get item. Make sure you entered the ID correctly.");
            return;
        }
        menu.itemHeader();
        menu.printEntity(r.getData());
        menu.printItemModMenu();
        InResponse modResp = editItemChoiceInput(itemID);
        if (modResp.isErrorOccurred())
            System.out.println(modResp.getMessage());
        else
            System.out.println("Changes saved!");

    }

    private InResponse editItemChoiceInput(int itemId) {
        Scanner scan = new Scanner(System.in);
        String choice = scan.next();
        InResponse r;
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
                double newCPrice = menu.instructAndReceive("Enter cost price: ", Double.class);
                r = service.modifyItemCostPrice(itemId, newCPrice);
                break;
            case "4":
                double newSPrice = menu.instructAndReceive("Enter sell price: ", Double.class);
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
                int newQuantityST = menu.instructAndReceive("Enter storage quantity: ", Integer.class);
                r = service.modifyItemStorageQuantity(itemId, newQuantityST);
                break;
            case "8":
                int newQuantitySH = menu.instructAndReceive("Enter storage quantity: ", Integer.class);
                r = service.modifyItemShelfQuantity(itemId, newQuantitySH);
                break;
            case "9":
                int newSupplier = menu.instructAndReceive("\"Add new supplier for the item: (enter supplier ID)\"): ", Integer.class);
                r = service.addItemSupplier(itemId, newSupplier);
                break;
            case "10":
                int oldSupplier = menu.instructAndReceive("Remove a supplier for the item: (enter supplier ID)", Integer.class);
                r = service.removeItemSupplier(itemId, oldSupplier);
                break;
            default:
                r = new InResponse(true, "Invalid choice");
                break;
        }
        return r;
    }

    private void removeItem() {
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        InResponse r = service.removeItem(itemID);
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
        InResponse addR = service.addCategory(catName, parentCategoryName);
        if (addR.isErrorOccurred())
            System.out.println(addR.getMessage());
        else
            System.out.println("Category added successfully");

    }

    private void showCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        InResponseT<Category> rCategory = service.getCategory(catName);
        if (rCategory.isErrorOccurred())
            System.out.println(rCategory.getMessage());
        else
            menu.printEntity(rCategory.getData());
    }

    private void editCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        InResponseT<Category> catR = service.getCategory(catName);
        if (catR.isErrorOccurred()) {
            menu.errorPrompt(catR.getMessage());
            return;
        } else
            menu.printEntity(catR.getData());
        menu.printCategoryModList();
        String userInput = menu.instructAndReceive("Enter choice: ");
        InResponse modResp;
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
                modResp = new InResponse(true, "Invalid choice");
                break;
        }

        if (modResp.isErrorOccurred())
            menu.errorPrompt(modResp.getMessage());
        else
            System.out.println("Changes Saved!");

    }

    private void removeCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        InResponse catR = service.removeCategory(catName);
        if (catR.isErrorOccurred())
            menu.errorPrompt(catR.getMessage());
        else
            System.out.println("Category removed!");
    }

    private <T extends SimpleEntity> void showSale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        InResponseT<Sale<T>> saleR = service.getSale(saleName);
        if (saleR.isErrorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
        } else {
            menu.printEntity(saleR.getData());
        }
    }

    private void addItemSale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        double saleDiscount = menu.instructAndReceive("Enter sale discount: (e.g. for 10% enter 0.1)", Double.class);
        Pair<Calendar, Calendar> dates = getStartEndDates();
        if (dates == null) return;
        //getting the item to be applied on
        int applyOnItem = menu.instructAndReceive("Enter item ID for the sale: ", Integer.class);
        //checking if exists
        InResponseT<Item> rExist = service.getItem(applyOnItem);
        if (rExist.isErrorOccurred()) {
            menu.errorPrompt(rExist.getMessage());
            return;
        }
        InResponse r1 = service.addItemSale(saleName, applyOnItem, saleDiscount, dates.getFirst(), dates.getSecond());
        if (r1.isErrorOccurred()) {
            menu.errorPrompt(r1.getMessage());
            return;
        }
        System.out.println("Sale Added successfully");
    }

    private void addCategorySale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        double saleDiscount = menu.instructAndReceive("Enter sale discount: (e.g. for 10% enter 0.1)", Double.class);
        //getting the dates
        Pair<Calendar, Calendar> dates = getStartEndDates();
        if (dates == null) return;
        //getting the item to be applied on
        String applyOnItem = menu.instructAndReceive("Enter category name for the sale: ");
        //checking if exists
        InResponseT<Category> rExist = service.getCategory(applyOnItem);
        if (rExist.isErrorOccurred()) {
            menu.errorPrompt(rExist.getMessage());
            return;
        }
        InResponse r1 = service.addCategorySale(saleName, applyOnItem, saleDiscount, dates.getFirst(), dates.getSecond());
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
        } catch (Exception ex) {
            menu.errorPrompt("Invalid input");
            return null;
        }
        String[] endDate = menu.instructAndReceive("Enter end date: use this format <YYYY-MM-DD>").split("-");
        Calendar end = Calendar.getInstance();
        try {
            end.set(Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]) - 1, Integer.parseInt(endDate[2]));
        } catch (Exception ex) {
            menu.errorPrompt("Invalid input");
            return null;
        }
        Pair<Calendar, Calendar> dates = new Pair<>(start, end);
        //checking that the date makes sense
        if (start.compareTo(end) > 0) {
            menu.errorPrompt("End date is before start date");
            return null;
        }
        return dates;
    }

    private <T extends SimpleEntity> void modifySale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        InResponseT<Sale<T>> saleR = service.getSale(saleName);
        if (saleR.isErrorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
            return;
        } else
            menu.printEntity(saleR.getData());

        menu.printSaleModList();
        String userInput = menu.instructAndReceive("Enter choice: ");
        InResponse modResp;
        switch (userInput) {
            case "1":
                String newName = menu.instructAndReceive("Enter new name: ");
                modResp = service.modifySaleName(saleR.getData().getName(), newName);
                break;
            case "2":
                double newDisc = menu.instructAndReceive("Enter sale discount: (e.g. for 10% enter 0.1)", Double.class);
                modResp = service.modifySaleDiscount(saleR.getData().getName(), newDisc);
                break;
            case "3":
                Pair<Calendar, Calendar> dates = getStartEndDates();
                if (dates == null)
                    return;
                modResp = service.modifySaleDates(saleR.getData().getName(), dates.getFirst(), dates.getSecond());
                break;
            default:
                modResp = new InResponse(true, "Invalid choice");
                break;
        }
        if (modResp.isErrorOccurred())
            menu.errorPrompt(modResp.getMessage());
        else
            System.out.println("Changes Saved!");
    }

    private <T extends SimpleEntity> void showSupplierDiscount() {
        int suppId = menu.instructAndReceive("Enter supplier ID: ", Integer.class);
        String[] disDateS = menu.instructAndReceive("Enter the date of the discount: ").split("-");
        Calendar date = Calendar.getInstance();
        try {
            date.set(Integer.parseInt(disDateS[0]), Integer.parseInt(disDateS[1]) - 1, Integer.parseInt(disDateS[2]));
        } catch (Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        InResponseT<List<Discount<T>>> discR = service.getDiscount(suppId, date);
        for (Discount<T> d : discR.getData()) {
            menu.printEntity(d);
        }

    }

    private void addItemSupplierDiscount() {
        //supplier Id
        int suppId = menu.instructAndReceive("Enter supplier ID: ", Integer.class);
        //item id
        int itemId = menu.instructAndReceive("Enter item ID that the discount applies on: ", Integer.class);
        //discount
        double discountGiven = menu.instructAndReceive("Enter discount received for the item: (e.g. for 10% enter 0.1)", Double.class);
        //dates
        String[] dateS = menu.instructAndReceive("Enter discount date: use this format <YYYY-MM-DD>").split("-");
        Calendar date = Calendar.getInstance();
        try {
            date.set(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]) - 1, Integer.parseInt(dateS[2]));
        } catch (Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        //count
        int itemCount = menu.instructAndReceive("Enter the amount the discount applied for: ", Integer.class);
        if (itemCount <= 0) {
            menu.errorPrompt("Item amount has to be at least 1.");
            return;
        }
        InResponse r1 = service.addItemDiscount(suppId, discountGiven, date, itemCount, itemId);
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
        } catch (Exception ex) {
            menu.errorPrompt("Invalid input");
            return;
        }
        String catName = menu.instructAndReceive("Enter the category name that the discount applies on: ");
        //discount
        double discountGiven = menu.instructAndReceive("Enter discount received for the item: (e.g. for 10% enter 0.1)", Double.class);
        //date
        String[] dateS = menu.instructAndReceive("Enter discount date: use this format <YYYY-MM-DD>").split("-");
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]) - 1, Integer.parseInt(dateS[2]));
        int itemCount = menu.instructAndReceive("Enter the amount the discount applied for: ", Integer.class);
        if (itemCount <= 0) {
            menu.errorPrompt("Item amount has to be at least 1.");
            return;
        }
        InResponse r1 = service.addCategoryDiscount(suppId, discountGiven, date, itemCount, catName);
        if (r1.isErrorOccurred()) {
            menu.errorPrompt(r1.getMessage());
            return;
        }
        System.out.println("Sale added successfully");

    }

    private void recordDefect() {
        Calendar date = Calendar.getInstance();
        //item id
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        //item amount
        int defectedAmount = menu.instructAndReceive("Enter the defect amount: ", Integer.class);
        //location2
        String location = menu.instructAndReceive("Enter storage/shelf location: follow this format \"<ST/SH>-A<number>-<L/R>-S<number>\"");
        InResponse recorded = service.recordDefect(itemID, date, defectedAmount, location);
        if (recorded.isErrorOccurred()) {
            menu.errorPrompt(recorded.getMessage());
            return;
        }
        System.out.println("Defect reported!");
    }

    private void inventoryReport() {
        InResponseT<List<Item>> reportResp = service.inventoryReport();
        if (reportResp.isErrorOccurred()) {
            menu.errorPrompt(reportResp.getMessage());
            return;
        }
        menu.printEntity(reportResp.getData());
    }

    private void categoryReport() {
        String catName = menu.instructAndReceive("Enter category name: ");
        InResponseT<Category> catR = service.getCategory(catName);
        if (catR.isErrorOccurred()) {
            menu.errorPrompt(catR.getMessage());
            return;
        }
        InResponseT<List<Item>> categoryItems = service.categoryReport(catName);
        if (categoryItems.isErrorOccurred()) {
            menu.errorPrompt(categoryItems.getMessage());
        }
        menu.printEntity(catR.getData());
    }

    private void itemShortageReport() {
        InResponseT<List<Item>> shortage = service.itemShortageReport();
        if (shortage.isErrorOccurred()) {
            menu.errorPrompt(shortage.getMessage());
            return;
        }
        menu.printEntity(shortage.getData());
    }

    private void defectsReport() {
        Pair<Calendar, Calendar> interval = getStartEndDates();
        if (interval == null)
            return;
        InResponseT<List<DefectEntry>> defects = service.defectsReport(interval.getFirst(), interval.getSecond());
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
            inventoryMainMenu();
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
        service.addCategory("Potato chips", "Snacks");
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
        service.addItem(78525, "Gouda Cheese 200g", "Hard Cheeses", 3.95, 12.99, 350,
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
        service.addItemDiscount(111, 0.12, discDate, 3, 845);

        Calendar discDate2 = Calendar.getInstance();
        discDate2.set(2021, Month.MARCH.getValue() - 1, 4);
        service.addItemDiscount(111, 0.12, discDate2, 10, 846);

        Calendar discDate3 = Calendar.getInstance();
        discDate3.set(2021, Month.MARCH.getValue() - 1, 5);
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

    private void inventoryMainMenu() {
        menu.printInventoryMainMenu();
        String choice = menu.instructAndReceive("Select a menu");

        switch (choice) {
            case "1":
                itemMenuOperations();
                break;
            case "2":
                categoryMenuOperations();
                break;
            case "3":
                saleMenuOperations();
                break;
            case "4":
                discountMenuOperations();
                break;
            case "5":
                recordDefect();
                break;
            case "6":
                reportMenuOperations();
            default:
                menu.errorPrompt("invalid choice - " + choice);
                break;
        }


    }

    private void itemMenuOperations() {
        menu.printItemMenu();
        String choice = menu.instructAndReceive("Select item option");
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
            default:
                menu.errorPrompt("invalid choice - " + choice);
                break;
        }
    }

    private void categoryMenuOperations() {
        menu.printCategoryMenu();
        String choice = menu.instructAndReceive("Select category option");
        switch (choice) {
            case "1":
                showCategory();
                break;
            case "2":
                addCategory();
                break;
            case "3":
                editCategory();
                break;
            case "4":
                removeCategory();
                break;
            default:
                menu.errorPrompt("invalid choice - " + choice);
                break;
        }
    }

    private void saleMenuOperations() {
        menu.printSaleMenu();
        String choice = menu.instructAndReceive("Select Sale option");
        switch (choice) {
            case "1":
                showSale();
                break;
            case "2":
                addItemSale();
                break;
            case "3":
                addCategorySale();
                break;
            case "4":
                modifySale();
                break;
            default:
                menu.errorPrompt("invalid choice - " + choice);
                break;
        }
    }

    private void discountMenuOperations() {
        menu.printDiscountMenu();
        String choice = menu.instructAndReceive("Select supplier discount option");
        switch (choice) {
            case "1":
                showSupplierDiscount();
                break;
            case "2":
                addItemSupplierDiscount();
                break;
            case "3":
                addCategorySupplierDiscount();
                break;
            default:
                menu.errorPrompt("invalid choice - " + choice);
                break;
        }
    }

    private void reportMenuOperations() {
        menu.printReportMenu();
        String choice = menu.instructAndReceive("Select Report option");
        switch (choice) {
            case "1":
                inventoryReport();
                break;
            case "2":
                categoryReport();
                break;
            case "3":
                itemShortageReport();
                break;
            case "4":
                defectsReport();
                break;
            default:
                menu.errorPrompt("invalid choice - " + choice);
                break;
        }

    }


}
