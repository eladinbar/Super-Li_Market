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
        facadeService.chooseTruck( truck);
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









}
