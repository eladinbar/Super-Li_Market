package Business_Layer_Trucking.Facade.FacadeObject;

import Business_Layer_Trucking.Resources.Driver;
import Business_Layer_Trucking.Resources.ResourcesController;
import Business_Layer_Trucking.Resources.Truck;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

    public FacadeDriver chooseDriver(int driver) {

        return new FacadeDriver(rc.chooseDriver(driver));


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

    public void addDriver(int id, String name, Driver.License licenseType) {//TODO- for ido - change it (if needed! ).

        rc.addDriver(id, name, licenseType);

    }


    public LinkedList<FacadeDriver> getAvailableDrivers() {
        HashMap<Integer,Driver> drivers = rc.getAvailableDrivers();
        LinkedList<FacadeDriver> output = new LinkedList<>();
        for(Map.Entry<Integer,Driver> entry : drivers.entrySet()){
            output.add(new FacadeDriver(entry.getValue()));
        }
        return output;

    }

    public LinkedList<FacadeDriver> getDrivers() {
        HashMap <Integer, Driver> drivers =  rc.getDrivers();
        LinkedList<FacadeDriver> output =  new LinkedList<>();
        for(HashMap.Entry<Integer,Driver> entry :  drivers.entrySet()){
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
}
