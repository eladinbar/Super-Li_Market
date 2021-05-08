package BusinessLayer.InventoryPackage.SalePackage;

import InfrastructurePackage.Pair;

import java.time.LocalDate;
import java.util.Calendar;

public abstract class Sale {
    protected String name;
    protected double discount;
    protected Pair<LocalDate, LocalDate> saleDates;

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Pair<LocalDate, LocalDate> getSaleDates() {
        return saleDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSaleDates(LocalDate startDate, LocalDate endDate) {
        this.saleDates.setFirst(startDate);
        this.saleDates.setSecond(endDate);
    }
}
