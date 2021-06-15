package BusinessLayer.SuppliersPackage.SupplierPackage;

import DataAccessLayer.DalObjects.SupplierObjects.DalPersonCard;

import java.sql.SQLException;

public class PersonCard {
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String id;
    protected String phone;
    protected DalPersonCard dalObject;

    public PersonCard(String firstName, String lastName, String email, String id, String phone) throws SQLException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.dalObject = toDalObject();
        dalObject.save();
    }

    public PersonCard(String firstName, String lastName, String email, String id, String phone, boolean load) throws SQLException {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.dalObject = toDalObject();
    }

    public PersonCard(String id) throws SQLException {
        this.id = id;
        this.dalObject = toDalObject();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) throws SQLException {
        this.firstName = firstName;
        dalObject.setFirstName(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) throws SQLException {
        this.lastName = lastName;
        dalObject.setLastName(lastName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws SQLException {
        this.email = email;
        dalObject.setEmail(email);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) throws SQLException {
        this.id = id;
        dalObject.setId(id);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) throws SQLException {
        this.phone = phone;
        dalObject.setPhone(phone);
    }

    public boolean find() throws SQLException {
        return dalObject.find();
    }

    public void save() throws SQLException {
        dalObject.save();
    }

    public void update() throws SQLException {
        dalObject.update();
    }

    public DalPersonCard toDalObject() throws SQLException {
        return new DalPersonCard(firstName, lastName, email, id, phone);
    }
}
