package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalSiteController extends DalController{

    public  DalSiteController(){//TODO - Check when tables created
        super();
        this.tableName="Sites";
        this.columnNames=new String[6];
        columnNames[0]="siteID";columnNames[1]="name";columnNames[2]="city";
        columnNames[3]="deliverArea";columnNames[4]="contactName";columnNames[5]="phoneNumber";}


    public boolean insert(DalSite dalSite) throws SQLException {
        //TODO - change URL
        boolean done=false;
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO ? VALUES ? ? ? ? ? ? ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setInt(2,dalSite.getSiteID());
            st.setString(3,dalSite.getName());
            st.setString(4,dalSite.getCity());
            st.setInt(5,dalSite.getDeliveryArea());
            st.setString(6,dalSite.getContactName());
            st.setString(7,dalSite.getPhoneNumber());

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

    public boolean update(DalSite dalSite) throws SQLException {
        int res=-1;
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE ? SET ?=?,?=?,?=?,?=?,?=? WHERE ?= ? ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);

            st.setString(2,columnNames[1]);
            st.setString(3,dalSite.getName());

            st.setString(4,columnNames[2]);
            st.setString(5,dalSite.getCity());

            st.setString(6,columnNames[3]);
            st.setInt(7,dalSite.getDeliveryArea());

            st.setString(8,columnNames[4]);
            st.setString(9,dalSite.getContactName());


            st.setString(10,columnNames[5]);
            st.setString(11,dalSite.getPhoneNumber());

            st.setString(12,columnNames[0]);
            st.setInt(13,dalSite.getSiteID());

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

    public boolean delete(DalSite dalSite) throws SQLException {
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM ? WHERE ?=? ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setString(2,columnNames[0]);
            st.setInt(3,dalSite.getSiteID());
            done=st.execute();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return done;
    }
    public LinkedList<DalSite> load () throws SQLException// Select From DB
    {
        LinkedList<DalSite> sites = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, tableName);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                boolean completed = resultSet.getString(4).equals("true");
                sites.add(new DalSite(resultSet.getInt(1),resultSet.getString(2),
                        resultSet.getString(3),resultSet.getInt(4),resultSet.getString(5)
                        ,resultSet.getString(6))
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return sites;
    }
}
