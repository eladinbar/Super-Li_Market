package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;
import DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;

import java.sql.*;

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
                    "REFERENCES " + SupplierCard.supplierIdColumnName + " (" + SUPPLIER_CARD_TABLE_NAME + ") ON DELETE NO ACTION," +
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
            String query = "INSERT INTO " + tableName + " VALUES (?,?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

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
            String query = "DELETE FROM " + tableName + " WHERE (" + CategoryDiscount.discountDateColumnName + "=? AND " + CategoryDiscount.supplierIdColumnName +
                    "=? AND " + CategoryDiscount.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

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
            String query = "UPDATE " + tableName + " SET " + CategoryDiscount.discountColumnName + "=?, " + CategoryDiscount.itemCountColumnName +
                     "=? WHERE(" + CategoryDiscount.discountDateColumnName + "=? AND " + CategoryDiscount.supplierIdColumnName
                    + "=? AND " + CategoryDiscount.categoryNameColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

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
    public CategoryDiscount select(CategoryDiscount categoryDiscount) throws SQLException {
        CategoryDiscount savedCategoryDiscount = new CategoryDiscount(categoryDiscount.getDiscountDate(), categoryDiscount.getSupplierID(),
                categoryDiscount.getCategoryName(), 0, 0);
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(0).equals(categoryDiscount.getDiscountDate()) &&
                        resultSet.getString(1).equals(categoryDiscount.getSupplierID()) && resultSet.getString(2).equals(categoryDiscount.getCategoryName());
                if (isDesired) {
                    savedCategoryDiscount.setDiscount(resultSet.getInt(1));
                    savedCategoryDiscount.setItemCount(resultSet.getInt(2));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return savedCategoryDiscount;
    }
}
