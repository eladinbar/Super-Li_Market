package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

public class PersonCard extends DalObject<PersonCard> {
    private String firstName;
    private String lastName;
    private String email;
    private String id;
    private String phone;

    public PersonCard(DalController<PersonCard> controller, String firstName, String lastName, String email, String id, String phone) {
        super(controller);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = id;
        this.phone = phone;
    }
}
