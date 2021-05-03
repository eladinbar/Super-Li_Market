package DAL;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class DalTruckingReportController extends DalController {

    public DalTruckingReportController(){//TODO - Check when tables created
        super();
        this.tableName="TruckingReports";
        this.columnNames=new String[8];
        columnNames[0]="ID";columnNames[1]="leavingHour";columnNames[2]="date";
        columnNames[3]="truckNumber";columnNames[4]="driverID";columnNames[5]="origin";
        columnNames[6]="completed";columnNames[7]="replaceTRID";
    }

    public boolean insert(DalTruckingReport truckingReport) throws SQLException {
        //TODO - change URL
        boolean done=false;
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO ? VALUES ? ? ? ? ? ? ? ?";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setInt(2,truckingReport.getID());
            st.setString(3,new Time(truckingReport.getLeavingHour().getHour(),truckingReport.getLeavingHour().getMinute(),
                    truckingReport.getLeavingHour().getSecond()).toString());
            st.setString(4,new Date(truckingReport.getDate().getYear(),truckingReport.getDate().getMonth().getValue()
                    ,truckingReport.getDate().getDayOfYear()).toString());
            st.setString(5,truckingReport.getTruckNumber());
            st.setString(6,truckingReport.getDriverID());
            st.setInt(7,truckingReport.getOrigin());
            st.setString(8,truckingReport.isCompleted().toString());
            st.setInt(9,truckingReport.getReplaceTRID());

            System.out.println("executing insert");
            done=st.execute();

        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }

        return done;


    }

    public boolean update(DalTruckingReport truckingReport) throws SQLException {
        int res=-1;
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE ? SET ?=?,?=?,?=?,?=?,?=?,?=?,?=?  WHERE ?= ?";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);

            st.setString(2,columnNames[1]);
            st.setString(3,new Time(truckingReport.getLeavingHour().getHour(),truckingReport.getLeavingHour().getMinute(),
                    truckingReport.getLeavingHour().getSecond()).toString());

            st.setString(4,columnNames[2]);
            st.setString(5,new Date(truckingReport.getDate().getYear(),truckingReport.getDate().getMonth().getValue()
                    ,truckingReport.getDate().getDayOfYear()).toString());

            st.setString(6,columnNames[3]);
            st.setString(7,truckingReport.getTruckNumber());

            st.setString(8,columnNames[4]);
            st.setString(9,truckingReport.getDriverID());

            st.setString(10,columnNames[5]);
            st.setInt(11,truckingReport.getOrigin());

            st.setString(12,columnNames[6]);
            st.setString(13,truckingReport.isCompleted().toString());

            st.setString(14,columnNames[7]);
            st.setInt(15,truckingReport.getReplaceTRID());

            st.setString(16,columnNames[0]);
            st.setInt(17,truckingReport.getID());
            System.out.println("executing insert");

            done=st.execute();


        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }
        return done;
    }

    public boolean delete(DalTruckingReport truckingReport) throws SQLException {
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM ? WHERE ?=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setString(2,columnNames[0]);
            st.setInt(3,truckingReport.getID());
            done=st.execute();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return done;
    }
    public LinkedList<DalTruckingReport> load () throws SQLException// Select From DB
    {
        LinkedList<DalTruckingReport> reports=new LinkedList<>();
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="SELECT * FROM ?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            ResultSet resultSet=st.executeQuery(query);
            while (resultSet.next())
            {
                LocalTime lt=LocalTime.parse(resultSet.getString(2));
                LocalDate ld=LocalDate.parse(resultSet.getString(3));
                boolean completed=resultSet.getString(7).equals("true");
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
