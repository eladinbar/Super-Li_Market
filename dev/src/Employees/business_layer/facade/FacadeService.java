package Employees.business_layer.facade;

import Employees.EmployeeException;
import Employees.business_layer.Employee.*;
import Employees.business_layer.Shift.ShiftController;
import Employees.business_layer.facade.facadeObject.*;

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

    public ResponseT<FacadeWeeklyShiftSchedule> createWeeklyshiftSchedule(LocalDate startingDate, FacadeShift[][] shifts)
    {
        return shiftService.createWeeklyshiftSchedule ( startingDate, shifts );
    }

    public Response changeShift(LocalDate date, int shift, HashMap<String, List<String>> manning) {
        return shiftService.changeShift ( date, shift, manning);
    }

    public Response addEmployeeToShift(String role, String ID, LocalDate date, int shift){
        return shiftService.addEmployeeToShift ( role, ID, date, shift );
    }

    public Response deleteEmployeeFromShift(String role, String ID, LocalDate date, int shift)  {
        return shiftService.deleteEmployeeFromShift ( role, ID, date, shift );
    }

    public Response changeShiftType(LocalDate date, int shift, String shiftType) {
        return shiftService.changeShiftType ( date, shift, shiftType);
    }

    public Response createShiftType(String shiftype, HashMap<String, Integer> manning){
        return shiftService.createShiftType ( shiftype, manning );
    }

    public Response updateRoleManning(String shiftType, String role, int num) {
        return shiftService.updateRoleManning ( shiftType, role, num );
    }

    public Response addRoleManning(String shiftType, String role, int num) {
        return shiftService.addRoleManning ( shiftType, role, num );
    }

    public ResponseT<FacadeWeeklyShiftSchedule> getWeeklyShiftSchedule(LocalDate date) {
        return shiftService.getWeeklyShiftSchedule ( date );
    }

    public ResponseT<FacadeShift> getShift(LocalDate date, int shift){
        return shiftService.getShift(date, shift);
    }

    public ResponseT<HashMap<Role, Integer>> getShiftType(String shiftType) {
        return shiftService.getShiftTypeManning(shiftType);
    }

    public Response deleteRoleFromShiftType(String shiftType, String role) {
        return shiftService.deleteRoleFromShiftType(shiftType, role);
    }

    //employee service responsibility

    public ResponseT<FacadeEmployee> login(String ID, Role role) {
        return employeeService.login ( ID, role );
    }

    public Response logout()  {
        return employeeService.logout ();
    }

    public Response giveConstraint(LocalDate date, int shift, String reason) {
        return employeeService.giveConstraint (date, shift, reason );
    }

    public Response deleteConstraint (LocalDate date, int shift)  {
        return employeeService.deleteConstraint (date, shift );
    }

    public Response removeEmployee(String Id)  {
        return employeeService.removeEmployee ( Id );
    }

    public Response updateBankAccount(String Id, int accountNum, int bankBranch, String bank) {
        return employeeService.updateBankAccount ( Id,accountNum,bankBranch,bank );
    }

    public Response updateTermsOfEmployee(String Id, int salary, int educationFund, int sickDays, int daysOff) {
        return employeeService.updateTermsOfemployee ( Id,salary,educationFund,sickDays,daysOff );
    }

    public Response addEmployee(FacadeEmployee employee) {
        return employeeService.addEmployee ( employee );
    }

    public Response addManager(FacadeEmployee manager) {
        return employeeService.addManager ( manager );
    }


    public ResponseT<HashMap<LocalDate,FacadeConstraint>> getConstraints() {
        return employeeService.getConstraints();
    }

    public ResponseT<FacadeEmployee> getLoggedin() {
        return employeeService.getLoggedin();
    }

    public ResponseT<FacadeEmployee> getEmployee(String ID){
        return employeeService.getEmployee ( ID );
    }

    public ResponseT<HashMap<LocalDate,FacadeConstraint>> getConstraints(String id) {
        return  employeeService.getConstraints (id);
    }

    public ResponseT<HashMap<Role, List<String>>> getEmployees() {
        return employeeService.getEmployees();
    }

    public Response createData() {
        Response response = employeeService.createData ( );
        if(response.errorOccured ())
            return response;
        response = shiftService.createData ( );
        return response;
    }
}
