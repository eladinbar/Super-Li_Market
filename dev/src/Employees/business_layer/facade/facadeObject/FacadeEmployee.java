package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Employee.Constraint;
import Employees.business_layer.Employee.Employee;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class FacadeEmployee implements FacadeObject{
    private String role;
    private String ID;
    private LocalDate transactionDate;
    private FacadeBankAccountInfo facadeBankAccountInfo;
    private FacadeTermsOfEmployment facadeTermsOfEmployment;
    private HashMap<LocalDate,FacadeConstraint> constraints;
    private boolean isManager;

    //an existing employee with given constraints
    public FacadeEmployee(String role, String ID, LocalDate transactionDate, FacadeBankAccountInfo facadeBankAccountInfo, FacadeTermsOfEmployment facadeTermsOfEmployment, HashMap<LocalDate, FacadeConstraint> constraints)
    {
        this.role = role;
        this.ID = ID;
        this.transactionDate = transactionDate;
        this.facadeBankAccountInfo = facadeBankAccountInfo;
        this.facadeTermsOfEmployment = facadeTermsOfEmployment;
        this.constraints = constraints;
        if(role.equals ("humanResourcesManager") || role.equals ( "branchManager") || role.equals ( "branchManagerAssistant" ))
            isManager = true;
        else
            isManager = false;
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
        if(role.equals ("humanResourcesManager") || role.equals ( "branchManager") || role.equals ( "branchManagerAssistant" ))
            isManager = true;
        else
            isManager = false;
    }

    public FacadeEmployee(Employee employee){
        role = employee.getRole ().name ();
        ID = employee.getID ();
        transactionDate = employee.getTransactionDate ();
        facadeBankAccountInfo = new FacadeBankAccountInfo ( employee.getBank () );
        facadeTermsOfEmployment = new FacadeTermsOfEmployment ( employee.getTerms () );
        constraints = new HashMap<> (  );
        for( Map.Entry<LocalDate, Constraint> entry : employee.getConstraints ().entrySet () )
        {
            constraints.put ( entry.getKey (), new FacadeConstraint ( entry.getValue () ) );
        }
        if(role.equals ("humanResourcesManager") || role.equals ( "branchManager") || role.equals ( "branchManagerAssistant" ))
            isManager = true;
        else
            isManager = false;
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

    public boolean isManager() {
        return isManager;
    }
}
