package PresentationLayer;

import InfrastructurePackage.Pair;
import ServiceLayer.IService;
import ServiceLayer.Response.*;
import ServiceLayer.Response.Response;
import ServiceLayer.Service;
import ServiceLayer.FacadeObjects.*;
import ServiceLayer.FacadeObjects.FacadeOrder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class PresentationController implements Runnable {
    private final IService service;

    private Scanner scan;
    private final InventorySupplierMenu menu;
    private boolean terminate;
    private final int MaxCompanyNumber = 100;
    private final int MaxNamesLength = 20;
    private final int PhoneLength = 10;
    private final int idLength = 9;


    public PresentationController() {
        this.menu = new InventorySupplierMenu();
        terminate = false;
        service = Service.getInstance();
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
        String storageL = menu.instructAndReceive("Enter storage location: follow this format \"ST-A<number>-<L/R>-S<number>\": ");
        String storeL = menu.instructAndReceive("Enter shelf location: follow this format \"SH-A<number>-<L/R>-S<number>\": ");
        int intManfac = menu.instructAndReceive("Enter manufacturer ID: ", Integer.class);
        int weight = menu.instructAndReceive("Enter item weight: ", Integer.class);
        String category = menu.instructAndReceive("Enter category name: ");
        Response r = service.addItem(itemID, name, category, costP, sellP, minA, storeL, storageL, storageQ, storeQ, intManfac, weight, new ArrayList<>());
        if (r.errorOccurred()) {
            menu.errorPrompt(r.getMessage());
        } else {
            System.out.println("Item added successfully");
        }
    }

    private void showItem() {
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        ResponseT<FacadeItem> r = service.getItem(itemID);
        if (!r.errorOccurred()) {
            menu.itemHeader();
            menu.printEntity(r.getValue());
        } else {
            menu.errorPrompt(r.getMessage());
        }
    }

    private void editItem() {
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        ResponseT<FacadeItem> r = service.getItem(itemID);
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
            case "1" -> {
                String newName = menu.instructAndReceive("Enter new item name: ");
                r = service.modifyItemName(itemId, newName);
            }
            case "2" -> {
                String newCategory = menu.instructAndReceive("Enter new item category: ");
                r = service.modifyItemCategory(itemId, newCategory);
            }
            case "3" -> {
                double newCPrice = menu.instructAndReceive("Enter cost price: ", Double.class);
                r = service.modifyItemCostPrice(itemId, newCPrice);
            }
            case "4" -> {
                double newSPrice = menu.instructAndReceive("Enter sell price: ", Double.class);
                r = service.modifyItemSellingPrice(itemId, newSPrice);
            }
            case "5" -> {
                String newLocationST = menu.instructAndReceive("Enter new item storage location: ");
                r = service.changeItemStorageLocation(itemId, newLocationST);
            }
            case "6" -> {
                String newLocationSH = menu.instructAndReceive("Enter new item shelf location: ");
                r = service.changeItemShelfLocation(itemId, newLocationSH);
            }
            case "7" -> {
                int newQuantityST = menu.instructAndReceive("Enter storage quantity: ", Integer.class);
                r = service.modifyItemStorageQuantity(itemId, newQuantityST);
            }
            case "8" -> {
                int newQuantitySH = menu.instructAndReceive("Enter storage quantity: ", Integer.class);
                r = service.modifyItemShelfQuantity(itemId, newQuantitySH);
            }
            default -> r = new Response(true, "Invalid choice");
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
        ResponseT<FacadeCategory> rCategory = service.getCategory(catName);
        if (rCategory.errorOccurred())
            System.out.println(rCategory.getMessage());
        else
            menu.printEntity(rCategory.getValue());
    }

    private void editCategory() {
        String catName = menu.instructAndReceive("Enter category name: ");
        ResponseT<FacadeCategory> catR = service.getCategory(catName);
        if (catR.errorOccurred()) {
            menu.errorPrompt(catR.getMessage());
            return;
        } else
            menu.printEntity(catR.getValue());
        menu.printCategoryModList();
        String userInput = menu.instructAndReceive("Enter choice: ");
        Response modResp;
        switch (userInput) {
            case "1" -> {
                String newName = menu.instructAndReceive("Enter new name: ");
                modResp = service.modifyCategoryName(catR.getValue().getName(), newName);
            }
            case "2" -> {
                String newParent = menu.instructAndReceive("Enter new parent category name: (keep in mind not to use a subcategory!)");
                modResp = service.changeParentCategory(catR.getValue().getName(), newParent);
            }
            default -> modResp = new Response(true, "Invalid choice");
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

    private <T extends FacadeEntity> void showSale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        ResponseT<FacadeSale<T>> saleR = service.getSale(saleName);
        if (saleR.errorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
        } else {
            menu.printEntity(saleR.getValue());
        }
    }

    private void addItemSale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        double saleDiscount = menu.instructAndReceive("Enter sale discount: (e.g. for 10% enter 0.1)", Double.class);
        Pair<LocalDate, LocalDate> dates = getStartEndDates();
        if (dates == null) return;
        //getting the item to be applied on
        int applyOnItem = menu.instructAndReceive("Enter item ID for the sale: ", Integer.class);
        //checking if exists
        ResponseT<FacadeItem> rExist = service.getItem(applyOnItem);
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
        Pair<LocalDate, LocalDate> dates = getStartEndDates();
        if (dates == null) return;
        //getting the item to be applied on
        String applyOnItem = menu.instructAndReceive("Enter category name for the sale: ");
        //checking if exists
        ResponseT<FacadeCategory> rExist = service.getCategory(applyOnItem);
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

    private Pair<LocalDate, LocalDate> getStartEndDates() {
        System.out.println("Enter start date: ");
        LocalDate start = getDateFromUser();
        System.out.println("Enter end date: ");
        LocalDate end = getDateFromUser();
        Pair<LocalDate, LocalDate> dates = new Pair<>(start, end);
        //checking that the date makes sense
        if (start.compareTo(end) > 0) {
            menu.errorPrompt("End date is before start date");
            return null;
        }
        return dates;
    }

    private <T extends FacadeEntity> void modifySale() {
        String saleName = menu.instructAndReceive("Enter sale name: ");
        ResponseT<FacadeSale<T>> saleR = service.getSale(saleName);
        if (saleR.errorOccurred()) {
            menu.errorPrompt(saleR.getMessage());
            return;
        } else
            menu.printEntity(saleR.getValue());

        menu.printSaleModList();
        String userInput = menu.instructAndReceive("Enter choice: ");
        Response modResp;
        switch (userInput) {
            case "1" -> {
                String newName = menu.instructAndReceive("Enter new name: ");
                modResp = service.modifySaleName(saleR.getValue().getName(), newName);
            }
            case "2" -> {
                double newDisc = menu.instructAndReceive("Enter sale discount (e.g. for 10% enter 0.1): ", Double.class);
                modResp = service.modifySaleDiscount(saleR.getValue().getName(), newDisc);
            }
            case "3" -> {
                Pair<LocalDate, LocalDate> dates = getStartEndDates();
                if (dates == null)
                    return;
                modResp = service.modifySaleDates(saleR.getValue().getName(), dates.getFirst(), dates.getSecond());
            }
            default -> modResp = new Response(true, "Invalid choice");
        }
        if (modResp.errorOccurred())
            menu.errorPrompt(modResp.getMessage());
        else
            System.out.println("Changes Saved!");
    }

    private <T extends FacadeEntity> void showSupplierDiscount() {
        String supplierID = readID();
        System.out.print("Enter date in the following format dd/mm/yyyy: ");
        LocalDate date = getDateFromUser();
        ResponseT<List<FacadeDiscount<T>>> discR = service.getDiscount(supplierID, date);
        if (discR.errorOccurred()) {
            menu.errorPrompt(discR.getErrorMessage());
            return;
        }
        for (FacadeDiscount<T> d : discR.getValue()) {
            menu.printEntity(d);
        }

    }

    private void recordDefect() {
        LocalDate date = LocalDate.now();
        //item id
        int itemID = menu.instructAndReceive("Enter item ID: ", Integer.class);
        //item amount
        int defectedAmount = menu.instructAndReceive("Enter the defect amount: ", Integer.class);
        //location2
        String location = menu.instructAndReceive("Enter storage/shelf location: follow this format \"<ST/SH>-A<number>-<L/R>-S<number>\": ");
        Response recorded = service.recordDefect(itemID, date, defectedAmount, location);
        if (recorded.errorOccurred()) {
            menu.errorPrompt(recorded.getMessage());
            return;
        }
        System.out.println("Defect reported!");
    }

    private void inventoryReport() {
        ResponseT<List<FacadeItem>> reportResp = service.inventoryReport();
        if (reportResp.errorOccurred()) {
            menu.errorPrompt(reportResp.getMessage());
            return;
        }
        menu.printEntity(reportResp.getValue());
    }

    private void categoryReport() {
        String catName = menu.instructAndReceive("Enter category name: ");
        ResponseT<FacadeCategory> catR = service.getCategory(catName);
        if (catR.errorOccurred()) {
            menu.errorPrompt(catR.getMessage());
            return;
        }
        ResponseT<List<FacadeItem>> categoryItems = service.categoryReport(catName);
        if (categoryItems.errorOccurred()) {
            menu.errorPrompt(categoryItems.getMessage());
        }
        catR.value.setItems(categoryItems.value);
        menu.printEntity(catR.getValue());
    }

    private void itemShortageReport() {
        ResponseT<List<FacadeItem>> shortage = service.itemShortageReport();
        if (shortage.errorOccurred()) {
            menu.errorPrompt(shortage.getMessage());
            return;
        }
        menu.printEntity(shortage.getValue());
    }

    private void defectsReport() {
        Pair<LocalDate, LocalDate> interval = getStartEndDates();
        if (interval == null)
            return;
        ResponseT<List<FacadeDefectEntry>> defects = service.defectsReport(interval.getFirst(), interval.getSecond());
        if (defects.errorOccurred()) {
            menu.errorPrompt(defects.getMessage());
            return;
        }
        menu.printDefectMenu();
        try {
            for (FacadeDefectEntry entry : defects.getValue()) {
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
            mainMenu();
        }
    }

    public void setupSystem() {
        String toSetup = menu.instructAndReceive("Would you like to load the system with data? [y/n]\n 'no' will start up a clean system ");
        toSetup = toSetup.toLowerCase();
        if (toSetup.compareTo("y") != 0) {
            System.out.println("Starting clean system");
            return;
        }
        CreateObjects co = new CreateObjects(service);
        co.setupSystem();
    }

    public void mainMenu() {
        menu.printMainMenu();
        String choice = menu.instructAndReceive("Select a menu to open: ");
        switch (choice) {
            case "1" -> inventoryMainMenu();
            case "2" -> suppliersMainMenu();
            case "3" -> orderMainMenu();
            case "q" -> {terminate = true; return;}
            default -> menu.errorPrompt("Invalid choice - " + choice);
        }
    }

    private void orderMainMenu() {
        System.out.println("\nOrder menu:");
        System.out.println("1. Create shortage order");
        System.out.println("2. Create scheduled order");
        System.out.println("3. Approve order");
        System.out.println("4. Get order");
        System.out.println("5. Add product to order");
        System.out.println("6. Remove product from order");
        String chooseInsideMenu = menu.instructAndReceive("Enter choice: ");
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
        ResponseT<List<FacadeOrder>> oo = service.createShortageOrder(lclDate);
        if (oo.errorOccurred()) {
            System.out.println(oo.getErrorMessage());
            if (oo.value == null)
                return;
        }
        String order = oo.value.stream().map(o -> o.getId() + "").reduce("added orders", (acc, curr) -> {
            service.sendOrderToTruck(Integer.parseInt(curr));
            return acc + ", " + curr;
        });
        System.out.println(order);
    }

    private void setPermanentOrder() { //case 7
        int day = menu.instructAndReceive("Day (1-7) in a week to get the order: ", Integer.class);
        while (true) {
            int itemID = menu.instructAndReceive("Enter item ID to add to a scheduled order: ", Integer.class);
            int amount = menu.instructAndReceive("Enter amount to order: ", Integer.class);
            ResponseT<FacadeOrder> orderR = service.createScheduledOrder(day, itemID, amount);
            if (orderR.errorOccurred()) {
                menu.errorPrompt(orderR.getErrorMessage());
                return;
            }
            String order = orderR.toString();
            System.out.println(order);
            String choice = menu.instructAndReceive("*) Press 1 to add more items\n*) Press anything else to complete \n");
            if (!choice.equals("1")) {
                break;
            }
        }

    }

    //a helper function that approve that the order arrived in the system
    private void approveOrder() {
        int orderID = menu.instructAndReceive("Please enter order ID: ", Integer.class);
        System.out.println(service.approveOrder(orderID).toString());
    }

    //a helper function to get an order from the system
    private void getOrder() {
        int orderID = menu.instructAndReceive("Please enter order ID: ", Integer.class);
        System.out.println(service.getOrder(orderID).toString());
    }

    //a helper function to add a product to an order
    private void addProductToOrder() {
        int orderID = menu.instructAndReceive("Please enter order ID: ", Integer.class);
        int productID = menu.instructAndReceive("Please enter item ID: ", Integer.class);
        int amount = menu.instructAndReceive("Enter amount to order: ", Integer.class);
        System.out.println(service.addProductToOrder(orderID, productID, amount).toString());
    }

    private void removeProductFromOrder() {
        System.out.println("\nPlease enter the following details: ");
        int orderId = menu.instructAndReceive("Order id: ", Integer.class);
        int productId = menu.instructAndReceive("Product id: ", Integer.class);
        System.out.println(service.removeProductFromOrder(orderId, productId).toString());
    }

    private void suppliersMainMenu() {
        System.out.println("\nSupplier menu:");
        System.out.println("1. Add supplier");
        System.out.println("2. Get supplier");
        System.out.println("3. Update supplier details");
        System.out.println("4. Add quantity List");
        System.out.println("5. Edit quantity List");
        System.out.println("6. Edit agreement");
        System.out.println("7. Get quantityList");
        System.out.println("8. Get agreement");
        System.out.println("9. Remove supplier");
        String chooseInsideMenu = menu.instructAndReceive("Enter choice: ");
        switch (chooseInsideMenu) {
            case "1" -> addSupplierFunc();
            case "2" -> getSupplier();
            case "3" -> updateSupplierDetailFunc();
            case "4" -> addQuantityList();
            case "5" -> editQuantityList();
            case "6" -> editAgreement();
            case "7" -> getQuantityList();
            case "8" -> getAgreement();
            case "9" -> removeSupplier();
        }
    }

    //a helper function that add supplier to the system
    private void addSupplierFunc() {
        System.out.print("\nPlease enter the following details: \n");
        String firstName = readName(0);
        String lName = readName(1);
        String email = readEmail();
        String ID = readID();
        String phone = readPhone();
        String address = menu.instructAndReceive("Address :");
        int companyNumber = readCompanyNumber();
        boolean perm;
        while (true) {
            String permanent = menu.instructAndReceive("Supplier approve permanent orders? y/n: ");
            if (permanent.equals("y")) {
                perm = true;
                break;
            } else if (permanent.equals("n")) {
                perm = false;
                break;
            } else System.out.println("Invalid input please choose again\n");
        }
        boolean self;
        while (true) {
            String selfD = menu.instructAndReceive("Supplier has self delivery? y/n: ");
            if (selfD.equals("y")) {
                self = true;
                break;
            } else if (selfD.equals("n")) {
                self = false;
                break;
            } else System.out.println("Invalid input please choose again\n");
        }
        String pay = "";
        while (true) {
            int payment = menu.instructAndReceive("How would you like to pay?\n1. Cash\n2. Bank transfer\n3. Check\nChoose number: ", Integer.class);
            if (payment == 1) {
                pay = "Cash";
                break;
            } else if (payment == 2) {
                pay = "Bank Transfer";
                break;
            } else if (payment == 3) {
                pay = "Check";
                break;
            } else System.out.println("\nInvalid input please choose again\n");
        }
        int area = 1;
        while (true) {
            area = menu.instructAndReceive("Enter delivery area\n1. North\n2. Center\n3. South\nchoose number: ", Integer.class);
            if (area > 0 || area < 4) {
                break;
            } else System.out.println("\nwrong input please choose again\n");
        }
        String print = service.addSupplier(firstName, lName, email, ID, phone, companyNumber, perm, self, pay, address, area).toString();
        System.out.println(print);
        if (!print.split(" ")[0].equals("\nError:")) {
            while (true) {
                System.out.println("\nPlease choose option:");
                System.out.println("1. add item to agreement");
                System.out.println("2. stop add items to agreement");
                System.out.print("Option: ");
                int opt = getIntFromUser();
                if (opt == 1) {
                    System.out.println("Choose items to agreement: ");
                    int productID = menu.instructAndReceive("Enter product id: ", Integer.class);
                    int companyProductID=menu.instructAndReceive("Enter your company product id: ", Integer.class);
                    int price = menu.instructAndReceive("Enter price: ", Integer.class);
                    System.out.println(service.addItemToAgreement(ID, productID,companyProductID, price).toString());
                } else if (opt == 2) break;
                else System.out.println("invalid option try again");
            }
        }
    }

    private void getSupplier() {
        System.out.print("Please enter supplier id: ");
        String supplierId = readID();
        System.out.println(service.getSupplier(supplierId).toString());
    }

    //a helper funtion that edit supplier details in the system
    private void updateSupplierDetailFunc() {
        int opt = -1;
        System.out.println("Please enter supplier id: ");
        String supplierID = readID();
        boolean flag = true;
        while (flag) {
            System.out.println("\nPlease choose an option to Edit: ");
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
            System.out.print("Option : ");
            int choose = getIntFromUser();
            switch (choose) {
                case 1:
                    System.out.print("Please enter ");
                    String firstName = readName(0);
                    System.out.println(service.updateFirstName(supplierID, firstName).toString());
                    break;
                case 2:
                    System.out.print("Please enter ");
                    String lastName = readName(1);
                    System.out.println(service.updateLastName(supplierID, lastName).toString());
                    break;
                case 3:
                    System.out.print("Please enter ");
                    String phoneNum = readPhone();
                    System.out.println(service.updatePhone(supplierID, phoneNum).toString());
                    break;
                case 4:
                    System.out.print("Please enter ");
                    String emailAddr = readEmail();
                    System.out.println(service.updateEmail(supplierID, emailAddr).toString());
                    break;
                case 5:
                    System.out.print("Please enter ");
                    int cn = readCompanyNumber();
                    System.out.println(service.updateCompanyNumber(supplierID, cn).toString());
                    break;
                case 6:
                    while (true) {
                        System.out.print("Choose:\n1. Permanent Days\n2. Non-Permanent Days\n Option number: ");
                        opt = getIntFromUser();
                        if (opt == 1) {
                            System.out.println(service.updatePernamentDays(supplierID, true).toString());
                            break;
                        } else if (opt == 2) {
                            System.out.println(service.updatePernamentDays(supplierID, false).toString());
                            break;
                        } else
                            System.out.println("Invalid option please choose again");
                    }
                    break;
                case 7:
                    while (true) {
                        System.out.print("Choose:\n1. Self Delivery\n2. Not Self Delivery\n Option number: ");
                        opt = getIntFromUser();
                        if (opt == 1) {
                            System.out.println(service.updateSelfDelivery(supplierID, true).toString());
                            break;
                        } else if (opt == 2) {
                            System.out.println(service.updateSelfDelivery(supplierID, false).toString());
                            break;
                        } else
                            System.out.println("Invalid option please choose again");
                    }
                    break;
                case 8:
                    while (true) {
                        System.out.print("Please choose a payment method\n1. Cash\n2. Bank Transfer\n3. Check\nChoose number: ");
                        opt = getIntFromUser();
                        if (opt == 1) {
                            System.out.println(service.updatePayment(supplierID, "Cash"));
                            break;
                        }
                        if (opt == 2) {
                            System.out.println(service.updatePayment(supplierID, "Bank Transfer"));
                            break;
                        }
                        if (opt == 3) {
                            System.out.println(service.updatePayment(supplierID, "Check"));
                            break;
                        } else
                            System.out.println("Invalid option please choose again");
                    }
                    break;
                case 9:
                    while (true) {
                        System.out.println("Choose an option:\n1. Add a contact person\n2. Back to suppliers menu");
                        opt = getIntFromUser();
                        if (opt == 2)
                            break;
                        else if (opt != 1)
                            System.out.println("Invalid option");
                        else if (opt == 1) {
                            System.out.print("\nPlease enter following details: ");
                            String fName = readName(0);
                            String lName = readName(1);
                            String email = readEmail();
                            String ID = readID();
                            String phone = readPhone();
                            System.out.println(service.addContactMember(supplierID, fName, lName, email, ID, phone).toString());
                        }
                    }
                    break;
                case 10:
                    System.out.println("Please enter a contact member id to remove: ");
                    String memberID = readID();
                    System.out.println(service.deleteContactMember(supplierID, memberID).toString());
                    break;
                default:
                    flag = false;
            }
        }
    }

    private void addQuantityList() { //case 4
        System.out.print("Please enter supplier id: ");
        String supplierId = readID();
        System.out.println(service.addQuantityList(supplierId).toString());
        while (true) {
            System.out.println("Please choose an option");
            System.out.println("1. Add new product");
            System.out.println("2. Exit");
            System.out.print("Option: ");
            int num = getIntFromUser();
            if (num == 2)
                break;
            System.out.println("\nPlease enter the following details: ");
            int productId = menu.instructAndReceive("Product id: ", Integer.class);
            int amount = menu.instructAndReceive("Amount of products to get a discount: ", Integer.class);
            int discount = menu.instructAndReceive("Discount amount: ", Integer.class);
            System.out.println(service.addQuantityListItem(supplierId, productId, amount, discount).toString());
        }
        System.out.println(service.getQuantityList(supplierId).toString());
    }

    private void editQuantityList() { //case 5
        System.out.print("Please enter supplier id: ");
        String supplierId = readID();
        System.out.println("Please choose an option to Edit:");
        System.out.println("1. edit product amount");
        System.out.println("2. edit product discount");
        System.out.println("3. add new product");
        System.out.println("4. delete product");
        System.out.println("5. delete quantity list");

        int num = getIntFromUser();
        switch (num) {
            case 1 -> { //edit product amount
                int prodId = menu.instructAndReceive("Product id: ", Integer.class);
                int amount = menu.instructAndReceive("New amount of products to get a discount: ", Integer.class);
                System.out.println(service.editQuantityListAmount(supplierId, prodId, amount).toString());
            }
            case 2 -> {  //edit product discount
                int prodId = menu.instructAndReceive("Product id: ", Integer.class);
                int discount = menu.instructAndReceive("New discount amount: ", Integer.class);
                System.out.println(service.editQuantityListDiscount(supplierId, prodId, discount).toString());
            }
            case 3 -> { //add new product
                int productId = menu.instructAndReceive("Product id: ", Integer.class);
                int amount = menu.instructAndReceive("Amount of products to get a discount: ", Integer.class);
                System.out.println("Discount amount: ");
                int discount = menu.instructAndReceive("Discount amount: ", Integer.class);
                System.out.println(service.addQuantityListItem(supplierId, productId, amount, discount).toString());
            }
            case 4 -> { //delete product
                int productId = menu.instructAndReceive("Product id: ", Integer.class);
                System.out.println(service.deleteQuantityListItem(supplierId, productId).toString());
            }
            case 5 -> { //delete quantity list
                System.out.println(service.deleteQuantityList(supplierId).toString());
            }
        }
    }

    private void editAgreement() {
        System.out.print("Please enter supplier id: ");
        String supplierId = readID();
        boolean flag = true;
        while (flag) {
            System.out.println("\nPlease choose an option:");
            System.out.println("1. Add product");
            System.out.println("2. Remove product");
            System.out.println("3. Edit company product id");
            System.out.println("4. Edit product price");
            System.out.println("5. Back to main menu");
            System.out.print("Option:");
            int opt = getIntFromUser();
            int productId = -1;
            switch (opt) {
                case 1 -> {//add new product
                    productId = menu.instructAndReceive("Please enter system product id: ", Integer.class);
                    int price = menu.instructAndReceive("Please enter price: ", Integer.class);
                    int companyProductID=menu.instructAndReceive("Enter your company product id: ", Integer.class);
                    System.out.println((service.addItemToAgreement(supplierId, productId,companyProductID, price).toString()));
                }
                case 2 -> {//delete product
                    productId = menu.instructAndReceive("Please enter system product id: ", Integer.class);
                    System.out.println(service.removeItemFromAgreement(supplierId, productId).toString());
                }
                case 3 -> {
                    System.out.println("\nPlease enter the following details: ");
                    productId = menu.instructAndReceive("Product id: ", Integer.class);
                    int newCompId = menu.instructAndReceive("Enter new company product id: ", Integer.class);
                    System.out.println(service.editAgreementItemCompanyProductID(supplierId, productId, newCompId).toString());
                }
                case 4 -> {
                    productId = menu.instructAndReceive("Product id: ", Integer.class);
                    int price = menu.instructAndReceive("Enter new price: ", Integer.class);
                    System.out.println(service.editAgreementItemPrice(supplierId, productId, price).toString());
                }
                case 5 -> flag = false;
                default -> System.out.println("Invalid input try again");
            }
        }
    }

    private void getAgreement() {
        System.out.print("Please enter supplier id: ");
        String supplierID = readID();
        System.out.println(service.getAgreement(supplierID).toString());
    }

    private void getQuantityList() {
        System.out.print("Please enter supplier id: ");
        String supplierID = readID();
        System.out.println(service.getQuantityList(supplierID).toString());
    }

    private void removeSupplier() {
        System.out.print("Please enter supplier id: ");
        String id = readID();
        System.out.println(service.removeSupplier(id).toString());
    }

    private void inventoryMainMenu() {
        menu.printInventoryMainMenu();
        String choice = menu.instructAndReceive("Select a sub menu: ");

        switch (choice) {
            case "1" -> itemMenuOperations();
            case "2" -> categoryMenuOperations();
            case "3" -> saleMenuOperations();
            case "4" -> discountMenuOperations();
            case "5" -> recordDefect();
            case "6" -> reportMenuOperations();
            default -> menu.errorPrompt("Invalid choice - " + choice);
        }


    }


    private void itemMenuOperations() {
        menu.printItemMenu();
        String choice = menu.instructAndReceive("Select item option: ");
        switch (choice) {
            case "1" -> showItem();
            case "2" -> addItem();
            case "3" -> editItem();
            case "4" -> removeItem();
            default -> menu.errorPrompt("Invalid choice - " + choice);
        }
    }

    private void categoryMenuOperations() {
        menu.printCategoryMenu();
        String choice = menu.instructAndReceive("Select category option: ");
        switch (choice) {
            case "1" -> showCategory();
            case "2" -> addCategory();
            case "3" -> editCategory();
            case "4" -> removeCategory();
            default -> menu.errorPrompt("Invalid choice - " + choice);
        }
    }

    private void saleMenuOperations() {
        menu.printSaleMenu();
        String choice = menu.instructAndReceive("Select Sale option: ");
        switch (choice) {
            case "1" -> showSale();
            case "2" -> addItemSale();
            case "3" -> addCategorySale();
            case "4" -> modifySale();
            default -> menu.errorPrompt("Invalid choice - " + choice);
        }
    }

    private void discountMenuOperations() {
        menu.printDiscountMenu();
        String choice = menu.instructAndReceive("Select supplier discount option: ");
        if ("1".equals(choice))
            showSupplierDiscount();
        else
            menu.errorPrompt("Invalid choice - " + choice);
    }

    public void reportMenuOperations() {
        menu.printReportMenu();
        String choice = menu.instructAndReceive("Select Report option: ");
        switch (choice) {
            case "1" -> inventoryReport();
            case "2" -> categoryReport();
            case "3" -> itemShortageReport();
            case "4" -> defectsReport();
            default -> menu.errorPrompt("Invalid choice - " + choice);
        }

    }

    //a helper function to get a Date from the user
    private LocalDate getDateFromUser() {
        boolean con = true;
        String output = "";
        LocalDate lclDate = LocalDate.now();
        while (con) {
            try {
                output = scan.next();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                lclDate = LocalDate.parse(output, dateTimeFormatter);
                con = false;
            } catch (Exception e) {
                System.out.print("Invalid date input, please insert again (dd/mm/yyyy): ");
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
                    System.out.println("You must choose an none-negative number ");
                } else {
                    scannerCon = false;
                }
            } catch (InputMismatchException ie) {
                System.out.println("Invalid input - a number must be inserted please try again ");
                scan.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input - a number must be inserted please try again ");
                scan.nextLine();
            }
        }
        return choose;
    }


    private void nameCheck(String name) throws Exception {
        if (name == null || name.equals(""))
            throw new Exception("Name cannot be null or empty spaces");
        //checks length of the name
        if (name.length() > MaxNamesLength)
            throw new Exception("Name length is too long");
        //check if contains only letters
        char[] arrayName = name.toLowerCase().toCharArray();
        for (char c : arrayName) {
            if (c < 'a' || c > 'z')
                throw new Exception("The name must contain letters only");
        }
    }

    private void idCheck(String id) throws Exception {
        if (id == null || id.equals(""))
            throw new Exception("ID cannot be null or empty spaces");
        if (id.length() != idLength)
            throw new Exception("ID must contain " + idLength + " letters");
        //check if contains only numbers
        char[] idArray = id.toCharArray();
        for (char c : idArray) {
            if (c < '0' || c > '9')
                throw new Exception("The ID must contain numbers only");
        }
    }

    private void phoneCheck(String phone) throws Exception {
        if (phone == null || phone.equals(""))
            throw new Exception("Phone cannot be null or empty spaces");
        if (phone.length() != PhoneLength)
            throw new Exception("Phone must contain " + PhoneLength + " letters, given phone :" + phone);
        //check if contains only numbers
        char[] idArray = phone.toCharArray();
        for (char c : idArray) {
            if (c < '0' || c > '9')
                throw new Exception("The number must contain numbers only");
        }
        if (phone.charAt(0) != '0')
            throw new Exception("Phone number must begin with 0");
    }

    private void emailCheck(String email) throws Exception {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            throw new Exception("Email cannot be null");
        if (!pat.matcher(email).matches())
            throw new Exception("Invalid email");
    }

    //method that check if companyNumber is legal
    private void companyNumberCheck(int comanyNumber) throws Exception {
        if (comanyNumber < 0 || comanyNumber > MaxCompanyNumber)
            throw new Exception("Invalid company number" + comanyNumber);
    }

    private String readID() {
        while (true) {
            String id = menu.instructAndReceive("ID: ");
            try {
                idCheck(id);
                return id;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\nTry again: ");
            }
        }
    }

    private String readName(int opt) {
        while (true) {
            String name = "";
            if (opt == 0)
                name = menu.instructAndReceive("First name: ");
            else
                name = menu.instructAndReceive("Last name: ");
            try {
                nameCheck(name);
                return name;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\nTry again: ");
            }
        }
    }

    private String readPhone() {
        while (true) {
            String phone = menu.instructAndReceive("Phone: ");
            try {
                phoneCheck(phone);
                return phone;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\nTry again: ");
            }
        }
    }

    private String readEmail() {
        while (true) {
            String email = menu.instructAndReceive("Email: ");
            try {
                emailCheck(email);
                return email;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\nTry again: ");
            }
        }
    }

    private int readCompanyNumber() {
        while (true) {
            int cn = menu.instructAndReceive("Company number: ", Integer.class);
            try {
                companyNumberCheck(cn);
                return cn;
            } catch (Exception e) {
                System.out.print(e.getMessage() + "\nTry again: ");
            }
        }
    }
}
