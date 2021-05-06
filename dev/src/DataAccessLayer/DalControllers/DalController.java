package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalObject;

/// <summary>
/// An abstract class used as the basis to form connections with the database for reading and writing purposes.
/// </summary>
public abstract class DalController<T extends DalObject<T>>{
    protected String connectionString;
    protected String tableName;
    protected final String databaseName = "SuperLi.db";

    /// <summary>
    /// A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
    /// </summary>
    /// <param name="tableName">The table name of the object this controller represents.</param>
    public DalController(String tableName)
    {
//        string path = Path.GetFullPath(Path.Combine(Directory.GetCurrentDirectory(), _databaseName));
//        _connectionString = $"Data Source={path}; Version=3;";
//        _tableName = tableName;
//        CreateTable();
    }

    //abstract methods

    /// <summary>
    /// Creates a database table with the name initialized in the field '_tableName'.
    /// </summary>
    abstract void CreateTable();

    /// <summary>
    /// Inserts the given object into its respective table in the database.
    /// </summary>
    /// <param name="dalObject">The data access layer object instance to insert into the database.</param>
    /// <returns>Returns true if the method changed more than 0 rows.</returns>
    public abstract boolean Insert(T dalObject);

    /// <summary>
    /// Deletes the given object from its respective table in the database.
    /// </summary>
    /// <param name="dalObject">The data access layer object instance to delete from the database.</param>
    /// <returns>Returns true if the method changed more than 0 rows.</returns>
    public abstract boolean Delete(T dalObject);

    /// <summary>
    /// Converts an SQLiteDataReader to a DalObject.
    /// </summary>
    /// <param name="reader">The SQLite reader to convert.</param>
    /// <returns>Returns a DalObject that extends DalObject<T>.</returns>
    abstract T ConvertReaderToObject(/*SQLiteDataReader reader*/);
}
