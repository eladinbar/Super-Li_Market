package DataAccessLayer.DalControllers.TruckingControllers;

import DataAccessLayer.DalControllers.Employee_Trucking_DALController_Interface;
import DataAccessLayer.DalObjects.TruckingObjects.DalDemand;

import java.sql.*;
import java.util.LinkedList;

public class DalDemandController extends Employee_Trucking_DALController_Interface {

    private static DalDemandController controller;

    private DalDemandController()
    {
        super();
        this.tableName="Demands";
        this.columnNames=new String[3];
        columnNames[0]="itemID";columnNames[1]="amount";columnNames[2]="siteID";

    }



    public static DalDemandController getInstance() throws SQLException {
        if (controller==null) {
            controller = new DalDemandController();
            controller.createTable();
        }
        return controller;

    }


    public boolean insert(DalDemand dalDemand) throws SQLException {
        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,dalDemand.getItemID());
            st.setInt(2,dalDemand.getAmount());
            st.setInt(3,dalDemand.getSiteID());


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

    public boolean update(DalDemand dalDemand) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=? WHERE "+columnNames[0]+"=? AND "+columnNames[2]+"=?";
        try{
            PreparedStatement st=conn.prepareStatement(query);

            st.setInt(1,dalDemand.getAmount());
            st.setInt(2,dalDemand.getItemID());
            st.setInt(3,dalDemand.getSiteID());
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

    public boolean delete(DalDemand dalDemand) throws SQLException {

        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE ?=? AND ?=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,columnNames[0]);
            st.setInt(2,dalDemand.getItemID());
            st.setString(3,columnNames[2]);
            st.setInt(4,dalDemand.getSiteID());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalDemand> load () throws SQLException// Select From DB
    {
        LinkedList<DalDemand> demands = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery();
            while (resultSet.next()) {
                demands.add(new DalDemand(resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return demands;
    }

    public boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS Demands("
                +"itemID INTEGER,"
                +"amount INTEGER,"
                +"siteID INTEGER,"
                +"FOREIGN KEY (itemID) REFERENCES Items (ID) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"FOREIGN KEY (siteID) REFERENCES Sites (siteID) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"PRIMARY KEY (itemID , siteID));";
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
