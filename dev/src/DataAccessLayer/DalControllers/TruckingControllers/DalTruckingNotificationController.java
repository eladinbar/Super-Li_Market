package DataAccessLayer.DalControllers.TruckingControllers;

import DataAccessLayer.DalControllers.Employee_Trucking_DALController_Interface;
import DataAccessLayer.DalObjects.TruckingObjects.DalTruckingNotification;

import java.sql.*;
import java.util.LinkedList;

public class DalTruckingNotificationController extends Employee_Trucking_DALController_Interface {
    private static DalTruckingNotificationController controller;
    //TODO - check how to createTable without PK

    private DalTruckingNotificationController(){
        super();
        this.tableName="TruckingNotifications";
        this.columnNames=new String[1];
        columnNames[0]="Content";
    }

    public static DalTruckingNotificationController getInstance() throws SQLException {
        if (controller==null) {controller=new DalTruckingNotificationController();
            controller.createTable();
        }
        return controller;
    }


    public boolean insert(DalTruckingNotification notification) throws SQLException {

        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT INTO "+tableName+" VALUES (?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,notification.getContent());


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

    public boolean update(DalTruckingNotification notification) throws SQLException {//TODO do we need it? if we do need to check how
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?"+" WHERE ("+columnNames[0]+"= ?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,notification.getContent());
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

    public boolean delete(DalTruckingNotification notification) throws SQLException {//TODO do we need it? if we do need to check how
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM " +tableName+ " WHERE("+columnNames[0]+"=?)";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,notification.getContent());
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
        LinkedList<DalTruckingNotification> drivers = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                drivers.add(new DalTruckingNotification(resultSet.getString(1)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return drivers;
    }
    public boolean createTable() throws SQLException {//TODO need to check how to create without PK
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS TruckingNotifications("
                +"ID Content,"
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

