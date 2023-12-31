package DataAccessLayer.DalControllers.EmployeeControllers;

import DataAccessLayer.DalObjects.EmployeeObjects.DalBankBranch;
import DataAccessLayer.DalControllers.Employee_Trucking_DALController_Interface;

import java.sql.*;
import java.util.LinkedList;

public class DalBankBranchController extends Employee_Trucking_DALController_Interface {
    private static DalBankBranchController instance = null;

    private DalBankBranchController() throws SQLException {
        super();
        tableName = "BANKS";
        columnNames = new String[] {"EMPLOYEEID", "BANK", "BANKBRANCH",  "ACCOUNTNUMBER"};
       try {
           createTable();
       }
       catch(SQLException e){
           throw e;
       }
    }

    @Override
    protected boolean createTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connection)) {
            String query = "CREATE TABLE IF NOT EXISTS BANKS("
                    + "EMPLOYEEID TEXT,"
                    + "BANK TEXT ,"
                    + "BANKBRANCH INTEGER,"
                    + "ACCOUNTNUMBER INTEGER,"
                    + "PRIMARY KEY (ACCOUNTNUMBER));";
            PreparedStatement st = conn.prepareStatement(query);
            st.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
        return true;
    }


    public static DalBankBranchController getInstance() throws SQLException{

        if(instance==null){ instance = new DalBankBranchController();}
        return instance;
    }

    public boolean insert(DalBankBranch dalBankBranch) throws SQLException {
        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT OR IGNORE INTO "+tableName+" VALUES (?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalBankBranch.getEmployeeId());
            st.setString(2,dalBankBranch.getBank());
            st.setInt(3,dalBankBranch.getBankBranch());
            st.setInt(4,dalBankBranch.getAccountNumber());
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

    public boolean update(DalBankBranch dalBankBranch) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[0]+"=?, "+columnNames[1]+"=?, "+columnNames[2]+"=?," +columnNames[3]+"=?, WHERE ("+columnNames[3]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalBankBranch.getEmployeeId());
            st.setString(2,dalBankBranch.getBank());
            st.setInt(3,dalBankBranch.getBankBranch());
            st.setInt(4,dalBankBranch.getAccountNumber());
            st.setInt(5,dalBankBranch.getAccountNumber());
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

    public boolean delete(DalBankBranch dalBankBranch) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE "+columnNames[2]+"=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalBankBranch.getAccountNumber());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw e;
        }
        return true;
    }
    public LinkedList<DalBankBranch> load () throws SQLException// Select From DB
    {
        LinkedList<DalBankBranch > bankBranches = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                bankBranches.add(new DalBankBranch(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getInt(3),resultSet.getInt(4))
                );
            }
        } catch (SQLException e) {
            throw e;
        }
        return bankBranches;
    }

    public DalBankBranch loadBank(int accountNumber) throws SQLException{
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName+"WHERE  BANKACCOUNT=? ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,accountNumber);
            ResultSet resultSet = st.executeQuery(query);
            return new DalBankBranch(resultSet.getString(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4));
        }
        catch (SQLException e){
            throw e;
        }
    }
}
