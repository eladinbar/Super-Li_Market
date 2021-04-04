package Employees.business_layer.Shift;

import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class WeeklyShiftSchedule {
    private LocalDate date;
    private Shift[][] shifts;


    //an existing schedule with given shifts
    public WeeklyShiftSchedule(LocalDate date, Shift[][] shifts)
    {
        if(date.getDayOfWeek ().equals ( DayOfWeek.SUNDAY ))
        this.date = date;
        this.shifts = shifts;
    }

    //a new schedule with no shifts
    public WeeklyShiftSchedule(LocalDate date)
    {
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
    }

    public void addEmployeeToShift(Role role, int ID, LocalDate date, int shift) throws EmployeeException {
        if(shift > 2 || shift < 1 || checkDatesValidity ( this.date, date ))
            throw new EmployeeException ( "no such shift to add the employee." );
        getShift ( date, shift ).addEmployee ( role, ID );

    }

    public void deleteEmployeeFromShift(Role role, int ID, LocalDate date, int shift) throws EmployeeException {
        if(shift > 2 || shift < 1 || checkDatesValidity ( this.date, date ))
            throw new EmployeeException ( "no such shift to delete the employee from." );
        getShift ( date, shift ).deleteEmployee ( role, ID );
    }

    public void changeShiftType(LocalDate date, int shift, String shiftType)
    {
        getShift ( date, shift ).changeShiftType(shiftType);
    }

    private boolean checkDatesValidity(LocalDate start, LocalDate end)
    {
        return (end.isAfter ( start ) && end.minusDays ( 7 ).isBefore ( start ));
    }

    private Shift getShift(LocalDate date, int shift)
    {
        return shifts[date.getDayOfWeek ().getValue ()][shift];
    }

    public void changeShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) {
        shifts[date.getDayOfWeek ().getValue ()][shift].changeManning( manning );
    }

    public void addShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) throws EmployeeException {
        if (getShift ( date, shift ).getManning () != null)
            throw new EmployeeException ( "shift is already exists." );
        changeShift ( date, shift, manning );
    }

    public void addShift(int i, Shift shift) throws EmployeeException {
        if(i< 0 || i > 1)
            throw new EmployeeException ( "no such shift to add (0-1)." );
        shifts[shift.getDate ().getDayOfWeek ().getValue ()][i] = shift;
    }

    public void recommendShifts(EmployeeController employeeController, int i) {
        shifts[i][0].createManning ( employeeController );
        shifts[i][1].createManning ( employeeController );
    }

    public boolean isMissing()
    {
        for(int i = 0; i < 7; i++)
        {
            if(i < 5)
                return (shifts[i][0].isMissing () || shifts[i][1].isMissing ());
            return shifts[5][0].isMissing () || shifts[6][1].isMissing ();

        }
    }
}
