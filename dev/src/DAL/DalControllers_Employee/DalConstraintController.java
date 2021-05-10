package DAL.DalControllers_Employee;

import DAL.DalObjects_Employees.DalConstraint;
import DAL.DalObjects_Employees.DalEmployee;
import DAL.DalController;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DalConstraintController extends DalController {
    private static DalConstraintController instance = null;
    private DalConstraintController() throws SQLException{
        super();
        tableName = "Constraints";
        columnNames = new String[] {"EMPLOYEEID","REASON", "DATE",  "SHIFT"};
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
        String query = "CREATE TABLE IF NOT EXISTS CONSTRAINTS("
                +"EMPLOYEEID TEXT,"
                +"REASON TEXT,"
                +"DATE DATE,"
                +"SHIFT INTEGER,"
                +"FOREIGN KEY (EMPLOYEEID) REFERENCES EMPLOYEES(ID),"
                +"PRIMARY KEY (DATE, EMPLOYEEID));";
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


    public static DalConstraintController getInstance()throws SQLException{
        if(instance==null){ instance = new DalConstraintController();}
        return instance;
    }
    public boolean insert(DalConstraint dalConstraint) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalConstraint.getEmployeeId());
            st.setString(2,dalConstraint.getReason());
            st.setDate(3, new Date(dalConstraint.getDate().getYear(), dalConstraint.getDate().getMonth().getValue(),dalConstraint.getDate().getDayOfMonth()));
            st.setInt(4,dalConstraint.getShift());

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

    public boolean update(DalConstraint dalConstraint) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[0]+"=?, "+columnNames[1]+"=?, "+columnNames[2]+"=?, "+columnNames[3]+"=?, WHERE ("+columnNames[0]+"=? AND "+columnNames[2]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalConstraint.getEmployeeId());
            st.setString(2,dalConstraint.getReason());
            st.setDate(3, new Date(dalConstraint.getDate().getYear(), dalConstraint.getDate().getMonth().getValue(),dalConstraint.getDate().getDayOfMonth()));
            st.setInt(4,dalConstraint.getShift());
            st.setString(5,dalConstraint.getEmployeeId());
            st.setDate(6, new Date(dalConstraint.getDate().getYear(), dalConstraint.getDate().getMonth().getValue(),dalConstraint.getDate().getDayOfMonth()));


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

    public boolean delete(DalConstraint dalConstraint) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE ("+columnNames[0]+"=? AND "+columnNames[2]+"=?)";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalConstraint.getEmployeeId());
            st.setDate(2, new Date(dalConstraint.getDate().getYear(), dalConstraint.getDate().getMonth().getValue(),dalConstraint.getDate().getDayOfMonth()));
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw e;
        }
        return true;
    }
    public LinkedList<DalConstraint> load () throws SQLException// Select From DB
    {
        LinkedList<DalConstraint> constraints = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                Date date = resultSet.getDate(3);
                LocalDate lDate = date.toLocalDate();
                constraints.add(new DalConstraint(resultSet.getString(1),resultSet.getString(2),
                        lDate,resultSet.getInt(4))
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        return constraints;
    }

    }
