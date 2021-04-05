package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Employee.TermsOfEmployment;

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

    public FacadeTermsOfEmployment(TermsOfEmployment termsOfEmployment)
    {
        salary = termsOfEmployment.getSalary();
        educationFund = termsOfEmployment.getEducationFund();
        sickDays = termsOfEmployment.getSickDays();
        daysOff = termsOfEmployment.getDaysOff();
    }

    public int getDaysOff() {
        return daysOff;
    }

    public int getEducationFund() {
        return educationFund;
    }

    public int getSalary() {
        return salary;
    }

    public int getSickDays() {
        return sickDays;
    }
}
