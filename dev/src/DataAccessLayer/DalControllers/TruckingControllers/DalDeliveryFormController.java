package DataAccessLayer.DalControllers.TruckingControllers;

import DataAccessLayer.DalControllers.Employee_Trucking_DALController_Interface;
import DataAccessLayer.DalObjects.TruckingObjects.DalDeliveryForm;

import java.sql.*;
import java.util.LinkedList;

public class DalDeliveryFormController extends Employee_Trucking_DALController_Interface {



    private static DalDeliveryFormController controller;

    private DalDeliveryFormController(){

        super();
        this.tableName="DeliveryForms";
        this.columnNames=new String[5];
        columnNames[0]="ID";columnNames[1]="destination";
        columnNames[2]="completed";columnNames[3]="leavingWeight";columnNames[4]="TRID";
    }

     public static DalDeliveryFormController getInstance() throws SQLException {
         if (controller==null) {
             controller = new DalDeliveryFormController();
             controller.createTable();
         }
         return controller;
    }


    public boolean insert(DalDeliveryForm deliveryForm) throws SQLException {
        int completed=0;
        if (deliveryForm.isCompleted())
            completed=1;
        Connection conn= DriverManager.getConnection(connection);
        String query= "INSERT OR IGNORE INTO "+tableName+" VALUES (?,?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,deliveryForm.getID());
            st.setInt(2,deliveryForm.getDestination());
            st.setInt(3,completed);
            st.setInt(4,deliveryForm.getLeavingWeight());
            st.setInt(5,deliveryForm.getTRID());

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

    public boolean update(DalDeliveryForm deliveryForm) throws SQLException {
        int completed=0;
        if (deliveryForm.isCompleted())
            completed=1;
        Connection conn=DriverManager.getConnection(connection);
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?,"+columnNames[2]+"=?,"+columnNames[3]+"=?,"+columnNames[4]+"=?"+ "WHERE ("+columnNames[0]+"= ?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);

            st.setInt(1,deliveryForm.getDestination());
            st.setInt(2,completed);
            st.setInt(3,deliveryForm.getLeavingWeight());
            st.setInt(4,deliveryForm.getTRID());
            st.setInt(5,deliveryForm.getID());
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

    public boolean delete(DalDeliveryForm deliveryForm) throws SQLException {
        Connection conn=DriverManager.getConnection(connection);
        String query="DELETE FROM "+tableName+" WHERE("+columnNames[0]+"=?)";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,deliveryForm.getID());
            st.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return true;
    }
    public LinkedList<DalDeliveryForm> load () throws SQLException// Select From DB
    {
        LinkedList<DalDeliveryForm> reports=new LinkedList<>();
        Connection conn=DriverManager.getConnection(connection);
        String query="SELECT * FROM "+tableName;
        try {
            PreparedStatement st=conn.prepareStatement(query);
            ResultSet resultSet=st.executeQuery();
            while (resultSet.next())
            {
                boolean completed=resultSet.getString(4).equals("true");
                reports.add(new DalDeliveryForm(resultSet.getInt(1),
                        resultSet.getInt(2),completed,resultSet.getInt(3),
                        resultSet.getInt(5)));
            }
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return reports;
    }
    public boolean createTable() throws SQLException {
        //TODO - Check exactName in supplierCard
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS DeliveryForms("
                +"ID INTEGER,"
                +"destination INTEGER,"
                +"completed INTEGER,"
                +"leavingWeight INTEGER,"
                +"TRID INTEGER,"
                +"FOREIGN KEY (TRID) REFERENCES TruckingReports(ID) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"FOREIGN KEY (destination) REFERENCES SupplierCard (siteID) ON DELETE NO ACTION ON UPDATE CASCADE,"

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
