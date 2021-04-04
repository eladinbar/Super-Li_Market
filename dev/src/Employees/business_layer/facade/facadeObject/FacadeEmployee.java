package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Employee.Constraint;
import Employees.business_layer.Employee.Employee;
import Employees.business_layer.Employee.Role;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FacadeEmployee implements FacadeObject{
    private Role role;
    private int ID;
    private LocalDate transactionDate;
    private FacadeBankAccountInfo facadeBankAccountInfo;
    private FacadeTermsOfEmployment facadeTermsOfEmployment;
    private HashMap<LocalDate,FacadeConstraint> constraints;

    //an existing employee with given constraints
    public FacadeEmployee(Role role, int ID, LocalDate transactionDate, FacadeBankAccountInfo facadeBankAccountInfo, FacadeTermsOfEmployment facadeTermsOfEmployment, HashMap<LocalDate, FacadeConstraint> constraints)
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

    public FacadeEmployee(Employee employee)
    {
        role = employee.getRole();
        ID = employee.getID();
        transactionDate = employee.getTransactionDate();
        facadeBankAccountInfo = new FacadeBankAccountInfo ( employee.getBankAccountInfo() );
        facadeTermsOfEmployment = new FacadeTermsOfEmployment ( employee.getTermsOfEmployment() );
        constraints = new HashMap<> (  );
        for( Map.Entry<LocalDate, Constraint> entry : employee.getConstraints )
        {
            constraints.put ( entry.getKey (), new FacadeConstraint ( entry.getValue () ) );
        }
    }
}
