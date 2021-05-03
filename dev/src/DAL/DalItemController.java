package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalItemController extends DalController{

    public DalItemController(){//TODO - Check when tables created
        super();
        this.tableName="Items";
        this.columnNames=new String[4];
        columnNames[0]="ID";columnNames[1]="name";columnNames[2]="weight";columnNames[3]="originSite";}


    public boolean insert(DalItem dalItem) throws SQLException {
        //TODO - change URL
        boolean done=false;
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO ? VALUES ? ? ? ? ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setString(2,dalItem.getName());
            st.setInt(3,dalItem.getID());
            st.setDouble(4,dalItem.getWeight());
            st.setInt(5,dalItem.getOriginSite());

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

    public boolean update(DalItem dalItem) throws SQLException {
        int res=-1;
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE ? SET ?=?,?=?,?=? WHERE ?= ? ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);

            st.setString(2,columnNames[1]);
            st.setString(3,dalItem.getName());

            st.setString(4,columnNames[2]);
            st.setDouble(5,dalItem.getWeight());

            st.setString(6,columnNames[3]);
            st.setInt(7,dalItem.getOriginSite());

            st.setString(8,columnNames[0]);
            st.setInt(9,dalItem.getOriginSite());
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

    public boolean delete(DalItem dalItem) throws SQLException {
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM ? WHERE ?=? ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setString(2,columnNames[0]);
            st.setInt(3,dalItem.getID());
            done=st.execute();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return done;
    }
    public LinkedList<DalItem> load () throws SQLException// Select From DB
    {
        LinkedList<DalItem> items = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, tableName);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                boolean completed = resultSet.getString(4).equals("true");
                items.add(new DalItem(resultSet.getInt(1), resultSet.getString(2), resultSet.getDouble(3),resultSet.getInt(4)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return items;
    }
}
