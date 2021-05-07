package DataAccessLayer.DalControllers.SupplierControllers;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.SupplierObjects.PersonCard;

public class PersonCardDalController extends DalController<PersonCard> {
    /**
     * <summary>
     * A public constructor, initializes the database path and the connection string accordingly. Initializes the respective table name and creates it in the database.
     * </summary>
     * <param name="tableName">The table name of the object this controller represents.</param>
     *
     * @param tableName
     */
    public PersonCardDalController(String tableName) {
        super(tableName);
    }

    @Override
    public void CreateTable() {

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
