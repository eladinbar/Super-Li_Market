package DAL;

public abstract class DalController {
   protected String connection;
    protected String tableName;
    protected String[] columnNames;

    public DalController()
    {
        connection=null;
    }

}
