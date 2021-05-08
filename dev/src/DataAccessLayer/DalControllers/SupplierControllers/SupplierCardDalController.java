package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.ItemDalController;
import DataAccessLayer.DalObjects.InventoryObjects.Item;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCard;
import DataAccessLayer.DalObjects.SupplierObjects.ProductsInOrder;
import DataAccessLayer.DalObjects.SupplierObjects.SupplierCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupplierCardDalController extends DalController<SupplierCard> {
    private static SupplierCardDalController instance = null;
    public final static String SUPPLIER_CARD_TABLE_NAME = "Supplier_Cards";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private SupplierCardDalController() throws SQLException {
        super(SUPPLIER_CARD_TABLE_NAME);
    }

    public static SupplierCardDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new SupplierCardDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    SupplierCard.supplierIdColumnName + " INTEGER NOT NULL," +
                    SupplierCard.companyNumberColumnName + " INTEGET NOT NULL," + SupplierCard.isPermanentDaysColumnName +" INTEGER NOT NULL," + SupplierCard.selfDeliveryColumnName+ " INTEGER NOT NULL,"+ SupplierCard.paymentColumnName+" TEXT NOT NULL,"+
                    "FOREIGN KEY (" + SupplierCard.supplierIdColumnName + ")" + "REFERENCES " + PersonCard.idColumnName + " (" + PersonCardDalController.PERSON_CARD_TABLE_NAME +") ON DELETE NO ACTION "+
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
    public boolean insert(SupplierCard dalObject) {
        return false;
    }

    @Override
    public boolean delete(SupplierCard dalObject) {
        return false;
    }

    @Override
    public boolean update(SupplierCard dalObject) {
        return false;
    }

    @Override
    public SupplierCard select(SupplierCard dalObject) {
        return null;
    }
}
