package Employees.business_layer.Shift;

import DAL.DalControllers_Employee.DalShiftController;
import DAL.DalControllers_Employee.DalShiftTypeController;
import DAL.DalObjects_Employees.DalShift;
import DAL.DalObjects_Employees.DalShiftType;
import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;

import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.*;

public class ShiftController {
    private static ShiftController instance = null;

    private HashMap<LocalDate, WeeklyShiftSchedule> shifts;
    EmployeeController employeeController;

    private ShiftController() {
        shifts = new HashMap<> ( );
        employeeController = EmployeeController.getInstance ( );
    }

    public static ShiftController getInstance() {
        if (instance == null)
            instance = new ShiftController ( );
        return instance;
    }

    public WeeklyShiftSchedule getRecommendation(LocalDate startingDate, boolean start) throws EmployeeException, SQLException {
        if (startingDate.isBefore ( LocalDate.now ( ) ))
            throw new EmployeeException ( "Starting date has already passed." );
        if (!start && startingDate.getDayOfWeek ( ) != DayOfWeek.SUNDAY)
            throw new EmployeeException ( "Starting date is not Sunday." );
        if (this.shifts.containsKey ( startingDate ))
            throw new EmployeeException ( "Weekly shift schedule is already exists in the system. \nYou can watch it and edit if you would like." );
        int day = startingDate.getDayOfWeek ().getValue () % 7;
        WeeklyShiftSchedule output = createEmptyWeeklyShiftSchedule ( startingDate.minusDays ( day ), start );
        for(int i = 0; i < day; i++ ){
            output.getShifts ()[i][0] = null;
            output.getShifts ()[i][1] = null;
        }
        for ( int i = day ; i < 7 ; i++ ) {
            output.recommendShifts ( employeeController, i );
        }
        shifts.put ( startingDate, output );
        return output;
    }

    public WeeklyShiftSchedule createWeeklyShiftSchedule(LocalDate startingDate, Shift[][] shifts) throws EmployeeException, SQLException {
        if (startingDate.isBefore ( LocalDate.now ( ) ))
            throw new EmployeeException ( "Starting date has already passed." );
        if (startingDate.getDayOfWeek ( ) != DayOfWeek.SUNDAY)
            throw new EmployeeException ( "Starting date is not sunday." );
        if (this.shifts.containsKey ( startingDate ))
            throw new EmployeeException ( "Weekly shift schedule is already exists in the system. \nYou can watch it and edit if you would like." );
        if (shifts == null)
            throw new EmployeeException ( "shifts are illegal." );
        for ( int i = 0 ; i < 5 ; i++ ) {
            checkManningVallidity2 ( shifts[i][0].getManning ( ) );
            checkDriverVallidity ( shifts[i][0].getManning () );
            checkManningVallidity2 ( shifts[i][1].getManning ( ) );
            checkDriverVallidity ( shifts[i][1].getManning () );
        }
        checkManningVallidity2 ( shifts[5][0].getManning ( ) );
        checkDriverVallidity ( shifts[5][0].getManning () );
        checkManningVallidity2 ( shifts[6][1].getManning ( ) );
        checkDriverVallidity ( shifts[6][1].getManning () );
        WeeklyShiftSchedule weeklyShiftSchedule = new WeeklyShiftSchedule ( startingDate, shifts );
        saveToDB(weeklyShiftSchedule);
        this.shifts.put ( startingDate, weeklyShiftSchedule );
        return weeklyShiftSchedule;
    }

    private void saveToDB(WeeklyShiftSchedule weeklyShiftSchedule) throws SQLException {
        Shift temp;
        for(int i = 0; i < 5; i++)
        {
            for(int j = 0; j < 2; j++) {
                temp = weeklyShiftSchedule.getShifts ( )[i][j];
                for ( Map.Entry<Role, List<String>> entry : temp.getManning ( ).entrySet ( ) )
                    for ( String id : entry.getValue ( ) )
                        try {
                            DalShiftController.getInstance ( ).insert ( new DalShift ( id, temp.getType ( ), temp.getDate ( ), temp.getmORe ( ), entry.getKey ( ).name () ) );
                        } catch (SQLException e)
                        {
                            DalShiftController.getInstance ().update ( new DalShift ( id, temp.getType ( ), temp.getDate ( ), temp.getmORe ( ), entry.getKey ( ).name () ) );
                        }
            }
        }
        temp = weeklyShiftSchedule.getShifts ( )[5][0];
        for ( Map.Entry<Role, List<String>> entry : temp.getManning ( ).entrySet ( ) )
            for ( String id : entry.getValue ( ) )
                DalShiftController.getInstance ( ).insert ( new DalShift ( id, temp.getType ( ), temp.getDate ( ), temp.getmORe ( ), entry.getKey ( ).name () ) );
        temp = weeklyShiftSchedule.getShifts ( )[6][1];
        for ( Map.Entry<Role, List<String>> entry : temp.getManning ( ).entrySet ( ) )
            for ( String id : entry.getValue ( ) )
                DalShiftController.getInstance ( ).insert ( new DalShift ( id, temp.getType ( ), temp.getDate ( ), temp.getmORe ( ), entry.getKey ( ).name () ) );

    }

    private void checkManningVallidity(HashMap<String, List<String>> manning) throws EmployeeException {
        for( Map.Entry<String, List<String>> entry : manning.entrySet () )
        {
            for(String employee : entry.getValue ()) {
                if (!employeeController.isExist ( entry.getKey ( ), employee ))
                    throw new EmployeeException ( "Id - " + employee + " and role - " + entry.getKey () + " does not exist in system.");
            }
        }
    }

    private void checkManningVallidity2(HashMap<Role, List<String>> manning) throws EmployeeException {
        for ( Map.Entry<Role, List<String>> entry : manning.entrySet ( ) ) {
            for ( String employee : entry.getValue ( ) ) {
                if (!employeeController.isExist ( entry.getKey ( ).name ( ), employee ))
                    throw new EmployeeException ( "Id - " + employee + " and role - " + entry.getKey ( ) + " does not exist in system." );
            }
        }
    }

    private void checkDriverVallidity(HashMap<Role, List<String>> manning) throws EmployeeException {
        if(manning.containsKey ( Role.driverC ) || manning.containsKey ( Role.driverC1 ))
            if(!manning.containsKey ( Role.storeKeeper ))
                throw new EmployeeException ( "Shift has driver/s without having a store keeper." );
    }

    private void checkDriverVallidity2(HashMap<String , List<String>> manning) throws EmployeeException {
        if(manning.containsKey ( Role.valueOf ( "driverC" ) ) || manning.containsKey (Role.valueOf ( "driverC1" ) ))
            if(!manning.containsKey ( Role.valueOf ( "storeKeeper" ) ))
                throw new EmployeeException ( "Shift has driver/s without having a store keeper." );
    }

    public WeeklyShiftSchedule createEmptyWeeklyShiftSchedule(LocalDate startingDate, boolean start) throws EmployeeException {
        if(!start && startingDate.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "starting day has already passed." );
        if(! start && startingDate.getDayOfWeek () != DayOfWeek.SUNDAY)
            throw new EmployeeException ( "Starting date is not sunday." );
        return new WeeklyShiftSchedule ( startingDate );
    }

    public void changeShift(LocalDate date, int shift, HashMap<String, List<String>> manning) throws EmployeeException, SQLException {
        if(date.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "the date has already passed." );
        if((date.getDayOfWeek ().getValue ()== 5 && shift == 1 )| (date.getDayOfWeek ().getValue ()== 6 && shift == 0 ))
            throw new EmployeeException ( "Super-Lee does not work at Shabbat." );
        checkDriverVallidity2 ( manning );
        checkManningVallidity ( manning );
        getWeeklyShiftSchedule ( date ).changeShift(date, shift, manning);
    }

    public void addEmployeeToShift(String role, String ID, LocalDate date, int shift) throws EmployeeException, SQLException {
        if(date.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "the date has already passed." );
        if((date.getDayOfWeek ().getValue ()== 5 && shift == 1 )| (date.getDayOfWeek ().getValue ()== 6 && shift == 0 ))
            throw new EmployeeException ( "Super-Lee does not work at Shabbat." );
        if(!employeeController.isExist(role, ID))
            throw new EmployeeException ( "Id - " + ID + " and role - " + role + " does not exist in system." );
        if(employeeController.getEmployee ( ID ).getConstraints ().containsKey ( date ) &&
                employeeController.getEmployee ( ID ).getConstraints ().get ( date ). isMorningShift () && shift == 0)
            throw new EmployeeException ( "Employee is unavailable." );
        if(employeeController.getEmployee ( ID ).getConstraints ().containsKey ( date ) &&
                employeeController.getEmployee ( ID ).getConstraints ().get ( date ). isEveningShift () && shift == 1)
            throw new EmployeeException ( "Employee is unavailable." );
        if(getWeeklyShiftSchedule ( date ).getShift ( date, 1-shift ).isWorking ( role, ID ))
            throw new EmployeeException ( "Employee is already manning the other shift of the day." );
        getWeeklyShiftSchedule ( date ).addEmployeeToShift ( role, ID, date, shift );
    }

    public void deleteEmployeeFromShift(String role, String ID, LocalDate date, int shift) throws EmployeeException, SQLException {
        if(date.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "the date has already passed." );
        if((date.getDayOfWeek ().getValue ()== 5 && shift == 1 )| (date.getDayOfWeek ().getValue ()== 6 && shift == 0 ))
            throw new EmployeeException ( "Super-Lee does not work at Shabbat." );
        getWeeklyShiftSchedule ( date ).deleteEmployeeFromShift (role, ID, date, shift );
    }

    public void changeShiftType(LocalDate date, int shift, String shiftType) throws EmployeeException, SQLException {
        if(date.isBefore ( LocalDate.now () ))
            throw new EmployeeException ( "the date has already passed." );
        if((date.getDayOfWeek ().getValue ()== 5 && shift == 1 )| (date.getDayOfWeek ().getValue ()== 6 && shift == 0 ))
            throw new EmployeeException ( "Super-Lee does not work at Shabbat." );
        getWeeklyShiftSchedule ( date ).changeShiftType ( date, shift, shiftType );
    }

    public void createShiftType(String shiftType, HashMap<String, Integer> manning) throws EmployeeException, SQLException {
        if(!ShiftTypes.getInstance ().contains(shiftType))
            ShiftTypes.getInstance ().addShiftType ( shiftType, manning );
        for( Map.Entry <String, Integer> entry : manning.entrySet ())
            DalShiftTypeController.getInstance ().insert ( new DalShiftType ( entry.getValue (), shiftType, entry.getKey () ) );
    }

    public void updateRoleManning(String shiftType, String role, int num) throws EmployeeException {
        ShiftTypes.getInstance ().updateRoleManning( shiftType, role, num );
    }

    public void addRoleManning(String shiftType, String role, int num) throws EmployeeException {
        ShiftTypes.getInstance ().addRoleManning ( shiftType, role, num );
    }

    public WeeklyShiftSchedule getWeeklyShiftSchedule(LocalDate date) throws EmployeeException {
        WeeklyShiftSchedule output;
        LocalDate sunday;
        if(date.getDayOfWeek () == DayOfWeek.SUNDAY) {
            sunday = date;
        }
        else {
            TemporalField field = WeekFields.of ( Locale.US ).dayOfWeek ();
            sunday = date.with ( field, 1 );
        }
        output = shifts.get (sunday);
        if(output == null)
            throw new EmployeeException ( "No such shift exists." );
        return output;
    }

    public Shift getShift(LocalDate date, int shift) throws EmployeeException {
        return getWeeklyShiftSchedule ( date ).getShift (date, shift);
    }

    public void deleteRoleFromShiftType(String shiftType, String role) throws EmployeeException {
        ShiftTypes.getInstance ().deleteRole(shiftType, role);
    }

    public void createData() throws EmployeeException, SQLException {
        createShiftTypes ();
        LocalDate temp = LocalDate.now ().plusDays ( 7 );
        LocalDate sunday = temp;
        if(temp.getDayOfWeek () != DayOfWeek.SUNDAY) {
            TemporalField field = WeekFields.of ( Locale.US ).dayOfWeek ( );
            sunday = temp.with ( field, 1 );
        }
        shifts.put (sunday, getRecommendation ( sunday, true));
        shifts.put (sunday.plusDays ( 7 ), getRecommendation ( sunday.plusDays ( 7 ), false ));
    }

    private void createShiftTypes() throws EmployeeException, SQLException {
        HashMap<String, Integer> manning = new HashMap<> (  );
        manning.put ( "shiftManager", 1 );
        manning.put ( "cashier", 2 );
        manning.put ( "guard", 1 );
        manning.put ( "usher", 2 );
        manning.put ( "storeKeeper", 1 );
        manning.put ( "driverC", 1 );
        manning.put ( "driverC1", 1 );
        createShiftType ( "morningShift", manning );
        createShiftType ( "eveningShift", manning );
    }

    public HashMap<LocalDate, HashMap<Integer, List<String>>> getDaysAndDrivers() throws EmployeeException {
         LocalDate date = LocalDate.now ();
         HashMap<LocalDate, HashMap<Integer, List<String>>> daysAndDrivers = new HashMap<> (  );
         WeeklyShiftSchedule cur;
         while (isExist ( date )){
             cur = getWeeklyShiftSchedule ( date );
             cur.getDaysAndDrivers(date, daysAndDrivers);
             date = date.plusDays ( 7-(date.getDayOfWeek ().getValue () % 7) );
         }
         return daysAndDrivers;
    }

    public void addDriverToShift(String id, LocalDate date, int shift) throws EmployeeException, SQLException {
        if (shift == 1) {
            if (getShift ( date, 0 ).isWorking ( "driver", id ))
                throw new EmployeeException ( "the driver is already manning the morning shift." );
        } else {
            if (getShift ( date, 1 ).isWorking ( "driver", id ))
                throw new EmployeeException ( "the driver is already manning the evening shift." );
        }
        getShift ( date, shift ).addEmployee ( "driver", id );
    }

    public boolean isDriverAssigned(String id, LocalDate date, int shift) throws EmployeeException {
        return getShift ( date, shift ).isWorking ( "driver", id );
    }

    private boolean isExist(LocalDate date){
        try {
            getWeeklyShiftSchedule ( date );
            return true;
        }catch (EmployeeException e){
            return false;
        }
    }

    public void loadData() throws SQLException, EmployeeException {
        LoadShifts ();
        LoadShiftTypes();
    }

    private void LoadShiftTypes() throws SQLException, EmployeeException {
        LinkedList<DalShiftType> types = DalShiftTypeController.getInstance ().load ();
        HashMap<String, Integer> manning = new HashMap<> (  );
        String type;
        try {
            type = types.get ( 0 ).getType ();
        } catch (IndexOutOfBoundsException e){
            return;
        }
        for(DalShiftType cur : types){
            if(!cur.getType ().equals ( type ))
            {
                ShiftTypes.getInstance ().addShiftType ( type, manning );
                manning = new HashMap<> (  );
                type = cur.getType ();
            }
            manning.put ( cur.getRole (),cur.getAmount ());
        }
        ShiftTypes.getInstance ().addShiftType ( type, manning );
    }

    private void LoadShifts() throws SQLException {
        LinkedList<DalShift> shifts = DalShiftController.getInstance ().load ();
        Integer j = 0;
        try {
            shifts.get ( j );
        }catch (IndexOutOfBoundsException e){
            this.shifts = new HashMap<> (  );
            return;
        }
        if (shifts.size () >= j && !shifts.get ( j ).getDate ().getDayOfWeek ().equals ( DayOfWeek.SUNDAY )){
            j = createPartlyWeek(shifts);
        }
        DalShift cur;
        try {
            cur = shifts.get ( j );
        }catch (IndexOutOfBoundsException e){
            cur = null;
        }
        while (cur != null){
            j = createWeek ( shifts, j );
            try {
                cur = shifts.get ( j );
            }catch (IndexOutOfBoundsException e){
                cur = null;
            }
        }
    }

    private Integer createPartlyWeek(LinkedList<DalShift> shifts) {
        int cur = 0;
        LinkedList<DalShift> shift;
        Shift newShift;
        int day = shifts.get ( 0 ).getDate ().getDayOfWeek ().getValue () % 7;
        DalShift temp = shifts.get ( 0 );
        Shift[][] week = new Shift[7][2];
        for(int i = 0; i < day; i ++){
            week[i][0] =null;
            week[i][1] = null;
        }
        for(int j = day; j < 5; j ++){
            for ( int k = 0 ; k < 2 ; k++ ) {
                shift = getListOfShift ( shifts, temp, cur );
                cur = cur + shift.size ();
                newShift = createShift ( shift );
                week[j][k] = newShift;
                try {
                    temp = shifts.get ( cur );
                }catch (IndexOutOfBoundsException e){
                    return cur;
                }
            }
        }
        if(day < 5) {
            if(day < 4) {
                shift = getListOfShift ( shifts, temp, cur );
                cur = cur + shift.size ( );
                newShift = createShift ( shift );
                week[5][0] = newShift;
                temp = shifts.get ( cur );
            }
            shift = getListOfShift ( shifts, temp, cur );
            newShift = createShift ( shift );
            week[6][1] = newShift;
            WeeklyShiftSchedule weeklyShiftSchedule = new WeeklyShiftSchedule ( week[0][0].getDate ( ), week );
            this.shifts.put ( weeklyShiftSchedule.getDate ( ), weeklyShiftSchedule );
        }
        return cur;
    }

    private Integer createWeek(LinkedList<DalShift> shifts, Integer j) {
        LinkedList<DalShift> shift;
        Shift[][] week = new Shift[7][2];
        Shift newShift;
        DalShift temp = shifts.get ( j );
        if (temp == null)
            return j;
        for ( int i = 0 ; i < 5 ; i++ ) {
            for ( int k = 0 ; k < 2 ; k++ ) {
                shift = getListOfShift ( shifts, temp, j );
                j = j + shift.size ();
                newShift = createShift ( shift );
                week[i][k] = newShift;
                try {
                    temp = shifts.get ( j );
                }catch (IndexOutOfBoundsException e){
                    return j;
                }
            }
        }
        shift = getListOfShift ( shifts, temp, j );
        j = j + shift.size ();
        newShift = createShift ( shift );
        week[5][0] = newShift;
        temp = shifts.get ( j );
        shift = getListOfShift ( shifts, temp, j );
        newShift = createShift ( shift );
        week[6][1] = newShift;
        WeeklyShiftSchedule weeklyShiftSchedule = new WeeklyShiftSchedule ( week[0][0].getDate ( ), week );
        this.shifts.put ( weeklyShiftSchedule.getDate ( ), weeklyShiftSchedule );
        return j;
    }


    private LinkedList<DalShift> getListOfShift(LinkedList<DalShift> shifts,DalShift cur, Integer j){
        LinkedList<DalShift> output = new LinkedList<> (  );
        if(cur != null) {
            LocalDate tempDate = cur.getDate ( );
            int tempShift = cur.getShift ( );
            while (cur != null && cur.getDate ( ).equals ( tempDate ) && cur.getShift ( ) == tempShift) {
                output.add ( cur );
                try {
                    cur = shifts.get ( ++j );
                }catch (IndexOutOfBoundsException e){
                    cur = null;
                }
            }
        }
        return output;
    }

    private Shift createShift(List<DalShift> shift) {
        Shift newShift;
        HashMap <Role, List<String>> manning = new HashMap<> (  );
        for(DalShift cur: shift){
            if(!manning.containsKey (Role.valueOf (cur.getRole())))
                manning.put ( Role.valueOf (cur.getRole()), new ArrayList<> (  ) );
            manning.get ( Role.valueOf (cur.getRole()) ).add ( cur.getEmployeeId () );
        }
        newShift = new Shift ( shift.get(0).getDate (), manning, shift.get(0).getType (), shift.get(0).getShift () );
        return newShift;
    }
}
