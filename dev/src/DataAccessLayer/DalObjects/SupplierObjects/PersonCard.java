package DataAccessLayer.DalObjects.SupplierObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

public class PersonCard extends DalObject<PersonCard> {
    protected PersonCard(DalController<PersonCard> controller) {
        super(controller);
    }
}
