package Business_Layer_Trucking.Resources;

import java.util.HashMap;
import java.util.LinkedList;

public class ResourcesController {
    HashMap<Integer,Driver> drivers;
    HashMap<Integer,Truck> trucks;
    LinkedList<Driver> driversByLicense;

    public ResourcesController()
    {
        drivers=new HashMap<>();
        trucks=new HashMap<>();
        driversByLicense=new LinkedList<>();
    }

    public HashMap<Integer, Driver> getDrivers() {
        return drivers;
    }

    public HashMap<Integer, Truck> getTrucks() {
        return trucks;
    }
    public void addDriver(int id , String name, Driver.License licenseType)
    {
      //TODO implement
       /* if (driver.getLicenseType() == Driver.License.C1)
            driversByLicense.addLast(driver);
        else driversByLicense.addFirst(driver);*/
      throw new UnsupportedOperationException();
    }
    public void addTruck(String model,int licenseNumber,int weightNeto,int maxWeight)
    {
        //TODO implement
        throw new UnsupportedOperationException();
    }
    public void deleteDriver(int ID)
    {
        //TODO implement
        throw new UnsupportedOperationException();
    }
    public void deleteTruck(int licenseNumber)
    {
        //TODO implement
        throw new UnsupportedOperationException();
    }
    public HashMap<Integer,Driver> getAvailableDrivers()
    {
        //TODO implement
        throw new UnsupportedOperationException();
    }
    public HashMap<Integer,Driver> getAvailableTrucks()
    {
        //TODO implement
        throw new UnsupportedOperationException();
    }

    public void chooseTruck(int truck) {
    
    }

    public void chooseDriver(int driver) {
    }

    public void replaceTruck(int truck) {
    }

    public void makeUnavailable_Driver(int driver) {
        drivers.get(driver).setUnavailable();
    }

    public void makeAvailable_Driver(int driver) {
        drivers.get(driver).makeAvailable();
    }

    public void makeUnavailable_Truck(int truck) {
        trucks.get(truck).setUnavailable();
    }

    public void makeAvailable_Truck(int truck) {
        trucks.get(truck).makeAvailable();
    }
}
