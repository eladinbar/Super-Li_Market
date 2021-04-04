package Employees.business_layer.Shift;

import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ShiftController {
    private HashMap<LocalDate, WeeklyShiftSchedule> shifts;
    EmployeeController employeeController;

    public ShiftController(HashMap<LocalDate, WeeklyShiftSchedule> shifts, EmployeeController employeeController)
    {
        this.shifts = shifts;
        this.employeeController = employeeController;
    }

    public ShiftController(EmployeeController employeeController){
        shifts = new HashMap<> (  );
        this.employeeController = employeeController;
    }

    public WeeklyShiftSchedule getRecommendation(LocalDate startingDate) throws EmployeeException {
        if(startingDate.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "starting day has already passed." );
        WeeklyShiftSchedule output = createEmptyWeeklyShiftSchedule ( startingDate );
        for(int i = 0; i < 7; i ++)
        {
            output.recommendShifts(employeeController, i);
        }
        return output;
    }

    public void createWeeklyshiftSchedule(LocalDate startingDate, Shift[][] shifts) throws EmployeeException {
        if(startingDate.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "starting day has already passed." );
        if(shifts == null)
            throw new EmployeeException ( "shifts are illegal." );
        this.shifts.put ( startingDate, new WeeklyShiftSchedule ( startingDate, shifts ) );
    }

    public WeeklyShiftSchedule createEmptyWeeklyShiftSchedule(LocalDate startingDate) throws EmployeeException {
        if(startingDate.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "starting day has already passed." );
        return new WeeklyShiftSchedule ( startingDate );
    }

    public void addShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) throws EmployeeException {
        getWeeklyShiftSchedule ( date ).addShift(date, shift, manning);
    }

    public void changeShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) throws EmployeeException {
        getWeeklyShiftSchedule ( date ).changeShift(date, shift, manning);
    }

    public void addEmployeeToShift(Role role, int ID, LocalDate date, int shift) throws EmployeeException {
        getWeeklyShiftSchedule ( date ).addEmployeeToShift ( role, ID, date, shift );
    }

    public void deleteEmployeeFromShift(Role role, int ID, LocalDate date, int shift) throws EmployeeException {
        getWeeklyShiftSchedule ( date ).deleteEmployeeFromShift (role, ID, date, shift );
    }

    public void changeShiftType(LocalDate date, int shift, String shiftType) throws EmployeeException {
        getWeeklyShiftSchedule ( date ).changeShiftType ( date, shift, shiftType );
    }

    public void createShiftType(String shiftype, HashMap<Role, Integer> manning) throws EmployeeException {
        ShiftTypes.getInstance ().addShiftType ( shiftype, manning );
    }

    public void updateRoleManning(String shiftType, Role role, int num) throws EmployeeException {
        ShiftTypes.getInstance ().unpdateRoleManning ( shiftType, role, num );
    }

    public void addRoleManning(String shiftType, Role role, int num) throws EmployeeException {
        ShiftTypes.getInstance ().addRoleManning ( shiftType, role, num );
    }

    public WeeklyShiftSchedule getWeeklyShiftSchedule(LocalDate date) throws EmployeeException {
        WeeklyShiftSchedule output = shifts.get (date.minusDays ( date.getDayOfWeek ().getValue () % 7) );
        if(output == null)
            throw new EmployeeException ( "no such shift exists." );
        return output;
    }
}
