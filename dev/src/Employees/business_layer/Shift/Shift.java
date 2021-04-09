package Employees.business_layer.Shift;

import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;
import Employees.business_layer.facade.facadeObject.FacadeShift;

import java.time.LocalDate;
import java.util.*;

public class Shift {
    private LocalDate date;
    private HashMap<Role, List<String>> manning;
    private String type;
    private int mORe;
    private boolean isMissing;

    //an existing shift with a given maning
    public Shift(LocalDate date, HashMap<Role, List<String>> manning, String type, int mORe)
    {
        this.date = date;
        this.manning = manning;
        this.type = type;
        this.mORe = mORe;
        isMissing = isMissing ();
    }

    //a new shift without an existing manning
    public Shift(LocalDate date, String type, int mORe)
    {
        this.date = date;
        this.manning = new HashMap<> ();
        this.type = type;
        this.mORe = mORe;
        isMissing = isMissing ();
    }

    public Shift(FacadeShift facadeShift) throws EmployeeException {
        date = facadeShift.getDate();
        manning = new HashMap<> (  );
        for( Map.Entry <String, List<String>> entry: facadeShift.getManning ().entrySet ())
        {
            manning.put ( Role.valueOf(entry.getKey ()), entry.getValue ());
        }
        type = facadeShift.getType();
        mORe = facadeShift.getmORe();
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public HashMap<Role, List<String>> getManning() {
        return manning;
    }

    public boolean isMissing()
    {
        for ( Map.Entry<Role, List<String>> entry : manning.entrySet ()) {
            Role key = entry.getKey ();
            if(isRoleMissing ( key ))
                return true;
        }
        return false;
    }

    public boolean isRoleMissing(Role role)
    {
        int needed = ShiftTypes.getInstance ().getRoleManning ( type, role );
        if(!manning.containsKey ( role )) {
            if (needed == 0)
                return false;
            return true;
        }
        if(needed > manning.get ( role ).size ())
            return true;
        return false;
    }

    public void deleteEmployee(String role, String ID) throws EmployeeException {
        Role newRole = Role.valueOf ( role );
        if(manning.containsKey ( newRole ) && manning.get ( newRole ).contains ( ID ))
            manning.get ( newRole ).remove ( ID );
        else
            throw new EmployeeException ( "no such employee to delete in the current shift." );
    }

    public void addEmployee(String role, String ID) throws EmployeeException
    {
        Role newRole = Role.valueOf ( role );
        if(!manning.containsKey ( newRole )){
            manning.put ( newRole, new ArrayList<> () );
        }
        if(manning.get ( newRole ).contains ( ID ))
            throw new EmployeeException ( "this employee already exists in shift." );
        manning.get ( newRole ).add ( ID );
    }

    public void changeShiftType(String shiftType) {
        this.type = shiftType;
    }

    public void changeManning(HashMap<String,List<String>> manning) {
        this.manning = new HashMap<> (  );
        for( Map.Entry<String, List<String >> entry : manning.entrySet () ){
            this.manning.put ( Role.valueOf ( entry.getKey () ), entry.getValue () );
        }
    }

    public void createManning(EmployeeController employeeController) throws EmployeeException {
        HashMap<Role, Integer> manning = ShiftTypes.getInstance ().getShiftTypeManning ( type );
        List<String> free;
        List<String> work = new ArrayList<> (  );
        for(Map.Entry<Role, Integer> entery: manning.entrySet ())
        {
            Role role = entery.getKey ();
            free = employeeController.getRoleInDate(date, role, mORe);
            String[] stillFree;
            int rand;
            for(int i = 0; i < ShiftTypes.getInstance ( ).getRoleManning ( type, role ); i++)
            {
                stillFree = getFree ( free, work );
                rand = getRandomEmployee ( stillFree );
                if( rand == -1 )
                    break;
                work.add ( stillFree[rand] );
            }
            this.manning.put ( role, new ArrayList<> ( work ) );
            work.clear ();
        }
    }

    private String[] getFree(List<String> canWork, List<String> work)
    {
        String[] free = new String[canWork.size () - work.size ()];
        int index = 0;
        for(int i = 0; i < canWork.size (); i ++)
        {
            if(!work.contains ( canWork.get ( i )))
                free[index++] = canWork.get ( i );
        }
        return free;
    }

    private int getRandomEmployee(String[] free)
    {
        if(free.length == 0)
            return -1;
        Random rand = new Random (  );
        return rand.nextInt ( free.length );
    }

    public int getmORe() {
        return mORe;
    }
}
