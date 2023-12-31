package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.CategoryDalController;
import DataAccessLayer.DalObjects.DalObject;

import java.sql.SQLException;

public class DalCategory extends DalObject<DalCategory> {
    public static final String categoryNameColumnName = "Name"; //Primary Key
    public static final String parentNameColumnName = "ParentName";

    private String name;
    private String parentName;

    public DalCategory() throws SQLException {
        super(CategoryDalController.getInstance());
    }

    public DalCategory(String name) throws SQLException {
        super(CategoryDalController.getInstance());
        this.name = name;
    }

    public DalCategory(String name, String parentName) throws SQLException {
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

    public void setName(String name) {
        this.name = name;
    }

    public void setAndSaveName(String name) {
        String oldName = this.name;
        this.name = name;
        try {
            controller.update(this, oldName);
        } catch (SQLException ex) {
            this.name = oldName;
            throw new RuntimeException("Something went wrong.");
        }
    }
}
