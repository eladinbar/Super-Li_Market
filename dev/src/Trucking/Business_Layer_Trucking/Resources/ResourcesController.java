package Trucking.Business_Layer_Trucking.Resources;

import Employees.EmployeeException;
import Employees.business_layer.Shift.ShiftController;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.List;

public class ResourcesController {
    private HashMap<String, Driver> drivers;
    private HashMap<String, Truck> trucks;
    private LinkedList<Driver> driversByLicense;
    private static ResourcesController instance = null;
    private String currDriverID;
    private String currTruckNumber;

    private HashMap<String, HashMap<LocalDate, Integer>> drivers_constraints;
    private HashMap<String, HashMap<LocalDate, Integer>> trucks_constraints;

    private ResourcesController() {
        drivers = new HashMap<>();
        trucks = new HashMap<>();
        driversByLicense = new LinkedList<>();
        this.currDriverID = "-1";
        this.currTruckNumber = "-1";

        drivers_constraints = new HashMap<>();
        trucks_constraints = new HashMap<>();

    }

    public static ResourcesController getInstance() {
        if (instance == null)
            instance = new ResourcesController();
        return instance;
    }

    public void addDriver(String id, String name, Driver.License licenseType) throws KeyAlreadyExistsException, SQLException {
        if (!drivers.containsKey(id)) {
            Driver driver = new Driver(id, name, licenseType);
            drivers.put(id, driver);
            if (licenseType == Driver.License.C1)
                driversByLicense.addLast(driver);
            else driversByLicense.addFirst(driver);
            drivers_constraints.put(id, new HashMap<>());

        } else {
            throw new KeyAlreadyExistsException("Driver already works here");
        }

    }

    public void deleteDriverConstraint(String id, LocalDate date, Integer shift) {
        deleteConstraint(id, date, shift, drivers_constraints);
    }

    public void deleteTruckConstraint(String id, LocalDate date, Integer shift) {
        deleteConstraint(id, date, shift, trucks_constraints);
    }


    public void addDriverConstraint(String driver_id, LocalDate date, Integer shift) throws IllegalArgumentException {
        addConstraint(driver_id, date, shift, drivers_constraints);
    }

    public void addTruckConstraint(String licenseNumber, LocalDate date, Integer shift) throws IllegalArgumentException {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("the constraint's date is invalid");
        }
        if (trucks_constraints.isEmpty()) {
            throw new IllegalArgumentException("no trucks in the system");
        }

        if (trucks_constraints.containsKey(licenseNumber)) { // TODO need to check if contains works with Strings
            addConstraint(licenseNumber, date, shift, trucks_constraints);


        } else
            throw new IllegalArgumentException("couldn't find truck with such license Number");
    }

    private void deleteConstraint(String id, LocalDate date, Integer shift, HashMap<String, HashMap<LocalDate, Integer>> constraints) {
        String name;
        String obj;
        String idn;
        if (constraints == trucks_constraints) {
            name = "trucks";
            obj = "Truck";
            idn = "id";
        } else {
            name = "drivers";
            idn = "license Number";
            obj = "Driver";
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("the constraint's date is invalid");
        }
        if (constraints.isEmpty()) {
            throw new IllegalArgumentException("no " + name + " in the system");
        }
        if (constraints.containsKey(id)) {
            HashMap<LocalDate, Integer> cons = constraints.get(id); // TODO need to check it deletes it
            Integer sh = cons.get(date);
            if (sh != null) {
                if (sh.equals(shift))
                    cons.remove(date);
                else if (shift.equals(1))
                    cons.put(date, 0);
                else
                    cons.put(date, 1);

            }

        }
        else
            throw new IllegalArgumentException("couldn't find " + obj + " with such " + idn);

    }

    private void addConstraint(String id, LocalDate date, Integer shift, HashMap<String, HashMap<LocalDate, Integer>> constraint_hash) {
        String name;
        String obj;
        String idn;
        if (constraint_hash == trucks_constraints) {
            name = "trucks";
            obj = "Truck";
            idn = "id";
        } else {
            name = "drivers";
            idn = "license Number";
            obj = "Driver";
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("the constraint's date is invalid");
        }
        if (constraint_hash.isEmpty()) {
            throw new IllegalArgumentException("no " + name + " in the system");
        }

        if (constraint_hash.containsKey(id)) { // TODO need to check if contains works with Strings
            HashMap<LocalDate, Integer> cons = constraint_hash.get(id);
            if (cons.get(date) == null) {
                cons.put(date, shift);
            } else if (cons.get(date) == 2 || cons.get(date).equals(shift)) {
                throw new IllegalArgumentException(obj + "'s constraint is already exist");
            } else {
                cons.put(date, 2);

            }


        } else
            throw new IllegalArgumentException("couldn't find " + obj + " with such " + idn);
    }


    public HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> getDaysAndDrivers() throws IllegalArgumentException {
        HashMap<LocalDate, HashMap<Integer, List<String>>> received = null;
        try {
            received = ShiftController.getInstance().getDaysAndDrivers();
        } catch (EmployeeException e) {
            System.out.println("Employee's Exception thrown in Resources Service. exits...");
            System.exit(1);
        }


        HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> output = new HashMap<>();
        for (Map.Entry<LocalDate, HashMap<Integer, List<String>>> entry : received.entrySet()) {     // loop for each date
            LocalDate date = entry.getKey();
            HashMap<Integer,LinkedList<String> >temp =  new HashMap<>();
            output.put(date, new HashMap<>());
            for (Map.Entry<Integer, List<String>> shift : entry.getValue().entrySet()) {     // loop for each shift
                if (shift.getKey().equals(2)) {
                    for (String id : shift.getValue()) {                                            // runs through drivers with shift number = 2
                        //HashMap<Integer, LinkedList<String>> hash = output.get(date);

                        for (int i = 0; i < 2; i++) {                                            // i presents the current shift
                            if (isDriverAvailable(id, date, i) && hasAvailableTrucks(date,i)) {

                                if (temp.get(i) == null) {
                                    temp.put(i, new LinkedList<>());
                                }
                                if (!temp.get(i).contains(id)) {
                                    temp.get(shift.getKey()).add(id);
                                }
                            }
                        }


                    }
                }
                for (String id : shift.getValue()) {                                               // runs through drivers
                    if (isDriverAvailable(id, date, shift.getKey()) && hasAvailableTrucks(date,shift.getKey())) {
                   /*     HashMap<Integer, LinkedList<String>> hash = output.get(date);
                        hash.computeIfAbsent(shift.getKey(), k -> new LinkedList<>());*/
                        if (temp.get(shift.getKey()) == null)
                            temp.put(shift.getKey(), new LinkedList<>());

                        if (!temp.get(shift.getKey()).contains(id))
                            temp.get(shift.getKey()).add(id);
                    }
                }
                if (!temp.isEmpty()) {
                    output.put(date, temp);
                }
                temp = new HashMap<>();
            }


        }
        return output;


    }


    private boolean isDriverAvailable(String id, LocalDate date, Integer shift) {
        if (!drivers_constraints.containsKey(id))
            throw new IllegalArgumentException("cannot find this driver");
        HashMap<LocalDate, Integer> driverConst = drivers_constraints.get(id);
        if (!driverConst.containsKey(date))
            return true;
        else {
            Integer workingShift = driverConst.get(date);
            return ((workingShift.equals(2)) || workingShift.equals(shift));
        }

    }

    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException, SQLException {
        if (!trucks.containsKey(licenseNumber)) {
            Truck truck = new Truck(model, licenseNumber, weightNeto, maxWeight);
            trucks.put(licenseNumber, truck);
            trucks_constraints.put(licenseNumber, new HashMap<>());
        } else {
            throw new KeyAlreadyExistsException("Truck already added");
        }
    }

    public boolean hasAvailableTrucks(LocalDate date, int shift) {
        for (Map.Entry<String, HashMap<LocalDate, Integer>> entry : trucks_constraints.entrySet()) {
            Integer d = entry.getValue().get(date);

            if (d == null)
                return true;
            if (!d.equals(2) && !d.equals(shift))
                return true;
        }
        return false;
    }

    public Truck chooseTruck(String truck, LocalDate date, int shift) throws IllegalStateException, NoSuchElementException {

        if (trucks.containsKey(truck)) {
            if (isTruckAvailable(truck, date, shift)) {
                currTruckNumber = truck;
                return trucks.get(truck);
            } else throw new IllegalStateException("Truck already taken");
        } else throw new NoSuchElementException("No such truck");
    }

    public Driver chooseDriver(String driver, LocalDate date, int shift) throws IllegalStateException, NoSuchElementException {

        if (drivers.containsKey(driver)) {
            if (isDriverAvailable(driver, date, shift)) {
                currDriverID = driver;
                return drivers.get(driver);
            } else throw new IllegalStateException("Driver already taken");
        } else throw new NoSuchElementException("No such Driver");

    }


    public void saveReport(LocalDate date, int shift) {
        addDriverConstraint(currDriverID, date, shift);
        addTruckConstraint(currTruckNumber, date, shift);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<<<< getters setters >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public HashMap<String, Driver> getDrivers() {
        return drivers;
    }

    public HashMap<String, Truck> getTrucks() {
        return trucks;
    }


    public void setDrivers(HashMap<String, Driver> drivers) {
        this.drivers = drivers;
    }


    public static void setInstance(ResourcesController instance) {
        ResourcesController.instance = instance;
    }


    public Driver getDriver(String driverID) {
        return drivers.get(driverID);
    }

    public Truck getTruck(String truckNumber) {
        return trucks.get(truckNumber);
    }




    private LocalTime turnShiftToTime(Integer shift) {
        if (shift == 0)
            return LocalTime.of(6, 0);
        else
            return LocalTime.of(14, 0);
    }

    public LinkedList<Driver> getAvailableDrivers(LocalDate date, int shift) {
        LinkedList<String> working_drivers = getDriversForDate(date,shift);
        LinkedList<Driver> output = new LinkedList<>();
        for (Map.Entry<String, HashMap<LocalDate, Integer>> entry : drivers_constraints.entrySet()) {
            if(working_drivers.contains(entry.getKey())) {
                if (!entry.getValue().containsKey(date))
                    output.add(getDriver(entry.getKey()));
                else {
                    if (!entry.getValue().get(date).equals(2) && !entry.getValue().get(date).equals(shift)) {
                        output.add(getDriver(entry.getKey()));
                    }
                }
            }
        }
        return output;
    }

    private LinkedList<String> getDriversForDate(LocalDate date, int shift) {
        HashMap<LocalDate, HashMap<Integer,LinkedList<String >>> all = getDaysAndDrivers();
        LinkedList<String> output ;
        output = all.get(date).get(shift);
        return output;
    }

    public LinkedList<Truck> getAvailableTrucks(LocalDate date, int shift) {
        LinkedList<Truck> output = new LinkedList<>();
        for (Map.Entry<String, HashMap<LocalDate, Integer>> entry : trucks_constraints.entrySet()) {
            if (!entry.getValue().containsKey(date))
                output.add(getTruck(entry.getKey()));
            else {
                if (!entry.getValue().get(date).equals(2) && !entry.getValue().get(date).equals(shift)) {
                    output.add(getTruck(entry.getKey()));
                }
            }
        }
        return output;
    }

    private boolean isDriverAvailable(String driver, LocalDate date, int shift) {

        Integer cons = drivers_constraints.get(driver).get(date);
        if (cons == null)
            return true;
        else {
            if ((cons.equals(shift)) || cons.equals(2))
                return false;
            else return true;
        }
    }

    private boolean isTruckAvailable(String truck, LocalDate date, int shift) {
        Integer cons = trucks_constraints.get(truck).get(date);
        if (cons == null)
            return true;
        else {
            if ((cons.equals(shift)) || cons.equals(2))
                return false;
            else return true;
        }
    }
    public void setTrucks(HashMap<String, Truck> trucks) {
        this.trucks = trucks;
    }


    public String getCurrDriverID() {
        return currDriverID;
    }

    public String getCurrTruckNumber() {
        return currTruckNumber;
    }

    public void setCurrDriverID(String currDriverID) {
        this.currDriverID = currDriverID;
    }

    public void setCurrTruckNumber(String currTruckNumber) {
        this.currTruckNumber = currTruckNumber;
    }

    public void setDriversByLicense(LinkedList<Driver> driversByLicense) {
        this.driversByLicense = driversByLicense;
    }


/*
    public HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> getDaysAndDrivers() throws IllegalArgumentException {
        HashMap<LocalDate, HashMap<Integer, List<String>>> received = null;
        try {
            received = ShiftController.getInstance().getDaysAndDrivers();
        } catch (EmployeeException e) {
            System.out.println("Employee's Exception thrown in Resources Service. exits...");
            System.exit(1);
        }


        HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> output = new HashMap<>();
        for (Map.Entry<LocalDate, HashMap<Integer, List<String>>> entry : received.entrySet()) {     // loop for each date
            LocalDate date = entry.getKey();
            HashMap<Integer,LinkedList<String> >temp =  new HashMap<>();
            output.put(date, new HashMap<>());
            for (Map.Entry<Integer, List<String>> shift : entry.getValue().entrySet()) {     // loop for each shift
                if (shift.getKey().equals(2)) {
                    for (String id : shift.getValue()) {                                            // runs through drivers with shift number = 2
                        HashMap<Integer, LinkedList<String>> hash = output.get(date);

                        for (int i = 0; i < 2; i++) {                                            // i presents the current shift
                            if (isDriverAvailable(id, date, i) && hasAvailableTrucks(date,i)) {

                                if (hash.get(i) == null) {
                                    hash.put(shift.getKey(), new LinkedList<>());
                                }
                                if (!hash.get(i).contains(id)) {
                                    hash.get(shift.getKey()).add(id);
                                }
                            }
                        }


                    }
                }
                for (String id : shift.getValue()) {                                               // runs through drivers
                    if (isDriverAvailable(id, date, shift.getKey()) && hasAvailableTrucks(date,shift.getKey())) {
                        HashMap<Integer, LinkedList<String>> hash = output.get(date);
                        hash.computeIfAbsent(shift.getKey(), k -> new LinkedList<>());
                        if (!hash.get(shift.getKey()).contains(id))
                            hash.get(shift.getKey()).add(id);
                    }
                }
            }


        }
        return output;


    }*/

    /*

    public void deleteDriver(int id) throws NoSuchElementException {
        if (drivers.containsKey(id)) {
            Driver removed = drivers.remove(id);
            driversByLicense.remove(removed);
            drivers_constraints.remove(id);
        } else throw new NoSuchElementException("We do not hiring that person");
    }

    public void deleteTruck(int licenseNumber) throws NoSuchElementException {
        if (trucks.containsKey(licenseNumber)) {
            trucks.remove(licenseNumber);
            trucks_constraints.remove(licenseNumber);
        } else throw new NoSuchElementException("No such truck found");
    }
*/



    /*   public void replaceTruck(String old_truck, String truckNumber) {
        deleteTruckConstraint(currTruckNumber);
        this.currTruckNumber = truckNumber;
        makeUnavailable_Truck(truckNumber);
    }*/

      /*  public void makeUnavailable_Driver(String driver) throws NoSuchElementException {
        if (!drivers.containsKey(driver))
            throw new NoSuchElementException("Driver does not exist");
        drivers.get(driver).setUnavailable();
    }

    public void makeAvailable_Driver(String driver) {
        drivers.get(driver).makeAvailable();
    }

    public void makeUnavailable_Truck(String truck) {
        trucks.get(truck).setUnavailable();
    }

    public void makeAvailable_Truck(String truck) {
        trucks.get(truck).makeAvailable();
    }
*/


/*    public HashMap<String, Driver> getAvailableDrivers() {
        HashMap<String, Driver> result = new HashMap<>();
        for (Map.Entry<String, Driver> entry : drivers.entrySet()) {
            if (entry.getValue().isAvailable())
                result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }*/

   /* public HashMap<String, Truck> getAvailableTrucks() {
        HashMap<String, Truck> result = new HashMap<>();
        for (Map.Entry<String, Truck> entry : trucks.entrySet()) {
            if (entry.getValue().isAvailable())
                result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }*/


}
