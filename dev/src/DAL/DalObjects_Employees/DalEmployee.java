package DAL.DalObjects_Employees;

import java.time.LocalDate;


public class DalEmployee {
    private String Id;
    private String role;
    private LocalDate transactionDate;
    private int daysOff;
    private int salary;
    private int sickDays;
    private int educationFund;
    private boolean employed;

    public DalEmployee( String Id, String role, LocalDate transactionDate, int daysOff, int salary, int sickDays, int educationFund, boolean employed){
        this.Id = Id;
        this.role = role;
        this.transactionDate = transactionDate;
        this.daysOff = daysOff;
        this.salary = salary;
        this.sickDays = sickDays;
        this.educationFund = educationFund;
        this.employed = employed;
    }

    // getters


    public String getId() {
        return Id;
    }

    public String getRole() {
        return role;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public int getDaysOff() {
        return daysOff;
    }

    public int getSalary() {
        return salary;
    }

    public int getSickDays() {
        return sickDays;
    }

    public int getEducationFund() {
        return educationFund;
    }

    public boolean getEmployed() {
        return employed;
    }
    //setters

    public void setId(String id) {
        Id = id;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public void setDaysOff(int daysOff) {
        this.daysOff = daysOff;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setEducationFund(int educationFund) {
        this.educationFund = educationFund;
    }

    public void setSickDays(int sickDays) {
        this.sickDays = sickDays;
    }

    public void setEmployed(boolean employed) {
        this.employed = employed;
    }
}
