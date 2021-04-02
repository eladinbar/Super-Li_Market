package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Shift.Shift;

import java.util.Date;
import java.util.HashMap;

public class FacadeWeeklyShiftSchedule implements FacadeObject{
    private Date date;
    private Shift[][] shifts;

    //an existing schedule with given shifts
    public FacadeWeeklyShiftSchedule(Date date, Shift[][] shifts)
    {
        this.date = date;
        this.shifts = shifts;
    }

    //a new schedule with no shifts
    public FacadeWeeklyShiftSchedule(Date date)
    {
        this.date = date;
        this.shifts = new Shift[7][2];
    }
}
