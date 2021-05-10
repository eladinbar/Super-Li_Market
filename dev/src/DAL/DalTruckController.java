package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalTruckController extends DalController{

    private static DalTruckController controller;

    private DalTruckController(){
        super();
        this.tableName="Trucks";
        this.columnNames=new String[4];
        columnNames[1]="model";columnNames[0]="licenseNumber";columnNames[2]="weightNeto";
        columnNames[3]="maxWeight";}

    public static DalTruckController getInstance() throws SQLException {
        if (controller==null) {
            controller = new DalTruckController();
            controller.createTable();
        }
        return controller;
    }

    public boolean insert(DalTruck dalTruck) throws SQLException {
        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalTruck.getLicenseNumber());
            st.setString(2,dalTruck.getModel());
            st.setInt(3,dalTruck.getWeightNeto());
            st.setInt(4,dalTruck.getMaxWeight());
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

    public boolean update(DalTruck dalTruck) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?, "+columnNames[2]+"=?, "+columnNames[3]+"=? WHERE ("+columnNames[0]+"=?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);

            st.setString(1,dalTruck.getModel());
            st.setInt(2,dalTruck.getWeightNeto());
            st.setInt(3,dalTruck.getMaxWeight());
            st.setString(4,dalTruck.getLicenseNumber());

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

    public boolean delete(DalTruck dalTruck) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE"+columnNames[0]+ "=? ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalTruck.getLicenseNumber());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalTruck> load () throws SQLException// Select From DB
    {
        LinkedList<DalTruck> trucks = new LinkedList<>();
        Connection conn = DriverManager.getConnection(connection);
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                trucks.add(new DalTruck(resultSet.getString(1),resultSet.getString(2),
                        resultSet.getInt(3),resultSet.getInt(4)                    )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return trucks;
    }

    public boolean createTable() throws SQLException {
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS Trucks("
                +"licenseNumber TEXT,"
                +"model TEXT,"
                +"weightNeto INTEGER,"
                +"maxWeight INTEGER,"
                +"PRIMARY KEY (licenseNumber));";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            System.out.println("Creating\n");
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
