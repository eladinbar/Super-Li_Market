package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Resources.Driver;
import Business_Layer_Trucking.Resources.ResourcesController;

import java.util.HashMap;
import java.util.LinkedList;

public class ResourcesService
{
    ResourcesController rc;


    public ResourcesService(){

    }


    public void chooseTruck(int truck) {
        rc.chooseTruck(truck);
    }

    public void chooseDriver(int driver) {
        rc.chooseDriver(driver);
    }

    public void replaceTruck(int truck) {
        rc.replaceTruck(truck);
        //TODO check if need to change driver in case we replaced truck
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

    public HashMap<Integer, Driver> getAvailableTrucks() {
        return rc.getAvailableTrucks();
    }


    public void addTruck(String model, int licenseNumber, int weightNeto, int maxWeight) {
         rc.addTruck( model, licenseNumber, weightNeto, maxWeight);
    }

    public void addDriver(int id, String name, Driver.License licenseType) {
        rc.addDriver(id, name, licenseType);
    }


}
