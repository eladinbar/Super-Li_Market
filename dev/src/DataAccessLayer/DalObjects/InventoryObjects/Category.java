package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.DalController;
import DataAccessLayer.DalObjects.DalObject;

public class Category extends DalObject<Category> {
    private String name;
    private String parentName;

    protected Category(DalController<Category> controller, String name, String parentName) {
        super(controller);
        this.name = name;
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
