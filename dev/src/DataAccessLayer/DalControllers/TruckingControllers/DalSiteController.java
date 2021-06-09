package DataAccessLayer.DalControllers.TruckingControllers;

import DataAccessLayer.DAL$.DalController$;
import DataAccessLayer.DalObjects.TruckingObjects.DalSite;

import java.sql.*;
import java.util.LinkedList;

public class DalSiteController extends DalController$ {

    private static DalSiteController controller;

    private DalSiteController(){
        super();
        this.tableName="Sites";
        this.columnNames=new String[6];
        columnNames[0]="siteID";columnNames[1]="name";columnNames[2]="city";
        columnNames[3]="deliverArea";columnNames[4]="contactName";columnNames[5]="phoneNumber";}

    public static DalSiteController getInstance() throws SQLException {
        if (controller==null) {
            controller = new DalSiteController();
            controller.createTable();
        }
        return controller;
    }

    public boolean insert(DalSite dalSite) throws SQLException {
        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalSite.getSiteID());
            st.setString(2,dalSite.getName());
            st.setString(3,dalSite.getCity());
            st.setInt(4,dalSite.getDeliveryArea());
            st.setString(5,dalSite.getContactName());
            st.setString(6,dalSite.getPhoneNumber());
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
        Connection conn=DriverManager.getConnection(connection);
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
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE("+columnNames[0]+"=?) ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalSite.getSiteID());
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
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
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
    public boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS Sites("
                +"siteID INTEGER,"
                +"name TEXT,"
                +"city TEXT,"
                +"deliveryArea INTEGER,"
                +"contactName TEXT,"
                +"phoneNumber TEXT,"
                +"PRIMARY KEY (siteID));";
        try {
            PreparedStatement st=conn.prepareStatement(query);
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
}
