package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalDemandController extends DalController{

    public DalDemandController()
    {//TODO - Check when tables created
        super();
        this.tableName="Demands";
        this.columnNames=new String[3];
        columnNames[0]="itemID";columnNames[1]="amount";columnNames[2]="siteID";
    }


    public boolean insert(DalDemand dalDemand) throws SQLException {
        //TODO - change URL
        boolean done=false;
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO ? VALUES ? ? ? ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setInt(2,dalDemand.getItemID());
            st.setInt(3,dalDemand.getAmount());
            st.setInt(4,dalDemand.getSiteID());

            System.out.println("executing insert");
            done=st.execute();

        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }

        return done;


    }

    public boolean update(DalDemand dalDemand) throws SQLException {
        int res=-1;
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE ? SET ?=? WHERE ?= ? AND ?=?";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);

            st.setString(2,columnNames[1]);
            st.setInt(3,dalDemand.getAmount());

            st.setString(4,columnNames[0]);
            st.setInt(5,dalDemand.getItemID());

            st.setString(6,columnNames[2]);
            st.setInt(7,dalDemand.getSiteID());
            System.out.println("executing insert");

            done=st.execute();


        }
        catch (SQLException e){
            throw new SQLException(e.getMessage());
        }
        finally {
            conn.close();
        }
        return done;
    }

    public boolean delete(DalDemand dalDemand) throws SQLException {
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM ? WHERE ?=? AND ?=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setString(2,columnNames[0]);
            st.setInt(3,dalDemand.getItemID());
            st.setString(4,columnNames[2]);
            st.setInt(5,dalDemand.getSiteID());
            done=st.execute();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return done;
    }
    public LinkedList<DalDemand> load () throws SQLException// Select From DB
    {
        LinkedList<DalDemand> demands = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, tableName);
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
