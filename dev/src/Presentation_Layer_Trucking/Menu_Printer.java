package Presentation_Layer_Trucking;
import Business_Layer_Trucking.Facade.FacadeObject.*;
import Business_Layer_Trucking.Resources.Driver;
import javax.management.openmbean.KeyAlreadyExistsException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
            System.out.println(spot + "\tUpdate a Trucking report and its Delivery Form's leaving weight");spot++;
            System.out.println(spot + ".\tRemove item from the system"); spot++;
            System.out.println(spot+".\tRemove site from the system");spot++; // 16
            System.out.println(spot+".\tRemove Demand from the system");spot++;
            System.out.println(spot + ".\tGo back to Main Menu");



            int choose =0;

            choose = getIntFromUserMain(scanner);
            switch (choose) {

                case 1:
                    try {
                        pc.CreateReport();
                        chooseDemands(scanner);
                        chooseTruckAndDriver(scanner);
                        boolean timeCon = true;
                        while(timeCon) {
                            try {
                                chooseLeavingHour(scanner);
                                chooseDate(scanner);
                                timeCon = false;
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }
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
                        updateDeliveryForm(scanner);
                    }catch (ReflectiveOperationException re){
                        System.out.println(re.getMessage());
                    }
                    break;
                case 15:
                    try{
                        removeItemFromPool(scanner);
                    }catch ( ReflectiveOperationException re){
                        System.out.println(re.getMessage());}
                    break;
                case 16:
                    try{
                        removeSiteFromPool(scanner);
                    }catch (ReflectiveOperationException e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 17:
                    try{
                        removeDemandFromPool(scanner);
                    }catch (ReflectiveOperationException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 18:

                    System.out.println("this option isn't supported yet. to be continue");
                    keepGoing = false;

                    break;
                default:

            }
        }
    }

    private void removeDemandFromPool(Scanner scanner) throws ReflectiveOperationException{
        LinkedList<FacadeDemand> demands = pc.showDemands();
        demands = sortDemandsBySite(demands);
        printDemands(demands);
        int chose = getIntFromUser(scanner);
        while (chose<1 ||chose>demands.size()){
            chose = getIntFromUser(scanner);
        }
        FacadeDemand d = demands.get(chose-1);
        pc.removeDemand(d);

    }

    private void chooseDate(Scanner scanner) throws IllegalArgumentException,ReflectiveOperationException {
        boolean dateCon = true;
        while(dateCon) {
            System.out.println("please choose the date you'd like the delivery to be executed:");
            System.out.print("year:");
            int year = getIntFromUser(scanner);
            System.out.print("month:");
            int month = getIntFromUser(scanner);
            System.out.print("day:");
            int day = getIntFromUser(scanner);
            try {
                LocalDate chosen = LocalDate.of(year, month, day);
                dateCon = false;
                pc.chooseDateToCurrentTR(chosen);
            } catch (DateTimeException de) {
                System.out.println("date is invalid, try again");

            }
        }

    }

    private void removeSiteFromPool(Scanner scanner) throws ReflectiveOperationException {
        System.out.println("please choose the site you'd like to remove");
        LinkedList<FacadeSite> sites = pc.getAllSites();
        int spot =1;
        for(FacadeSite site: sites){
            System.out.println(spot +")" +site.getName());spot++;
        }
        int chose = getIntFromUser(scanner);
        while (chose<1 ||chose>sites.size()){
            System.out.println("option is out of bounds, please try again");
            chose = getIntFromUser(scanner);
        }
        int siteID = sites.get(chose-1).getSiteID();
        pc.removeSiteFromPool(siteID);

    }


    private void addDemandToSystem(Scanner scanner) throws ReflectiveOperationException {
        int itemId;
        int site;
        int amount;
        int spot=1;
        System.out.println("Please choose the item you'd add:");
        try {
            LinkedList<FacadeItem> items = pc.getAllItems();
            for (FacadeItem item : items) {
                System.out.println(spot + ")item id: " + item.getID() + "\tname: " + item.getName() + "\tdelivery area: " + pc.getSiteDeliveryArea(item.getOriginId()));
                spot++;
            }

            itemId = getIntFromUser(scanner);
            while(itemId<1 || itemId>items.size()){
                System.out.println("the option you chose is out of bounds, try again");
                itemId = getIntFromUser(scanner);
            }
            itemId = items.get((itemId -1)).getID();

            try{
                LinkedList<FacadeSite> sites = pc.getAllSites();
                System.out.println("please choose the site you'd like to Deliver to");
                // TODO NTH order by site number
                spot = 1;
                for (FacadeSite s: sites){
                    System.out.println(spot+")"+s.getName()+":\tsiteID: "+s.getSiteID() + "\tcity: " + s.getCity() + "\t\tin Delivery Area: "  + s.getDeliveryArea());
                    spot++;
                }
                site = getIntFromUser(scanner);
                while(site<1 || site>sites.size()){
                    System.out.println("the option you chose is out of bounds, try again");
                    site = getIntFromUser(scanner);
                }
                site = sites.get(site-1).getSiteID();
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
        if (choice < 1 ||  choice >  items.size() ){
            System.out.println("option is out of bounds, going back to menu");
        }
        else {
            pc.RemoveItemFromPool(items.get(choice-1).getID());
        }
    }

    private void addItem(Scanner scanner) throws ReflectiveOperationException{
        boolean con = true;
        while (con){
            System.out.print("Item id: ");
            int id =  getIntFromUser(scanner);
            System.out.print("item weight: ");
            double weight =  getDoubleFromUser(scanner);
            System.out.print("item name: ");
            String name = getStringFromUser(scanner);
            while (!name.matches("[A-Z a-z]*")){
                System.out.println("invalid name instered, please reWrite it");
                name = getStringFromUser(scanner);
            }
            System.out.println("please choose its origin site ");
            LinkedList<FacadeSite> sites = pc.getAllSites();
            int spot =1;
            for(FacadeSite site: sites){
                System.out.println(spot +")" +site.getName());spot++;
            }
            int chose = getIntFromUser(scanner);
            while (chose<1 ||chose>sites.size()){
                System.out.println("option is out of bounds, please try again");
                chose = getIntFromUser(scanner);
            }
            int siteID = sites.get(chose-1).getSiteID();
            try {
                pc.addItem(id, weight, name, siteID);
                con = false;
            }
            catch (KeyAlreadyExistsException | NoSuchElementException ke){
                System.out.println(ke.getMessage());
            }
        }
    }

    private void addSite(Scanner scanner) throws ReflectiveOperationException{
        boolean con = true;
        while(con) {
            System.out.print("City name:");
            String city = getStringFromUser(scanner);

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
                pc.addSite(city, deliveryArea, phoneNumber, contactName,name);
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
            int spot=1;
            for (FacadeDriver facadeDriver : drivers) {

                System.out.println(spot+")"+facadeDriver.getName() + ":\n" + "Driver ID: " + facadeDriver.getID() +
                        " License Type: " + facadeDriver.getLicenseType() +
                        " Available: " + facadeDriver.isAvailable());
                spot++;
            }
            System.out.println("Choose Driver");
            int chose=getIntFromUser(scanner);
            while (chose<1||chose>spot)
            {
                System.out.println("Option out of bounds, please choose again");
                chose=getIntFromUser(scanner);
            }
            String driver= drivers.get(chose-1).getID();
            pc.makeAvailable_Driver(driver);
        }
    }

    private void makeTruckAvailable(Scanner scanner) throws ReflectiveOperationException {
        LinkedList<FacadeTruck> trucks = pc.getTrucks();
        if (trucks == null) System.out.println("no trucks in the system yet");
        else{
            System.out.println("trucks:");
            int spot=1;
            for(FacadeTruck truck : trucks){
                System.out.println(spot+")Trucks License Number: " + truck.getLicenseNumber() +
                        "\n model: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight()+"\tavailable:"+truck.isAvailable());
                spot++;
            }
            System.out.println("Choose truck by License:");
            int chose= getIntFromUser(scanner);
            while (chose<1||chose>spot)
            {
                System.out.println("Option out of bounds, please choose again");
                chose=getIntFromUser(scanner);
            }
            String truck=trucks.get(chose-1).getLicenseNumber();
            pc.makeAvailable_Truck(truck) ;
        }
    }

    private void makeDriverUnavailable(Scanner scanner) throws ReflectiveOperationException{
        LinkedList<FacadeDriver> drivers = pc.getAvailableDrivers();
        if (drivers == null) System.out.println("no available drivers");
        else{
            System.out.println("available drivers:");
            int spot=1;
            for (FacadeDriver facadeDriver : drivers) {
                System.out.println(spot+")"+facadeDriver.getName() + ":\n" + "Driver ID: " + facadeDriver.getID() +
                        " License Type: " + facadeDriver.getLicenseType() +
                        " Available: " + facadeDriver.isAvailable());
                spot++;
            }
            System.out.println("Choose Driver by id");
            int chose=getIntFromUser(scanner);
            while (chose<1||chose>spot)
            {
                System.out.println("Option out of bounds, please choose again");
                chose=getIntFromUser(scanner);
            }
            String driver= drivers.get(chose-1).getID();
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
            int spot=1;
            for(FacadeTruck truck : trucks){
                System.out.println(spot+")Trucks License Number: " + truck.getLicenseNumber() +
                        "\n model: " + truck.getModel() + " maxWeight: " + truck.getMaxWeight()+"\tavailable:"+truck.isAvailable());
                spot++;
            }
            System.out.println("Choose truck:");
            int chose=getIntFromUser(scanner);
            while (chose<1||chose>spot)
            {
                System.out.println("Option out of bounds, please choose again");
                chose=getIntFromUser(scanner);
            }
            String truck= trucks.get(chose-1).getLicenseNumber();
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
                System.out.println("the  truck's max weight is not higher then it's neto weight("+weightNeto+"), please choose again or quit with -1");
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


        } catch (KeyAlreadyExistsException e) {
            System.out.println(e.getMessage());
            System.out.println("");
        }

    }

    private void chooseLeavingHour(Scanner scanner) throws ReflectiveOperationException {
        boolean con =  true;

        System.out.print("please choose leaving time: \nhour: ");
        int hour =  getIntFromUser(scanner);
        while (hour>23 ){
            hour = getIntFromUser(scanner);
        }
        System.out.print("minutes: ");
        int minute =  getIntFromUser(scanner);
        while(minute>59){minute =getIntFromUser(scanner); }
        LocalTime time = LocalTime.of(hour, minute);

        pc.chooseLeavingHour(time);
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
                    if (chose == demands.size() + 1){
                        con = false;
                    }
                    else
                    {
                        while (chose < 1 || chose > demands.size()) {
                            System.out.println("option out of bounds, please choose again");
                            chose = getIntFromUser(scanner);
                        }
                        int itemNumber = demands.get(chose - 1).getItemID();
                        System.out.print("amount: ");
                        int amount = getIntFromUser(scanner);
                        while (amount <=0){
                            System.out.println("cannot deliver a non- positive number of items, please try again");
                            amount = getIntFromUser(scanner);
                        }
                        int siteID = demands.get(chose - 1).getSite(); // destination to delivery to
                        con = pc.addDemandToReport(itemNumber, amount, siteID);

                    }
                } catch (IllegalStateException e) { // different delivery area
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
                                if (chose == demands.size() + 1){
                                    enough = true;
                                }
                                else
                                {
                                    while (chose < 1 || chose > demands.size()) {
                                        System.out.println("option out of bounds, please choose again");
                                        chose = getIntFromUser(scanner);
                                    }
                                    int itemNumber = demands.get(chose - 1).getItemID();
                                    System.out.print("amount: ");
                                    int amount = getIntFromUser(scanner);
                                    while (amount <=0){
                                        System.out.println("cannot deliver a non- positive number of items, please try again");
                                        amount = getIntFromUser(scanner);
                                    }
                                    int siteID = demands.get(chose - 1).getSite(); // destination to delivery to
                                    try {
                                        pc.continueAddDemandToReport(itemNumber, amount, siteID);
                                    }catch (IllegalArgumentException illegalArgumentException){
                                        System.out.println(illegalArgumentException.getMessage());
                                    }

                                }
                            }
                            break;
                        case "n":


                            break;

                        default:
                            System.out.println("theres no such option, choose between y or n explicit");
                    }

                }catch (IllegalArgumentException e){ // one of arguments doesn't match
                    System.out.println(e.getMessage());
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
                    "\t\tto: " + pc.getSiteName(fd.getSite()) + "\t\tdelivery area: "  +  pc.getSiteDeliveryArea(fd.getSite()) + "\t site ID:" + fd.getSite()
                    + "\t\titem weight: "  + pc.getWeight(fd.getItemID()))   ;spot++;
        }
    }

    private void chooseTruckAndDriver(Scanner scanner) throws ReflectiveOperationException {
        System.out.println("please choose the Truck you'd like to deliver it with:");
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
            int spot =1;
            for (FacadeSite site : sites) {
                System.out.println(spot+")Site Name: " + site.getName() +"\tSite ID: " + site.getSiteID() + "Site city: " + site.getCity() +
                        "Site: Delivery area: " + site.getDeliveryArea() + "\n products:");
                LinkedList<FacadeDemand> siteDemands = pc.getCurrentDemandsBySite(site);
                for (FacadeDemand demand : siteDemands) {

                    System.out.println(pc.getItemName(demand.getItemID()) + ": " +
                            "item ID:" + demand.getItemID() + "amount: " + demand.getAmount());
                }
            }
            int siteID =  getIntFromUser(scanner);
            while (siteID<1 || siteID>sites.size()){
                System.out.println("option out of bounds, please try again");
                siteID = getIntFromUser(scanner);
            }
            siteID = sites.get(siteID).getSiteID();
            try {
                con = pc.removeDestination(siteID);
            }catch (NoSuchElementException ne){
                System.out.println(ne.getMessage());

            }

        }
    }

    void updateDeliveryForm(Scanner scanner) throws ReflectiveOperationException{
        LinkedList<FacadeTruckingReport> truckingReports =  pc.getActiveTruckingReports();
        if (truckingReports.isEmpty()){
            throw new ReflectiveOperationException("There is no active reports to update");
        }
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
        while(chose<1 || chose>truckingReports.size()) {
            System.out.println("option out of bounds, please try again");
            chose = getIntFromUser(scanner);
        }
        FacadeTruckingReport ftr = truckingReports.get(chose-1);
        LinkedList<FacadeDeliveryForm> deliveryForms =  pc.getDeliveryForms(ftr.getID());
        spot =1;
        for (FacadeDeliveryForm dlf: deliveryForms){
            System.out.println(spot+")origin: "+ dlf.getOrigin() + "\tdestination: " + dlf.getDestination() +
                    "\nitems delivered:" );
            spot++;
            for (Map.Entry<Integer,Integer> entry: dlf.getItems().entrySet()){
                System.out.println("\t" + pc.getItemName(entry.getKey()) + "\tamount: " +entry.getValue());
            }

        }
        System.out.print("Choose delivery form you'd like to update:");
        chose = getIntFromUser(scanner);
        while(chose<1 || chose>deliveryForms.size() )
            chose = getIntFromUser(scanner);
        FacadeDeliveryForm fdf = deliveryForms.get(chose-1);
        System.out.print("Please enter the current truck weight: ");
        int weight = getIntFromUser(scanner);
        try {
            pc.updateDeliveryFormRealWeight(ftr.getID(), fdf.getID(), weight);
        }catch (IllegalStateException illegalStateException){
            System.out.println(illegalStateException.getMessage());
            rePlanAfterWeight(scanner,ftr,weight);
            updateDeliveryForm(scanner);
        }

    }

    private void rePlanAfterWeight(Scanner scanner, FacadeTruckingReport tr,int weight) throws ReflectiveOperationException {

        System.out.println("Welcome to replan menu! please choose the option you'd like to re plan the report with:");
        int spot =1;
        System.out.println(spot + ") remove a site (all the products from this site and to it will be removed)");spot++;
        System.out.println(spot + ") switch demands  - removes a site and adds a new demand to add by choose"); spot++;
        System.out.println(spot + ") change a truck");spot++;
        System.out.println(spot + ") remove item");
        System.out.println("choose different number to quit");
        System.out.print("place your option");

        int option = getIntFromUser(scanner);
        switch (option) {
            case 1: {
                removeSiteFromTruckReport(scanner, tr);
                break;
            }


            // remove site and add new items
            case 2: {
                removeSiteFromTruckReport(scanner, tr);
                int siteID;
                System.out.println("now choose the Items you'd like to add");

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
                            int itemNumber = getIntFromUser(scanner);
                            if (itemNumber == demands.size() + 1) {
                                con = false;
                            } else {
                                while (itemNumber < 1 || itemNumber > demands.size()) {
                                    System.out.println("option out of bounds, please choose again");
                                    itemNumber = getIntFromUser(scanner);
                                }
                                itemNumber = demands.get(itemNumber - 1).getItemID();
                                System.out.print("amount: ");
                                int amount = getIntFromUser(scanner);

                                System.out.print("site ID: ");
                                siteID = getIntFromUser(scanner);
                                con = pc.addDemandToTruckReport(itemNumber, amount, siteID, tr.getID());

                            }
                        } catch (IllegalStateException e) { // different delivery area
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
                                            int itemNumber = getIntFromUser(scanner);
                                            System.out.println();
                                            System.out.print("amount: ");
                                            int amount = getIntFromUser(scanner);
                                            System.out.println();
                                            System.out.println("site id:");
                                            siteID = getIntFromUser(scanner);
                                            enough = pc.continueAddDemandToTruckReport(itemNumber, amount, siteID,tr.getID());
                                        }
                                    }
                                    break;
                                case "n":


                                    return;

                                default:
                                    System.out.println("theres no such option, choose between y or n explicit");
                            }

                        } catch (IllegalArgumentException e) { // one of arguments doesn't match
                            System.out.println(e.getMessage());
                        }


                    } catch (NoSuchElementException ne) {
                        System.out.println(ne.getMessage());
                        con = false;
                    }
                }



                break;
            }


            // replace truck
            case 3: {
                System.out.println("please choose the Truck you'd like to deliver it with:");
                LinkedList<FacadeTruck> trucks = pc.getAvailableTrucks();

                System.out.println("please notice the truck weight is: " + weight);
                System.out.println("available trucks:");
                spot = 1;
                for (FacadeTruck truck : trucks) {
                    System.out.println(spot + ") truck LicenseNumber: " + truck.getLicenseNumber() + " max Weight :" + truck.getMaxWeight());
                    spot++;
                }
                int chose = getIntFromUser(scanner);
                while (chose < 1 || chose > trucks.size()) {
                    System.out.println("option out of bounds, please try again");

                    chose = getIntFromUser(scanner);
                }

                String truckNumber = trucks.get(chose - 1).getLicenseNumber();
                try {
                    pc.replaceTruck(tr.getID(), truckNumber, weight);
                } catch (IllegalStateException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    System.out.println("now please choose driver:");

                    LinkedList<FacadeDriver> drivers = pc.getAvailableDrivers();
                    System.out.println("available Drivers:");
                    spot = 1;
                    for (FacadeDriver driver : drivers) {
                        System.out.println(spot + ") Driver ID:" + driver.getID() + " License degree: " + driver.getLicenseType() + " =" + driver.getLicenseType().getSize());
                        spot++;
                    }
                    chose = getIntFromUser(scanner);
                    while (chose < 1 || chose > drivers.size()) {
                        System.out.println("option is out of bounds, please try again");
                        chose = getIntFromUser(scanner);
                    }

                    String driverID = drivers.get(chose - 1).getID();

                    try {
                        pc.replaceDriver(tr.getID(), driverID,weight);
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                        rePlanAfterWeight(scanner, tr, weight);
                    }
                }
                break;
            }

            case 4: //remove items
            {
                LinkedList<FacadeDemand> items = pc.getItemOnReport(tr.getID());
                spot = 1;
                for (FacadeDemand item : items) {
                    System.out.println(spot+") "+pc.getItemName(item.getItemID()) + "\tamount: " +item.getAmount() + "\tdeliver to:" + pc.getSiteName(item.getSite()) );
                }
                int chose =  getIntFromUser(scanner);
                while (chose<1 || chose>items.size()){
                    chose = getIntFromUser(scanner);
                }

                FacadeDemand demand = items.get(chose);
                pc.removeItemFromTruckingReport(tr.getID(), demand);
            }
            default:
                throw new ReflectiveOperationException("no such option - returning to Trucking menu");
        }


    }

    private void removeSiteFromTruckReport(Scanner scanner, FacadeTruckingReport tr) throws ReflectiveOperationException {
        int spot;
        LinkedList<Integer> sites = tr.getDestinations();
        System.out.println("please choose the site you'd like to remove");
        spot = 1;
        for (Integer id : sites) {
            System.out.println(spot + ")" + pc.getSiteName(id));
        }
        int siteID = getIntFromUser(scanner);
        while (siteID < 1 || siteID > sites.size())
            siteID = getIntFromUser(scanner);
        siteID = sites.get((siteID));
        pc.removeSiteFromTruckReport(siteID, tr.getID());
    }



    public void putInitialTestState(){
        pc.addDriver("203834734","Ido" ,Driver.License.C1);
        pc.addDriver("123456789", "Shir" ,Driver.License.C);
        pc.addDriver("987654321", "Ofir" , Driver.License.C);

        pc.addTruck("Mercedes" , "62321323" , 2000, 12000);
        pc.addTruck("Man", "1231231", 1500, 8000);
        pc.addTruck("Volvo","123" ,1000, 10000);

        pc.addSite("Haifa", 1, "0502008216" , "Shimi", "SuperLee-Haifa");
        pc.addSite("Nazareth" , 1,"0522002123" , "Esti" , "Suber-LNazerath");
        pc.addSite("Beer Sheva" ,2, "0502008217" , "Yotam" , "superLee-BeerSheva");
        pc.addSite("Rahat" , 2 , "0502008214" , "Mohamad" , "MilkHere");
        pc.addSite("Afula", 3,"0502008215" , "Raz" , "Tnuva");
        pc.addSite("Geva" , 3, "0503988883", "ShirHayafa","Dubi");

        pc.addItem(1,1,"milk",1);
        pc.addItem(2 , 2 , "cream cheese",1);
        pc.addItem(3 , 4 , "cottage" ,2);
        pc.addItem(4 , 2 , "banana",2);
        pc.addItem(5 , 3 , "cucumber",3);
        pc.addItem(6,0.1,"chocolate",5);

        pc.addDemandToSystem(1,2,1000);
        pc.addDemandToSystem(3, 1, 100);
        pc.addDemandToSystem(5,4,1000);
        pc.addDemandToSystem(6, 6,500);
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
                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            } catch (NoSuchElementException|IllegalStateException e){
                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            }
        return choose;

    }
    private int getIntFromUser(Scanner scanner) throws ReflectiveOperationException{
        int choose = -1;
        boolean scannerCon = true;
        while(scannerCon) {
            try {
                choose = scanner.nextInt();
                if (choose == -1) throw new ReflectiveOperationException("by pressing -1 you chose to go back");
                if (choose < 0) {
                    System.out.println("you must choose an none-negative number ");
                }
                else {
                    scannerCon = false;
                }
            } catch (InputMismatchException ie) {
                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            } catch (NoSuchElementException | IllegalStateException e) {
                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            }
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
                System.out.println("wrong input - a number must be inserted please try again ");
                scanner.nextLine();
            } catch (NoSuchElementException|IllegalStateException e){
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







    // TODO NTH need to print the chosen option.



    // TODO show on TR - if finished or not
    // TODO maybe the user wont choose item id

    // TODO need to check all methods are in use




}
