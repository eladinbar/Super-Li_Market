package Employees.business_layer.facade;

import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;
import Employees.business_layer.Shift.ShiftController;
import Employees.business_layer.Shift.WeeklyShiftSchedule;
import Employees.business_layer.facade.facadeObject.FacadeShift;
import Employees.business_layer.facade.facadeObject.FacadeWeeklyShiftSchedule;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class FacadeService {
    private EmployeeService employeeService;
    private ShiftService shiftService;

    public  FacadeService()
    {
        EmployeeController employeeController = new EmployeeController ();
        employeeService = new EmployeeService (employeeController);
        shiftService = new ShiftService (new ShiftController ( employeeController ) );
    }

    //shift service responsibility

    public ResponseT<FacadeWeeklyShiftSchedule> getRecommendation(LocalDate startingDate) {
        return shiftService.getRecommendation ( startingDate );
    }

    public Response createWeeklyshiftSchedule(LocalDate startingDate, FacadeShift[][] shifts)
    {
        return shiftService.createWeeklyshiftSchedule ( startingDate, shifts );
    }

    public ResponseT<WeeklyShiftSchedule> createEmptyWeeklyShiftSchedule(LocalDate startingDate){
        return shiftService.createEmptyWeeklyShiftSchedule ( startingDate );
    }

    public Response addShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) {
        return shiftService.addShift ( date, shift, manning );
    }

    public Response changeShift(LocalDate date, int shift, HashMap<Role, List<Integer>> manning) {
        return shiftService.changeShift ( date, shift, manning);
    }

    public Response addEmployeeToShift(Role role, int ID, LocalDate date, int shift){
        return shiftService.addEmployeeToShift ( role, ID, date, shift );
    }

    public Response deleteEmployeeFromShift(Role role, int ID, LocalDate date, int shift)  {
        return shiftService.deleteEmployeeFromShift ( role, ID, date, shift );
    }

    public Response changeShiftType(LocalDate date, int shift, String shiftType) {
        return shiftService.changeShiftType ( date, shift, shiftType);
    }

    public Response createShiftType(String shiftype, HashMap<Role, Integer> manning){
        return shiftService.createShiftType ( shiftype, manning );
    }

    public Response updateRoleManning(String shiftType, Role role, int num) {
        return shiftService.updateRoleManning ( shiftType, role, num );
    }

    public Response addRoleManning(String shiftType, Role role, int num) {
        return shiftService.addRoleManning ( shiftType, role, num );
    }

    public ResponseT<FacadeWeeklyShiftSchedule> getWeeklyShiftSchedule(LocalDate date) {
        return shiftService.getWeeklyShiftSchedule ( date );
    }

    //employee service responsibility



}
