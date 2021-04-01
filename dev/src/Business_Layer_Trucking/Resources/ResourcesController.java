package Business_Layer_Trucking.Resources;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

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
    public void addDriver(int id , String name, Driver.License licenseType) throws Exception
    {
        if (!drivers.containsKey(id)) {
            Driver driver=new Driver(id,name,licenseType);
            drivers.put(id,driver);
            if (licenseType == Driver.License.C1)
                driversByLicense.addLast(driver);
            else driversByLicense.addFirst(driver);
        }
        else {
            throw new KeyAlreadyExistsException("Driver already works here");
        }

    }
    public void addTruck(String model,int licenseNumber,int weightNeto,int maxWeight) throws Exception
    {
        if (!trucks.containsKey(licenseNumber))
        {
            Truck truck=new Truck(model, licenseNumber, weightNeto, maxWeight);
            trucks.put(licenseNumber,truck);
        }
        else {
            throw new KeyAlreadyExistsException("Truck already added");
        }
    }
    public void deleteDriver(int id)throws Exception
    {
        if (drivers.containsKey(id))
        {
            Driver removed=drivers.remove(id);
            driversByLicense.remove(removed);
        }
        else throw new NoSuchElementException("We do not hiring that person");
    }
    public void deleteTruck(int licenseNumber)throws Exception
    {
        if (trucks.containsKey(licenseNumber))
        {
            trucks.remove(licenseNumber);
        }
        else throw new NoSuchElementException("No such truck found");
    }
    public HashMap<Integer,Driver> getAvailableDrivers()
    {
        HashMap<Integer,Driver> result=new HashMap<>();
        for (Map.Entry<Integer,Driver> entry:drivers.entrySet())
        {
            if (entry.getValue().isAvailable())
                result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    public HashMap<Integer,Truck> getAvailableTrucks()
    {
        HashMap<Integer,Truck> result=new HashMap<>();
        for (Map.Entry<Integer,Truck> entry:trucks.entrySet())
        {
            if (entry.getValue().isAvailable())
                result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public void chooseTruck(int truck) throws Exception{
        if (trucks.containsKey(truck)){
            if (trucks.get(truck).isAvailable())
            {
                trucks.get(truck).setUnavailable();
            }
            else throw new IllegalStateException("Truck already taken");
        }
        else throw new NoSuchElementException("No such truck");
    }

    public void chooseDriver(int driver) throws Exception{
        if (trucks.containsKey(driver)){
            if (trucks.get(driver).isAvailable())
            {
                trucks.get(driver).setUnavailable();
            }
            else throw new IllegalStateException("Driver already taken");
        }
        else throw new NoSuchElementException("No such Driver");
    }

    public void replaceTruck(int new_Truck,int old_Truck ) {
        if (trucks.containsKey(new_Truck))
        {
            if (trucks.get(new_Truck).isAvailable())
            {
                trucks.get(new_Truck).setUnavailable();
                trucks.get(old_Truck).makeAvailable();
            }
            else throw new  IllegalStateException("Truck is not available");
        }
        else throw new NoSuchElementException("Truck does not exist");
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
