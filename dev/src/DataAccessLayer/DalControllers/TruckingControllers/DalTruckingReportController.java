package DataAccessLayer.DalControllers.TruckingControllers;


import DataAccessLayer.DalControllers.Employee_Trucking_DALController_Interface;
import DataAccessLayer.DalObjects.TruckingObjects.DalTruckingReport;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class DalTruckingReportController extends Employee_Trucking_DALController_Interface {
    private static DalTruckingReportController controller;

    private DalTruckingReportController(){
        super();
        this.tableName="TruckingReports";
        this.columnNames=new String[7];
        columnNames[0]="ID";columnNames[1]="date";columnNames[2]="leavingHour";
        columnNames[3]="truckNumber";columnNames[4]="driverID";
        columnNames[5]="completed";columnNames[6]="approved";

    }

    public static DalTruckingReportController getInstance() throws SQLException {
        if (controller==null) {
            controller = new DalTruckingReportController();
            controller.createTable();
        }
        return controller;
    }



    public boolean insert(DalTruckingReport truckingReport) throws SQLException {
        int completed=0;
        int approved=0;
        if (truckingReport.isCompleted())
            completed=1;
        if (truckingReport.isApproved())
            approved=1;

        Connection conn= DriverManager.getConnection(connection);
        String query="INSERT OR IGNORE INTO "+tableName+" VALUES "+"(?,?,?,?,?,?,?)";
        String year=truckingReport.getDate().toString();
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,truckingReport.getID());
            st.setString(2,year);
            st.setString(3,new Time(truckingReport.getLeavingHour().getHour(),truckingReport.getLeavingHour().getMinute(),
                    truckingReport.getLeavingHour().getSecond()).toString());
            st.setString(4,truckingReport.getTruckNumber());
            st.setString(5,truckingReport.getDriverID());
            st.setInt(6,completed);
            st.setInt(7,approved);
            st.executeUpdate();

        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }

        return true;


    }

    public boolean update(DalTruckingReport truckingReport) throws SQLException {
        int completed=0;
        int approved=0;
        if (truckingReport.isCompleted())
            completed=1;
        if (truckingReport.isApproved())
            approved=1;

        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName +" SET "+columnNames[1]+"=?,"+
                columnNames[2]+"=?,"+columnNames[3]+"=?,"+columnNames[4]+"=?,"+columnNames[5]+"=?,"+columnNames[6]+"=?"
                +"WHERE ("+columnNames[0]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,new Date(truckingReport.getDate().getYear(),truckingReport.getDate().getMonth().getValue()
                    ,truckingReport.getDate().getDayOfYear()).toString());
            st.setString(2,new Time(truckingReport.getLeavingHour().getHour(),truckingReport.getLeavingHour().getMinute(),
                    truckingReport.getLeavingHour().getSecond()).toString());
            st.setString(3,truckingReport.getTruckNumber());
            st.setString(4,truckingReport.getDriverID());
            st.setInt(5,completed);
            st.setInt(6,approved);
            st.setInt(7,truckingReport.getID());
            st.executeUpdate();


        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }
        return true;
    }

    public boolean delete(DalTruckingReport truckingReport) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+ " WHERE"+columnNames[0]+"=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,truckingReport.getID());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }
        return true;
    }
    public LinkedList<DalTruckingReport> load () throws SQLException// Select From DB
    {
        LinkedList<DalTruckingReport> reports=new LinkedList<>();
        Connection conn=DriverManager.getConnection(connection);
        String query="SELECT * FROM "+tableName;
        try {
            PreparedStatement st=conn.prepareStatement(query);

            ResultSet resultSet=st.executeQuery();
            while (resultSet.next())
            {
                LocalTime lt=LocalTime.parse(resultSet.getString(2));
                String s1=resultSet.getString(3);
                //DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate ld=LocalDate.parse(s1);
                boolean completed=resultSet.getString(6).equals("1");
                boolean approved=resultSet.getString(7).equals("1");
                reports.add(new DalTruckingReport(resultSet.getInt(1),lt,ld,resultSet.getString(4),
                        resultSet.getString(5),completed,approved));
            }
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return reports;
    }
    public boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS TruckingReports("
                +"ID INTEGER,"
                +"date TEXT,"
                +"leavingHour TEXT,"
                +"truckNumber TEXT,"
                +"driverID TEXT,"
                +"completed INTEGER,"
                +"approved INTEGER,"
                +"FOREIGN KEY (driverID) REFERENCES Drivers(ID) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"FOREIGN KEY (truckNumber) REFERENCES Trucks(licenseNumber) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"PRIMARY KEY (ID));";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.executeUpdate();
        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }
        return true;
    }


}
