package DataAccessLayer.DalControllers;

import java.sql.SQLException;

public abstract class Employee_Trucking_DALController_Interface {
    protected String connection;
    protected String tableName;
    protected String[] columnNames;

    public Employee_Trucking_DALController_Interface()
    {
        connection=System.getProperty("user.dir")+"\\"+"database.db";
        connection="jdbc:sqlite:/"+connection;
    }

    protected abstract boolean createTable () throws SQLException ;
}