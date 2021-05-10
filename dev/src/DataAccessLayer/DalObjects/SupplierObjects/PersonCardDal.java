package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.SupplierControllers.PersonCardDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class PersonCardDal extends DalObject<PersonCardDal> {
    public static final String idColumnName = "ID";
    public static final String firstNameColumnName = "First_Name";
    public static final String lastNameColumnName = "Last_Name";
    public static final String emailColumnName = "Email";
    public static final String phoneColumnName = "Phone";
    private String id; //primary key
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public PersonCardDal(String firstName, String lastName, String email, String id, String phone) throws SQLException {
        super(PersonCardDalController.getInstance());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(String id) throws SQLException {
        this.id = id;
        update();
    }

    public void setFirstName(String firstName) throws SQLException {
        this.firstName = firstName;
        update();
    }

    public void setLastName(String lastName) throws SQLException {
        this.lastName = lastName;
        update();
    }

    public void setEmail(String email) throws SQLException {
        this.email = email;
        update();
    }

    public void setPhone(String phone) throws SQLException {
        this.phone = phone;
        update();
    }
}
