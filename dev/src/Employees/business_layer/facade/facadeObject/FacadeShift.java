package Employees.business_layer.facade.facadeObject;

import java.util.Date;
import java.util.HashMap;

public class FacadeShift implements FacadeObject {
    private Date date;
    private HashMap<Role, int[]> manning;
    private FacadeShiftType type;

    //an existing shift with a given maning
    public FacadeShift(Date date, HashMap<Role, int[]> manning, FacadeShiftType type)
    {
        this.date = date;
        this.manning = manning;
        this.type = type;
    }

    //a new shift without an existing manning
    public FacadeShift(Date date, FacadeShiftType type)
    {
        this.date = date;
        this.manning = new HashMap<> ();
        this.type = type;
    }
}
