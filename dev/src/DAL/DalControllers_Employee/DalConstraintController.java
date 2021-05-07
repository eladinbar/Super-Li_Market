package DAL.DalControllers_Employee;

import DAL.DalObjects_Employees.DalConstraint;
import DAL.DalObjects_Employees.DalEmployee;
import DAL.DalController;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DalConstraintController extends DalController {
    private static DalConstraintController instance = null;
    private DalConstraintController(){
        super();
        tableName = "Constraint";
        columnNames = new String[] {"EMPLOYEE ID","REASON", "DATE",  "SHIFT"};
    }

    public static DalConstraintController getInstance(){
        if(instance==null){ instance = new DalConstraintController();}
        return instance;
    }
    public boolean insert(DalConstraint dalConstraint) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
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
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }

        return true;


    }

    public boolean update(DalConstraint dalConstraint) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
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
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM "+tableName+" WHERE ("+columnNames[0]+"=? AND "+columnNames[2]+"=?)";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalConstraint.getEmployeeId());
            st.setDate(2, new Date(dalConstraint.getDate().getYear(), dalConstraint.getDate().getMonth().getValue(),dalConstraint.getDate().getDayOfMonth()));

            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalConstraint> load () throws SQLException// Select From DB
    {
        LinkedList<DalConstraint> constraints = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                Date date = resultSet.getDate(3);
                LocalDate lDate = date.toLocalDate();
                constraints.add(new DalConstraint(resultSet.getString(1),resultSet.getString(2),
                        lDate,resultSet.getInt(4))
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return constraints;
    }
}
