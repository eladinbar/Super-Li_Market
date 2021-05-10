package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;
import DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCardDal;

import java.sql.*;
import java.util.List;

import static DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController.CATEGORY_TABLE_NAME;
import static DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME;

public class CategoryDiscountDalController extends DalController<CategoryDiscount> {
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
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    CategoryDiscount.discountDateColumnName + " TEXT NOT NULL," +
                    CategoryDiscount.supplierIdColumnName + " TEXT NOT NULL," +
                    CategoryDiscount.categoryNameColumnName + " TEXT NOT NULL," +
                    CategoryDiscount.discountColumnName + " REAL DEFAULT 0 NOT NULL," +
                    CategoryDiscount.itemCountColumnName + " INTEGER DEFAULT 0 NOT NULL," +
                    "PRIMARY KEY (" + CategoryDiscount.discountDateColumnName + ", " + CategoryDiscount.supplierIdColumnName + "," +
                    CategoryDiscount.categoryNameColumnName + ")," +
                    "FOREIGN KEY (" + CategoryDiscount.supplierIdColumnName + ")" +
                    "REFERENCES " + SupplierCardDal.supplierIdColumnName + " (" + SUPPLIER_CARD_TABLE_NAME + ") ON DELETE NO ACTION," +
                    "FOREIGN KEY (" + CategoryDiscount.categoryNameColumnName + ")" +
                    "REFERENCES " + Category.categoryNameColumnName + " (" + CATEGORY_TABLE_NAME + ") ON DELETE NO ACTION" +
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
    public boolean insert(CategoryDiscount categoryDiscount) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "INSERT OR IGNORE INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(command);

            stmt.setString(1, categoryDiscount.getDiscountDate());
            stmt.setString(2, categoryDiscount.getSupplierID());
            stmt.setString(3, categoryDiscount.getCategoryName());
            stmt.setDouble(4, categoryDiscount.getDiscount());
            stmt.setInt(5, categoryDiscount.getItemCount());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(CategoryDiscount categoryDiscount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "DELETE FROM " + tableName + " WHERE (" + CategoryDiscount.discountDateColumnName + "=? AND " + CategoryDiscount.supplierIdColumnName +
                    "=? AND " + CategoryDiscount.categoryNameColumnName + "=?)";
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
    public boolean update(CategoryDiscount categoryDiscount) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + CategoryDiscount.discountColumnName + "=?, " + CategoryDiscount.itemCountColumnName +
                     "=? WHERE(" + CategoryDiscount.discountDateColumnName + "=? AND " + CategoryDiscount.supplierIdColumnName
                    + "=? AND " + CategoryDiscount.categoryNameColumnName + "=?)";
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
    public boolean update(CategoryDiscount categoryDiscount, String oldField) throws SQLException {
        if (extractType(oldField) == 1)
            return updateDate(categoryDiscount, oldField);
        else if (extractType(oldField) == 2)
            return updateID(categoryDiscount, oldField);
        else //extractType(oldField) == 3
            return updateName(categoryDiscount, oldField);
    }

    private boolean updateDate(CategoryDiscount categoryDiscount, String oldDate) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + CategoryDiscount.discountDateColumnName + "=?, " +
                    CategoryDiscount.discountColumnName + "=?, " + CategoryDiscount.itemCountColumnName +
                    "=? WHERE(" + CategoryDiscount.discountDateColumnName + "=? AND " + CategoryDiscount.supplierIdColumnName
                    + "=? AND " + CategoryDiscount.categoryNameColumnName + "=?)";
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

    public boolean updateID(CategoryDiscount categoryDiscount, String oldId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + CategoryDiscount.supplierIdColumnName + "=?, " +
                    CategoryDiscount.discountColumnName + "=?, " + CategoryDiscount.itemCountColumnName +
                    "=? WHERE(" + CategoryDiscount.discountDateColumnName + "=? AND " + CategoryDiscount.supplierIdColumnName
                    + "=? AND " + CategoryDiscount.categoryNameColumnName + "=?)";
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

    public boolean updateName(CategoryDiscount categoryDiscount, String oldName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "UPDATE " + tableName + " SET " + CategoryDiscount.categoryNameColumnName + "=?, " +
                    CategoryDiscount.discountColumnName + "=?, " + CategoryDiscount.itemCountColumnName +
                    "=? WHERE(" + CategoryDiscount.discountDateColumnName + "=? AND " + CategoryDiscount.supplierIdColumnName
                    + "=? AND " + CategoryDiscount.categoryNameColumnName + "=?)";
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
    public boolean select(CategoryDiscount categoryDiscount) throws SQLException {
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
    public boolean select(CategoryDiscount categoryDiscount, List<CategoryDiscount> categoryDiscounts) throws SQLException {
        boolean isDesired = false;
        CategoryDiscount savedCategoryDiscount = new CategoryDiscount();
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(categoryDiscount.getDiscountDate()) &&
                resultSet.getString(2).equals(categoryDiscount.getSupplierID());
                if (isDesired) {
                    savedCategoryDiscount = new CategoryDiscount();
                    categoryDiscount.setCategoryName(resultSet.getString(3));
                    categoryDiscount.setDiscount(resultSet.getInt(4));
                    categoryDiscount.setItemCount(resultSet.getInt(5));
                    categoryDiscounts.add(savedCategoryDiscount);
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
