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
import Trucking.Presentation_Layer_Trucking.Menu_Printer;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
//        PresentationController presentationController = new PresentationController();
//        presentationController.start();
        Menu_Printer.getInstance().mainMenu();
    }
}

