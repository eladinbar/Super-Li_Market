package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController$;
import DataAccessLayer.DalObjects.SupplierObjects.DalPersonCard;

import java.sql.*;

public class PersonCardDalController extends DalController$<DalPersonCard> {
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
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String command = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    DalPersonCard.idColumnName + " INTEGER NOT NULL," +
                    DalPersonCard.firstNameColumnName + " TEXT NOT NULL," +
                    DalPersonCard.lastNameColumnName +" TEXT NOT NULL,"+
                    DalPersonCard.emailColumnName + " TEXT NOT NULL," +
                    DalPersonCard.phoneColumnName + " TEXT NOT NULL,"+
                    "PRIMARY KEY (" + DalPersonCard.idColumnName + ")" +
                    ");";

            PreparedStatement stmt = conn.prepareStatement(command);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean insert(DalPersonCard personCard) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "INSERT INTO " + tableName + " VALUES (?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, personCard.getId());
            stmt.setString(2, personCard.getFirstName());
            stmt.setString(3, personCard.getLastName());
            stmt.setString(4, personCard.getEmail());
            stmt.setString(5, personCard.getPhone());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean delete(DalPersonCard personCard) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "DELETE FROM " + tableName + " WHERE (" + DalPersonCard.idColumnName+ "=?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, personCard.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return true;
    }

    @Override
    public boolean update(DalPersonCard personCard) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "UPDATE " + tableName + " SET " + DalPersonCard.firstNameColumnName + "=?, "+
                    DalPersonCard.lastNameColumnName +"=?, "+
                    DalPersonCard.emailColumnName+"=?, "+
                    DalPersonCard.phoneColumnName+"=? " +
                    " WHERE(" + DalPersonCard.idColumnName + "=?)";
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
    public boolean select(DalPersonCard personCard) throws SQLException {
        boolean isDesired = false;
        try (Connection conn = DriverManager.getConnection(connectionString)) {
            String query = "SELECT * FROM " + tableName;
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next())
            {
                isDesired = resultSet.getString(1).equals(personCard.getId());
                if (isDesired) {
                    personCard.setFirstNameLoad(resultSet.getString(2));
                    personCard.setLastNameLoad(resultSet.getString(3));
                    personCard.setEmailLoad(resultSet.getString(4));
                    personCard.setPhoneLoad(resultSet.getString(5));
                    break; //Desired category discount found
                }
            }
        } catch (SQLException ex) {
            throw new SQLException(ex.getMessage());
        }
        return isDesired;
    }
}
