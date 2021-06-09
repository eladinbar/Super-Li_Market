package BusinessLayer.EmployeePackage.Facade.FacadeObject;

import BusinessLayer.EmployeePackage.EmployeePackage.Constraint;

import java.time.LocalDate;

public class FacadeConstraint implements FacadeObject{
    private LocalDate date;
    private boolean morningShift;
    private boolean eveningShift;
    private String reason;

    public FacadeConstraint(LocalDate date, boolean morningShift, boolean eveningShift, String reason)
    {
        this.date = date;
        this.morningShift = morningShift;
        this.eveningShift = eveningShift;
        this.reason = reason;
    }

    public FacadeConstraint(Constraint constraint) {
        date = constraint.getDate();
        morningShift = constraint.isMorningShift();
        eveningShift = constraint.isEveningShift();
        reason = constraint.getReason();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getReason() {
        return reason;
    }

    public boolean isMorningShift()
    {
        return morningShift;
    }

    public boolean isEveningShift(){
        return eveningShift;
    }
}

