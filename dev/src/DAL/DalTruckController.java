package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalTruckController extends DalController{

    public DalTruckController(){//TODO - Check when tables created
        super();
        this.tableName="Trucks";
        this.columnNames=new String[4];
        columnNames[1]="model";columnNames[0]="licenseNumber";columnNames[2]="weightNeto";
        columnNames[3]="maxWeight";}


    public boolean insert(DalTruck dalTruck) throws SQLException {
        //TODO - change URL
        boolean done=false;
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO ? VALUES ? ? ? ?  ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setString(2,dalTruck.getLicenseNumber());
            st.setString(3,dalTruck.getModel());
            st.setInt(4,dalTruck.getWeightNeto());
            st.setInt(5,dalTruck.getMaxWeight());

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

    public boolean update(DalTruck dalTruck) throws SQLException {
        int res=-1;
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE ? SET ?=?,?=?,?=? WHERE ?= ? ";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);

            st.setString(2,columnNames[1]);
            st.setString(3,dalTruck.getModel());

            st.setString(4,columnNames[2]);
            st.setInt(5,dalTruck.getWeightNeto());

            st.setString(6,columnNames[3]);
            st.setInt(7,dalTruck.getMaxWeight());

            st.setString(8,columnNames[0]);
            st.setString(9,dalTruck.getLicenseNumber());

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

    public boolean delete(DalTruck dalTruck) throws SQLException {
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM ? WHERE ?=? ";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setString(2,columnNames[0]);
            st.setString(3,dalTruck.getLicenseNumber());
            done=st.execute();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return done;
    }
    public LinkedList<DalTruck> load () throws SQLException// Select From DB
    {
        LinkedList<DalTruck> trucks = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM ?";
        try {
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, tableName);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                boolean completed = resultSet.getString(4).equals("true");
                trucks.add(new DalTruck(resultSet.getString(1),resultSet.getString(2),
                        resultSet.getInt(3),resultSet.getInt(4)                    )
                );
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return trucks;
    }
}
