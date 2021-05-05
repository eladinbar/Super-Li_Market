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
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO"+tableName+" VALUES ? ?  ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalItemsOnDF.getItemID());
            st.setInt(2,dalItemsOnDF.getAmount());

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

    public boolean update(DalItemsOnDF dalItemsOnDF) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE "+tableName+" SET ?=? WHERE ?= ? ";
        try{
            PreparedStatement st=conn.prepareStatement(query);

            st.setString(1,columnNames[1]);
            st.setInt(2,dalItemsOnDF.getAmount());

            st.setString(3,columnNames[0]);
            st.setInt(4,dalItemsOnDF.getItemID());

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

    public boolean delete(DalItemsOnDF dalItemsOnDF) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM "+tableName+" WHERE ?=? ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,columnNames[0]);
            st.setInt(2,dalItemsOnDF.getItemID());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalItemsOnDF> load () throws SQLException// Select From DB
    {
        LinkedList<DalItemsOnDF> items = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
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
