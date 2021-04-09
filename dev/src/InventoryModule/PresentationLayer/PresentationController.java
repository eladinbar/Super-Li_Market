package InventoryModule.PresentationLayer;

import InfrastructurePackage.Pair;
import InventoryModule.ControllerLayer.InventoryService;
import InventoryModule.ControllerLayer.InventoryServiceImpl;
import InventoryModule.ControllerLayer.Response;
import InventoryModule.ControllerLayer.ResponseT;
import InventoryModule.ControllerLayer.SimpleObjects.*;

import java.util.*;

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
        String tempID = menu.instructAndReceive("Enter item ID: ");
        if(tempID.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int itemID = Integer.parseInt(tempID);
        String name = menu.instructAndReceive("Enter name: ");
        String tempCost = menu.instructAndReceive("Enter cost price: ");
        if(tempCost.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        double costP = Double.parseDouble(tempCost);
        String tempSell = menu.instructAndReceive("Enter sell price: ");
        if(tempSell.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        double sellP = Double.parseDouble(tempSell);
        String tempMin = menu.instructAndReceive("Enter minimum amount");
        if(tempMin.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        int minA = Integer.parseInt(tempMin);
        String tempQuan = menu.instructAndReceive("Enter storage quantity: ");
        if(tempQuan.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        int storageQ = Integer.parseInt(tempQuan);
        String tempQuan2 = menu.instructAndReceive("Enter shelf quantity: ");
        if(tempQuan2.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        int storeQ = Integer.parseInt(tempQuan2);
        String storageL = menu.instructAndReceive("Enter storage location: follow this format \"ST-A<number>-<L/R>-S<number>\"");
        String storeL = menu.instructAndReceive("Enter shelf location: follow this format \"SH-A<number>-<L/R>-S<number>\"");
        String tempM = menu.instructAndReceive("Enter manufacturer ID: ");
        if(tempM.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int idManf = Integer.parseInt(tempM);
        String category = menu.instructAndReceive("Enter category name: ");

        Response r = service.addItem(itemID, name, category, costP, sellP, minA, storeL, storageL, storageQ, storeQ, idManf, new ArrayList<>());
        if (r.isErrorOccurred()) {
            menu.errorPrompt(r.getMessage());
        } else {
            System.out.println("Item added successfully");
        }
    }

    private void showItem() {
        String temp = menu.instructAndReceive("Enter item ID: ");
        if(temp.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int itemID = Integer.parseInt(temp);
        ResponseT<Item> r = service.getItem(itemID);
        if (!r.isErrorOccurred()) {
            menu.printEntity(r.getData());
        } else {
            menu.errorPrompt(r.getMessage());
        }
    }

    private void editItem() {
        String temp = menu.instructAndReceive("Enter item ID: ");
        if(temp.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int itemID = Integer.parseInt(temp);
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
                if(tempCost.isEmpty()){
                    return new Response(true, "no price entered");
                }
                double newCPrice = Double.parseDouble(tempCost);
                r = service.modifyItemCostPrice(itemId, newCPrice);
                break;
            case "4":
                String tempSell = menu.instructAndReceive("Enter cost price: ");
                if(tempSell.isEmpty()){
                    return new Response(true, "no price entered");
                }
                double newSPrice = Double.parseDouble(tempSell);
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
                if(tempQuan.isEmpty()){
                    menu.errorPrompt("no price entered");
                    return new Response(true, "no quantity entered");
                }
                int newQuantityST = Integer.parseInt(tempQuan);
                r = service.modifyItemStorageQuantity(itemId, newQuantityST);
                break;
            case "8":
                String tempQuan2 = menu.instructAndReceive("Enter shelf quantity: ");
                if(tempQuan2.isEmpty()){
                    menu.errorPrompt("no price entered");
                    return new Response(true, "no quantity entered");
                }
                int newQuantitySH = Integer.parseInt(tempQuan2);
                r = service.modifyItemShelfQuantity(itemId, newQuantitySH);
                break;
            case "9":
                String tempSup = menu.instructAndReceive("\"Add  new supplier for the item: (enter supplier ID)\"): ");
                if(tempSup.isEmpty()){
                    menu.errorPrompt("no price entered");
                    return new Response(true, "no id entered");
                }
                int newSupplier = Integer.parseInt(tempSup);
                r = service.addItemSupplier(itemId, newSupplier);
                break;
            case "10":
                String tempRevSup = menu.instructAndReceive("Remove a supplier for the item: (enter supplier ID)");
                if(tempRevSup.isEmpty()){
                    menu.errorPrompt("no price entered");
                    return new Response(true, "no id entered");
                }
                int oldSupplier = Integer.parseInt(tempRevSup);
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
        if(temp.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int itemID = Integer.parseInt(temp);
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
        if(tempDisc.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        double saleDiscount = Double.parseDouble(tempDisc);
        Pair<Calendar, Calendar> dates = getStartEndDates();
        if (dates == null) return;
        //getting the item to be applied on
        String temp = menu.instructAndReceive("Enter item ID for the sale: ");
        if(temp.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int applyOnItem = Integer.parseInt(temp);
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
        if(tempDisc.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        double saleDiscount = Double.parseDouble(tempDisc);
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
        start.set(Integer.parseInt(startDate[0]), Integer.parseInt(startDate[1]) - 1, Integer.parseInt(startDate[2]));
        String[] endDate = menu.instructAndReceive("Enter end date: use this format <YYYY-MM-DD>").split("-");
        Calendar end = Calendar.getInstance();
        end.set(Integer.parseInt(endDate[0]), Integer.parseInt(endDate[1]) - 1, Integer.parseInt(endDate[2]));
        Pair<Calendar, Calendar> dates = new Pair<>(start, end);
        //checking that the date make sense
        if (start.compareTo(end) > 0) {
            menu.errorPrompt("End date is before start date");
            return null;
        }
        return dates;
    }

    private <T extends SimpleEntity> void modifySale() {
        String saleName = menu.instructAndReceive("Enter category name: ");
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
                if(tempDisc.isEmpty()){
                    menu.errorPrompt("no price entered");
                    return;
                }
                double newDisc = Double.parseDouble(tempDisc);
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
        if(temp.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int suppId = Integer.parseInt(temp);
        String[] disDateS = menu.instructAndReceive("Enter the date of the discount").split("-");
        ;
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(disDateS[0]), Integer.parseInt(disDateS[1]) - 1, Integer.parseInt(disDateS[2]));
        ResponseT<List<Discount<T>>> discR = service.getDiscount(suppId, date);
        for (Discount<T> d : discR.getData()) {
            menu.printEntity(d);
        }

    }

    private void addItemSupplierDiscount() {
        //supplier Id
        String temp = menu.instructAndReceive("Enter supplier ID: ");
        if(temp.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int suppId = Integer.parseInt(temp);
        //item id
        String temp2 = menu.instructAndReceive("Enter item ID that the discount applies on: ");
        if(temp2.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int itemId = Integer.parseInt(temp);
        //discount
        String tempDisc = menu.instructAndReceive("Enter discount received for the item: (e.g. for 10% enter 0.1)");
        if(tempDisc.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        double discountGiven = Double.parseDouble(tempDisc);
        //dates
        String[] dateS = menu.instructAndReceive("Enter discount date: use this format <YYYY-MM-DD>").split("-");
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]) - 1, Integer.parseInt(dateS[2]));
        //count
        String tempAmount = menu.instructAndReceive("Enter the amount the discount applied for: ");
        if(tempAmount.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        int itemCount = Integer.parseInt(tempAmount);
        if (itemCount <= 0) {
            menu.errorPrompt("Item amount has to be at least 1.");
            return;
        }
        Response r1 = service.addItemDiscount(suppId, discountGiven, date, itemCount, itemId);
        if (r1.isErrorOccurred()) {
            menu.errorPrompt(r1.getMessage());
            return;
        }
        System.out.println("Sale added successfully");


    }

    private void addCategorySupplierDiscount() {
        //supplier Id
        String temp = menu.instructAndReceive("Enter supplier ID: ");
        if(temp.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int suppId = Integer.parseInt(temp);
        String catName = menu.instructAndReceive("Enter the category name that the discount applies on: ");
        //discount
        String tempDisc = menu.instructAndReceive("Enter discount received for the item: (e.g. for 10% enter 0.1)");
        if(tempDisc.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        double discountGiven = Double.parseDouble(tempDisc);
        //date
        String[] dateS = menu.instructAndReceive("Enter discount date: use this format <YYYY-MM-DD>").split("-");
        Calendar date = Calendar.getInstance();
        date.set(Integer.parseInt(dateS[0]), Integer.parseInt(dateS[1]) - 1, Integer.parseInt(dateS[2]));
        String tempAmount = menu.instructAndReceive("Enter the amount the discount applied for: ");
        if(tempAmount.isEmpty()){
            menu.errorPrompt("no price entered");
            return;
        }
        int itemCount = Integer.parseInt(tempAmount);
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
        String temp = menu.instructAndReceive("Enter item ID: ");
        if(temp.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int itemID = Integer.parseInt(temp);
        //item amount
        String tempAmount = menu.instructAndReceive("Enter the defect amount: ");
        if(tempAmount.isEmpty()){
            menu.errorPrompt("no id entered");
            return;
        }
        int defectedAmount = Integer.parseInt(tempAmount);
        //location
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
        menu.getTextFormatter().DefectsMenuFormat();
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
        while (!terminate) {
            setupSystem();
            menu.printOperationMenu();
            operationInput();
        }

    }

    private void setupSystem() {

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
