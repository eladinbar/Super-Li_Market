package Employees.business_layer.facade;

import Employees.EmployeeException;
import Employees.business_layer.Employee.*;
import Employees.business_layer.Shift.Shift;
import Employees.business_layer.Shift.ShiftController;
import Employees.business_layer.Shift.WeeklyShiftSchedule;
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

    public Response addShift(LocalDate date, int shift, HashMap<Role, List<String>> manning) {
        return shiftService.addShift ( date, shift, manning );
    }

    public Response changeShift(LocalDate date, int shift, HashMap<Role, List<String>> manning) {
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

    public Response updateRoleManning(String shiftType, Role role, int num) {
        return shiftService.updateRoleManning ( shiftType, role, num );
    }

    public Response addRoleManning(String shiftType, Role role, int num) {
        return shiftService.addRoleManning ( shiftType, role, num );
    }

    public ResponseT<FacadeWeeklyShiftSchedule> getWeeklyShiftSchedule(LocalDate date) {
        return shiftService.getWeeklyShiftSchedule ( date );
    }

    public ResponseT<FacadeShift> getShift(LocalDate date, int shift){
        return shiftService.getShift(date, shift);
    }

    //employee service responsibility

    public Response login(int ID, Role role) {
        return employeeService.login ( ID, role );
    }

    public Response logout()  {
        return employeeService.logout ();
    }

    public Response giveConstraint(FacadeEmployee employee, LocalDate date, int shift, String reason) {
        return employeeService.giveConstraint ( employee, date, shift, reason );
    }

    public Response updateConstraint (FacadeEmployee employee, LocalDate date, int shift, String reason) {
        return employeeService.updateConstraint ( employee, date, shift, reason);
    }

    public Response deleteConstraint (LocalDate date, int shift)  {
        return employeeService.deleteConstraint (date, shift );
    }

    public Response addEmployee(Role role, int Id, FacadeTermsOfEmployment terms, LocalDate transactionDate, FacadeBankAccountInfo bank) {
        return employeeService.addEmployee ( role, Id, terms, transactionDate, bank );
    }

    public Response removeEmployee(int Id)  {
        return employeeService.removeEmployee ( Id );
    }

    public Response deleteBankAccount(int Id){
        return employeeService.removeEmployee ( Id );
    }

    public Response updateBankAccount(int Id, int accountNum, int bankBranch, String bank) {
        return employeeService.updateBankAccount ( Id,accountNum,bankBranch,bank );
    }

    public Response updateTermsOfemployee(int Id, int salary, int educationFund, int sickDays, int daysOff) {
        return employeeService.updateTermsOfemployee ( Id,salary,educationFund,sickDays,daysOff );
    }

    public Response addEmployee(FacadeEmployee employee) {
        return employeeService.addEmployee ( employee );
    }

    public Response addManager(FacadeEmployee manager) {
        return employeeService.addManager ( manager );
    }


    public ResponseT<List<FacadeConstraint>> getConstraints() {
        return employeeService.getConstraints();
    }

    public ResponseT<FacadeEmployee> getLoggedin() {
        return employeeService.getLoggedin();
    }

    public ResponseT<FacadeEmployee> getEmployee(String ID){
        return employeeService.getEmployee ( ID );
    }

    public ResponseT<List<FacadeConstraint>> getConstraints(String id) {
        return  employeeService.getConstraints (id);
    }

    public ResponseT<List<FacadeEmployee>> getEmployees() {
        return employeeService.getEmployees();
    }
}
