package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCard;

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
    public void CreateTable() throws SQLException {

    }

    @Override
    public boolean Insert(PersonCard dalObject) {
        return false;
    }

    @Override
    public boolean Delete(PersonCard dalObject) {
        return false;
    }

    @Override
    public PersonCard ConvertReaderToObject() {
        return null;
    }
}
