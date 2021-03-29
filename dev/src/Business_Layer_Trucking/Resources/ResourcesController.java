package Business_Layer_Trucking.Resources;

import java.util.HashMap;

public class ResourcesController {
    HashMap<Integer,Driver> drivers;
    HashMap<Integer,Truck> trucks;

    public ResourcesController()
    {
        drivers=new HashMap<>();
        trucks=new HashMap<>();
    }

    public HashMap<Integer, Driver> getDrivers() {
        return drivers;
    }

    public HashMap<Integer, Truck> getTrucks() {
        return trucks;
    }
    public void addDriver(Driver driver)
    {
      //TODO implement
      throw new UnsupportedOperationException();
    }
    public void addTruck(Truck truck)
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
}
