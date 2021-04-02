package Employees.business_layer.Shift;

import Employees.EmployeeException;
import Employees.business_layer.Employee.Role;

import java.util.HashMap;

public class ShiftTypes {
    private static ShiftTypes instance = null;

    private HashMap<String, HashMap<Role, Integer>> shiftTypes;

    //an existing shift type with a given manning
    private ShiftTypes() {
        shiftTypes = new HashMap<> ( );
    }

    public static ShiftTypes getInstance()
    {
        if(instance == null)
            instance = new ShiftTypes ();
        return instance;
    }

    public int getRoleManning(String shiftType, Role role)
    {
        if(!shiftTypes.containsKey ( shiftType ) || !shiftTypes.get ( shiftType ).containsKey ( role ))
            return 0;
        return shiftTypes.get ( shiftType ).get ( role );
    }

    public void unpdateRoleManning(String shiftType, Role role, int num) throws EmployeeException {
        if(!shiftTypes.containsKey ( shiftType ))
            throw new EmployeeException ( "no such shift type to update." );
        if(!shiftTypes.get ( shiftType ).containsKey ( role ))
            shiftTypes.get ( shiftType ).put ( role,num );
        else
            shiftTypes.get ( shiftType ).replace ( role, num );
    }

    public void addRoleManning(String shiftType, Role role, int num) throws EmployeeException {
        if(!shiftTypes.containsKey ( shiftType ))
            throw new EmployeeException ( "no such shift type exist to add a role manning." );
        if(shiftTypes.get ( shiftType ).containsKey ( role ))
            throw new EmployeeException ("role is already exsits in manning.");
        shiftTypes.get ( shiftType ).put ( role, num);
    }

    public HashMap<Role, Integer> getShiftTypeManning(String shiftType) throws EmployeeException {
        if(!shiftTypes.containsKey ( shiftType ))
            throw new EmployeeException ( "no such Shift type." );
        return shiftTypes.get ( shiftType );
    }

    public void addShiftType(String shiftType, HashMap<Role, Integer> manning) throws EmployeeException {
        if(shiftType.contains ( shiftType ))
            throw new EmployeeException ( "this current shift type already exists." );
        shiftTypes.put ( shiftType, manning );
    }
}
