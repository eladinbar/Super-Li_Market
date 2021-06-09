package BusinessLayer.TruckingPackage.Facade.FacadeObject;

import BusinessLayer.TruckingPackage.ResourcesPackage.Driver;
import BusinessLayer.TruckingPackage.ResourcesPackage.ResourcesController;
import BusinessLayer.TruckingPackage.ResourcesPackage.Truck;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

public class ResourcesService {
    private ResourcesController rc;
    private static ResourcesService instance = null;


    public ResourcesService() {
        rc = ResourcesController.getInstance();

    }

    public static ResourcesService getInstance() {
        if (instance == null)
            instance = new ResourcesService();
        return instance;
    }


    public Truck chooseTruck(String truck,LocalDate date, int shift) throws NoSuchElementException, IllegalStateException {

        return rc.chooseTruck(truck,date,shift);

    }

    public FacadeDriver chooseDriver(String driver,LocalDate date,  int shift) throws IllegalStateException, NoSuchElementException {

        return new FacadeDriver(rc.chooseDriver(driver,date,shift));


    }



    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException, SQLException {
        rc.addTruck(model, licenseNumber, weightNeto, maxWeight);
    }

    public void addDriver(String id, String name, Driver.License licenseType) throws KeyAlreadyExistsException,SQLException {

        rc.addDriver(id, name, licenseType);

    }




    public LinkedList<FacadeDriver> getDrivers() {
        HashMap<String, Driver> drivers = rc.getDrivers();
        LinkedList<FacadeDriver> output = new LinkedList<>();
        for (HashMap.Entry<String, Driver> entry : drivers.entrySet()) {
            output.add(new FacadeDriver(entry.getValue()));

        }
        return output;
    }

    public LinkedList<FacadeTruck> getTrucks() {
        LinkedList<FacadeTruck> output = new LinkedList<>();
        HashMap<String, Truck> trucks = rc.getTrucks();
        for (Map.Entry<String, Truck> entry : trucks.entrySet()) {
            output.add(new FacadeTruck(entry.getValue()));
        }
        return output;
    }

    public void saveReport(LocalDate date, int shift) {
        rc.saveReport(date, shift);
    }


    public FacadeDriver getDriver(String driverID) {
        return new FacadeDriver(rc.getDriver(driverID));
    }

    public FacadeTruck getTruck(String truckNumber) {
        return new FacadeTruck(rc.getTruck(truckNumber));

    }

    public HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> getDayAndDrivers() throws IllegalArgumentException {
        return rc.getDaysAndDrivers();
    }

    public LinkedList<FacadeTruck> getAvailableTrucks(LocalDate date, int shift) {
        LinkedList<Truck> trucks = rc.getAvailableTrucks(date, shift);
        LinkedList<FacadeTruck> output = new LinkedList<>();
        for (Truck t : trucks) {
            output.add(new FacadeTruck(t));
        }
        return output;
    }

    public LinkedList<FacadeDriver> getAvailableDrivers(LocalDate date, int shift) {
        LinkedList<Driver> trucks = rc.getAvailableDrivers(date, shift);
        LinkedList<FacadeDriver> output = new LinkedList<>();
        for (Driver t : trucks) {
            output.add(new FacadeDriver(t));
        }
        return output;
    }

    public void deleteDriverConstarint(String id, LocalDate date, int leavingHour) {
        rc.deleteDriverConstraint(id, date, leavingHour);
    }

    public void deleteTruckConstarint(String id, LocalDate date, int leavingHour) {
        rc.deleteTruckConstraint(id, date, leavingHour);
    }

    public void addDriverConstarint(String id, LocalDate date, int turnTimeToShift) {
        rc.addDriverConstraint(id,date,turnTimeToShift);
    }

    public void addTruckConstraint(String id, LocalDate date, int leavingHour) {
        rc.addTruckConstraint(id,date,leavingHour);
    }

    public void upload(HashMap driver_cons, HashMap trucks_cons) throws SQLException {
        rc.upload(driver_cons, trucks_cons);
    }
}

/*
    public void replaceTruck(String old_truck, String truckNumber) {
        rc.replaceTruck(old_truck, truckNumber);
    }
*/


/*
    public void makeUnavailable_Driver(String driver) throws NoSuchElementException {
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
*/

  /*  public LinkedList<FacadeTruck> getAvailableTrucks() {
        LinkedList<FacadeTruck> output =  new LinkedList<>();
        HashMap<String , Truck> trucks =  rc.getAvailableTrucks();
        for (Map.Entry<String ,Truck> entry:trucks.entrySet())
        {
            output.add(new FacadeTruck( entry.getValue()));
        }
        return output;
    }*/