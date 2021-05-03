package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalItemsOnDFController extends DalController{

    public DalItemsOnDFController(){//TODO - Check when tables created
        super();
        this.tableName="ItemsOnDF";
        this.columnNames=new String[2];
        columnNames[0]="itemID";columnNames[1]="amount";}


    public boolean insert(DalItemsOnDF dalItemsOnDF) throws SQLException {
        //TODO - change URL
        boolean done=false;
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO ? VALUES ? ?  ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setInt(2,dalItemsOnDF.getItemID());
            st.setInt(3,dalItemsOnDF.getAmount());

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

    public boolean update(DalItemsOnDF dalItemsOnDF) throws SQLException {
        int res=-1;
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE ? SET ?=? WHERE ?= ? ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);

            st.setString(2,columnNames[1]);
            st.setInt(3,dalItemsOnDF.getAmount());

            st.setString(4,columnNames[0]);
            st.setInt(5,dalItemsOnDF.getItemID());
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

    public boolean delete(DalItemsOnDF dalItemsOnDF) throws SQLException {
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM ? WHERE ?=? ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setString(2,columnNames[0]);
            st.setInt(3,dalItemsOnDF.getItemID());
            done=st.execute();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return done;
    }
    public LinkedList<DalItemsOnDF> load () throws SQLException// Select From DB
    {
        LinkedList<DalItemsOnDF> items = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, tableName);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                boolean completed = resultSet.getString(4).equals("true");
                items.add(new DalItemsOnDF(resultSet.getInt(1),resultSet.getInt(2)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return items;
    }
}
