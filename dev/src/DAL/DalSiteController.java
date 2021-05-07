package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalSiteController extends DalController{

    private DalSiteController(){//TODO - Check when tables created
        super();
        this.tableName="Sites";
        this.columnNames=new String[6];
        columnNames[0]="siteID";columnNames[1]="name";columnNames[2]="city";
        columnNames[3]="deliverArea";columnNames[4]="contactName";columnNames[5]="phoneNumber";}

    public static DalSiteController getInstance() {
        return new DalSiteController();
    }

    public boolean insert(DalSite dalSite) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalSite.getSiteID());
            st.setString(2,dalSite.getName());
            st.setString(3,dalSite.getCity());
            st.setInt(4,dalSite.getDeliveryArea());
            st.setString(5,dalSite.getContactName());
            st.setString(6,dalSite.getPhoneNumber());

            System.out.println("executing update");
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

    public boolean update(DalSite dalSite) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?, "+columnNames[2]+"=?, "+columnNames[3]+"=? " +
                columnNames[4]+"=?,"+columnNames[5]+"WHERE ("+columnNames[0]+"=?)";

        try{
            PreparedStatement st=conn.prepareStatement(query);

            st.setString(1,dalSite.getName());
            st.setString(2,dalSite.getCity());
            st.setInt(3,dalSite.getDeliveryArea());
            st.setString(4,dalSite.getContactName());
            st.setString(5,dalSite.getPhoneNumber());
            st.setInt(6,dalSite.getSiteID());

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

    public boolean delete(DalSite dalSite) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM "+tableName+" WHERE ?=? ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,columnNames[0]);
            st.setInt(2,dalSite.getSiteID());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalSite> load () throws SQLException// Select From DB
    {
        LinkedList<DalSite> sites = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
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
