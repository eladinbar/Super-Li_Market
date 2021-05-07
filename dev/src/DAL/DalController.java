package DAL;

public abstract class DalController {
    String connection;
    String tableName;
    String[] columnNames;

    public DalController()
    {
        connection=null;
    }
    // TODO need to decide whether we want to upload all data or upload only necessary data
    // TODO change connection

}
