package DataAccessLayer.DalObjects;

import DataAccessLayer.DalControllers.DalController;

import java.sql.SQLException;

public abstract class DalObject<T extends DalObject<T>> {
    protected DalController<T> controller;

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
}
