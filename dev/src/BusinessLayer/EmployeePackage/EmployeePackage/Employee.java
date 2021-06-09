package BusinessLayer.EmployeePackage.EmployeePackage;
import DataAccessLayer.DalControllers.EmployeeControllers.DalConstraintController;
import DataAccessLayer.DalObjects.EmployeeObjects.DalConstraint;
import BusinessLayer.EmployeePackage.EmployeeException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Employee {
    private Role role;
    private String ID;
    private boolean isManager;
    private boolean employed;
    private  TermsOfEmployment terms;
    private LocalDate transactionDate;
    private BankAccountInfo bank;
    private HashMap<LocalDate, Constraint> constraints;

    public Employee (String role, String ID, TermsOfEmployment terms, LocalDate transactionDate,  BankAccountInfo bank ) {
        this.role = Role.valueOf (role);
        this.ID = ID;
        this.isManager = isManager(this.role);
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

    public HashMap<LocalDate, Constraint> getConstraints(){
        return constraints;
    }

//Setters:

    public void setRole(Role role) {
        this.role = role;
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
    public void giveConstraint(LocalDate date, int shift, String reason) throws EmployeeException, SQLException {
        if(!validDate(date)){
            throw new EmployeeException("A constraint can be filed up to two weeks in advance");
        }
        if (constraints.containsKey(date)){ // if the employment already has a constraint on one of the shifts that day.
            Constraint exist = constraints.get(date);
            if( shift == 0 || shift == 2){// morning shift
                if(exist.isMorningShift()){
                    throw new EmployeeException ("You already have a constraint on this shift, if you want to update it please delete and enter a new one");
                }
                exist.setMorningShift(true);
            }
            else if (shift ==1 || shift == 2){// evening shift
                if(exist.isEveningShift()){
                    throw new EmployeeException("You already have a constraint on this shift, if you want to update it please delete and enter a new one");
                }
                exist.setEveningShift(true);
            }
            exist.setReason(exist.getReason()+"\n"+reason);
            DalConstraintController.getInstance ().update ( new DalConstraint ( ID, reason, date, shift ) );
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
            DalConstraintController.getInstance ().insert ( new DalConstraint ( ID, reason, date, shift )  );
        }

        constraints = sortConstraintsByDate(constraints);
    }


public void deleteConstraint(LocalDate date, int shift) throws EmployeeException, SQLException {
    if (!constraints.containsKey(date) || (!constraints.get ( date ).isEveningShift () && shift == 1) || (!constraints.get ( date ).isMorningShift () && shift == 0) || (shift == 2 && (!constraints.get ( date ).isMorningShift () || !constraints.get ( date ).isEveningShift ()))){
        throw new EmployeeException("There is no constraint to delete on that day and shift");
    }
    Constraint exist = constraints.get(date);
    if(shift == 0) {
        if (exist.isEveningShift ( )) {
            exist.setMorningShift ( false );
            DalConstraintController.getInstance ().update ( new DalConstraint ( ID, exist.getReason ( ), exist.getDate ( ), 1 ) );
        } else {
            constraints.remove ( date );
            DalConstraintController.getInstance ( ).delete ( new DalConstraint ( ID, exist.getReason ( ), exist.getDate ( ), 0 ) );
        }
    }
    else if(shift == 1) {
        if (exist.isMorningShift ()) {
            exist.setEveningShift ( false );
            DalConstraintController.getInstance ( ).update ( new DalConstraint ( ID, exist.getReason ( ), exist.getDate ( ), 0 ) );
        } else {
            constraints.remove ( date );
            DalConstraintController.getInstance ( ).delete ( new DalConstraint ( ID, exist.getReason ( ), exist.getDate ( ), 1 ) );
        }
    }
    else if(shift == 2) {
        constraints.remove ( date );
        DalConstraintController.getInstance ( ).delete ( new DalConstraint ( ID, exist.getReason ( ), exist.getDate ( ), 2 ) );
    }
    else
        throw new EmployeeException("There is no constraint to the specific shift to delete on that day");
    if(!exist.isMorningShift() & !exist.isEveningShift()){
        constraints.remove(date);
    }
}

//private methods

    private boolean isManager(Role role){
        return role == Role.branchManager | role == Role.branchManagerAssistant | role == Role.humanResourcesManager;
    }

    private boolean validDate(LocalDate date) {
        return (LocalDate.now().isBefore(date.minusWeeks(2)));
    }

    private HashMap<LocalDate, Constraint> sortConstraintsByDate(HashMap<LocalDate, Constraint> constraints) {
        return constraints.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (a,b)->{ throw new AssertionError();},
                LinkedHashMap::new
        ));
//    return constraints;
    }
}
