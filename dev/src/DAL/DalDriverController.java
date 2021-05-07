package DAL;

import java.sql.*;
import java.util.LinkedList;

public class DalDriverController extends DalController{

    private DalDriverController(){
        //TODO - Check when tables created
        super();
        this.tableName="Drivers";
        this.columnNames=new String[6];
        columnNames[0]="ID";columnNames[1]="name";columnNames[2]="license";
    }

    public static DalDriverController getInstance() {
        return new DalDriverController();
    }


    public boolean insert(DalDriver dalDriver) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalDriver.getID());
            st.setString(2,dalDriver.getName());
            st.setString(3,dalDriver.getLicense());

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

    public boolean update(DalDriver dalDriver) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?,"+columnNames[2]+"=? WHERE ("+columnNames[0]+"= ?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,dalDriver.getName());
            st.setString(2,dalDriver.getLicense());
            st.setString(3,dalDriver.getID());
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

    public boolean delete(DalDriver dalDriver) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM " +tableName+ " WHERE ?=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,tableName);
            st.setString(2,columnNames[0]);
            st.setString(3,dalDriver.getID());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalDriver> load () throws SQLException// Select From DB
    {
        LinkedList<DalDriver> drivers = new LinkedList<>();
        Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query = "SELECT * FROM "+tableName;
        try {
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet resultSet = st.executeQuery(query);
            while (resultSet.next()) {
                boolean completed = resultSet.getString(4).equals("true");
                drivers.add(new DalDriver(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3)));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
        return drivers;
    }
}
