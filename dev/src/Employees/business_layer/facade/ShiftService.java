package Employees.business_layer.facade;

import Employees.EmployeeException;
import Employees.business_layer.Employee.Role;
import Employees.business_layer.Shift.Shift;
import Employees.business_layer.Shift.ShiftController;
import Employees.business_layer.Shift.ShiftTypes;
import Employees.business_layer.Shift.WeeklyShiftSchedule;
import Employees.business_layer.facade.facadeObject.FacadeShift;
import Employees.business_layer.facade.facadeObject.FacadeWeeklyShiftSchedule;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ShiftService {
    private ShiftController shiftController;

    public ShiftService(ShiftController shiftController)
    {
        this.shiftController = shiftController;
    }

    public ResponseT<FacadeWeeklyShiftSchedule> getRecommendation(LocalDate startingDate) {
        try {
            WeeklyShiftSchedule weeklyShiftSchedule = shiftController.getRecommendation ( startingDate );
            FacadeWeeklyShiftSchedule facadeWeeklyShiftSchedule = new FacadeWeeklyShiftSchedule ( weeklyShiftSchedule );
            return new ResponseT<> ( facadeWeeklyShiftSchedule );
        } catch (EmployeeException e)
        {
            return new ResponseT(e.getMessage ());
        }
    }

    public ResponseT<FacadeWeeklyShiftSchedule> createWeeklyshiftSchedule(LocalDate startingDate, FacadeShift[][] shifts)
    {
        try{
            Shift[][] newShifts = new Shift[7][2];
            for ( int i = 0; i < 7; i ++ )
            {
                FacadeShift morning = shifts[i][0];
                FacadeShift evening = shifts[i][1];
                if(morning != null)
                    newShifts[i][0] = new Shift ( morning );
                if(evening != null)
                    newShifts[i][1] = new Shift ( evening );
            }
            FacadeWeeklyShiftSchedule facadeWeeklyShiftSchedule = new FacadeWeeklyShiftSchedule (shiftController.createWeeklyshiftSchedule ( startingDate, newShifts ));
            return new ResponseT ( facadeWeeklyShiftSchedule );
        }catch (EmployeeException e){
            return new ResponseT ( e.getMessage () );
        }
    }

    public ResponseT<FacadeWeeklyShiftSchedule> createEmptyWeeklyShiftSchedule(LocalDate startingDate){
        try {
            FacadeWeeklyShiftSchedule facadeWeeklyShiftSchedule = new FacadeWeeklyShiftSchedule ( new WeeklyShiftSchedule ( startingDate ) );
            return new ResponseT( facadeWeeklyShiftSchedule );
        }catch (EmployeeException e)
        {
            return new ResponseT<> ( e.getMessage () );
        }

    }

    public Response addShift(LocalDate date, int shift, HashMap<Role, List<String>> manning) {
        try
        {
            shiftController.addShift ( date, shift, manning );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response changeShift(LocalDate date, int shift, HashMap<Role, List<String>> manning) {
        try {
            shiftController.changeShift ( date, shift, manning );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response addEmployeeToShift(String role, String ID, LocalDate date, int shift){
        try
        {
            shiftController.addEmployeeToShift ( role, ID, date, shift );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response deleteEmployeeFromShift(String role, String ID, LocalDate date, int shift)  {
        try {
            shiftController.deleteEmployeeFromShift ( role, ID, date, shift );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response changeShiftType(LocalDate date, int shift, String shiftType) {
        try {
            shiftController.changeShiftType ( date, shift, shiftType);
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response createShiftType(String shiftype, HashMap<String, Integer> manning){
        try {
            shiftController.createShiftType ( shiftype, manning );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response updateRoleManning(String shiftType, Role role, int num) {
        try {
            shiftController.updateRoleManning ( shiftType, role, num );
            return new Response (  );
        }catch (EmployeeException e)
        {
            return new Response ( e.getMessage () );
        }
    }

    public Response addRoleManning(String shiftType, Role role, int num) {
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
}
