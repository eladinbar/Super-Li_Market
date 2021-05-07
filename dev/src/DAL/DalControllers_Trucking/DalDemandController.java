package DAL.DalControllers_Trucking;

import DAL.DalControllers_Employee.DalController;
import DAL.DalObjects_Trucking.DalDemand;

import java.sql.*;
import java.util.LinkedList;

public class DalDemandController extends DalController {

    public DalDemandController()
    {//TODO - Check when tables created
        super();
        this.tableName="Demands";
        this.columnNames=new String[3];
        columnNames[0]="itemID";columnNames[1]="amount";columnNames[2]="siteID";
    }


    public boolean insert(DalDemand dalDemand) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalDemand.getItemID());
            st.setInt(2,dalDemand.getAmount());
            st.setInt(3,dalDemand.getSiteID());

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

    public boolean update(DalDemand dalDemand) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=? WHERE "+columnNames[0]+"=? AND"+columnNames[2]+"=?";
        try{
            PreparedStatement st=conn.prepareStatement(query);

            st.setInt(1,dalDemand.getAmount());
            st.setInt(2,dalDemand.getItemID());
            st.setInt(3,dalDemand.getSiteID());
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

    public boolean delete(DalDemand dalDemand) throws SQLException {

        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM "+tableName+" WHERE ?=? AND ?=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,columnNames[0]);
            st.setInt(2,dalDemand.getItemID());
            st.setString(3,columnNames[2]);
            st.setInt(4,dalDemand.getSiteID());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalDemand> load () throws SQLException// Select From DB
    {
        LinkedList<DalDemand> demands = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                boolean completed = resultSet.getString(4).equals("true");
                demands.add(new DalDemand(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return demands;
    }
}
