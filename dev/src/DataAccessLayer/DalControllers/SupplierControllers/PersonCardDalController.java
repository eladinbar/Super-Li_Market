package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCardDal;

import java.sql.*;

public class PersonCardDalController extends DalController<PersonCardDal> {
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
                    PersonCardDal.idColumnName + " INTEGER NOT NULL," +
                    PersonCardDal.firstNameColumnName + " TEXT NOT NULL," + PersonCardDal.lastNameColumnName +" TEXT NOT NULL,"+ PersonCardDal.emailColumnName + " TEXT NOT NULL," + PersonCardDal.phoneColumnName + " TEXT NOT NULL,"+
                    "PRIMARY KEY (" + PersonCardDal.idColumnName + ")" +
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
    public boolean insert(PersonCardDal personCard) throws SQLException {
        System.out.println("Initiating " + tableName + " insert.");
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, personCard.getId());
            stmt.setString(2, personCard.getFirstName());
            stmt.setString(3, personCard.getLastName());
            stmt.setString(4, personCard.getEmail());
            stmt.setString(5, personCard.getPhone());
            System.out.println("Executing " + tableName + " insert.");
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(PersonCardDal personCard) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + PersonCardDal.idColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, personCard.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(PersonCardDal personCard) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + PersonCardDal.firstNameColumnName +
                    "=?, "+ PersonCardDal.lastNameColumnName +"=?, "+PersonCardDal.emailColumnName+"=?, "+
                    PersonCardDal.phoneColumnName+"=?" +
                    " WHERE(" + PersonCardDal.idColumnName + "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, personCard.getFirstName());
            stmt.setString(2, personCard.getLastName());
            stmt.setString(3, personCard.getEmail());
            stmt.setString(4, personCard.getPhone());
            stmt.setString(5, personCard.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean select(PersonCardDal personCard) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next())
            {
                isDesired = resultSet.getString(0).equals(personCard.getId());
                if (isDesired) {
                    personCard.setFirstName(resultSet.getString(1));
                    personCard.setLastName(resultSet.getString(2));
                    personCard.setEmail(resultSet.getString(3));
                    personCard.setPhone(resultSet.getString(4));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
