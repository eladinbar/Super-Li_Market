package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Employee.Role;

import java.util.Date;
import java.util.HashMap;

public class FacadeEmployee implements FacadeObject{
    private Role role;
    private int ID;
    private Date transactionDate;
    private FacadeBankAccountInfo facadeBankAccountInfo;
    private FacadeTermsOfEmployment facadeTermsOfEmployment;
    private HashMap<Date,FacadeConstraint> constraints;

    //an existing employee with given constraints
    public FacadeEmployee(Role role, int ID, Date transactionDate, FacadeBankAccountInfo facadeBankAccountInfo, FacadeTermsOfEmployment facadeTermsOfEmployment, HashMap<Date, FacadeConstraint> constraints)
    {
        this.role = role;
        this.ID = ID;
        this.transactionDate = transactionDate;
        this.facadeBankAccountInfo = facadeBankAccountInfo;
        this.facadeTermsOfEmployment = facadeTermsOfEmployment;
        this.constraints = constraints;
    }

    //new employee with no constraints
    public FacadeEmployee(Role role, int ID, Date transactionDate, FacadeBankAccountInfo facadeBankAccountInfo, FacadeTermsOfEmployment facadeTermsOfEmployment)
    {
        this.role = role;
        this.ID = ID;
        this.transactionDate = transactionDate;
        this.facadeBankAccountInfo = facadeBankAccountInfo;
        this.facadeTermsOfEmployment = facadeTermsOfEmployment;
        this.constraints = new HashMap<>();
    }
}
