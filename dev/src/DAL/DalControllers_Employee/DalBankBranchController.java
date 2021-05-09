package DAL.DalControllers_Employee;

import DAL.DalObjects_Employees.DalBankBranch;
import DAL.DalObjects_Employees.DalConstraint;
import DAL.DalObjects_Employees.DalEmployee;
import DAL.DalController;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DalBankBranchController extends DalController {
    private static DalBankBranchController instance = null;

    private DalBankBranchController(){
        super();
        tableName = "BANKS";
        columnNames = new String[] {"BANK", "BANKBRANCH",  "ACCOUNTNUMBER"};
       try {
           createTable();
       }
       catch(SQLException e){
       }

    }

    @Override
    protected boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "CREATE TABLE IF NOT EXISTS BANKS("
                +"bank TEXT ,"
                +"BANKBRANCH INTEGER,"
                +"ACCOUNTNUMBER INTEGER,"
                +"PRIMARY KEY (ACCOUNTNUMBERS));";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            System.out.println("Creating\n");
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


    public static DalBankBranchController getInstance(){
        if(instance==null){ instance = new DalBankBranchController();}
        return instance;
    }

    public boolean insert(DalBankBranch dalBankBranch) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalBankBranch.getBank());
            st.setInt(2,dalBankBranch.getBankBranch());
            st.setInt(3,dalBankBranch.getAccountNumber());

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

    public boolean update(DalBankBranch dalBankBranch) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE "+tableName+" SET "+columnNames[0]+"=?, "+columnNames[1]+"=?, "+columnNames[2]+"=?, WHERE ("+columnNames[2]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalBankBranch.getBank());
            st.setInt(2,dalBankBranch.getBankBranch());
            st.setInt(3,dalBankBranch.getAccountNumber());
            st.setInt(4,dalBankBranch.getAccountNumber());
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

    public boolean delete(DalBankBranch dalBankBranch) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM "+tableName+" WHERE"+columnNames[2]+"=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalBankBranch.getAccountNumber());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalBankBranch> load () throws SQLException// Select From DB
    {
        LinkedList<DalBankBranch > bankBranches = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                bankBranches.add(new DalBankBranch(resultSet.getString(1),
                        resultSet.getInt(2),resultSet.getInt(3))
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return bankBranches;
    }
}
