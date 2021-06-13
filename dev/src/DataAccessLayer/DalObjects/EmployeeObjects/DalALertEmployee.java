package DataAccessLayer.DalObjects.EmployeeObjects;

import BusinessLayer.EmployeePackage.EmployeePackage.Role;

public class DalALertEmployee {
    private int ID;
    private String role;
    private String alert;

    public DalALertEmployee( int ID, String  role, String alert){
        this.ID = ID;
        this.role = role;
        this.alert = alert;
    }

    public int getId() {
        return ID;
    }

    public String getRole() {return role;}

    public String getAlert() {
        return alert;
    }
}
