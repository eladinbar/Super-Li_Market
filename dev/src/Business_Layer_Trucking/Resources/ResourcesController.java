package Business_Layer_Trucking.Resources;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

public class ResourcesController {
    private HashMap<String,Driver> drivers;
    private HashMap<Integer,Truck> trucks;
    private LinkedList<Driver> driversByLicense;
    private  static ResourcesController instance=null;
    private int currDriverID;
    private int currTruckNumber;

    private ResourcesController()
    {
        drivers=new HashMap<>();
        trucks=new HashMap<>();
        driversByLicense=new LinkedList<>();
        this.currDriverID=-1;
        this.currTruckNumber=-1;
    }

    public static ResourcesController getInstance() {
        if (instance==null)
            instance=new ResourcesController();
        return instance;
    }
    public void addDriver(String id , String name, Driver.License licenseType) throws KeyAlreadyExistsException
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
    public void addTruck(String model,int licenseNumber,int weightNeto,int maxWeight) throws KeyAlreadyExistsException
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
    public void deleteDriver(int id)throws NoSuchElementException
    {
        if (drivers.containsKey(id))
        {
            Driver removed=drivers.remove(id);
            driversByLicense.remove(removed);
        }
        else throw new NoSuchElementException("We do not hiring that person");
    }
    public void deleteTruck(int licenseNumber)throws NoSuchElementException
    {
        if (trucks.containsKey(licenseNumber))
        {
            trucks.remove(licenseNumber);
        }
        else throw new NoSuchElementException("No such truck found");
    }
    public HashMap<String,Driver> getAvailableDrivers()
    {
        HashMap<String,Driver> result=new HashMap<>();
        for (Map.Entry<String,Driver> entry:drivers.entrySet())
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

    public Truck chooseTruck(int truck) throws IllegalStateException,NoSuchElementException{

        if (trucks.containsKey(truck)){
            if (trucks.get(truck).isAvailable())
            {
                currTruckNumber=truck;
                return trucks.get(truck);
            }
            else throw new IllegalStateException("Truck already taken");
        }
        else throw new NoSuchElementException("No such truck");
    }

    public Driver chooseDriver(int driver) throws IllegalStateException , NoSuchElementException{

        if (drivers.containsKey(driver)){
            if (drivers.get(driver).isAvailable())
            {
                currDriverID=driver;
                return drivers.get(driver);
            }
            else throw new IllegalStateException("Driver already taken");
        }
        else throw new NoSuchElementException("No such Driver");

    }


    public void makeUnavailable_Driver(int driver)throws NoSuchElementException {
        if (!drivers.containsKey(driver))
            throw new NoSuchElementException("Driver does not exist");
        drivers.get(driver).setUnavailable();
    }

    public void makeAvailable_Driver(int driver) {
        //TODO need to prevent from making available on a mission
        drivers.get(driver).makeAvailable();
    }

    public void makeUnavailable_Truck(int truck) {
        trucks.get(truck).setUnavailable();
    }

    public void makeAvailable_Truck(int truck) {
        trucks.get(truck).makeAvailable();
    }
    public void saveReport()
    {
        drivers.get(currDriverID).setUnavailable();
        trucks.get(currTruckNumber).setUnavailable();
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<< getters setters >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public HashMap<String, Driver> getDrivers() {
        return drivers;
    }

    public HashMap<Integer, Truck> getTrucks() {
        return trucks;
    }

    public int getCurrDriverID() {
        return currDriverID;
    }

    public int getCurrTruckNumber() {
        return currTruckNumber;
    }

    public void setCurrDriverID(int currDriverID) {
        this.currDriverID = currDriverID;
    }

    public void setCurrTruckNumber(int currTruckNumber) {
        this.currTruckNumber = currTruckNumber;
    }

    public void setDrivers(HashMap<String, Driver> drivers) {
        this.drivers = drivers;
    }

    public void setDriversByLicense(LinkedList<Driver> driversByLicense) {
        this.driversByLicense = driversByLicense;
    }

    public static void setInstance(ResourcesController instance) {
        ResourcesController.instance = instance;
    }

    public void setTrucks(HashMap<Integer, Truck> trucks) {
        this.trucks = trucks;
    }
}
