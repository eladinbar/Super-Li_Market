package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController$;
import DataAccessLayer.DalObjects.InventoryObjects.DalCategory;
import DataAccessLayer.DalObjects.InventoryObjects.DalCategorySale;

import java.sql.*;

import static DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController.CATEGORY_TABLE_NAME;

public class CategorySaleDalController extends DalController$<DalCategorySale> {
    private static CategorySaleDalController instance = null;
    public final static String CATEGORY_SALE_TABLE_NAME = "Category_Sales";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private CategorySaleDalController() throws SQLException {
        super(CATEGORY_SALE_TABLE_NAME);
    }

    public static CategorySaleDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new CategorySaleDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalCategorySale.categorySaleNameColumnName + " TEXT NOT NULL," +
                    DalCategorySale.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    DalCategorySale.startSaleDateColumnName + " TEXT NOT NULL," +
                    DalCategorySale.endSaleDateColumnName + " TEXT NOT NULL," +
                    DalCategorySale.categoryNameColumnName + " TEXT NOT NULL," +
                    "PRIMARY KEY (" + DalCategorySale.categorySaleNameColumnName + ")," +
                    "FOREIGN KEY (" + DalCategorySale.categoryNameColumnName + ")" +
                    "REFERENCES " + DalCategory.categoryNameColumnName + " (" + CATEGORY_TABLE_NAME + ") ON DELETE CASCADE" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalCategorySale categorySale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.setString(1, categorySale.getName());
            stmt.setDouble(2, categorySale.getDiscount());
            stmt.setString(3, categorySale.getStartSaleDate());
            stmt.setString(4, categorySale.getEndSaleDate());
            stmt.setString(5, categorySale.getCategoryName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalCategorySale categorySale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "DELETE FROM " + tableName + " WHERE (" + DalCategorySale.categorySaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categorySale.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalCategorySale categorySale) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalCategorySale.discountColumnName + "=?, " +
                    DalCategorySale.startSaleDateColumnName + "=?, " + DalCategorySale.endSaleDateColumnName + "=?, " +
                    DalCategorySale.categoryNameColumnName + "=? WHERE(" + DalCategorySale.categorySaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setDouble(1, categorySale.getDiscount());
            stmt.setString(2, categorySale.getStartSaleDate());
            stmt.setString(3, categorySale.getEndSaleDate());
            stmt.setString(4, categorySale.getCategoryName());
            stmt.setString(5, categorySale.getName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalCategorySale categorySale, String oldName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalCategorySale.categorySaleNameColumnName + "=?, " + DalCategorySale.discountColumnName + "=?, " +
                    DalCategorySale.startSaleDateColumnName + "=?, " + DalCategorySale.endSaleDateColumnName + "=?, " +
                    DalCategorySale.categoryNameColumnName + "=? WHERE(" + DalCategorySale.categorySaleNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categorySale.getName());
            stmt.setDouble(2, categorySale.getDiscount());
            stmt.setString(3, categorySale.getStartSaleDate());
            stmt.setString(4, categorySale.getEndSaleDate());
            stmt.setString(5, categorySale.getCategoryName());
            stmt.setString(6, oldName);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(DalCategorySale categorySale) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(categorySale.getName());
                if (isDesired) {
                    categorySale.setDiscount(resultSet.getInt(2));
                    categorySale.setStartSaleDate(resultSet.getString(3));
                    categorySale.setEndSaleDate(resultSet.getString(4));
                    categorySale.setCategoryName(resultSet.getString(5));
                    break; //Desired category sale found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
