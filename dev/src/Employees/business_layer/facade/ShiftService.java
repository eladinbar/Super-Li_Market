package Employees.business_layer.facade;

import Employees.EmployeeException;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;
import Employees.business_layer.Shift.Shift;
import Employees.business_layer.Shift.ShiftController;
import Employees.business_layer.Shift.ShiftTypes;
import Employees.business_layer.Shift.WeeklyShiftSchedule;
import Employees.business_layer.facade.facadeObject.FacadeShift;
import Employees.business_layer.facade.facadeObject.FacadeWeeklyShiftSchedule;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShiftService {
    private ShiftController shiftController;

    public ShiftService()
    {
        shiftController = ShiftController.getInstance ();
    }

    public ResponseT<FacadeWeeklyShiftSchedule> getRecommendation(LocalDate startingDate, boolean start) throws SQLException {
        try {
            WeeklyShiftSchedule weeklyShiftSchedule = shiftController.getRecommendation ( startingDate, start );
            FacadeWeeklyShiftSchedule facadeWeeklyShiftSchedule = new FacadeWeeklyShiftSchedule ( weeklyShiftSchedule );
            return new ResponseT<> ( facadeWeeklyShiftSchedule );
        } catch (EmployeeException e)
        {
            return new ResponseT(e.getMessage ());
        }
    }

    public ResponseT<FacadeWeeklyShiftSchedule> createWeeklyShiftSchedule(LocalDate startingDate, FacadeShift[][] shifts) throws SQLException {
        try{
            Shift[][] newShifts = new Shift[7][2];
            for ( int i = 0; i < 7; i ++ )
            {
                FacadeShift morning = shifts[i][0];
                FacadeShift evening = shifts[i][1];
                if(morning != null) {
                    checkManningValidityRole(newShifts[i][0].getManning ());
                    checkConstraintRole ( newShifts[i][0].getManning (), startingDate.plusDays ( i ), 0 );
                    newShifts[i][0] = new Shift ( morning );
                }
                if(evening != null) {
                    checkManningValidityRole(newShifts[i][1].getManning ());
                    checkConstraintRole ( newShifts[i][0].getManning (), startingDate.plusDays ( i ), 1 );
                    newShifts[i][1] = new Shift ( evening );
                }
            }
            FacadeWeeklyShiftSchedule facadeWeeklyShiftSchedule = new FacadeWeeklyShiftSchedule (shiftController.createWeeklyShiftSchedule ( startingDate, newShifts ));
            return new ResponseT ( facadeWeeklyShiftSchedule );
        }catch (EmployeeException e){
            return new ResponseT ( e.getMessage () );
        }
    }

    private void checkManningValidityRole(HashMap<Role, List<String>> manning) throws EmployeeException {
        for( Map.Entry<Role, List<String>> entry : manning.entrySet () ){
            for(String emp : entry.getValue ())
                if(!EmployeeController.getInstance ().isExist ( entry.getKey ().name (), emp ))
                    throw new EmployeeException ( emp + " and role " + entry.getKey ().name () + " does not exist in system" );
        }
    }

    private void checkConstraintRole(HashMap<Role, List<String>> manning, LocalDate date, int shift) throws EmployeeException {
        for ( Map.Entry<Role, List<String>> entry : manning.entrySet ( ) ) {
            for ( String ID : entry.getValue ( ) ) {
                if (EmployeeController.getInstance ( ).getEmployee ( ID ).getConstraints ( ).containsKey ( date ) &&
                        EmployeeController.getInstance ( ).getEmployee ( ID ).getConstraints ( ).get ( date ).isMorningShift ( ) && shift == 0)
                    throw new EmployeeException ( "Employee is unavailable." );
                if (EmployeeController.getInstance ( ).getEmployee ( ID ).getConstraints ( ).containsKey ( date ) &&
                        EmployeeController.getInstance ( ).getEmployee ( ID ).getConstraints ( ).get ( date ).isEveningShift ( ) && shift == 1)
                    throw new EmployeeException ( "Employee is unavailable." );
            }
        }
    }

    private void checkConstraint(HashMap<String, List<String>> manning, LocalDate date, int shift) throws EmployeeException {
        for ( Map.Entry<String, List<String>> entry : manning.entrySet ( ) ) {
            for ( String ID : entry.getValue ( ) ) {
                if (EmployeeController.getInstance ( ).getEmployee ( ID ).getConstraints ( ).containsKey ( date ) &&
                        EmployeeController.getInstance ( ).getEmployee ( ID ).getConstraints ( ).get ( date ).isMorningShift ( ) && shift == 0)
                    throw new EmployeeException ( "Employee is unavailable." );
                if (EmployeeController.getInstance ( ).getEmployee ( ID ).getConstraints ( ).containsKey ( date ) &&
                        EmployeeController.getInstance ( ).getEmployee ( ID ).getConstraints ( ).get ( date ).isEveningShift ( ) && shift == 1)
                    throw new EmployeeException ( "Employee is unavailable." );
            }
        }
    }

    private void checkManningValidity(HashMap<String, List<String>> manning) throws EmployeeException {
        for( Map.Entry<String, List<String>> entry : manning.entrySet () ){
            for(String emp : entry.getValue ())
                if(!EmployeeController.getInstance ().isExist ( entry.getKey (), emp ))
                    throw new EmployeeException ( emp + " and role " + entry.getKey () + " does not exist in system" );
        }
    }

    public Response changeShift(LocalDate date, int shift, HashMap<String, List<String>> manning) throws SQLException {
        try {
            checkManningValidity(manning);
            checkConstraint ( manning, date, shift );
            shiftController.changeShift ( date, shift, manning );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response addEmployeeToShift(String role, String ID, LocalDate date, int shift) throws SQLException {
        try {
            shiftController.addEmployeeToShift ( role, ID, date, shift );
            return new Response ( );
        } catch (EmployeeException e) {
            return new Response ( e.getMessage ( ) );
        }
    }

    public Response deleteEmployeeFromShift(String role, String ID, LocalDate date, int shift) throws SQLException {
        try {
            shiftController.deleteEmployeeFromShift ( role, ID, date, shift );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response changeShiftType(LocalDate date, int shift, String shiftType) throws SQLException {
        try {
            shiftController.changeShiftType ( date, shift, shiftType);
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response createShiftType(String shiftType, HashMap<String, Integer> manning) throws SQLException {
        try {
            shiftController.createShiftType ( shiftType, manning );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response updateRoleManning(String shiftType, String role, int num) {
        try {
            shiftController.updateRoleManning ( shiftType, role, num );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response addRoleManning(String shiftType, String role, int num) {
        try {
            shiftController.addRoleManning ( shiftType, role, num );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public ResponseT<FacadeWeeklyShiftSchedule> getWeeklyShiftSchedule(LocalDate date) {
        try {
            FacadeWeeklyShiftSchedule facadeWeeklyShiftSchedule = new FacadeWeeklyShiftSchedule ( shiftController.getWeeklyShiftSchedule(date) );
            return new ResponseT ( facadeWeeklyShiftSchedule );
        }catch (EmployeeException e)
        {
            return new ResponseT ( e.getMessage () );
        }
    }

    public ResponseT<FacadeShift> getShift(LocalDate date, int shift) {
        try {
            Shift s = shiftController.getShift ( date, shift );
            return new ResponseT (  new FacadeShift ( s ) );
        } catch (EmployeeException e){
            return new ResponseT ( e.getMessage () );
        }
    }

    public ResponseT<HashMap<Role, Integer>> getShiftTypeManning(String shiftType) {
        try{
            HashMap<Role, Integer> manning = ShiftTypes.getInstance ().getShiftTypeManning ( shiftType );
            return new ResponseT ( manning );
        }catch (EmployeeException e){
            return new ResponseT ( e.getMessage () );
        }
    }

    public Response deleteRoleFromShiftType(String shiftType, String role) {
        try {
            shiftController.deleteRoleFromShiftType(shiftType, role);
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response createData() throws SQLException {
        try {
            shiftController.createData ( );
            return new Response (  );
        } catch (EmployeeException e) {
            return new Response ( e.getMessage ( ) );
        }
    }

    public Response loadData() throws SQLException {
        try {
            shiftController.loadData();
            return new Response (  );
        } catch (EmployeeException e){
            return new Response ( e.getMessage () );
        }
    }
}
