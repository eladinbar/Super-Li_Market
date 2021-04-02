package Presentation_Layer_Trucking;

import Business_Layer_Trucking.Facade.FacadeObject.*;
import Business_Layer_Trucking.Resources.Driver;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu_Printer {
    PresentationController pc;
    private static Menu_Printer instance =null;

    private Menu_Printer(){ pc =  PresentationController.getInstance(); }

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
        System.out.println(spot + ". add new site to the System"); spot ++;
        System.out.println(spot + ". add new item to the System"); spot ++;//8
        System.out.println(spot + ". make truck unavailable"); spot++;
        System.out.println(spot + ". make driver unavailable"); spot++;
        System.out.println(spot + ". make truck available"); spot++;
        System.out.println(spot + ". make driver available"); spot++;// 12
        System.out.println(spot + ". go back to main Menu");
        // TODO remove item/ site and whatever methods


        int choose = scanner.nextInt();
        switch (choose){
            case 1:
                pc.CreateReport();
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
                List<FacadeDemand>  demands = pc.showDemands();
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
                addSite(scanner);
            case 8:
                addItem(scanner);
            case 9:
                makeTruckUnavailable(scanner);
            case 10:
                makeDriverUnavailable(scanner);
            case 11:
                makeTruckAvailable(scanner);
            case 12:
                makeDriverAvailable(scanner);
            case 13:
                System.out.println("this option isnt supported yet. to be continue");
            default:

        }

    }

    private void addItem(Scanner scanner) {
        boolean con = true;
        while (con){
            System.out.print("Item id: ");
            int id = scanner.nextInt();
            System.out.print("item weight: ");
            int weight = scanner.nextInt();
            System.out.println("item name: ");
            String name = scanner.nextLine();
            try {
                pc.addItem(id, weight, name);
                con = false;
            }
            catch (KeyAlreadyExistsException ke){
                System.out.println(ke.getMessage());
            }
        }
    }

    private void addSite(Scanner scanner) {
        boolean con = true;
        while(con) {
            System.out.print("City name:");
            String city = scanner.nextLine();
            // TODO maybe site Id ours
            System.out.print("siteID:");
            int siteID = scanner.nextInt();
            System.out.print("Delivery area ID:");
            int deliveryArea = scanner.nextInt();
            System.out.print("contact Name:");
            String contactName = scanner.nextLine();
            System.out.println("contact Number:");
            String phoneNumber = scanner.nextLine();
            try {
                pc.addSite(city, siteID, deliveryArea, phoneNumber, contactName);
                con = false;
            } catch (KeyAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
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
        boolean con = true;
        while (con) {
            System.out.print("Truck License ID: ");
            int licenseNumber = scanner.nextInt();
            System.out.print("the Trucks model:");
            String model = scanner.next();
            System.out.print("Weight Neto:");
            int weightNeto = scanner.nextInt();
            System.out.print("Max Weight:");
            int maxWeight = scanner.nextInt();

            try {
                pc.addTruck(model, licenseNumber, weightNeto, maxWeight);
                con = false;
            } catch (KeyAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
        }
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
            boolean con = true;
            while(con) {
                try {
                    pc.addDriver(ID, name, licenseType);
                    con = false;
                } catch (KeyAlreadyExistsException e) {
                    System.out.println(e.getMessage());

                }
            }
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
        while (con) {
            List<FacadeDemand>  demands = pc.showDemands();
            if (demands == null) {
                System.out.println("no demands left to display, Well done Sir!");
            }
            else {
                demands =  sortDemandsBySite(demands);
                printDemands(demands);
                try {
                    // TODO need to check how to finish
                    System.out.println("if you'de like to finish, insert -1 in item number");
                    System.out.print("item number: ");
                    int itemNumber = scanner.nextInt();
                    System.out.println();
                    System.out.print("amount: ");
                    int amount = scanner.nextInt();
                    System.out.println();

                    System.out.println("site: ");
                    int siteID = scanner.nextInt();

                    con = pc.addDemandToReport(itemNumber, amount,siteID);

                } catch (IllegalStateException e) {
                    con = false;
                    System.out.println("you chose different delivery area from the currents," +
                            " would you like to continue? y for yes, n for not");
                    String answer = scanner.nextLine();
                    switch (answer) {
                        case "y":
                            boolean enough = false;
                            while (!enough) {
                                demands = pc.showDemands();
                                if (demands == null) {
                                    System.out.println("no demands left to display, Well done Sir!");
                                }
                                else {
                                    demands =  sortDemandsBySite(demands);
                                    printDemands(demands);
                                    System.out.print("item number: ");
                                    int itemNumber = scanner.nextInt();
                                    System.out.println();
                                    System.out.print("amount: ");
                                    int amount = scanner.nextInt();
                                    System.out.println();
                                    System.out.println("site id:");
                                    int siteID = scanner.nextInt();
                                    enough = pc.continueAddDemandToReport(itemNumber, amount, siteID);
                                }
                            }
                        case "n":

                            // TODO need to think where should it get out to
                            pc.closeReport();


                            return;
                        default:
                            System.out.println("theres no such option, choose between y or n explicit");
                    }

                } catch (Exception e) {
                    rePlan(scanner);
                }
            }
        }
    }

    private void printDemands(List<FacadeDemand> demands) {
        System.out.println("the current demands:");
        for (FacadeDemand fd : demands) {
            String itemName = pc.getItemName(fd.getItemID());
            System.out.println( "item id: " + fd.getItemID() + itemName + " amount needed" + fd.getAmount() +
                    " to " + pc.getSiteName(fd.getSite()) + " site id: " + fd.getSite()
                    + " item weight: "  + pc.getWeight(fd.getItemID()))   ;
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

    private FacadeTruckingReport rePlan(Scanner scanner) {
        //pc.rePlanning();
        System.out.println("Welcome to replan menu! please choose the option you'd like to re plan the report with:");
        int spot =1;
        System.out.println(spot + ") remove a site (all the products from this site and to it will be removed)");spot++;
        System.out.println(spot + ") switch demands  - removes a site and adds a new demand to add by choose"); spot++;
        System.out.println(spot + ") change a truck");spot++;
        System.out.println(spot + ") remove item");
        System.out.println("choose different number to quit");
        System.out.print("place your option");
        int option = scanner.nextInt();
        switch (option){
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
                // TODO driver exchange might be needed too
                chooseTruckAndDriver(scanner);
                return pc.getCurrTruckReport();

            case 4://remove items

                System.out.println("choose item to remove");
                LinkedList<FacadeDemand> items = pc.getItemsOnTruck();
                for(FacadeDemand demand : items){
                    System.out.println(pc.getItemName(demand.getItemID()) + ") " +
                            "amount: " + demand.getAmount() + " wight per unit: " + pc.getWeight(demand.getItemID())
                            + "delivery site: " + pc.getSiteName( demand.getSite() )) ;
                }
                System.out.print("place the item id here: ");
                int itemId = scanner.nextInt();
                pc.removeItemFromReport(itemId);
                return pc.getCurrTruckReport();


            default:
                return null;
        }
    }

    private List<FacadeDemand> sortDemandsBySite(List<FacadeDemand> demands) {
        return pc.sortDemandsBySite(demands);
    }

    private void removeSite(Scanner scanner){
        boolean con = true;
        while(con) {
            LinkedList<FacadeSite> sites = pc.showCurrentSites();
            System.out.println("choose a site you'd like to remove");
            for (FacadeSite site : sites) {
                System.out.println("Site ID: " + site.getSiteID() + "Site city: " + site.getCity() +
                        "Site: Delivery area: " + site.getDeliveryArea() + "\n products:");
                LinkedList<FacadeDemand> siteDemands = pc.getCurrentDemandsBySite(site);
                // TODO maybe need to show weight aswell
                for (FacadeDemand demand : siteDemands) {

                    System.out.println(pc.getItemName(demand.getItemID()) + ": " +
                            "item ID:" + demand.getItemID() + "amount: " + demand.getAmount());
                }
            }
            int siteID = scanner.nextInt();
            con = pc.removeDestination(siteID);
            if (!con) System.out.println("the chosen site id doesnt exist here!");
        }
    }
}
