package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.*;

import java.sql.*;

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
                    DefectEntry.itemIdColumnName + " INTEGER NOT NULL," +
                    DefectEntry.locationColumnName + " TEXT NOT NULL," +
                    DefectEntry.quantityColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    "PRIMARY KEY (" + DefectEntry.entryDateColumnName + ", " + DefectEntry.itemIdColumnName + ")," +
                    "FOREIGN KEY (" + DefectEntry.itemIdColumnName + ")" +
                    "REFERENCES " + Item.itemIdColumnName + " (" + ITEM_TABLE_NAME + ") ON DELETE NO ACTION" +
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
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, defectEntry.getEntryDate());
            stmt.setInt(2, defectEntry.getItemID());
            stmt.setString(3, defectEntry.getLocation());
            stmt.setInt(4, defectEntry.getQuantity());
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
            String command = "DELETE FROM " + tableName + " WHERE ("+ DefectEntry.entryDateColumnName + "=? AND " + DefectEntry.itemIdColumnName + "=?)";
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
    public boolean update(DefectEntry defectEntry) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DefectEntry.locationColumnName + "=?, " +
                    DefectEntry.quantityColumnName + "=? " +
                    "WHERE(" + DefectEntry.entryDateColumnName + "=? AND " + DefectEntry.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, defectEntry.getLocation());
            stmt.setInt(2, defectEntry.getQuantity());
            stmt.setString(3, defectEntry.getEntryDate());
            stmt.setInt(4, defectEntry.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DefectEntry defectEntry, String oldDate) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DefectEntry.entryDateColumnName + "=?, " + DefectEntry.locationColumnName + "=?, " +
                    DefectEntry.quantityColumnName + "=? " +
                    "WHERE(" + DefectEntry.entryDateColumnName + "=? AND " + DefectEntry.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, defectEntry.getEntryDate());
            stmt.setString(2, defectEntry.getLocation());
            stmt.setInt(3, defectEntry.getQuantity());
            stmt.setString(4, oldDate);
            stmt.setInt(5, defectEntry.getItemID());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DefectEntry defectEntry, int oldId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DefectEntry.itemIdColumnName + "=?, " + DefectEntry.locationColumnName + "=?, " +
                    DefectEntry.quantityColumnName + "=? " +
                    "WHERE(" + DefectEntry.entryDateColumnName + "=? AND " + DefectEntry.itemIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setInt(1, defectEntry.getItemID());
            stmt.setString(2, defectEntry.getLocation());
            stmt.setInt(3, defectEntry.getQuantity());
            stmt.setString(4, defectEntry.getEntryDate());
            stmt.setInt(5, oldId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public DefectEntry select(DefectEntry defectEntry) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                isDesired = resultSet.getString(0).equals(defectEntry.getEntryDate()) &&
                        resultSet.getInt(1) == defectEntry.getItemID();
                if (isDesired) {
                    defectEntry.setLocation(resultSet.getString(1));
                    defectEntry.setQuantity(resultSet.getInt(2));
                    break; //Desired defect entry found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired ? defectEntry : null;
    }
}
