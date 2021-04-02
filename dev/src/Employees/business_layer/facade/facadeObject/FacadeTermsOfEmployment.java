package Employees.business_layer.facade.facadeObject;

public class FacadeTermsOfEmployment implements FacadeObject{
    private int salary;
    private int educationFund;
    private int sickDays;
    private int daysOff;

    public FacadeTermsOfEmployment(int salary, int educationFund, int sickDays, int daysOff)
    {
        this.salary = salary;
        this.educationFund = educationFund;
        this.sickDays = sickDays;
        this.daysOff = daysOff;
    }
}
