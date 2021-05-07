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

    public DalShiftController(){
        super();
        tableName = "Shift";
        columnNames = new String[] {"EMPLOYEE ID",  "TYPE","DATE", "SHIFT"};
    }

    public static DalShiftController getInstance(){
        if(instance == null){ instance = new DalShiftController(); }
        return instance;
    }

    public boolean insert(DalShift dalShift) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
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
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }

        return true;


    }

    public boolean update(DalShift dalShift) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
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
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }
        return true;
    }

    public boolean delete(DalShift dalShift) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
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
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalShift> load () throws SQLException// Select From DB
    {
        LinkedList<DalShift> shifts = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                Date date = resultSet.getDate(3);
                LocalDate lDate = date.toLocalDate();
                shifts.add(new DalShift(resultSet.getString(1),resultSet.getString(2),
                        lDate,resultSet.getInt(4))
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return shifts;
    }
}
