package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Employee.Role;

import java.util.HashMap;

public class FacadeShiftType implements FacadeObject{
    private HashMap<Role, Integer> manning;
    private String type;

    //an existing shift type with a given manning
    public FacadeShiftType(HashMap<Role,Integer> manning, String type)
    {
        this.manning = manning;
        this.type = type;
    }

    //a new shift type without an existing manning
    public FacadeShiftType(String type)
    {
        this.manning = new HashMap<> ();
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public HashMap<Role, Integer> getManning() {
        return manning;
    }
}
