package Employees.business_layer.facade.facadeObject;

import java.time.LocalDate;
import java.util.HashMap;

public class FacadeEmployee implements FacadeObject{
    private String role;
    private int ID;
    private LocalDate transactionDate;
    private FacadeBankAccountInfo facadeBankAccountInfo;
    private FacadeTermsOfEmployment facadeTermsOfEmployment;
    private HashMap<LocalDate,FacadeConstraint> constraints;
    private boolean isManager;

    //an existing employee with given constraints
    public FacadeEmployee(String role, int ID, LocalDate transactionDate, FacadeBankAccountInfo facadeBankAccountInfo, FacadeTermsOfEmployment facadeTermsOfEmployment, HashMap<LocalDate, FacadeConstraint> constraints)
    {
        this.role = role;
        this.ID = ID;
        this.transactionDate = transactionDate;
        this.facadeBankAccountInfo = facadeBankAccountInfo;
        this.facadeTermsOfEmployment = facadeTermsOfEmployment;
        this.constraints = constraints;
        if(role.equals ("humanResourcesManager") || role.equals ( "branchManager") || role.equals ( "branchManagerAssistent" ))
            isManager = true;
        else
            isManager = false;
    }

    //new employee with no constraints
    public FacadeEmployee(String role, int ID, LocalDate transactionDate, FacadeBankAccountInfo facadeBankAccountInfo, FacadeTermsOfEmployment facadeTermsOfEmployment)
    {
        this.role = role;
        this.ID = ID;
        this.transactionDate = transactionDate;
        this.facadeBankAccountInfo = facadeBankAccountInfo;
        this.facadeTermsOfEmployment = facadeTermsOfEmployment;
        this.constraints = new HashMap<>();
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public int getID() {
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
