package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController.CATEGORY_TABLE_NAME;
import static DataAccessLayer.DalControllers.InventoryControllers.ItemDalController.ITEM_TABLE_NAME;

public class DefectEntryDalController extends DalController<DefectEntry> {
    private static DefectEntryDalController instance = null;
    public final static String DEFECT_ENTRY_TABLE_NAME = "Defect_Entries";

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
    public void CreateTable() throws SQLException {
        System.out.println("Initiating create '" + DEFECT_ENTRY_TABLE_NAME + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + DEFECT_ENTRY_TABLE_NAME + " (" +
                    DefectEntry.entryDateColumnName + " TEXT NOT NULL," +
                    DefectEntry.locationColumnName + " TEXT NOT NULL," +
                    DefectEntry.quantityColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    DefectEntry.itemIdColumnName + " INTEGER NOT NULL," +
                    "PRIMARY KEY (" + DefectEntry.entryDateColumnName + ")," +
                    "FOREIGN KEY (" + DefectEntry.itemIdColumnName + ")" +
                    "REFERENCES " + Item.itemIdColumnName + " (" + ITEM_TABLE_NAME + ") ON DELETE CASCADE," +
                    "CONSTRAINT Natural_Number CHECK (" + DefectEntry.quantityColumnName + ">=0)" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            System.out.println("Creating '" + DEFECT_ENTRY_TABLE_NAME + "' table.");
            stmt.executeUpdate();
            System.out.println("Table '" + DEFECT_ENTRY_TABLE_NAME + "' created.");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
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
