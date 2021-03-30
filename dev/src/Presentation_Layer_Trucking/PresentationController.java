package Presentation_Layer_Trucking;

import Business_Layer_Trucking.Facade.DeliveryService;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDeliveryForm;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeTruckingReport;
import Business_Layer_Trucking.Facade.FacadeService;

import javax.swing.*;
import java.util.Date;
import java.util.Scanner;

public class PresentationController {
    private FacadeService facadeService;
    private Menu_Printer menu_printer;

    public FacadeTruckingReport CreateReport()
    {
        // TODO need to add quit option in any time the player can choose
        facadeService.createTruckingReport();
        //TODO need to check how to write it
        Scanner scanner=new Scanner(System.in);
        scanner.next();
        int first=1;
        int second=1;
        // TODO need to extract demand
        boolean stop=false;
        // TODO might throw exception, need to check how to handle right
        // TODO need to display the possible Demands
        try {
            while (!stop) {
                if (scanner.next() != "0")
                    facadeService.addDemandToReport(first, second);
                else stop = true;
            }
        }catch (IllegalStateException e){
            boolean moveOn = true;
            if (moveOn){
                facadeService.continueAddDemandToReport(first, second);
            }
            else {
                // Quit
            }
        }
        int truck=1;
        // TODO try and catch
        // TODO to display trucks
        try {
            facadeService.chooseTruck(truck);
        }
        catch (IllegalStateException e)
        {
            //Need to choose truck again
        }
        int driver =1;
        facadeService.chooseDriver(driver);
        Date leavingHour = new Date("");
        facadeService.chooseLeavingHour(leavingHour);
        facadeService.saveReport();
        return (facadeService.getCurrTruckReport());
    }

    public FacadeDeliveryForm getDeliveryForm(){
        int trNumber=1;
        FacadeTruckingReport tr = facadeService.getTruckReport(trNumber);
        // TODO display Delivery form
        int dfNumber =1;
        return facadeService.getDeliveryForms(dfNumber);
    }
    public FacadeTruckingReport rePlanning()
    {
        Scanner scanner=new Scanner(System.in);
        int option=-1;
        while (option!=-1)
        switch (option){
            case 1://remove site
            {
                System.out.println("Choose site to remove");
                int site=-1;
                facadeService.removeDestination(site);
            }
            case 2://add demand(=site)
            {
                System.out.println("choose demand to add");
                int demand=-1;
                System.out.println("choose amount");
                int amount=-1;
                facadeService.addDemandToReport(demand,amount);
            }
            case 3://replace truck
            {
                System.out.println("choose new truck");
                int truck=-1;
                facadeService.replaceTruck(truck);
            }
            case 4://remove items (do we need to remove all amount of item?some of it?from one demand?if not,from which demand?)
            {
                System.out.println("choose item to remove");
                int item=-1;
                facadeService.removeItem(item);
            }
        }
        return facadeService.getCurrTruckReport();
    }

    public boolean makeUnavailable_Driver()
    {
        //TODO display only available drivers
        System.out.println("Choose Driver");
        int driver=-1;
        facadeService.makeUnavailable_Driver(driver);
        return true;
    }
    public boolean makeAvailable_Driver()
    {
        //TODO display only unavailable drivers
        System.out.println("Choose Driver");
        int driver=-1;
        facadeService.makeAvailable_Driver(driver);
        return true;
    }
    public boolean makeUnavailable_Truck()
    {
        //TODO display only available trucks
        System.out.println("Choose truck");
        int truck=-1;
        facadeService.makeUnavailable_Truck(truck);
        return true;
    }
    public boolean makeAvailable_Truck()
    {
        //TODO display only unavailable trucks
        System.out.println("Choose truck");
        int truck=-1;
        facadeService.makeAvailable_Truck(truck);
        return true;
    }









}
