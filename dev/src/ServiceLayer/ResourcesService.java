package ServiceLayer;

import BusinessLayer.EmployeePackage.EmployeeException;
import BusinessLayer.TruckingPackage.ResourcesPackage.*;
import InfrastructurePackage.Pair;
import ServiceLayer.FacadeObjects.FacadeDriver;
import ServiceLayer.FacadeObjects.FacadeTruck;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;

public class ResourcesService {
    private final ResourcesController rc;
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
    public HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> getDayAndDrivers() throws IllegalArgumentException, EmployeeException {
        return rc.getDaysAndDrivers();
    }

    public Pair<Pair<FacadeDriver, FacadeTruck>, Integer> findDriverAndTruckForDateFromExisting(LocalDate date, Pair<LinkedList<String>, LinkedList<String>> busyTrucks) throws EmployeeException {
        Pair<Pair<Driver, Truck>, Integer> p = rc.findDriverAndTruckForDateFromExisting(date, busyTrucks);
        if (p != null) {
            Pair<Pair<FacadeDriver, FacadeTruck>, Integer> result = new Pair<>(new Pair<>(new FacadeDriver(p.getFirst().getFirst()), new FacadeTruck(p.getFirst().getSecond())), p.getSecond());
            return result;
        }
        return null;
    }

    /**
     * @param date
     * @return the Truck and Driver, returns null if cannot
     */

    public Pair<Pair<FacadeDriver, FacadeTruck>, Integer> findDriverAndTruckForDateFromPool(LocalDate date, Pair<LinkedList<String>, LinkedList<String>> busyTrucks) throws EmployeeException, SQLException {
        Pair<Pair<Driver, Truck>, Integer> p = rc.findDriverAndTruckForDateFromPool(date, busyTrucks);
        if (p != null) {
            Pair<Pair<FacadeDriver, FacadeTruck>, Integer> result =
                    new Pair<>(new Pair<>(new FacadeDriver(p.getFirst().getFirst()), new FacadeTruck(p.getFirst().getSecond())), p.getSecond());
            return result;
        }
        return null;
    }


    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException, SQLException {
        rc.addTruck(model, licenseNumber, weightNeto, maxWeight);
    }

    public LinkedList<FacadeDriver> getDrivers() {
        LinkedList<FacadeDriver> drivers = new LinkedList<>();
        for (Driver d : rc.getDrivers()) {
            drivers.add(new FacadeDriver(d));
        }
        return drivers;
    }

    public LinkedList<FacadeTruck> getTrucks() {
        LinkedList<FacadeTruck> trucks = new LinkedList<>();
        for (Truck t : rc.getTrucks())
            trucks.add(new FacadeTruck(t));
        return trucks;
    }

    public int getMaxWeight(String driverID, String truckNumber) {
        return rc.getMaxWeight(driverID, truckNumber);
    }

    public void deleteDriverConstraint(String id, LocalDate date, int leavingHour) {
        rc.deleteDriverConstraint(id, date, leavingHour);
    }

    public void deleteTruckConstraint(String id, LocalDate date, int leavingHour) {
        rc.deleteTruckConstraint(id, date, leavingHour);
    }

    public void addDriverConstraint(String id, LocalDate date, int turnTimeToShift) {
        rc.addDriverConstraint(id, date, turnTimeToShift);
    }

    public void addTruckConstraint(String id, LocalDate date, int leavingHour) {
        rc.addTruckConstraint(id, date, leavingHour);
    }

    public void upload(HashMap driver_cons, HashMap trucks_cons) throws SQLException {
        rc.upload(driver_cons, trucks_cons);
    }

    public int getPossibleWeightByDate(LocalDate date, Pair<LinkedList<String>, LinkedList<String>> busyTrucks) throws EmployeeException, SQLException {
        return rc.getPossibleWeightByDate(date, busyTrucks);
    }
}
