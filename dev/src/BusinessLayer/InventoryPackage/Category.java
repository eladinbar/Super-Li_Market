package BusinessLayer.InventoryPackage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private DataAccessLayer.DalObjects.InventoryObjects.Category dalCopyCategory;

    private String name;
    private List<Item> items;
    private Category parentCategory;
    private List<Category> subCategories;

    public Category() {}

    public Category(String name) {
        this.name = name;
        items = new ArrayList<>();
        parentCategory = null;
        subCategories = new ArrayList<>();
    }

    public Category(String name, List<Item> items, Category parentCategory, List<Category> subCategories) {
        this.name = name;
        this.items = items;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws SQLException {
        dalCopyCategory.setName(name); //Primary Key fields auto-update and auto-check
        this.name = name;

    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void removeItem(Item item) {
        this.items.remove(item);
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        dalCopyCategory.setParentName(parentCategory.name);
        try {
            dalCopyCategory.update();
            this.parentCategory = parentCategory;
        } catch (SQLException ex) {
            dalCopyCategory.setParentName(this.parentCategory.name);
            throw new RuntimeException("Something went wrong.");
        }
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(Category subCategory) {
        this.subCategories.add(subCategory);
    }

    public void removeSubCategory(Category subCategory) {
        this.subCategories.remove(subCategory);
    }

    public void save() throws SQLException {
        dalCopyCategory = new DataAccessLayer.DalObjects.InventoryObjects.Category(this.name, this.parentCategory.name);
        dalCopyCategory.save();
    }

    public void delete() throws SQLException {
        dalCopyCategory.delete();
    }

    public void update() throws SQLException {
        dalCopyCategory.update();
    }

    public boolean find() throws SQLException {
        dalCopyCategory = new DataAccessLayer.DalObjects.InventoryObjects.Category(name, null);

        boolean found = dalCopyCategory.find(); //Retrieves DAL Category from the database
        //Set the fields according to the retrieved data
        if (found) {
            this.name = dalCopyCategory.getName();

            List<Item> savedItems = new ArrayList<>();
            Item item = new Item();
            item.find(savedItems, name);
            this.items = savedItems;

            if (dalCopyCategory.getParentName() != null) {
                this.parentCategory = new Category(dalCopyCategory.getName());
                parentCategory.addSubCategory(this);
            }
        }

        return found;
    }
}
