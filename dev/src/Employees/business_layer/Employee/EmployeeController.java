package Employees.business_layer.Employee;

import Employees.EmployeeException;
import Employees.business_layer.facade.facadeObject.FacadeEmployee;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class EmployeeController {
    private HashMap<String, Employee> employees;
    private Employee loggedIn;

    public EmployeeController(){
        this.loggedIn = null;
        employees = new HashMap<>();
    }

    public Employee getLoggedIn(){ return loggedIn;}

    public Employee login(String ID, String role) throws EmployeeException {
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
            if (toLogin.getRole() != Role.valueOf(role)) {
                throw new EmployeeException("Id does not match to the role");
            }
        loggedIn = toLogin;
        return employees.get(ID);
    }

    public Employee logout() throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is loggedIn");
        }
        String id = loggedIn.getID();
        loggedIn= null;
        return employees.get(id);
    }

    public Employee getEmployee(String Id) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }

        if(!loggedIn.getIsManager()) {
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)) {
            throw new EmployeeException("Employee not found");
        }
        return employees.get(Id);
    }

    public void giveConstraint(LocalDate date, int shift, String reason) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }

        if(!loggedIn.getIsManager()){
            throw new EmployeeException("The method 'giveConstraint' was called from a user in a managerial position");
        }

        if(!loggedIn.isEmployed()) {
            throw new EmployeeException("The employee is not employed ");
        }
        if(!vaildDate(date)){
            throw new EmployeeException("A constraint can be filed up to two weeks in advance");
        }
        loggedIn.giveConstraint(date, shift, reason);
    }


    public void updateConstraint (LocalDate date, int shift, String reason) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }

        if(loggedIn.getIsManager()){
            throw new EmployeeException("The method 'giveConstraint' was called from a user in a managerial position");
        }
        if(!loggedIn.isEmployed()){
            throw new EmployeeException("The employee is not employed ");
        }

        loggedIn.updateConstarint(date, shift, reason);
    }

    public void deleteConstraint (LocalDate date, int shift) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }

        if(loggedIn.getIsManager()){
            throw new EmployeeException("The method 'giveConstraint' was called from a user in a managerial position");
        }
        if(!loggedIn.isEmployed()){
            throw new EmployeeException("The employee is not employed ");
        }

        loggedIn.deleteConstraint(date, shift);
    }

    public Employee addEmployee(FacadeEmployee e) throws EmployeeException {

        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }

        if(!loggedIn.getIsManager()) {
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!validId(e.getID())){
            throw new EmployeeException("An invalid ID was entered ");
        }
       TermsOfEmployment terms = new TermsOfEmployment(e.getFacadeTermsOfEmployment().getSalary(), e.getFacadeTermsOfEmployment().getEducationFund(), e.getFacadeTermsOfEmployment().getSickDays(), e.getFacadeTermsOfEmployment().getDaysOff());
       BankAccountInfo bank = new BankAccountInfo(e.getFacadeBankAccountInfo().getAccountNumber(), e.getFacadeBankAccountInfo().getBankBranch(), e.getFacadeBankAccountInfo().getBank());
       Employee newEmployee = new Employee(e.getRole(), e.getID(),terms, e.getTransactionDate(), bank);
       employees.put(e.getID(), newEmployee);
       return newEmployee;
    }



    public Employee addManager(FacadeEmployee e) throws EmployeeException {
        if(!loggedIn.getIsManager()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!validId(e.getID())){
            throw new EmployeeException("An invalid ID was entered ");
        }
        TermsOfEmployment terms = new TermsOfEmployment(e.getFacadeTermsOfEmployment().getSalary(), e.getFacadeTermsOfEmployment().getEducationFund(), e.getFacadeTermsOfEmployment().getSickDays(), e.getFacadeTermsOfEmployment().getDaysOff());
        BankAccountInfo bank = new BankAccountInfo(e.getFacadeBankAccountInfo().getAccountNumber(), e.getFacadeBankAccountInfo().getBankBranch(), e.getFacadeBankAccountInfo().getBank());
        Employee newEmployee = new Employee(e.getRole(), e.getID(),terms, e.getTransactionDate(), bank);
        employees.put(e.getID(), newEmployee);
        return newEmployee;
    }

    public Employee removeEmployee(String Id) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }

        if(!loggedIn.getIsManager()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }

        if(!employees.containsKey(Id)){
                throw new EmployeeException("Employee not found");
        }
        employees.get(Id).setEmployed(false);
        return employees.get(Id);
    }

    public void deleteBankAccount(int Id) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }

        if(!loggedIn.getIsManager()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(!employees.containsKey(Id)){
            throw new EmployeeException("Employee not found");
        }
        employees.get(Id).setBank(null);
    }

    public void updateBankAccount(String Id, int accountNum, int bankBranch, String bank) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }

        if(!loggedIn.getIsManager()){
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

    public void updateTermsOfemployee(String Id, int salary, int educationFund, int sickDays, int daysOff) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }

        if(!loggedIn.getIsManager()){
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

    public LinkedList<String> getRoleInDate(LocalDate date, Role roleName, int shift) throws EmployeeException {
        if(loggedIn==null){
            throw new EmployeeException("No user is login");
        }
        if(!loggedIn.getIsManager()){
            throw new EmployeeException("Only an administrator can perform this operation");
        }
        if(employees.isEmpty()){
            throw new EmployeeException("The list of employees is empty");
        }
        LinkedList<String> specificRole = new LinkedList<>();
        for (Map.Entry<String, Employee> entry : employees.entrySet()) {
            Employee employee = entry.getValue();
            if(employee.getRole()== roleName){
                if(employee.getConstraints().containsKey(date)) {//Checks if the employee has a constraint on this day
                  if(shift==0) {//Checks on morning shift
                      if (!employee.getConstraints().get(date).isMorningShift())// If the employee is free on this shift
                          specificRole.add(employee.getID());
                  }
                  if(shift==1) {//Checks on evening shift
                      if (!employee.getConstraints().get(date).isEveningShift())// If the employee is free on this shift
                          specificRole.add(employee.getID());
                    }
                  if (shift == 2) {

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
    private BankAccountInfo createAccount(int accountNum, int bankBranch, String bank) throws EmployeeException {
        if(accountNum<0 | bankBranch<0 ){
            throw new EmployeeException("One of the details entered is negative ");
        }
        return new BankAccountInfo(accountNum, bankBranch, bank);
    }

    private TermsOfEmployment creatTermsOfEmployment( int salary, int educationFund, int sickDays, int daysOff) throws EmployeeException {
        if(salary<0 | educationFund<0 | sickDays<0 | daysOff<0){
            throw new EmployeeException("One of the details entered is negative ");
        }
        return new TermsOfEmployment(salary, educationFund, sickDays, daysOff);
    }

    private boolean validId(String ID){
        char[] idChar = ID.toCharArray();
        boolean firstHalf = false;
        boolean secHalf = false;
        for (int i = 0; i < 5; ++i){//Check first half
            if ((idChar[i] > 47 && idChar[i] < 58)){//Checks ascii vals to see if valid ID
                firstHalf = true;
            }
        }

        for (int i = 5; i < idChar.length; ++i){//Check second half
            if ((idChar[i] > 47 && idChar[i] < 58)){//Checks ascii vals to see if valid ID
                secHalf = true;
            }
        }

        //If all values are valid, returns true.
        if (firstHalf == true && secHalf == true && idChar[4] == '-' && ID.length() == 9){
            return true;
        }

        return false;
    }

    private boolean vaildDate(LocalDate date) {
        return (LocalDate.now().isBefore(date.minusWeeks(2)));
    }
}
