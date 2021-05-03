import DAL.DalTruckingReport;
import DAL.DalTruckingReportController;
import Employees.presentation_layer.PresentationController;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        //PresentationController presentationController = new PresentationController ();
        //presentationController.start ();
        DalTruckingReport dtr=new DalTruckingReport(1, LocalTime.NOON,LocalDate.of(2022,5,5),
                "1","1",1,true,0);
        DalTruckingReportController d=new DalTruckingReportController();

        try {
            d.insert(dtr);
        }
        catch (SQLException se)
        {}
    }
}
