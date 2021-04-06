package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Resources.Driver;
import Business_Layer_Trucking.Resources.ResourcesController;
import Business_Layer_Trucking.Resources.Truck;

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


    public Truck chooseTruck(int truck) throws NoSuchElementException, IllegalStateException {

        return rc.chooseTruck(truck);

    }

    public FacadeDriver chooseDriver(int driver)throws IllegalStateException,NoSuchElementException {

        return new FacadeDriver(rc.chooseDriver(driver));


    }



    public void makeUnavailable_Driver(int driver)throws NoSuchElementException {
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

    public LinkedList<FacadeTruck> getAvailableTrucks() {
        LinkedList<FacadeTruck> output =  new LinkedList<>();
        HashMap<Integer, Truck> trucks =  rc.getAvailableTrucks();
        for (Map.Entry<Integer,Truck> entry:trucks.entrySet())
        {
            output.add(new FacadeTruck( entry.getValue()));
        }
        return output;
    }


    public void addTruck(String model, int licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException {
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
        HashMap<Integer, Truck> trucks =  rc.getTrucks();
        for (Map.Entry<Integer,Truck> entry:trucks.entrySet())
        {
            output.add(new FacadeTruck( entry.getValue()));
        }
        return output;
    }

    public void saveReport() {
        rc.saveReport();
    }
}
