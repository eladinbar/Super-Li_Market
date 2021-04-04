package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Shift.Shift;
import Employees.business_layer.Shift.WeeklyShiftSchedule;

import java.time.LocalDate;

public class FacadeWeeklyShiftSchedule implements FacadeObject{
    private LocalDate date;
    private FacadeShift[][] shifts;

    //an existing schedule with given shifts
    public FacadeWeeklyShiftSchedule(LocalDate date, FacadeShift[][] shifts)
    {
        this.date = date;
        this.shifts = shifts;
    }

    //a new schedule with no shifts
    public FacadeWeeklyShiftSchedule(LocalDate date)
    {
        this.date = date;
        this.shifts = new FacadeShift[7][2];
    }

    public FacadeWeeklyShiftSchedule(WeeklyShiftSchedule weeklyShiftSchedule) {
        date = weeklyShiftSchedule.getDate ();
        this.shifts = new FacadeShift[7][2];
        for(int i = 0; i < 7; i ++)
        {
            Shift morning = weeklyShiftSchedule.getShifts ()[i][0];
            Shift evening = weeklyShiftSchedule.getShifts ()[i][1];
            if(morning != null)
                shifts[i][0] = new FacadeShift ( morning );
            if(evening != null)
                shifts[i][1] = new FacadeShift ( evening );
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public FacadeShift[][] getShifts() {
        return shifts;
    }
}
