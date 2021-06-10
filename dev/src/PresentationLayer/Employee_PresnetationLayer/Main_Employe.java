package PresentationLayer.Employee_PresnetationLayer;

import java.sql.SQLException;

public class Main_Employe {
    public Main_Employe() {
    }

    public static void main(String[] args) throws SQLException {
        PresentationController_Employee presentationController = new PresentationController_Employee ();
        presentationController.start();
    }
}

