package DAL;

public abstract class DalController {
    protected String connection;
    protected String tableName;
    protected String[] columnNames;

    public DalController()
    {
        connection=System.getProperty("user.dir")+"\\"+"database.db";
        connection="jdbc:sqlite:/"+connection;
    }
    // TODO need to decide whether we want to upload all data or upload only necessary data
    // TODO change connection

}
