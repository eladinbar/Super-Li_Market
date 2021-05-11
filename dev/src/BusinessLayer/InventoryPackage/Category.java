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

    public void setName(String name) {
        dalCopyCategory.setName(name);
        this.name = name;
    }

    public void setAndSaveName(String name) {
        dalCopyCategory.setAndSaveName(name);
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
        this.parentCategory = parentCategory;
    }

    public void setAndSaveParentCategory(Category parentCategory) {
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

    public void addSubCategories(List<Category> subCategories) {
        this.subCategories.addAll(subCategories);
    }

    public void removeSubCategory(Category subCategory) {
        this.subCategories.remove(subCategory);
    }

    public void save() {
        try {
            dalCopyCategory = new DataAccessLayer.DalObjects.InventoryObjects.Category(this.name, this.parentCategory.name);
            dalCopyCategory.save();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void delete() {
        try {
            dalCopyCategory.delete();
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
    }

    public void update() throws SQLException {
        dalCopyCategory.update();
    }

    public boolean find() {
        boolean found;
        try {
            dalCopyCategory = new DataAccessLayer.DalObjects.InventoryObjects.Category(name);

            found = dalCopyCategory.find(); //Retrieves DAL Category from the database
            //Set the fields according to the retrieved data
            if (found) {
                this.name = dalCopyCategory.getName();

                List<Item> savedItems = new ArrayList<>();
                Item item = new Item();
                item.find(savedItems, name);
                this.items = savedItems;

                //Set dud parent category for parent category name
                if (dalCopyCategory.getParentName() != null) {
                    this.parentCategory = new Category(dalCopyCategory.getParentName());
                }

                //Get all sub categories to set sub category names
                List<Category> subCategories = new ArrayList<>();
                Category dudCategory = new Category();
                dudCategory.find(subCategories, this.name);

                this.subCategories = subCategories;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }

        return found;
    }

    public boolean find(List<Category> categories, String parentName) {
        boolean found;
        try {
            List<DataAccessLayer.DalObjects.InventoryObjects.Category> dalCopyCategories = new ArrayList<>();
            dalCopyCategory = new DataAccessLayer.DalObjects.InventoryObjects.Category(null);
            dalCopyCategory.setParentName(parentName);

            found = dalCopyCategory.find(dalCopyCategories); //Retrieves DAL Categories from the database according to 'parentName'
            //Set the fields according to the retrieved data
            if (found) {
                for (DataAccessLayer.DalObjects.InventoryObjects.Category category : dalCopyCategories) {
                    List<Item> savedItems = new ArrayList<>();
                    Item item = new Item();
                    item.find(savedItems, category.getName());
                    Category savedCategory = new Category(category.getName(), savedItems, new Category(category.getParentName()), new ArrayList<>());

                    categories.add(savedCategory);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
        return found;
    }

    public boolean find(List<Category> categories) {
        boolean found;
        try {
            List<DataAccessLayer.DalObjects.InventoryObjects.Category> dalCopyCategories = new ArrayList<>();
            dalCopyCategory = new DataAccessLayer.DalObjects.InventoryObjects.Category();

            found = dalCopyCategory.findAll(dalCopyCategories); //Retrieves all DAL categories from the database
            //Set the fields according to the retrieved data
            if (found) {
                for (DataAccessLayer.DalObjects.InventoryObjects.Category category : dalCopyCategories) {
                    List<Item> savedItems = new ArrayList<>();
                    Item item = new Item();
                    item.find(savedItems, category.getName());
                    Category savedCategory = new Category(category.getName(), savedItems, new Category(category.getParentName()), new ArrayList<>());

                    categories.add(savedCategory);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Something went wrong.");
        }
        return found;
    }
}
