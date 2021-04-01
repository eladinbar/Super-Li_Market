package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Resources.Driver;
import Business_Layer_Trucking.Resources.ResourcesController;
import Business_Layer_Trucking.Resources.Truck;

import java.util.HashMap;
import java.util.LinkedList;

public class ResourcesService
{
    ResourcesController rc;


    public ResourcesService(){

    }


    public void chooseTruck(int truck) {//TODO- for ido - change it (if needed! )
        try {
            rc.chooseTruck(truck);
        }
        catch (Exception e){}
    }

    public void chooseDriver(int driver) {//TODO- for ido - change it (if needed! )
        try {
            rc.chooseDriver(driver);
        }
        catch (Exception e){}
    }

    public void replaceTruck(int truck) {
        rc.replaceTruck(truck,-1);
        //TODO check if need to change driver in case we replaced truck
        //we need to send the number of the the truck we want to replace!
    }

    public void makeUnavailable_Driver(int driver) {
        rc.makeUnavailable_Driver(driver);
    }

    public void makeAvailable_Driver(int driver) {
        rc.makeAvailable_Driver(driver);
    }

    public void makeUnavailable_Truck(int truck) {
        rc.makeUnavailable_Truck(truck);
    }

    public void makeAvailable_Truck(int truck) {
        rc.makeAvailable_Truck(truck);
    }

    public HashMap<Integer, Truck> getAvailableTrucks() {
        return rc.getAvailableTrucks();
    }


    public void addTruck(String model, int licenseNumber, int weightNeto, int maxWeight) {
         try {
             rc.addTruck( model, licenseNumber, weightNeto, maxWeight);
         }
         catch (Exception e){}//TODO- for ido - change it (if needed! )
    }

    public void addDriver(int id, String name, Driver.License licenseType) {//TODO- for ido - change it (if needed! ).
        try {
            rc.addDriver(id, name, licenseType);
        }
        catch (Exception e){}
    }


}
