package DataAccessLayer.DalControllers;

import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

/** <summary>
An abstract class used as the basis to form connections with the database for reading and writing purposes.
</summary> */
public abstract class DalController<T extends DalObject<T>>{
    protected String tableName;
    protected final String databaseName = "SuperLi";
    protected final String path = System.getProperty("user.dir") + "\\" + databaseName + ".db";
    protected final String connectionString = "jdbc:sqlite:/" + path;

    /** <summary>
    A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
    </summary>
    <param name="tableName">The table name of the object this controller represents.</param> */
    protected DalController(String tableName) throws SQLException {
        this.tableName = tableName;
        createTable();
    }

    //methods

    /** <summary>
    Creates a database table with the name initialized in the field 'tableName'.
    </summary>
     * @return*/
    public abstract boolean createTable() throws SQLException;

    /** <summary>
    Inserts the given object into its respective table in the database.
    </summary>
    <param name="dalObject">The data access layer object instance to insert into the database.</param>
    <returns>Returns true if the method changed more than 0 rows.</returns> */
    public abstract boolean insert(T dalObject) throws SQLException;

    /** <summary>
    Deletes the given object from its respective table in the database.
    </summary>
    <param name="dalObject">The data access layer object instance to delete from the database.</param>
    <returns>Returns true if the method changed more than 0 rows.</returns> */
    public abstract boolean delete(T dalObject) throws SQLException;

    /** <summary>
     Updates the column in the database associated with the given DalObject and sets the value.
     </summary>
     <param name="dalObject">The object to select from the database.</param>
     <returns>Returns a DalObject that extends DalObject<T>.</returns> */
    public abstract boolean update(T dalObject) throws SQLException;

    /** <summary>
     Updates the column in the database associated with the given DalObject and sets the value.
     </summary>
     <param name="dalObject">The object to select from the database.</param>
     <param name="oldName">The old name of the object to update.</param>
     <returns>Returns a DalObject that extends DalObject<T>.</returns> */
    public boolean update(T dalObject, String oldName) throws SQLException {
        return false;
    }

    /** <summary>
     Updates the column in the database associated with the given DalObject and sets the value.
     </summary>
     <param name="dalObject">The object to select from the database.</param>
     <param name="oldId">The old ID of the object to update.</param>
     <returns>Returns a DalObject that extends DalObject<T>.</returns> */
    public boolean update(T dalObject, int oldId) throws SQLException {
        return false;
    }

    /** <summary>
    A select command function to extract an object from database into RAM.
    </summary>
     <param name="dalObject">The object to select from the database.</param>
     <returns>Returns a DalObject that extends DalObject<T>.</returns> */
    public abstract T select(T dalObject) throws SQLException;
}
