package Employees.business_layer.Shift;

import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;
import Employees.business_layer.facade.facadeObject.FacadeShift;

import java.time.LocalDate;
import java.util.*;

public class Shift {
    private LocalDate date;
    private HashMap<Role, List<Integer>> manning;
    private String type;
    private int mORe;

    //an existing shift with a given maning
    public Shift(LocalDate date, HashMap<Role, List<Integer>> manning, String type, int mORe)
    {
        this.date = date;
        this.manning = manning;
        this.type = type;
        this.mORe = mORe;
    }

    //a new shift without an existing manning
    public Shift(LocalDate date, String type, int mORe)
    {
        this.date = date;
        this.manning = new HashMap<> ();
        this.type = type;
        this.mORe = mORe;
    }

    public Shift(FacadeShift facadeShift) {
        date = facadeShift.getDate();
        manning = facadeShift.getManning();
        type = facadeShift.getType();
        mORe = facadeShift.getmORe();
    }

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public HashMap<Role, List<Integer>> getManning() {
        return manning;
    }

    public boolean isMissing()
    {
        for ( Map.Entry<Role, List<Integer>> entry : manning.entrySet ()) {
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

    public void deleteEmployee(Role role, int ID) throws EmployeeException {
        if(manning.containsKey ( role ) && manning.get ( role ).contains ( ID ))
            manning.get ( role ).remove ( ID );
        else
            throw new EmployeeException ( "no such employee to delete in the current shift." );
    }

    public void addEmployee(Role role, int ID) throws EmployeeException
    {
        if(!manning.containsKey ( role )){
            manning.put ( role, new ArrayList<> () );
        }
        if(manning.get ( role ).contains ( ID ))
            throw new EmployeeException ( "this employee already exists in shift." );
        manning.get ( role ).add ( ID );
    }

    public void changeShiftType(String shiftType) {
        this.type = shiftType;
    }

    public void changeManning(HashMap<Role,List<Integer>> manning) {
        this.manning = manning;
    }

    public void createManning(EmployeeController employeeController) throws EmployeeException {
        HashMap<Role, Integer> manning = ShiftTypes.getInstance ().getShiftTypeManning ( type );
        int[] free;
        List<Integer> work = new ArrayList<> (  );
        for(Map.Entry<Role, Integer> entery: manning.entrySet ())
        {
            Role role = entery.getKey ();
            free = employeeController.getRoleInDate(date, role, mORe);
            int[] stillFree;
            int id;
            for(int i = 0; i < ShiftTypes.getInstance ( ).getRoleManning ( type, role ); i++)
            {
                stillFree = getFree ( free, work );
                id = getRandomEmployee ( stillFree );
                if( id == -1 )
                    break;
                work.add ( id );
            }
        }
    }

    private int[] getFree(int[] canWork, List<Integer> work)
    {
        int[] free = new int[canWork.length - work.size ()];
        int index = 0;
        for(int i = 0; i < canWork.length; i ++)
        {
            if(!work.contains ( canWork[i]))
                free[index++] = canWork[i];
        }
        return free;
    }

    private int getRandomEmployee(int[] free)
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
