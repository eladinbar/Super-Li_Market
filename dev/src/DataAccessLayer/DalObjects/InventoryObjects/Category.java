package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class Category extends DalObject<Category> {
    public static final String categoryNameColumnName = "Name"; //Primary Key
    public static final String parentNameColumnName = "ParentName";

    private String name;
    private String parentName;

    public Category(String name, String parentName) throws SQLException {
        super(CategoryDalController.getInstance());
        this.name = name;
        this.parentName = parentName;
    }

    public String getName() {
        return name;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
