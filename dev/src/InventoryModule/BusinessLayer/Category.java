package InventoryModule.BusinessLayer;

import java.util.ArrayList;
import java.util.List;

public class Category {
    private String name;
    private final List<Item> items;
    private Category parentCategory;
    private final List<Category> subCategories;

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

    public List<Category> getSubCategories() {
        return subCategories;
    }

    public void addSubCategory(Category subCategory) {
        this.subCategories.add(subCategory);
    }

    public void removeSubCategory(Category subCategory) {
        this.subCategories.remove(subCategory);
    }
}
