package Employees.business_layer.facade;

import Employees.EmployeeException;
import Employees.business_layer.Employee.*;
import Employees.business_layer.facade.facadeObject.FacadeBankAccountInfo;
import Employees.business_layer.facade.facadeObject.FacadeConstraint;
import Employees.business_layer.facade.facadeObject.FacadeEmployee;
import Employees.business_layer.facade.facadeObject.FacadeTermsOfEmployment;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EmployeeService {
    EmployeeController employeeController;

    public EmployeeService(EmployeeController employeeController)
    {
        this.employeeController = employeeController;
    }

    public ResponseT<FacadeConstraint> login(String ID, Role role) {
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
            return new ResponseT<>(new FacadeEmployee(employeeController.addEmployee(employee)));
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response addManager(FacadeEmployee manager) {
        try{
            return new ResponseT<>(new FacadeEmployee(employeeController.addManager(manager)));
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public ResponseT<FacadeEmployee> removeEmployee(String Id)  {
        try{
            return new ResponseT<>(new FacadeEmployee(employeeController.removeEmployee(Id)));
        }
        catch (EmployeeException e){
            return new ResponseT<>(e.getMessage());
        }
    }

    public Response deleteBankAccount(String Id) throws EmployeeException {
        try{
            employeeController.deleteBankAccount(Id);
            return new Response();
        }
        catch (EmployeeException e){
            return new Response(e.getMessage());
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

    public Response updateTermsOfemployee(String Id, int salary, int educationFund, int sickDays, int daysOff) {
        try{
            employeeController.updateTermsOfemployee(Id,salary, educationFund, sickDays,daysOff);
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

    public ResponseT<FacadeEmployee> getLoggedin() {
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

    public void createData (){}

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
