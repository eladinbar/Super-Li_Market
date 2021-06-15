package PresentationLayer;

import java.sql.SQLException;

public class Main {
    public Main() {
    }

    public static void main(String[] args) throws SQLException {
        EmployeePresentationController.getInstance().start();
    }
}
