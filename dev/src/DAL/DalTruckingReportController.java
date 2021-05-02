package DAL;


import java.sql.*;

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
        Connection conn= DriverManager.getConnection("need to fix");
        String query=
        "INSERT INTO @tableName VALUES @ID @leavingHour @date" +
                " @truckNumber @driverID @origin @completed @replaceTRID";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setTime(2,new Time(truckingReport.getLeavingHour().getHour(),truckingReport.getLeavingHour().getMinute(),truckingReport.getLeavingHour().getSecond()));
            st.setInt(3,truckingReport.getID());
            st.setDate(4,new Date(truckingReport.getDate().getYear(),truckingReport.getDate().getMonth().getValue()
                    ,truckingReport.getDate().getDayOfYear()));
            st.setString(5,truckingReport.getTruckNumber());
            st.setString(6,truckingReport.getDriverID());
            st.setInt(7,truckingReport.getOrigin());
            st.setBoolean(8,truckingReport.isCompleted());
            st.setInt(9,truckingReport.getReplaceTRID());

            st.execute();

        }
        catch (SQLException e){
        }
        finally {
            conn.close();
        }


        return false;
    }

    public boolean update(DalTruckingReport truckingReport) {
        //TODO - implement
        return false;
    }

    public boolean delete(DalTruckingReport truckingReport) {
        //TODO - implement
        return false;
    }
    public DalTruckingReport load ()// Select From DB
    {
        //TODO- implement
        return null;
    }
}
