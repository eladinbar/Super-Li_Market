package Employees.business_layer.Shift;

import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklyShiftSchedule {
    private LocalDate date;
    private Shift[][] shifts;
    private boolean isMissing;


    //an existing schedule with given shifts
    public WeeklyShiftSchedule(EmployeeController employeeController, LocalDate date, Shift[][] shifts) throws EmployeeException {
        if(date.getDayOfWeek ().equals ( DayOfWeek.SUNDAY ))
        this.date = date;
        for(int i = 0; i < 7; i ++)
        {
            checkManningVallidity2 ( employeeController, shifts[i][0].getManning () );
        }
        this.shifts = shifts;
        isMissing = isMissing ();
    }

    //a new schedule with no shifts
    public WeeklyShiftSchedule(LocalDate date) throws EmployeeException {
        if(date.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "starting day has already passed." );
        this.date = date;
        this.shifts = new Shift[7][2];
        for(int i = 0; i < 7; i++)
        {
            if(i != 7)
                shifts[i][0] = new Shift(date.plusDays ( i ), "morningShift", 0 );
            if(i != 6)
                shifts[i][1] = new Shift ( date.plusDays ( i ), "eveningShift", 1 );
            shifts[7][0] = null;
            shifts[6][1] = null;
        }
        isMissing = true;
    }

    public void addEmployeeToShift(String role, String ID, LocalDate date, int shift) throws EmployeeException {
        if(shift > 2 || shift < 1 || checkDatesValidity ( this.date, date ))
            throw new EmployeeException ( "no such shift to add the employee." );
        getShift ( date, shift ).addEmployee ( role, ID );
        isMissing = isMissing();
    }

    public void deleteEmployeeFromShift(String role, String ID, LocalDate date, int shift) throws EmployeeException {
        if(shift > 2 || shift < 1 || checkDatesValidity ( this.date, date ))
            throw new EmployeeException ( "no such shift to delete the employee from." );
        getShift ( date, shift ).deleteEmployee ( role, ID );
        isMissing = isMissing();
    }

    public void changeShiftType(LocalDate date, int shift, String shiftType) throws EmployeeException {
        getShift ( date, shift ).changeShiftType(shiftType);
        isMissing = isMissing();
    }

    private boolean checkDatesValidity(LocalDate start, LocalDate end) throws EmployeeException {
        if(end.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "The date of the shift already passed." );
        return (end.isAfter ( start ) && end.minusDays ( 7 ).isBefore ( start ));
    }

    public Shift getShift(LocalDate date, int shift) throws EmployeeException {
        if(shift < 0 || shift > 1)
            throw new EmployeeException ( "shift index is illegal." );
        return shifts[date.getDayOfWeek ().getValue ()][shift];
    }

    public void changeShift(EmployeeController employeeController, LocalDate date, int shift, HashMap<String, List<String>> manning) throws EmployeeException {
        checkManningVallidity ( employeeController, manning );
        shifts[date.getDayOfWeek ().getValue ()][shift].changeManning( manning );
        isMissing = isMissing();
    }

    public void addShift(EmployeeController employeeController, LocalDate date, int shift, HashMap<String, List<String>> manning) throws EmployeeException {
        checkManningVallidity ( employeeController, manning );
        if (getShift ( date, shift ).getManning () != null)
            throw new EmployeeException ( "shift is already exists." );
        changeShift (employeeController, date, shift, manning );
        isMissing = isMissing();
    }

    private void checkManningVallidity(EmployeeController employeeController, HashMap<String, List<String>> manning) throws EmployeeException {
        for( Map.Entry<String, List<String>> entry : manning.entrySet () )
        {
            for(String employee : entry.getValue ()) {
                if (!employeeController.isExist ( entry.getKey ( ), employee ))
                    throw new EmployeeException ( "Id - " + employee + " and role - " + entry.getKey () + " does not exist in system.");
            }
        }
    }

    private void checkManningVallidity2(EmployeeController employeeController, HashMap<Role, List<String>> manning) throws EmployeeException {
        for( Map.Entry<Role, List<String>> entry : manning.entrySet () )
        {
            for(String employee : entry.getValue ()) {
                if (!employeeController.isExist ( entry.getKey ( ).name (), employee ))
                    throw new EmployeeException ( "Id - " + employee + " and role - " + entry.getKey () + " does not exist in system.");
            }
        }
    }

//    public void addShift(int i, Shift shift) throws EmployeeException {
//        if(i< 0 || i > 1)
//            throw new EmployeeException ( "no such shift to add (0-1)." );
//        shifts[shift.getDate ().getDayOfWeek ().getValue ()][i] = shift;
//        isMissing = isMissing();
//    }

    public void recommendShifts(EmployeeController employeeController, int i) throws EmployeeException {
        shifts[i][0].createManning ( employeeController );
        shifts[i][1].createManning ( employeeController );
        isMissing = isMissing();
    }

    public boolean isMissing()
    {
        for(int i = 0; i < 5; i++)
        {
            if (shifts[i][0].isMissing () || shifts[i][1].isMissing ())
                return true;
        }
        return shifts[5][0].isMissing () || shifts[6][1].isMissing ();
    }

    public LocalDate getDate() {
        return date;
    }

    public Shift[][] getShifts() {
        return shifts;
    }
}
