package Employees.business_layer.Employee;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EmployeeController {
    private HashMap<Integer, Employee> employees;
    private Employee loggedIn;

    public EmployeeController(Employee employee){
        this.loggedIn = employee;
        employees = new HashMap<Integer, Employee>();
    }

    public void login(int ID, Role role){
        if(loggedIn!= null){
            throw new EmployeeExeption("Two users cannot be logged in at the same time");
        }
        if(!employees.containsKey(ID)) {
            throw new EmployeeExeption("Employee not found");
        }
            Employee toLogin = employees.get(ID);
            if(!toLogin.isEmployed()){
                throw new EmployeeExeption("The employee is not employed ");
            }
            if (toLogin.getRole() != (role)) {
                throw new EmployeeExeption("Id does not match to the role");
            }
        loggedIn = toLogin;

    }

    public void logout(){
        if(loggedIn==null){
            throw new EmployeeExeption("No user is loggedIn");
        }
        loggedIn= null;
    }

    public void giveConstraint(Employee employee, LocalDate date, int shift, String reason) throws Exception {
        if(!employee.isEmployed()){
            throw new EmployeeExeption("The employee is not employed ");
        }
        if (employee.getID() != loggedIn.getID()) {
            throw new EmployeeExeption("The relevant employee must be logged in to perform this action");
        }
        employee.giveConstraint(date, shift, reason);
    }

    public void updateConstraint (Employee employee, LocalDate date, int shift, String reason) throws Exception {
        if (employee.getID() != loggedIn.getID()) {
            throw new EmployeeExeption("The relevant employee must be logged in to perform this action");
        }
        employee.updateConstarint(date, shift, reason);
    }

    public void deleteConstraint (Employee employee, LocalDate date, int shift) throws Exception {
        if (employee.getID() != loggedIn.getID()) {
            throw new EmployeeExeption("The relevant employee must be logged in to perform this action");
        }
        employee.deletConstraint(date, shift);
    }

    public void addEmployee(Role role, int Id, TermsOfEmployment terms, LocalDate transactionDate, BankAccountInfo bank){
        if(!isManager(loggedIn.getRole()))
            throw new EmployeeExeption("Only an administrator can perform this operation");
       Employee newEmployee = new Employee(role, Id, terms, transactionDate, bank);
       employees.put(Id, newEmployee);
    }

    public void removeEmployee(int Id){
        if(!isManager(loggedIn.getRole())){
            throw new EmployeeExeption("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
                throw new EmployeeExeption("Employee not found");
        }
        employees.get(Id).setEmployed(false);
    }

    public void deleteBankAccount(int Id){
        if(!isManager(loggedIn.getRole())){
            throw new EmployeeExeption("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
            throw new EmployeeExeption("Employee not found");
        }
        employees.get(Id).setBank(null);
    }

    public void updateBankAccount(int Id, int accountNum, int bankBranch, String bank){
        if(!isManager(loggedIn.getRole())){
            throw new EmployeeExeption("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
            throw new EmployeeExeption("Employee not found");
        }
        BankAccountInfo toUpdate = employees.get(Id).getBank();
        toUpdate.setAccountNumber(accountNum);
        toUpdate.setBankBranch(bankBranch);
        toUpdate.setBank(bank);
    }

    public void updateTermsOfemployee(int Id, int salary, int educationFund, int sickDays, int daysOff){
        if(!isManager(loggedIn.getRole())){
            throw new EmployeeExeption("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
            throw new EmployeeExeption("Employee not found");
        }
        TermsOfEmployment toUpdate = employees.get(Id).getTerms();
        toUpdate.setSalary(salary);
        toUpdate.setEducationFund(educationFund);
        toUpdate.setSickDays(sickDays);
        toUpdate.setDaysOff(daysOff);
    }

    public LinkedList<Integer> getRoleinDate(LocalDate date, Role roleName, int shift){
        LinkedList<Integer> specificRole = new LinkedList<>();
        for (Map.Entry<Integer, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();
            if(employee.getRole()== roleName){
                if(employee.getConstraints().containsKey(date)) {//Checks if the employee has a constraint on this day
                  if(shift==1) {//Checks on morning shift
                      if (!employee.getConstraints().get(date).isMorningShift())// If the employee is free on this shift
                          specificRole.add(employee.getID());
                  }
                    if(shift==2) {//Checks on evening shift
                        if (!employee.getConstraints().get(date).isEveningShift())// If the employee is free on this shift
                            specificRole.add(employee.getID());
                    }
                }
                else{ // If the employee is free on this day
                    specificRole.add(employee.getID());
                }
            }
        }
        return specificRole;
    }



// private methods
    private BankAccountInfo createAccount(int accountNum, int bankBranch, String bank){
        return new BankAccountInfo(accountNum, bankBranch, bank);
    }

    private TermsOfEmployment creatTermsOfEmployment( int salary, int educationFund, int sickDays, int daysOff){
        return new TermsOfEmployment(salary, educationFund, sickDays, daysOff);
    }

    private boolean isManager(Role role){
        return role == Role.branchManager | role == Role.branchManagerAssistent | role == Role.humanResourcesMananger;
    }
}
