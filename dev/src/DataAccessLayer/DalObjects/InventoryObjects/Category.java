package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class Category extends DalObject<Category> {
    public final String categoryNameColumnName = "Name"; //Primary Key
    public final String parentNameColumnName = "ParentName";

    private String name; //Primary Key
    private String parentName;

    protected Category(String name, String parentName) throws SQLException {
        super(CategoryDalController.getInstance());
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
