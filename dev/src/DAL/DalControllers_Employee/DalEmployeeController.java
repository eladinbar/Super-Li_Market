package DAL.DalControllers_Employee;

import DAL.DalObjects_Employees.DalEmployee;
import DAL.DalController;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DalEmployeeController extends DalController {
    private static DalEmployeeController instance = null;

    public DalEmployeeController() throws SQLException{
        super();
            tableName = "EMPLOYEES";
            columnNames = new String[] {"ID","ROLE", "TRANSACTIONDATE",  "DAYS OFF", "SALARY", "SICK  DAYS", "EDUCATION FUND", "EMPLOYED"};
            try{
                createTable();
            }
            catch(SQLException e){
                throw e;
            }
    }

    @Override
    protected boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS EMPLOYEES("
                +"ID TEXT,"
                +"ROLE TEXT,"
                +"TRANSACTIONDATE DATE,"
                +"DAYSOFF INTEGER,"
                +"SALARY INTEGER,"
                +"SICKDAYS INTEGER,"
                +"EDUCATIONFUND INTEGER,"
                +"EMPLOYED INTEGER,"
                +"PRIMARY KEY (ID));";
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

    public static DalEmployeeController getInstance() throws SQLException{
        if(instance==null){ instance = new DalEmployeeController();}
        return instance;
    }
    public boolean insert(DalEmployee dalEmployee) throws SQLException {
        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalEmployee.getId());
            st.setString(2,dalEmployee.getRole());
            st.setDate(3, new Date(dalEmployee.getTransactionDate().getYear(), dalEmployee.getTransactionDate().getMonth().getValue(),dalEmployee.getTransactionDate().getDayOfMonth()));
            st.setInt(4,dalEmployee.getDaysOff());
            st.setInt(5, dalEmployee.getSalary());
            st.setInt(6, dalEmployee.getSickDays());
            st.setInt(7, dalEmployee.getEducationFund());
            int bool = 0;
            if(dalEmployee.getEmployed()) {bool =1;}
            st.setInt(8,bool);

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

    public boolean update(DalEmployee dalEmployee) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[0]+"=?"+columnNames[1]+"=?, "+columnNames[2]+"=?, "+columnNames[3]+"=?, "+columnNames[4]+"=?, "+columnNames[5]+"=?, "+columnNames[6]+"=?"+columnNames[7]+"=?, WHERE ("+columnNames[0]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalEmployee.getId());
            st.setString(2,dalEmployee.getRole());
            st.setDate(3, new Date(dalEmployee.getTransactionDate().getYear(), dalEmployee.getTransactionDate().getMonth().getValue(),dalEmployee.getTransactionDate().getDayOfMonth()));
            st.setInt(4,dalEmployee.getDaysOff());
            st.setInt(5, dalEmployee.getSalary());
            st.setInt(6, dalEmployee.getSickDays());
            int bool = 0;
            if(dalEmployee.getEmployed()) {bool =1;}
            st.setInt(7, bool);
            st.setInt(8, dalEmployee.getEducationFund());


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

    public boolean delete(DalEmployee dalEmployee) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE "+columnNames[0]+"=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalEmployee.getId());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw e;
        }
        return true;
    }
    public LinkedList<DalEmployee> load () throws SQLException// Select From DB
    {
        LinkedList<DalEmployee> employees = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Date date = resultSet.getDate(3);
                LocalDate lDate = date.toLocalDate();
                boolean employed = true;
                if(resultSet.getInt(8)==0){employed=false;}
                employees.add(new DalEmployee(resultSet.getString(1),resultSet.getString(2),
                       lDate,resultSet.getInt(4), resultSet.getInt(5) , resultSet.getInt(6), resultSet.getInt(7), employed )
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        return employees;
    }
}
