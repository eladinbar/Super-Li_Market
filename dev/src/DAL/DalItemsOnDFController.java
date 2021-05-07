package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalItemsOnDFController extends DalController{

    private DalItemsOnDFController(){//TODO - Check when tables created
        super();
        this.tableName="ItemsOnDF";
        this.columnNames=new String[3];
        columnNames[0]="DFID";
        columnNames[1]="itemID";columnNames[2]="amount";}

    public static DalItemsOnDFController getInstance() {
        return new DalItemsOnDFController();
    }

    public boolean insert(DalItemsOnDF dalItemsOnDF) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalItemsOnDF.getDFID());
            st.setInt(2,dalItemsOnDF.getItemID());
            st.setInt(3,dalItemsOnDF.getAmount());

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
        String query="UPDATE "+tableName+" SET "+columnNames[2]+"=? WHERE ("+columnNames[0]+"= ? AND"+columnNames[1]+"=?) ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalItemsOnDF.getAmount());
            st.setInt(2,dalItemsOnDF.getDFID());
            st.setInt(3,dalItemsOnDF.getItemID());
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
        String query="DELETE FROM "+tableName+" WHERE "+columnNames[0]+"=? AND "+columnNames[1]+"=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalItemsOnDF.getDFID());
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
                items.add(new DalItemsOnDF(resultSet.getInt(1),resultSet.getInt(2),resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return items;
    }
}
