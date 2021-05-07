package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.DefectEntry;

import java.sql.SQLException;

public class DefectEntryDalController extends DalController<DefectEntry> {
    private static DefectEntryDalController instance = null;
    final static String DEFECT_ENTRY_TABLE_NAME = "Defect Entries";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private DefectEntryDalController() throws SQLException {
        super(DEFECT_ENTRY_TABLE_NAME);
    }

    public static DefectEntryDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new DefectEntryDalController();
        return instance;
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
