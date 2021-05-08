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

    public static DalTruckingReportController getInstance() {
        if (controller==null)
        controller= new DalTruckingReportController();
        return controller;
    }

    public boolean insert(DalTruckingReport truckingReport) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        int completed=0;
        if (truckingReport.isCompleted())
            completed=1;
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        //String query= "INSERT INTO "+tableName+"("+columnNames[0]+columnNames[1]+columnNames[2]+columnNames[3]
        //        +columnNames[4]+columnNames[5]+columnNames[6]+columnNames[7]+") VALUES (? ? ? ? ? ? ? ?)";
        String query="INSERT INTO "+tableName+" VALUES "+"(?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,truckingReport.getID());
            st.setString(2,new Time(truckingReport.getLeavingHour().getHour(),truckingReport.getLeavingHour().getMinute(),
                    truckingReport.getLeavingHour().getSecond()).toString());
            st.setString(3,new Date(truckingReport.getDate().getYear(),truckingReport.getDate().getMonth().getValue()
                    ,truckingReport.getDate().getDayOfYear()).toString());
            st.setString(4,truckingReport.getTruckNumber());
            st.setString(5,truckingReport.getDriverID());
            st.setInt(6,truckingReport.getOrigin());
            st.setInt(7,completed);
            st.setInt(8,truckingReport.getReplaceTRID());

            System.out.println("executing insert");
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
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
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
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
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
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
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

}
