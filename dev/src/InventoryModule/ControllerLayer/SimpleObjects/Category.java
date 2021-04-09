package InventoryModule.ControllerLayer.SimpleObjects;

import java.util.List;

public class Category implements SimpleEntity {
    private String name;
    private List<Item> items;
    private String parentCategory;
    private List<String> subCategories;

    public Category(String name, List<Item> items, String parentCategory, List<String> subCategories) {
        this.name = name;
        this.items = items;
        this.parentCategory = parentCategory;
        this.subCategories = subCategories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }
}
