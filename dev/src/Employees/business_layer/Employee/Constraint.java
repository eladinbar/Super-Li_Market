package Employees.business_layer.Employee;
import java.time.LocalDate;


public class Constraint {
    private LocalDate date;
    private boolean morningShift;
    private boolean eveningShift;
    private String reason;

    public Constraint(LocalDate date, boolean morningShift, boolean evningShift, String reason){
        this.date = date;
        this.morningShift = morningShift;
        this.eveningShift = evningShift;
        this.reason = reason;
    }
// Getters:
    public LocalDate getDate(){
        return date;
    }


    public boolean isMorningShift() {
        return morningShift;
    }

    public boolean isEveningShift() {
        return eveningShift;
    }

    public String getReason() {
        return reason;
    }
// Setters:
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setMorningShift(boolean morningShift) {
        this.morningShift = morningShift;
    }

    public void setEveningShift(boolean eveningShift) {
        this.eveningShift = eveningShift;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
