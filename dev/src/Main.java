import Employees.presentation_layer.PresentationController;

import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        PresentationController presentationController = new PresentationController ();
        presentationController.start ();
    }
}
