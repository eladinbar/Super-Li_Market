package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
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
            System.out.println("Creating '" + tableName + "' table.");
            stmt.executeUpdate();
            System.out.println("Table '" + tableName + "' created.");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DefectEntry defectEntry) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, defectEntry.getEntryDate());
            stmt.setString(2, defectEntry.getLocation());
            stmt.setInt(3, defectEntry.getQuantity());
            stmt.setInt(4, defectEntry.getItemID());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DefectEntry defectEntry) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE ?=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, DefectEntry.entryDateColumnName);
            stmt.setString(2, defectEntry.getEntryDate());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DefectEntry defectEntry) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + DefectEntry.locationColumnName + "=?, " +
                    DefectEntry.quantityColumnName + "=?, " + DefectEntry.itemIdColumnName +
                    "=? WHERE(" + DefectEntry.entryDateColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, defectEntry.getLocation());
            stmt.setInt(2, defectEntry.getQuantity());
            stmt.setInt(3, defectEntry.getItemID());
            stmt.setString(4, defectEntry.getEntryDate());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public DefectEntry select(DefectEntry defectEntry) {
        return null;
    }
}
