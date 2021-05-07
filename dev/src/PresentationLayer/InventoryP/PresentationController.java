package PresentationLayer.InventoryP;

import InfrastructurePackage.Pair;
import SerciveLayer.IService;
import SerciveLayer.InventoryService;
import SerciveLayer.InventoryServiceImpl;
import SerciveLayer.Response.*;
import SerciveLayer.Response.Response;
import SerciveLayer.Service;
import SerciveLayer.SimpleObjects.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class PresentationController implements Runnable {
    private final InventoryService service;
    private final IService Service;
    private PresentationLayer.SuppliersP.PresentationController pc;
    private Scanner scan;
    private final Menu menu;
    private boolean terminate;
    private final int MaxCompanyNumber = 100;
    private final int MaxNamesLength = 20;
    private final int PhoneLength = 10;
    private final int idLength = 9;


    public PresentationController() {
        this.service = new InventoryServiceImpl();
        this.menu = new Menu();
        terminate = false;
        Service = new Service();
        scan = new Scanner(System.in);
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
        Response r = service.addItem(itemID, name, category, costP, sellP, minA, storeL, storageL, storageQ, storeQ, intManfac, new ArrayList<>());
        if (r.errorOccurred()) {
            menu.errorPrompt(r.getMessage());
        } else {
            System.out.println("Item added successfully");
        }
    }

    private void showItem() {
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        ResponseT<Item> r = service.getItem(itemID);
        if (!r.errorOccurred()) {
            menu.itemHeader();
            menu.printEntity(r.getValue());
        } else {
            menu.errorPrompt(r.getMessage());
        }
    }

    private void editItem() {
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        ResponseT<Item> r = service.getItem(itemID);
        if (r.errorOccurred()) {
            menu.errorPrompt("Could not get item. Make sure you entered the ID correctly.");
            return;
        }
        menu.itemHeader();
        menu.printEntity(r.getValue());
        menu.printItemModMenu();
        Response modResp = editItemChoiceInput(itemID);
        if (modResp.errorOccurred())
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
                String newName = menu.instructAndReceive("Enter new item name: ");
                r = service.modifyItemName(itemId, newName);
                break;
            case "2":
                String newCategory = menu.instructAndReceive("Enter new item category: ");
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
                String newLocationST = menu.instructAndReceive("Enter new item storage location: ");
                r = service.changeItemStorageLocation(itemId, newLocationST);
                break;
            case "6":
                String newLocationSH = menu.instructAndReceive("Enter new item shelf location: ");
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
            default:
                r = new Response(true, "Invalid choice");
                break;
        }
        return r;
    }

    private void removeItem() {
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        Response r = service.removeItem(itemID);
        if (r.errorOccurred()) {
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
        if (addR.errorOccurred())
            System.out.println(addR.getMessage());
        else
            System.out.println("Category added successfully");

    }

    private void showCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        ResponseT<Category> rCategory = service.getCategory(catName);
        if (rCategory.errorOccurred())
            System.out.println(rCategory.getMessage());
        else
            menu.printEntity(rCategory.getValue());
    }

    private void editCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        ResponseT<Category> catR = service.getCategory(catName);
        if (catR.errorOccurred()) {
            menu.errorPrompt(catR.getMessage());
            return;
        } else
            menu.printEntity(catR.getValue());
        menu.printCategoryModList();
        String userInput = menu.instructAndReceive("Enter choice: ");
        Response modResp;
        switch (userInput) {
            case "1":
                String newName = menu.instructAndReceive("Enter new name: ");
                modResp = service.modifyCategoryName(catR.getValue().getName(), newName);
                break;
            case "2":
                String newParent = menu.instructAndReceive("Enter new parent category name: (keep in mind not to use a subcategory!)");
                modResp = service.changeParentCategory(catR.getValue().getName(), newParent);
                break;
            default:
                modResp = new Response(true, "Invalid choice");
                break;
        }

        if (modResp.errorOccurred())
            menu.errorPrompt(modResp.getMessage());
        else
            System.out.println("Changes Saved!");

    }

    private void removeCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        Response catR = service.removeCategory(catName);
        if (catR.errorOccurred())
            menu.errorPrompt(catR.getMessage());
        else
            System.out.println("Category removed!");
    }

    private <T extends SimpleEntity> void showSale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        ResponseT<Sale<T>> saleR = service.getSale(saleName);
        if (saleR.errorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
        } else {
            menu.printEntity(saleR.getValue());
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
        ResponseT<Item> rExist = service.getItem(applyOnItem);
        if (rExist.errorOccurred()) {
            menu.errorPrompt(rExist.getMessage());
            return;
        }
        Response r1 = service.addItemSale(saleName, applyOnItem, saleDiscount, dates.getFirst(), dates.getSecond());
        if (r1.errorOccurred()) {
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
        ResponseT<Category> rExist = service.getCategory(applyOnItem);
        if (rExist.errorOccurred()) {
            menu.errorPrompt(rExist.getMessage());
            return;
        }
        Response r1 = service.addCategorySale(saleName, applyOnItem, saleDiscount, dates.getFirst(), dates.getSecond());
        if (r1.errorOccurred()) {
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
        ResponseT<Sale<T>> saleR = service.getSale(saleName);
        if (saleR.errorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
            return;
        } else
            menu.printEntity(saleR.getValue());

        menu.printSaleModList();
        String userInput = menu.instructAndReceive("Enter choice: ");
        Response modResp;
        switch (userInput) {
            case "1":
                String newName = menu.instructAndReceive("Enter new name: ");
                modResp = service.modifySaleName(saleR.getValue().getName(), newName);
                break;
            case "2":
                double newDisc = menu.instructAndReceive("Enter sale discount: (e.g. for 10% enter 0.1)", Double.class);
                modResp = service.modifySaleDiscount(saleR.getValue().getName(), newDisc);
                break;
            case "3":
                Pair<Calendar, Calendar> dates = getStartEndDates();
                if (dates == null)
                    return;
                modResp = service.modifySaleDates(saleR.getValue().getName(), dates.getFirst(), dates.getSecond());
                break;
            default:
                modResp = new Response(true, "Invalid choice");
                break;
        }
        if (modResp.errorOccurred())
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
        ResponseT<List<Discount<T>>> discR = service.getDiscount(suppId, date);
        for (Discount<T> d : discR.getValue()) {
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
        Response r1 = service.addItemDiscount(suppId, discountGiven, date, itemCount, itemId);
        if (r1.errorOccurred()) {
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
        Response r1 = service.addCategoryDiscount(suppId, discountGiven, date, itemCount, catName);
        if (r1.errorOccurred()) {
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
        Response recorded = service.recordDefect(itemID, date, defectedAmount, location);
        if (recorded.errorOccurred()) {
            menu.errorPrompt(recorded.getMessage());
            return;
        }
        System.out.println("Defect reported!");
    }

    private void inventoryReport() {
        ResponseT<List<Item>> reportResp = service.inventoryReport();
        if (reportResp.errorOccurred()) {
            menu.errorPrompt(reportResp.getMessage());
            return;
        }
        menu.printEntity(reportResp.getValue());
    }

    private void categoryReport() {
        String catName = menu.instructAndReceive("Enter category name: ");
        ResponseT<Category> catR = service.getCategory(catName);
        if (catR.errorOccurred()) {
            menu.errorPrompt(catR.getMessage());
            return;
        }
        ResponseT<List<Item>> categoryItems = service.categoryReport(catName);
        if (categoryItems.errorOccurred()) {
            menu.errorPrompt(categoryItems.getMessage());
        }
        menu.printEntity(catR.getValue());
    }

    private void itemShortageReport() {
        ResponseT<List<Item>> shortage = service.itemShortageReport();
        if (shortage.errorOccurred()) {
            menu.errorPrompt(shortage.getMessage());
            return;
        }
        menu.printEntity(shortage.getValue());
    }

    private void defectsReport() {
        Pair<Calendar, Calendar> interval = getStartEndDates();
        if (interval == null)
            return;
        ResponseT<List<DefectEntry>> defects = service.defectsReport(interval.getFirst(), interval.getSecond());
        if (defects.errorOccurred()) {
            menu.errorPrompt(defects.getMessage());
            return;
        }
        menu.printDefectMenu();
        try {
            for (DefectEntry entry : defects.getValue()) {
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
        ResponseT<Map<Integer, Integer>> map = service.getItemsInShortAndQuantities();
        for (int i : map.getValue().keySet()) {
            System.out.println("id " + i + "\tamount " + map.value.get(i));
        }
        while (!terminate) {
            mainMenu();
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

    private void mainMenu() {
        menu.printMainMenu();
        String choice = menu.instructAndReceive("select a menu to open: ");
        switch (choice) {
            case "1":
                inventoryMainMenu();
                break;
            case "2":
                suppliersMainMenu();
                break;
            case "3":
                orderMainMenu();
                break;
            default:
                menu.errorPrompt("invalid choice - " + choice);
                break;
        }

    }

    private void orderMainMenu() {
        System.out.println("order menu:");
        System.out.println("1. create shortage order");
        System.out.println("2. create scheduled order");
        System.out.println("3. approve order");
        System.out.println("4. get order");
        System.out.println("5. add product to order");
        System.out.println("6. remove product from order");
        String chooseInsideMenu = menu.instructAndReceive("enter choice: ");
        switch (chooseInsideMenu) {
            case "1" -> createNewOrder();
            case "2" -> setPermanentOrder();
            case "3" -> approveOrder();
            case "4" -> getOrder();
            case "5" -> addProductToOrder();
            case "6" -> removeProductFromOrder();

        }
    }

    //implemented
    private void createNewOrder() { //case 6
        System.out.print("date (dd/mm/yyyy): ");
        LocalDate lclDate = getDateFromUser();
        String order = Service.createShortageOrder(lclDate).value.stream().
                map(o -> o.getId() + "").reduce("added orders", (acc, curr) -> acc + ", " + curr);
        System.out.println(order.substring(0, order.length() - 1));
    }

    private void setPermanentOrder() { //case 7
        int day = menu.instructAndReceive("day (1-7) in a week to get the order", Integer.class);
        while(true) {
            int itemID = menu.instructAndReceive("enter item ID to add to a scheduled order: ", Integer.class);
            int amount = menu.instructAndReceive("enter amount to order: ", Integer.class);
            String order = Service.createScheduledOrder(day, itemID, amount).value.toString();
            System.out.println(order);
            String choice = menu.instructAndReceive("1. add more items.\nenter any other key for completing the order ");
            if (!choice.equals("1")){
                break;
            }
        }

    }

    //a helper function that approve that the order arrived in the system
    private void approveOrder() {
        int orderID = menu.instructAndReceive("please enter order ID: ", Integer.class);
        System.out.println(pc.approveOrder(orderID));
    }

    //a helper function to get an order from the system
    private void getOrder() {
        int orderID = menu.instructAndReceive("please enter order ID: ", Integer.class);
        System.out.println(pc.getOrder(orderID));
    }

    //todo: check what kind of id is checked (company/System)
    //a helper function to add a product to an order
    private void addProductToOrder() {
        int orderID = menu.instructAndReceive("please enter order ID: ", Integer.class);
        int productID = menu.instructAndReceive("please enter item ID: ", Integer.class);
        int amount = menu.instructAndReceive("enter amount to order: ", Integer.class);
        System.out.println(pc.addProductToOrder(orderID, productID, amount));
    }

    private void removeProductFromOrder() {
        System.out.println("please enter the following details: ");
        int orderId = menu.instructAndReceive("order id: ", Integer.class);
        int productId = menu.instructAndReceive("product id: ", Integer.class);
        System.out.println(pc.removeProductFromOrder(orderId, productId));
    }

    private void suppliersMainMenu() {
        System.out.println("supplier menu:");
        System.out.println("1. add supplier");
        System.out.println("2. get supplier");
        System.out.println("3. update supplier details");
        System.out.println("4. add quantity List");
        System.out.println("5. edit quantity List");
        System.out.println("6. edit agreement");
        System.out.println("7. get quantityList");
        System.out.println("8. get agreement");
        System.out.println("9. remove supplier");
        String chooseInsideMenu = menu.instructAndReceive("enter choice: ");
        switch (chooseInsideMenu) {
            case "1" -> addSupplierFunc();
            case "2" -> getSupplier();
            case "3" -> updateSupplierDetailFunc();
            case "4" -> addQuantityList();
            case "5" -> editQuantityList();
            case "6" -> editAgrreement();
            case "7" -> getQuantityList();
            case "8" -> getAgreement();
            case "9" -> removeSupplier();
        }
    }

    //a helper function that add supplier to the system
    private void addSupplierFunc() {
        System.out.print("please enter following details: ");
        System.out.print("\nfirst name: ");
        String firstName = readName();
        System.out.print("last name: ");
        String lName = readName();
        System.out.print("email: ");
        String email = readEmail();
        System.out.print("ID: ");
        String ID = readID();
        System.out.print("phone: ");
        String phone = readPhone();
        int companyNumber = readCompanyNumber();
        boolean perm;
        while (true) {
            String permanent = menu.instructAndReceive("do you a permanent order days? y/n: ");
            if (permanent.equals("y")) {
                perm = true;
                break;
            } else if (permanent.equals("n")) {
                perm = false;
                break;
            } else System.out.println("wrong input please choose again\n");
        }
        boolean self;
        while (true) {
            String selfD = menu.instructAndReceive("do you deliver the orders by yourself? y/n: ");
            if (selfD.equals("y")) {
                self = true;
                break;
            } else if (selfD.equals("n")) {
                self = false;
                break;
            } else System.out.println("wrong input please choose again\n");
        }
        String pay = "";
        while (true) {
            System.out.print("how would you like to pay?\n1. Cash\n2. Bank transfer\n3. check\nchoose number: ");
            int payment = menu.instructAndReceive("how would you like to pay?\n1. Cash\n2. Bank transfer\n3. check\nchoose number: ", Integer.class);
            if (payment == 1) {
                pay = "cash";
                break;
            } else if (payment == 2) {
                pay = "bankTrasfer";
                break;
            } else if (payment == 3) {
                pay = "check";
                break;
            } else System.out.println("\nwrong input please choose again\n");
        }
        String print = pc.addSupplier(firstName, lName, email, ID, phone, companyNumber, perm, self, pay);
        System.out.println(print);
        if (!print.split(" ")[0].equals("\nError:")) {
            while (true) {
                System.out.println("please choose option:");
                System.out.println("1. add item to agreement");
                System.out.println("2. stop add items to agreement");
                System.out.print("Option: ");
                int opt = getIntFromUser();
                if (opt == 1) {
                    System.out.println("choose items to agreement: ");
                    System.out.print("enter product id: ");
                    int productID = getIntFromUser();
                    System.out.print("enter your company product id: ");
                    int companyProductID = getIntFromUser();
                    System.out.print("enter price: ");
                    int price = getIntFromUser();
                    System.out.println(pc.addItemToagreement(ID, productID, companyProductID, price));
                } else if (opt == 2) break;
                else System.out.println("invalid option try again");
            }
        }
    }

    private void getSupplier() {
        System.out.print("please enter supplier id: ");
        String supplierId = readID();
        System.out.println(pc.getSupplier(supplierId));
    }
    //a helper funtion that edit supplier details in the system
    private void updateSupplierDetailFunc() {
        int opt = -1;
        System.out.println("please enter supplier id");
        String supplierID = readID();
        boolean flag = true;
        while (flag) {
            System.out.println("Please choose an option to Edit:");
            System.out.println("1. edit first name");
            System.out.println("2. edit last name");
            System.out.println("3. edit phone number");
            System.out.println("4. edit email");
            System.out.println("5. edit company number");
            System.out.println("6. edit permanent orders option");
            System.out.println("7. edit delivery option");
            System.out.println("8. edit payment method");
            System.out.println("9. add contact person");
            System.out.println("10. remove contact person");
            System.out.println("for main menu choose any other option");
            System.out.print("option : ");
            int choose = getIntFromUser();
            switch (choose) {
                case 1:
                    System.out.print("please enter first name: ");
                    String firstName = readName();
                    System.out.println(pc.updateFirstName(supplierID, firstName));
                    break;
                case 2:
                    System.out.print("please enter last name: ");
                    String lastName = readName();
                    System.out.println(pc.updateLastName(supplierID, lastName));
                    break;
                case 3:
                    System.out.print("please enter phone number: ");
                    String phoneNum = readPhone();
                    System.out.println(pc.updatePhone(supplierID, phoneNum));
                    break;
                case 4:
                    System.out.print("please enter email: ");
                    String emailAddr = readEmail();
                    System.out.println(pc.updateEmail(supplierID, emailAddr));
                    break;
                case 5:
                    System.out.print("please enter company number: ");
                    int cn = readCompanyNumber();
                    System.out.println(pc.updateCompanyNumber(supplierID, cn));
                    break;
                case 6:
                    while (true) {
                        System.out.print("choose:\n1. permanent days\n2. non permanent days\n option number: ");
                        opt = getIntFromUser();
                        if (opt == 1) {
                            System.out.println(pc.updatePernamentDays(supplierID, true));
                            break;
                        } else if (opt == 2) {
                            System.out.println(pc.updatePernamentDays(supplierID, false));
                            break;
                        } else
                            System.out.println("invalid option please chose again");
                    }
                    break;
                case 7:
                    while (true) {
                        System.out.print("choose:\n1. self delivery\n2. not self delivery\n option number: ");
                        opt = getIntFromUser();
                        if (opt == 1) {
                            System.out.println(pc.updateSelfDelivery(supplierID, true));
                            break;
                        } else if (opt == 2) {
                            System.out.println(pc.updateSelfDelivery(supplierID, false));
                            break;
                        } else
                            System.out.println("invalid option please chose again");
                    }
                    break;
                case 8:
                    while (true) {
                        System.out.print("please choose a payment method\n1. Cash\n2. Bank transfer\n3. check\nchoose number: ");
                        opt = getIntFromUser();
                        if (opt == 1) {
                            System.out.println(pc.updatePayment(supplierID, "cash"));
                            break;
                        }
                        if (opt == 2) {
                            System.out.println(pc.updatePayment(supplierID, "bankTrasfer"));
                            break;
                        }
                        if (opt == 3) {
                            System.out.println(pc.updatePayment(supplierID, "check"));
                            break;
                        } else
                            System.out.println("invalid option please chose again");
                    }
                case 9:
                    while (true) {
                        System.out.println("Choose an option:\n1. add a contact person\n2. back to suppliers menu");
                        opt = getIntFromUser();
                        if (opt == 2)
                            break;
                        else if (opt != 1)
                            System.out.println("invalid option");
                        else if (opt == 1) {
                            System.out.print("please enter following details: ");
                            System.out.print("\nfirst name: ");
                            String fName = readName();
                            System.out.print("last name: ");
                            String lName = readName();
                            System.out.print("email: ");
                            String email = readEmail();
                            System.out.print("ID: ");
                            String ID = readID();
                            System.out.print("phone: ");
                            String phone = readPhone();
                            System.out.println(pc.addContactMember(supplierID, fName, lName, email, ID, phone));
                        }
                    }
                    break;
                case 10:
                    System.out.println("please enter a contact member id to remove:");
                    String memberID = readID();
                    System.out.println(pc.deleteContactMember(supplierID, memberID));
                    break;
                default:
                    flag = false;
            }
        }
    }

    private void addQuantityList() { //case 4
        System.out.print("please enter supplier id: ");
        String supplierId = readID();
        System.out.println(pc.addQuantityList(supplierId));
        while (true) {
            System.out.println("please choose an option");
            System.out.println("1. add new product");
            System.out.println("2. exit");
            System.out.print("option: ");
            int num = getIntFromUser();
            if (num == 2)
                break;
            System.out.println("please enter the following details: ");
            System.out.print("product id: ");
            int productId = getIntFromUser();
            System.out.print("amount of products to get a discount: ");
            int amount = getIntFromUser();
            System.out.print("discount amount: ");
            int discount = getIntFromUser();
            System.out.println(pc.addQuantityListItem(supplierId, productId, amount, discount));
        }
        System.out.println(pc.getQuantityList(supplierId));
    }

    private void editQuantityList() { //case 5
        System.out.print("please enter supplier id: ");
        String supplierId = readID();
        System.out.println("Please choose an option to Edit:");
        System.out.println("1. edit product amount");
        System.out.println("2. edit product discount");
        System.out.println("3. add new product");
        System.out.println("4. delete product");
        System.out.println("5. delete quantity list");

        int num = getIntFromUser();
        switch (num) {
            case 1: { //edit product amount
                System.out.println("product id: ");
                int prodId = getIntFromUser();
                System.out.println("new amount of products to get a discount: ");
                int amount = getIntFromUser();
                System.out.println(pc.editQuantityListAmount(supplierId, prodId, amount));
                break;
            }
            case 2: {  //edit product discount
                System.out.println("product id: ");
                int prodId = getIntFromUser();
                System.out.println("new discount: ");
                int discount = getIntFromUser();
                System.out.println(pc.editQuantityListDiscount(supplierId, prodId, discount));
                break;
            }
            case 3: { //add new product
                System.out.println("product id: ");
                int productId = getIntFromUser();
                System.out.println("amount of products to get a discount: ");
                int amount = getIntFromUser();
                System.out.println("discount amount: ");
                int discount = getIntFromUser();
                System.out.println(pc.addQuantityListItem(supplierId, productId, amount, discount));
                break;
            }
            case 4: { //delete product
                System.out.println("product id: ");
                int productId = getIntFromUser();
                System.out.println(pc.deleteQuantityListItem(supplierId, productId));
            }
            case 5: { //delete quantity list
                System.out.println(pc.deleteQuantityList(supplierId));
            }
        }
    }

    private void editAgrreement() {
        System.out.print("Please enter supplier ID:");
        String supplierId = readID();
        boolean flag = true;
        while (flag) {
            System.out.println("please choose an option:");
            System.out.println("1. add product");
            System.out.println("2. remove product");
            System.out.println("3. edit company product id");
            System.out.println("4. edit product price");
            System.out.println("5. back to main menu");
            System.out.print("Option:");
            int opt = getIntFromUser();
            int productId = -1;
            switch (opt) {
                case 1 -> {//add new product
                    System.out.print("please enter system product id: ");
                    productId = getIntFromUser();
                    System.out.print("please enter your company product id: ");
                    int compNum = getIntFromUser();
                    System.out.print("please enter price: ");
                    int price = getIntFromUser();
                    System.out.print(pc.addItemToagreement(supplierId, productId, compNum, price));
                }
                case 2 -> {//delete product
                    System.out.print("product id: ");
                    productId = getIntFromUser();
                    System.out.println(pc.removeItemFromAgreement(supplierId, productId));
                }
                case 3 -> {
                    System.out.println("please enter the following details: ");
                    System.out.print("supplier id: ");
                    supplierId = readID();
                    System.out.println("product id: ");
                    productId = scan.nextInt();
                    System.out.println("new company product id: ");
                    int newCompId = scan.nextInt();
                    System.out.println(pc.editAgreementItemCompanyProductID(supplierId, productId, newCompId));
                }

                case 4 -> {
                    System.out.print("product id: ");
                    productId = scan.nextInt();
                    System.out.print("new price: ");
                    int price = scan.nextInt();
                    System.out.println(pc.editAgreementItemPrice(supplierId, productId, price));
                }
                case 5 -> flag = false;
                default -> System.out.println("wrong input try again");
            }
        }
    }

    private void getAgreement() {
        System.out.print("please enter supplier ID: ");
        String supplierID = readID();
        System.out.println(pc.getAgreement(supplierID));
    }
    private void getQuantityList() {
        System.out.print("please enter supplier ID: ");
        String supplierID = readID();
        System.out.println(pc.getQuantityList(supplierID));
    }

    private void removeSupplier() {
        System.out.print("please enter supplier id: ");
        String id = readID();
        System.out.println(pc.removeSupplier(id));
    }
    private void inventoryMainMenu() {
        menu.printInventoryMainMenu();
        String choice = menu.instructAndReceive("Select a sub menu: ");

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
        String choice = menu.instructAndReceive("Select item option: ");
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
        String choice = menu.instructAndReceive("Select category option: ");
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
        String choice = menu.instructAndReceive("Select Sale option: ");
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
        String choice = menu.instructAndReceive("Select supplier discount option: ");
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
        String choice = menu.instructAndReceive("Select Report option: ");
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

    private String getStringFromUser() {
        boolean con = true;
        String output = "";
        while (con) {
            try {
                output = scan.next();
                con = false;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("wrong input please try again");
                scan.nextLine();
            }
        }
        return output;

    }

    //a helper function to get a Date from the user
    private LocalDate getDateFromUser() {
        boolean con = true;
        String output = "";
        LocalDate lclDate = LocalDate.now();
        while (con) {
            try {
                output = scan.next();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
                lclDate = LocalDate.parse(output, dateTimeFormatter);
                con = false;
            } catch (Exception e) {
                System.out.print("invalid date input, please insert again (dd/mm/yyyy): ");
            }
        }
        return lclDate;
    }

    //a helper function to get in from the user
    private int getIntFromUser() {
        int choose = -1;
        boolean scannerCon = true;
        while (scannerCon) {
            try {
                choose = scan.nextInt();
                if (choose < 0) {
                    System.out.println("you must choose an none-negative number ");
                } else {
                    scannerCon = false;
                }
            } catch (InputMismatchException ie) {
                System.out.println("wrong input - a number must be inserted please try again ");
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("wrong input - a number must be inserted please try again ");
                scan.nextLine();
            }
        }
        return choose;
    }


    private void nameCheck(String name) throws Exception {
        if (name == null || name.equals(""))
            throw new Exception("name cannot be null or empty spaces");
        //checks length of the name
        if (name.length() > MaxNamesLength)
            throw new Exception("name length is too long");
        //check if contains only letters
        char[] arrayName = name.toLowerCase().toCharArray();
        for (char c : arrayName) {
            if (c < 'a' || c > 'z')
                throw new Exception("the name must contain letters only");
        }
    }

    private void idCheck(String id) throws Exception {
        if (id == null || id.equals(""))
            throw new Exception("id cannot be null or empty spaces");
        if (id.length() != idLength)
            throw new Exception("id must contain " + idLength + " letters");
        //check if contains only numbers
        char[] idArray = id.toCharArray();
        for (char c : idArray) {
            if (c < '0' || c > '9')
                throw new Exception("the id must contain numbers only");
        }
    }

    private void phoneCheck(String phone) throws Exception {
        if (phone == null || phone.equals(""))
            throw new Exception("phone cannot be null or empty spaces");
        if (phone.length() != PhoneLength)
            throw new Exception("phone must contain " + PhoneLength + " letters, given phone :" + phone);
        //check if contains only numbers
        char[] idArray = phone.toCharArray();
        for (char c : idArray) {
            if (c < '0' || c > '9')
                throw new Exception("the number must contain numbers only");
        }
        if (phone.charAt(0) != '0')
            throw new Exception("phone number must begin with 0");
    }

    private void emailCheck(String email) throws Exception {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            throw new Exception("email cannot be null");
        if (!pat.matcher(email).matches())
            throw new Exception("invalid email");
    }

    //method that check if companyNumber is legal
    private void companyNumberCheck(int comanyNumber) throws Exception {
        if (comanyNumber < 0 || comanyNumber > MaxCompanyNumber)
            throw new Exception("invalid company number" + comanyNumber);
    }

    private String readID() {
        while (true) {
            String id = getStringFromUser();
            try {
                idCheck(id);
                return id;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\ntry again: ");
            }
        }
    }

    private String readName() {
        while (true) {
            String name = getStringFromUser();
            try {
                nameCheck(name);
                return name;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\ntry again: ");
            }
        }
    }

    private String readPhone() {
        while (true) {
            String phone = getStringFromUser();
            try {
                phoneCheck(phone);
                return phone;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\ntry again: ");
            }
        }
    }

    private String readEmail() {
        while (true) {
            String email = menu.instructAndReceive("enter E-Mail: ");
            try {
                emailCheck(email);
                return email;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\ntry again: ");
            }
        }
    }

    private int readCompanyNumber() {
        while (true) {
            int cn = menu.instructAndReceive("enter company number: ", Integer.class);
            try {
                companyNumberCheck(cn);
                return cn;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\ntry again: ");
            }
        }
    }


}
