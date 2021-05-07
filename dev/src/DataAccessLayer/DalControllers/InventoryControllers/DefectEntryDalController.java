package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.DefectEntry;

public class DefectEntryDalController extends DalController<DefectEntry> {
    final static String DEFECT_ENTRY_TABLE_NAME = "Defect Entries";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    public DefectEntryDalController() {
        super(DEFECT_ENTRY_TABLE_NAME);
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
