package Employees.business_layer.facade.facadeObject;

import java.util.Date;

public class FacadeConstraint implements FacadeObject{
    private Date date;
    private boolean morningShift;
    private boolean eveningShift;
    private String reason;

    public FacadeConstraint(Date date, boolean morningShift, boolean eveningShift, String reason)
    {
        this.date = date;
        this.morningShift = morningShift;
        this.eveningShift = eveningShift;
        this.reason = reason;
    }
}

