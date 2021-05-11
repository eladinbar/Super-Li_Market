package DAL;

import java.sql.SQLException;

public abstract class DalController {
    protected String connection;
    protected String tableName;
    protected String[] columnNames;

    public DalController()
    {
        connection=System.getProperty("user.dir")+"\\"+"database.db";
        connection="jdbc:sqlite:/"+connection;
    }

    protected abstract boolean createTable () throws SQLException ;
}
