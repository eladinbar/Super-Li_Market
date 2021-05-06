package Employees.business_layer.Shift;

import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ShiftController {
    private static ShiftController instance = null;

    private HashMap<LocalDate, WeeklyShiftSchedule> shifts;
    EmployeeController employeeController;

    private ShiftController() {
        shifts = new HashMap<> ( );
        employeeController = EmployeeController.getInstance ( );
    }

    public static ShiftController getInstance() {
        if (instance == null)
            instance = new ShiftController ( );
        return instance;
    }

    public WeeklyShiftSchedule getRecommendation(LocalDate startingDate) throws EmployeeException {
        if (startingDate.isBefore ( LocalDate.now ( ) ))
            throw new EmployeeException ( "Starting date has already passed." );
        if (startingDate.getDayOfWeek ( ) != DayOfWeek.SUNDAY)
            throw new EmployeeException ( "Starting date is not Sunday." );
        if (this.shifts.containsKey ( startingDate ))
            throw new EmployeeException ( "Weekly shift schedule is already exists in the system. \nYou can watch it and edit if you would like." );
        WeeklyShiftSchedule output = createEmptyWeeklyShiftSchedule ( startingDate );
        for ( int i = 0 ; i < 7 ; i++ ) {
            output.recommendShifts ( employeeController, i );
        }
        return output;
    }

    public WeeklyShiftSchedule createWeeklyShiftSchedule(LocalDate startingDate, Shift[][] shifts) throws EmployeeException {
        if (startingDate.isBefore ( LocalDate.now ( ) ))
            throw new EmployeeException ( "Starting date has already passed." );
        if (startingDate.getDayOfWeek ( ) != DayOfWeek.SUNDAY)
            throw new EmployeeException ( "Starting date is not sunday." );
        if (this.shifts.containsKey ( startingDate ))
            throw new EmployeeException ( "Weekly shift schedule is already exists in the system. \nYou can watch it and edit if you would like." );
        if (shifts == null)
            throw new EmployeeException ( "shifts are illegal." );
        for ( int i = 0 ; i < 5 ; i++ ) {
            checkManningVallidity2 ( shifts[i][0].getManning ( ) );
            checkDriverVallidity ( shifts[i][0].getManning () );
            checkManningVallidity2 ( shifts[i][1].getManning ( ) );
            checkDriverVallidity ( shifts[i][1].getManning () );
        }
        checkManningVallidity2 ( shifts[5][0].getManning ( ) );
        checkDriverVallidity ( shifts[5][0].getManning () );
        checkManningVallidity2 ( shifts[6][1].getManning ( ) );
        checkDriverVallidity ( shifts[6][1].getManning () );
        WeeklyShiftSchedule weeklyShiftSchedule = new WeeklyShiftSchedule ( startingDate, shifts );
        this.shifts.put ( startingDate, weeklyShiftSchedule );
        return weeklyShiftSchedule;
    }

    private void checkManningVallidity(HashMap<String, List<String>> manning) throws EmployeeException {
        for( Map.Entry<String, List<String>> entry : manning.entrySet () )
        {
            for(String employee : entry.getValue ()) {
                if (!employeeController.isExist ( entry.getKey ( ), employee ))
                    throw new EmployeeException ( "Id - " + employee + " and role - " + entry.getKey () + " does not exist in system.");
            }
        }
    }

    private void checkManningVallidity2(HashMap<Role, List<String>> manning) throws EmployeeException {
        for ( Map.Entry<Role, List<String>> entry : manning.entrySet ( ) ) {
            for ( String employee : entry.getValue ( ) ) {
                if (!employeeController.isExist ( entry.getKey ( ).name ( ), employee ))
                    throw new EmployeeException ( "Id - " + employee + " and role - " + entry.getKey ( ) + " does not exist in system." );
            }
        }
    }

    private void checkDriverVallidity(HashMap<Role, List<String>> manning) throws EmployeeException {
        if(manning.containsKey ( Role.driverC ) || manning.containsKey ( Role.driverC1 ))
            if(!manning.containsKey ( Role.storeKeeper ))
                throw new EmployeeException ( "Shift has driver/s without having a store keeper." );
    }

    private void checkDriverVallidity2(HashMap<String , List<String>> manning) throws EmployeeException {
        if(manning.containsKey ( Role.valueOf ( "driverC" ) ) || manning.containsKey (Role.valueOf ( "driverC1" ) ))
            if(!manning.containsKey ( Role.valueOf ( "storeKeeper" ) ))
                throw new EmployeeException ( "Shift has driver/s without having a store keeper." );
    }

    public WeeklyShiftSchedule createEmptyWeeklyShiftSchedule(LocalDate startingDate) throws EmployeeException {
        if(startingDate.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "starting day has already passed." );
        if(startingDate.getDayOfWeek () != DayOfWeek.SUNDAY)
            throw new EmployeeException ( "Starting date is not sunday." );
        return new WeeklyShiftSchedule ( startingDate );
    }

    public void changeShift(LocalDate date, int shift, HashMap<String, List<String>> manning) throws EmployeeException {
        if(date.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "the date has already passed." );
        if((date.getDayOfWeek ().getValue ()== 5 && shift == 1 )| (date.getDayOfWeek ().getValue ()== 6 && shift == 0 ))
            throw new EmployeeException ( "Super-Lee does not work at Shabbat." );
        checkDriverVallidity2 ( manning );
        checkManningVallidity ( manning );
        getWeeklyShiftSchedule ( date ).changeShift(employeeController, date, shift, manning);
    }

    public void addEmployeeToShift(String role, String ID, LocalDate date, int shift) throws EmployeeException {
        if(date.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "the date has already passed." );
        if((date.getDayOfWeek ().getValue ()== 5 && shift == 1 )| (date.getDayOfWeek ().getValue ()== 6 && shift == 0 ))
            throw new EmployeeException ( "Super-Lee does not work at Shabbat." );
        if(!employeeController.isExist(role, ID))
            throw new EmployeeException ( "Id - " + ID + " and role - " + role + " does not exist in system." );
        if(employeeController.getEmployee ( ID ).getConstraints ().containsKey ( date ) &&
                employeeController.getEmployee ( ID ).getConstraints ().get ( date ). isMorningShift () && shift == 0)
            throw new EmployeeException ( "Employee is unavailable." );
        if(employeeController.getEmployee ( ID ).getConstraints ().containsKey ( date ) &&
                employeeController.getEmployee ( ID ).getConstraints ().get ( date ). isEveningShift () && shift == 1)
            throw new EmployeeException ( "Employee is unavailable." );
        if(getWeeklyShiftSchedule ( date ).getShift ( date, 1-shift ).isWorking ( role, ID ))
            throw new EmployeeException ( "Employee is already manning the other shift of the day." );
        getWeeklyShiftSchedule ( date ).addEmployeeToShift ( role, ID, date, shift );
    }

    public void deleteEmployeeFromShift(String role, String ID, LocalDate date, int shift) throws EmployeeException {
        if(date.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "the date has already passed." );
        if((date.getDayOfWeek ().getValue ()== 5 && shift == 1 )| (date.getDayOfWeek ().getValue ()== 6 && shift == 0 ))
            throw new EmployeeException ( "Super-Lee does not work at Shabbat." );
        getWeeklyShiftSchedule ( date ).deleteEmployeeFromShift (role, ID, date, shift );
    }

    public void changeShiftType(LocalDate date, int shift, String shiftType) throws EmployeeException {
        if(date.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "the date has already passed." );
        if((date.getDayOfWeek ().getValue ()== 5 && shift == 1 )| (date.getDayOfWeek ().getValue ()== 6 && shift == 0 ))
            throw new EmployeeException ( "Super-Lee does not work at Shabbat." );
        getWeeklyShiftSchedule ( date ).changeShiftType ( date, shift, shiftType );
    }

    public void createShiftType(String shiftype, HashMap<String, Integer> manning) throws EmployeeException {
        ShiftTypes.getInstance ().addShiftType ( shiftype, manning );
    }

    public void updateRoleManning(String shiftType, String role, int num) throws EmployeeException {
        ShiftTypes.getInstance ().updateRoleManning( shiftType, role, num );
    }

    public void addRoleManning(String shiftType, String role, int num) throws EmployeeException {
        ShiftTypes.getInstance ().addRoleManning ( shiftType, role, num );
    }

    public WeeklyShiftSchedule getWeeklyShiftSchedule(LocalDate date) throws EmployeeException {
        WeeklyShiftSchedule output;
        LocalDate sunday;
        if(date.getDayOfWeek () == DayOfWeek.SUNDAY) {
            sunday = date;
        }
        else {
            TemporalField field = WeekFields.of ( Locale.US ).dayOfWeek ();
            sunday = date.with ( field, 1 );
        }
        output = shifts.get (sunday);
        if(output == null)
            throw new EmployeeException ( "No such shift exists." );
        return output;
    }

    public Shift getShift(LocalDate date, int shift) throws EmployeeException {
        return getWeeklyShiftSchedule ( date ).getShift (date, shift);
    }

    public void deleteRoleFromShiftType(String shiftType, String role) throws EmployeeException {
        ShiftTypes.getInstance ().deleteRole(shiftType, role);
    }

    public void createData() throws EmployeeException {
        createShiftTypes ();
        LocalDate temp = LocalDate.now ().plusDays ( 7 );
        LocalDate sunday = temp;
        if(temp.getDayOfWeek () != DayOfWeek.SUNDAY) {
            TemporalField field = WeekFields.of ( Locale.US ).dayOfWeek ( );
            sunday = temp.with ( field, 1 );
        }
        shifts.put (sunday, getRecommendation ( sunday ));
        shifts.put (sunday.plusDays ( 7 ), getRecommendation ( sunday.plusDays ( 7 ) ));
    }

    private void createShiftTypes() throws EmployeeException {
        HashMap<String, Integer> manning = new HashMap<> (  );
        manning.put ( "shiftManager", 1 );
        manning.put ( "cashier", 2 );
        manning.put ( "guard", 1 );
        manning.put ( "usher", 2 );
        manning.put ( "storeKeeper", 1 );
        createShiftType ( "morningShift", manning );
        createShiftType ( "eveningShift", manning );
    }

    public HashMap<LocalDate, HashMap<Integer, List<String>>> getDaysAndDrivers() throws EmployeeException {
         LocalDate date = LocalDate.now ();
         HashMap<LocalDate, HashMap<Integer, List<String>>> daysAndDrivers = new HashMap<> (  );
         WeeklyShiftSchedule cur;
         while (isExist ( date )){
             cur = getWeeklyShiftSchedule ( date );
             cur.getDaysAndDrivers(date, daysAndDrivers);
             date = date.plusDays ( 7-(date.getDayOfWeek ().getValue () % 7) );
         }
         return daysAndDrivers;
    }

    public void addDriverToShift(String id, LocalDate date, int shift) throws EmployeeException {
        if (shift == 1) {
            if (getShift ( date, 0 ).isWorking ( "driver", id ))
                throw new EmployeeException ( "the driver is already manning the morning shift." );
        } else {
            if (getShift ( date, 1 ).isWorking ( "driver", id ))
                throw new EmployeeException ( "the driver is already manning the evening shift." );
        }
        getShift ( date, shift ).addEmployee ( "driver", id );
    }

    public boolean isDriverAssigned(String id, LocalDate date, int shift) throws EmployeeException {
        return getShift ( date, shift ).isWorking ( "driver", id );
    }

    private boolean isExist(LocalDate date){
        try {
            getWeeklyShiftSchedule ( date );
            return true;
        }catch (EmployeeException e){
            return false;
        }
    }

}
