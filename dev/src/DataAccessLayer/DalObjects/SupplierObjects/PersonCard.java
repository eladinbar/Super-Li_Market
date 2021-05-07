package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.SupplierControllers.PersonCardDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class PersonCard extends DalObject<PersonCard> {
    public final String firstNameColumnName = "First_Name";
    public final String lastNameColumnName = "Last_Name";
    public final String emailColumnName = "Email";
    public final String idColumnName = "ID";
    public final String phoneColumnName = "Phone";
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String phone;

    public PersonCard(String firstName, String lastName, String email, String id, String phone) throws SQLException {
        super(PersonCardDalController.getInstance());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.phone = phone;
    }
}
