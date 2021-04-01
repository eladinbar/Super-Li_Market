package Presentation_Layer_Trucking;

import Business_Layer_Trucking.Facade.DeliveryService;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDeliveryForm;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeDemand;
import Business_Layer_Trucking.Facade.FacadeObject.FacadeTruckingReport;
import Business_Layer_Trucking.Facade.FacadeService;
import Business_Layer_Trucking.Resources.Driver;

import javax.swing.*;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
            // exception - different delivery area
            boolean moveOn = true;
            if (moveOn){
                facadeService.continueAddDemandToReport(first, second);
            }

            else {
                // Quit
            }
        }
        catch (Exception e) // TODO need to check exception type
        {
            // exception -  overWeight
            rePlanning();
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
        LocalDateTime leavingHour = LocalDateTime.now();//TODO- check how to call constructor
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
        while (option != -1)
        switch (option){
            case 1://remove site
            { // TODO all sout need to be deleted and remove to GUI
                facadeService.displaySites();//TODO - watch - added method ! take a look all the way down ( there is a sout)
                int site=-1;
                facadeService.removeDestination(site);
            }
            case 2://switch demand(=site)
            {
                int site =-1;
                facadeService.removeDestination(site);
                System.out.println("choose demand to add");
                int demand=-1;
                System.out.println("choose amount");
                int amount=-1;
                FacadeDemand new_FD = facadeService.addDemandToReport(demand,amount);
                while (new_FD != null){
                    //TODO system in need to be added here
                    new_FD = facadeService.addDemandToReport(demand,amount);
                }

            }
            case 3://replace truck
            {
                System.out.println("choose new truck");
                HashMap  trucks =facadeService.getAvailableTrucks();

                int truck=-1;
                facadeService.replaceTruck(truck);
            }
            case 4://remove items
            {
                System.out.println("choose item to remove");

                LinkedList<FacadeDemand> items = facadeService.getItemsOnTruck();
                int item=-1;

                facadeService.removeItemFromReport(item);
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

    public void addTruck(){
        String model="";
        int licenseNumber=0;
        int weightNeto=0;
        int maxWeight=0;
        facadeService.addTruck( model, licenseNumber, weightNeto, maxWeight);

    }

    public void addDriver(){
        int ID =0;
        String name="";
        Driver.License licenseType = Driver.License.C1;
        facadeService.addDriver(ID, name, licenseType);
    }

    public void addSite(){
        String city ="";
        int siteID=0;
        int deliveryArea=0;
        String phoneNumber ="";
        String contactName = "";
        facadeService.addSite(city, siteID, deliveryArea, phoneNumber, contactName );
    }

    public void addItem(){
        int id =0;
        int weight =0;
        String name="";
        facadeService.addItem(id, weight,name);
    }
    public void RemoveItemFromPool()
    {
        //TODO- implement if needed.
        int item=-1;
        facadeService.removeItemFromPool(item);
    }

}
