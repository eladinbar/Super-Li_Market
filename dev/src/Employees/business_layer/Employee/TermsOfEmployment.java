package Employees.business_layer.Employee;

public class TermsOfEmployment {
    private int salary;
    private int educationFund;
    private int sickDays;
    private int daysOff;

    public TermsOfEmployment(int salary, int educationFund, int sickDays, int daysOff){
        this.salary = salary;
        this.educationFund = educationFund;
        this.sickDays = sickDays;
        this.daysOff = daysOff;
    }

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


    public void setSalary(int salary) {
        this.salary = salary;
    }
    public void setEducationFund(int educationFund) {
        this.educationFund = educationFund;
    }
    public void setDaysOff(int daysOff) {
        this.daysOff = daysOff;
    }

    public void setSickDays(int sickDays) {
        this.sickDays = sickDays;
    }
}
