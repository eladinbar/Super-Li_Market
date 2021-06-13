package ServiceLayer;
import BusinessLayer.EmployeePackage.EmployeeException;
import BusinessLayer.TruckingPackage.ResourcesPackage.*;
import InfrastructurePackage.Pair;
import ServiceLayer.FacadeObjects.FacadeDriver;
import ServiceLayer.FacadeObjects.FacadeTruck;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NoSuchElementException;

import static java.lang.System.exit;

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


    //returns day,shiftType,List of drivers ID
    public HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> getDayAndDrivers() throws IllegalArgumentException {
        return rc.getDaysAndDrivers();
    }

    public Pair<Pair<FacadeDriver, FacadeTruck>,Integer> findDriverAndTruckForDateFromExisting(LocalDate date,Pair<LinkedList<String>,LinkedList<String>> busyTrucks){
        Pair<Pair<Driver,Truck>,Integer> p= rc.findDriverAndTruckForDateFromExisting(date,busyTrucks);
        Pair<Pair<FacadeDriver, FacadeTruck>,Integer> result= new Pair<>(new Pair<>(new FacadeDriver(p.getFirst().getFirst()),new FacadeTruck(p.getFirst().getSecond())),p.getSecond());
        return result;
    }




    /**
     *
     * @param date
     * @return the Truck and Driver, returns null if cannot
     */


    //TODO reimplement
    public Pair<Pair<FacadeDriver, FacadeTruck>,Integer> findDriverAndTruckForDateFromPool(LocalDate date,Pair<LinkedList<String>,LinkedList<String>> busyTrucks){
        Pair<Pair<Driver, Truck>,Integer> p= null;
        try {
            p = rc.findDriverAndTruckForDateFromPool(date,busyTrucks);
        } catch (EmployeeException e) {
            e.printStackTrace();
            exit(1);
        }
        Pair<Pair<FacadeDriver, FacadeTruck>,Integer> result=
                new Pair<>(new Pair<>(new FacadeDriver(p.getFirst().getFirst()),new FacadeTruck(p.getFirst().getSecond())),p.getSecond());
        return result;
    }


    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException, SQLException {
        rc.addTruck(model, licenseNumber, weightNeto, maxWeight);
    }

    public LinkedList<FacadeDriver> getDrivers() {
        LinkedList<FacadeDriver> drivers=new LinkedList<>();
        for (Driver d:rc.getDrivers())
        {
            drivers.add(new FacadeDriver(d));
        }
        return drivers;
    }

    public LinkedList<FacadeTruck> getTrucks(){
        LinkedList<FacadeTruck> trucks=new LinkedList<>();
        for (Truck t:rc.getTrucks())
            trucks.add(new FacadeTruck(t));
        return trucks;
    }

    public int getMaxWeight(String driverID, String truckNumber) {
        return rc.getMaxWeight(driverID,truckNumber);
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

    public int getPossibleWeightByDate(LocalDate date,Pair<LinkedList<String>,LinkedList<String>> busyTrucks) throws EmployeeException, SQLException {
       return rc.getPossibleWeightByDate(date,busyTrucks);
    }
/*
    public Truck chooseTruck(String truck,LocalDate date, int shift) throws NoSuchElementException, IllegalStateException {

        return rc.chooseTruck(truck,date,shift);

    }

    public FacadeDriver chooseDriver(String driver, LocalDate date, int shift) throws IllegalStateException, NoSuchElementException {

        return new FacadeDriver(rc.chooseDriver(driver,date,shift));


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



    */
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
