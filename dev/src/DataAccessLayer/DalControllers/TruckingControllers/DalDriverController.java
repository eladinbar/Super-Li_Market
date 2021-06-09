package DataAccessLayer.DalControllers.TruckingControllers;

import DataAccessLayer.DAL$.DalController$;
import DataAccessLayer.DalObjects.TruckingObjects.DalDriver;

import java.sql.*;
import java.util.LinkedList;

public class DalDriverController extends DalController$ {

    private static DalDriverController controller;

    private DalDriverController(){
        //TODO - Check when tables created
        super();
        this.tableName="Drivers";
        this.columnNames=new String[6];
        columnNames[0]="ID";columnNames[1]="name";columnNames[2]="license";
    }

    public static DalDriverController getInstance() throws SQLException {
        if (controller==null) {controller=new DalDriverController();
            controller.createTable();
        }
        return controller;
    }


    public boolean insert(DalDriver dalDriver) throws SQLException {

        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalDriver.getID());
            st.setString(2,dalDriver.getName());
            st.setString(3,dalDriver.getLicense());

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

    public boolean update(DalDriver dalDriver) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?,"+columnNames[2]+"=? WHERE ("+columnNames[0]+"= ?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalDriver.getName());
            st.setString(2,dalDriver.getLicense());
            st.setString(3,dalDriver.getID());
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

    public boolean delete(DalDriver dalDriver) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM " +tableName+ " WHERE("+columnNames[0]+"=?)";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalDriver.getID());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalDriver> load () throws SQLException// Select From DB
    {
        LinkedList<DalDriver> drivers = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                drivers.add(new DalDriver(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return drivers;
    }
    public boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS Drivers("
                +"ID TEXT,"
                +"name TEXT,"
                +"license TEXT,"
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
