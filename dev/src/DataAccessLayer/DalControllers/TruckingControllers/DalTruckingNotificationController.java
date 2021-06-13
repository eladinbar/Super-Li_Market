package DataAccessLayer.DalControllers.TruckingControllers;

import DataAccessLayer.DalControllers.Employee_Trucking_DALController_Interface;
import DataAccessLayer.DalObjects.TruckingObjects.DalTruckingNotification;

import java.sql.*;
import java.util.LinkedList;

public class DalTruckingNotificationController extends Employee_Trucking_DALController_Interface {
    private static DalTruckingNotificationController controller;

    private DalTruckingNotificationController(){
        super();
        this.tableName="TruckingNotifications";
        this.columnNames=new String[2];
        columnNames[1]="Content";columnNames[0]="ID";
    }

    public static DalTruckingNotificationController getInstance() throws SQLException {
        if (controller==null) {controller=new DalTruckingNotificationController();
            controller.createTable();
        }
        return controller;
    }


    public boolean insert(DalTruckingNotification notification) throws SQLException {

        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT OR IGNORE INTO "+tableName+" VALUES (?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,notification.getID());
            st.setString(2,notification.getContent());


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

    public boolean update(DalTruckingNotification notification) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?"+" WHERE ("+columnNames[0]+"= ?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,notification.getContent());
            st.setInt(2,notification.getID());
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

    public boolean deleteAll() throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM " +tableName;
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalTruckingNotification> load () throws SQLException// Select From DB
    {
        LinkedList<DalTruckingNotification> notifications = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                notifications.add(new DalTruckingNotification(resultSet.getInt(1),resultSet.getString(2)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return notifications;
    }
    public boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS TruckingNotifications("
                +"ID TEXT,"
                +"Content TEXT,"
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

