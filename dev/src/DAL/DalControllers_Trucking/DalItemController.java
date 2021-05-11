package DAL.DalControllers_Trucking;

import DAL.DalController;
import DAL.DalObjects_Trucking.DalItem;

import java.sql.*;
import java.util.LinkedList;

public class DalItemController extends DalController {

    private static DalItemController controller;

    private DalItemController(){
        super();
        this.tableName="Items";
        this.columnNames=new String[4];
        columnNames[0]="ID";columnNames[1]="weight";columnNames[2]="name";columnNames[3]="originSite";}

    public static DalItemController getInstance() throws SQLException {
        if (controller==null) {
            controller = new DalItemController();
            controller.createTable();
        }
        return controller;
    }

    public boolean insert(DalItem dalItem) throws SQLException {
        Connection conn= DriverManager.getConnection(connection);

        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalItem.getID());
            st.setDouble(2,dalItem.getWeight());
            st.setString(3,dalItem.getName());
            st.setInt(4,dalItem.getOriginSite());

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
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?, "+columnNames[2]+"=?, "+columnNames[3]+"=? WHERE ("+columnNames[0]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);


            st.setDouble(1,dalItem.getWeight());

            st.setString(2,dalItem.getName());
            st.setInt(3,dalItem.getOriginSite());

            st.setInt(4,dalItem.getID());


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
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE("+columnNames[0]+"=?) ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalItem.getID());
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
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                DalItem di=new DalItem(resultSet.getInt(1),  resultSet.getDouble(2),resultSet.getString(3),resultSet.getInt(4));
                items.add(di);
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return items;
    }
    public boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS Items("
                +"ID INTEGER,"
                +"weight NUMERIC,"
                +"name TEXT,"
                +"originSite INTEGER,"
                +"FOREIGN KEY (originSite) REFERENCES Sites (siteID) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"PRIMARY KEY (ID));";
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
