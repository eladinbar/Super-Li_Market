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
                        "\n\tmodel: " + truck.getModel() + "\t\tmaxWeight: " + truck.getMaxWeight());

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
        System.out.println("\tTrucking report ID: " + tr.getID() + "\n" + "\tDate: " + tr.getDate() +
                "\n\tLeaving Hour: " + tr.getLeavingHour() + "\n\tstatus: " + rep );

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
        System.out.println("\tname\t\t\tAmount");
        HashMap<Integer, Integer> items = deliveryForm.getItems();
        for (Map.Entry<Integer,Integer> entry: items.entrySet()){
            printItem(entry.getKey(), entry.getValue());
        }
        System.out.println("total Weight: " + deliveryForm.getLeavingWeight());
    }

    private void printItem(Integer item_id, Integer amount) {
        String name = ts.getItemName(item_id);
        System.out.println("\t" + name+"\t\t\t"+amount);
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
        while (chose<1 || chose > reports.size()){
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
}
