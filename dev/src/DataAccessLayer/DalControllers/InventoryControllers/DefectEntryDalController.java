package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController$;
import DataAccessLayer.DalObjects.InventoryObjects.*;

import java.sql.*;
import java.util.List;

import static DataAccessLayer.DalControllers.InventoryControllers.ItemDalController.ITEM_TABLE_NAME;

public class DefectEntryDalController extends DalController$<DalDefectEntry> {
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
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalDefectEntry.entryDateColumnName + " TEXT NOT NULL," +
                    DalDefectEntry.itemIdColumnName + " INTEGER NOT NULL," +
                    DalDefectEntry.itemNameColumnName + " TEXT NOT NULL," +
                    DalDefectEntry.locationColumnName + " TEXT NOT NULL," +
                    DalDefectEntry.quantityColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    "PRIMARY KEY (" + DalDefectEntry.entryDateColumnName + ", " + DalDefectEntry.itemIdColumnName + ")," +
                    "FOREIGN KEY (" + DalDefectEntry.itemIdColumnName + ")" +
                    "REFERENCES " + DalItem.itemIdColumnName + " (" + ITEM_TABLE_NAME + ") ON DELETE NO ACTION" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalDefectEntry defectEntry) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, defectEntry.getEntryDate());
            stmt.setInt(2, defectEntry.getItemID());
            stmt.setString(3, defectEntry.getItemName());
            stmt.setString(4, defectEntry.getLocation());
            stmt.setInt(5, defectEntry.getQuantity());

            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalDefectEntry defectEntry) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "DELETE FROM " + tableName + " WHERE ("+ DalDefectEntry.entryDateColumnName + "=? AND " + DalDefectEntry.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, defectEntry.getEntryDate());
            stmt.setInt(2, defectEntry.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalDefectEntry defectEntry) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalDefectEntry.itemNameColumnName + "=?, " +
                    DalDefectEntry.locationColumnName + "=?, " + DalDefectEntry.quantityColumnName + "=? " +
                    "WHERE(" + DalDefectEntry.entryDateColumnName + "=? AND " + DalDefectEntry.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, defectEntry.getItemName());
            stmt.setString(2, defectEntry.getLocation());
            stmt.setInt(3, defectEntry.getQuantity());
            stmt.setString(4, defectEntry.getEntryDate());
            stmt.setInt(5, defectEntry.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalDefectEntry defectEntry, String oldDate) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalDefectEntry.entryDateColumnName + "=?, " +
                    DalDefectEntry.itemNameColumnName + "=?, " + DalDefectEntry.locationColumnName + "=?, " + DalDefectEntry.quantityColumnName + "=? " +
                    "WHERE(" + DalDefectEntry.entryDateColumnName + "=? AND " + DalDefectEntry.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, defectEntry.getEntryDate());
            stmt.setString(2, defectEntry.getItemName());
            stmt.setString(3, defectEntry.getLocation());
            stmt.setInt(4, defectEntry.getQuantity());
            stmt.setString(5, oldDate);
            stmt.setInt(6, defectEntry.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalDefectEntry defectEntry, int oldId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalDefectEntry.itemIdColumnName + "=?, " + DalDefectEntry.itemNameColumnName + "=?, " +
                    DalDefectEntry.locationColumnName + "=?, " + DalDefectEntry.quantityColumnName + "=? " +
                    "WHERE(" + DalDefectEntry.entryDateColumnName + "=? AND " + DalDefectEntry.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setInt(1, defectEntry.getItemID());
            stmt.setString(2, defectEntry.getItemName());
            stmt.setString(3, defectEntry.getLocation());
            stmt.setInt(4, defectEntry.getQuantity());
            stmt.setString(5, defectEntry.getEntryDate());
            stmt.setInt(6, oldId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(DalDefectEntry defectEntry) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(defectEntry.getEntryDate()) &&
                        resultSet.getInt(2) == defectEntry.getItemID();
                if (isDesired) {
                    defectEntry.setItemName(resultSet.getString(3));
                    defectEntry.setLocation(resultSet.getString(4));
                    defectEntry.setQuantity(resultSet.getInt(5));
                    break; //Desired defect entry found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    @Override
    public boolean select(List<DalDefectEntry> defectEntries) throws SQLException {
        DalDefectEntry savedDefectEntry;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                savedDefectEntry = new DalDefectEntry();
                savedDefectEntry.setEntryDate(resultSet.getString(1));
                savedDefectEntry.setItemID(resultSet.getInt(2));
                savedDefectEntry.setItemName(resultSet.getString(3));
                savedDefectEntry.setLocation(resultSet.getString(4));
                savedDefectEntry.setQuantity(resultSet.getInt(5));
                defectEntries.add(savedDefectEntry);
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }
}
