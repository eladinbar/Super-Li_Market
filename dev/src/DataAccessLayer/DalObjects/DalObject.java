package DataAccessLayer.DalObjects;

import DataAccessLayer.DalControllers.DalController;

public abstract class DalObject<T extends DalObject<T>> {
    protected DalController<T> controller;

    /// <summary>
    /// A protected constructor that initializes the given controller.
    /// </summary>
    /// <param name="controller">The respective controller of the DalObject.</param>
    protected DalObject(DalController<T> controller)
    {
        controller = controller;
    }

    /// <summary>
    /// Inserts 'this' into the database.
    /// </summary>
    /// <returns>Returns true if 'Insert' was successful.</returns>
    public boolean Save()
    {
        return controller.Insert((T) this);
    }

    /// <summary>
    /// Deletes the equivalent row of 'this' in the database.
    /// </summary>
    /// <returns>Returns true if the row was removed successfully.</returns>
    public boolean Delete()
    {
        return controller.Delete((T) this);
    }
}
