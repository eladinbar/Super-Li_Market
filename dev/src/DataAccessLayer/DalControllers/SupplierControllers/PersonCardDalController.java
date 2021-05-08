package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.Order;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PersonCardDalController extends DalController<PersonCard> {
    private static PersonCardDalController instance = null;
    public final static String PERSON_CARD_TABLE_NAME = "Person_Cards";

    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     */
    private PersonCardDalController() throws SQLException {
        super(PERSON_CARD_TABLE_NAME);
    }

    public static PersonCardDalController getInstance() throws SQLException {
        if (instance == null)
            instance = new PersonCardDalController();
        return instance;
    }

    @Override
    public boolean createTable() throws SQLException {
        System.out.println("Initiating create '" + tableName + "' table.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    PersonCard.idColumnName + " INTEGER NOT NULL," +
                    PersonCard.firstNameColumnName + " TEXT NOT NULL," + PersonCard.lastNameColumnName +" TEXT NOT NULL,"+ PersonCard.emailColumnName + " TEXT NOT NULL," + PersonCard.phoneColumnName + " TEXT NOT NULL,"+
                    "PRIMARY KEY (" + PersonCard.idColumnName + ")" +
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
    public boolean insert(PersonCard dalObject) {
        return false;
    }

    @Override
    public boolean delete(PersonCard dalObject) {
        return false;
    }

    @Override
    public boolean update(PersonCard dalObject) {
        return false;
    }

    @Override
    public PersonCard select(PersonCard dalObject) {
        return null;
    }
}
