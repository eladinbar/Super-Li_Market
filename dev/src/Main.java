import DAL.*;
import Employees.presentation_layer.PresentationController;
import Trucking.Business_Layer_Trucking.Resources.Truck;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.LinkedList;
import java.util.Locale;


import Employees.presentation_layer.PresentationController;

public class Main {
    public static void main(String[] args) throws SQLException {
        PresentationController presentationController = new PresentationController();
        presentationController.start();
    }
}

