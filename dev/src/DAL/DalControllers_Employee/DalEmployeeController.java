package DAL.DalControllers_Employee;

import DAL.DalObjects_Employees.DalEmployee;
import DAL.DalController;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DalEmployeeController extends DalController {
    private static DalEmployeeController instance = null;

    public DalEmployeeController(){
        super();
        tableName = "Employees";
            columnNames = new String[] {"ID","ROLE", "TRANSACTION DATE",  "DAYS OFF", "SALARY", "SICK  DAYS", "EDUCATION FUND"};
    }
    public static DalEmployeeController getInstance(){
        if(instance==null){ instance = new DalEmployeeController();}
        return instance;
    }
    public boolean insert(DalEmployee dalEmployee) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalEmployee.getId());
            st.setString(2,dalEmployee.getRole());
            st.setDate(3, new Date(dalEmployee.getTransactionDate().getYear(), dalEmployee.getTransactionDate().getMonth().getValue(),dalEmployee.getTransactionDate().getDayOfMonth()));
            st.setInt(4,dalEmployee.getDaysOff());
            st.setInt(5, dalEmployee.getSalary());
            st.setInt(6, dalEmployee.getSickDays());
            st.setInt(7, dalEmployee.getEducationFund());

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

    public boolean update(DalEmployee dalEmployee) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE "+tableName+" SET "+columnNames[0]+"=?"+columnNames[1]+"=?, "+columnNames[2]+"=?, "+columnNames[3]+"=?, "+columnNames[4]+"=?, "+columnNames[5]+"=?, "+columnNames[6]+"=?, WHERE ("+columnNames[0]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalEmployee.getId());
            st.setString(2,dalEmployee.getRole());
            st.setDate(3, new Date(dalEmployee.getTransactionDate().getYear(), dalEmployee.getTransactionDate().getMonth().getValue(),dalEmployee.getTransactionDate().getDayOfMonth()));
            st.setInt(4,dalEmployee.getDaysOff());
            st.setInt(5, dalEmployee.getSalary());
            st.setInt(6, dalEmployee.getSickDays());
            st.setInt(7, dalEmployee.getEducationFund());

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

    public boolean delete(DalEmployee dalEmployee) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM "+tableName+" WHERE"+columnNames[0]+"=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalEmployee.getId());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalEmployee> load () throws SQLException// Select From DB
    {
        LinkedList<DalEmployee> employees = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                Date date = resultSet.getDate(3);
                LocalDate lDate = date.toLocalDate();
                employees.add(new DalEmployee(resultSet.getString(1),resultSet.getString(2),
                       lDate,resultSet.getInt(4), resultSet.getInt(5) , resultSet.getInt(6), resultSet.getInt(7) )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return employees;
    }
}
