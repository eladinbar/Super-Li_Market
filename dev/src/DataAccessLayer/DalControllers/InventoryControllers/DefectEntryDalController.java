package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.DefectEntry;

public class DefectEntryDalController extends DalController<DefectEntry> {
    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     * <param name="tableName">The table name of the object this controller represents.</param>
     *
     * @param tableName
     */
    public DefectEntryDalController(String tableName) {
        super(tableName);
    }

    @Override
    public void CreateTable() {

    }

    @Override
    public boolean Insert(DefectEntry dalObject) {
        return false;
    }

    @Override
    public boolean Delete(DefectEntry dalObject) {
        return false;
    }

    @Override
    public DefectEntry ConvertReaderToObject() {
        return null;
    }
}
