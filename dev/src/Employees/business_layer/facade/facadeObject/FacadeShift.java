package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Employee.Role;
import Employees.business_layer.Shift.Shift;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class FacadeShift implements FacadeObject {
    private LocalDate date;
    private HashMap<Role, List<Integer>> manning;
    private String type;
    private int mORe;

    //an existing shift with a given maning
    public FacadeShift(LocalDate date, HashMap<Role, List<Integer>> manning, String type, int mORe)
    {
        this.date = date;
        this.manning = manning;
        this.type = type;
        this.mORe = mORe;
    }

    //a new shift without an existing manning
    public FacadeShift(LocalDate date, String type, int mORe)
    {
        this.date = date;
        this.manning = new HashMap<> ();
        this.type = type;
        this.mORe = mORe;
    }

    public FacadeShift(Shift shift) {
        date = shift.getDate ();
        manning = shift.getManning ();
        type = shift.getType ();
        mORe = shift.getmORe();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public HashMap<Role, List<Integer>> getManning() {
        return manning;
    }

    public int getmORe() {
        return mORe;
    }
}
