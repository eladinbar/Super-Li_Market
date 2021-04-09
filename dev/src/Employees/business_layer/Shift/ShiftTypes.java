package Employees.business_layer.Shift;

import Employees.EmployeeException;
import Employees.business_layer.Employee.Role;

import java.util.HashMap;
import java.util.Map;

public class ShiftTypes {
    private static ShiftTypes instance = null;

    private HashMap<String, HashMap<Role, Integer>> shiftTypes;

    //an existing shift type with a given manning
    private ShiftTypes() {
        shiftTypes = new HashMap<> ( );
    }

    public static ShiftTypes getInstance() {
        if (instance == null)
            instance = new ShiftTypes ( );
        return instance;
    }

    public int getRoleManning(String shiftType, Role role) {
        if (!shiftTypes.containsKey ( shiftType ) || !shiftTypes.get ( shiftType ).containsKey ( role ))
            return 0;
        return shiftTypes.get ( shiftType ).get ( role );
    }

    public void unpdateRoleManning(String shiftType, String role, int num) throws EmployeeException {
        if (!shiftTypes.containsKey ( shiftType ))
            throw new EmployeeException ( "no such shift type to update." );
        Role newRole = Role.valueOf ( role );
        if(newRole == null)
            throw new EmployeeException ( "Illegal role name." );
        if (!shiftTypes.get ( shiftType ).containsKey ( newRole ))
            shiftTypes.get ( shiftType ).put ( newRole, num );
        else
            shiftTypes.get ( shiftType ).replace ( newRole, num );
    }

    public void addRoleManning(String shiftType, String role, int num) throws EmployeeException {
        if (!shiftTypes.containsKey ( shiftType ))
            throw new EmployeeException ( "no such shift type exist to add a role manning." );
        Role newRole = Role.valueOf ( role );
        if(newRole == null)
            throw new EmployeeException ( "Illegal role name." );
        if (shiftTypes.get ( shiftType ).containsKey ( newRole ))
            throw new EmployeeException ( "role is already exsits in manning." );
        shiftTypes.get ( shiftType ).put ( newRole, num );
    }

    public HashMap<Role, Integer> getShiftTypeManning(String shiftType) throws EmployeeException {
        if (!shiftTypes.containsKey ( shiftType ))
            throw new EmployeeException ( "no such Shift type." );
        return shiftTypes.get ( shiftType );
    }

    public void addShiftType(String shiftType, HashMap<String, Integer> manning) throws EmployeeException {
        if (shiftTypes.containsKey ( shiftType ))
            throw new EmployeeException ( "this current shift type is already exists." );
        HashMap<Role, Integer> newManning = new HashMap<> ( );
        for ( Map.Entry<String, Integer> entry : manning.entrySet ( ) ) {
            newManning.put ( Role.valueOf ( entry.getKey ( ) ), entry.getValue ( ) );
        }
        shiftTypes.put ( shiftType, newManning );
    }

    public void deleteRole(String shiftType, String role) throws EmployeeException {
        if(!shiftTypes.containsKey ( shiftType ))
            throw new EmployeeException ( "Illegal shift type." );
        shiftTypes.get (shiftType).remove ( Role.valueOf ( role ) );
    }

    public String[] getShiftTypes() {
        String[] shifts = new String[shiftTypes.size ()];
        int i = 0;
        for( Map.Entry<String, HashMap<Role, Integer>> shift : shiftTypes.entrySet ())
            shifts[i++] = shift.getKey ();
        return shifts;
    }
}
