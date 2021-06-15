package ServiceLayer.FacadeObjects;

import BusinessLayer.EmployeePackage.EmployeePackage.Constraint;
import BusinessLayer.EmployeePackage.EmployeePackage.Employee;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FacadeEmployee implements FacadeObject {
    private String role;
    private String ID;
    private LocalDate transactionDate;
    private FacadeBankAccountInfo facadeBankAccountInfo;
    private FacadeTermsOfEmployment facadeTermsOfEmployment;
    private HashMap<LocalDate,FacadeConstraint> constraints;

    //an existing employee with given constraints
    public FacadeEmployee(String role, String ID, LocalDate transactionDate, FacadeBankAccountInfo facadeBankAccountInfo, FacadeTermsOfEmployment facadeTermsOfEmployment, HashMap<LocalDate, FacadeConstraint> constraints)
    {
        this.role = role;
        this.ID = ID;
        this.transactionDate = transactionDate;
        this.facadeBankAccountInfo = facadeBankAccountInfo;
        this.facadeTermsOfEmployment = facadeTermsOfEmployment;
        this.constraints = constraints;
    }

    //new employee with no constraints
    public FacadeEmployee(String role, String ID, LocalDate transactionDate, FacadeBankAccountInfo facadeBankAccountInfo, FacadeTermsOfEmployment facadeTermsOfEmployment)
    {
        this.role = role;
        this.ID = ID;
        this.transactionDate = transactionDate;
        this.facadeBankAccountInfo = facadeBankAccountInfo;
        this.facadeTermsOfEmployment = facadeTermsOfEmployment;
        this.constraints = new HashMap<>();
    }

    public FacadeEmployee(Employee employee){
        role = employee.getRole ().name ();
        ID = employee.getID ();
        transactionDate = employee.getTransactionDate ();
        facadeBankAccountInfo = new FacadeBankAccountInfo ( employee.getBank () );
        facadeTermsOfEmployment = new FacadeTermsOfEmployment ( employee.getTerms () );
        constraints = new HashMap<> (  );
        for( Map.Entry<LocalDate, Constraint> entry : employee.getConstraints ().entrySet ()) {
            constraints.put ( entry.getKey (), new FacadeConstraint ( entry.getValue () ) );
        }
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String getID() {
        return ID;
    }

    public String getRole() {
         return role;
    }

    public FacadeBankAccountInfo getFacadeBankAccountInfo() {
        return facadeBankAccountInfo;
    }

    public FacadeTermsOfEmployment getFacadeTermsOfEmployment() {
        return facadeTermsOfEmployment;
    }

    public HashMap<LocalDate, FacadeConstraint> getConstraints(){
        return constraints;
    }
}
