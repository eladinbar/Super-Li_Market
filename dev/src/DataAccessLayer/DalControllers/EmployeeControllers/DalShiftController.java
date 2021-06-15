package DataAccessLayer.DalControllers.EmployeeControllers;


import DataAccessLayer.DalControllers.Employee_Trucking_DALController_Interface;
import DataAccessLayer.DalObjects.EmployeeObjects.DalShift;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DalShiftController extends Employee_Trucking_DALController_Interface {

    private static DalShiftController  instance = null;

    public DalShiftController()throws SQLException{
        super();
        tableName = "SHIFTS";
        columnNames = new String[] {"EMPLOYEEID",  "TYPE","DATE", "SHIFT", "ROLE"};
        try{
            createTable();
        }
        catch(SQLException e){
            throw e;
        }
    }

    @Override
    protected boolean createTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connection)) {
            String query = "CREATE TABLE IF NOT EXISTS SHIFTS("
                    + "EMPLOYEEID TEXT,"
                    + "TYPE TEXT,"
                    + "DATE TEXT,"
                    + "SHIFT INTEGER,"
                    + "ROLE TEXT,"
                    + "FOREIGN KEY (EMPLOYEEID) REFERENCES EMPLOYEES(ID),"
                    + "PRIMARY KEY (SHIFT, DATE, EMPLOYEEID));";
            PreparedStatement st = conn.prepareStatement(query);
            st.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return true;
    }

    public static DalShiftController getInstance()throws SQLException{
        if(instance == null){ instance = new DalShiftController(); }
        return instance;
    }

    public boolean insert(DalShift dalShift) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connection)) {
            String query = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, dalShift.getEmployeeId());
            st.setString(2, dalShift.getType());
            st.setString(3, dalShift.getDate().toString());
            st.setInt(4, dalShift.getShift());
            st.setString(5, dalShift.getRole());
            st.executeUpdate();

        } catch (SQLException e) {
            throw e;
        }
        return true;
    }

    public boolean update(DalShift dalShift) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connection)) {
            String query = "UPDATE " + tableName + " SET " + columnNames[0] + "=?, " + columnNames[1] + "=?, " + columnNames[2] + "=?, " + columnNames[3] + "=?, " + columnNames[4] + "=?, WHERE (" + columnNames[0] + "=? AND " + columnNames[2] + "=? AND " + columnNames[3] + "=? )";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, dalShift.getEmployeeId());
            st.setString(2, dalShift.getType());
            st.setString(3, dalShift.getDate().toString());
            st.setInt(4, dalShift.getShift());
            st.setString(5, dalShift.getRole());
            st.setString(6, dalShift.getEmployeeId());
            st.setString(7, dalShift.getDate().toString());
            st.setInt(8, dalShift.getShift());
            st.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return true;
    }

    public boolean delete(DalShift dalShift) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE ("+columnNames[0]+"=? AND " +columnNames[2]+ "=? AND "+columnNames[3]+"=? )";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalShift.getEmployeeId());
            st.setString (2, dalShift.getDate ().toString ());
            st.setInt(3,dalShift.getShift());
            st.executeUpdate();
        }
        catch (SQLException e) {
            throw e;
        }
        return true;
    }

    public LinkedList<DalShift> load () throws SQLException { // Select From DB
        LinkedList<DalShift> shifts = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                String date = resultSet.getString ( 3 );
                LocalDate lDate = LocalDate.parse ( date );
                shifts.add(new DalShift(resultSet.getString(1), resultSet.getString(2),
                        lDate, resultSet.getInt(4), resultSet.getString(5))
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        return shifts;
    }
}
