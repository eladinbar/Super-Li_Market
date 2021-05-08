package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.Category;
import DataAccessLayer.DalObjects.InventoryObjects.CategoryDiscount;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.SupplierObjects.AgreementItems;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;

import java.sql.*;

import static DataAccessLayer.DalControllers.SupplierControllers.SupplierCardDalController.SUPPLIER_CARD_TABLE_NAME;

public class AgreementItemsDalController extends DalController<AgreementItems> {
    private static AgreementItemsDalController instance = null;
    public final static String AGREEMENT_ITEMS_TABLE_NAME = "Agreement_Items";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private AgreementItemsDalController() throws SQLException {
        super(AGREEMENT_ITEMS_TABLE_NAME);
    }

    public static AgreementItemsDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new AgreementItemsDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    AgreementItems.productIdColumnName + " INTEGER NOT NULL," +
                    AgreementItems.productCompIdColumnName + " INTEGER NOT NULL," + AgreementItems.priceColumnName +" REAL NOT NULL,"+
                    "PRIMARY KEY ("+ AgreementItems.productIdColumnName +"),"+
                    "FOREIGN KEY (" + AgreementItems.productIdColumnName + ")" + "REFERENCES " + Item.itemIdColumnName + " (" + ItemDalController.ITEM_TABLE_NAME +") ON DELETE NO ACTION "+
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
    public boolean insert(AgreementItems agreementItem) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, agreementItem.getProductId());
            stmt.setInt(2, agreementItem.getProductCompId());
            stmt.setDouble(3, agreementItem.getPrice());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(AgreementItems agreementItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + AgreementItems.productIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, agreementItem.getProductId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(AgreementItems agreementItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + AgreementItems.priceColumnName +
                    "=?, "+AgreementItems.productCompIdColumnName+"=? WHERE(" + AgreementItems.productIdColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setDouble(1, agreementItem.getPrice());
            stmt.setInt(2, agreementItem.getProductCompId());
            stmt.setInt(3, agreementItem.getProductId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public AgreementItems select(AgreementItems agreementItem) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                boolean isDesired = resultSet.getString(0).equals(agreementItem.getProductId());
                if (isDesired) {
                    agreementItem.setProductCompId(resultSet.getInt(1));
                    agreementItem.setPrice(resultSet.getInt(2));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return agreementItem;
    }
}
