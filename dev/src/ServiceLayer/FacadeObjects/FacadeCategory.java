package ServiceLayer.FacadeObjects;

import java.util.List;

public class FacadeCategory extends FacadeEntity {
    private String name;
    private List<FacadeItem> items;
    private String parentCategory;
    private List<String> subCategories;

    public FacadeCategory(String name, List<FacadeItem> items, String parentCategory, List<String> subCategories) {
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

    public List<FacadeItem> getItems() {
        return items;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public List<String> getSubCategories() {
        return subCategories;
    }
}
