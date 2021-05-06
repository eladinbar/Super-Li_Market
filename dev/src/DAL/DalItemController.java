package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalItemController extends DalController{

    public DalItemController(){//TODO - Check when tables created
        super();
        this.tableName="Items";
        this.columnNames=new String[4];
        columnNames[0]="ID";columnNames[1]="weight";columnNames[2]="name";columnNames[3]="originSite";}


    public boolean insert(DalItem dalItem) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");

        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalItem.getID());
            st.setDouble(2,dalItem.getWeight());
            st.setString(3,dalItem.getName());
            st.setInt(4,dalItem.getOriginSite());

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

    public boolean update(DalItem dalItem) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?, "+columnNames[2]+"=?, "+columnNames[3]+"=? WHERE ("+columnNames[0]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);

            //st.setString(1,columnNames[1]);

            //st.setString(3,columnNames[2]);
            st.setDouble(1,dalItem.getWeight());

            st.setString(2,dalItem.getName());
            //st.setString(5,columnNames[3]);
            st.setInt(3,dalItem.getOriginSite());

            //st.setString(7,columnNames[0]);
            st.setInt(4,dalItem.getID());
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

    public boolean delete(DalItem dalItem) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM "+tableName+" WHERE ?=? ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,columnNames[0]);
            st.setInt(2,dalItem.getID());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalItem> load () throws SQLException// Select From DB
    {
        LinkedList<DalItem> items = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                boolean completed = resultSet.getString(4).equals("true");
                items.add(new DalItem(resultSet.getInt(1),  resultSet.getDouble(2),resultSet.getString(3),resultSet.getInt(4)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return items;
    }
}
