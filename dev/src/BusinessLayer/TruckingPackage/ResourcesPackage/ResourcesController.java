package BusinessLayer.TruckingPackage.ResourcesPackage;

import BusinessLayer.EmployeePackage.EmployeeException;
import BusinessLayer.EmployeePackage.ShiftPackage.ShiftController;
import DataAccessLayer.DalControllers.TruckingControllers.DalDriverController;
import DataAccessLayer.DalControllers.TruckingControllers.DalTruckController;
import DataAccessLayer.DalObjects.TruckingObjects.DalDriver;
import DataAccessLayer.DalObjects.TruckingObjects.DalTruck;
import InfrastructurePackage.Pair;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static java.lang.System.*;

public class ResourcesController {
    private HashMap<String, Driver> drivers;
    private HashMap<String, Truck> trucks;
    private LinkedList<Driver> driversByLicense;
    private static ResourcesController instance = null;

    private HashMap<String, HashMap<LocalDate, Integer>> drivers_constraints;
    private HashMap<String, HashMap<LocalDate, Integer>> trucks_constraints;

    private ResourcesController() {
        drivers = new HashMap<>();
        trucks = new HashMap<>();
        driversByLicense = new LinkedList<>();
        drivers_constraints = new HashMap<>();
        trucks_constraints = new HashMap<>();
    }

    public static ResourcesController getInstance() {
        if (instance == null)
            instance = new ResourcesController();
        return instance;
    }

    public HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> getDaysAndDrivers() throws IllegalArgumentException, EmployeeException {
        HashMap<LocalDate, HashMap<Integer, List<String>>> received = null;
        received = ShiftController.getInstance().getDaysAndDrivers();

        HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> output = new HashMap<>();
        for (Map.Entry<LocalDate, HashMap<Integer, List<String>>> entry : received.entrySet()) {     // loop for each date
            LocalDate date = entry.getKey();
            HashMap<Integer, LinkedList<String>> temp = new HashMap<>();
            output.put(date, new HashMap<>());
            for (Map.Entry<Integer, List<String>> shift : entry.getValue().entrySet()) {     // loop for each shift
                if (shift.getKey().equals(2)) {
                    for (String id : shift.getValue()) {                                            // runs through drivers with shift number = 2
                        //HashMap<Integer, LinkedList<String>> hash = output.get(date);

                        for (int i = 0; i < 2; i++) {                                            // i presents the current shift
                            if (isDriverAvailable(id, date, i) && hasAvailableTrucks(date, i)) {

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
                    if (isDriverAvailable(id, date, shift.getKey()) && hasAvailableTrucks(date, shift.getKey())) {
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

    public Pair<Pair<Driver, Truck>, Integer> findDriverAndTruckForDateFromExisting(LocalDate date, Pair<LinkedList<String>, LinkedList<String>> busyTrucks) throws EmployeeException {
        LinkedList<Pair<Pair<Driver, Truck>, Integer>> list = findListDriversAndTrucksFromExisting(date, busyTrucks);
        if (list != null && !list.isEmpty()) {
            return list.getFirst();
        } else return null;
    }

    private LinkedList<Pair<Pair<Driver, Truck>, Integer>> findListDriversAndTrucksFromExisting(LocalDate date, Pair<LinkedList<String>, LinkedList<String>> busyTrucks) throws EmployeeException {
        HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> shiftDrivers = new HashMap<>();
        HashMap<Integer, LinkedList<String>> temp = new HashMap<>();

        temp.put(0, turnDriversToList(getAvailableDrivers(date, 0)));
        temp.put(1, turnDriversToList(getAvailableDrivers(date, 1)));
        shiftDrivers.put(date, temp);

        LinkedList<Truck> freeTrucks = new LinkedList<>();
        LinkedList<Pair<Pair<Driver, Truck>, Integer>> result = new LinkedList<>();
        for (Map.Entry<String, Truck> entry : trucks.entrySet()) {
            if (!busyTrucks.getFirst().contains(entry.getKey())) {
                freeTrucks.add(entry.getValue());
            }
        }

        if (shiftDrivers.containsKey(date)) {
            HashMap<Integer, LinkedList<String>> day = shiftDrivers.get(date);//Hash Map with the two shifts
            if (day.get(0) != null) {
                LinkedList<String> dayDrivers = day.get(0);

                dayDrivers = sortByLicense(dayDrivers);
                freeTrucks.sort(Comparator.comparingInt(Truck::getMaxWeight));
                for (int i = 0; i < Math.min(dayDrivers.size(), freeTrucks.size()); i++) {
                    Driver d = drivers.get(dayDrivers.removeFirst());
                    Truck t = freeTrucks.removeFirst();
                    result.add(new Pair<>(new Pair<>(d, t), 0));
                }
            }

            //------ Night Shift ---------------------
            LinkedList<String> nightDrivers = new LinkedList<>();
            if (day.get(1) != null) {
                for (String s : day.get(1)) {
                    if (day.get(0) == null || !day.get(0).contains(s)) {
                        nightDrivers.add(s);//to make sure no duplicates
                    }
                }
                nightDrivers = sortByLicense(nightDrivers);
                freeTrucks = new LinkedList<>();
                for (Map.Entry<String, Truck> entry : trucks.entrySet()) {
                    if (!busyTrucks.getSecond().contains(entry.getKey())) {
                        freeTrucks.add(entry.getValue());
                    }
                }
                Collections.sort(freeTrucks, new Comparator<Truck>() {
                    @Override
                    public int compare(Truck o1, Truck o2) {
                        return o2.getMaxWeight() - o1.getMaxWeight();
                    }
                });
                int limit = Math.min(nightDrivers.size(), freeTrucks.size());
                for (int i = 0; i < limit; i++) {
                    Driver d = drivers.get(nightDrivers.removeFirst());
                    Truck t = freeTrucks.removeFirst();
                    result.add(new Pair<>(new Pair<>(d, t), 1));
                }
            }
            return result;
        }
        return null;
    }

    private LinkedList<String> turnDriversToList(LinkedList<Driver> drivers) {
        LinkedList<String> output = new LinkedList<>();
        for (Driver d : drivers) {
            output.add(d.getID());
        }
        return output;
    }

    private LinkedList<String> sortByLicense(LinkedList<String> dayDrivers) {
        LinkedList<String> sorted = new LinkedList<>();
        for (String s : dayDrivers) {
            if (drivers.get(s).getLicenseType() == Driver.License.C)
                sorted.addLast(s);
            else sorted.addFirst(s);
        }
        return sorted;
    }

    public Pair<Pair<Driver, Truck>, Integer> findDriverAndTruckForDateFromPool
            (LocalDate date, Pair<LinkedList<String>, LinkedList<String>> busyTrucks) throws EmployeeException, SQLException {
        LinkedList<Pair<Pair<Driver, Truck>, Integer>> pairs = findListDriverAndTruckForDateFromPool(date, busyTrucks);
        if (pairs != null) {
            for (Pair<Pair<Driver, Truck>, Integer> p : pairs) {
                Driver d = p.getFirst().getFirst();
                if (ShiftController.getInstance().addDriverToShift(d.getID(), date, 0)
                        || ShiftController.getInstance().addDriverToShift(d.getID(), date, 1)) {
                    return p;
                }
            }
        }
        return null;
    }

    private LinkedList<Pair<Pair<Driver, Truck>, Integer>> findListDriverAndTruckForDateFromPool
            (LocalDate date, Pair<LinkedList<String>, LinkedList<String>> busyTrucks) throws EmployeeException, SQLException {
        // all drivers that can be called from home
        LinkedList<String> morningAvailableDrivers = new LinkedList<>();
        LinkedList<String> eveningAvailableDrivers = new LinkedList<>();
        for (Map.Entry<String, Driver> driver : drivers.entrySet()) {
            HashMap<LocalDate, Integer> cons = drivers_constraints.get(driver.getKey());
            if (cons.get(date) == null || cons.isEmpty()) {
                if (ShiftController.getInstance().canAddDriverToShift(driver.getKey(), date, 0)) {
                    morningAvailableDrivers.add(driver.getKey());
                }
                if (ShiftController.getInstance().canAddDriverToShift(driver.getKey(), date, 1)) {

                    eveningAvailableDrivers.add(driver.getKey());
                }
            }
        }

        morningAvailableDrivers = sortByLicense(morningAvailableDrivers);
        eveningAvailableDrivers = sortByLicense(eveningAvailableDrivers);

        // finds trucks now
        LinkedList<Truck> morningAvailableTrucks = new LinkedList<>();
        LinkedList<Truck> eveningAvailableTrucks = new LinkedList<>();
        for (Map.Entry<String, Truck> truck : trucks.entrySet()) {
            if (!busyTrucks.getFirst().contains(truck.getKey())) {
                morningAvailableTrucks.add(truck.getValue());
            }
            if (!busyTrucks.getSecond().contains(truck.getKey())) {
                eveningAvailableTrucks.add(truck.getValue());
            }
        }
        Collections.sort(morningAvailableTrucks, new Comparator<Truck>() {
            @Override
            public int compare(Truck o1, Truck o2) {
                return o2.getMaxWeight() - o1.getMaxWeight();
            }
        });
        Collections.sort(eveningAvailableTrucks, new Comparator<Truck>() {
            @Override
            public int compare(Truck o1, Truck o2) {
                return o2.getMaxWeight() - o1.getMaxWeight();
            }
        });

        // now all linkedLists are sorted and properly inserted.
        // merging the lists

        LinkedList<Pair<Pair<Driver, Truck>, Integer>> output = new LinkedList<>();
        // loop through mornings
        for (String driver : morningAvailableDrivers) {
            if (morningAvailableTrucks.isEmpty())
                break;
            output.add(new Pair<>(new Pair<>(drivers.get(driver), morningAvailableTrucks.removeFirst()), 0));
        }
        for (String driver : eveningAvailableDrivers) {
            if (morningAvailableTrucks.isEmpty())
                break;
            output.add(new Pair<>(new Pair<>(drivers.get(driver), eveningAvailableTrucks.removeFirst()), 1));
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

    public void addTruck(String model, String licenseNumber, int weightNeto, int maxWeight) throws KeyAlreadyExistsException, SQLException {
        if (!trucks.containsKey(licenseNumber)) {
            Truck truck = new Truck(model, licenseNumber, weightNeto, maxWeight);
            trucks.put(licenseNumber, truck);
            trucks_constraints.put(licenseNumber, new HashMap<>());
        } else {
            throw new KeyAlreadyExistsException("Truck already added");
        }
    }

    public LinkedList<Driver> getDrivers() {
        LinkedList<Driver> result = new LinkedList<>();
        for (Map.Entry<String, Driver> entry : drivers.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public LinkedList<Truck> getTrucks() {
        LinkedList<Truck> result = new LinkedList<>();
        for (Map.Entry<String, Truck> entry : trucks.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }

    public int getMaxWeight(String driverID, String truckNumber) {
        int driverWeight;
        int truckWeight = trucks.get(truckNumber).getMaxWeight() - trucks.get(truckNumber).getWeightNeto();
        if (drivers.get(driverID).getLicenseType() == Driver.License.C) {
            driverWeight = 12000;
        } else driverWeight = 20000;
        return Math.min(driverWeight, truckWeight);
    }

    public void addDriver(String id, String name, Driver.License licenseType) throws KeyAlreadyExistsException, SQLException {
        if (!drivers.containsKey(id)) {
            Driver driver;
            driver = new Driver(id, name, licenseType);

            drivers.put(id, driver);
            if (licenseType == Driver.License.C1)
                driversByLicense.addLast(driver);
            else driversByLicense.addFirst(driver);
            drivers_constraints.put(id, new HashMap<>());

        } else {
            throw new KeyAlreadyExistsException("Driver already works here");
        }
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

        if (trucks_constraints.containsKey(licenseNumber)) {
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
            HashMap<LocalDate, Integer> cons = constraints.get(id);
            Integer sh = cons.get(date);
            if (sh != null) {
                if (sh.equals(shift))
                    cons.remove(date);
                else if (shift.equals(1))
                    cons.put(date, 0);
                else
                    cons.put(date, 1);
            }
        } else
            throw new IllegalArgumentException("couldn't find " + obj + " with such " + idn);
    }

    private void addConstraint(String id, LocalDate date, Integer shift, HashMap<String, HashMap<LocalDate, Integer>> constraint_hash) throws IllegalArgumentException {
        String name;
        String obj;
        String idn;
        if (constraint_hash == trucks_constraints) {
            name = "trucks";
            obj = "Truck";
            idn = "id";
        } else {
            name = "drivers";
            idn = "ID Number";
            obj = "Driver";
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("the constraint's date is invalid");
        }
        if (constraint_hash.isEmpty()) {
            throw new IllegalArgumentException("no " + name + " in the system");
        }

        if (constraint_hash.containsKey(id)) {
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

    public void deleteDriverConstraint(String id, LocalDate date, Integer shift) {
        deleteConstraint(id, date, shift, drivers_constraints);
    }

    public void deleteTruckConstraint(String id, LocalDate date, Integer shift) {
        deleteConstraint(id, date, shift, trucks_constraints);
    }

    public void upload(HashMap<String, HashMap<LocalDate, Integer>> driver_cons, HashMap<String, HashMap<LocalDate, Integer>> truck_cons) throws SQLException {
        DalDriverController driverController = DalDriverController.getInstance();
        DalTruckController truckController = DalTruckController.getInstance();
        LinkedList<DalTruck> dalTrucks = truckController.load();
        for (DalTruck truck : dalTrucks) {
            trucks.put(truck.getLicenseNumber(), new Truck(truck));
            trucks_constraints.put(truck.getLicenseNumber(), new HashMap<>());
        }
        for (Map.Entry<String, HashMap<LocalDate, Integer>> entry : truck_cons.entrySet()) {
            for (Map.Entry<LocalDate, Integer> dates : entry.getValue().entrySet()) {
                addTruckConstraint(entry.getKey(), dates.getKey(), dates.getValue());

            }
        }

        LinkedList<DalDriver> dalDrivers = driverController.load();
        for (DalDriver driver : dalDrivers) {
            Driver bDriver = new Driver(driver);
            drivers.put(driver.getID(), bDriver);
            driversByLicense.add(bDriver);
            drivers_constraints.put(driver.getID(), new HashMap<>());
        }
        for (Map.Entry<String, HashMap<LocalDate, Integer>> entry : driver_cons.entrySet()) {
            for (Map.Entry<LocalDate, Integer> dates : entry.getValue().entrySet()) {
                addDriverConstraint(entry.getKey(), dates.getKey(), dates.getValue());

            }
        }
    }

    public int getPossibleWeightByDate(LocalDate date, Pair<LinkedList<String>, LinkedList<String>> busyTrucks) throws EmployeeException, SQLException {
        int sum = 0;
        LinkedList<Pair<Pair<Driver, Truck>, Integer>> poolList = findListDriverAndTruckForDateFromPool(date, busyTrucks);
        for (Pair<Pair<Driver, Truck>, Integer> pair : poolList) {
            if (ShiftController.getInstance().canAddDriverToShift(pair.getFirst().getFirst().getID(), date, pair.getSecond())) {
                Driver d = pair.getFirst().getFirst();
                Truck t = pair.getFirst().getSecond();
                sum += Math.min(d.getLicenseType().getSize(), t.getMaxWeight() - t.getWeightNeto());
            }
        }

        LinkedList<Pair<Pair<Driver, Truck>, Integer>> existingList = findListDriversAndTrucksFromExisting(date, busyTrucks);
        for (Pair<Pair<Driver, Truck>, Integer> pair : existingList) {
            if (ShiftController.getInstance().canAddDriverToShift(pair.getFirst().getFirst().getID(), date, pair.getSecond())) {
                Driver d = pair.getFirst().getFirst();
                Truck t = pair.getFirst().getSecond();
                sum += Math.min(d.getLicenseType().getSize(), t.getMaxWeight() - t.getWeightNeto());
            }
        }
        return sum;
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


    public LinkedList<Driver> getAvailableDrivers(LocalDate date, int shift) throws EmployeeException {
        LinkedList<String> working_drivers = getDriversForDate(date, shift);
        LinkedList<Driver> output = new LinkedList<>();
        if (working_drivers != null && !working_drivers.isEmpty()) {
            for (Map.Entry<String, HashMap<LocalDate, Integer>> entry : drivers_constraints.entrySet()) {
                if (working_drivers.contains(entry.getKey())) {
                    if (!entry.getValue().containsKey(date))
                        output.add(getDriver(entry.getKey()));
                    else {
                        if (!entry.getValue().get(date).equals(2) && !entry.getValue().get(date).equals(shift)) {
                            output.add(getDriver(entry.getKey()));
                        }
                    }
                }
            }
        }
        return output;
    }

    private LinkedList<String> getDriversForDate(LocalDate date, int shift) throws EmployeeException {
        HashMap<LocalDate, HashMap<Integer, LinkedList<String>>> all = getDaysAndDrivers();
        LinkedList<String> output;
        output = all.get(date).get(shift);
        return output;
    }

    public Driver getDriver(String driverID) {
        return drivers.get(driverID);
    }

    public Truck getTruck(String truckNumber) {
        return trucks.get(truckNumber);
    }

    private boolean isDriverAvailable(String driver, LocalDate date, int shift) {

        Integer cons = drivers_constraints.get(driver).get(date);
        if (cons == null)
            return true;
        else {
            return (!cons.equals(shift)) && !cons.equals(2);
        }
    }

    private boolean isTruckAvailable(String truck, LocalDate date, int shift) {
        Integer cons = trucks_constraints.get(truck).get(date);
        if (cons == null)
            return true;
        else {
            return (!cons.equals(shift)) && !cons.equals(2);
        }
    }
}
