package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.DalCategory;
import DataAccessLayer.DalObjects.InventoryObjects.DalCategoryDiscount;
import DataAccessLayer.DalObjects.SupplierObjects.DalSupplierCard;

import java.sql.*;
import java.util.List;

import static DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController.CATEGORY_TABLE_NAME;
import static DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME;

public class CategoryDiscountDalController extends DalController<DalCategoryDiscount> {
    private static CategoryDiscountDalController instance = null;
    final static String CATEGORY_DISCOUNT_TABLE_NAME = "Category_Discounts";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private CategoryDiscountDalController() throws SQLException {
        super(CATEGORY_DISCOUNT_TABLE_NAME);
    }

    public static CategoryDiscountDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new CategoryDiscountDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalCategoryDiscount.discountDateColumnName + " TEXT NOT NULL," +
                    DalCategoryDiscount.supplierIdColumnName + " TEXT NOT NULL," +
                    DalCategoryDiscount.categoryNameColumnName + " TEXT NOT NULL," +
                    DalCategoryDiscount.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    DalCategoryDiscount.itemCountColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    "PRIMARY KEY (" + DalCategoryDiscount.discountDateColumnName + ", " + DalCategoryDiscount.supplierIdColumnName + "," +
                    DalCategoryDiscount.categoryNameColumnName + ")," +
                    "FOREIGN KEY (" + DalCategoryDiscount.supplierIdColumnName + ")" +
                    "REFERENCES " + DalSupplierCard.supplierIdColumnName + " (" + SUPPLIER_CARD_TABLE_NAME + ") ON DELETE NO ACTION," +
                    "FOREIGN KEY (" + DalCategoryDiscount.categoryNameColumnName + ")" +
                    "REFERENCES " + DalCategory.categoryNameColumnName + " (" + CATEGORY_TABLE_NAME + ") ON DELETE NO ACTION" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalCategoryDiscount categoryDiscount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categoryDiscount.getDiscountDate());
            stmt.setString(2, categoryDiscount.getSupplierID());
            stmt.setString(3, categoryDiscount.getCategoryName());
            stmt.setDouble(4, categoryDiscount.getDiscount());
            stmt.setInt(5, categoryDiscount.getItemCount());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalCategoryDiscount categoryDiscount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "DELETE FROM " + tableName + " WHERE (" + DalCategoryDiscount.discountDateColumnName + "=? AND " + DalCategoryDiscount.supplierIdColumnName +
                    "=? AND " + DalCategoryDiscount.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categoryDiscount.getDiscountDate());
            stmt.setString(2, categoryDiscount.getSupplierID());
            stmt.setString(3, categoryDiscount.getCategoryName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalCategoryDiscount categoryDiscount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalCategoryDiscount.discountColumnName + "=?, " + DalCategoryDiscount.itemCountColumnName +
                     "=? WHERE(" + DalCategoryDiscount.discountDateColumnName + "=? AND " + DalCategoryDiscount.supplierIdColumnName
                    + "=? AND " + DalCategoryDiscount.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setDouble(1, categoryDiscount.getDiscount());
            stmt.setInt(2, categoryDiscount.getItemCount());
            stmt.setString(3, categoryDiscount.getDiscountDate());
            stmt.setString(4, categoryDiscount.getSupplierID());
            stmt.setString(5, categoryDiscount.getCategoryName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalCategoryDiscount categoryDiscount, String oldField) throws SQLException {
        if (extractType(oldField) == 1)
            return updateDate(categoryDiscount, oldField);
        else if (extractType(oldField) == 2)
            return updateID(categoryDiscount, oldField);
        else //extractType(oldField) == 3
            return updateName(categoryDiscount, oldField);
    }

    private boolean updateDate(DalCategoryDiscount categoryDiscount, String oldDate) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalCategoryDiscount.discountDateColumnName + "=?, " +
                    DalCategoryDiscount.discountColumnName + "=?, " + DalCategoryDiscount.itemCountColumnName +
                    "=? WHERE(" + DalCategoryDiscount.discountDateColumnName + "=? AND " + DalCategoryDiscount.supplierIdColumnName
                    + "=? AND " + DalCategoryDiscount.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categoryDiscount.getDiscountDate());
            stmt.setDouble(2, categoryDiscount.getDiscount());
            stmt.setInt(3, categoryDiscount.getItemCount());
            stmt.setString(4, oldDate);
            stmt.setString(5, categoryDiscount.getSupplierID());
            stmt.setString(6, categoryDiscount.getCategoryName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    public boolean updateID(DalCategoryDiscount categoryDiscount, String oldId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalCategoryDiscount.supplierIdColumnName + "=?, " +
                    DalCategoryDiscount.discountColumnName + "=?, " + DalCategoryDiscount.itemCountColumnName +
                    "=? WHERE(" + DalCategoryDiscount.discountDateColumnName + "=? AND " + DalCategoryDiscount.supplierIdColumnName
                    + "=? AND " + DalCategoryDiscount.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categoryDiscount.getSupplierID());
            stmt.setDouble(2, categoryDiscount.getDiscount());
            stmt.setInt(3, categoryDiscount.getItemCount());
            stmt.setString(4, categoryDiscount.getDiscountDate());
            stmt.setString(5, oldId);
            stmt.setString(6, categoryDiscount.getCategoryName());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    public boolean updateName(DalCategoryDiscount categoryDiscount, String oldName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + DalCategoryDiscount.categoryNameColumnName + "=?, " +
                    DalCategoryDiscount.discountColumnName + "=?, " + DalCategoryDiscount.itemCountColumnName +
                    "=? WHERE(" + DalCategoryDiscount.discountDateColumnName + "=? AND " + DalCategoryDiscount.supplierIdColumnName
                    + "=? AND " + DalCategoryDiscount.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categoryDiscount.getCategoryName());
            stmt.setDouble(2, categoryDiscount.getDiscount());
            stmt.setInt(3, categoryDiscount.getItemCount());
            stmt.setString(4, categoryDiscount.getDiscountDate());
            stmt.setString(5, categoryDiscount.getSupplierID());
            stmt.setString(6, oldName);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    private int extractType(String oldParameter) { //Assumes valid input from business layer
        if (oldParameter.indexOf("-") == 4)
            return 1; //Parameter represents date
        else if(oldParameter.charAt(0) >= 48 & oldParameter.charAt(0) <= 57)
            return 2; //Parameter represents supplier id
        else
            return 3; //Parameter represents category name
    }

    @Override
    public boolean select(DalCategoryDiscount categoryDiscount) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(categoryDiscount.getDiscountDate()) &&
                        resultSet.getString(2).equals(categoryDiscount.getSupplierID()) && resultSet.getString(3).equals(categoryDiscount.getCategoryName());
                if (isDesired) {
                    categoryDiscount.setDiscount(resultSet.getInt(4));
                    categoryDiscount.setItemCount(resultSet.getInt(5));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }

    @Override
    public boolean select(DalCategoryDiscount categoryDiscount, List<DalCategoryDiscount> categoryDiscounts) throws SQLException {
        boolean hasItem = false;
        DalCategoryDiscount savedCategoryDiscount;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(1).equals(categoryDiscount.getDiscountDate()) &&
                resultSet.getString(2).equals(categoryDiscount.getSupplierID());
                if (!hasItem & isDesired)
                    hasItem = true;
                if (isDesired) {
                    savedCategoryDiscount = new DalCategoryDiscount();
                    savedCategoryDiscount.setDiscountDate(resultSet.getString(1));
                    savedCategoryDiscount.setSupplierID(resultSet.getString(2));
                    savedCategoryDiscount.setCategoryName(resultSet.getString(3));
                    savedCategoryDiscount.setDiscount(resultSet.getInt(4));
                    savedCategoryDiscount.setItemCount(resultSet.getInt(5));
                    categoryDiscounts.add(savedCategoryDiscount);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return hasItem;
    }
}
