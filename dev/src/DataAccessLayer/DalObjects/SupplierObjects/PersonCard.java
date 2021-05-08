package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalControllers.SupplierControllers.PersonCardDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class PersonCard extends DalObject<PersonCard> {
    public static final String idColumnName = "ID";
    public static final String firstNameColumnName = "First_Name";
    public static final String lastNameColumnName = "Last_Name";
    public static final String emailColumnName = "Email";
    public static final String phoneColumnName = "Phone";
    private String firstName;
    private String lastName;
    private String email;
    private String id; //primary key
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
