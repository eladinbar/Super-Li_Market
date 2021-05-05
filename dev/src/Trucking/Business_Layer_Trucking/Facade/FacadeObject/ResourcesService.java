package Trucking.Business_Layer_Trucking.Facade.FacadeObject;

import Trucking.Business_Layer_Trucking.Resources.Driver;
import Trucking.Business_Layer_Trucking.Resources.ResourcesController;
import Trucking.Business_Layer_Trucking.Resources.Truck;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

public class ResourcesService
{
    private ResourcesController rc;
    private static  ResourcesService instance = null;


    public ResourcesService(){
        rc = ResourcesController.getInstance();

    }

    public static ResourcesService getInstance() {
        if (instance == null)
            instance = new ResourcesService();
        return instance;
    }


    public Truck chooseTruck(String truck) throws NoSuchElementException, IllegalStateException {

        return rc.chooseTruck(truck);

    }

    public FacadeDriver chooseDriver(String driver)throws IllegalStateException,NoSuchElementException {

        return new FacadeDriver(rc.chooseDriver(driver));


    }



    public void makeUnavailable_Driver(String driver)throws NoSuchElementException {
        rc.makeUnavailable_Driver(driver);
    }

    public void makeAvailable_Driver(String driver) {
        rc.makeAvailable_Driver(driver);
    }

    public void makeUnavailable_Truck(String truck) {
        rc.makeUnavailable_Truck(truck);
    }

    public void makeAvailable_Truck(String truck) {
        rc.makeAvailable_Truck(truck);
    }

    public LinkedList<FacadeTruck> getAvailableTrucks() {
        LinkedList<FacadeTruck> output =  new LinkedList<>();
        HashMap<String , Truck> trucks =  rc.getAvailableTrucks();
        for (Map.Entry<String ,Truck> entry:trucks.entrySet())
        {
            output.add(new FacadeTruck( entry.getValue()));
        }
        return output;
    }


    public void addTruck(String model, String  licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException {
        rc.addTruck( model, licenseNumber, weightNeto, maxWeight);
    }

    public void addDriver(String id, String name, Driver.License licenseType) throws KeyAlreadyExistsException {

        rc.addDriver(id, name, licenseType);

    }


    public LinkedList<FacadeDriver> getAvailableDrivers() {
        HashMap<String,Driver> drivers = rc.getAvailableDrivers();
        LinkedList<FacadeDriver> output = new LinkedList<>();
        for(Map.Entry<String,Driver> entry : drivers.entrySet()){
            output.add(new FacadeDriver(entry.getValue()));
        }
        return output;

    }

    public LinkedList<FacadeDriver> getDrivers() {
        HashMap <String, Driver> drivers =  rc.getDrivers();
        LinkedList<FacadeDriver> output =  new LinkedList<>();
        for(HashMap.Entry<String,Driver> entry :  drivers.entrySet()){
            output.add(new FacadeDriver(entry.getValue()));

        }
        return output;
    }

    public LinkedList<FacadeTruck> getTrucks() {
        LinkedList<FacadeTruck> output =  new LinkedList<>();
        HashMap<String , Truck> trucks =  rc.getTrucks();
        for (Map.Entry<String ,Truck> entry:trucks.entrySet())
        {
            output.add(new FacadeTruck( entry.getValue()));
        }
        return output;
    }

    public void saveReport() {
        rc.saveReport();
    }

    public void replaceTruck(String old_truck, String truckNumber) {
        rc.replaceTruck(old_truck,truckNumber);
    }

    public FacadeDriver getDriver(String driverID) {
        return new FacadeDriver(rc.getDriver(driverID));
    }

    public FacadeTruck getTruck(String truckNumber) {
        return new FacadeTruck(rc.getTruck(truckNumber));

    }
}
