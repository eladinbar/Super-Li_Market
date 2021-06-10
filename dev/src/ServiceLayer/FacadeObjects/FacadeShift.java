package ServiceLayer.FacadeObjects;

import BusinessLayer.EmployeePackage.EmployeePackage.Role;
import BusinessLayer.EmployeePackage.ShiftPackage.Shift;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FacadeShift implements FacadeObject {
    private LocalDate date;
    private HashMap<String, List<String>> manning;
    private String type;
    private int mORe;
    private boolean isMissing;

    //an existing shift with a given maning
    public FacadeShift(LocalDate date, HashMap<String, List<String>> manning, String type, int mORe, boolean isMissing)
    {
        this.date = date;
        this.manning = manning;
        this.type = type;
        this.mORe = mORe;
        this.isMissing = isMissing;
    }

    //a new shift without an existing manning
    public FacadeShift(LocalDate date, String type, int mORe)
    {
        this.date = date;
        this.manning = new HashMap<> ();
        this.type = type;
        this.mORe = mORe;
        isMissing = true;
    }

    public FacadeShift(Shift shift) {
        date = shift.getDate ();
        manning = new HashMap<> (  );
        for( Map.Entry <Role, List<String>> entry: shift.getManning ().entrySet ())
        {
            manning.put ( entry.getKey ().name (), entry.getValue ());
        }
        type = shift.getType ();
        mORe = shift.getmORe();
        isMissing = shift.isMissing ();
    }

    public LocalDate getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public HashMap<String, List<String>> getManning() {
        return manning;
    }

    public int getmORe() {
        return mORe;
    }

    public boolean isMissing(){
        return isMissing;
    }

    public void setManning(HashMap<String, List<String>> manning) {
        this.manning = manning;
    }
}
