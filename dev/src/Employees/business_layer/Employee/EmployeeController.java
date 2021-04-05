package Employees.business_layer.Employee;

import Employees.EmployeeException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EmployeeController {
    private HashMap<Integer, Employee> employees;
    private Employee loggedIn;

    public EmployeeController(){
        this.loggedIn = null;
        employees = new HashMap<>();
    }

    public void login(int ID, Role role) throws EmployeeException {
        if(loggedIn!= null){
            throw new EmployeeException("Two users cannot be logged in at the same time");
        }
        if(!employees.containsKey(ID)) {
            throw new EmployeeException("Employee not found");
        }
            Employee toLogin = employees.get(ID);
            if(!toLogin.isEmployed()){
                throw new EmployeeException("The employee is not employed ");
            }
            if (toLogin.getRole() != (role)) {
                throw new EmployeeException("Id does not match to the role");
            }
        loggedIn = toLogin;

    }

    public void logout() throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is loggedIn");
        }
        loggedIn= null;
    }

    public void giveConstraint(LocalDate date, int shift, String reason) throws EmployeeException {
        if(isManager(loggedIn.getRole())){
            throw new EmployeeException("The method 'giveConstraint' was called from a user in a managerial position");
        }

        if(!loggedIn.isEmployed()){
            throw new EmployeeException("The employee is not employed ");
        }
        if (loggedIn.getID() != loggedIn.getID()) {
            throw new EmployeeException("The relevant employee must be logged in to perform this action");
        }
        loggedIn.giveConstraint(date, shift, reason);
    }

    public void updateConstraint (LocalDate date, int shift, String reason) throws Exception {
        if(isManager(loggedIn.getRole())){
            throw new EmployeeException("The method 'giveConstraint' was called from a user in a managerial position");
        }
        loggedIn.updateConstarint(date, shift, reason);
    }

    public void deleteConstraint (LocalDate date, int shift) throws Exception {
        if(isManager(loggedIn.getRole())){
            throw new EmployeeException("The method 'giveConstraint' was called from a user in a managerial position");
        }
        loggedIn.deletConstraint(date, shift);
    }

    public void addEmployee(Role role, int Id, TermsOfEmployment terms, LocalDate transactionDate, BankAccountInfo bank) throws EmployeeException {
        if(!isManager(loggedIn.getRole()))
            throw new EmployeeException("Only an administrator can perform this operation");
       Employee newEmployee = new Employee(role, Id, terms, transactionDate, bank);
       employees.put(Id, newEmployee);
    }

    public void removeEmployee(int Id) throws EmployeeException {
        if(!isManager(loggedIn.getRole())){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
                throw new EmployeeException("Employee not found");
        }
        employees.get(Id).setEmployed(false);
    }

    public void deleteBankAccount(int Id) throws EmployeeException {
        if(!isManager(loggedIn.getRole())){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
            throw new EmployeeException("Employee not found");
        }
        employees.get(Id).setBank(null);
    }

    public void updateBankAccount(int Id, int accountNum, int bankBranch, String bank) throws EmployeeException {
        if(!isManager(loggedIn.getRole())){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
            throw new EmployeeException("Employee not found");
        }
        BankAccountInfo toUpdate = employees.get(Id).getBank();
        toUpdate.setAccountNumber(accountNum);
        toUpdate.setBankBranch(bankBranch);
        toUpdate.setBank(bank);
    }

    public void updateTermsOfemployee(int Id, int salary, int educationFund, int sickDays, int daysOff) throws EmployeeException {
        if(!isManager(loggedIn.getRole())){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
            throw new EmployeeException("Employee not found");
        }
        TermsOfEmployment toUpdate = employees.get(Id).getTerms();
        toUpdate.setSalary(salary);
        toUpdate.setEducationFund(educationFund);
        toUpdate.setSickDays(sickDays);
        toUpdate.setDaysOff(daysOff);
    }

    public LinkedList<Integer> getRoleInDate(LocalDate date, Role roleName, int shift){
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
