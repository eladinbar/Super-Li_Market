package DataAccessLayer.DalControllers.InventoryControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CategoryDalController extends DalController<Category> {
    private static CategoryDalController instance = null;
    public final static String CATEGORY_TABLE_NAME = "Categories";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private CategoryDalController() throws SQLException {
        super(CATEGORY_TABLE_NAME);
    }

    public static CategoryDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new CategoryDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + CATEGORY_TABLE_NAME + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + CATEGORY_TABLE_NAME + " (" +
                    Category.categoryNameColumnName + " TEXT NOT NULL," +
                    Category.parentNameColumnName + " TEXT," +
                    "PRIMARY KEY (" + Category.categoryNameColumnName + ")" +
                    ");";
            PreparedStatement stmt = conn.prepareStatement(command);
            System.out.println("Creating '" + CATEGORY_TABLE_NAME + "' table.");
            stmt.executeUpdate();
            System.out.println("Table '" + CATEGORY_TABLE_NAME + "' created.");
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(Category category) throws SQLException {
        System.out.println("Initiating category insert");
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:/D:\\Year2\\ניתוצ\\עבודה 2\\database.db")) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?,?)";
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, category.getName());
            st.setString(2, category.getParentName());
            System.out.println("Executing category insert");
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(Category dalObject) {
        return false;
    }

    @Override
    public boolean update(Category dalObject) {
        return false;
    }

    @Override
    public Category select(Category dalObject) {
        return null;
    }
}
