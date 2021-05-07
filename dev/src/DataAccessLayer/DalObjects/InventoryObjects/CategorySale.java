package DataAccessLayer.DalObjects.InventoryObjects;

import DataAccessLayer.DalControllers.InventoryControllers.CategorySaleDalController;
import DataAccessLayer.DalObjects.DalObject;
import InfrastructurePackage.Pair;

import java.sql.SQLException;
import java.util.Calendar;

public class CategorySale extends DalObject<CategorySale> {
    private String name; //Primary Key
    private double discount;
    private Pair<Calendar, Calendar> saleDates;
    private String categoryName; //Foreign Key

    protected CategorySale(String name, double discount, Pair<Calendar, Calendar> saleDates, String categoryName) throws SQLException {
        super(CategorySaleDalController.getInstance());
        this.name = name;
        this.discount = discount;
        this.saleDates = saleDates;
        this.categoryName = categoryName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Pair<Calendar, Calendar> getSaleDates() {
        return saleDates;
    }

    public void setSaleDates(Pair<Calendar, Calendar> saleDates) {
        this.saleDates = saleDates;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
