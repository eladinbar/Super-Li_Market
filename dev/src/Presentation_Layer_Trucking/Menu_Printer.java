package Presentation_Layer_Trucking;

import Business_Layer_Trucking.Facade.FacadeObject.FacadeDemand;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDriver;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeTruck;
import Business_Layer_Trucking.Resources.Driver;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu_Printer {
    PresentationController pc;
    private static Menu_Printer instance =null;

    private Menu_Printer(){pc =  new PresentationController(); }

    public static Menu_Printer getInstance() {
        if (instance == null){
            instance = new Menu_Printer();
        }
        return instance;
    }

    public void startMenu(){
        Scanner scanner =  new Scanner(System.in);
        System.out.println("Welcome to Trucking Menu!\nplease choose the option you'd like:");
        int spot =1;
        System.out.println(spot +". Create new Trucking Report"); spot++;
        System.out.println(spot + ". Show Drivers"); spot++;
        System.out.println(spot + ". Show Trucks"); spot++;
        System.out.println(spot + ". Show Current Demands"); spot++; //4
        System.out.println(spot + ". Add new Driver to the System"); spot++;
        System.out.println(spot + " add new Truck to the System"); spot++;
        System.out.println(spot + ". make truck unavailable"); spot++;
        System.out.println(spot + ". make driver unavailable"); spot++; //8
        System.out.println(spot + ". make truck available"); spot++;
        System.out.println(spot + ". make driver available"); spot++;
        System.out.println(spot + ". go back to main Menu");
        int choose = scanner.nextInt();
        switch (choose){
            case 1:
                chooseDemands(scanner);
                // TODO need to print the weight received
                chooseTruckAndDriver(scanner);
                chooseLeavingHour(scanner);
                pc.saveReport();





            case 2:
                // TODO NTH -  insert time to finish if unavailable
                LinkedList<FacadeDriver> drivers =  pc.getDrivers();
                if (drivers == null) System.out.println("no Drivers in the system yet");
                else {
                    for (FacadeDriver facadeDriver : drivers) {
                        System.out.println(facadeDriver.getName() + ":\n" + "Driver ID: " + facadeDriver.getID() +
                                " License Type: " + facadeDriver.getLicenseType() +
                                " Available: " + facadeDriver.isAvailable());
                    }
                }

            case 3:
                LinkedList<FacadeTruck> trucks = pc.getTrucks();
                for(FacadeTruck truck : trucks){
                    System.out.println("Trucks License Number: " + truck.getLicenseNumber() +
                            "\n model: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight()
                            + "Available: " + truck.isAvailable() );
                }
            case 4:
                List<FacadeDemand>  demands = pc.showDemads();
                if (demands == null) {
                    System.out.println("no demands left to display, Well done Sir!");
                }
                else {
                    demands = sortDemandsBySite(demands);

                    printDemands(demands);
                }

            case 5:
                addNewDriver(scanner);
            case 6:
                addNewTruck(scanner);
            case 7:
                makeTruckUnavailable(scanner);
            case 8:
                makeDriverUnavailable(scanner);
            case 9:
                makeTruckAvailable(scanner);
            case 10:
                makeDriverAvailable(scanner);
            case 11:
                System.out.println("this option isnt supported yet. to be continue");
            default:

        }

    }

    private void makeDriverAvailable(Scanner scanner) {
        LinkedList<FacadeDriver> drivers = pc.getDrivers();
        if (drivers == null) System.out.println("no drivers in the system yet");
        else{
            System.out.println("drivers:");

            for (FacadeDriver facadeDriver : drivers) {
                System.out.println(facadeDriver.getName() + ":\n" + "Driver ID: " + facadeDriver.getID() +
                        " License Type: " + facadeDriver.getLicenseType() +
                        " Available: " + facadeDriver.isAvailable());
            }
            System.out.println("Choose Driver by id");
            int driver=scanner.nextInt();
            pc.makeAvailable_Driver(driver);
        }
    }

    private void makeTruckAvailable(Scanner scanner) {
        LinkedList<FacadeTruck> trucks = pc.getTrucks();
        if (trucks == null) System.out.println("no trucks in the system yet");
        else{
            System.out.println("trucks:");
            for(FacadeTruck truck : trucks){
                System.out.println("Trucks License Number: " + truck.getLicenseNumber() +
                        "\n model: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight()  );
            }
            System.out.println("Choose truck by License:");
            int truck=scanner.nextInt();
            // TODO throws exception for busy truck?
            pc.makeAvailable_Truck(truck) ;
        }
    }

    private void makeDriverUnavailable(Scanner scanner) {
        LinkedList<FacadeDriver> drivers = pc.getAvailableDrivers();
        if (drivers == null) System.out.println("no available drivers");
        else{
            System.out.println("available drivers:");

            for (FacadeDriver facadeDriver : drivers) {
                System.out.println(facadeDriver.getName() + ":\n" + "Driver ID: " + facadeDriver.getID() +
                        " License Type: " + facadeDriver.getLicenseType() +
                        " Available: " + facadeDriver.isAvailable());
            }
            //TODO display only available drivers
            System.out.println("Choose Driver by id");
            int driver=scanner.nextInt();
            pc.makeUnavailable_Driver(driver);
        }
    }

    private void makeTruckUnavailable(Scanner scanner) {
        LinkedList<FacadeTruck> trucks = pc.getAvailableTrucks();
        if (trucks == null) System.out.println("no available trucks");
        else{
            System.out.println("the available trucks:");
            for(FacadeTruck truck : trucks){
                System.out.println("Trucks License Number: " + truck.getLicenseNumber() +
                        "\n model: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight()  );
            }
            System.out.println("Choose truck by License:");
            int truck=scanner.nextInt();
            pc.makeUnavailable_Truck(truck);
        }
    }

    private void addNewTruck(Scanner scanner) {
        System.out.print("Truck License ID: ");
        int licenseNumber  = scanner.nextInt();
        System.out.print("the Trucks model:");
        String model=scanner.next();
        System.out.print("Weight Neto:");
        int weightNeto = scanner.nextInt();
        System.out.print("Max Weight:");
        int maxWeight =scanner.nextInt();


        pc.addTruck(model, licenseNumber,weightNeto,maxWeight);
    }

    private void addNewDriver(Scanner scanner) {
        // TODO this method need to be checked with workers branch, might be more demands
        System.out.print("ID: ");
        int ID = scanner.nextInt();
        System.out.print("Driver's name: ");
        String name = scanner.next();
        // TODO need to check for legal name
        Driver.License licenseType = null;
        System.out.println("now choose Driver's License degree:\n1) C1 - more then 12k\n2) C - less then 12k");
        boolean stop = false;
        while (!stop) {
            switch (scanner.nextInt()) {
                case 1:
                    licenseType = Driver.License.C1;
                    stop = true;
                case 2:
                    licenseType = Driver.License.C;
                    stop = true;
                default:
                    System.out.println("this isn't a legal option, choose again between 1 or 2");

            }

            pc.addDriver(ID, name, licenseType);
        }
    }

    private void chooseLeavingHour(Scanner scanner) {
        System.out.print("please choose leaving time: \n hour: ");
        int hour = scanner.nextInt();
        System.out.print("minutes: ");
        int minute = scanner.nextInt();
        LocalTime time = LocalTime.of (hour ,minute);
        pc.chooseLeavingHour(time);
    }

    private void chooseDemands(Scanner scanner) {
        boolean con = true;
        pc.CreateReport();
        List<FacadeDemand>  demands = pc.showDemads();
        if (demands == null) {
            System.out.println("no demands left to display, Well done Sir!");
        }
        else {
            demands =  sortDemandsBySite(demands);
            while (con) {

                printDemands(demands);
                try {
                    System.out.print("item number: ");
                    int itemNumber = scanner.nextInt();
                    System.out.println();
                    System.out.print("amount: ");
                    int amount = scanner.nextInt();
                    System.out.println();

                    con = pc.addDemandToReport(itemNumber, amount);

                } catch (IllegalStateException e) {
                    System.out.println("you chose different delivery area from the currents," +
                            " would you like to continue? y for yes, n for not");
                    String answer = scanner.nextLine();
                    switch (answer) {
                        case "y":
                            boolean enough = false;
                            while (!enough) {
                                System.out.print("item number: ");
                                int itemNumber = scanner.nextInt();
                                System.out.println();
                                System.out.print("amount: ");
                                int amount = scanner.nextInt();
                                System.out.println();
                                enough = pc.continueAddDemandToReport(itemNumber, amount);
                            }
                        case "n":

                            // TODO need to think where should it get out to
                            pc.closeReport();

                            return;
                        default:
                            System.out.println("theres no such option, choose between y or n explicit");
                    }

                } catch (Exception e) {
                    rePlan();
                }
            }
        }
    }

    private void printDemands(List<FacadeDemand> demands) {
        System.out.println("the current demands:");
        for (FacadeDemand fd : demands) {
            String itemName = pc.getItemName(fd.getItemID());
            System.out.println("item id: " + fd.getItemID() + itemName + " amount needed" + fd.getAmount() +
                    " to " + pc.getSiteName(fd.getSite()) + " item weight: "  + pc.getWeight(fd.getItemID()))   ;
        }
    }

    private void chooseTruckAndDriver(Scanner scanner) {
        System.out.println("please choose the Truck you'd like to deliver it with:");
        LinkedList<FacadeTruck> trucks =  pc.getAvailableTrucks();
        System.out.println("available trucks:");
        for (FacadeTruck truck: trucks) {
            System.out.println("truck LicenseNumber: " + truck.getLicenseNumber() + " max Weight :" + truck.getMaxWeight())  ;
        }
        int truckID = scanner.nextInt();
        FacadeTruck facadeTruck = pc.chooseTruck(truckID);
        System.out.println("now please choose driver:");
        // TODO need to figure how to know the truck weight and throw exception
        // System.out.println("please notice the truck weight is: ");
        LinkedList<FacadeDriver> drivers = pc.getAvailableDrivers();
        System.out.println("available Drivers:");
        for ( FacadeDriver driver : drivers) {
            System.out.println("Driver ID:" + driver.getID() + " License degree: " + driver.getLicenseType() + " =" + driver.getLicenseType().getSize()  )  ;
        }
        int driverID = scanner.nextInt();
        FacadeDriver facadeDriver = pc.chooseDriver(driverID);

    }

    private void rePlan() {
        pc.rePlanning();
    }

    private List<FacadeDemand> sortDemandsBySite(List<FacadeDemand> demands) {
        return null;
    }
}
