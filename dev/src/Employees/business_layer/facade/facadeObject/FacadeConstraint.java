package Employees.business_layer.facade.facadeObject;

import Employees.business_layer.Employee.Constraint;

import java.time.LocalDate;
import java.util.Date;

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
        morningShift = constraint.getMorningShift();
        eveningShift = constraint.getEveningShift();
        reason = constraint.getReason();
    }
}

