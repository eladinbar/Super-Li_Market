import Employees.presentation_layer.PresentationController;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {
        PresentationController presentationController = new PresentationController();
        presentationController.start();
    }
}

