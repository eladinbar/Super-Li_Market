package DAL.DalObjects_Employees;

import java.time.LocalDate;

public class DalConstraint {
    private  String EmployeeId;
    private String reason;
    private LocalDate date;
    private int shift;


    public DalConstraint(String EmployeeId, String reason, LocalDate date, int shift){
        this.EmployeeId = EmployeeId;
        this.reason = reason;
        this.date = date;
        this.shift = shift;
    }

    // getters

    public String getEmployeeId() {
        return EmployeeId;
    }

    public String getReason() {
        return reason;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getShift() {
        return shift;
    }

// setters
    public void setEmployeeId(String employeeId) { EmployeeId = employeeId; }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}
