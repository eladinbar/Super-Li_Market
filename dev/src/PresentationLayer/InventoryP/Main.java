package PresentationLayer.InventoryP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;

public class Main {
    public static void main(String[] args) throws Exception{
//            CategoryDalController controller = CategoryDalController.getInstance();
//            controller.createDBFile();
            System.out.println(System.getProperty("user.dir"));
            try(Connection conn = DriverManager.getConnection( "jdbc:sqlite:/"+System.getProperty("user.dir") + "\\" + "SuperLi" + ".db");
                Statement stmt = conn.createStatement();
            ) {
                System.out.println("Database created successfully...");
            } catch (SQLException e) {
                e.printStackTrace();
            }

//        PresentationController c = new PresentationController();
//        c.run();
    }
}
