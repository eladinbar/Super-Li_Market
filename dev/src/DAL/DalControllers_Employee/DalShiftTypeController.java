package DAL.DalControllers_Employee;

import DAL.DalObjects_Employees.DalConstraint;
import DAL.DalObjects_Employees.DalEmployee;
import DAL.DalController;
import DAL.DalObjects_Employees.DalShift;
import DAL.DalObjects_Employees.DalShiftType;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DalShiftTypeController extends DalController {
    private static DalShiftTypeController instance = null;

    private DalShiftTypeController()throws SQLException{
        super();
        tableName = "SHIFT";
        columnNames = new String[] {"EMPLOYEEID",  "TYPE","DATE", "SHIFT"};
        try{
            createTable();
        }
        catch (SQLException e){
            throw e;
        }
    }

    @Override
    protected boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
            String query = "CREATE TABLE IF NOT EXISTS SHIFTTYPES("
                +"AMOUNT INTEGER,"
                +"TYPE INTEGER,"
                +"ROLE TEXT,"
                +"PRIMARY KEY (TYPE, ROLE));";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            System.out.println("Creating\n");
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

    public static DalShiftTypeController getInstance()throws SQLException{
        if(instance==null){ instance = new DalShiftTypeController();}
        return instance;
    }

    public boolean insert(DalShiftType dalShiftType) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalShiftType.getAmount());
            st.setString(2,dalShiftType.getType());
            st.setString(4,dalShiftType.getRole());

            System.out.println("executing insert");
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

    public boolean update(DalShiftType dalShiftType) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[0]+"=?, "+columnNames[1]+"=?, "+columnNames[2]+"=?, WHERE ("+columnNames[1]+"=? AND "+columnNames[2]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalShiftType.getAmount());
            st.setString(2,dalShiftType.getType());
            st.setString(3,dalShiftType.getRole());
            st.setString(4,dalShiftType.getType());
            st.setString(5,dalShiftType.getRole());

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

    public boolean delete(DalShiftType dalShiftType) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+"WHERE ("+columnNames[1]+"=? AND "+columnNames[2]+"=?)";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1, dalShiftType.getType());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw e;
        }
        return true;
    }
    public LinkedList<DalShiftType> load () throws SQLException// Select From DB
    {
        LinkedList<DalShiftType> shiftTypes = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                shiftTypes.add(new DalShiftType(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(3))
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        return shiftTypes;
    }
}
