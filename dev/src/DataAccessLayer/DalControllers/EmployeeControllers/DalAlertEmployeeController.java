package DataAccessLayer.DalControllers.EmployeeControllers;

import DataAccessLayer.DalControllers.Employee_Trucking_DALController_Interface;
import DataAccessLayer.DalObjects.EmployeeObjects.DalALertEmployee;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DalAlertEmployeeController extends Employee_Trucking_DALController_Interface {
    private static DalAlertEmployeeController instance = null;

    private DalAlertEmployeeController() throws SQLException {
        super();
        tableName = "RESOURCE_MANAGER_ALERTS";
        columnNames = new String[] {"rowid","ROLE", "ALERTS"};
        try {
            createTable();
        }
        catch(SQLException e){
            throw e;
        }

    }

    @Override
    protected boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS RESOURCE_MANAGER_ALERTS("
                +"rowid INTEGER PRIMARY KEY AUTHORIZATION ,"
                +"ROLE TEXT"
                +"ALERT TEXT);";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.executeUpdate();
        }
        catch (SQLException e){
            throw e;
        }
        finally {
            conn.close();
        }
        return true;
    }

    public List<DalALertEmployee> Load() throws SQLException {
        LinkedList<DalALertEmployee > alerts = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                alerts.add(new DalALertEmployee (resultSet.getInt ( 1 ), resultSet.getString (2), resultSet.getString(3)));
            }
        } catch (SQLException e) {
            throw e;
        }
        return alerts;
    }


    public static DalAlertEmployeeController getInstance() throws SQLException{

        if(instance==null){ instance = new DalAlertEmployeeController ();}
        return instance;
    }

    public boolean insert(DalALertEmployee dalALertEmployee) throws SQLException {
        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT INTO "+tableName+" VALUES (?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString (1,dalALertEmployee.getRole ());
            st.setString(2,dalALertEmployee.getAlert());
            st.executeUpdate();
        }
        catch (SQLException e){
            throw e;
        }
        finally {
            conn.close();
        }

        return true;
    }

    public boolean delete(int id) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE "+columnNames[1]+"=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt (1,id);
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw e;
        }
        return true;
    }

    public int getLast() throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query = "SELECT last_insert_rowid()";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            return st.executeQuery ().getInt ( 1 );
        } catch (SQLException e){
            throw e;
        }
    }
}
