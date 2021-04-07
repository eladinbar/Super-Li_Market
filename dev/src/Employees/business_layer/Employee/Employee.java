package Employees.business_layer.Employee;
import Employees.EmployeeException;

import java.time.LocalDate;
import java.util.HashMap;

public class Employee {
    private Role role;
    private String ID;
    private boolean isManager;
    private boolean employed;
    private  TermsOfEmployment terms;
    private LocalDate transactionDate;
    private BankAccountInfo bank;
    private HashMap<LocalDate, Constraint> constraints;

    public Employee (Role role, String ID, TermsOfEmployment terms, LocalDate transactionDate,  BankAccountInfo bank ){
        this.role = role;
        this.ID = ID;
        this.isManager = isManager(role);
        employed = true;
        this.terms = terms;
        this.transactionDate = transactionDate;
        this.bank = bank;
        constraints = new HashMap<>();

    }
// Getters:
    public Role getRole() {
        return role;
    }

    public String getID() {
        return ID;
    }

    public boolean getIsManager() { return isManager; }

    public boolean isEmployed() {
        return employed;
    }

    public TermsOfEmployment getTerms() {
        return terms;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public BankAccountInfo getBank() {
        return bank;
    }

    public HashMap<LocalDate, Constraint> getConstraints() {
        return constraints;
    }

//Setters:

    public void setRole(Role role) {
        this.role = role;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }

    public void setTerms(TermsOfEmployment terms) {
        this.terms = terms;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setBank(BankAccountInfo bank) {
        this.bank = bank;
    }


// functions:
    public void giveConstraint(LocalDate date, int shift, String reason) throws EmployeeException {
        if (constraints.containsKey(date)){ // if the employment already has a constraint on one of the shifts that day.
            Constraint exist = constraints.get(date);
            if( shift == 1){// morning shift
                if(exist.isMorningShift()){
                    throw new EmployeeException ("You already have a constraint on this shift");
                }
                exist.setMorningShift(true);
            }
            else if (shift ==2){// evening shift
                if(exist.isEveningShift()){
                    throw new EmployeeException("You already have a constraint on this shift");
                }
                exist.setEveningShift(true);
            }
            exist.setReason(exist.getReason()+"\n"+reason);// הסיבה החדשה דורסת את הקיימת? תלוי במימוש
        }

        else{ // if the employment has no constraint on that day.
            Constraint newConstraint;
            if(shift == 0){ // morning shift
                newConstraint = new Constraint(date, true, false, reason );
            }
            else if( shift == 1){
                newConstraint = new Constraint(date, false, true, reason );
            }
            else{
                newConstraint = new Constraint(date, true, true, reason );
            }
            constraints.put(date, newConstraint);
        }
    }


public void deleteConstraint(LocalDate date, int shift) throws EmployeeException {
    if (!constraints.containsKey(date)){
        throw new EmployeeException("There is no constraint to delete on that day");
    }
    Constraint exist = constraints.get(date);
    if(!(exist.isMorningShift() & exist.isEveningShift())){
        constraints.remove(date);
    }
    else if(exist.isMorningShift()){
        exist.setMorningShift(false);
    }
    else{
        exist.setEveningShift(false);
    }
}

//private methods

    private boolean isManager(Role role){
        return role == Role.branchManager | role == Role.branchManagerAssistent | role == Role.humanResourcesManager;
    }
}
