package DAL.DalControllers_Employee;

import DAL.DalObjects_Employees.DalConstraint;
import DAL.DalObjects_Employees.DalEmployee;
import DAL.DalController;
import DAL.DalObjects_Employees.DalShift;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DalShiftController extends DalController {

    private static DalShiftController  instance = null;

    public DalShiftController()throws SQLException{
        super();
        tableName = "SHIFTS";
        columnNames = new String[] {"EMPLOYEEID",  "TYPE","DATE", "SHIFT"};
        try{
            createTable();
        }
        catch(SQLException e){
            throw e;
        }
    }

    @Override
    protected boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:Desktop\\database_nitoz.db");
        String query = "CREATE TABLE IF NOT EXISTS SHIFTS("
                +"EMPLOYEEID TEXT,"
                +"TYPE TEXT,"
                +"DATE DATE,"
                +"SHIFT INTEGER,"
                +"FOREIGN KEY (EMPLOYEEID) REFERENCES EMPLOYEES(ID),"
                +"PRIMARY KEY (SHIFT, DATE, EMPLOYEEID));";
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

    public static DalShiftController getInstance()throws SQLException{
        if(instance == null){ instance = new DalShiftController(); }
        return instance;
    }

    public boolean insert(DalShift dalShift) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:Desktop\\database_nitoz.db");
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalShift.getEmployeeId());
            st.setString(2,dalShift.getType());
            st.setDate(3, new Date(dalShift.getDate().getYear(), dalShift.getDate().getMonth().getValue(), dalShift.getDate().getDayOfMonth()));
            st.setInt(4,dalShift.getShift());

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

    public boolean update(DalShift dalShift) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:Desktop\\database_nitoz.db");
        String query="UPDATE "+tableName+" SET "+columnNames[0]+"=?, "+columnNames[1]+"=?, "+columnNames[2]+"=?, "+columnNames[3]+"=?, WHERE ("+columnNames[0]+"=? AND " +columnNames[2]+ "=? AND "+columnNames[3]+"=? )";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalShift.getEmployeeId());
            st.setString(2,dalShift.getType());
            st.setDate(3, new Date(dalShift.getDate().getYear(), dalShift.getDate().getMonth().getValue(), dalShift.getDate().getDayOfMonth()));
            st.setInt(4,dalShift.getShift());
            st.setString(5,dalShift.getEmployeeId());
            st.setDate(6, new Date(dalShift.getDate().getYear(), dalShift.getDate().getMonth().getValue(), dalShift.getDate().getDayOfMonth()));
            st.setInt(7,dalShift.getShift());

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

    public boolean delete(DalShift dalShift) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:Desktop\\database_nitoz.db");
        String query="DELETE FROM "+tableName+" WHERE ("+columnNames[0]+"=? AND " +columnNames[2]+ "=? AND "+columnNames[3]+"=? )";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalShift.getEmployeeId());
            st.setDate(2, new Date(dalShift.getDate().getYear(), dalShift.getDate().getMonth().getValue(), dalShift.getDate().getDayOfMonth()));
            st.setInt(3,dalShift.getShift());

            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw e;
        }
        return true;
    }
    public LinkedList<DalShift> load () throws SQLException// Select From DB
    {
        LinkedList<DalShift> shifts = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:Desktop\\database_nitoz.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                Date date = resultSet.getDate(3);
                LocalDate lDate = date.toLocalDate();
                shifts.add(new DalShift(resultSet.getString(1), resultSet.getString(2),
                        lDate, resultSet.getInt(4))
                );

            }
        } catch (SQLException e) {
            throw e;
        }
        return shifts;
    }

    public LinkedList<String> loadShiftManning (LocalDate date, String type )throws SQLException{
        LinkedList<String> IDs = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:Desktop\\database_nitoz.db");
        String query = "SELECT EMPLOYEEID FROM "+tableName+"WHERE TYPE =? AND DATE=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,type);
            st.setDate(2, new Date(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth()));
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                IDs.add(resultSet.getString(1));
            }
        }
        catch (SQLException e){
            throw e;
        }
        return IDs;
    }

}
