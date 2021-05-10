package DAL.DalObjects_Employees;

import java.time.LocalDate;

public class DalShift {
    private String EmployeeId;
    private String type;
    private LocalDate date;
    private int shift;
    private String role;

    public DalShift(String EmployeeId, String type, LocalDate date, int shift, String role){
        this.EmployeeId = EmployeeId;
        this.type = type;
        this.date = date;
        this.shift = shift;
        this.role = role;
    }

    // getters
    public String getEmployeeId() { return EmployeeId;}

    public String getType() {
        return type;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getShift() {
        return shift;
    }

    public String getRole() {
        return role;
    }

    // setters
    public void setEmployeeId(String employeeId) { EmployeeId = employeeId;}

    public void setType(String type) {
        this.type = type;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
