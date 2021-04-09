package Employees.business_layer.Shift;

import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ShiftController {
    private static ShiftController instance = null;

    private HashMap<LocalDate, WeeklyShiftSchedule> shifts;
    EmployeeController employeeController;

//    public ShiftController(HashMap<LocalDate, WeeklyShiftSchedule> shifts, EmployeeController employeeController)
//    {
//        this.shifts = shifts;
//        this.employeeController = employeeController;
//    }

    private ShiftController(){
        shifts = new HashMap<> (  );
        employeeController = EmployeeController.getInstance ();
    }

    public static ShiftController getInstance() {
        if (instance == null)
            instance = new ShiftController ();
        return instance;
    }

    public WeeklyShiftSchedule getRecommendation(LocalDate startingDate) throws EmployeeException {
        if(startingDate.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "Starting date has already passed." );
        if(startingDate.getDayOfWeek () != DayOfWeek.SUNDAY)
            throw new EmployeeException ( "Starting date is not Sunday." );
        WeeklyShiftSchedule output = createEmptyWeeklyShiftSchedule ( startingDate );
        for(int i = 0; i < 7; i ++)
        {
            output.recommendShifts(employeeController, i);
        }
        return output;
    }

    public WeeklyShiftSchedule createWeeklyShiftSchedule(LocalDate startingDate, Shift[][] shifts) throws EmployeeException {
        if(startingDate.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "Starting date has already passed." );
        if(startingDate.getDayOfWeek () != DayOfWeek.SUNDAY)
            throw new EmployeeException ( "Starting date is not sunday." );
        if(shifts == null)
            throw new EmployeeException ( "shifts are illegal." );
        if(this.shifts.containsKey ( startingDate ))
            throw new EmployeeException ( "Weekly shift schedule is already exists in the system. \nYou can watch it and edit if you would like." );
        WeeklyShiftSchedule weeklyShiftSchedule = new WeeklyShiftSchedule (employeeController, startingDate, shifts );
        this.shifts.put ( startingDate, weeklyShiftSchedule );
        return weeklyShiftSchedule;
    }

    public WeeklyShiftSchedule createEmptyWeeklyShiftSchedule(LocalDate startingDate) throws EmployeeException {
        if(startingDate.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "starting day has already passed." );
        return new WeeklyShiftSchedule ( startingDate );
    }

    public void changeShift(LocalDate date, int shift, HashMap<String, List<String>> manning) throws EmployeeException {
        getWeeklyShiftSchedule ( date ).changeShift(employeeController, date, shift, manning);
    }

    public void addEmployeeToShift(String role, String ID, LocalDate date, int shift) throws EmployeeException {
        if(!employeeController.isExist(role, ID))
            throw new EmployeeException ( "Id - " + ID + " and role - " + role + " does not exist in system." );
        if(employeeController.getEmployee ( ID ).getConstraints ().containsKey ( date ) &&
                employeeController.getEmployee ( ID ).getConstraints ().get ( date ). isMorningShift () && shift == 0)
            throw new EmployeeException ( "Employee is unavailable." );
        if(employeeController.getEmployee ( ID ).getConstraints ().containsKey ( date ) &&
                employeeController.getEmployee ( ID ).getConstraints ().get ( date ). isEveningShift () && shift == 1)
            throw new EmployeeException ( "Employee is unavailable." );
        getWeeklyShiftSchedule ( date ).addEmployeeToShift ( role, ID, date, shift );
    }

    public void deleteEmployeeFromShift(String role, String ID, LocalDate date, int shift) throws EmployeeException {
        getWeeklyShiftSchedule ( date ).deleteEmployeeFromShift (role, ID, date, shift );
    }

    public void changeShiftType(LocalDate date, int shift, String shiftType) throws EmployeeException {
        getWeeklyShiftSchedule ( date ).changeShiftType ( date, shift, shiftType );
    }

    public void createShiftType(String shiftype, HashMap<String, Integer> manning) throws EmployeeException {
        ShiftTypes.getInstance ().addShiftType ( shiftype, manning );
    }

    public void updateRoleManning(String shiftType, String role, int num) throws EmployeeException {
        ShiftTypes.getInstance ().unpdateRoleManning ( shiftType, role, num );
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


}
