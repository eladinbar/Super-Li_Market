package DAL;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class DalDeliveryFormController extends DalController {

    private DalDeliveryFormController(){
        //TODO - Check when tables created
        super();
        this.tableName="DeliveryForms";
        this.columnNames=new String[6];
        columnNames[0]="ID";columnNames[1]="origin";columnNames[2]="destination";
        columnNames[3]="completed";columnNames[4]="leavingWeight";columnNames[5]="TRID";
    }

     public static DalDeliveryFormController getInstance() {
        return new DalDeliveryFormController();
    }


    public boolean insert(DalDeliveryForm deliveryForm) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO "+tableName+" VALUES (?,?,?,?,?,?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);
            st.setInt(1,deliveryForm.getID());
            st.setInt(2,deliveryForm.getOrigin());
            st.setInt(3,deliveryForm.getDestination());
            st.setString(4,deliveryForm.getCompleted().toString());
            st.setInt(5,deliveryForm.getLeavingWeight());
            st.setInt(6,deliveryForm.getTRID());

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

    public boolean update(DalDeliveryForm deliveryForm) throws SQLException {
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE "+tableName+" SET "+columnNames[1]+"=?,"+columnNames[2]+"=?,"+columnNames[3]+"=?,"+columnNames[4]+"=?,"+
                columnNames[5]+"=? WHERE ("+columnNames[0]+"= ?)";
        try{
            PreparedStatement st=conn.prepareStatement(query);

            st.setInt(1,deliveryForm.getOrigin());
            st.setInt(2,deliveryForm.getDestination());
            st.setString(3,deliveryForm.getCompleted().toString());
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
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM "+tableName+" WHERE ?=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,columnNames[0]);
            st.setInt(2,deliveryForm.getID());
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
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="SELECT * FROM "+tableName;
        try {
            PreparedStatement st=conn.prepareStatement(query);
            ResultSet resultSet=st.executeQuery(query);
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
}
