package BusinessLayer.EmployeePackage;

import BusinessLayer.Notification;

public class EmployeeNotification extends Notification {

    int id;
    String role;

    public EmployeeNotification(int id, String role, String content ) {
        super ( content );
        this.id = id;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
    }
}
