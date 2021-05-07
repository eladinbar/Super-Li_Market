package DAL;

public abstract class DalController {
    String connection;
    String tableName;
    String[] columnNames;

    public DalController()
    {
        connection=null;
    }

}
