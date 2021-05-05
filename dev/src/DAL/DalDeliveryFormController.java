package DAL;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class DalDeliveryFormController extends DalController {

    public DalDeliveryFormController(){
        //TODO - Check when tables created
        super();
        this.tableName="DeliveryForms";
        this.columnNames=new String[6];
        columnNames[0]="ID";columnNames[1]="origin";columnNames[2]="destination";
        columnNames[3]="completed";columnNames[4]="leavingWeight";columnNames[5]="TRID";
    }


    public boolean insert(DalDeliveryForm deliveryForm) throws SQLException {
        //TODO - change URL
        System.out.println("starting insert");
        Connection conn= DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query= "INSERT INTO "+tableName+" VALUES ? ? ? ? ? ?";
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
        int res=-1;
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="UPDATE "+tableName+" SET ?=?,?=?,?=?,?=?,?=? WHERE ?= ?";
        try{
            PreparedStatement st=conn.prepareStatement(query);

            st.setString(1,columnNames[1]);
            st.setInt(2,deliveryForm.getOrigin());

            st.setString(3,columnNames[2]);
            st.setInt(4,deliveryForm.getDestination());

            st.setString(5,columnNames[3]);
            st.setString(6,deliveryForm.getCompleted().toString());

            st.setString(7,columnNames[4]);
            st.setInt(8,deliveryForm.getLeavingWeight());

            st.setString(9,columnNames[5]);
            st.setInt(10,deliveryForm.getTRID());

            st.setString(11,columnNames[0]);
            st.setInt(12,deliveryForm.getID());
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

    public boolean delete(DalDeliveryForm deliveryForm) throws SQLException {
        boolean done=false;
        Connection conn=DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db");
        String query="DELETE FROM "+tableName+" WHERE ?=?";
        try {
            PreparedStatement st=conn.prepareStatement(query);
            st.setString(1,columnNames[0]);
            st.setInt(2,deliveryForm.getID());
            done=st.execute();
        }
        catch (SQLException e)
        {
            throw new SQLException(e.getMessage());
        }
        return done;
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
