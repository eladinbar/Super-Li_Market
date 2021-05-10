package DAL.DalControllers_Trucking;

import DAL.DalController;
import DAL.DalObjects_Trucking.DalDeliveryForm;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class DalDeliveryFormController extends DalController {

    private static DalDeliveryFormController controller;

    private DalDeliveryFormController(){
        //TODO - Check when tables created
        super();
        this.tableName="DeliveryForms";
        this.columnNames=new String[6];
        columnNames[0]="ID";columnNames[1]="origin";columnNames[2]="destination";
        columnNames[3]="completed";columnNames[4]="leavingWeight";columnNames[5]="TRID";
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
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,deliveryForm.getID());
            st.setInt(2,deliveryForm.getOrigin());
            st.setInt(3,deliveryForm.getDestination());
            st.setInt(4,completed);
            st.setInt(5,deliveryForm.getLeavingWeight());
            st.setInt(6,deliveryForm.getTRID());

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
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?,"+columnNames[2]+"=?,"+columnNames[3]+"=?,"+columnNames[4]+"=?,"+
                columnNames[5]+"=? WHERE ("+columnNames[0]+"= ?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);

            st.setInt(1,deliveryForm.getOrigin());
            st.setInt(2,deliveryForm.getDestination());
            st.setInt(3,completed);
            st.setInt(4,deliveryForm.getLeavingWeight());
            st.setInt(5,deliveryForm.getTRID());
            st.setInt(6,deliveryForm.getID());
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
                reports.add(new DalDeliveryForm(resultSet.getInt(1),resultSet.getInt(2),
                        resultSet.getInt(3),completed,resultSet.getInt(4),
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
        Connection conn = DriverManager.getConnection(connection);
        String query = "CREATE TABLE IF NOT EXISTS DeliveryForms("
                +"ID INTEGER,"
                +"origin INTEGER,"
                +"destination INTEGER,"
                +"completed INTEGER,"
                +"leavingWeight INTEGER,"
                +"TRID INTEGER,"
                +"FOREIGN KEY (TRID) REFERENCES TruckingReports(ID) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"FOREIGN KEY (origin) REFERENCES Sites (siteID) ON DELETE NO ACTION ON UPDATE CASCADE,"
                +"FOREIGN KEY (destination) REFERENCES Sites (siteID) ON DELETE NO ACTION ON UPDATE CASCADE,"

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
