package Presentation_Layer_Trucking;

import Business_Layer_Trucking.Facade.FacadeObject.FacadeDemand;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeTruck;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Menu_Printer {
    PresentationController pc;
    private static Menu_Printer instance =null;

    private Menu_Printer(){pc =  new PresentationController(); }

    public static Menu_Printer getInstance() {
        return instance;
    }

    public void startMenu(){
        Scanner scanner =  new Scanner(System.in);
        System.out.println("Welcome to Trucking Menu!\nplease choose the option you'd like:");
        System.out.println("1. Create new Trucking Report");
        System.out.println("2. Show Drivers");
        System.out.println("3. Show Trucks");
        System.out.println("4. Show Current Demands");
        System.out.println("5. go back to main Menu");
        System.out.println("6. make truck unavailable");
        System.out.println("7. make driver unavailable");
        int choose = scanner.nextInt();
        switch (choose){
            case 1:
                pc.CreateReport();
                List<FacadeDemand>  demands = pc.showDemads();
                demands =  sortDemandsBySite(demands);
                System.out.println("the current demands:");
                int i =1;
                for (FacadeDemand fd: demands) {
                    String itemName = pc.getItemName( fd.getItemID());
                    System.out.println(i + ") " +   itemName + " amount needed" + fd.getAmount() +
                            " to " + pc.getSiteName( fd.getSite()) );
                }
                boolean con = true;
                try {
                    while (con) {
                        System.out.print("item number: ");
                        int itemNumber = scanner.nextInt();
                        System.out.println();
                        System.out.print("amount: ");
                        int amount = scanner.nextInt();
                        System.out.println();

                        con = pc.addDemandToReport(itemNumber, amount);

                    }
                }
                catch (IllegalStateException e){
                    System.out.println("you chose different delivery area from the currents," +
                            " would you like to continue? y for yes, n for not");
                    String answer = scanner.nextLine();
                    switch (answer){
                        case "y":
                            boolean enough=false;
                            while(!enough ) {
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

                }
                catch (Exception e){
                    rePlan();
                }
                // TODO need to print the weight received
                chooseTruckAndDriver();





            case 2:

            case 3:
            case 4:
            case 5:



        }




    }

    private void chooseTruckAndDriver() {
        System.out.println("lease choose the Truck you'd like to deliver it with:");
        LinkedList<FacadeTruck> trucks =  pc.getTrucksAvailableTrucks();
        System.out.println("available trucks:");
        int i =1;
        for (FacadeTruck truck: trucks) {
            System.out.println(i +") " +  truck.getLicenseNumber() + " max Weight" + truck.getMaxWeight())  ;

        }
    }

    private void rePlan() {
        pc.rePlanning();
    }

    private List<FacadeDemand> sortDemandsBySite(List<FacadeDemand> demands) {
        return null;
    }
}
