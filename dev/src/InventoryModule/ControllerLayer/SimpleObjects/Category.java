package InventoryModule.ControllerLayer.SimpleObjects;

import java.util.List;

public class Category implements SimpleEntity {
    private String name;
    private List<Item> items;
    private Category parentCategory;
    private List<Category> subCategories;

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
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Category> subCategories) {
        this.subCategories = subCategories;
    }
}
