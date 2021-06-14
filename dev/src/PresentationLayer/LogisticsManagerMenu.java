package PresentationLayer;

import BusinessLayer.Notification;
import BusinessLayer.TruckingNotifications;
import BusinessLayer.TruckingPackage.ResourcesPackage.Driver;
import ServiceLayer.FacadeObjects.*;
import ServiceLayer.Response.ResponseT;
import ServiceLayer.TruckingService;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.naming.TimeLimitExceededException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.Scanner;

public class LogisticsManagerMenu {
    TruckingService ts;
    private static LogisticsManagerMenu instance = null;

    private LogisticsManagerMenu() {
        ts = TruckingService.getInstance();
    }

    public static LogisticsManagerMenu getInstance() {
        if (instance == null) {
            instance = new LogisticsManagerMenu();
        }
        return instance;
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Menus >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.


    public void mainMenu() {
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("[\\,\\n\\r]+");
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\n\nWelcome to Trucking Menu!\nplease choose the option you'd like:");
            int spot = 1;

            System.out.println(spot + ".\tCurrent Status");
            spot++;
            System.out.println(spot + ".\tManager Options - Drivers and Trucks");
            spot++;

            System.out.println(spot + ".\tLog Out");
            int choose = getIntFromUserMain(scanner);
            switch (choose) {
                case 1:
                    while (currentStatusMenu(scanner)) ;
                    break;
                case 2:
                    while (managerOptionsMenu(scanner)) ;
                    break;
                case 3:
                    keepGoing = false;
                    break;

                default:
                    System.out.println("option is out of bounds, please try again");
                    break;
            }


        }

    }

    // TODO need to check
    private boolean currentStatusMenu(Scanner scanner) {
        int spot = 1;
        System.out.println(spot + ".\tShow Notifications");
        spot++;
        System.out.println(spot + ".\tShow Drivers");
        spot++;
        System.out.println(spot + ".\tShow Trucks");
        spot++;
        System.out.println(spot + ".\tShow Current Demands");
        spot++; //4
        System.out.println(spot + ".\tShow Active Trucking Reports"); // 4
        spot++;
        System.out.println(spot + ".\tShow Old Active Reports");
        spot++;
        System.out.println(spot + ".\tGo back Logistics Menu");

        int chose = getIntFromUserMain(scanner);
        switch (chose) {
            case 1:
                printNotifications();
                break;
            case 2:
                printDrivers();
                break;
            case 3:
                printTrucks();
                break;
            case 4:
                printDemands();
                break;
            case 5:
                showActiveTruckingReports();
                break;
            case 6:
                showOldTruckingReports();
                break;
            case 7:
                return false;
            default:
                System.out.println("option is out of bounds, please try again");
                break;

        }
        return true;
    }

    private void printNotifications() {
        ResponseT<LinkedList<TruckingNotifications>> res = ts.getNotifications();
        if (res.errorOccurred()){
            System.out.println(res.getErrorMessage());
            return;
        }
        LinkedList<TruckingNotifications> notes = res.getValue() ;
        if (notes.isEmpty()){
            System.out.println("no new notifications ");
            return;
        }
        int spot =1;
        for (TruckingNotifications n : notes){
            System.out.print(spot + ")");
            System.out.println(notes);
            spot++;
        }
    }

    private boolean managerOptionsMenu(Scanner scanner) {
        int spot = 1;
        System.out.println(spot + ".\tApprove/Cancel Trucking Report");
        spot++;
        System.out.println(spot + "\tAdd New Truck To System");
        spot++;
        System.out.println(spot + "\tGo Back To Logistic Menu");
        spot++;
        System.out.print("please choose the option you'd like: ");
        int chose = getIntFromUserMain(scanner);
        switch (chose) {
            case 1:
                try {
                    approveTruckReports(scanner);
                } catch (ReflectiveOperationException re) {
                    System.out.println(re.getMessage());
                }
                break;
            case 2:
                try {
                    addNewTruck(scanner);
                } catch (ReflectiveOperationException re) {
                    System.out.println(re.getMessage());
                }
                break;
            case 3:
                return false;
            default:
                System.out.println("option is out of bounds, please try again");
                break;
        }
        return true;


    }


//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Print Methods >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private void printTrucks() {
        ResponseT<LinkedList<FacadeTruck>> res = ts.getTrucks();
        if (res.errorOccurred()){
            System.out.println(res.getErrorMessage());
            return;
        }
        LinkedList<FacadeTruck> trucks = res.getValue();
        if (trucks == null || trucks.isEmpty())
            System.out.println("no Trucks in the system yet");
        else {
            for (FacadeTruck truck : trucks) {
                System.out.println("Trucks License Number: " + truck.getLicenseNumber() +
                        "\nmodel: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight());

            }
        }
    }

    private void printDemands() {
        try {
            ResponseT<LinkedList<FacadeDemand>> res = ts.getDemands();
            if (res.errorOccurred()){
                System.out.println(res.getErrorMessage());
                return;
            }
            LinkedList<FacadeDemand> demands = res.getValue();
            if(demands.isEmpty()){
                System.out.println("No demands left in the system");
            }
            demands = sortDemandsBySite(demands);
            int spot =1;
            for (FacadeDemand demand: demands){
                System.out.print( spot + ")");
                printDemand(demand);
            }


        } catch (NoSuchElementException ne) {
            System.out.println(ne.getMessage());
        }
    }

    private void printDemand(FacadeDemand demand) {
        String itemName = ts.getItemName(demand.getItemID());
        System.out.println(itemName + "\tAmount: "+demand.getAmount() + "\tSupplier" + ts.getSupplierName(demand.getSupplier()));
    }


    private void printDrivers() {
        ResponseT<LinkedList<FacadeDriver>> res = ts.getDrivers();
        if (res.errorOccurred()){
            System.out.println(res.getErrorMessage());
            return;
        }
        LinkedList<FacadeDriver> drivers = res.getValue();

        if (drivers == null || drivers.isEmpty()) System.out.println("no Drivers in the system yet");
        else {
            for (FacadeDriver facadeDriver : drivers) {
                System.out.print("\t");
                printDriverDetails(facadeDriver);
            }
        }
    }



    private void showActiveTruckingReports() {
        ResponseT<LinkedList<FacadeTruckingReport>> res = ts.getActiveTruckingReports();
        ResponseT<LinkedList<FacadeTruckingReport>> res2 = ts.getWaitingTruckingReports();
        if (res.errorOccurred() ){
            System.out.println(res.getErrorMessage());
            return;
        }
        if (res2.errorOccurred() ){
            System.out.println(res.getErrorMessage());
            return;
        }

        LinkedList<FacadeTruckingReport> truckingReports = res.getValue();
        truckingReports.addAll(res2.getValue());

        if (truckingReports.isEmpty())
            System.out.println("No active Trucking Reports");
        else {
            int spot = 1;
            for (FacadeTruckingReport tr : truckingReports) {

                System.out.print(spot + ")");
                printTruckReport(tr);
                spot++;
            }
        }
    }



    private void showOldTruckingReports() {
        ResponseT<LinkedList<FacadeTruckingReport>> res = ts.getOldTruckingReports();
        if (res.errorOccurred()){
            System.out.println(res.getErrorMessage());
            return;
        }
        LinkedList<FacadeTruckingReport> truckingReports = res.getValue();

        if (truckingReports.isEmpty())
            System.out.println("No active Trucking Reports");
        else {
            int spot = 1;
            for (FacadeTruckingReport tr : truckingReports) {

                System.out.print(spot + ")");
                printTruckReport(tr);
                spot++;
            }
        }
    }


    private void printTruckReport(FacadeTruckingReport tr){
        int spot = 1;
        String rep = "didn't approved";
        if (tr.isApproved()) {
            String com = "not Completed";
            if (tr.isCompleted())
                com = "Completed";
            rep = "approved "+ com;
        }
        System.out.println("\tTrucking report ID: " + tr.getID() + "\nOrigin site:"  + "\tDate:" + tr.getDate() +
                "\tLeaving Hour:" + tr.getLeavingHour() + "status: " + rep );

        System.out.println("related delivery form:");
        ResponseT<LinkedList<FacadeDeliveryForm>> res = ts.getDeliveryFormsByTruckReport(tr.getID());
        if (res.errorOccurred()){
            System.out.println(res.getErrorMessage());
            return;
        }
        LinkedList<FacadeDeliveryForm> dfs = res.getValue();

        spot =1;
        for (FacadeDeliveryForm deliveryForm: dfs){
            System.out.print(spot+")");
            printDeliveryForm(deliveryForm);
            spot++;
        }


    }

    private void printDeliveryForm(FacadeDeliveryForm deliveryForm) {
        System.out.println("ID:" + deliveryForm.getID()+ "\tsupplier: " + deliveryForm.getSupplier() + "" +
                "\nitems:");
        System.out.println("\t\tname\t\tAmount");
        HashMap<Integer, Integer> items = deliveryForm.getItems();
        for (Map.Entry<Integer,Integer> entry: items.entrySet()){
            printItem(entry.getKey(), entry.getValue());
        }
        System.out.println("total Weight: " + deliveryForm.getLeavingWeight());

    }

    private void printItem(Integer item_id, Integer amount) {
        String name = ts.getItemName(item_id);
        System.out.println("\t\t" + name+"\t\t"+amount);

    }


    private void approveTruckReports(java.util.Scanner scanner) throws ReflectiveOperationException {
        ResponseT<LinkedList<FacadeTruckingReport>> res = ts.getWaitingTruckingReports();
        if (res.errorOccurred()){
            System.out.println(res.getErrorMessage());
            return;
        }
        LinkedList<FacadeTruckingReport> reports = res.getValue();
        if (reports.isEmpty()){
            System.out.println("no reports to approve yet");
            return;
        }
        int spot =1;
        for (FacadeTruckingReport report: reports){
            System.out.print(spot+")");
            printTruckReport(report);
        }
        System.out.println("\nplease choose the truck report you'd like to approve or cancel");
        int chose = getIntFromUser(scanner);
        while (chose<1 || chose > reports.size()-1){
            System.out.println("number out bounds, please try again");
            chose= getIntFromUser(scanner);
        }
        chose = chose -1;
        FacadeTruckingReport report = reports.get(chose);
        System.out.println("choose 1 to accept order. 2 to cancel");
        chose = getIntFromUser(scanner);
        boolean keepGoing = true;
        while (keepGoing) {
            switch (chose) {
                case 1:
                    try {
                        keepGoing = false;
                        ts.managerApproveTruckReport(report.getID());
                        System.out.println("report has been approved successfully");


                    } catch (TimeLimitExceededException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    keepGoing = false;
                    try {
                        ts.managerCancelTruckReport(report.getID());
                        System.out.println("report has been canceled");
                    } catch (TimeLimitExceededException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("option out of bounds, please try again");


            }
        }


    }

    private void addNewTruck(Scanner scanner) throws ReflectiveOperationException {
        boolean con = true;
        while (con) {
            System.out.print("Truck License ID: ");
            String licenseNumber = getStringFromUser(scanner);
            while (!licenseNumber.matches("[0-9]*")) {
                System.out.println("License number should only contain digits,please try again");
                licenseNumber = getStringFromUser(scanner);
            }
            System.out.print("the Trucks model:");
            String model = getStringFromUser(scanner);
            System.out.print("Weight Neto:");
            int weightNeto = getIntFromUser(scanner);
            System.out.print("Max Weight:");
            int maxWeight = getIntFromUser(scanner);
            while (maxWeight <= weightNeto) {
                System.out.println("the  truck's max weight is not higher then it's neto weight(" + weightNeto + "), please choose again or quit with -1");
                maxWeight = getIntFromUser(scanner);
            }

            try {
                ts.addTruck(model, licenseNumber, weightNeto, maxWeight);
                con = false;
            } catch (KeyAlreadyExistsException|SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }



    private int getIntFromUserMain(Scanner scanner) {

        int choose = -1;
        boolean scannerCon = true;
        while (scannerCon) {
            try {

                choose = scanner.nextInt();

                if (choose < 0) {
                    System.out.println("you must choose an none-negative number ");
                }
                scannerCon = false;
            } catch (Exception n) {
                System.out.println(n.getMessage());
                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            }
        }
        return choose;

    }



    private int getIntFromUser(Scanner scanner) throws ReflectiveOperationException {
        int choose = -1;
        boolean scannerCon = true;
        while (scannerCon) {
            try {

                choose = scanner.nextInt();

                if (choose == -1) throw new ReflectiveOperationException("by pressing -1 you chose to go back");
                if (choose < 0) {
                    System.out.println("you must choose an none-negative number ");
                } else {
                    scannerCon = false;
                }
            } catch (InputMismatchException i) {
                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            } catch (NoSuchElementException | IllegalStateException | NumberFormatException ie) {

                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            }

        }
        return choose;
    }


    private double getDoubleFromUser(Scanner scanner) throws ReflectiveOperationException {


        double choose = -1;
        boolean scannerCon = true;
        while (scannerCon)
            try {

                choose = scanner.nextDouble();
                if (choose == -1) throw new ReflectiveOperationException("by pressing -1 you chose to go back");
                if (choose < 0) {
                    System.out.println("you must choose an none-negative number ");
                }
                scannerCon = false;
            } catch (InputMismatchException ie) {
                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            } catch (NoSuchElementException | IllegalStateException | NumberFormatException e) {
                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            }
        return choose;
    }

    private String getStringFromUser(Scanner scanner) throws ReflectiveOperationException {

        boolean con = true;
        String output = "";
        while (con) {
            try {
                output = scanner.next();
                if (output.equals("-1")) throw new ReflectiveOperationException("by pressing -1 you chose to go back");
                con = false;
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("wrong input please try again");
                scanner.nextLine();
            }
        }
        return output;

    }

    private LinkedList<FacadeDemand> sortDemandsBySite(LinkedList<FacadeDemand> demands) {
        return ts.sortDemandsBySite(demands);
    }




    private void printDriverDetails(FacadeDriver driver) {
        System.out.println(driver.getName() + ":\n\t" + "Driver ID: " + driver.getID() +
                "\tLicense Type: " + driver.getLicenseType());
    }


// TODO need to check all down>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


   /* // TODO need to check
    private boolean managerDriverAndTrucks(Scanner scanner) {
        int spot = 1;
        System.out.println(spot + "\tAdd new Truck to the System");
        spot++;


        System.out.println(spot + ".\tAdd new site to the System"); // 2
        spot++;
        System.out.println(spot + ".\tAdd new item to the System");
        spot++;
        System.out.println(spot + ".\tAdd new Demand to the System");
        spot++;


        System.out.println(spot + ".\tRemove item from the system");
        spot++;
        System.out.println(spot + ".\tRemove site from the system");//6
        spot++;
        System.out.println(spot + ".\tRemove Demand from the system");
        spot++;

        System.out.println(spot + ".\tGo back To PresentationLayer.Employee_PresnetationLayer$.Main Menu");

        int chose = getIntFromUserMain(scanner);
        switch (chose) {
            case 1:
                try {
                    addNewTruck(scanner);

                } catch (ReflectiveOperationException re) {
                    System.out.println(re.getMessage());
                }

                break;
            case 2:
                try {
                    addSite(scanner);
                } catch (ReflectiveOperationException re) {
                    System.out.println(re.getMessage());
                }
                break;
            case 3:
                try {
                    addItem(scanner);
                } catch (ReflectiveOperationException re) {
                    System.out.println(re.getMessage());
                }
                break;

            case 4:
                try {
                    addDemandToSystem(scanner);
                } catch (ReflectiveOperationException re) {
                    System.out.println(re.getMessage());
                }
                break;
            case 5:
                try {
                    removeItemFromPool(scanner);
                } catch (ReflectiveOperationException |SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 6:
                try {
                    removeSiteFromPool(scanner);
                } catch (ReflectiveOperationException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 7:
                try {
                    removeDemandFromPool(scanner);
                } catch (ReflectiveOperationException |SQLException e) {
                    System.out.println(e.getMessage());
                }
                break;


            case 8:
                return false;
            default:
                System.out.println("option is out of bounds, please try again");
                break;

        }
        return true;

    }
    private void printDateOptions(LocalDate date, HashMap<Integer, LinkedList<String>> shiftAndDrivers, int spot) {
        for (Map.Entry<Integer, LinkedList<String>> entry : shiftAndDrivers.entrySet()) {
            System.out.println(spot + ".\t" + date + "\tshift start time:" + turnShiftToTimes(entry.getKey()));
            System.out.println("\nThe Drivers working in this this shift:");
            for (String id : entry.getValue()) {
                printDriverDetails(pc.getDriver(id));
            }
            System.out.println("\n\n");

        }

    }


    // <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< methods >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    private LocalTime turnShiftToTimes(int shift) {
        if (shift == 0)
            return LocalTime.of(6, 0);
        else
            return LocalTime.of(14, 0);

    }

    private void showOldDeliveryForm() {
        LinkedList<FacadeTruckingReport> truckingReports = pc.getOldTruckingReport();
        if (truckingReports.isEmpty())
            System.out.println("No Completed Trucking Reports has been found");
        else {
            int spot = 1;
            for (FacadeTruckingReport tr : truckingReports) {
                String rep = "--";
                if (tr.getTRReplace() != null) {
                    rep = "" + tr.getTRReplace().getID();
                }
                System.out.println(spot + ")\tTrucking report ID: " + tr.getID() + "\nOrigin site:" + tr.getOrigin() + "\tDate" + tr.getDate() +
                        "\tLeaveing Hour:" + tr.getLeavingHour() + "\nreplaced:" + rep);


                spot++;
            }
        }

    }



    private void checkAvailableTrucksAndDrivers(LocalDate date, LocalTime shift) throws ReflectiveOperationException {
        if (pc.getAvailableTrucks(date, shift).isEmpty())
            throw new ReflectiveOperationException("no trucks left to deliver with, please try later");
        if (pc.getAvailableDrivers(date, shift).isEmpty())
            throw new ReflectiveOperationException("no drivers left to deliver with, please try later");
    }

    private void removeDemandFromPool(Scanner scanner) throws ReflectiveOperationException,SQLException {
        LinkedList<FacadeDemand> demands = pc.showDemands();
        demands = sortDemandsBySite(demands);
        printDemands(demands);
        int chose = getIntFromUser(scanner);
        while (chose < 1 || chose > demands.size()) {
            chose = getIntFromUser(scanner);
        }
        FacadeDemand d = demands.get(chose - 1);
        pc.removeDemand(d);

    }

    private LinkedList<String> chooseDate(Scanner scanner) throws IllegalArgumentException, ReflectiveOperationException, SQLException {

        HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> daysAndDrivers = pc.getDaysAndDrivers();
        int spot = 1;
        if (daysAndDrivers.isEmpty()) {
            throw new ReflectiveOperationException("no possible dates to deliver.");
        }
        for (Map.Entry<LocalDate, HashMap<Integer, LinkedList<String>>> entry : daysAndDrivers.entrySet()) {
            printDateOptions(entry.getKey(), entry.getValue(), spot);
            spot++;

        }
        System.out.println("please insert your choice here or -1 to quit");

        int chose = getIntFromUser(scanner);
        while (chose < 1 || chose > daysAndDrivers.size()) {
            System.out.println("option is out of bounds, please try again");
            chose = getIntFromUser(scanner);
        }
        LocalDate toInsert;
        spot = 1;
        for (Map.Entry<LocalDate, HashMap<Integer, LinkedList<String>>> entry : daysAndDrivers.entrySet()) {
            if (spot == chose) {
                System.out.println("The date you choose: " + entry.getKey());
                LocalTime time = null;
                int c;
                if (entry.getValue().size() > 1) {
                    System.out.print("please choose the shift for this date:\n1.\tmorning shift\n2.\tafter-noon shift");
                    c = getIntFromUser(scanner);
                    while (c < 1 || c > 2) {
                        c = getIntFromUser(scanner);
                    }
                    c = c - 1;
                    time = turnShiftToTimes(c);


                } else {
                    c = 0;
                    for (Map.Entry<Integer, LinkedList<String>> shift : entry.getValue().entrySet()) {
                        c = shift.getKey();
                    }
                    time = turnShiftToTimes(c);
                }
                toInsert = entry.getKey();
                pc.chooseDateToCurrentTR(toInsert);
                pc.chooseLeavingHour(time);
                return entry.getValue().get(c);


            }
            spot ++;

        }

        return null;

    }


    private void removeSiteFromPool(Scanner scanner) throws ReflectiveOperationException {
        System.out.println("please choose the site you'd like to remove");
        LinkedList<FacadeSite> sites = pc.getAllSites();
        int spot = 1;
        for (FacadeSite site : sites) {
            System.out.println(spot + ")" + site.getName());
            spot++;
        }
        int chose = getIntFromUser(scanner);
        while (chose < 1 || chose > sites.size()) {
            System.out.println("option is out of bounds, please try again");
            chose = getIntFromUser(scanner);
        }
        int siteID = sites.get(chose - 1).getSiteID();
        try {
            pc.removeSiteFromPool(siteID);
        } catch (NoSuchElementException | IllegalStateException|SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    private void addDemandToSystem(Scanner scanner) throws ReflectiveOperationException {
        int itemId;
        int site;
        int amount;
        int spot = 1;
        System.out.println("Please choose the item you'd add:");
        try {
            LinkedList<FacadeItem> items = pc.getAllItems();
            for (FacadeItem item : items) {
                System.out.println(spot + ")item id: " + item.getID() + "\tname: " + item.getName() + "\tOrigin Site: " + pc.getSiteName(item.getOriginId()) + "\tdelivery area: " + pc.getSiteDeliveryArea(item.getOriginId()));
                spot++;
            }

            itemId = getIntFromUser(scanner);
            while (itemId < 1 || itemId > items.size()) {
                System.out.println("the option you chose is out of bounds, try again");
                itemId = getIntFromUser(scanner);
            }
            itemId = items.get((itemId - 1)).getID();

            try {
                LinkedList<FacadeSite> sites = pc.getAllSites();
                System.out.println("please choose the site you'd like to Deliver to");
                spot = 1;
                for (FacadeSite s : sites) {
                    System.out.println(spot + ")" + s.getName() + ":\tsiteID: " + s.getSiteID() + "\tcity: " + s.getCity() + "\t\tin Delivery Area: " + s.getDeliveryArea());
                    spot++;
                }
                site = getIntFromUser(scanner);
                while (site < 1 || site > sites.size()) {
                    System.out.println("the option you chose is out of bounds, try again");
                    site = getIntFromUser(scanner);
                }
                site = sites.get(site - 1).getSiteID();
                try {
                    System.out.print("please choose the amount you'd like to Deliver:");
                    amount = getIntFromUser(scanner);
                    pc.addDemandToSystem(itemId, site, amount);
                } catch (NoSuchElementException|SQLException ne) {
                    System.out.println(ne.getMessage());
                }

            } catch (NoSuchElementException ne) {
                System.out.println(ne.getMessage());
            }


        } catch (NoSuchElementException ne) {
            System.out.println(ne.getMessage());
        }
    }


    private void removeItemFromPool(Scanner scanner) throws ReflectiveOperationException, SQLException {
        LinkedList<FacadeItem> items = pc.getAllItems();
        int counter = 1;
        for (FacadeItem item : items) {
            System.out.println(counter + ")\tItem Name: " + item.getName() + "\tItem id: " + item.getID());
            counter++;
        }
        System.out.print("choose the item you'd to remove from pool:");
        int choice = getIntFromUser(scanner);
        if (choice < 1 || choice > items.size()) {
            System.out.println("option is out of bounds, going back to menu");
        } else {
            try{
                pc.RemoveItemFromPool(items.get(choice - 1).getID());

            }catch (IllegalStateException e){
                throw new ReflectiveOperationException(e.getMessage());
            }
        }
    }

    private void addItem(Scanner scanner) throws ReflectiveOperationException {
        boolean con = true;
        while (con) {
            System.out.print("item weight: ");
            double weight = getDoubleFromUser(scanner);
            System.out.print("item name: ");
            String name = getStringFromUser(scanner);
            while (!name.matches("[A-Z a-z]*")) {
                System.out.println("invalid name instered, please reWrite it");
                name = getStringFromUser(scanner);
            }
            System.out.println("please choose its origin site ");
            LinkedList<FacadeSite> sites = pc.getAllSites();
            int spot = 1;
            for (FacadeSite site : sites) {
                System.out.println(spot + ")" + site.getName());
                spot++;
            }
            int chose = getIntFromUser(scanner);
            while (chose < 1 || chose > sites.size()) {
                System.out.println("option is out of bounds, please try again");
                chose = getIntFromUser(scanner);
            }
            int siteID = sites.get(chose - 1).getSiteID();
            try {
                pc.addItem(weight, name, siteID);
                con = false;
            } catch (KeyAlreadyExistsException | NoSuchElementException|SQLException ke) {
                System.out.println(ke.getMessage());
            }
        }
    }

    private void addSite(Scanner scanner) throws ReflectiveOperationException {
        boolean con = true;
        while (con) {
            System.out.print("City name:");
            String city = getStringFromUser(scanner);

            System.out.print("Delivery area ID:");
            int deliveryArea = getIntFromUser(scanner);
            System.out.print("contact Name:");
            String contactName = getStringFromUser(scanner);
            while (!contactName.matches("[A-Z a-z]*")) {
                System.out.println("invalid name has been inserted, please insert again ");
                contactName = getStringFromUser(scanner);
            }
            System.out.print("contact Number:");
            String phoneNumber = getStringFromUser(scanner);
            while (!phoneNumber.matches("[0-9]*")) {
                System.out.println("phone number must be digits only, NOTICE! no signs such as '-' ");
                phoneNumber = getStringFromUser(scanner);
            }
            System.out.print("Site Name:");
            String name = getStringFromUser(scanner);
            try {
                pc.addSite(city, deliveryArea, phoneNumber, contactName, name);
                con = false;
            } catch (KeyAlreadyExistsException|SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }




    private void addNewDriver(Scanner scanner) throws ReflectiveOperationException {

        try {
            System.out.print("ID: ");
            String ID = getStringFromUser(scanner);
            while (!ID.matches("\\d{9}")) {
                System.out.println("id must be exactly 9 digits, insert again");
                ID = getStringFromUser(scanner);
            }

            System.out.print("Driver's name: ");
            String name = getStringFromUser(scanner);
            while (!name.matches("^[A-Z a-z]*")) {
                System.out.println("invalid name has been inserted, please insert again ");
                name = getStringFromUser(scanner);
            }
            Driver.License licenseType = null;
            System.out.println("now choose Driver's License degree:\n1) C1 - more then 12k\n2) C - less then 12k");
            boolean stop = false;
            while (!stop) {
                switch (getIntFromUser(scanner)) {
                    case 1:
                        licenseType = Driver.License.C1;
                        stop = true;
                        break;
                    case 2:
                        licenseType = Driver.License.C;
                        stop = true;
                        break;
                    default:
                        System.out.println("this isn't a legal option, choose again between 1 or 2");

                }
            }

            pc.addDriver(ID, name, licenseType);


        } catch (KeyAlreadyExistsException|SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("");
        }

    }

    private void chooseDemands(Scanner scanner) throws ReflectiveOperationException {
        boolean con = true;
        while (con) {
            try {
                LinkedList<FacadeDemand> demands = pc.showDemands();
                demands = sortDemandsBySite(demands);
                printDemands(demands);
                try {
                    System.out.println("\nif you'd like to finish, insert " + (demands.size() + 1) + " in item number");
                    System.out.println();
                    System.out.print("item number: ");
                    int chose = getIntFromUser(scanner);
                    if (chose == demands.size() + 1) {
                        con = false;
                    } else {
                        while (chose < 1 || chose > demands.size()) {
                            System.out.println("option out of bounds, please choose again");
                            chose = getIntFromUser(scanner);
                        }
                        int itemNumber = demands.get(chose - 1).getItemID();
                        System.out.print("amount: ");
                        int amount = getIntFromUser(scanner);
                        while (amount <= 0) {
                            System.out.println("cannot deliver a non- positive number of items, please try again");
                            amount = getIntFromUser(scanner);
                        }
                        int siteID = demands.get(chose - 1).getSite(); // destination to delivery to
                        con = pc.addDemandToReport(itemNumber, amount, siteID);

                    }
                } catch (IllegalStateException|SQLException e) { // different delivery area
                    System.out.println("you chose different delivery area from the currents," +
                            " would you like to continue? y for yes, n for not");
                    String answer = getStringFromUser(scanner);
                    switch (answer) {
                        case "y":
                            con = false;

                            boolean enough = false;
                            while (!enough) {
                                demands = pc.showDemands();
                                demands = sortDemandsBySite(demands);
                                printDemands(demands);
                                System.out.println("\nif you'd like to finish, insert " + (demands.size() + 1) + " in item number");
                                System.out.println();
                                System.out.print("item number: ");
                                int chose = getIntFromUser(scanner);
                                if (chose == demands.size() + 1) {
                                    enough = true;
                                } else {
                                    while (chose < 1 || chose > demands.size()) {
                                        System.out.println("option out of bounds, please choose again");
                                        chose = getIntFromUser(scanner);
                                    }
                                    int itemNumber = demands.get(chose - 1).getItemID();
                                    System.out.print("amount: ");
                                    int amount = getIntFromUser(scanner);
                                    while (amount <= 0) {
                                        System.out.println("cannot deliver a non- positive number of items, please try again");
                                        amount = getIntFromUser(scanner);
                                    }
                                    int siteID = demands.get(chose - 1).getSite(); // destination to delivery to
                                    try {
                                        pc.continueAddDemandToReport(itemNumber, amount, siteID);
                                    } catch (IllegalArgumentException|SQLException exception) {
                                        System.out.println(exception.getMessage());
                                    }

                                }
                            }
                            break;
                        case "n":


                            break;

                        default:
                            System.out.println("theres no such option, choose between y or n explicit");
                    }

                } catch (IllegalArgumentException e) { // one of arguments doesn't match
                    System.out.println(e.getMessage());
                }


            } catch (NoSuchElementException|SQLException ne) {
                System.out.println(ne.getMessage());
                con = false;
            }
        }
    }

    private void printDemands(List<FacadeDemand> demands) {
        System.out.println("the current demands:");
        int spot = 1;
        for (FacadeDemand fd : demands) {
            String itemName = ts.getItemName(fd.getItemID());
            System.out.println(spot + ")\t" + itemName + ":\titem id: " + fd.getItemID() + "\n" + "\tamount needed:" + fd.getAmount() +
                    "\t\tsupplier: " + ts.getSupplierName(fd.getSupplier()) + "\t\tdelivery area: " + ts.getSiteDeliveryArea(fd.getSupplier())
                    + "\t\titem weight: " + ts.getItemWeight(fd.getItemID()));
            spot++;
        }
    }

    private void chooseTruckAndDriver(Scanner scanner, LocalDate date, LocalTime shift) throws ReflectiveOperationException {
        System.out.println("please choose the Truck you'd like to deliver it with:");
        String truckNumber = chooseTruck(scanner, date, shift);
        try {
            pc.chooseTruck(truckNumber, date, shift);
            String driverID = chooseDriver(scanner, date, shift);
            pc.chooseDriver(driverID, date, shift);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }

 LinkedList<FacadeTruck> trucks =  pc.getAvailableTrucks();
        int weight=pc.getWeightOfCurrReport();
        System.out.println("please notice the truck weight is: "+weight);
        System.out.println("available trucks:");
        int spot =1;
        for (FacadeTruck truck: trucks) {
            System.out.println(spot+")truck LicenseNumber: " + truck.getLicenseNumber() + "\tmax Weight :" + truck.getMaxWeight() + "\tWeight Neto:" + truck.getWeightNeto())  ;
            spot++;
        }
        int chose = getIntFromUser(scanner);
        while (chose<1 ||chose > trucks.size()){
            System.out.println("option out of bounds, please try again");

            chose = getIntFromUser(scanner);
        }

        String truck = trucks.get(chose-1).getLicenseNumber();
        boolean con =true;
        while(con) {
            try {
                FacadeTruck facadeTruck = pc.chooseTruck(truck);
                con = false;
            }catch (NoSuchElementException|IllegalStateException e  ){
                System.out.println(e.getMessage());
                rePlan(scanner);
            }
        }
        System.out.println("now please choose driver:");

        LinkedList<FacadeDriver> drivers = pc.getAvailableDrivers();
        System.out.println("available Drivers:");
        spot =1;
        for ( FacadeDriver driver : drivers) {
            System.out.println(spot+") Driver ID:" + driver.getID() + " License degree: " + driver.getLicenseType() + " =" + driver.getLicenseType().getSize()  )  ;
            spot++;
        }
        chose = getIntFromUser(scanner);
        while (chose<1 || chose>drivers.size()){
            System.out.println("option is out of bounds, please try again");
            chose = getIntFromUser(scanner);
        }

        String driverID = drivers.get(chose-1).getID();
        con =true;
        while(con) {
            try {
                FacadeDriver facadeDriver = pc.chooseDriver(driverID);
                con = false;
            }catch (NoSuchElementException|IllegalStateException e  ){
                System.out.println(e.getMessage());
                rePlan(scanner);
            }
        }



    }






    void updateDeliveryForm(Scanner scanner) throws ReflectiveOperationException {
        LinkedList<FacadeTruckingReport> truckingReports = pc.showTodayReports();
        if (truckingReports.isEmpty()) {
            throw new ReflectiveOperationException("There is no active reports for today to update");
        }
        System.out.println("please choose the report you'd like to advance");
        int spot = 1;
        for (FacadeTruckingReport ftr : truckingReports) {
            System.out.print(spot + ")" + "origin site:" + ftr.getOrigin() + "\ndestinations:\n");
            LinkedList<Integer> destinations = ftr.getSuppliers();
            spot++;
            for (Integer des : destinations) {
                System.out.println("\t" + pc.getSiteName(des));
            }
        }
        int chose = getIntFromUser(scanner);
        while (chose < 1 || chose > truckingReports.size()) {
            System.out.println("option out of bounds, please try again");
            chose = getIntFromUser(scanner);
        }
        FacadeTruckingReport ftr = truckingReports.get(chose - 1);
        LinkedList<FacadeDeliveryForm> deliveryForms = pc.getUnComplitedDeliveryForms(ftr.getID());

        System.out.println("the relevant Delivery Forms: ");
        spot = 1;
        for (FacadeDeliveryForm dlf : deliveryForms) {
            System.out.println(spot + ")origin: " + dlf.getOrigin() + "\tdestination: " + dlf.getSupplier() +
                    "\nitems delivered:");
            spot++;
            for (Map.Entry<Integer, Integer> entry : dlf.getItems().entrySet()) {
                System.out.println("\t" + pc.getItemName(entry.getKey()) + "\tamount: " + entry.getValue());
            }

        }
        System.out.print("Choose delivery form you'd like to update:");
        chose = getIntFromUser(scanner);
        while (chose < 1 || chose > deliveryForms.size()) {
            System.out.print("input is out of bounds, please try again:");
            chose = getIntFromUser(scanner);
        }
        FacadeDeliveryForm fdf = deliveryForms.get(chose - 1);
        System.out.print("Please enter the current truck weight: ");
        int weight = getIntFromUser(scanner);
        try {
            pc.updateDeliveryFormRealWeight(ftr.getID(), fdf.getID(), weight);
        } catch (IllegalStateException| SQLException e) {
            System.out.println(e.getMessage());
            rePlanAfterWeight(scanner, ftr, weight, fdf);
        }

    }

    private void rePlanAfterWeight(Scanner scanner, FacadeTruckingReport tr, int weight, FacadeDeliveryForm fdf) throws ReflectiveOperationException {

        System.out.println("Welcome to replan menu! please choose the option you'd like to re plan the report with:");
        int spot = 1;
        System.out.println(spot + ") remove a site (all the products from this site and to it will be removed)");
        spot++;
        System.out.println(spot + ") switch demands  - removes a site and adds a new demand to add by choose");
        spot++;
        System.out.println(spot + ") change a truck and Driver");
        spot++;
        System.out.println(spot + ") remove item");
        System.out.println("choose different number to quit");
        System.out.print("place your option: ");
        int option = getIntFromUser(scanner);

        switch (option) {
            case 1: {
                removeSiteFromTruckReport(scanner, tr);
                break;
            }


            // remove site and add new items
            case 2: {
                try {
                    removeSiteFromTruckReport(scanner, tr);
                    chooseDemands(scanner);
                    pc.moveDemandsFromCurrentToReport(tr);

                }
                catch (SQLException e)
                {
                 e.getMessage();
                }
                break;
            }


            // replace truck and Driver
            case 3: {
                System.out.println("Please choose Truck and Driver");
                System.out.println("please notice the truck weight is: " + weight);
                String oldD = tr.getDriverID();
                String oldT = tr.getTruckNumber();
                try {

                    pc.deleteDriverConstarint(oldD, tr.getDate(), tr.getLeavingHour());
                    pc.deleteTruckConstarint(oldT, tr.getDate(), tr.getLeavingHour());

                    String truckNumber = chooseTruck(scanner, tr.getDate(), tr.getLeavingHour());
                    String DriverID = chooseDriver(scanner, tr.getDate(), tr.getLeavingHour());
                    pc.replaceTruckAndDriver(truckNumber, DriverID, pc.getCurrentTruckReport(), weight);
                    pc.updateDeliveryFormRealWeight(pc.getCurrTruckReport().getID(), fdf.getID(), weight);
                } catch (IllegalStateException|InputMismatchException|SQLException e) {
                    System.out.println(e.getMessage());
                    pc.addDriverConstraint(oldD, tr.getDate(), tr.getLeavingHour());
                    pc.addTruckConstraint(oldT, tr.getDate(), tr.getLeavingHour());
                }
                break;


            }
            case 4: //remove items
            {
                LinkedList<FacadeDemand> items = pc.getUnCompletedItemOnReportByOld(tr.getID());
                spot = 1;
                for (FacadeDemand item : items) {
                    System.out.println(spot + ") " + pc.getItemName(item.getItemID()) + "\tamount: " + item.getAmount() + "\tdeliver to:" + pc.getSiteName(item.getSite()));
                    spot++;
                }
                int chose = getIntFromUser(scanner);
                while (chose < 1 || chose > items.size()) {
                    chose = getIntFromUser(scanner);
                }

                FacadeDemand demand = items.get(chose - 1);
                try{
                    pc.removeItemFromTruckingReport(tr.getID(), demand);
                }
                catch (SQLException e)
                {
                    System.out.println(e.getMessage());
                }

                break;
            }
            default:
                throw new ReflectiveOperationException("no such option - returning to Trucking menu");
        }


    }

    private String chooseDriver(Scanner scanner, LocalDate date, LocalTime shift) throws ReflectiveOperationException {
        LinkedList<FacadeDriver> drivers = pc.getAvailableDrivers(date, shift);
        if (drivers.isEmpty()){
            throw new ReflectiveOperationException("no are possible for this date.");

        }
        System.out.println("available Drivers:");
        int spot = 1;
        for (FacadeDriver d : drivers) {
            FacadeDriver driver = pc.getDriver(d.getID());
            System.out.print(spot + ".\t");
            printDriverDetails(driver);

            spot++;
        }
        int chose = getIntFromUser(scanner);
        while (chose < 1 || chose > drivers.size()) {
            System.out.println("option is out of bounds, please try again");
            chose = getIntFromUser(scanner);
        }

        return drivers.get(chose - 1).getID();
    }

    private String chooseTruck(Scanner scanner, LocalDate date, LocalTime shift) throws ReflectiveOperationException {
        LinkedList<FacadeTruck> trucks = pc.getAvailableTrucks(date, shift);
        if (trucks == null || trucks.isEmpty())
            throw new ReflectiveOperationException("no trucks available for this shift");
        else {

            System.out.println("available trucks:");
            int spot = 1;
            for (FacadeTruck truck : trucks) {
                System.out.println(spot + ") truck LicenseNumber: " + truck.getLicenseNumber() + " max Weight :" + truck.getMaxWeight());
                spot++;
            }
            int chose = getIntFromUser(scanner);
            while (chose < 1 || chose > trucks.size()) {
                System.out.println("option out of bounds, please try again");

                chose = getIntFromUser(scanner);
            }


            return trucks.get(chose - 1).getLicenseNumber();
        }
    }

    private void removeSiteFromTruckReport(Scanner scanner, FacadeTruckingReport tr) throws ReflectiveOperationException {
        int spot;
        LinkedList<FacadeDeliveryForm> dfs = pc.getUncompletedDeliveryFormsFromOld(tr.getID());
        LinkedList<Integer> sites = new LinkedList<>();
        for (FacadeDeliveryForm df : dfs) {
            if (!(df.isCompleted())) {
                sites.add(df.getSupplier());
            }
        }
        System.out.println("please choose the site you'd like to remove");
        spot = 1;
        for (Integer id : sites) {
            System.out.println(spot + ")" + pc.getSiteName(id));
            spot++;

        }
        int siteID = getIntFromUser(scanner);
        while (siteID < 1 || siteID > sites.size())
            siteID = getIntFromUser(scanner);
        siteID = sites.get((siteID - 1));
        try {
            pc.removeSiteFromTruckReport(siteID, tr.getID());
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }

    public void putInitialTestState() {

        try {

            pc.addTruck("Mercedes", "62321323", 2000, 12000);
            pc.addTruck("Man", "1231231", 1500, 8000);
            pc.addTruck("Volvo", "123", 1000, 10000);
            pc.addTruck("Volvo", "12121", 1000, 14000);

            pc.addSite("Beer-Sheba", 3, "0503123465", "Ido", "Super-Lee");
            pc.addSite("Haifa", 1, "0502008216", "Shimi", "Tnuva-Haifa");
            pc.addSite("Qiryat Shemona", 1, "0503988883", "Ofir", "Tara-North");
            pc.addSite("Tel-Aviv", 2, "0504123465", "Raz", "Meat-Bar");
            pc.addSite("Rahat", 3, "0502008214", "Mohamad", "Wheat-Farmers");
            pc.addSite("Sderot", 3, "0523123465", "Shaked", "Klil");



            pc.addItem(1, "milk", 2);
            pc.addItem(2, "cream cheese", 2);
            pc.addItem(4, "cottage", 3);
            pc.addItem(2, "banana", 5);
            pc.addItem(3, "cucumber", 5);
            pc.addItem(0.1, "chocolate", 6);


            pc.addDemandToSystem(6, 1, 100);
            pc.addDemandToSystem(4, 1, 1000);
            pc.addDemandToSystem(5, 1, 500);
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }





// old methods that may be used in the next projects

private void removeSite(Scanner scanner) throws ReflectiveOperationException {
        boolean con = true;
        while (con) {
            LinkedList<FacadeSite> sites = pc.showCurrentSites();
            System.out.println("\nchoose a site you'd like to remove\n");
            int spot = 1;
            for (FacadeSite site : sites) {
                System.out.println(spot + ")Site Name: " + site.getName() + "\tSite ID: " + site.getSiteID() + "\tSite city: " + site.getCity() +
                        "\tSite: Delivery area: " + site.getDeliveryArea() + "\n products:");
                LinkedList<FacadeDemand> siteDemands = pc.getCurrentDemandsBySite(site);
                for (FacadeDemand demand : siteDemands) {

                    System.out.println(pc.getItemName(demand.getItemID()) + ": " +
                            "item ID: " + demand.getItemID() + "\tamount: " + demand.getAmount());
                }
                spot++;
            }
            int siteID = getIntFromUser(scanner);
            while (siteID < 1 || siteID > sites.size()) {
                System.out.println("option out of bounds, please try again");
                siteID = getIntFromUser(scanner);
            }
            siteID = sites.get(siteID - 1).getSiteID();
            try {
                con = pc.removeDestination(siteID);
                if (pc.showCurrentSites().isEmpty()) {
                    throw new ReflectiveOperationException("no more demands left in this report, aborting..");
                }
            } catch (NoSuchElementException ne) {
                System.out.println(ne.getMessage());

            }

        }
    }



    private void chooseLeavingHour(Scanner scanner) throws ReflectiveOperationException {

        System.out.print("please choose leaving time: \nhour: ");
        int hour = getIntFromUser(scanner);
        while (hour > 23) {
            hour = getIntFromUser(scanner);
        }
        System.out.print("minutes: ");
        int minute = getIntFromUser(scanner);
        while (minute > 59) {
            minute = getIntFromUser(scanner);
        }
        LocalTime time = LocalTime.of(hour, minute);

        pc.chooseLeavingHour(time);
    }




    private void makeDriverAvailable(Scanner scanner) throws ReflectiveOperationException {
        LinkedList<FacadeDriver> drivers = pc.getDrivers();
        if (drivers == null) System.out.println("no drivers in the system yet");
        else {
            System.out.println("drivers:");
            int spot = 1;
            for (FacadeDriver facadeDriver : drivers) {

                System.out.println(spot + ")" + facadeDriver.getName() + ":\n" + "Driver ID: " + facadeDriver.getID() +
                        " License Type: " + facadeDriver.getLicenseType() +
                        " Available: " + facadeDriver.isAvailable());
                spot++;
            }
            System.out.println("Choose Driver");
            int chose = getIntFromUser(scanner);
            while (chose < 1 || chose > spot) {
                System.out.println("Option out of bounds, please choose again");
                chose = getIntFromUser(scanner);
            }
            String driver = drivers.get(chose - 1).getID();
            pc.makeAvailable_Driver(driver);
        }
    }


  private void makeTruckAvailable(Scanner scanner) throws ReflectiveOperationException {
        LinkedList<FacadeTruck> trucks = pc.getTrucks();
        if (trucks == null) System.out.println("no trucks in the system yet");
        else {
            System.out.println("trucks:");
            int spot = 1;
            for (FacadeTruck truck : trucks) {
                System.out.println(spot + ")Trucks License Number: " + truck.getLicenseNumber() +
                        "\n model: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight() + "\tavailable:" + truck.isAvailable());
                spot++;
            }
            System.out.println("Choose truck by License:");
            int chose = getIntFromUser(scanner);
            while (chose < 1 || chose > spot) {
                System.out.println("Option out of bounds, please choose again");
                chose = getIntFromUser(scanner);
            }
            String truck = trucks.get(chose - 1).getLicenseNumber();
            pc.makeAvailable_Truck(truck);
        }
    }

    private void makeDriverUnavailable(Scanner scanner) throws ReflectiveOperationException {
        LinkedList<FacadeDriver> drivers = pc.getAvailableDrivers();
        if (drivers == null) System.out.println("no available drivers");
        else {
            System.out.println("available drivers:");
            int spot = 1;
            for (FacadeDriver facadeDriver : drivers) {
                System.out.println(spot + ")" + facadeDriver.getName() + ":\n" + "Driver ID: " + facadeDriver.getID() +
                        " License Type: " + facadeDriver.getLicenseType() +
                        " Available: " + facadeDriver.isAvailable());
                spot++;
            }
            System.out.println("Choose Driver by id");
            int chose = getIntFromUser(scanner);
            while (chose < 1 || chose > spot) {
                System.out.println("Option out of bounds, please choose again");
                chose = getIntFromUser(scanner);
            }
            String driver = drivers.get(chose - 1).getID();
            try {
                pc.makeUnavailable_Driver(driver);
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void makeTruckUnavailable(Scanner scanner) throws ReflectiveOperationException {
        LinkedList<FacadeTruck> trucks = pc.getAvailableTrucks();
        if (trucks == null) System.out.println("no available trucks");
        else {
            System.out.println("the available trucks:");
            int spot = 1;
            for (FacadeTruck truck : trucks) {
                System.out.println(spot + ")Trucks License Number: " + truck.getLicenseNumber() +
                        "\n model: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight() + "\tavailable:" + truck.isAvailable());
                spot++;
            }
            System.out.println("Choose truck:");
            int chose = getIntFromUser(scanner);
            while (chose < 1 || chose > spot) {
                System.out.println("Option out of bounds, please choose again");
                chose = getIntFromUser(scanner);
            }
            String truck = trucks.get(chose - 1).getLicenseNumber();
            pc.makeUnavailable_Truck(truck);
        }
    }


private FacadeTruckingReport rePlan(Scanner scanner) throws ReflectiveOperationException {

        System.out.println("Welcome to replan menu! please choose the option you'd like to re plan the report with:");
        int spot = 1;
        System.out.println(spot + ") remove a site (all the products from this site and to it will be removed)");
        spot++;
        System.out.println(spot + ") switch demands  - removes a site and adds a new demand to add by choose");
        spot++;
        System.out.println(spot + ") change a truck");
        spot++;
        System.out.println(spot + ") remove item");
        System.out.println("choose different number to quit");
        System.out.print("place your option: ");
        int option = getIntFromUser(scanner);
        switch (option) {
            case 1://remove site

                removeSite(scanner);
                return pc.getCurrTruckReport();

            case 2://switch demand(=site)

                removeSite(scanner);
                chooseDemands(scanner);
                return pc.getCurrTruckReport();

            case 3://replace truck

                System.out.println("choose new truck");
                LinkedList<FacadeTruck> trucks = pc.getAvailableTrucks();
                for (FacadeTruck ft : trucks) {
                    System.out.println("Truck number: " + ft.getLicenseNumber() + "\tWeight neto: " + ft.getWeightNeto()
                            + "\tMax weight: " + ft.getMaxWeight());
                }
                chooseTruckAndDriver(scanner);
                return pc.getCurrTruckReport();

            case 4://remove items

                System.out.println("choose item to remove");
                LinkedList<FacadeDemand> items = pc.getItemsOnTruck();
                int counter = 1;
                for (FacadeDemand demand : items) {
                    System.out.println(counter + ") " +
                            "amount: " + demand.getAmount() + " wight per unit: " + pc.getWeight(demand.getItemID())
                            + "delivery site: " + pc.getSiteName(demand.getSite()));
                }
                System.out.print("your choice: ");
                int itemId = getIntFromUser(scanner);
                while (itemId > items.size() - 1 || itemId < 1) {
                    System.out.println("your option is out of bounds, please choose again");
                    itemId = getIntFromUser(scanner);
                }
                FacadeDemand demand = items.get(itemId - 1);
                System.out.println("amount: ");
                int amount = getIntFromUser(scanner);
                pc.removeItemFromReport(demand, amount);
                return pc.getCurrTruckReport();


            default:
                return null;
        }
    }*/



}
