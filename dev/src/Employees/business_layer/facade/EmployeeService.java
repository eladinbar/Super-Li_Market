package Employees.business_layer.facade;

import Employees.EmployeeException;
import Employees.business_layer.Employee.Constraint;
import Employees.business_layer.Employee.Employee;
import Employees.business_layer.Employee.EmployeeController;
import Employees.business_layer.Employee.Role;
import Employees.business_layer.facade.facadeObject.FacadeConstraint;
import Employees.business_layer.facade.facadeObject.FacadeEmployee;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EmployeeService {
    EmployeeController employeeController;

    public EmployeeService()
    {
        employeeController = EmployeeController.getInstance ();
    }

    public ResponseT<FacadeEmployee> login(String ID, Role role) {
        try{
            return new ResponseT(new FacadeEmployee(employeeController.login(ID,role.name())));
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response logout()  {
        try{
            return new ResponseT<>(new FacadeEmployee(employeeController.logout()));
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response giveConstraint(LocalDate date, int shift, String reason) {
        try{
            employeeController.giveConstraint(date, shift, reason);
            return new Response();
        }
        catch (EmployeeException e){
            return new Response(e.getMessage());
        }
    }



    public Response deleteConstraint (LocalDate date, int shift)  {
        try{
            employeeController.deleteConstraint(date, shift);
            return new Response();
        }
        catch (EmployeeException e){
            return new Response(e.getMessage());
        }
    }

    public ResponseT<FacadeEmployee> addEmployee(FacadeEmployee employee) {
        try{
            employeeController.addEmployee ( employee );
            return new ResponseT<>(employee);
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<FacadeEmployee> addDriver(FacadeEmployee employee, String name) {
        try{
            employeeController.addDriver ( employee, name );
            return new ResponseT<>(employee);
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<FacadeEmployee> addManager(FacadeEmployee manager) {
        try{
            return new ResponseT(new FacadeEmployee(employeeController.addManager(manager)));
        }
        catch (EmployeeException e){
            return new ResponseT(e.getMessage());
        }
    }

    public ResponseT<FacadeEmployee> removeEmployee(String Id)  {
        try{
            return new ResponseT(new FacadeEmployee(employeeController.removeEmployee(Id)));
        }
        catch (EmployeeException e){
            return new ResponseT(e.getMessage());
        }
    }

    public Response updateBankAccount(String Id, int accountNum, int bankBranch, String bank) {
        try{
            employeeController.updateBankAccount(Id,accountNum,bankBranch,bank);
            return new Response();
        }
        catch (EmployeeException e){
            return new Response(e.getMessage());
        }
    }

    public Response updateTermsOfEmployee(String Id, int salary, int educationFund, int sickDays, int daysOff) {
        try{
            employeeController.updateTermsOfEmployee(Id,salary, educationFund, sickDays,daysOff);
            return new Response();
        }
        catch (EmployeeException e){
            return new Response(e.getMessage());
        }
    }




    public ResponseT<FacadeEmployee> getEmployee(String Id){
        try{
            return new ResponseT<>(new FacadeEmployee(employeeController.getEmployee(Id)));
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<FacadeEmployee> getLoggedIn() {
        try{
            return new ResponseT<>(new FacadeEmployee(employeeController.getLoggedIn()));
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<HashMap<LocalDate,FacadeConstraint>> getConstraints() {
        try{
            HashMap<LocalDate,FacadeConstraint> constraints = convertConstrainToFacade(employeeController.getConstraints());
            return new ResponseT<>(constraints);
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }



    public ResponseT<HashMap<LocalDate,FacadeConstraint>> getConstraints(String id) {
        try{
            HashMap<LocalDate,FacadeConstraint> constraints = convertConstrainToFacade(employeeController.getConstraints(id));
            return new ResponseT<>(constraints);
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<HashMap<Role,List<String>>> getEmployees() {
        try{
            HashMap<Role, List<String>> toReturn = new HashMap<>();
            HashMap<String, Employee> employees = employeeController.getEmployees();

            for (Role role: Role.values()) {
                List<String> empByRole = new LinkedList<>();
                for (Employee employee: employees.values()) {
                    if(employee.getRole()==role){
                        empByRole.add(employee.getID());
                    }
                }
                toReturn.put(role, empByRole);
            }
            return new ResponseT<>(toReturn);
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response createData (){
        try {
            employeeController.createData();
            return new Response();
        } catch (EmployeeException e) {
            return new Response ( e.getMessage () );
        }
    }

    //private methods:
    private HashMap<LocalDate, FacadeConstraint> convertConstrainToFacade(HashMap<LocalDate, Constraint> toConvert) {
        HashMap<LocalDate, FacadeConstraint> converted = new HashMap<>();
        for (LocalDate date: toConvert.keySet()) {
            FacadeConstraint facadeConstraint = new FacadeConstraint(toConvert.get(date));
            converted.put(date, facadeConstraint );
        }
        return converted;
    }
}