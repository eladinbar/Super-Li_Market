package Presentation_Layer_Trucking;

import Business_Layer_Trucking.Delivery.TruckingReport;
import Business_Layer_Trucking.Facade.FacadeObject.*;
import Business_Layer_Trucking.Resources.Driver;
import jdk.jshell.spi.ExecutionControl;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.NotActiveException;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ExecutionException;

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
        scanner.useDelimiter("\n");
        boolean keepGoing= true;
        while (keepGoing) {
            System.out.println("\n\nWelcome to Trucking Menu!\nplease choose the option you'd like:");
            int spot = 1;
            System.out.println(spot + ".\tCreate new Trucking Report");spot++;
            System.out.println(spot + ".\tShow Drivers"); spot++;
            System.out.println(spot + ".\tShow Trucks");spot++;
            System.out.println(spot + ".\tShow Current Demands");spot++; //4
            System.out.println(spot + ".\tAdd new Driver to the System");spot++;
            System.out.println(spot + "\tAdd new Truck to the System");spot++;
            System.out.println(spot + ".\tAdd new site to the System");spot++;
            System.out.println(spot + ".\tAdd new item to the System");spot++;//8
            System.out.println(spot + ".\tAdd new Demand to the System");spot++;
            System.out.println(spot + ".\tMake truck unavailable");spot++;
            System.out.println(spot + ".\tMake driver unavailable");spot++;
            System.out.println(spot + ".\tMake truck available");spot++;//12
            System.out.println(spot + ".\tMake driver available");spot++;
            System.out.println(spot + ".\tRemove item from the Pool"); spot++;
            System.out.println(spot + ".\tGo back to Main Menu");


            // TODO remove item/ site and whatever methods

            int choose =0;

            choose = getIntFromUserMain(scanner);
            switch (choose) {

                case 1:
                    // TODO exception need to stop running and not insert into next func
                    try {
                        pc.CreateReport();
                        //chooseOrigin(scanner);
                        chooseDemands(scanner);
                        System.out.println("Total demands Weight is: " + pc.getWeightOfCurrReport());
                        chooseTruckAndDriver(scanner);
                        chooseLeavingHour(scanner);
                        pc.saveReport();
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}

                    break;


                case 2:
                    // TODO NTH -  insert time to finish if unavailable
                    LinkedList<FacadeDriver> drivers = pc.getDrivers();
                    if (drivers == null) System.out.println("no Drivers in the system yet");
                    else {
                        for (FacadeDriver facadeDriver : drivers) {
                            System.out.println(facadeDriver.getName() + ":\n" + "Driver ID: " + facadeDriver.getID() +
                                    " License Type: " + facadeDriver.getLicenseType() +
                                    " Available: " + facadeDriver.isAvailable());
                        }
                    }
                    break;

                case 3:
                    LinkedList<FacadeTruck> trucks = pc.getTrucks();
                    for (FacadeTruck truck : trucks) {
                        System.out.println("Trucks License Number: " + truck.getLicenseNumber() +
                                "\nmodel: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight()
                                + " Available: " + truck.isAvailable());
                    }
                    break;
                case 4:
                    try {
                        LinkedList<FacadeDemand> demands = pc.showDemands();
                        demands = sortDemandsBySite(demands);

                        printDemands(demands);

                    } catch (NoSuchElementException ne) {
                        System.out.println(ne.getMessage());
                    }

                    break;

                case 5:
                    try {
                        addNewDriver(scanner);

                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}

                    break;
                case 6:
                    try{
                        addNewTruck(scanner);

                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;
                case 7:
                    try{
                        addSite(scanner);
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;
                case 8:
                    try{
                        addItem(scanner);
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;

                case 9:
                    try{
                        addDemandToSystem(scanner);
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;
                case 10:
                    try{
                        makeTruckUnavailable(scanner);
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;
                case 11:
                    try{
                        makeDriverUnavailable(scanner);
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;
                case 12:
                    try{
                        makeTruckAvailable(scanner);
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;
                case 13:
                    try{
                        makeDriverAvailable(scanner);
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;
                case 14:
                    try{
                        removeItemFromPool(scanner);
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;
                case 15:

                    System.out.println("this option isn't supported yet. to be continue");
                    keepGoing = false;

                    break;
                default:

            }
        }
    }



    private void addDemandToSystem(Scanner scanner) throws ReflectiveOperationException {
        int itemId;
        int site;
        int amount;
        System.out.println("Please choose the item you'd like to delivery:");
        try {
            LinkedList<FacadeItem> items = pc.getAllItems();
            for (FacadeItem item : items)
                System.out.println("item id: " +item.getID() +  "\tname: "+ item.getName());



            itemId = getIntFromUser(scanner);

            try{
                LinkedList<FacadeSite> sites = pc.getAllSites();
                System.out.println("please choose the site you'd like to Deliver to");
                // TODO NTH order by site number
                for (FacadeSite s: sites){
                    System.out.println(s.getName()+":\tsiteID: "+s.getSiteID() + "\tcity: " + s.getCity() + "\t\tin Delivery Area: "  + s.getDeliveryArea());
                }
                site = getIntFromUser(scanner);
                try{
                    System.out.print("please choose the amount you'd like to Deliver:");
                    amount= getIntFromUser(scanner);
                    pc.addDemandToSystem(itemId, site, amount);
                }
                catch (NoSuchElementException ne){
                    System.out.println(ne.getMessage());
                }

            }catch (NoSuchElementException ne){
                System.out.println(ne.getMessage());
            }


        }catch (NoSuchElementException ne){
            System.out.println(ne.getMessage());
        }
    }

    private void removeItemFromPool(Scanner scanner) throws ReflectiveOperationException {
        LinkedList <FacadeItem> items = pc.getAllItems();
        int counter =1;
        for (FacadeItem item: items){
            System.out.println(counter + ")\tItem Name: " +item.getName() + "\tItem id: " +item.getID());
            counter ++;
        }
        System.out.print("choose the item you'd to remove from pool:");
        int choice = getIntFromUser(scanner);
        if (choice < 1 ||  choice >  items.size() + 1){
            System.out.println("option is out of bounds, going back to menu");
        }
        else {
            pc.RemoveItemFromPool(items.get(choice).getID());
        }
    }

    private void addItem(Scanner scanner) throws ReflectiveOperationException{
        boolean con = true;
        while (con){
            System.out.print("Item id: ");
            int id =  getIntFromUser(scanner);
            System.out.print("item weight: ");
            int weight =  getIntFromUser(scanner);
            System.out.print("item name: ");
            String name = getStringFromUser(scanner);
            while (!name.matches("[A-Z][a-z]")){
                System.out.println("invalid name instered, please reWrite it");
                name = getStringFromUser(scanner);
            }
            try {
                pc.addItem(id, weight, name);
                con = false;
            }
            catch (KeyAlreadyExistsException ke){
                System.out.println(ke.getMessage());
            }
        }
    }

    private void addSite(Scanner scanner) throws ReflectiveOperationException{
        boolean con = true;
        while(con) {
            // TODO should we check for valid city?
            System.out.print("City name:");
            String city = getStringFromUser(scanner);
            // TODO maybe site Id ours
            System.out.print("siteID:");
            int siteID =  getIntFromUser(scanner);
            System.out.print("Delivery area ID:");
            int deliveryArea =  getIntFromUser(scanner);
            System.out.print("contact Name:");
            String contactName = getStringFromUser(scanner);
            while (!contactName.matches("[A-Z a-z]*")){
                System.out.println("invalid name has been inserted, please insert again ");
                contactName = getStringFromUser(scanner);
            }
            System.out.print("contact Number:");
            String phoneNumber = getStringFromUser(scanner);
            while (!phoneNumber.matches("[0-9]*")){
                System.out.println("phone number must be digits only, NOTICE! no signs such as '-' ");
                phoneNumber = getStringFromUser(scanner);
            }
            System.out.print("Site Name:");
            String name=getStringFromUser(scanner);
            try {
                pc.addSite(city, siteID, deliveryArea, phoneNumber, contactName,name);
                con = false;
            } catch (KeyAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void makeDriverAvailable(Scanner scanner) throws ReflectiveOperationException {
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
            int driver= getIntFromUser(scanner);
            pc.makeAvailable_Driver(driver);
        }
    }

    private void makeTruckAvailable(Scanner scanner) throws ReflectiveOperationException {
        LinkedList<FacadeTruck> trucks = pc.getTrucks();
        if (trucks == null) System.out.println("no trucks in the system yet");
        else{
            System.out.println("trucks:");
            for(FacadeTruck truck : trucks){
                System.out.println("Trucks License Number: " + truck.getLicenseNumber() +
                        "\n model: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight()  );
            }
            System.out.println("Choose truck by License:");
            int truck= getIntFromUser(scanner);
            // TODO throws exception for busy truck?
            pc.makeAvailable_Truck(truck) ;
        }
    }

    private void makeDriverUnavailable(Scanner scanner) throws ReflectiveOperationException{
        LinkedList<FacadeDriver> drivers = pc.getAvailableDrivers();
        if (drivers == null) System.out.println("no available drivers");
        else{
            System.out.println("available drivers:");

            for (FacadeDriver facadeDriver : drivers) {
                System.out.println(facadeDriver.getName() + ":\n" + "Driver ID: " + facadeDriver.getID() +
                        " License Type: " + facadeDriver.getLicenseType() +
                        " Available: " + facadeDriver.isAvailable());
            }
            System.out.println("Choose Driver by id");
            int driver= getIntFromUser(scanner);
            try {
                pc.makeUnavailable_Driver(driver);
            }catch (NoSuchElementException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void makeTruckUnavailable(Scanner scanner) throws  ReflectiveOperationException{
        LinkedList<FacadeTruck> trucks = pc.getAvailableTrucks();
        if (trucks == null) System.out.println("no available trucks");
        else{
            System.out.println("the available trucks:");
            for(FacadeTruck truck : trucks){
                System.out.println("Trucks License Number: " + truck.getLicenseNumber() +
                        "\n model: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight()  );
            }
            System.out.println("Choose truck by License:");
            int truck= getIntFromUser(scanner);
            pc.makeUnavailable_Truck(truck);
        }
    }

    private void addNewTruck(Scanner scanner) throws ReflectiveOperationException{
        boolean con = true;
        while (con) {
            System.out.print("Truck License ID: ");
            String licenseNumber =  getStringFromUser(scanner);
            while (!licenseNumber.matches("[0-9]*"))
            {
                System.out.println("License number should only contain digits,please try again");
                licenseNumber = getStringFromUser(scanner);
            }
            System.out.print("the Trucks model:");
            String model =getStringFromUser(scanner);
            System.out.print("Weight Neto:");
            int weightNeto =  getIntFromUser(scanner);
            System.out.print("Max Weight:");
            int maxWeight =  getIntFromUser(scanner);
            while (maxWeight <= weightNeto){
                System.out.println("the  truck's max weight is lower then it's neto weight("+weightNeto+"), please choose again or quit with -1");
                maxWeight = getIntFromUser(scanner);
            }

            try {
                pc.addTruck(model, licenseNumber, weightNeto, maxWeight);
                con = false;
            } catch (KeyAlreadyExistsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void addNewDriver(Scanner scanner) throws ReflectiveOperationException{
        // TODO this method need to be checked with workers branch, might be more demands

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
            // TODO need to check for legal name
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


        } catch (KeyAlreadyExistsException e) {
            System.out.println(e.getMessage());
            System.out.println("");
        }

    }

    private void chooseLeavingHour(Scanner scanner) throws ReflectiveOperationException {
        boolean con =  true;
        while (con) {
            System.out.print("please choose leaving time: \n hour: ");
            int hour =  getIntFromUser(scanner);
            System.out.print("minutes: ");
            int minute =  getIntFromUser(scanner);
            LocalTime time = LocalTime.of(hour, minute);
            try {
                pc.chooseLeavingHour(time);
                con = false;
            } catch (IllegalArgumentException ie) {
                System.out.println(ie.getMessage());
                System.out.println("please choose again;");

            }
        }
    }

    private void chooseDemands(Scanner scanner) throws ReflectiveOperationException {
        boolean con = true;
        while (con) {
            try {
                LinkedList<FacadeDemand> demands = pc.showDemands();
                if (demands == null) {
                    System.out.println("no demands left to display, Well done Sir!");
                } else {
                    demands = sortDemandsBySite(demands);
                    printDemands(demands);
                    try {// TODO need to fix the -1
                        System.out.println("\nif you'de like to finish, insert 0 in item number");
                        System.out.println();
                        System.out.print("item number: ");
                        int itemNumber =getIntFromUser(scanner);
                        while(itemNumber <1 || itemNumber > demands.size()){
                            System.out.println("option out of bounds, please choose again");
                            itemNumber =  getIntFromUser(scanner);
                        }
                        itemNumber = demands.get(itemNumber-1).getItemID();
                        System.out.print("amount: ");
                        int amount =  getIntFromUser(scanner);

                        System.out.print("site: ");
                        int siteID =  getIntFromUser(scanner);
                        // TODO need to throw more excptions for such as incompatible item id
                        con = pc.addDemandToReport(itemNumber, amount, siteID);

                    } catch (IllegalStateException e) {
                        con = false;
                        System.out.println("you chose different delivery area from the currents," +
                                " would you like to continue? y for yes, n for not");
                        String answer = getStringFromUser(scanner);
                        switch (answer) {
                            case "y":
                                boolean enough = false;
                                while (!enough) {
                                    demands = pc.showDemands();
                                    if (demands == null) {
                                        System.out.println("no demands left to display, Well done Sir!");
                                    } else {
                                        demands = sortDemandsBySite(demands);
                                        printDemands(demands);
                                        System.out.print("item number: ");
                                        int itemNumber =  getIntFromUser(scanner);
                                        System.out.println();
                                        System.out.print("amount: ");
                                        int amount =  getIntFromUser(scanner);
                                        System.out.println();
                                        System.out.println("site id:");
                                        int siteID =  getIntFromUser(scanner);
                                        enough = pc.continueAddDemandToReport(itemNumber, amount, siteID);
                                    }
                                }
                                break;
                            case "n":


                                return;

                            default:
                                System.out.println("theres no such option, choose between y or n explicit");
                        }

                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                    catch (Exception e) {
                        e.getMessage();
                    }
                }
            }catch (NoSuchElementException ne) {
                System.out.println(ne.getMessage());
                con = false;
            }
        }
    }

    private void printDemands(List<FacadeDemand> demands) {
        System.out.println("the current demands:");
        int spot = 1;
        for (FacadeDemand fd : demands) {
            String itemName = pc.getItemName(fd.getItemID());
            System.out.println(spot + ")\t" +  itemName + ":\titem id: " +  fd.getItemID() +"\n"+"\tamount needed:" + fd.getAmount() +
                    "\t\tto: " + pc.getSiteName(fd.getSite()) + "\t\tsite id: " + fd.getSite()
                    + "\t\titem weight: "  + pc.getWeight(fd.getItemID()))   ;spot++;
        }
    }

    private void chooseTruckAndDriver(Scanner scanner) throws ReflectiveOperationException {
        System.out.println("please choose the Truck you'd like to deliver it with:");
        LinkedList<FacadeTruck> trucks =  pc.getAvailableTrucks();
        int weight=pc.getWeightOfCurrReport();
        System.out.println("please notice the truck weight is: "+weight);
        System.out.println("available trucks:");
        for (FacadeTruck truck: trucks) {
            System.out.println("truck LicenseNumber: " + truck.getLicenseNumber() + " max Weight :" + truck.getMaxWeight())  ;
        }
        int truckID =  getIntFromUser(scanner);
        boolean con =true;
        while(con) {
            try {
                FacadeTruck facadeTruck = pc.chooseTruck(truckID);
                con = false;
            }catch (NoSuchElementException|IllegalStateException e  ){
                System.out.println(e.getMessage());
                rePlan(scanner);
            }
        }
        System.out.println("now please choose driver:");
        // TODO need to figure how to know the truck weight and throw exception

        LinkedList<FacadeDriver> drivers = pc.getAvailableDrivers();
        System.out.println("available Drivers:");
        for ( FacadeDriver driver : drivers) {
            System.out.println("Driver ID:" + driver.getID() + " License degree: " + driver.getLicenseType() + " =" + driver.getLicenseType().getSize()  )  ;
        }
        int driverID =  getIntFromUser(scanner);
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

    private FacadeTruckingReport rePlan(Scanner scanner) throws ReflectiveOperationException {

        System.out.println("Welcome to replan menu! please choose the option you'd like to re plan the report with:");
        int spot =1;
        System.out.println(spot + ") remove a site (all the products from this site and to it will be removed)");spot++;
        System.out.println(spot + ") switch demands  - removes a site and adds a new demand to add by choose"); spot++;
        System.out.println(spot + ") change a truck");spot++;
        System.out.println(spot + ") remove item");
        System.out.println("choose different number to quit");
        System.out.print("place your option");
        int option =  getIntFromUser(scanner);
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
                //TODO need to display trucks
                for (FacadeTruck ft:trucks)
                {
                    System.out.println("Truck number: "+ft.getLicenseNumber()+" , Weight neto: "+ft.getWeightNeto()
                            +" , Max weight: "+ft.getMaxWeight());
                }
                chooseTruckAndDriver(scanner);
                return pc.getCurrTruckReport();

            case 4://remove items

                System.out.println("choose item to remove");
                LinkedList<FacadeDemand> items = pc.getItemsOnTruck();
                int counter =1;
                for(FacadeDemand demand : items){
                    System.out.println(counter + ") " +
                            "amount: " + demand.getAmount() + " wight per unit: " + pc.getWeight(demand.getItemID())
                            + "delivery site: " + pc.getSiteName( demand.getSite() )) ;
                }
                System.out.print("your choice: ");
                int itemId =  getIntFromUser(scanner);
                while ( itemId > items.size() -1 || itemId<1) {
                    System.out.println("your option is out of bounds, please choose again");
                    itemId =  getIntFromUser(scanner);
                }
                FacadeDemand demand = items.get(itemId -1);
                System.out.println("amount: ");
                int amount =  getIntFromUser(scanner);
                pc.removeItemFromReport(demand , amount);
                return pc.getCurrTruckReport();


            default:
                return null;
        }
    }

    private LinkedList<FacadeDemand> sortDemandsBySite(LinkedList<FacadeDemand> demands) {
        return pc.sortDemandsBySite(demands);
    }

    private void removeSite(Scanner scanner) throws ReflectiveOperationException{
        boolean con = true;
        while(con) {
            LinkedList<FacadeSite> sites = pc.showCurrentSites();
            System.out.println("choose a site you'd like to remove");
            for (FacadeSite site : sites) {
                System.out.println("Site ID: " + site.getSiteID() + "Site city: " + site.getCity() +
                        "Site: Delivery area: " + site.getDeliveryArea() + "\n products:");
                LinkedList<FacadeDemand> siteDemands = pc.getCurrentDemandsBySite(site);
                // TODO maybe need to show weight as well
                for (FacadeDemand demand : siteDemands) {

                    System.out.println(pc.getItemName(demand.getItemID()) + ": " +
                            "item ID:" + demand.getItemID() + "amount: " + demand.getAmount());
                }
            }
            int siteID =  getIntFromUser(scanner);
            con = pc.removeDestination(siteID);
            if (!con) System.out.println("the chosen site id doesn't exist here!");
        }
    }

    void updateDeliveryForm(Scanner scanner) throws ReflectiveOperationException{
        LinkedList<FacadeTruckingReport> truckingReports =  pc.getActiveTruckingReports();
        System.out.println("please choose the report you'd like to advance");
        int spot = 1;
        for(FacadeTruckingReport ftr: truckingReports){
            System.out.print(spot+")" + "origin site:"+ftr.getOrigin() + "\ndestinations:\n" );
            LinkedList<Integer> destinations =  ftr.getDestinations();
            spot ++;
            for (Integer des: destinations){
                System.out.println("\t"+pc.getSiteName(des));
            }
        }
        int chose = getIntFromUser(scanner);
        while(chose<1 || chose>truckingReports.size())
            chose = getIntFromUser(scanner);
        FacadeTruckingReport ftr = truckingReports.get(chose-1);
        LinkedList<FacadeDeliveryForm> deliveryForms =  pc.getDeliveryForms(ftr.getID());
        spot =1;
        for (FacadeDeliveryForm dlf: deliveryForms){
            System.out.println(spot+")origin:"+ dlf.getOrigin() + "\tdestination" + dlf.getDestination() +
                    "\nitems delivered:" );
            spot++;
            for (Map.Entry<Integer,Integer> entry: dlf.getItems().entrySet()){
                System.out.println("\t" + pc.getItemName(entry.getKey()) + "\tamount: " +entry.getValue());
            }

        }
        chose = getIntFromUser(scanner);
        while(chose<1 || chose>deliveryForms.size() )
            chose = getIntFromUser(scanner);
        FacadeDeliveryForm fdf = deliveryForms.get(chose);

        int weight = getIntFromUser(scanner);
        try {
            pc.updateDeliveryFormRealWeight(ftr.getID(), fdf.getID(), weight);
        }catch (IllegalStateException illegalStateException){
            System.out.println(illegalStateException.getMessage());
            rePlanAfterWeight(scanner,ftr.getID());
            updateDeliveryForm(scanner);
        }

    }

    private void rePlanAfterWeight(Scanner scanner, int trID) throws ReflectiveOperationException {

        System.out.println("Welcome to replan menu! please choose the option you'd like to re plan the report with:");
        int spot =1;
        System.out.println(spot + ") remove a site (all the products from this site and to it will be removed)");spot++;
        System.out.println(spot + ") switch demands  - removes a site and adds a new demand to add by choose"); spot++;
        System.out.println(spot + ") change a truck");spot++;
        System.out.println(spot + ") remove item");
        System.out.println("choose different number to quit");
        System.out.print("place your option");


        /*int option =  getIntFromUser(scanner);
        switch (option){
            case 1://remove site

                removeSite(scanner , trID);


            case 2://switch demand(=site)

                removeSite(scanner, trID);
                chooseDemands(scanner);



            case 3://replace truck

                System.out.println("choose new truck");
                LinkedList<FacadeTruck> trucks = pc.getAvailableTrucks();
                spot =1;
                //TODO need to display trucks
                for (FacadeTruck ft:trucks)
                {
                    System.out.println(spot+")Truck number: "+ft.getLicenseNumber()+" , Weight neto: "+ft.getWeightNeto()
                            +" , Max weight: "+ft.getMaxWeight());spot++;
                }
                int chose = getIntFromUser(scanner);
                while(chose<1 || chose>trucks.size()){
                    chose = getIntFromUser(scanner);
                }
                FacadeTruck facadeTruck = trucks.get(chose);
                try {
                    pc.replaceTruck(facadeTruck.getLicenseNumber(), trID);
                }catch (IllegalStateException illegalStateException){
                    illegalStateException.getMessage();
                    // TODO replace driver
                }

            case 4://remove items

                System.out.println("choose item to remove");

                LinkedList<FacadeTruckingReport> reports = pc.getActiveTruckingReports();
                FacadeTruckingReport truckingReport = null;
                for (FacadeTruckingReport ftr: reports){
                    if (ftr.getID() == trID)
                        truckingReport = ftr;
                }
                LinkedList<FacadeDeliveryForm> fdf = pc.getDeliveryForms(trID);
                int counter =1;
                for(FacadeDemand demand : items){
                    System.out.println(counter + ") " +
                            "amount: " + demand.getAmount() + " wight per unit: " + pc.getWeight(demand.getItemID())
                            + "delivery site: " + pc.getSiteName( demand.getSite() )) ;
                }
                System.out.print("your choice: ");
                int itemId =  getIntFromUser(scanner);
                while ( itemId > items.size() -1 || itemId<1) {
                    System.out.println("your option is out of bounds, please choose again");
                    itemId =  getIntFromUser(scanner);
                }
                FacadeDemand demand = items.get(itemId -1);
                System.out.println("amount: ");
                int amount =  getIntFromUser(scanner);
                pc.removeItemFromReport(demand , amount);


            default:
                return null;
        }*/
    }

    public void putInitialTestState(){
        pc.addDriver("203834734","Ido" ,Driver.License.C1);
        pc.addDriver("123456789", "Shir" ,Driver.License.C);
        pc.addDriver("987654321", "Ofir" , Driver.License.C);

        pc.addTruck("Mercedes" , "62321323" , 2000, 12000);
        pc.addTruck("Man", "1231231", 1500, 8000);
        pc.addTruck("Volvo","123" ,1000, 10000);

        pc.addSite("Haifa", 2,1 , "0502008216" , "Shimi", "SuperLee-Haifa");
        pc.addSite("Beer Sheva" ,3, 3,"0502008217" , "Yotam" , "superLee-BeerSheva");
        pc.addSite("Rahat" , 4 , 3 , "0502008214" , "Mohamad" , "MilkHere");
        pc.addSite("Nazareth" , 5,1,"0522002123" , "Esti" , "Suber-LNazerath");
        pc.addSite("Afula", 1,1,"0502008215" , "Raz" , "Tnuva");
        pc.addItem(1,1,"milk");
        pc.addItem(2 , 2 , "cream cheese");
        pc.addItem(10 , 4 , "cottage" );
        pc.addItem(11 , 2 , "banana");
        pc.addItem(13 , 3 , "cucumber");
        pc.addDemandToSystem(1, 1, 100);
        pc.addDemandToSystem(2,2,1000);
        pc.addDemandToSystem(11, 4,500);
    }

    private int getIntFromUserMain(Scanner scanner){
        int choose = -1;
        boolean scannerCon = true;
        while(scannerCon)
            try {
                choose = scanner.nextInt();

                if (choose < 0){
                    System.out.println("you must choose an none-negative number ");
                }
                scannerCon =false;
            } catch (InputMismatchException ie){
                System.out.println("wrong input please try again");
                scanner.nextLine();
            } catch (NoSuchElementException|IllegalStateException e){
                System.out.println("wrong input please try again");
                scanner.nextLine();
            }
        return choose;

    }
    private int getIntFromUser(Scanner scanner) throws ReflectiveOperationException{
        int choose = -1;
        boolean scannerCon = true;
        while(scannerCon)
            try {
                choose = scanner.nextInt();
                if (choose == -1) throw new ReflectiveOperationException ("by pressing -1 you chose to go back");
                if (choose < 0){
                    System.out.println("you must choose an none-negative number ");
                }
                scannerCon =false;
            } catch (InputMismatchException ie){
                System.out.println("wrong input please try again");
                scanner.nextLine();
            } catch (NoSuchElementException|IllegalStateException e){
                System.out.println("wrong input please try again");
                scanner.nextLine();
            }
        return choose;
    }



    private double getDoubleFromUser(Scanner scanner) throws ReflectiveOperationException{

        double choose = -1;
        boolean scannerCon = true;
        while(scannerCon)
            try {
                choose = scanner.nextDouble();
                if (choose == -1) throw new ReflectiveOperationException("by pressing -1 you chose to go back");
                if (choose < 0){
                    System.out.println("you must choose an none-negative number ");
                }
                scannerCon =false;
            } catch (InputMismatchException ie){
                System.out.println("wrong input please try again");
                scanner.nextLine();
            } catch (NoSuchElementException|IllegalStateException e){
                System.out.println("wrong input please try again");
                scanner.nextLine();
            }
        return choose;
    }

    private String getStringFromUser(Scanner scanner) throws ReflectiveOperationException {

        boolean con = true;
        String output = "";
        while (con) {
            try {
                output =scanner.next();
                if (output.equals("-1")) throw new ReflectiveOperationException("by pressing -1 you chose to go back");
                con= false;
            }catch (NoSuchElementException|IllegalStateException e){
                System.out.println("wrong input please try again");
                scanner.nextLine();
            }
        }
        return output;

    }





    // TODO change weight to double -  all the way down


    // TODO NTH need to print the chosen option.
    // TODO need to check supporting products return
    //  TODO need to check all exception catched
    // TODO weights - grams or Kilos



    // TODO site id- not for chose
    // TODO need to implement the weight insert exceptions
    // TODO need to do the DF updates
    // TODO need create replaced TR



}
