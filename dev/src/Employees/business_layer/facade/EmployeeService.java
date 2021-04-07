package Employees.business_layer.facade;

import Employees.EmployeeException;
import Employees.business_layer.Employee.*;
import Employees.business_layer.facade.facadeObject.FacadeBankAccountInfo;
import Employees.business_layer.facade.facadeObject.FacadeConstraint;
import Employees.business_layer.facade.facadeObject.FacadeEmployee;
import Employees.business_layer.facade.facadeObject.FacadeTermsOfEmployment;

import java.time.LocalDate;
import java.util.List;

public class EmployeeService {
    EmployeeController employeeController;

    public EmployeeService(EmployeeController employeeController)
    {
        this.employeeController = employeeController;
    }

    public Response login(int ID, Role role) {

    }

    public Response logout()  {

    }

    public Response giveConstraint(FacadeEmployee employee, LocalDate date, int shift, String reason) {

    }

    public Response updateConstraint (FacadeEmployee employee, LocalDate date, int shift, String reason) {

    }

    public Response deleteConstraint (FacadeEmployee employee, LocalDate date, int shift)  {

    }

    public Response addEmployee(Role role, int Id, FacadeTermsOfEmployment terms, LocalDate transactionDate, FacadeBankAccountInfo bank) {

    }

    public Response removeEmployee(int Id)  {

    }

    public Response deleteBankAccount(int Id) throws EmployeeException {

    }

    public Response updateBankAccount(int Id, int accountNum, int bankBranch, String bank) {

    }

    public Response updateTermsOfemployee(int Id, int salary, int educationFund, int sickDays, int daysOff) {

    }


    public Response addEmployee(FacadeEmployee employee) {

    }

    public Response addManager(FacadeEmployee manager) {

    }

    public ResponseT<FacadeEmployee> getEmployee(int ID){

    }

    public ResponseT<FacadeEmployee> getLoggedin() {
    }

    public ResponseT<List<FacadeConstraint>> getConstraints() {
    }

    public ResponseT<List<FacadeConstraint>> getConstraints(String id) {

    }

    public ResponseT<List<FacadeEmployee>> getEmployees() {

    }
}
