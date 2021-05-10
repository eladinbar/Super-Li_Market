package DAL;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class DalTruckingReportController extends DalController {
    private static DalTruckingReportController controller;

    private DalTruckingReportController(){
        super();
        this.tableName="TruckingReports";
        this.columnNames=new String[8];
        columnNames[0]="ID";columnNames[1]="leavingHour";columnNames[2]="date";
        columnNames[3]="truckNumber";columnNames[4]="driverID";columnNames[5]="origin";
        columnNames[6]="completed";columnNames[7]="replaceTRID";

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
        if (truckingReport.isCompleted())
            completed=1;
        Connection conn= DriverManager.getConnection(connection);
        String query="INSERT INTO "+tableName+" VALUES "+"(?,?,?,?,?,?,?,?)";
        String year=truckingReport.getDate().toString();
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,truckingReport.getID());
            st.setString(2,new Time(truckingReport.getLeavingHour().getHour(),truckingReport.getLeavingHour().getMinute(),
                    truckingReport.getLeavingHour().getSecond()).toString());
            st.setString(3,year);
            st.setString(4,truckingReport.getTruckNumber());
            st.setString(5,truckingReport.getDriverID());
            st.setInt(6,truckingReport.getOrigin());
            st.setInt(7,completed);
            st.setInt(8,truckingReport.getReplaceTRID());
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
        if (truckingReport.isCompleted())
            completed=1;
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName +" SET "+columnNames[1]+"=?,"+
                columnNames[2]+"=?,"+columnNames[3]+"=?,"+columnNames[4]+"=?,"+columnNames[5]+"=?,"+columnNames[6]+"=?,"+
                columnNames[7]+"=?  WHERE ("+columnNames[0]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,new Time(truckingReport.getLeavingHour().getHour(),truckingReport.getLeavingHour().getMinute(),
                    truckingReport.getLeavingHour().getSecond()).toString());
            st.setString(2,new Date(truckingReport.getDate().getYear(),truckingReport.getDate().getMonth().getValue()
                    ,truckingReport.getDate().getDayOfYear()).toString());
            st.setString(3,truckingReport.getTruckNumber());
            st.setString(4,truckingReport.getDriverID());
            st.setInt(5,truckingReport.getOrigin());
            st.setInt(6,completed);
            st.setInt(7,truckingReport.getReplaceTRID());
            st.setInt(8,truckingReport.getID());
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
                String s=resultSet.getString(2);
                LocalTime lt=LocalTime.parse(resultSet.getString(2));
                String s1=resultSet.getString(3);
                DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate ld=LocalDate.parse(s1,formatter);
                boolean completed=resultSet.getString(7).equals("1");
                reports.add(new DalTruckingReport(resultSet.getInt(1),lt,ld,resultSet.getString(4),
                        resultSet.getString(5),resultSet.getInt(6),completed,resultSet.getInt(7)));
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
                +"leavingHour TEXT,"
                +"date TEXT,"
                +"truckNumber TEXT,"
                +"driverID TEXT,"
                +"origin INTEGER,"
                +"completed INTEGER,"
                +"replaceTRID INTEGER,"
                +"FOREIGN KEY (driverID) REFERENCES Drivers(ID) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"FOREIGN KEY (truckNumber) REFERENCES Trucks(licenseNumber) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"FOREIGN KEY (origin) REFERENCES Sites (siteID) ON DELETE NO ACTION ON UPDATE CASCADE,"
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
