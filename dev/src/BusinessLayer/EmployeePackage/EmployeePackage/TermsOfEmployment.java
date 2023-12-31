package BusinessLayer.EmployeePackage.EmployeePackage;

import BusinessLayer.EmployeePackage.EmployeeException;

public class TermsOfEmployment {
    private int salary;
    private int educationFund;
    private int sickDays;
    private int daysOff;

    public TermsOfEmployment(int salary, int educationFund, int sickDays, int daysOff) {
        this.salary = salary;
        this.educationFund = educationFund;
        this.sickDays = sickDays;
        this.daysOff = daysOff;
    }

    //Getters
    public int getSalary() {
        return salary;
    }

    public int getEducationFund() {
        return educationFund;
    }

    public int getSickDays() {
        return sickDays;
    }

    public int getDaysOff() {
        return daysOff;
    }

    //Setters
    public void setSalary(int salary) throws EmployeeException {
        if(salary<0){throw new EmployeeException("Invalid salary entered");}
        this.salary = salary;
    }

    public void setEducationFund(int educationFund) throws EmployeeException {
        if(educationFund<0){throw new EmployeeException("Invalid education fund entered");}
        this.educationFund = educationFund;
    }

    public void setDaysOff(int daysOff) throws EmployeeException {
        if(daysOff<0){throw new EmployeeException("Invalid days off entered");}
        this.daysOff = daysOff;
    }

    public void setSickDays(int sickDays) throws EmployeeException {
        if(sickDays<0){throw new EmployeeException("Invalid sick days entered");}
        this.sickDays = sickDays;
    }
}
