package DataAccessLayer.DalObjects;

import DataAccessLayer.DalControllers.DalController;

import java.sql.SQLException;
import java.util.List;

public abstract class DalObject<T extends DalObject<T>> {
    protected DalController<T> controller;

    public DalObject() {

    }

    /// <summary>
    /// A protected constructor that initializes the given controller.
    /// </summary>
    /// <param name="controller">The respective controller of the DalObject.</param>
    protected DalObject(DalController<T> controller)
    {
        this.controller = controller;
    }

    /// <summary>
    /// Inserts 'this' into the database.
    /// </summary>
    /// <returns>Returns true if 'Insert' was successful.</returns>
    public boolean save() throws SQLException {
        return controller.insert((T) this);
    }

    /// <summary>
    /// Deletes the equivalent row of 'this' in the database.
    /// </summary>
    /// <returns>Returns true if the row was removed successfully.</returns>
    public boolean delete() throws SQLException {
        return controller.delete((T) this);
    }

    /// <summary>
    /// Retrieves the equivalent row of 'this' from the database.
    /// </summary>
    /// <returns>Returns true if the row was removed successfully.</returns>
    public boolean find() throws SQLException {
        return controller.select((T) this);
    }

    /// <summary>
    /// Retrieves the equivalent row of 'this' from the database.
    /// </summary>
    /// <returns>Returns true if the row was removed successfully.</returns>
    public boolean find(List<T> dalObjects) throws SQLException {
        return controller.select((T) this, dalObjects);
    }
}
